package ua.pp.fairwind.communications.lines.abstracts;

import ua.pp.fairwind.communications.abstractions.LineSelector;
import ua.pp.fairwind.communications.abstractions.SystemEllement;
import ua.pp.fairwind.communications.devices.abstracts.DeviceInterface;
import ua.pp.fairwind.communications.devices.abstracts.ImitatorDevice;
import ua.pp.fairwind.communications.devices.abstracts.LineSelectDevice;
import ua.pp.fairwind.communications.devices.logging.LineMonitoringEvent;
import ua.pp.fairwind.communications.internatianalisation.I18N;
import ua.pp.fairwind.communications.lines.exceptions.LineErrorException;
import ua.pp.fairwind.communications.lines.exceptions.LineTimeOutException;
import ua.pp.fairwind.communications.lines.exceptions.TrunsactionError;
import ua.pp.fairwind.communications.lines.lineparams.LineParameters;
import ua.pp.fairwind.communications.lines.operations.CommunicationAnswer;
import ua.pp.fairwind.communications.lines.operations.CommunicationProtocolRequest;
import ua.pp.fairwind.communications.lines.performance.PerformanceMonitorEventData;
import ua.pp.fairwind.communications.messagesystems.event.EventType;
import ua.pp.fairwind.communications.utils.CommunicationUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Сергей on 10.07.2015.
 */
abstract  public class AbstractLine extends SystemEllement implements LineInterface  {
    private final LinkedBlockingQueue<CommunicationProtocolRequest> requests=new LinkedBlockingQueue<>();
    private final AtomicBoolean threadRunned=new AtomicBoolean(false);
    private final AtomicBoolean threadCreated=new AtomicBoolean(false);
    private final AtomicBoolean transuction=new AtomicBoolean(false);
    private final AtomicBoolean threadPaused=new AtomicBoolean(false);
    private final AtomicBoolean performanceMonitor=new AtomicBoolean(false);
    private final AtomicBoolean threadTrunsactionPaused=new AtomicBoolean(false);
    protected volatile SERVICE_MODE serverMode=SERVICE_MODE.CLIENT_ONLY;
    private volatile long startTrunsactionTime=-1;
    private volatile UUID trunsactionUUID;
    private final long maxTransactionTime;
    private final Set<DeviceInterface> readmonitoring=new HashSet<>();
    private final Set<DeviceInterface> writemonitoring=new HashSet<>();
    private final Set<ImitatorDevice> deviceForService=new HashSet<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final ExecutorService service= Executors.newCachedThreadPool();
    private volatile LineSelectDevice lineSelector;
    private volatile LineParameters serverLineParameter;
    private volatile LineParameters curentLineParameters;
    private volatile Object curentLine;

    protected AbstractLine(String codename, String uuid, long maxTransactionTime) {
        super(codename, uuid);
        this.maxTransactionTime = maxTransactionTime;
    }

    protected AbstractLine(String codename, long maxTransactionTime) {
        super(codename, null);
        this.maxTransactionTime = maxTransactionTime;
    }

    protected AbstractLine(String codename, String uuid) {
        super(codename, uuid);
        this.maxTransactionTime = 5000;
    }

    public static String localizeError(String key){
        return localizeError("line", key);
    }
    //Проверка активности атранзакций (внутренний для других методов)
    synchronized private boolean isTrunsactionActive(){
        if(transuction.get()){
            if(startTrunsactionTime+ maxTransactionTime >System.currentTimeMillis()){
                transuction.set(false);
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    //Отправка данных из серверного режима
    protected void sendMessageFromServerMode(byte[] data, LineParameters params) throws LineErrorException,LineTimeOutException{
        sendMessage(data, serverLineParameter);
    }
    //Обработка запроса при серверном режиме
    protected void serviceRecivedFromLineDataInServerMode(byte[] recivvedBuffer){
        if(!threadPaused.get() && serverMode!=SERVICE_MODE.CLIENT_ONLY && recivvedBuffer!=null && recivvedBuffer.length>0) {
            readmonitor(recivvedBuffer,null);
            deviceForService.parallelStream().forEach(device -> {
                try {
                    byte[] copydata = Arrays.copyOf(recivvedBuffer, recivvedBuffer.length);
                    byte[] answer = device.process_line_data(copydata);
                    if (answer != null && answer.length > 0) {
                        try {
                            lock.lock();
                            long starttime=System.currentTimeMillis();
                            sendMessageFromServerMode(answer, serverLineParameter);
                            perfarmanceMonitoring(PerformanceMonitorEventData.EXECUTE_TYPE.WRITE_OPERATION, System.currentTimeMillis() - starttime);
                            writemonitor(answer, null);
                        } catch (Exception e) {
                            fireEvent(EventType.ERROR, e.getLocalizedMessage());
                            device.errorDuringSend();
                        } finally {
                            lock.unlock();
                        }
                    }
                } catch (Exception err) {
                    fireEvent(EventType.ERROR, err.getLocalizedMessage());
                }
            });
        }
    }

    //Метод записи сообщений производителности
    private void perfarmanceMonitoring(PerformanceMonitorEventData.EXECUTE_TYPE typeExecute,long time){
        if(performanceMonitor.get()) {
            PerformanceMonitorEventData data=new PerformanceMonitorEventData(typeExecute,time);
            fireEvent(EventType.PERFORMANCE, data);
        }
    }
    //метод проверки что транзакция свободна, требует значение UUID того, кто хочет занять транзакцтию
    synchronized private boolean isTrunsactionActive(UUID uuid){
        if(uuid==null) return false;
        if(transuction.get()){
            if(startTrunsactionTime+ maxTransactionTime >System.currentTimeMillis()){
                transuction.set(false);
                return false;
            } else {
                return !uuid.equals(trunsactionUUID);
            }
        } else {
            return false;
        }
    }



    //метод активации транзакции для абонента с UUID (параметр время, которое ожидается возможность получения транзакции
    @Override
    synchronized public void startTransaction(UUID uuid,long waitTime) throws TrunsactionError {
        if(uuid==null) throw new TrunsactionError( localizeError("uuid_error"), TrunsactionError.TrunsactionErrorType.TRUNSACTION_ERROR);
        if(!uuid.equals(trunsactionUUID) && transuction.get() && startTrunsactionTime+ maxTransactionTime <System.currentTimeMillis()){
            throw new TrunsactionError(localizeError("another_trunsaction"), TrunsactionError.TrunsactionErrorType.ANOTHER_TRUNSACTION_EXECUTE);
        } else {
            try {
                if(lock.tryLock(waitTime, TimeUnit.MILLISECONDS)) {
                    threadTrunsactionPaused.set(true);
                    lock.unlock();
                    trunsactionUUID = uuid;
                    startTrunsactionTime = System.currentTimeMillis();
                    transuction.set(true);
                    onStartTrunsaction();
                }
            } catch (InterruptedException ex){
                throw new TrunsactionError(localizeError("acquire_monitor"), TrunsactionError.TrunsactionErrorType.TRUNSACTION_ERROR);
            }
        }
    }

    //Метод выполняет переключение линии на указанную линию параметром, если для линии был выставлено устройство переключения
    public boolean lineSelectorExecute(Object selectLine){
        if(lineSelector!=null&&selectLine!=null) {
            return lineSelectorExecute(lineSelector.selectLine(selectLine));
        }
        return true;
    }

    //Метод выполняет переключение линии  при помощи устройства переключения линий (выполняет формирование,  отравку запроса и контроль ответа от коммутатора линий)
    protected boolean lineSelectorExecute(LineSelector selectData){
        if(selectData==null) return false;
        if(!selectData.isAlreadySelect()){
            try{
                byte[] send=selectData.getSendbuffer();
                //System.out.print("WRITED:");CommunicationUtils.showBuffer(send);
                if(send!=null && send.length>0) {
                    selectData.getPauseBeforeWrite();
                    sendMessage(send, selectData.getLineParam());
                }
                if(selectData.neededByteCount()>0) {
                    selectData.getPauseBeforeRead();
                    byte[] readed = reciveMessage(selectData.getReadTimeOut(),selectData.neededByteCount(),selectData.getLineParam());
                    //System.out.print("READED:");CommunicationUtils.showBuffer(readed);
                    return selectData.compare(readed);
                }
            } catch (LineErrorException|LineTimeOutException ex){
                fireEvent(EventType.ERROR,ex.getLocalizedMessage());
                return false;
            }
        }
        return true;
    }
    //Еще один метод в цепочке переключения линий ВЕРХНИЙ УРОВЕНЬ (выполняет к контроль за параметрами линии и необходимостью переключения)
    private boolean lineSelectorExecute(LineParameters params){
        if(lineSelector!=null) {
            Object always_set_line=params.getLineParameter(LineParameters.ALWAYS_SET_LINE);
            boolean need_set_line=false;
            if(always_set_line!=null && always_set_line instanceof Boolean){
                need_set_line=(Boolean)always_set_line;
            }
            Object selectedline = params.getLineParameter("SUB_LINE_NUMBER");
            Object lineChange=params.getLineParameter("NEED_LINE_CHANGE");
            if(!need_set_line){
                if(curentLine==selectedline){
                    need_set_line=false;
                } else {
                    if(curentLine==null)need_set_line=true;
                    else need_set_line=curentLine.equals(selectedline);
                }
            }
            if(need_set_line) {
                if (lineChange != null && lineChange instanceof Boolean && (Boolean) lineChange) {
                    //System.out.println("NEED SELECT LINE:"+lineChange+" NEW LINE:"+selectedline);
                    return lineSelectorExecute(selectedline);
                } else return true;
            } else {
                return true;
            }

        }
        return true;
    }
    //МЕТОДЫ ДЛЯ РЕАЛИЗАЦИИ В ДОЧЕРНИХ ОБЪЕКТАХ
    //метод выполняет отправку данных в линию (физическое действие по отправке, с перестройков параметров линии на указанные) ВНИМАНИЕ: параметры линии проверяются на идентичноить к уже установленнымм
    abstract protected void sendMessage(byte[] data, LineParameters params) throws LineErrorException,LineTimeOutException;
    //метод выполняет чтение данных из линии (физическое действие по приему указанного количества байт за указанное время, с перестройков параметров линии на указанные) ВНИМАНИЕ: параметры линии проверяются на идентичноить к уже установленнымм
    //КОЛИЧЕСТВО может быть указано как положительное так и отрицательное, положительное надо точно такое количество байт, отрицательное надо не меннее указанного количество (при этом всегда выдерживается пауза)
    abstract protected byte[] reciveMessage(long timeOut,long bytesForReadCount, LineParameters params) throws LineErrorException,LineTimeOutException;
    //метод который физически перестраивает параметры линии
    abstract protected boolean setupLineParameters(LineParameters params, LineParameters curentLineParameters, boolean alwaysSet);
    //метод выполняющий очистку внутренних буфером линии
    abstract protected boolean clearBuffers(LineParameters parameters);
    //метод вызывается всякий раз при старте новой транзакции
    abstract protected void onStartTrunsaction();
    //метод вызывается всякий раз при завершении транзакции
    abstract protected void onEndTrunsaction();
    //метод освобождает ресурсы занятые линией (завершение работы)
    abstract protected void closeUsedResources();
    //Метод сравнения параметров линии запрошенных у уже установленными для предотвращения излишних действий по перестроке параметров
    abstract protected boolean testIdentialyLineParameters(LineParameters current,LineParameters newparmeters);
    //Метод вызывается всякий раз при активации серверного режима
    abstract protected void activateServerMode(LineParameters serverLineParameter,SERVICE_MODE mode);
    //Метод вызывается всякий раз при де активации серверного режима
    abstract protected void deactivateServerMode();
    //МЕТОД закрывает линии (например в случае сокеты или порта)
    @Override
    public abstract void closeLine();

    //Внутренний метод переключения параметров линии со сравнением с уже установленным значением
    protected boolean setLineParameters(LineParameters params){
        if(!clearBuffers(params)) return false;
        if(params==null){
            return setupLineParameters(params, curentLineParameters, true);
        }
        Object always_set_line_param=params.getLineParameter(LineParameters.ALWAYS_SET_LINE_PARAM);
        boolean need_set_line_param=false;
        if(always_set_line_param!=null && always_set_line_param instanceof Boolean){
            need_set_line_param=(Boolean)always_set_line_param;
        }
        if(need_set_line_param){
            return setupLineParameters(params, curentLineParameters, true);
        } else {
            if(!testIdentialyLineParameters(curentLineParameters,params)){
                if(setupLineParameters(params, curentLineParameters, false)){
                    curentLineParameters=params;
                    return true;
                } else {
                    fireEvent(EventType.ERROR,localizeError("line_parameters_error"));
                    return false;
                }

            }else return true;
        }
    }

    ///МЕТОД для пользователей объекта позволяет завершить ранее начятую транзакцию
    @Override
    synchronized public void endTransaction(UUID uuid) {
        if(uuid!=null && isTrunsactionActive() && uuid.equals(trunsactionUUID)){
            transuction.set(false);
            trunsactionUUID=null;
            threadTrunsactionPaused.set(false);
            onEndTrunsaction();
        }
    }

    //Метод отправки сообщения в линии (для пользователя объекта) требует наличия UUID для проверки того, что UUID ранее открыл транзакцию и она еще не завершилась
    @Override
    public void sendMessage(UUID uuid,final byte[] data, LineParameters params) throws TrunsactionError,LineErrorException,LineTimeOutException {
        if(!isTrunsactionActive(uuid) && serverMode!=SERVICE_MODE.SERVER_ONLY){
            try {
                if (lock.tryLock(maxTransactionTime, TimeUnit.MILLISECONDS)) {
                    if (lineSelectorExecute(params)) {
                        long starttime = System.currentTimeMillis();
                        sendMessage(data, params);
                        perfarmanceMonitoring(PerformanceMonitorEventData.EXECUTE_TYPE.WRITE_OPERATION, System.currentTimeMillis() - starttime);
                        writemonitor(data, null);
                    } else {
                        fireEvent(EventType.ERROR,localizeError("line_parameters_error"));
                    }
                } else {
                    endTransaction(uuid);
                    throw new TrunsactionError(localizeError("another_trunsaction"), TrunsactionError.TrunsactionErrorType.TRUNSACTION_ERROR);
                }
            }catch (InterruptedException e){
                endTransaction(uuid);
                throw new TrunsactionError(localizeError("another_trunsaction"), TrunsactionError.TrunsactionErrorType.TRUNSACTION_ERROR);
            } finally {
                lock.unlock();
            }
        } else {
            throw new TrunsactionError(localizeError("another_trunsaction"), TrunsactionError.TrunsactionErrorType.TRUNSACTION_ERROR);
        }
    }

    //внетренний метод для выдачи данных на объеты мониторинга линии (в объеты мониторинга передается содержимое принятых данных)
    private void readmonitor(final byte[] data,final CommunicationProtocolRequest request){
        if(data!=null && data.length>0) {
            fireEvent(EventType.READ_MONITOR,new LineMonitoringEvent(LineMonitoringEvent.ACTION_TYPE.READ,data,this,request!=null?request.getSenderDevice():null,request!=null?request.getProperty():null));
            if (readmonitoring.size() > 0) {
                CommunicationAnswer comans = new CommunicationAnswer(request, CommunicationAnswer.CommunicationResult.READ_MONITOR, data, null, this,0,0);
                readmonitoring.parallelStream().forEach(dev -> dev.processRecivedMessage(comans));
            }
        }
    }
    //внетренний метод для выдачи данных на объеты мониторинга линии (в объеты мониторинга передается содержимое отправляемых данных)
    private void writemonitor(final byte[] data,final CommunicationProtocolRequest request){
        if(data!=null && data.length>0) {
            fireEvent(EventType.WRITE_MONITOR, new LineMonitoringEvent(LineMonitoringEvent.ACTION_TYPE.WRITE, data, this, request != null ? request.getSenderDevice() : null, request != null ? request.getProperty() : null));
            if (writemonitoring.size() > 0 && data != null && data.length > 0) {
                final CommunicationAnswer comans = new CommunicationAnswer(request, CommunicationAnswer.CommunicationResult.WRITE_MONITOR, data, null, this,0,0);
                writemonitoring.parallelStream().forEach(dev -> dev.processRecivedMessage(comans));
            }
        }
    }

    //Метод получения сообщения их линии (для пользователя объекта) требует наличия UUID для проверки того, что UUID ранее открыл транзакцию и она еще не завершилась
    @Override
    public byte[] reciveMessage(UUID uuid,long timeOut,long bytesForReadCount, LineParameters params) throws TrunsactionError,LineTimeOutException,LineErrorException {
        if(!isTrunsactionActive(uuid)&& serverMode!=SERVICE_MODE.SERVER_ONLY){
            try {
                if (lock.tryLock(maxTransactionTime, TimeUnit.MILLISECONDS)) {
                    if (lineSelectorExecute(params)&&setLineParameters(params)) {
                        long start_time = System.currentTimeMillis();
                        final byte[] data = reciveMessage(timeOut, bytesForReadCount, params);
                        perfarmanceMonitoring(PerformanceMonitorEventData.EXECUTE_TYPE.WRITE_OPERATION, System.currentTimeMillis() - start_time);
                        readmonitor(data, null);
                        return data;
                    } else {
                        fireEvent(EventType.ERROR,localizeError("line_parameters_error"));
                        return null;
                    }
                } else {
                    endTransaction(uuid);
                    throw new TrunsactionError(localizeError("another_trunsaction"), TrunsactionError.TrunsactionErrorType.TRUNSACTION_ERROR);
                }
            }catch (InterruptedException e){
                endTransaction(uuid);
                throw new TrunsactionError(localizeError("another_trunsaction"), TrunsactionError.TrunsactionErrorType.TRUNSACTION_ERROR);
            } finally {
                lock.unlock();
            }
        } else {
            throw new TrunsactionError(localizeError("another_trunsaction"), TrunsactionError.TrunsactionErrorType.TRUNSACTION_ERROR);
        }
    }

    //функция выплолнения операций чтения записи в линию для запроса (работает для обслуживания устройств) Внутренний метод вызывемый из executeRequest
    private CommunicationAnswer processCommunicationOperationForRequest(final CommunicationProtocolRequest request){
        if(request!=null && serverMode!=SERVICE_MODE.SERVER_ONLY){
            if(!setLineParameters(request.getParameters())){
                CommunicationAnswer answ=new CommunicationAnswer(request, CommunicationAnswer.CommunicationResult.ERROR,null,localizeError("line_parameters_error"),this,0,0);
            }
            if(request.getPauseBeforeWrite()>0) {
                CommunicationUtils.RealThreadPause(request.getPauseBeforeWrite());
                perfarmanceMonitoring(PerformanceMonitorEventData.EXECUTE_TYPE.BEFORE_WRITE_PAUSE, request.getPauseBeforeWrite());
            }
            try {
                long starttime=System.currentTimeMillis();
                sendMessage(
                    request.getBytesForSend(),
                    request.getParameters());
                perfarmanceMonitoring(PerformanceMonitorEventData.EXECUTE_TYPE.WRITE_OPERATION, System.currentTimeMillis() - starttime);
                writemonitor(request.getBytesForSend(),request);
            if(request.getPauseBeforeRead()>0) {
                perfarmanceMonitoring(PerformanceMonitorEventData.EXECUTE_TYPE.BEFORE_READ_PAUSE,request.getPauseBeforeRead());
                CommunicationUtils.RealThreadPause(request.getPauseBeforeRead());
            }
                long startTime=System.currentTimeMillis();
                final byte[] buf=reciveMessage(request.getTimeOut(), request.getBytesForReadCount(), request.getParameters());
                long waitTime=System.currentTimeMillis()-startTime;
                perfarmanceMonitoring(PerformanceMonitorEventData.EXECUTE_TYPE.READ_OPERATION, waitTime);
                readmonitor(buf,request);
                CommunicationAnswer answ=new CommunicationAnswer(request, buf,this,startTime,waitTime);
                return answ;
            } catch (LineErrorException e) {
                CommunicationAnswer answ=new CommunicationAnswer(request, CommunicationAnswer.CommunicationResult.ERROR,null,e.getLocalizedMessage(),this,0,0);
                return answ;
            } catch (LineTimeOutException e) {
                CommunicationAnswer answ=new CommunicationAnswer(request, CommunicationAnswer.CommunicationResult.TIMEOUT,null,e.getLocalizedMessage(),this,e.getStartWaitTime(),e.getWaitTime());
                return answ;
            }

        }
        return new CommunicationAnswer(request, CommunicationAnswer.CommunicationResult.ERROR,null,localizeError("null_request"),this,0,0);
    }

    //выполнение действий предусмотренных запросом
    private void executeRequest(CommunicationProtocolRequest request){
        if (request != null) {
            try {
                //System.out.println(request);
                if(!lineSelectorExecute(request.getParameters())){
                    if(request.isCanTry()){
                        CommunicationProtocolRequest newRequest = CommunicationProtocolRequest.createReuest(request);
                        if (newRequest != null) requests.add(newRequest);
                    } else {
                        request.invalidate();
                    }
                } else {
                    CommunicationAnswer answer = processCommunicationOperationForRequest(request);
                    if (answer.getStatus() == CommunicationAnswer.CommunicationResult.TIMEOUT && request.isCanTry()) {
                        //request.destroy();
                        CommunicationProtocolRequest newRequest = CommunicationProtocolRequest.createReuest(request);
                        if (newRequest != null) requests.add(newRequest);
                    } else {
                        DeviceInterface dev = request.getSenderDevice();
                        if (dev != null && threadRunned.get()) {
                            service.submit(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        long starttime=System.currentTimeMillis();
                                        dev.processRecivedMessage(answer);
                                        perfarmanceMonitoring(PerformanceMonitorEventData.EXECUTE_TYPE.ANALISE_OPERATINO, System.currentTimeMillis() - starttime);
                                    } catch (Exception e) {
                                        fireEvent(EventType.ERROR, e);
                                    } finally {
                                        answer.destroy();
                                    }
                                }
                            });
                        } else {
                            answer.destroy();
                        }
                        CommunicationProtocolRequest subrequest=request.getSubrequest();
                        if(subrequest!=null) executeRequest(subrequest);
                    }
                }
            }catch (Exception all_ex){
                request.invalidate();
                request.destroy();
            }
        }
    }

    //МЕТОД СЕРВИСА ОБРАБОТКИ ЗАПРОСОВ ОТ УСТРОЙСТВ
    //отвечает за запуск потока обработки запросов и контроль за ним, а также за поддержку транзакций
    //метод в омент выполнения запроса блокирует любые другие действия,
    //также данный метод может быть заблокирован методом обработки серверных запросов
    @Override
    public void async_communicate(CommunicationProtocolRequest requesting) {
        //System.out.println("ADD REQUEST"+requesting);
        try {
            requests.offer(requesting, 500, TimeUnit.MICROSECONDS);
        } catch (InterruptedException e) {
            return;
        }
        if(threadCreated.compareAndSet(false,true)){
            Thread processor=new Thread(() -> {
                threadRunned.set(true);
                while (threadRunned.get()){
                    if(!threadPaused.get() && !threadTrunsactionPaused.get() && serverMode!=SERVICE_MODE.SERVER_ONLY ) {
                        try {
                            lock.lock();
                            CommunicationProtocolRequest request = requests.poll(1,TimeUnit.SECONDS);
                            if(request!=null) {
                                //System.out.println("PROCESS REQUEST" + request);
                                executeRequest(request);
                            }
                        } catch (Exception ex){
                            fireEvent(EventType.FATAL_ERROR,ex);
                        }finally {
                            lock.unlock();
                        }
                    } else {
                        if(threadTrunsactionPaused.get()){
                            long elapsed=System.currentTimeMillis();
                            if(elapsed>startTrunsactionTime+maxTransactionTime){
                                threadTrunsactionPaused.set(false);
                                transuction.set(false);
                                trunsactionUUID=null;
                            }
                        } else {
                            closeUsedResources();
                        }
                    }
                }
                threadCreated.set(false);
            });
            processor.start();
        }
    }

    //Метод добавляет к линии устройство мониторинга за данными принимаемыми линией
    @Override
    public void addReadMonitoringDevice(DeviceInterface device) {
        readmonitoring.add(device);
    }
    //Метод добавляет к линии устройство мониторинга за данными отправляемым линией
    @Override
    public void addWriteMonitoringDevice(DeviceInterface device) {
        writemonitoring.add(device);
    }

    //Метод удаляет из линии устройство мониторинга за данными принимаемыми линией
    @Override
    public void removeReadMonitoringDevice(DeviceInterface device) {
        readmonitoring.remove(device);
    }
    //Метод удаляет из линии устройство мониторинга за данными отправляемым линией
    @Override
    public void removeWriteMonitoringDevice(DeviceInterface device) {
        writemonitoring.remove(device);
    }

    //Метод проверяет не остановлена ли линия
    @Override
    public boolean isPaused() {
        return threadPaused.get();
    }

    //Метод позволяет остановить функционирование линии
    @Override
    public void setPaused(boolean pause) {
        threadPaused.set(pause);
    }

    @Override
    public String toString() {
        return String.format(I18N.getLocalizedString("line.description"), getName(), getUUIDString(), getDescription());
    }


    //Метод ДОЛЖЕН БЫТЬ ВЫЗВАН ДЛЯ КОРРЕКТНОГО ЗАВЕРШЕНИЯ РАБОТЫ ЛИНИИ
    @Override
    public void destroy(){
        closeUsedResources();
        threadRunned.set(false);
        service.shutdown();
        super.destroy();
    }

    //МЕТОД УКАЗЫВАЕТ формирует ли линиия сообщения о производительности операций чтения/записи
    public boolean isPerformanceMonitor() {
        return performanceMonitor.get();
    }
    //МЕТОД АКТИВИРАУЕТ/ДЕ АКТИВИРАУЕТ формирование линиией сообщений о производительности операций чтения/записи
    public void setPerformanceMonitor(boolean state) {
        performanceMonitor.set(state);
    }

    //Возврщает установленное для линии устройство отвечающие за коммутацию (переключение) линий
    public LineSelectDevice getLineSelector() {
        return lineSelector;
    }
    //Установливает для линии устройство отвечающие за коммутацию (переключение) линий
    public void setLineSelector(LineSelectDevice lineSelector) {
        this.lineSelector = lineSelector;
    }


    //МЕТОД ПЕРЕКЛЮЧЕНИЕ РЕЖИМОВ РАБОТЫ ЛИНИИ
    @Override
    public void setServiceMode(SERVICE_MODE mode, LineParameters serverLineParameter) {
        this.serverLineParameter = serverLineParameter;
        if(this.serverMode==SERVICE_MODE.SERVER_ONLY && mode!=SERVICE_MODE.SERVER_ONLY) {
            threadPaused.set(true);
        } else if(this.serverMode!=SERVICE_MODE.SERVER_ONLY && mode==SERVICE_MODE.SERVER_ONLY) {
            threadPaused.set(false);
        }
        if(mode!=SERVICE_MODE.CLIENT_ONLY){
            setLineParameters(serverLineParameter);
            activateServerMode(serverLineParameter,mode);
        } else {
            deactivateServerMode();
        }
        this.serverMode=mode;
    }

    //МЕТОД АКТИВАЦИИ КЛИЕНТСКОГО РЕЖИМА
    @Override
    public void setClientMode() {
        setServiceMode(SERVICE_MODE.CLIENT_ONLY, null);
    }

    //метод возвращает текущий режим линии
    @Override
    public SERVICE_MODE getServiceMode() {
        return serverMode;
    }

    //метод осуществялет проверку что линия находится в серверном режиме
    public boolean isServerMode() {
        return serverMode!=SERVICE_MODE.CLIENT_ONLY;
    }

    //добавляет устройство которое будет принимать запросы от линии в сервеном режиме
    public void addDeviceToService(ImitatorDevice device){
        if(device!=null)deviceForService.add(device);
    }
    //удаляет устройство которое будет принимать запросы от линии в сервеном режиме
    public void removeDeviceToService(ImitatorDevice device){
        if(device!=null)deviceForService.remove(device);
    }
    //возвращяет массив устройств которые будут принимать запросы от линии в сервеном режиме
    public ImitatorDevice[] getDeivicesForService(){
        return deviceForService.toArray(new ImitatorDevice[deviceForService.size()]);
    }

    public LineParameters getServerLineParameter() {
        return serverLineParameter;
    }
}

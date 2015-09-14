package ua.pp.fairwind.communications.lines.abstracts;

import ua.pp.fairwind.communications.abstractions.LineSelector;
import ua.pp.fairwind.communications.abstractions.SystemEllement;
import ua.pp.fairwind.communications.devices.abstracts.DeviceInterface;
import ua.pp.fairwind.communications.devices.abstracts.ImitatorDevice;
import ua.pp.fairwind.communications.devices.abstracts.LineSelectDevice;
import ua.pp.fairwind.communications.devices.logging.LineMonitoringEvent;
import ua.pp.fairwind.communications.elementsdirecotry.SystemElementDirectory;
import ua.pp.fairwind.communications.internatianalisation.I18N;
import ua.pp.fairwind.communications.lines.exceptions.LineErrorException;
import ua.pp.fairwind.communications.lines.exceptions.LineTimeOutException;
import ua.pp.fairwind.communications.lines.exceptions.TrunsactionError;
import ua.pp.fairwind.communications.lines.lineparams.LineParameters;
import ua.pp.fairwind.communications.lines.operations.CommunicationAnswer;
import ua.pp.fairwind.communications.lines.operations.CommunicationProtocolRequest;
import ua.pp.fairwind.communications.lines.performance.PerformanceMonitorEventData;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.event.EventType;
import ua.pp.fairwind.communications.utils.CommunicationUtils;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Сергей on 10.07.2015.
 */
abstract  public class AbstractLine extends SystemEllement implements LineInterface  {
    private final ConcurrentLinkedQueue<CommunicationProtocolRequest> requests=new ConcurrentLinkedQueue<>();
    private final AtomicBoolean threadRunned=new AtomicBoolean(false);
    private final AtomicBoolean threadCreated=new AtomicBoolean(false);
    private final AtomicBoolean transuction=new AtomicBoolean(false);
    private final AtomicBoolean threadPaused=new AtomicBoolean(false);
    private final AtomicBoolean performanceMonitor=new AtomicBoolean(false);
    private final AtomicBoolean threadTrunsactionPaused=new AtomicBoolean(false);
    protected final AtomicBoolean serverMode=new AtomicBoolean(false);
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

    protected AbstractLine(String name, String uuid, String description, MessageSubSystem centralSystem,long maxTransactionTime) {
        super(name, uuid, description, centralSystem);
        this.maxTransactionTime = maxTransactionTime;
    }

    protected AbstractLine(String name, String uuid, String description, SystemElementDirectory centralSystem,long maxTransactionTime) {
        super(name, uuid, description, centralSystem);
        this.maxTransactionTime = maxTransactionTime;
    }

    protected AbstractLine(String name, String uuid, String description, MessageSubSystem centralSystem,HashMap<String,String> uuids,long maxTransactionTime) {
        super(name, uuid, description, centralSystem);
        this.maxTransactionTime = maxTransactionTime;
    }

    protected AbstractLine(String name, String uuid, String description, SystemElementDirectory centralSystem,HashMap<String,String> uuids,long maxTransactionTime) {
        super(name, uuid, description, centralSystem);
        this.maxTransactionTime = maxTransactionTime;
    }

    public static String localizeName(String key){
        return localizeName("line", key);
    }
    public static String localizeDescription(String key){
        return localizeDescription("line", key);
    }
    public static String localizeError(String key){
        return localizeError("line", key);
    }

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

    protected void serviceRecivedFromLineDataInServerMode(final byte[] data){
        if(serverMode.get() && data!=null && data.length>0){
            try {
                if (lock.tryLock(maxTransactionTime * 3, TimeUnit.MILLISECONDS)) {
                        deviceForService.forEach(device -> {
                            byte[] copydata = Arrays.copyOf(data, data.length);
                            byte[] answer = device.process_line_data(copydata);
                            if (answer != null && answer.length > 0) {
                                long starttime=System.currentTimeMillis();
                                try {
                                    sendMessage(answer, serverLineParameter);
                                    perfarmanceMonitoring(PerformanceMonitorEventData.EXECUTE_TYPE.WRITE_OPERATION, System.currentTimeMillis() - starttime);
                                    writemonitor(answer, null);
                                } catch (LineErrorException e) {
                                    fireEvent(EventType.FATAL_ERROR, e);
                                } catch (LineTimeOutException e) {
                                    fireEvent(EventType.FATAL_ERROR, e);
                                }
                            }
                        });
                }
            }catch (InterruptedException ex){
                //do nothing
            } catch (Exception ex){
                fireEvent(EventType.FATAL_ERROR,ex);
            }finally {
                lock.unlock();
            }
        }
    }

    private void perfarmanceMonitoring(PerformanceMonitorEventData.EXECUTE_TYPE typeExecute,long time){
        if(performanceMonitor.get()) {
            PerformanceMonitorEventData data=new PerformanceMonitorEventData(typeExecute,time);
            fireEvent(EventType.PERFORMANCE,data);
        }
    }

    synchronized private boolean isTrunsactionActive(UUID uuid){
        if(uuid==null) return false;
        if(transuction.get()){
            if(startTrunsactionTime+ maxTransactionTime >System.currentTimeMillis()){
                transuction.set(false);
                return false;
            } else {
                if(uuid.equals(trunsactionUUID)) return false;
                return true;
            }
        } else {
            return false;
        }
    }




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

    public boolean lineSelectorExecute(Object selectLine){
        if(lineSelector!=null&&selectLine!=null) {
            return lineSelectorExecute(lineSelector.selectLine(selectLine));
        }
        return true;
    }

    public boolean lineSelectorExecute(LineSelector selectData){
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

    private boolean lineSelectorExecute(LineParameters params){
        if(lineSelector!=null) {
            Object always_set_line=params.getLineParameter(LineParameters.ALWAYS_SET_LINE);
            boolean need_set_line=false;
            if(always_set_line!=null && always_set_line instanceof Boolean){
                need_set_line=(Boolean)need_set_line;
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

    abstract protected void sendMessage(byte[] data, LineParameters params) throws LineErrorException,LineTimeOutException;
    abstract protected byte[] reciveMessage(long timeOut,long bytesForReadCount, LineParameters params) throws LineErrorException,LineTimeOutException;
    abstract protected boolean setepLineParameters(LineParameters params,LineParameters curentLineParameters,boolean alwaysSet);
    abstract protected void onStartTrunsaction();
    abstract protected void onEndTrunsaction();
    abstract protected void closeUsedResources();
    abstract protected boolean testIdentialyLineParameters(LineParameters current,LineParameters newparmeters);

    protected boolean setLineParameters(LineParameters params){
        if(params==null){
            return setepLineParameters(params,curentLineParameters,true);
        }
        Object always_set_line_param=params.getLineParameter(LineParameters.ALWAYS_SET_LINE_PARAM);
        boolean need_set_line_param=false;
        if(always_set_line_param!=null && always_set_line_param instanceof Boolean){
            need_set_line_param=(Boolean)always_set_line_param;
        }
        if(need_set_line_param){
            return setepLineParameters(params,curentLineParameters,true);
        } else {
            if(!testIdentialyLineParameters(curentLineParameters,params)){
                if(setepLineParameters(params,curentLineParameters,false)){
                    curentLineParameters=params;
                    return true;
                } else {
                    fireEvent(EventType.ERROR,localizeError("line_parameters_error"));
                    return false;
                }

            }else return true;
        }
    }


    @Override
    synchronized public void endTransaction(UUID uuid) {
        if(uuid!=null && isTrunsactionActive() && uuid.equals(trunsactionUUID)){
            transuction.set(false);
            trunsactionUUID=null;
            threadTrunsactionPaused.set(false);
            onEndTrunsaction();
        }
    }

    @Override
    public void sendMessage(UUID uuid,final byte[] data, LineParameters params) throws TrunsactionError,LineErrorException,LineTimeOutException {
        if(!isTrunsactionActive(uuid)){
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

    private void readmonitor(final byte[] data,final CommunicationProtocolRequest request){
        if(data!=null && data.length>0) {
            fireEvent(EventType.READ_MONITOR,new LineMonitoringEvent(LineMonitoringEvent.ACTION_TYPE.READ,data,this,request!=null?request.getSenderDevice():null,request!=null?request.getProperty():null));
            if (readmonitoring.size() > 0) {
                CommunicationAnswer comans = new CommunicationAnswer(request, CommunicationAnswer.CommunicationResult.READ_MONITOR, data, null, this,0,0);
                readmonitoring.parallelStream().forEach(dev -> dev.processRecivedMessage(comans));
            }
        }
    }

    private void writemonitor(final byte[] data,final CommunicationProtocolRequest request){
        if(data!=null && data.length>0) {
            fireEvent(EventType.WRITE_MONITOR,new LineMonitoringEvent(LineMonitoringEvent.ACTION_TYPE.WRITE,data,this,request!=null?request.getSenderDevice():null,request!=null?request.getProperty():null));
            if (writemonitoring.size() > 0 && data != null && data.length > 0) {
                final CommunicationAnswer comans = new CommunicationAnswer(request, CommunicationAnswer.CommunicationResult.WRITE_MONITOR, data, null, this,0,0);
                writemonitoring.parallelStream().forEach(dev -> dev.processRecivedMessage(comans));
            }
        }
    }

    @Override
    public byte[] reciveMessage(UUID uuid,long timeOut,long bytesForReadCount, LineParameters params) throws TrunsactionError,LineTimeOutException,LineErrorException {
        if(!isTrunsactionActive(uuid)){
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
    //функция выплолнения операций чтения записи в линию для запроса
    private CommunicationAnswer processCommunicationOperationForRequest(final CommunicationProtocolRequest request){
        if(request!=null && !serverMode.get()){
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

    @Override
    public void async_communicate(CommunicationProtocolRequest request) {
        requests.add(request);
        if(!threadCreated.get()){
            Thread processor=new Thread(new Runnable() {
                @Override
                public void run() {
                    threadCreated.set(true);
                    threadRunned.set(true);
                    while (threadRunned.get()){
                        if(!threadPaused.get() && !threadTrunsactionPaused.get() &&!serverMode.get() ) {
                            try {
                                lock.lock();
                                CommunicationProtocolRequest request = requests.poll();
                                executeRequest(request);
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
                }
            });
            processor.start();
        }
    }

    @Override
    public void addReadMonitoringDevice(DeviceInterface device) {
        readmonitoring.add(device);
    }

    @Override
    public void addWriteMonitoringDevice(DeviceInterface device) {
        writemonitoring.add(device);
    }

    @Override
    public void removeReadMonitoringDevice(DeviceInterface device) {
        readmonitoring.remove(device);
    }

    @Override
    public void removeWriteMonitoringDevice(DeviceInterface device) {
        writemonitoring.remove(device);
    }

    @Override
    public boolean isPaused() {
        return threadPaused.get();
    }

    @Override
    public void setPaused(boolean pause) {
        threadPaused.set(pause);
    }

    @Override
    public String toString() {
        return String.format(I18N.getLocalizedString("line.description"),getName(),getUUIDString(),getDescription());
    }

    @Override
    public void destroy(){
        closeUsedResources();
        threadRunned.set(false);
        service.shutdown();
        super.destroy();
    }

    public boolean isPerformanceMonitor() {
        return performanceMonitor.get();
    }

    public void setPerformanceMonitor(boolean state) {
        performanceMonitor.set(state);
    }

    public LineSelectDevice getLineSelector() {
        return lineSelector;
    }

    public void setLineSelector(LineSelectDevice lineSelector) {
        this.lineSelector = lineSelector;
    }

    public void setServerLineParameter(boolean setServerMode,LineParameters serverLineParameter) {
        if(setServerMode) {
            this.serverLineParameter = serverLineParameter;
            serverMode.set(false);
        } else {
            serverMode.set(false);
        }
    }

    public void stopServerMode() {
        setServerLineParameter(false, null);
    }

    public boolean isServerMode() {
        return serverMode.get();
    }

    public void addDeviceToService(ImitatorDevice device){
        if(device!=null)deviceForService.add(device);
    }

    public void removeDeviceToService(ImitatorDevice device){
        if(device!=null)deviceForService.remove(device);
    }

    public ImitatorDevice[] getDeivicesForService(){
        return deviceForService.toArray(new ImitatorDevice[deviceForService.size()]);
    }
}

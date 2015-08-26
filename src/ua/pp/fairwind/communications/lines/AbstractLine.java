package ua.pp.fairwind.communications.lines;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.abstractions.SystemEllement;
import ua.pp.fairwind.communications.devices.DeviceInterface;
import ua.pp.fairwind.communications.devices.logging.LineMonitoringEvent;
import ua.pp.fairwind.communications.propertyes.event.EventType;
import ua.pp.fairwind.communications.utils.CommunicationUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Сергей on 10.07.2015.
 */
abstract  public class AbstractLine extends SystemEllement implements LineInterface  {
    public static final String PROPERTY_DEVICE_ADDRESS="propertyAddress";
    private final ConcurrentLinkedQueue<CommunicationProtocolRequest> requests=new ConcurrentLinkedQueue<>();
    private final AtomicBoolean threadRunned=new AtomicBoolean(false);
    private final AtomicBoolean threadCreated=new AtomicBoolean(false);
    private final AtomicBoolean transuction=new AtomicBoolean(false);
    private final AtomicBoolean threadPaused=new AtomicBoolean(false);
    private final AtomicBoolean threadTrunsactionPaused=new AtomicBoolean(false);
    private volatile long startTrunsactionTime=-1;
    private volatile UUID trunsactionUUID;
    private final long maxTransactionTime;
    private final Set<DeviceInterface> readmonitoring=new HashSet<>();
    private final Set<DeviceInterface> writemonitoring=new HashSet<>();
    private final ReentrantLock lock = new ReentrantLock();

    public AbstractLine(String name, String uuid, String description, MessageSubSystem centralSystem,long maxTransactionTime) {
        super(name, uuid, description, centralSystem);
        this.maxTransactionTime = maxTransactionTime;
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
        if(uuid==null) throw new TrunsactionError("NEED UUID!!", TrunsactionError.TrunsactionErrorType.TRUNSACTION_ERROR);
        if(!uuid.equals(trunsactionUUID) && transuction.get() && startTrunsactionTime+ maxTransactionTime <System.currentTimeMillis()){
            throw new TrunsactionError("Another transaction started!", TrunsactionError.TrunsactionErrorType.ANOTHER_TRUNSACTION_EXECUTE);
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
                throw new TrunsactionError("Can not acquire monitor!", TrunsactionError.TrunsactionErrorType.TRUNSACTION_ERROR);
            }
        }
    }

    abstract protected void sendMessage(byte[] data, LineParameters params) throws LineErrorException,LineTimeOutException;
    abstract protected byte[] reciveMessage(long timeOut,long bytesForReadCount, LineParameters params) throws LineErrorException,LineTimeOutException;
    abstract protected void onStartTrunsaction();
    abstract protected void onEndTrunsaction();

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
            sendMessage(data, params);
            writemonitor(data,null);
        } else {
            throw new TrunsactionError("Another transaction started!", TrunsactionError.TrunsactionErrorType.TRUNSACTION_ERROR);
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
            final byte[] data=reciveMessage(timeOut,bytesForReadCount,params);
            readmonitor(data,null);
            return data;
        } else {
            throw new TrunsactionError("Another transaction started!", TrunsactionError.TrunsactionErrorType.TRUNSACTION_ERROR);
        }
    }

    private CommunicationAnswer processRequest(final CommunicationProtocolRequest request){
        if(request!=null){
            if(request.getPauseBeforeWrite()>0) CommunicationUtils.RealThreadPause(request.getPauseBeforeWrite());
            try {
            sendMessage(
                    request.getBytesForSend(),
                    request.getParameters());
            writemonitor(request.getBytesForSend(),request);
            if(request.getPauseBeforeRead()>0) CommunicationUtils.RealThreadPause(request.getPauseBeforeRead());
                long startTime=System.currentTimeMillis();
                final byte[] buf=reciveMessage(request.getTimeOut(), request.getBytesForReadCount(), request.getParameters());
                long waitTime=System.currentTimeMillis()-startTime;
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
        return new CommunicationAnswer(request, CommunicationAnswer.CommunicationResult.ERROR,null,"NULL REQUEST",this,0,0);
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
                        if(!threadPaused.get() && !threadTrunsactionPaused.get() ) {
                            try {
                                lock.lock();
                                CommunicationProtocolRequest request = requests.poll();
                                if (request != null) {
                                    System.out.println(request);
                                    CommunicationAnswer answer = processRequest(request);
                                    if (request.getSenderDevice() != null) {
                                        DeviceInterface dev = request.getSenderDevice();
                                        dev.processRecivedMessage(answer);
                                    }
                                    if (answer.getStatus() == CommunicationAnswer.CommunicationResult.TIMEOUT && request.getTryCount() < 1) {
                                        requests.add(new CommunicationProtocolRequest(request));
                                    }
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
        return "LINE{Name:"+getName()+" ,U:"+getUUIDString()+",D:"+getDescription()+"}";
    }
}

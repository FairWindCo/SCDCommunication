package ua.pp.fairwind.communications.lines;

import ua.pp.fairwind.communications.devices.DeviceInterface;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.propertyTrunsactions.OPERATION_TYPE;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Created by Сергей on 09.07.2015.
 */
public class CommunicationProtocolRequest {
    public static enum REQUEST_TYPE{
        READ_PROPERTY(OPERATION_TYPE.READ_PROPERTY),
        WRITE_PROPERTY(OPERATION_TYPE.WRITE_PROPERTY),
        COMMAND_EXECUTE(OPERATION_TYPE.COMMAND_EXECUTE),
        CUSTOM__BUFFER(OPERATION_TYPE.NONE);

        private final OPERATION_TYPE propertyOperationType;

        REQUEST_TYPE(OPERATION_TYPE propertyOperationType) {
            this.propertyOperationType = propertyOperationType;
        }

        public OPERATION_TYPE getPropertyOperationType() {
            return propertyOperationType;
        }
    }
    private final static AtomicLong counter=new AtomicLong(0);
    final private byte[] bytesForSend;
    private final long createTime=System.currentTimeMillis();
    private final long timeOut;
    private final long pauseBeforeRead;
    private final long pauseBeforeWrite;
    private final long bytesForReadCount;
    final private DeviceInterface senderDevice;
    private final LineParameters parameters;
    private final AbstractProperty property;
    private final LineInterface nextLine;
    private final boolean needRollBack;
    private final long tryCount;
    private final long maxTryCount;
    private final long requestNum=counter.getAndIncrement();
    private final AtomicBoolean requestValidated=new AtomicBoolean(false);
    private final REQUEST_TYPE requestType;


    public static CommunicationProtocolRequest createReuest(REQUEST_TYPE requestType,byte[] bytesForSend, long bytesForReadCount, DeviceInterface senderDevice, long timeOut, long pauseBeforeRead, long pauseBeforeWrite,LineParameters parameters, AbstractProperty property, LineInterface nextLine,boolean needRollBack,long maxTryCount){
        if(senderDevice==null) return null;
        if(property!=null &&!property.startRequest(requestType.getPropertyOperationType())){
            return null;
        }
        return new CommunicationProtocolRequest(requestType,bytesForSend,bytesForReadCount,senderDevice,timeOut,pauseBeforeRead,pauseBeforeWrite,parameters,property,nextLine,needRollBack,maxTryCount);
    }


    private CommunicationProtocolRequest(REQUEST_TYPE requestType,byte[] bytesForSend, long bytesForReadCount, DeviceInterface senderDevice, long timeOut, long pauseBeforeRead, long pauseBeforeWrite,LineParameters parameters, AbstractProperty property, LineInterface nextLine,boolean needRollBack,long maxTryCount){
        this.bytesForSend = bytesForSend;
        this.senderDevice = senderDevice;
        this.timeOut = timeOut;
        this.parameters = parameters;
        this.property = property;
        this.pauseBeforeRead=pauseBeforeRead;
        this.nextLine=nextLine;
        this.bytesForReadCount=bytesForReadCount;
        this.pauseBeforeWrite=pauseBeforeWrite;
        this.needRollBack=needRollBack;
        this.requestType=requestType;
        this.maxTryCount=maxTryCount;
        tryCount=0;
    }

    public static CommunicationProtocolRequest createReuest(byte[] bytesForSend, long bytesForReadCount, DeviceInterface senderDevice, long timeOut, long pauseBeforeRead, long pauseBeforeWrite, LineParameters parameters, LineInterface nextLine,boolean needRollBack,long maxTryCount) {
        if(senderDevice==null) return null;
        return new CommunicationProtocolRequest(REQUEST_TYPE.CUSTOM__BUFFER,bytesForSend,bytesForReadCount,senderDevice,timeOut,pauseBeforeRead,pauseBeforeWrite,parameters,null,nextLine,needRollBack,maxTryCount);
    }

    public static CommunicationProtocolRequest createReuest(long bytesForReadCount, DeviceInterface senderDevice, long timeOut, long pauseBeforeRead, long pauseBeforeWrite, LineParameters parameters, LineInterface nextLine,boolean needRollBack,long maxTryCount) {
        if(senderDevice==null) return null;
        return new CommunicationProtocolRequest(REQUEST_TYPE.CUSTOM__BUFFER,null,bytesForReadCount,senderDevice,timeOut,pauseBeforeRead,pauseBeforeWrite,parameters,null,nextLine,needRollBack,maxTryCount);
    }
    //Сформировать запрос для переотправки в случае если произошел TimeOut
    public static CommunicationProtocolRequest createReuest(CommunicationProtocolRequest notSuccessRequest){
        if(notSuccessRequest==null)return null;
        /*if(notSuccessRequest.property!=null &&!notSuccessRequest.property.startRequest()){
            return null;
        }/**/
        return new CommunicationProtocolRequest(notSuccessRequest);
    }

    private CommunicationProtocolRequest(CommunicationProtocolRequest notSuccessRequest){
        this.bytesForSend = notSuccessRequest.bytesForSend;
        this.senderDevice = notSuccessRequest.senderDevice;
        this.timeOut = notSuccessRequest.timeOut;
        this.parameters = notSuccessRequest.parameters;
        this.property = notSuccessRequest.property;
        this.pauseBeforeRead=notSuccessRequest.pauseBeforeRead;
        this.nextLine=notSuccessRequest.nextLine;
        this.bytesForReadCount=notSuccessRequest.bytesForReadCount;
        this.tryCount=notSuccessRequest.tryCount+1;
        this.pauseBeforeWrite=notSuccessRequest.pauseBeforeWrite;
        this.needRollBack=notSuccessRequest.needRollBack;
        this.requestType=notSuccessRequest.requestType;
        this.maxTryCount=notSuccessRequest.maxTryCount;
    }

    public byte[] getBytesForSend() {
        return bytesForSend;
    }

    public DeviceInterface getSenderDevice() {
        return senderDevice;
    }

    public AbstractProperty getProperty() {
        return property;
    }

    public long getCreateTime() {
        return createTime;
    }

    public LineParameters getParameters() {
        return parameters;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public long getPauseBeforeRead() {
        return pauseBeforeRead;
    }

    public boolean isExistReservLine(){
        return nextLine!=null;
    }

    public long getBytesForReadCount() {
        return bytesForReadCount;
    }

    public CommunicationProtocolRequest formRequestForNextLine(){
        return createReuest(requestType,bytesForSend, bytesForReadCount, senderDevice, timeOut, pauseBeforeRead, pauseBeforeWrite, parameters, property, null, needRollBack,maxTryCount);
    }

    public void sendRequestOverReservLine(){
        if(nextLine!=null){
            if(property!=null && property instanceof ValueProperty){
                if(property.startRequest(requestType.propertyOperationType)){
                    CommunicationProtocolRequest newRequest=formRequestForNextLine();
                    nextLine.async_communicate(newRequest);
                }
            }
        } else {
            //if(property!=null && property instanceof ValueProperty) ((ValueProperty)property).invalidate();
        }

    }

    public long getTryCount() {
        return tryCount;
    }

    public boolean isCanTry(){
        return tryCount<maxTryCount;
    }

    public long getPauseBeforeWrite() {
        return pauseBeforeWrite;
    }

    public long getRequestNum() {
        return requestNum;
    }

    public static long getCounter() {
        return counter.get();
    }

    @Override
    public String toString() {
        return "Request{" +
                requestType +
                ", requestNum=" + requestNum +
                ", bytesForSend=" + Arrays.toString(bytesForSend) +
                '}';
    }

    public void invalidate(){
        if(property!=null && property instanceof ValueProperty<?>){
            if(requestValidated.compareAndSet(false,true)) property.endRequest(requestType.propertyOperationType);
            if(needRollBack)((ValueProperty<?>)property).rollback();
            else ((ValueProperty<?>)property).invalidate();
        }
    }

    public void destroy(){
        if(property!=null && property instanceof ValueProperty<?>){
            if(requestValidated.compareAndSet(false,true)) property.endRequest(requestType.propertyOperationType);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        destroy();
        super.finalize();
    }
}

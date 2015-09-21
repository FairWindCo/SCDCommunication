package ua.pp.fairwind.communications.lines.operations;

import ua.pp.fairwind.communications.devices.abstracts.DeviceInterface;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.communications.lines.lineparams.LineParameters;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractTrunsactionExecutor;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.propertyTrunsactions.OPERATION_TYPE;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Created by Сергей on 09.07.2015.
 */
public class CommunicationProtocolRequest extends AbstractTrunsactionExecutor{
    public enum REQUEST_TYPE{
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
    private final CommunicationProtocolRequest subrequest;


    public static CommunicationProtocolRequest createReuest(REQUEST_TYPE requestType,byte[] bytesForSend, long bytesForReadCount, DeviceInterface senderDevice, long timeOut, long pauseBeforeRead, long pauseBeforeWrite,LineParameters parameters, AbstractProperty property, LineInterface nextLine,boolean needRollBack,long maxTryCount,CommunicationProtocolRequest subrequest){
        if(senderDevice==null) return null;
        if(property!=null &&!startRequest(property,requestType.getPropertyOperationType())){
            return null;
        }
        return new CommunicationProtocolRequest(requestType,bytesForSend,bytesForReadCount,senderDevice,timeOut,pauseBeforeRead,pauseBeforeWrite,parameters,property,nextLine,needRollBack,maxTryCount,subrequest);
    }

    public static CommunicationProtocolRequest createReuestNextLine(REQUEST_TYPE requestType,byte[] bytesForSend, long bytesForReadCount, DeviceInterface senderDevice, long timeOut, long pauseBeforeRead, long pauseBeforeWrite,LineParameters parameters, AbstractProperty property, LineInterface nextLine,boolean needRollBack,long maxTryCount,CommunicationProtocolRequest subrequest){
        if(senderDevice==null) return null;
        return new CommunicationProtocolRequest(requestType,bytesForSend,bytesForReadCount,senderDevice,timeOut,pauseBeforeRead,pauseBeforeWrite,parameters,property,nextLine,needRollBack,maxTryCount,subrequest);
    }

    public static CommunicationProtocolRequest createReuest(byte[] bytesForSend, long bytesForReadCount, DeviceInterface senderDevice, long timeOut, long pauseBeforeRead, long pauseBeforeWrite, LineParameters parameters, LineInterface nextLine,boolean needRollBack,long maxTryCount,CommunicationProtocolRequest subrequest) {
        if(senderDevice==null) return null;
        return new CommunicationProtocolRequest(REQUEST_TYPE.CUSTOM__BUFFER,bytesForSend,bytesForReadCount,senderDevice,timeOut,pauseBeforeRead,pauseBeforeWrite,parameters,null,nextLine,needRollBack,maxTryCount,subrequest);
    }

    public static CommunicationProtocolRequest createReuest(long bytesForReadCount, DeviceInterface senderDevice, long timeOut, long pauseBeforeRead, long pauseBeforeWrite, LineParameters parameters, LineInterface nextLine,boolean needRollBack,long maxTryCount,CommunicationProtocolRequest subrequest) {
        if(senderDevice==null) return null;
        return new CommunicationProtocolRequest(REQUEST_TYPE.CUSTOM__BUFFER,null,bytesForReadCount,senderDevice,timeOut,pauseBeforeRead,pauseBeforeWrite,parameters,null,nextLine,needRollBack,maxTryCount,subrequest);
    }

    public static CommunicationProtocolRequest createReuest(REQUEST_TYPE requestType,byte[] bytesForSend, long bytesForReadCount, DeviceInterface senderDevice, long timeOut, long pauseBeforeRead, long pauseBeforeWrite,LineParameters parameters, AbstractProperty property, LineInterface nextLine,boolean needRollBack,long maxTryCount){
        if(senderDevice==null) return null;
        if(property!=null &&!startRequest(property,requestType.getPropertyOperationType())){
            return null;
        }
        return new CommunicationProtocolRequest(requestType,bytesForSend,bytesForReadCount,senderDevice,timeOut,pauseBeforeRead,pauseBeforeWrite,parameters,property,nextLine,needRollBack,maxTryCount,null);
    }

    public static CommunicationProtocolRequest createReuestNextLine(REQUEST_TYPE requestType,byte[] bytesForSend, long bytesForReadCount, DeviceInterface senderDevice, long timeOut, long pauseBeforeRead, long pauseBeforeWrite,LineParameters parameters, AbstractProperty property, LineInterface nextLine,boolean needRollBack,long maxTryCount){
        if(senderDevice==null) return null;
        return new CommunicationProtocolRequest(requestType,bytesForSend,bytesForReadCount,senderDevice,timeOut,pauseBeforeRead,pauseBeforeWrite,parameters,property,nextLine,needRollBack,maxTryCount,null);
    }

    public static CommunicationProtocolRequest createReuest(byte[] bytesForSend, long bytesForReadCount, DeviceInterface senderDevice, long timeOut, long pauseBeforeRead, long pauseBeforeWrite, LineParameters parameters, LineInterface nextLine,boolean needRollBack,long maxTryCount) {
        if(senderDevice==null) return null;
        return new CommunicationProtocolRequest(REQUEST_TYPE.CUSTOM__BUFFER,bytesForSend,bytesForReadCount,senderDevice,timeOut,pauseBeforeRead,pauseBeforeWrite,parameters,null,nextLine,needRollBack,maxTryCount,null);
    }

    public static CommunicationProtocolRequest createReuest(long bytesForReadCount, DeviceInterface senderDevice, long timeOut, long pauseBeforeRead, long pauseBeforeWrite, LineParameters parameters, LineInterface nextLine,boolean needRollBack,long maxTryCount) {
        if(senderDevice==null) return null;
        return new CommunicationProtocolRequest(REQUEST_TYPE.CUSTOM__BUFFER,null,bytesForReadCount,senderDevice,timeOut,pauseBeforeRead,pauseBeforeWrite,parameters,null,nextLine,needRollBack,maxTryCount,null);
    }
    //Сформировать запрос для переотправки в случае если произошел TimeOut
    public static CommunicationProtocolRequest createReuest(CommunicationProtocolRequest notSuccessRequest){
        if(notSuccessRequest==null)return null;
        /*if(notSuccessRequest.property!=null &&!notSuccessRequest.property.startRequest()){
            return null;
        }/**/
        return new CommunicationProtocolRequest(notSuccessRequest);
    }

    private CommunicationProtocolRequest(REQUEST_TYPE requestType,byte[] bytesForSend, long bytesForReadCount, DeviceInterface senderDevice, long timeOut, long pauseBeforeRead, long pauseBeforeWrite,LineParameters parameters, AbstractProperty property, LineInterface nextLine,boolean needRollBack,long maxTryCount,CommunicationProtocolRequest surequest){
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
        this.subrequest=surequest;
        tryCount=0;
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
        this.subrequest=notSuccessRequest.subrequest;
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
        return createReuestNextLine(requestType,bytesForSend, bytesForReadCount, senderDevice, timeOut, pauseBeforeRead, pauseBeforeWrite, parameters, property, null, needRollBack,maxTryCount);
    }

    public boolean sendRequestOverReservLine(){
        if(nextLine!=null){
            if(property!=null && property instanceof ValueProperty){
                if(startRequest(property,requestType.propertyOperationType)){
                    CommunicationProtocolRequest newRequest=formRequestForNextLine();
                    if(newRequest!=null) {
                        nextLine.async_communicate(newRequest);
                        return true;
                    }
                }
            }
        }
        return false;
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
                ", property="+property+
                '}';
    }

    public void invalidate(){
        if(property!=null && property instanceof ValueProperty){
            if(requestValidated.compareAndSet(false,true)) endRequest(property, requestType.propertyOperationType);
            if(needRollBack)rollback((ValueProperty) property);
            else invalidate((ValueProperty)property);
        }
        if(subrequest!=null)subrequest.invalidate();
    }

    public void destroy(){
        if(property!=null && property instanceof ValueProperty<?>){
            if(requestValidated.compareAndSet(false,true)) endRequest(property,requestType.propertyOperationType);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        destroy();
        super.finalize();
    }

    public CommunicationProtocolRequest getSubrequest() {
        return subrequest;
    }
}

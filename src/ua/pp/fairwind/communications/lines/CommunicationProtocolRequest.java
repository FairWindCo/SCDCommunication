package ua.pp.fairwind.communications.lines;

import ua.pp.fairwind.communications.devices.DeviceInterface;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Created by Сергей on 09.07.2015.
 */
public class CommunicationProtocolRequest {
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
    private final long requestNum=counter.getAndIncrement();

    public CommunicationProtocolRequest(byte[] bytesForSend, long bytesForReadCount, DeviceInterface senderDevice, long timeOut, long pauseBeforeRead, long pauseBeforeWrite,LineParameters parameters, AbstractProperty property, LineInterface nextLine,boolean needRollBack) {
        if(senderDevice==null) throw new IllegalArgumentException("senderDevice cannot be NULL!!!!");
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
        tryCount=0;
    }

    public CommunicationProtocolRequest(byte[] bytesForSend, long bytesForReadCount, DeviceInterface senderDevice, long timeOut, long pauseBeforeRead, long pauseBeforeWrite, LineParameters parameters, LineInterface nextLine,boolean needRollBack) {
        if(senderDevice==null) throw new IllegalArgumentException("senderDevice cannot be NULL!!!!");
        this.bytesForSend = bytesForSend;
        this.senderDevice = senderDevice;
        this.timeOut = timeOut;
        this.parameters = parameters;
        property=null;
        this.pauseBeforeRead=pauseBeforeRead;
        this.nextLine=nextLine;
        this.bytesForReadCount=bytesForReadCount;
        this.pauseBeforeWrite=pauseBeforeWrite;
        this.needRollBack=needRollBack;
        tryCount=0;
    }

    public CommunicationProtocolRequest(long bytesForReadCount, DeviceInterface senderDevice, long timeOut, long pauseBeforeRead, long pauseBeforeWrite, LineParameters parameters, LineInterface nextLine,boolean needRollBack) {
        if(senderDevice==null) throw new IllegalArgumentException("senderDevice cannot be NULL!!!!");
        this.senderDevice = senderDevice;
        this.timeOut = timeOut;
        this.parameters = parameters;
        property=null;
        bytesForSend=null;
        this.pauseBeforeRead=pauseBeforeRead;
        this.pauseBeforeWrite=pauseBeforeWrite;
        this.nextLine=nextLine;
        this.bytesForReadCount=bytesForReadCount;
        this.needRollBack=needRollBack;
        tryCount=0;
    }

    public CommunicationProtocolRequest(CommunicationProtocolRequest notSuccessRequest){
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
        return new CommunicationProtocolRequest(bytesForSend,bytesForReadCount,senderDevice,timeOut,pauseBeforeRead,pauseBeforeWrite,parameters,property,null,needRollBack);
    }

    public void sendRequestOverReservLine(){
        if(nextLine!=null){
            nextLine.async_communicate(formRequestForNextLine());
        } else {
            if(property!=null && property instanceof ValueProperty) ((ValueProperty)property).invalidate();
        }

    }

    public long getTryCount() {
        return tryCount;
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
                "requestNum=" + requestNum +
                ", bytesForSend=" + Arrays.toString(bytesForSend) +
                '}';
    }

    public void invalidate(){
        if(property!=null && property instanceof ValueProperty<?>){
            if(needRollBack)((ValueProperty<?>)property).rollback();
            else ((ValueProperty<?>)property).invalidate();
        }
    }

}

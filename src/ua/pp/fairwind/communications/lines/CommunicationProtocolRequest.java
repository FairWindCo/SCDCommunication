package ua.pp.fairwind.communications.lines;

import ua.pp.fairwind.communications.devices.DeviceInterface;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;


/**
 * Created by Сергей on 09.07.2015.
 */
public class CommunicationProtocolRequest {
    final private byte[] bytesForSend;
    private final long createTime=System.currentTimeMillis();
    private final long timeOut;
    private final long pauseBeforeRead;
    private final long bytesForReadCount;
    final private DeviceInterface senderDevice;
    private final LineParameters parameters;
    private final AbstractProperty property;
    private final LineInterface nextLine;
    private final long tryCount;

    public CommunicationProtocolRequest(byte[] bytesForSend, long bytesForReadCount, DeviceInterface senderDevice, long timeOut, long pauseBeforeRead, LineParameters parameters, AbstractProperty property, LineInterface nextLine) {
        if(senderDevice==null) throw new IllegalArgumentException("senderDevice cannot be NULL!!!!");
        this.bytesForSend = bytesForSend;
        this.senderDevice = senderDevice;
        this.timeOut = timeOut;
        this.parameters = parameters;
        this.property = property;
        this.pauseBeforeRead=pauseBeforeRead;
        this.nextLine=nextLine;
        this.bytesForReadCount=bytesForReadCount;
        tryCount=0;
    }

    public CommunicationProtocolRequest(byte[] bytesForSend, long bytesForReadCount, DeviceInterface senderDevice, long timeOut, long pauseBeforeRead, LineParameters parameters, LineInterface nextLine) {
        if(senderDevice==null) throw new IllegalArgumentException("senderDevice cannot be NULL!!!!");
        this.bytesForSend = bytesForSend;
        this.senderDevice = senderDevice;
        this.timeOut = timeOut;
        this.parameters = parameters;
        property=null;
        this.pauseBeforeRead=pauseBeforeRead;
        this.nextLine=nextLine;
        this.bytesForReadCount=bytesForReadCount;
        tryCount=0;
    }

    public CommunicationProtocolRequest(long bytesForReadCount, DeviceInterface senderDevice, long timeOut, long pauseBeforeRead, LineParameters parameters, LineInterface nextLine) {
        if(senderDevice==null) throw new IllegalArgumentException("senderDevice cannot be NULL!!!!");
        this.senderDevice = senderDevice;
        this.timeOut = timeOut;
        this.parameters = parameters;
        property=null;
        bytesForSend=null;
        this.pauseBeforeRead=pauseBeforeRead;
        this.nextLine=nextLine;
        this.bytesForReadCount=bytesForReadCount;
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
        return new CommunicationProtocolRequest(bytesForSend,bytesForReadCount,senderDevice,timeOut,pauseBeforeRead,parameters,property,null);
    }

    public void sendRequestOverReservLine(){
        if(nextLine!=null){
            nextLine.async_communicate(formRequestForNextLine());
        }

    }

    public long getTryCount() {
        return tryCount;
    }
}

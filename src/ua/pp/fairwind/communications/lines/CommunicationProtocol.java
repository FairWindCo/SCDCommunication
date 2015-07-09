package ua.pp.fairwind.communications.lines;

import ua.pp.fairwind.communications.devices.DeviceInterface;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;


/**
 * Created by Сергей on 09.07.2015.
 */
public class CommunicationProtocol {
    final private byte[] bytesForSend;
    final private DeviceInterface senderDevice;
    private final long createTime=System.currentTimeMillis();
    private final long timeOut;
    private final long pauseBeforeRead;
    private final LineParameters parameters;
    private final AbstractProperty property;
    private final LineInterface nextLine;

    public CommunicationProtocol(byte[] bytesForSend, DeviceInterface senderDevice, long timeOut, long pauseBeforeRead,LineParameters parameters, AbstractProperty property,LineInterface nextLine) {
        if(senderDevice==null) throw new IllegalArgumentException("senderDevice cannot be NULL!!!!");
        this.bytesForSend = bytesForSend;
        this.senderDevice = senderDevice;
        this.timeOut = timeOut;
        this.parameters = parameters;
        this.property = property;
        this.pauseBeforeRead=pauseBeforeRead;
        this.nextLine=nextLine;
    }

    public CommunicationProtocol(byte[] bytesForSend, DeviceInterface senderDevice, long timeOut, long pauseBeforeRead,LineParameters parameters,LineInterface nextLine) {
        if(senderDevice==null) throw new IllegalArgumentException("senderDevice cannot be NULL!!!!");
        this.bytesForSend = bytesForSend;
        this.senderDevice = senderDevice;
        this.timeOut = timeOut;
        this.parameters = parameters;
        property=null;
        this.pauseBeforeRead=pauseBeforeRead;
        this.nextLine=nextLine;
    }

    public CommunicationProtocol(DeviceInterface senderDevice, long timeOut, long pauseBeforeRead,LineParameters parameters,LineInterface nextLine) {
        if(senderDevice==null) throw new IllegalArgumentException("senderDevice cannot be NULL!!!!");
        this.senderDevice = senderDevice;
        this.timeOut = timeOut;
        this.parameters = parameters;
        property=null;
        bytesForSend=null;
        this.pauseBeforeRead=pauseBeforeRead;
        this.nextLine=nextLine;
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

    public CommunicationProtocol formRequestForNextLine(){
        return new CommunicationProtocol(bytesForSend,senderDevice,timeOut,pauseBeforeRead,parameters,property,null);
    }

    public void sendRequestOverReservLine(){
        if(nextLine!=null){
            nextLine.async_communicate(formRequestForNextLine());
        }

    }
}

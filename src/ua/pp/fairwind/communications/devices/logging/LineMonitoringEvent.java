package ua.pp.fairwind.communications.devices.logging;

import ua.pp.fairwind.communications.devices.DeviceInterface;
import ua.pp.fairwind.communications.lines.LineInterface;
import ua.pp.fairwind.communications.utils.CommunicationUtils;

/**
 * Created by Сергей on 13.08.2015.
 */
public class LineMonitoringEvent {
    public enum ACTION_TYPE{
        READ,
        WRITE
    }
    final private ACTION_TYPE action;
    final private byte[] buffer;
    final LineInterface line;
    final DeviceInterface device;
    final private long date=System.currentTimeMillis();

    public LineMonitoringEvent(ACTION_TYPE action, byte[] buffer, LineInterface line, DeviceInterface device) {
        this.action = action;
        this.buffer = buffer;
        this.line = line;
        this.device = device;
    }

    public ACTION_TYPE getAction() {
        return action;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public LineInterface getLine() {
        return line;
    }

    public DeviceInterface getDevice() {
        return device;
    }

    public long getDate() {
        return date;
    }

    public String getStringBuffer(){
        return CommunicationUtils.bufferToString(buffer);
    }
}

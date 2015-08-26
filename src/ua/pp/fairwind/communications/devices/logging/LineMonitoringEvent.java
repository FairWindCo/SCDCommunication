package ua.pp.fairwind.communications.devices.logging;

import ua.pp.fairwind.communications.devices.DeviceInterface;
import ua.pp.fairwind.communications.lines.LineInterface;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.utils.CommunicationUtils;

import java.util.Arrays;
import java.util.Date;

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
    final AbstractProperty property;
    final private long date=System.currentTimeMillis();

    public LineMonitoringEvent(ACTION_TYPE action, byte[] buffer, LineInterface line, DeviceInterface device,AbstractProperty property) {
        this.action = action;
        this.buffer = buffer;
        this.line = line;
        this.device = device;
        this.property=property;
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

    public AbstractProperty getProperty() {
        return property;
    }

    @Override
    public String toString() {
        return "LME{" +
                "A=" + action +
                ", B=" + Arrays.toString(buffer) +
                ", L=" + line +
                ", D=" + device +
                ", P=" + property +
                ", date=" + new Date(date) +
                '}';
    }
}

package ua.pp.fairwind.communications.devices.logging;

import ua.pp.fairwind.communications.devices.abstracts.LinedDeviceInterface;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.utils.CommunicationUtils;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by Сергей on 13.08.2015.
 */
public class LineMonitoringEvent {
    final LineInterface line;
    final LinedDeviceInterface device;
    final AbstractProperty property;
    final private ACTION_TYPE action;
    final private byte[] buffer;
    final private long date = System.currentTimeMillis();
    public LineMonitoringEvent(ACTION_TYPE action, byte[] buffer, LineInterface line, LinedDeviceInterface device, AbstractProperty property) {
        this.action = action;
        this.buffer = buffer;
        this.line = line;
        this.device = device;
        this.property = property;
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

    public LinedDeviceInterface getDevice() {
        return device;
    }

    public long getDate() {
        return date;
    }

    public String getStringBuffer() {
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

    public enum ACTION_TYPE {
        READ,
        WRITE
    }
}

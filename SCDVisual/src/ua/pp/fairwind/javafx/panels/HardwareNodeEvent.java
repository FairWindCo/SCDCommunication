package ua.pp.fairwind.javafx.panels;

import ua.pp.fairwind.communications.propertyes.event.EventType;

/**
 * Created by Сергей on 16.07.2015.
 */
public class HardwareNodeEvent {
    final private String elementName;
    final private EventType level;
    final private String info;
    final private long time=System.currentTimeMillis();

    public HardwareNodeEvent(String elementName, EventType level, String info) {
        this.elementName = elementName;
        this.level = level;
        this.info = info;
    }

    public HardwareNodeEvent(String elementName, EventType level, Object info) {
        this.elementName = elementName;
        this.level = level;
        this.info = info==null?"----":info.toString();
    }

    public String getElementName() {
        return elementName;
    }

    public EventType getLevel() {
        return level;
    }

    public String getInfo() {
        return info;
    }

    public long getTime() {
        return time;
    }
}

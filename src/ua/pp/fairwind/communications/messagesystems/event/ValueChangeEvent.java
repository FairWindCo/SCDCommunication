package ua.pp.fairwind.communications.messagesystems.event;

import ua.pp.fairwind.communications.abstractions.SystemEllement;

/**
 * Created by ������ on 30.06.2015.
 */
public class ValueChangeEvent<T> {
    final private String uuidFrom;
    final private String nameFrom;
    final private SystemEllement sourceEvent;
    final private T oldValue;
    final private T newValue;
    final private Event parent;

    public ValueChangeEvent(String uuidFrom, String nameFrom, SystemEllement sourceEvent, T oldValue, T newValue,Event parent) {
        this.uuidFrom = uuidFrom;
        this.nameFrom = nameFrom;
        this.sourceEvent = sourceEvent;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.parent=parent;
    }

    public String getUuidFrom() {
        return uuidFrom;
    }

    public String getNameFrom() {
        return nameFrom;
    }

    public SystemEllement getSourceEvent() {
        return sourceEvent;
    }

    public T getOldValue() {
        return oldValue;
    }

    public T getNewValue() {
        return newValue;
    }

    public Event getParent() {
        return parent;
    }
}

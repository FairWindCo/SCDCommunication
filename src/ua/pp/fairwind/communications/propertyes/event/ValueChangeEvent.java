package ua.pp.fairwind.communications.propertyes.event;

import ua.pp.fairwind.communications.abstractions.SystemEllement;

/**
 * Created by Сергей on 30.06.2015.
 */
public class ValueChangeEvent<T> {
    final private String uuidFrom;
    final private String nameFrom;
    final private SystemEllement sourceEvent;
    final private T oldValue;
    final private T newValue;

    public ValueChangeEvent(String uuidFrom, String nameFrom, SystemEllement sourceEvent, T oldValue, T newValue) {
        this.uuidFrom = uuidFrom;
        this.nameFrom = nameFrom;
        this.sourceEvent = sourceEvent;
        this.oldValue = oldValue;
        this.newValue = newValue;
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
}

package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.messagesystems.event.Event;

/**
 * Created by Сергей on 09.11.2015.
 */
public interface ValuePropertyModificator {
    static void setInternalValue(ValueProperty property, final Comparable value, Event modificatorEvent) {
        property.setInternalValue(value, modificatorEvent);
    }
    static void setSilentValue(ValueProperty property, final Comparable value) {
        property.setSilentValue(value);
    }
}

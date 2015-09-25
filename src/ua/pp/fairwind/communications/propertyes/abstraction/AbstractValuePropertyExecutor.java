package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.messagesystems.event.Event;

/**
 * Created by Сергей on 13.09.2015.
 */
public abstract class AbstractValuePropertyExecutor {
    static protected void setInternalValue(ValueProperty property, final Comparable value,Event modificatorEvent){
        property.setInternalValue(value,modificatorEvent);
    }
}

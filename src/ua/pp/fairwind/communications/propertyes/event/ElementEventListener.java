package ua.pp.fairwind.communications.propertyes.event;

import ua.pp.fairwind.communications.abstractions.ElementInterface;

/**
 * Created by Сергей on 01.07.2015.
 */
public interface ElementEventListener {
    void elementEvent(ElementInterface element,EventType typeEvent,Object params);
}

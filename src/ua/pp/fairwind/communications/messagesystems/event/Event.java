package ua.pp.fairwind.communications.messagesystems.event;

import ua.pp.fairwind.communications.abstractions.ElementInterface;

import java.util.UUID;

/**
 * Created by Сергей on 25.09.2015.
 */
public class Event {
    public final ElementInterface sourceElement;
    public final UUID sourceElementUUID;
    public final UUID parentEcentElementUUID;
    public final EventType typeEvent;
    public final Object params;
    public final Event parent;


    public Event(UUID sourceElement, EventType typeEvent, Object params) {
        this.sourceElement = null;
        sourceElementUUID = sourceElement;
        this.parentEcentElementUUID = null;
        this.typeEvent = typeEvent;
        this.params = params;
        this.parent = null;
    }

    public Event(UUID sourceElement, EventType typeEvent, Object params, Event parentevent) {
        this.sourceElement = null;
        sourceElementUUID = sourceElement;
        this.typeEvent = typeEvent;
        this.params = params;
        this.parent = parentevent;
        this.parentEcentElementUUID = parentevent.sourceElementUUID;

    }


    public Event(ElementInterface sourceElement, EventType typeEvent, Object params) {
        this.sourceElement = sourceElement;
        sourceElementUUID = sourceElement.getUUID();
        this.parentEcentElementUUID = null;
        this.typeEvent = typeEvent;
        this.params = params;
        this.parent = null;
    }

    public Event(ElementInterface sourceElement, EventType typeEvent, Object params, Event parentevent) {
        this.sourceElement = sourceElement;
        sourceElementUUID = sourceElement.getUUID();
        this.typeEvent = typeEvent;
        this.params = params;
        this.parent = parentevent;
        this.parentEcentElementUUID = (parentevent != null) ? parentevent.sourceElementUUID : null;
    }

    public Event(ElementInterface sourceElement, EventType typeEvent, Object params, UUID parentEcentElementUUID) {
        this.sourceElement = sourceElement;
        sourceElementUUID = sourceElement.getUUID();
        this.typeEvent = typeEvent;
        this.params = params;
        this.parent = null;
        this.parentEcentElementUUID = parentEcentElementUUID;
    }

    public ElementInterface getSourceElement() {
        return sourceElement;
    }

    public UUID getSourceElementUUID() {
        return sourceElementUUID;
    }

    public UUID getParentEcentElementUUID() {
        return parentEcentElementUUID;
    }

    public EventType getTypeEvent() {
        return typeEvent;
    }

    public Object getParams() {
        return params;
    }

    public Event getParent() {
        return parent;
    }
}

package ua.pp.fairwind.communications.abstractions;

import ua.pp.fairwind.communications.messagesystems.event.EventType;

/**
 * Created by Сергей on 27.08.2015.
 */
public interface EventSender {
    void fireEvent(EventType type,Object param);
}

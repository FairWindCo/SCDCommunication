package ua.pp.fairwind.communications.messagesystems.event;

/**
 * Created by ������ on 01.07.2015.
 */
public interface ElementEventListener {
    void elementEvent(final Event event,final Object processingParameter);

    ElementEventListener println=(event,param) -> System.out.println(event.typeEvent + " : " + event.sourceElement + " - " + event.params);
}

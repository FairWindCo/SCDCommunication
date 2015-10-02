package ua.pp.fairwind.communications.abstractions;

import ua.pp.fairwind.communications.messagesystems.event.ElementEventListener;
import ua.pp.fairwind.communications.messagesystems.event.EventType;

import java.util.UUID;

/**
 * Created by ������ on 30.06.2015.
 */
public interface ElementInterface {
    String getName();
    UUID getUUID();
    String getUUIDString();
    String getDescription();
    String getCodename();

    void addEventListener(ElementEventListener listener);
    void addEventListener(ElementEventListener listener,EventType... recivedEventsTypes);
    void addEventListener(ElementEventListener listener,UUID ignore);
    void addEventListener(ElementEventListener listener,UUID ignore,EventType... recivedEventsTypes);
    void addEventListener(ElementEventListener listener,UUID ignore,Object processorParam);
    void addEventListener(ElementEventListener listener,UUID ignore,Object processorParam,EventType... recivedEventsTypes);

    void removeEventListener(ElementEventListener listener);
    void destroy();

    void setEnabled(boolean enabled);
    boolean isEnabled();
    String getHardwareName();
}

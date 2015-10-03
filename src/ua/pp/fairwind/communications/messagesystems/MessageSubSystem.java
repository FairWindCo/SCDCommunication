package ua.pp.fairwind.communications.messagesystems;

import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.messagesystems.event.*;

import java.util.UUID;

/**
 * Created by ������ on 01.07.2015.
 */
public interface MessageSubSystem {
    void fireEvent(ElementInterface element, EventType type, Object param);

    void fireEvent(ElementInterface element, EventType type, Object param, Event parent);

    void fireEvent(UUID source, final Event event);

    void fireEvent(UUID source, final ValueChangeEvent<?> event);

    void addEventListener(ElementEventListener listener);

    void addEventListener(ElementEventListener listener, UUID ignore);

    void addEventListener(ElementEventListener listener, UUID ignore, EventType... recivedEcentTypes);

    void addEventListener(ElementEventListener listener, EventType... recivedEcentTypes);

    void addChangeEventListener(ValueChangeListener<?> listener);

    void removeEventListener(ElementEventListener listener);

    void removeChangeEventListener(ValueChangeListener<?> listener);

    void clear();

    void destroy();

    void destroyService();

    MessageSubSystem getNewChild(final UUID requestedElement);
}

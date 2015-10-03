package ua.pp.fairwind.communications.messagesystems;

import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.messagesystems.event.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Created by Сергей on 27.08.2015.
 */
public class MessageSubSystemSimple implements MessageSubSystem {
    protected final CopyOnWriteArrayList<ListenerHolder> eventDispatcher = new CopyOnWriteArrayList<>();
    protected final CopyOnWriteArrayList<ValueChangeListener<?>> calueEventDispatcher = new CopyOnWriteArrayList<>();

    @Override
    public void fireEvent(ElementInterface element, EventType type, Object param, Event parent) {
        fireEvent(element != null ? element.getUUID() : null, new Event(element, type, param, parent));
    }

    @Override
    public void fireEvent(UUID source, Event event) {
        for (ListenerHolder listener : eventDispatcher) {
            listener.executeEvent(event);
        }
    }

    @Override
    public void fireEvent(ElementInterface element, EventType type, Object param) {
        fireEvent(element != null ? element.getUUID() : null, new Event(element, type, param));
    }


    protected void fireEventExecute(UUID source, final ValueChangeEvent<?> event) {
        for (ValueChangeListener listener : calueEventDispatcher) {
            listener.valueChange(event);
        }
    }

    @Override
    public void fireEvent(UUID source, final ValueChangeEvent<?> event) {
        fireEventExecute(source, event);
    }

    @Override
    public void addEventListener(ElementEventListener listener) {
        if (listener != null) {
            eventDispatcher.add(new ListenerHolder(listener, null));
        }
    }

    @Override
    public void addEventListener(ElementEventListener listener, EventType... recivedEcentTypes) {
        if (listener != null) {
            eventDispatcher.add(new ListenerHolder(listener, null, recivedEcentTypes));
        }
    }

    @Override
    public void addEventListener(ElementEventListener listener, UUID ignore) {
        if (listener != null) {
            eventDispatcher.add(new ListenerHolder(listener, ignore));
        }
    }

    @Override
    public void addEventListener(ElementEventListener listener, UUID ignore, EventType... recivedEcentTypes) {
        if (listener != null) {
            eventDispatcher.add(new ListenerHolder(listener, ignore, recivedEcentTypes));
        }
    }

    @Override
    public void removeEventListener(ElementEventListener listener) {
        List<ListenerHolder> removs = eventDispatcher.stream().filter(holder -> holder.getListener().equals(listener)).collect(Collectors.toList());
        if (listener != null) {
            eventDispatcher.removeAll(removs);
        }
    }

    @Override
    public void clear() {
        eventDispatcher.clear();
        calueEventDispatcher.clear();
    }

    @Override
    public void destroy() {
        eventDispatcher.clear();
        calueEventDispatcher.clear();
    }


    public void addChangeEventListener(ValueChangeListener<?> listener) {
        calueEventDispatcher.add(listener);
    }


    public void removeChangeEventListener(ValueChangeListener<?> listener) {
        calueEventDispatcher.remove(listener);
    }

    @Override
    public void destroyService() {

    }

    @Override
    public MessageSubSystem getNewChild(UUID requestedElement) {
        return new MessageSubSystemSimple();
    }
}

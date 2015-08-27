package ua.pp.fairwind.communications.messagesystems;

import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.propertyes.event.ElementEventListener;
import ua.pp.fairwind.communications.propertyes.event.EventType;
import ua.pp.fairwind.communications.propertyes.event.ValueChangeEvent;
import ua.pp.fairwind.communications.propertyes.event.ValueChangeListener;

/**
 * Created by ������ on 01.07.2015.
 */
public interface MessageSubSystem {
        void fireEvent(ElementInterface element,EventType type,Object param);
        void fireEvent(final ValueChangeEvent<?> event);
        void addEventListener(ElementEventListener listener);
        void addChangeEventListener(ValueChangeListener<?> listener);
        void removeEventListener(ElementEventListener listener);
        void removeChangeEventListener(ValueChangeListener<?> listener);
        void clear();
        void destroy();
}

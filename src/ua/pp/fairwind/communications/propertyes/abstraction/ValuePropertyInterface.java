package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.messagesystems.event.ValueChangeListener;
import ua.pp.fairwind.communications.propertyes.abstraction.markers.ValueInterfaceMarker;

import java.util.Date;

/**
 * Created by ������ on 04.07.2015.
 */
public interface ValuePropertyInterface<T extends Comparable<? super T>> extends ElementInterface, ReadWriteProperty<T>,ValueInterfaceMarker<T> {
    @Override
    T getValue();

    @Override
    void setValue(T value);

    Date getLastChangeTime();

    void addChangeEventListener(ValueChangeListener<? super T> listener);

    void removeChangeEventListener(ValueChangeListener<? super T> listener);

    void bindReadProperty(ValueProperty<? extends T> property);

    void bindWriteProperty(ValueProperty<? super T> property);

    @Override
    int compareTo(T o);

    @Override
    void destroy();

    void readValueRequest();

    void writeValueRequest();

    boolean isReadAccepted();

    boolean isWriteAccepted();

    boolean isActive();

    void setActive(boolean active);
}

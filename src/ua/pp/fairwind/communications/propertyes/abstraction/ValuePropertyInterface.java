package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.propertyes.event.ValueChangeListener;

import java.util.Date;

/**
 * Created by ������ on 04.07.2015.
 */
public interface ValuePropertyInterface<T extends Comparable<? super T>> extends ElementInterface, ReadWriteProperty<T> {
    @Override
    T getValue();

    @Override
    void setValue(T value);

    Date getLastChangeTime();

    void addChangeEventListener(ValueChangeListener<? super T> listener);

    void removeChangeEventListener(ValueChangeListener<? super T> listener);

    void bindReadProperty(ValueProperty<? extends T> property);

    void bindWriteProperty(ValueProperty<? super T> property);

    void unbindReadProperty();

    void unbindWriteProperty();

    @Override
    int compareTo(T o);

    @Override
    void destroy();

    void readValueRequest();
    void writeValueRequest();

    boolean isReadAccepted();
    boolean isWriteAccepted();
}

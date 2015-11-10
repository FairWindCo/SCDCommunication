package ua.pp.fairwind.communications.propertyes.abstraction.markers;

/**
 * Created by Сергей on 10.11.2015.
 */
public interface ValueInterfaceMarker<T> extends MarkerInterfaces{
    T getValue();
    void setValue(T value);
}

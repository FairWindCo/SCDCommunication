package ua.pp.fairwind.communications.propertyes.abstraction;

/**
 * Created by ������ on 26.06.2015.
 */
public interface WriteOnlyProperty<T> extends Comparable<T>{
    void setValue(final T value);
}

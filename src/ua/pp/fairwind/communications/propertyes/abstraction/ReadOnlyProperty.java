package ua.pp.fairwind.communications.propertyes.abstraction;

/**
 * Created by ������ on 26.06.2015.
 */
public interface ReadOnlyProperty<T> extends Comparable<T> {
    T getValue();
}

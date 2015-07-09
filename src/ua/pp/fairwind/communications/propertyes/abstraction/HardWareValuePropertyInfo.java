package ua.pp.fairwind.communications.propertyes.abstraction;

/**
 * Created by Сергей on 09.07.2015.
 */
public class HardWareValuePropertyInfo<T extends Comparable<? super T>> extends HardWarePropertyInfo {
    public HardWareValuePropertyInfo(long address, ValueProperty<T> property) {
        super(address,property);
    }

    public T getInternalValue() {
        return ((ValueProperty<T>)property).getInternalValue();
    }

    public void setInternalValue(T value) {
        ((ValueProperty<T>)property).setInternalValue(value);
    }
}

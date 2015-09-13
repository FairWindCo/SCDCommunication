package ua.pp.fairwind.communications.propertyes.abstraction;

/**
 * Created by Сергей on 13.09.2015.
 */
public abstract class AbstractValuePropertyExecutor {
    static protected void setInternalValue(ValueProperty property, final Comparable value,boolean silent,boolean fromHardWare){
        property.setInternalValue(value, silent, fromHardWare);
    }

    static protected void setHardWareInternalValue(ValueProperty property, final Comparable value) {
        setInternalValue(property,value, false, true);
    }

    static protected void setSilentInternalValue(ValueProperty property, final Comparable value) {
        setInternalValue(property,value, true, false);
    }

    static protected void setInternalValue(ValueProperty property, final Comparable value) {
        setInternalValue(property,value, false, false);
    }

}

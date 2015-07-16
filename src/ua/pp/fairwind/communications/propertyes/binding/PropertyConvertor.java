package ua.pp.fairwind.communications.propertyes.binding;

import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;

/**
 * Created by Сергей on 16.07.2015.
 */
public interface PropertyConvertor<FROM extends AbstractProperty,TO extends AbstractProperty> {
    boolean convertValue(FROM fromValue,TO toValue) throws BindingConvertExceptions;

    default boolean convert(AbstractProperty from,AbstractProperty to){
        return false;
    }
}

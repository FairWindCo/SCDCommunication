package ua.pp.fairwind.communications.propertyes.abstraction;

import java.util.IllegalFormatConversionException;

/**
 * Created by Сергей on 25.09.2015.
 */
public interface BindingValueProcessot<SourceValue,DestValue> {
    SourceValue convertValue(DestValue value)throws IllegalFormatConversionException;
}

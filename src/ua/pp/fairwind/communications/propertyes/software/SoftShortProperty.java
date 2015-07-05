package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;

/**
 * Created by Сергей on 01.07.2015.
 */
public class SoftShortProperty extends NumberProperty<Short> {
    public SoftShortProperty(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public SoftShortProperty(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public SoftShortProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public SoftShortProperty(String name, MessageSubSystem centralSystem, Short value) {
        super(name, centralSystem, value);
    }

    public SoftShortProperty(String name, String description, MessageSubSystem centralSystem, Short value) {
        super(name, description, centralSystem, value);
    }

    public SoftShortProperty(String name, String uuid, String description, MessageSubSystem centralSystem, Short value) {
        super(name, uuid, description, centralSystem, value);
    }

    protected Short convertFromNumber(Number value){
        if(value!=null) return value.shortValue();return null;
    }

    @Override
    protected Short convertFromString(String value, int radix) {
        if(value!=null) return Short.parseShort(value,radix);
        return null;
    }
}

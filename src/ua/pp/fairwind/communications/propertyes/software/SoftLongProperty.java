package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;

/**
 * Created by Сергей on 04.07.2015.
 */
public class SoftLongProperty extends NumberProperty<Long> {
    public SoftLongProperty(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public SoftLongProperty(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public SoftLongProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public SoftLongProperty(String name, MessageSubSystem centralSystem, Long value) {
        super(name, centralSystem, value);
    }

    public SoftLongProperty(String name, String description, MessageSubSystem centralSystem, Long value) {
        super(name, description, centralSystem, value);
    }

    public SoftLongProperty(String name, String uuid, String description, MessageSubSystem centralSystem, Long value) {
        super(name, uuid, description, centralSystem, value);
    }

    @Override
    protected Long convertFromNumber(Number value) {
        if(value!=null)return value.longValue();
        return null;
    }

    @Override
    protected Long convertFromString(String value, int radix) {
        if(value!=null) return Long.parseLong(value,radix);
        return null;
    }
}


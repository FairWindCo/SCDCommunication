package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;

/**
 * Created by Сергей on 01.07.2015.
 */
public class SoftIntegerProperty extends NumberProperty<Integer> {
    public SoftIntegerProperty(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public SoftIntegerProperty(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public SoftIntegerProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public SoftIntegerProperty(String name, MessageSubSystem centralSystem, Integer value) {
        super(name, centralSystem, value);
    }

    public SoftIntegerProperty(String name, String description, MessageSubSystem centralSystem, Integer value) {
        super(name, description, centralSystem, value);
    }

    public SoftIntegerProperty(String name, String uuid, String description, MessageSubSystem centralSystem, Integer value) {
        super(name, uuid, description, centralSystem, value);
    }

    protected Integer convertFromNumber(Number value){
        if(value!=null) return value.intValue();return null;
    }

    @Override
    protected Integer convertFromString(String value, int radix) {
        if(value!=null) return Integer.parseInt(value,radix);
        return null;
    }
}

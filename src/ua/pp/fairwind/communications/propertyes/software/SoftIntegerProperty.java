package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;

/**
 * Created by FairWindCo on 07.07.2015
 */
public class SoftIntegerProperty extends NumberProperty<Integer> {
    public SoftIntegerProperty(String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly) {
        super(name, uuid, description, centralSystem, readonly, writeonly);
    }

    public SoftIntegerProperty(String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly, Integer value) {
        super(name, uuid, description, centralSystem, readonly, writeonly, value);
    }

    public SoftIntegerProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public SoftIntegerProperty(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public SoftIntegerProperty(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public SoftIntegerProperty(String name, String uuid, String description) {
        super(name, uuid, description);
    }

    public SoftIntegerProperty(String name, String description) {
        super(name, description);
    }

    public SoftIntegerProperty(String name) {
        super(name);
    }

    public SoftIntegerProperty(String name, String uuid, String description, MessageSubSystem centralSystem, Integer value) {
        super(name, uuid, description, centralSystem, value);
    }

    public SoftIntegerProperty(String name, String description, MessageSubSystem centralSystem, Integer value) {
        super(name, description, centralSystem, value);
    }

    public SoftIntegerProperty(String name, MessageSubSystem centralSystem, Integer value) {
        super(name, centralSystem, value);
    }

    public SoftIntegerProperty(String name, String uuid, String description, Integer value) {
        super(name, uuid, description, value);
    }

    public SoftIntegerProperty(String name, String description, Integer value) {
        super(name, description, value);
    }

    public SoftIntegerProperty(String name, Integer value) {
        super(name, value);
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

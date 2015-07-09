package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;

/**
 * Created by FairWindCo on 07.07.2015
 */
public class SoftFloatProperty extends NumberProperty<Float> {
    public SoftFloatProperty(String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly) {
        super(name, uuid, description, centralSystem, readonly, writeonly);
    }

    public SoftFloatProperty(String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly, Float value) {
        super(name, uuid, description, centralSystem, readonly, writeonly, value);
    }

    public SoftFloatProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public SoftFloatProperty(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public SoftFloatProperty(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public SoftFloatProperty(String name, String uuid, String description) {
        super(name, uuid, description);
    }

    public SoftFloatProperty(String name, String description) {
        super(name, description);
    }

    public SoftFloatProperty(String name) {
        super(name);
    }

    public SoftFloatProperty(String name, String uuid, String description, MessageSubSystem centralSystem, Float value) {
        super(name, uuid, description, centralSystem, value);
    }

    public SoftFloatProperty(String name, String description, MessageSubSystem centralSystem, Float value) {
        super(name, description, centralSystem, value);
    }

    public SoftFloatProperty(String name, MessageSubSystem centralSystem, Float value) {
        super(name, centralSystem, value);
    }

    public SoftFloatProperty(String name, String uuid, String description, Float value) {
        super(name, uuid, description, value);
    }

    public SoftFloatProperty(String name, String description, Float value) {
        super(name, description, value);
    }

    public SoftFloatProperty(String name, Float value) {
        super(name, value);
    }

    protected Float convertFromNumber(Number value){
        if(value!=null) return value.floatValue();return null;
    }

    @Override
    protected Float convertFromString(String value, int radix) {
        if(value!=null) return Float.parseFloat(value);
        return null;
    }
}

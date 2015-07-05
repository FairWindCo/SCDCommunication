package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;

/**
 * Created by Сергей on 01.07.2015.
 */
public class SoftFloatProperty extends NumberProperty<Float> {
    public SoftFloatProperty(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public SoftFloatProperty(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public SoftFloatProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public SoftFloatProperty(String name, MessageSubSystem centralSystem, Float value) {
        super(name, centralSystem, value);
    }

    public SoftFloatProperty(String name, String description, MessageSubSystem centralSystem, Float value) {
        super(name, description, centralSystem, value);
    }

    public SoftFloatProperty(String name, String uuid, String description, MessageSubSystem centralSystem, Float value) {
        super(name, uuid, description, centralSystem, value);
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

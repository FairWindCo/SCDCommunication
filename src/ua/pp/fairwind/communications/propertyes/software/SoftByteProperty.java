package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;

/**
 * Created by Сергей on 04.07.2015.
 */
public class SoftByteProperty extends NumberProperty<Byte> {
    public SoftByteProperty(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public SoftByteProperty(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public SoftByteProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public SoftByteProperty(String name, MessageSubSystem centralSystem, Byte value) {
        super(name, centralSystem, value);
    }

    public SoftByteProperty(String name, String description, MessageSubSystem centralSystem, Byte value) {
        super(name, description, centralSystem, value);
    }

    public SoftByteProperty(String name, String uuid, String description, MessageSubSystem centralSystem, Byte value) {
        super(name, uuid, description, centralSystem, value);
    }

    @Override
    protected Byte convertFromNumber(Number value) {
        if(value!=null)return value.byteValue();
        return null;
    }

    @Override
    protected Byte convertFromString(String value, int radix) {
        if(value!=null) return Byte.parseByte(value,radix);
        return null;
    }
}

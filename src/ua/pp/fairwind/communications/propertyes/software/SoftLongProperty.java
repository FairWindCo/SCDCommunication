package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;

/**
 * Created by FairWindCo on 07.07.2015
 */
public class SoftLongProperty extends NumberProperty<Long> {
    public SoftLongProperty(String name, String uuid, String description, MessageSubSystem centralSystem,SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid, description, centralSystem, softOperationType);
    }

    public SoftLongProperty(String name, String uuid, String description, MessageSubSystem centralSystem, SOFT_OPERATION_TYPE softOperationType, Long value) {
        super(name, uuid, description, centralSystem, softOperationType, value);
    }

    public SoftLongProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public SoftLongProperty(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public SoftLongProperty(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public SoftLongProperty(String name, String uuid, String description) {
        super(name, uuid, description);
    }

    public SoftLongProperty(String name, String description) {
        super(name, description);
    }

    public SoftLongProperty(String name) {
        super(name);
    }

    public SoftLongProperty(String name, String uuid, String description, MessageSubSystem centralSystem, Long value) {
        super(name, uuid, description, centralSystem, value);
    }

    public SoftLongProperty(String name, String description, MessageSubSystem centralSystem, Long value) {
        super(name, description, centralSystem, value);
    }

    public SoftLongProperty(String name, MessageSubSystem centralSystem, Long value) {
        super(name, centralSystem, value);
    }

    public SoftLongProperty(String name, String uuid, String description, Long value) {
        super(name, uuid, description, value);
    }

    public SoftLongProperty(String name, String description, Long value) {
        super(name, description, value);
    }

    public SoftLongProperty(String name, Long value) {
        super(name, value);
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


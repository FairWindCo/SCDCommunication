package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;

/**
 * Created by FairWindCo on 07.07.2015
 */
public class SoftShortProperty extends NumberProperty<Short> {
    public SoftShortProperty(String name, String uuid, String description, MessageSubSystem centralSystem, SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid, description, centralSystem, softOperationType);
    }

    public SoftShortProperty(String name, String uuid, String description, MessageSubSystem centralSystem,SOFT_OPERATION_TYPE softOperationType, Short value) {
        super(name, uuid, description, centralSystem, softOperationType, value);
    }

    public SoftShortProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public SoftShortProperty(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public SoftShortProperty(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public SoftShortProperty(String name, String uuid, String description) {
        super(name, uuid, description);
    }

    public SoftShortProperty(String name, String description) {
        super(name, description);
    }

    public SoftShortProperty(String name) {
        super(name);
    }

    public SoftShortProperty(String name, String uuid, String description, MessageSubSystem centralSystem, Short value) {
        super(name, uuid, description, centralSystem, value);
    }

    public SoftShortProperty(String name, String description, MessageSubSystem centralSystem, Short value) {
        super(name, description, centralSystem, value);
    }

    public SoftShortProperty(String name, MessageSubSystem centralSystem, Short value) {
        super(name, centralSystem, value);
    }

    public SoftShortProperty(String name, String uuid, String description, Short value) {
        super(name, uuid, description, value);
    }

    public SoftShortProperty(String name, String description, Short value) {
        super(name, description, value);
    }

    public SoftShortProperty(String name, Short value) {
        super(name, value);
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

package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;

/**
 * Created by FairWindCo on 07.07.2015
 */
public class SoftFloatProperty extends NumberProperty<Float> {
    public SoftFloatProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid, softOperationType);
    }

    public SoftFloatProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType, Float value) {
        super(name, uuid, softOperationType, value);
    }

    public SoftFloatProperty(String name, SOFT_OPERATION_TYPE softOperationType) {
        super(name, softOperationType);
    }

    public SoftFloatProperty(String name, SOFT_OPERATION_TYPE softOperationType, Float value) {
        super(name, softOperationType, value);
    }

    public SoftFloatProperty(String name, String uuid) {
        super(name, uuid);
    }

    public SoftFloatProperty(String name) {
        super(name);
    }

    public SoftFloatProperty(String name, String uuid, Float value) {
        super(name, uuid, value);
    }

    public SoftFloatProperty(String name, Float value) {
        super(name, value);
    }

    protected Float convertFromNumber(Number value) {
        if (value != null) return value.floatValue();
        return null;
    }

    @Override
    protected Float convertFromString(String value, int radix) {
        if (value != null) return Float.parseFloat(value);
        return null;
    }
}

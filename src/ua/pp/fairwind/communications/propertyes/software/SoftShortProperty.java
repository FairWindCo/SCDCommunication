package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;

/**
 * Created by FairWindCo on 07.07.2015
 */
public class SoftShortProperty extends NumberProperty<Short> {
    public SoftShortProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid, softOperationType);
    }

    public SoftShortProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType, Short value) {
        super(name, uuid, softOperationType, value);
    }

    public SoftShortProperty(String name, SOFT_OPERATION_TYPE softOperationType) {
        super(name, softOperationType);
    }

    public SoftShortProperty(String name, SOFT_OPERATION_TYPE softOperationType, Short value) {
        super(name, softOperationType, value);
    }

    public SoftShortProperty(String name, String uuid) {
        super(name, uuid);
    }

    public SoftShortProperty(String name) {
        super(name);
    }

    public SoftShortProperty(String name, String uuid, Short value) {
        super(name, uuid, value);
    }

    public SoftShortProperty(String name, Short value) {
        super(name, value);
    }

    protected Short convertFromNumber(Number value) {
        if (value != null) return value.shortValue();
        return null;
    }

    @Override
    protected Short convertFromString(String value, int radix) {
        if (value != null) return Short.parseShort(value, radix);
        return null;
    }
}

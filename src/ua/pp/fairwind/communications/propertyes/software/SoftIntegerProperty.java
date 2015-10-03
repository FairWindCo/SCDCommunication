package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;

/**
 * Created by FairWindCo on 07.07.2015
 */
public class SoftIntegerProperty extends NumberProperty<Integer> {
    public SoftIntegerProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid, softOperationType);
    }

    public SoftIntegerProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType, Integer value) {
        super(name, uuid, softOperationType, value);
    }

    public SoftIntegerProperty(String name, SOFT_OPERATION_TYPE softOperationType) {
        super(name, softOperationType);
    }

    public SoftIntegerProperty(String name, SOFT_OPERATION_TYPE softOperationType, Integer value) {
        super(name, softOperationType, value);
    }

    public SoftIntegerProperty(String name, String uuid) {
        super(name, uuid);
    }

    public SoftIntegerProperty(String name) {
        super(name);
    }

    public SoftIntegerProperty(String name, String uuid, Integer value) {
        super(name, uuid, value);
    }

    public SoftIntegerProperty(String name, Integer value) {
        super(name, value);
    }

    protected Integer convertFromNumber(Number value) {
        if (value != null) return value.intValue();
        return null;
    }

    @Override
    protected Integer convertFromString(String value, int radix) {
        if (value != null) return Integer.parseInt(value, radix);
        return null;
    }


}

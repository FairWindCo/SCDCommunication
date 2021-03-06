package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.markers.LongValueInterface;

/**
 * Created by FairWindCo on 07.07.2015
 */
public class SoftLongProperty extends NumberProperty<Long> implements LongValueInterface{
    public SoftLongProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid, softOperationType);
    }

    public SoftLongProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType, Long value) {
        super(name, uuid, softOperationType, value);
    }

    public SoftLongProperty(String name, SOFT_OPERATION_TYPE softOperationType) {
        super(name, softOperationType);
    }

    public SoftLongProperty(String name, SOFT_OPERATION_TYPE softOperationType, Long value) {
        super(name, softOperationType, value);
    }

    public SoftLongProperty(String name, String uuid) {
        super(name, uuid);
    }

    public SoftLongProperty(String name) {
        super(name);
    }

    public SoftLongProperty(String name, String uuid, Long value) {
        super(name, uuid, value);
    }

    public SoftLongProperty(String name, Long value) {
        super(name, value);
    }

    @Override
    protected Long convertFromNumber(Number value) {
        if (value != null) return value.longValue();
        return null;
    }

    @Override
    protected Long convertFromString(String value, int radix) {
        if (value != null) return Long.parseLong(value, radix);
        return null;
    }

    @Override
    public SoftLongProperty setAdditionalInfo(String paramsName, Object value) {
        super.setAdditionalInfo(paramsName, value);
        return this;
    }
}


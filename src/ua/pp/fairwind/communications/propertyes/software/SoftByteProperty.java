package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;

/**
 * Created by FairWindCo on 07.07.2015
 */
public class SoftByteProperty extends NumberProperty<Byte> {
    public SoftByteProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid, softOperationType);
    }

    public SoftByteProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType, Byte value) {
        super(name, uuid, softOperationType, value);
    }

    public SoftByteProperty(String name, SOFT_OPERATION_TYPE softOperationType) {
        super(name, softOperationType);
    }

    public SoftByteProperty(String name, SOFT_OPERATION_TYPE softOperationType, Byte value) {
        super(name, softOperationType, value);
    }

    public SoftByteProperty(String name, String uuid) {
        super(name, uuid);
    }

    public SoftByteProperty(String name) {
        super(name);
    }

    public SoftByteProperty(String name, String uuid, Byte value) {
        super(name, uuid, value);
    }

    public SoftByteProperty(String name, Byte value) {
        super(name, value);
    }

    @Override
    protected Byte convertFromNumber(Number value) {
        if (value != null) return value.byteValue();
        return null;
    }

    @Override
    protected Byte convertFromString(String value, int radix) {
        if (value != null) return Byte.parseByte(value, radix);
        return null;
    }

    @Override
    public SoftByteProperty setAdditionalInfo(String paramsName, Object value) {
        super.setAdditionalInfo(paramsName, value);
        return this;
    }
}

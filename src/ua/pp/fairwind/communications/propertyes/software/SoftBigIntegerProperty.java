package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.markers.BigIntValueInterface;

import java.math.BigInteger;

/**
 * Created by Сергей on 07.07.2015.
 */
public class SoftBigIntegerProperty extends NumberProperty<BigInteger> implements BigIntValueInterface{
    public SoftBigIntegerProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid, softOperationType);
    }

    public SoftBigIntegerProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType, BigInteger value) {
        super(name, uuid, softOperationType, value);
    }

    public SoftBigIntegerProperty(String name, SOFT_OPERATION_TYPE softOperationType) {
        super(name, softOperationType);
    }

    public SoftBigIntegerProperty(String name, SOFT_OPERATION_TYPE softOperationType, BigInteger value) {
        super(name, softOperationType, value);
    }

    public SoftBigIntegerProperty(String name, String uuid) {
        super(name, uuid);
    }

    public SoftBigIntegerProperty(String name) {
        super(name);
    }

    public SoftBigIntegerProperty(String name, String uuid, BigInteger value) {
        super(name, uuid, value);
    }

    public SoftBigIntegerProperty(String name, BigInteger value) {
        super(name, value);
    }

    @Override
    protected BigInteger convertFromNumber(Number value) {
        if (value != null) {
            return new BigInteger(value.toString());
        } else {
            return null;
        }
    }

    @Override
    protected BigInteger convertFromString(String value, int radix) {
        if (value != null) {
            return new BigInteger(value, radix);
        } else {
            return null;
        }
    }
}

package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;

import java.math.BigInteger;

/**
 * Created by Сергей on 07.07.2015.
 */
public class SoftBigIntegerProperty extends NumberProperty<BigInteger>{

    public SoftBigIntegerProperty(String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly) {
        super(name, uuid, description, centralSystem, readonly, writeonly);
    }

    public SoftBigIntegerProperty(String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly, BigInteger value) {
        super(name, uuid, description, centralSystem, readonly, writeonly, value);
    }

    public SoftBigIntegerProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public SoftBigIntegerProperty(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public SoftBigIntegerProperty(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public SoftBigIntegerProperty(String name, String uuid, String description) {
        super(name, uuid, description);
    }

    public SoftBigIntegerProperty(String name, String description) {
        super(name, description);
    }

    public SoftBigIntegerProperty(String name) {
        super(name);
    }

    public SoftBigIntegerProperty(String name, String uuid, String description, MessageSubSystem centralSystem, BigInteger value) {
        super(name, uuid, description, centralSystem, value);
    }

    public SoftBigIntegerProperty(String name, String description, MessageSubSystem centralSystem, BigInteger value) {
        super(name, description, centralSystem, value);
    }

    public SoftBigIntegerProperty(String name, MessageSubSystem centralSystem, BigInteger value) {
        super(name, centralSystem, value);
    }

    public SoftBigIntegerProperty(String name, String uuid, String description, BigInteger value) {
        super(name, uuid, description, value);
    }

    public SoftBigIntegerProperty(String name, String description, BigInteger value) {
        super(name, description, value);
    }

    public SoftBigIntegerProperty(String name, BigInteger value) {
        super(name, value);
    }

    @Override
    protected BigInteger convertFromNumber(Number value) {
        if(value!=null) {
            return new BigInteger(value.toString());
        } else {
            return null;
        }
    }

    @Override
    protected BigInteger convertFromString(String value, int radix) {
        if(value!=null) {
            return new BigInteger(value, radix);
        } else {
            return null;
        }
    }
}
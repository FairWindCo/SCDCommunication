package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;

import java.math.BigDecimal;

/**
 * Created by Сергей on 07.07.2015.
 */
public class SoftBigDecimalProperty extends NumberProperty<BigDecimal> {
    public SoftBigDecimalProperty(String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly) {
        super(name, uuid, description, centralSystem, readonly, writeonly);
    }

    public SoftBigDecimalProperty(String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly, BigDecimal value) {
        super(name, uuid, description, centralSystem, readonly, writeonly, value);
    }

    public SoftBigDecimalProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public SoftBigDecimalProperty(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public SoftBigDecimalProperty(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public SoftBigDecimalProperty(String name, String uuid, String description) {
        super(name, uuid, description);
    }

    public SoftBigDecimalProperty(String name, String description) {
        super(name, description);
    }

    public SoftBigDecimalProperty(String name) {
        super(name);
    }

    public SoftBigDecimalProperty(String name, String uuid, String description, MessageSubSystem centralSystem, BigDecimal value) {
        super(name, uuid, description, centralSystem, value);
    }

    public SoftBigDecimalProperty(String name, String description, MessageSubSystem centralSystem, BigDecimal value) {
        super(name, description, centralSystem, value);
    }

    public SoftBigDecimalProperty(String name, MessageSubSystem centralSystem, BigDecimal value) {
        super(name, centralSystem, value);
    }

    public SoftBigDecimalProperty(String name, String uuid, String description, BigDecimal value) {
        super(name, uuid, description, value);
    }

    public SoftBigDecimalProperty(String name, String description, BigDecimal value) {
        super(name, description, value);
    }

    public SoftBigDecimalProperty(String name, BigDecimal value) {
        super(name, value);
    }

    @Override
    protected BigDecimal convertFromNumber(Number value) {
        if(value==null) return null;
        return new BigDecimal(value.toString());
    }

    @Override
    protected BigDecimal convertFromString(String value, int radix) {
        if(value==null) return null;
        return new BigDecimal(value);
    }
}

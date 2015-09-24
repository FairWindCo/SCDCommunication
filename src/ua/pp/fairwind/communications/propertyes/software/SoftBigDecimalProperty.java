package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.elementsdirecotry.SystemElementDirectory;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;

import java.math.BigDecimal;

/**
 * Created by Сергей on 07.07.2015.
 */
public class SoftBigDecimalProperty extends NumberProperty<BigDecimal> {
    public SoftBigDecimalProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid, softOperationType);
    }

    public SoftBigDecimalProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType, BigDecimal value) {
        super(name, uuid, softOperationType, value);
    }

    public SoftBigDecimalProperty(String name, SOFT_OPERATION_TYPE softOperationType) {
        super(name, softOperationType);
    }

    public SoftBigDecimalProperty(String name, SOFT_OPERATION_TYPE softOperationType, BigDecimal value) {
        super(name, softOperationType, value);
    }

    public SoftBigDecimalProperty(String name, String uuid) {
        super(name, uuid);
    }

    public SoftBigDecimalProperty(String name) {
        super(name);
    }

    public SoftBigDecimalProperty(String name, String uuid, BigDecimal value) {
        super(name, uuid, value);
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

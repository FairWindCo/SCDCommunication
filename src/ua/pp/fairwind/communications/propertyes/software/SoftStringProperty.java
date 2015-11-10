package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.StringPropertyInterface;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.markers.StringValueInterface;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ������ on 04.07.2015.
 */
public class SoftStringProperty extends ValueProperty<String> implements StringPropertyInterface,StringValueInterface {
    volatile private int readradix = 10;
    volatile private String readformat;
    volatile private boolean readbinarybool = false;


    public SoftStringProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid, softOperationType);
    }


    public SoftStringProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType, String value) {
        super(name, uuid, softOperationType, value);
    }

    public SoftStringProperty(String name, String uuid, String value) {
        super(name, uuid, value);
    }


    public SoftStringProperty(String name) {
        super(name);
    }

    protected String convertValue(Object value, String format, int radix, int position, int length, boolean convertBool) {
        String newval = null;
        if (value == null) return null;
        if (value instanceof Number) {
            if (format != null) {
                DecimalFormat fm = new DecimalFormat(format);
                newval = fm.format(value);
            } else {
                if (value instanceof Integer) {
                    newval = Integer.toString((Integer) value, radix);
                } else if (value instanceof Short) {
                    newval = Integer.toString(((Short) value).shortValue(), radix);
                } else if (value instanceof Long) {
                    newval = Long.toString((Long) value, radix);
                } else if (value instanceof Byte) {
                    newval = Integer.toString(((Byte) value).byteValue(), radix);
                } else if (value instanceof BigInteger) {
                    newval = ((BigInteger) value).toString(radix);
                } else if (value instanceof BigDecimal) {
                    newval = value.toString();
                } else if (value instanceof Float) {
                    newval = value.toString();
                } else if (value instanceof Double) {
                    newval = value.toString();
                }
            }
        } else if (value instanceof Date) {
            if (format == null) {
                newval = value.toString();
            } else {
                DateFormat fm = new SimpleDateFormat(readformat);
                newval = fm.format(value);
            }
        } else if (value instanceof Boolean) {
            if (convertBool) {
                if (((Boolean) value).booleanValue()) {
                    return "1";
                } else {
                    return "0";
                }
            } else {
                newval = value.toString();
            }
        } else {
            newval = super.convertValue(value);
        }
        return newval;
    }

    @Override
    public void bindReadNumberProperty(NumberProperty<?> property, int radix) {
        this.readradix = radix;
        bindPropertyForRead(property, null);
        this.readformat = null;
    }

    @Override
    public void bindReadNumberProperty(NumberProperty<?> property) {
        this.readradix = 10;
        bindPropertyForRead(property, null);
        this.readformat = null;
    }

    @Override
    public void bindReadNumberProperty(NumberProperty<?> property, String format) {
        bindPropertyForRead(property, format);
    }

    @Override
    public void bindWriteNumberProperty(NumberProperty<?> property, int radix) {
        bindPropertyForWrite(property, null);
    }

    @Override
    public void bindWriteNumberProperty(NumberProperty<?> property) {
        bindPropertyForWrite(property, null);
    }

    @Override
    public void bindWriteNumberProperty(NumberProperty<?> property, String format) {
        bindPropertyForWrite(property, format);
    }


}

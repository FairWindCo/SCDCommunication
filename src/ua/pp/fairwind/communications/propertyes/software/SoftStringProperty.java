package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.StringPropertyInterface;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ������ on 04.07.2015.
 */
public class SoftStringProperty extends ValueProperty<String> implements StringPropertyInterface {
    volatile private int readradix=10;
    volatile private String readformat;
    volatile private boolean readbinarybool=false;



    protected String convertValue(Object value,String format,int radix,int position,int length,boolean convertBool){
        String newval=null;
        if(value==null) return null;
        if(value instanceof Number){
            if(format!=null){
                DecimalFormat fm=new DecimalFormat(format);
                newval=fm.format(value);
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
        }else if(value instanceof Date) {
            if(format==null) {
                newval = value.toString();
            } else {
                DateFormat fm=new SimpleDateFormat(readformat);
                newval=fm.format(value);
            }
        } else if(value instanceof Boolean){
            if(convertBool){
                if(((Boolean) value).booleanValue()){
                    return "1";
                } else {
                    return "0";
                }
            } else {
                newval = value.toString();
            }
        }else {
            newval = super.convertValue(value);
        }
        return newval;
    }


    @Override
    public void bindReadNumberProperty(NumberProperty<?> property, int radix) {
          this.readradix=radix;
          bindPropertyForRead(property);
          this.readformat=null;
    }

    @Override
    public void bindReadNumberProperty(NumberProperty<?> property) {
        this.readradix=10;
        bindPropertyForRead(property);
        this.readformat=null;
    }


    @Override
    public void unbindReadNumberProperty() {
        readformat=null;
        unbindPropertyForRead();
    }

    @Override
    public void unbindWriteNumberProperty() {
        unbindPropertyForWrite();
    }

    @Override
    public void bindReadNumberProperty(NumberProperty<?> property, String format) {
        bindPropertyForRead(property);
        this.readformat=format;
    }

    @Override
    public void bindWriteNumberProperty(NumberProperty<?> property, int radix) {
        bindPropertyForWrite((AbstractProperty)property, null, radix, 0, -1, false);
    }


    @Override
    public void bindWriteNumberProperty(NumberProperty<?> property) {
        bindPropertyForWrite((AbstractProperty)property, null, 10, 0, -1, false);
    }

    @Override
    public void bindWriteNumberProperty(NumberProperty<?> property, String format) {
        bindPropertyForWrite((AbstractProperty)property, format, 10, 0, -1, false);
    }

    public SoftStringProperty(String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly) {
        super(name, uuid, description, centralSystem, readonly, writeonly);
    }

    public SoftStringProperty(String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly, String value) {
        super(name, uuid, description, centralSystem, readonly, writeonly, value);
    }

    public SoftStringProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public SoftStringProperty(String name, String uuid, String description, MessageSubSystem centralSystem, String value) {
        super(name, uuid, description, centralSystem, value);
    }


    public SoftStringProperty(String name) {
        super(name);
    }

    public SoftStringProperty(String name, String uuid, String description, String value) {
        super(name, uuid, description, value);
    }

}

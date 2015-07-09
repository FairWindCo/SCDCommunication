package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.event.EventType;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * Created by ������ on 01.07.2015.
 */
public abstract class NumberProperty<M extends Number & Comparable<? super M>> extends ValueProperty<M> implements NumberPropertyInterface<M>,ValuePropertyInterface<M> {
    volatile private int readradix=10;
    volatile private String readformat;

    public NumberProperty(String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly) {
        super(name, uuid, description, centralSystem, readonly, writeonly);
    }

    public NumberProperty(String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly, M value) {
        super(name, uuid, description, centralSystem, readonly, writeonly, value);
    }

    public NumberProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        this(name, uuid, description, centralSystem, false, false);
    }

    public NumberProperty(String name, String description, MessageSubSystem centralSystem) {
        this(name, null, description, centralSystem, false, false);
    }

    public NumberProperty(String name, MessageSubSystem centralSystem) {
        this(name, null, null, centralSystem, false, false);
    }

    public NumberProperty(String name, String uuid, String description) {
        this(name, uuid, description, null, false, false);
    }

    public NumberProperty(String name, String description) {
        this(name, null, description, null, false, false);
    }

    public NumberProperty(String name) {
        this(name, null, null, null, false, false);
    }

    public NumberProperty(String name, String uuid, String description, MessageSubSystem centralSystem, M value) {
        this(name, uuid, description, centralSystem, false, false,value);
    }

    public NumberProperty(String name, String description, MessageSubSystem centralSystem, M value) {
        this(name, null, description, centralSystem, false, false,value);
    }

    public NumberProperty(String name, MessageSubSystem centralSystem, M value) {
        this(name, null, null, centralSystem, false, false,value);
    }

    public NumberProperty(String name, String uuid, String description, M value) {
        this(name, uuid, description, null, false, false,value);
    }

    public NumberProperty(String name, String description, M value) {
        this(name, null, description, null, false, false, value);
    }

    public NumberProperty(String name, M value) {
        this(name, null, null, null, false, false, value);
    }

    @Override
    public void bindReadNumberProperty(NumberProperty<? extends Number> property) {
        bindPropertyForRead(property);
    }

    @Override
    public void bindWriteNumberProperty(NumberProperty<? extends Number> property){
        bindPropertyForWrite(property,null,10,0,-1,false);
    }

    abstract protected M convertFromNumber(Number value);
    abstract protected M convertFromString(String value,int radix);


       @Override
    protected void reciveValueFromBindingWrite(AbstractProperty property, Object valueForWtite, String formatForWrite, int radixForWrite, int positionForWrite, int lengthForWrite, boolean convertBoolToBinaryForWrite) {
        if(valueForWtite!=null){
            setValue(convertValue(valueForWtite,readformat,readradix,positionForWrite,lengthForWrite,convertBoolToBinaryForWrite));
        } else {
            super.reciveValueFromBindingWrite(property, valueForWtite, formatForWrite, radixForWrite, positionForWrite, lengthForWrite, convertBoolToBinaryForWrite);
        }
    }

    @Override
    protected void reciveValueFromBindingRead(AbstractProperty property, Object valueForWtite) {
        if(valueForWtite!=null){
            setValue(convertValue(valueForWtite,readformat,readradix,0,-1,false));
        } else {
            super.reciveValueFromBindingRead(property, valueForWtite);
        }

    }

    protected M convertValue(Object value,String format,int radix,int position,int length,boolean convertBool){
        if(value instanceof Number){
            return convertFromNumber((Number)value);
        } else if (value instanceof String){
            if(format==null) {
                try {
                    return convertFromString((String) value, radix);
                } catch (NumberFormatException ex){
                    fireEvent(EventType.PARSE_ERROR,ex.getLocalizedMessage());
                    return null;
                }
            } else {
                DecimalFormat frm=new DecimalFormat(format);
                try {
                    return convertFromNumber(frm.parse((String) value));
                } catch (ParseException e) {
                    fireEvent(EventType.PARSE_ERROR,e.getLocalizedMessage());
                    return null;
                }
            }
        }
        return super.convertValue(value);
    }



    @Override
    public void bindReadStringProperty(StringPropertyInterface property, int radix) {
        this.readradix=radix;
        this.readformat=null;
        bindPropertyForRead((AbstractProperty) property);
    }


    @Override
    public void bindWriteStringProperty(StringPropertyInterface property, int radix) {
        bindPropertyForWrite((AbstractProperty)property,null,radix,0,-1,false);
    }

    @Override
    public void bindReadStringProperty(StringPropertyInterface property, String format) {
        bindPropertyForRead((AbstractProperty) property);
        this.readformat=format;
    }

    @Override
    public void bindWriteStringProperty(StringPropertyInterface property, String format) {
        bindPropertyForWrite((AbstractProperty)property,format,10,0,-1,false);
    }

    @Override
    public void bindReadStringProperty(StringPropertyInterface property) {
        bindPropertyForRead((AbstractProperty)property);
    }

    @Override
    public void bindWriteStringProperty(StringPropertyInterface property) {
        bindPropertyForWrite((AbstractProperty)property, null, 10, 0, -1, false);
    }

    @Override
    public void unbindReadNumberProperty() {
        unbindPropertyForRead();
    }

    @Override
    public void unbindWriteNumberProperty() {
        unbindPropertyForWrite();
    }

    @Override
    public void unbindReadStringProperty() {
        readformat=null;
        unbindPropertyForRead();
    }

    @Override
    public void unbindWriteStringProperty() {
        unbindPropertyForWrite();
    }

    @Override
    public void destroy() {
        super.destroy();
        unbindReadNumberProperty();
        unbindWriteNumberProperty();
        unbindReadStringProperty();
        unbindWriteStringProperty();
    }
}

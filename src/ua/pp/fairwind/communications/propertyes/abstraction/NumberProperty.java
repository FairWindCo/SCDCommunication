package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.event.EventType;
import ua.pp.fairwind.communications.propertyes.event.ValueChangeListener;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * Created by Сергей on 01.07.2015.
 */
public abstract class NumberProperty<M extends Number & Comparable<? super M>> extends ValueProperty<M> implements NumberPropertyInterface<M>,ValuePropertyInterface<M> {
    volatile private int readradix=10;
    volatile private String readformat;
    volatile private int writeradix=10;
    volatile private String writeformat;

    private final ValueChangeListener<String> readstrlistener=event -> {
     if(readformat==null){
        try {
            setValue(convertFromString((String) event.getNewValue(), readradix));
        } catch (NumberFormatException ex){
            fireEvent(EventType.PARSE_ERROR,ex.getLocalizedMessage());
        }
     }else {
         DecimalFormat frm=new DecimalFormat(readformat);
         try {
             setValue(convertFromNumber(frm.parse((String) event.getNewValue())));
         } catch (ParseException e) {
             fireEvent(EventType.PARSE_ERROR,e.getLocalizedMessage());
         }
     }
    };

    public NumberProperty(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public NumberProperty(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public NumberProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public NumberProperty(String name, MessageSubSystem centralSystem, M value) {
        super(name, centralSystem, value);
    }

    public NumberProperty(String name, String description, MessageSubSystem centralSystem, M value) {
        super(name, description, centralSystem, value);
    }

    public NumberProperty(String name, String uuid, String description, MessageSubSystem centralSystem, M value) {
        super(name, uuid, description, centralSystem, value);
    }

    @Override
    public void bindReadNumberProperty(NumberProperty<? extends Number> property) {
        bindPropertyForRead(property);
    }

    @Override
    public void bindWriteNumberProperty(NumberProperty<? extends Number> property){
        bindPropertyForWrite(property);
    }

    abstract protected M convertFromNumber(Number value);
    abstract protected M convertFromString(String value,int radix);

    @Override
    protected M convertValue(Object value) {
        if(value instanceof Number){
            return convertFromNumber((Number)value);
        } else if (value instanceof String){
            if(readformat==null) {
                try {
                    return convertFromString((String) value, readradix);
                } catch (NumberFormatException ex){
                    fireEvent(EventType.PARSE_ERROR,ex.getLocalizedMessage());
                    return null;
                }
            } else {
                DecimalFormat frm=new DecimalFormat(readformat);
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
    protected void writeBindingOpearion(AbstractProperty property) {
        if(property instanceof NumberPropertyInterface<?>){
            ((NumberPropertyInterface<?>)property).bindReadNumberProperty(this);
        } else if(property instanceof StringPropertyInterface){
            if(writeformat==null) {
                ((StringPropertyInterface) property).bindReadNumberProperty(this, writeradix);
            } else {
                ((StringPropertyInterface) property).bindReadNumberProperty(this, writeformat);
            }
        } else {
            super.writeBindingOpearion(property);
        }
    }

    @Override
    public void bindWriteStringProperty(StringPropertyInterface property, int radix) {
        this.writeradix=radix;
        this.writeformat=null;
        bindPropertyForWrite((AbstractProperty)property);
    }

    @Override
    public void bindReadStringProperty(StringPropertyInterface property, String format) {
        bindPropertyForRead((AbstractProperty) property);
        this.readformat=format;
    }

    @Override
    public void bindWriteStringProperty(StringPropertyInterface property, String format) {
        bindPropertyForWrite((AbstractProperty)property);
        writeformat=format;
    }

    @Override
    public void bindReadStringProperty(StringPropertyInterface property) {
        bindPropertyForRead((AbstractProperty)property);
    }

    @Override
    public void bindWriteStringProperty(StringPropertyInterface property) {
        this.writeformat=null;
        bindPropertyForWrite((AbstractProperty)property);
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
        writeformat=null;
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

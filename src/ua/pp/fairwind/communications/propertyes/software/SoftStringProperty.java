package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberPropertyInterface;
import ua.pp.fairwind.communications.propertyes.abstraction.StringPropertyInterface;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.event.ValueChangeListener;

import java.text.DecimalFormat;

/**
 * Created by Сергей on 04.07.2015.
 */
public class SoftStringProperty extends ValueProperty<String> implements StringPropertyInterface {
    private NumberPropertyInterface<? extends Number> readnumproperty;
    private NumberPropertyInterface<? extends Number> writenumproperty;
    volatile private int readradix=10;
    volatile private String readformat;
    private final ValueChangeListener<Number> readlistener=event->{
        Number val=(Number)event.getNewValue();
        if(val!=null) {
            String newval;
            if(readformat!=null){
                DecimalFormat fm=new DecimalFormat(readformat);
                newval=fm.format(val);
            }else{
                if(val instanceof Integer){
                    newval=Integer.toString((Integer)val,readradix);
                } else if(val instanceof Short){
                    newval=Integer.toString(val.shortValue(),readradix);
                }else if(val instanceof Long){
                    newval=Long.toString((Long)val,readradix);
                }else if(val instanceof Byte){
                    newval=Integer.toString(val.byteValue(),readradix);
                }else {
                    newval = String.valueOf(val);
                }
            }
            setValue(newval);
        }
    };


    public SoftStringProperty(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public SoftStringProperty(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public SoftStringProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public SoftStringProperty(String name, MessageSubSystem centralSystem, String value) {
        super(name, centralSystem, value);
    }

    public SoftStringProperty(String name, String description, MessageSubSystem centralSystem, String value) {
        super(name, description, centralSystem, value);
    }

    public SoftStringProperty(String name, String uuid, String description, MessageSubSystem centralSystem, String value) {
        super(name, uuid, description, centralSystem, value);
    }

    @Override
    public void bindReadNumberProperty(NumberProperty<?> property, int radix) {
        if(property!=null){
            this.readradix=radix;
            bindReadNumberProperty(property);
        }
    }

    @Override
    public void bindWriteNumberProperty(NumberProperty<?> property, int radix) {
        if(property!=null) {
            synchronized (readlistener) {
                if (writenumproperty != null) {
                    unbindWriteNumberProperty();
                }
                property.bindReadStringProperty(this,radix);
                writenumproperty=property;
            }
        }
    }

    @Override
    public void bindReadNumberProperty(NumberProperty<?> property) {
        if(property!=null){
            synchronized (readlistener){
                if(readnumproperty!=null){
                    unbindReadNumberProperty();
                }
                readnumproperty.addChangeEventListener(readlistener);
                readnumproperty=property;
            }
        }

    }

    @Override
    public void bindWriteNumberProperty(NumberProperty<?> property) {
        if(property!=null){
            synchronized (readlistener) {
                if (writenumproperty != null) {
                    unbindWriteNumberProperty();
                }
                property.bindReadStringProperty(this);
                writenumproperty = property;
            }
        }
    }

    @Override
    public void unbindReadNumberProperty() {
        synchronized (readlistener){
            if(readnumproperty!=null){
                readnumproperty.removeChangeEventListener(readlistener);
                readnumproperty=null;
                readformat=null;
            }
        }
    }

    @Override
    public void unbindWriteNumberProperty() {
        synchronized (readlistener){
            if(writenumproperty!=null){
                writenumproperty.unbindReadStringProperty();
                writenumproperty=null;
            }
        }
    }

    @Override
    public void bindReadNumberProperty(NumberProperty<?> property, String format) {
        if(property!=null){
            this.readformat=format;
            bindReadNumberProperty(property);
        }
    }

    @Override
    public void bindWriteNumberProperty(NumberProperty<?> property, String format) {
        synchronized (readlistener) {
            if (writenumproperty != null) {
                unbindWriteNumberProperty();
            }
            property.bindReadStringProperty(this,format);
            writenumproperty=property;
        }
    }
}

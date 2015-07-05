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
    private NumberPropertyInterface<? extends Number> readnumproperty;
    private NumberPropertyInterface<? extends Number> writenumproperty;
    private final ValueChangeListener<Number> readlistener=event->setValue(convertFromNumber((Number)event.getNewValue()));
    private StringPropertyInterface readstrproperty;
    private StringPropertyInterface writestrproperty;
    volatile private int readradix=10;
    volatile private String readformat;

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
        if(property!=null){
            synchronized (readlistener) {
                if(readnumproperty!=null) unbindReadNumberProperty();
                setValue(convertFromNumber(property.getValue()));
                property.addChangeEventListener(readlistener);
                readnumproperty = property;
            }
        }
    }

    @Override
    public void bindWriteNumberProperty(NumberProperty<? extends Number> property){
        if(property!=null){
            synchronized (readlistener) {
                property.bindReadNumberProperty(this);
                writenumproperty = property;
            }
        }
    }

    abstract protected M convertFromNumber(Number value);
    abstract protected M convertFromString(String value,int radix);

    @Override
    public void unbindReadNumberProperty() {
        synchronized (readlistener) {
            if (readnumproperty != null) {
                readnumproperty.removeChangeEventListener(readlistener);
                readnumproperty = null;
            }
        }
    }

    @Override
    public void unbindWriteNumberProperty() {
        synchronized (readlistener) {
            if (readnumproperty != null) {
                writenumproperty.unbindReadNumberProperty();
                readnumproperty = null;
            }
        }
    }

    @Override
    public void bindReadStringProperty(StringPropertyInterface property) {
        if(property!=null){
            synchronized (readstrlistener){
                if(readstrproperty!=null){
                    unbindReadStringProperty();
                }
                property.addChangeEventListener(readstrlistener);
                readstrproperty=property;
            }
        }
    }

    @Override
    public void bindWriteStringProperty(StringPropertyInterface property) {
        if(property!=null){
            synchronized (readstrlistener){
                if(writestrproperty!=null){
                    unbindWriteStringProperty();
                }
                property.bindReadNumberProperty(this,10);
                writestrproperty=property;
            }
        }
    }

    @Override
    public void bindReadStringProperty(StringPropertyInterface property, int radix) {
        if(property!=null){
            this.readradix=radix;
            this.readformat=null;
            bindReadStringProperty(property);
        }
    }

    @Override
    public void bindWriteStringProperty(StringPropertyInterface property, int radix) {
        if(property!=null){
            synchronized (readstrlistener){
                if(writestrproperty!=null){
                    unbindWriteStringProperty();
                }
                property.bindReadNumberProperty(this,radix);
                writestrproperty=property;
            }
        }
    }

    @Override
    public void bindReadStringProperty(StringPropertyInterface property, String format) {
        if(property!=null){
            this.readformat=format;
            bindReadStringProperty(property);
        }
    }

    @Override
    public void bindWriteStringProperty(StringPropertyInterface property, String format) {
        if(property!=null){
            synchronized (readstrlistener){
                if(writestrproperty!=null){
                    unbindWriteStringProperty();
                }
                property.bindReadNumberProperty(this,format);
                writestrproperty=property;
            }
        }
    }

    @Override
    public void unbindReadStringProperty() {
        synchronized (readstrlistener){
            if(readstrproperty!=null){
                readstrproperty.removeChangeEventListener(readstrlistener);
                readstrproperty=null;
                readformat=null;
            }
        }
    }

    @Override
    public void unbindWriteStringProperty() {
        synchronized (readstrlistener){
            if(writestrproperty!=null){
                writestrproperty.unbindReadNumberProperty();
                writestrproperty=null;
            }
        }
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

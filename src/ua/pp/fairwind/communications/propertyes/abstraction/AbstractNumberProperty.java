package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.event.ValueChangeListener;

/**
 * Created by Сергей on 01.07.2015.
 */
public abstract class AbstractNumberProperty<M extends Number & Comparable<? super M>> extends AbstractValueProperty<M> {
    volatile private AbstractNumberProperty<? extends Number> readnumproperty;
    volatile private AbstractNumberProperty<? extends Number> writenumproperty;
    private final ValueChangeListener<Number> readlistener=event->setValue(convertFromNumber((Number)event.getNewValue()));

    public AbstractNumberProperty(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public AbstractNumberProperty(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public AbstractNumberProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public AbstractNumberProperty(String name, MessageSubSystem centralSystem, M value) {
        super(name, centralSystem, value);
    }

    public AbstractNumberProperty(String name, String description, MessageSubSystem centralSystem, M value) {
        super(name, description, centralSystem, value);
    }

    public AbstractNumberProperty(String name, String uuid, String description, MessageSubSystem centralSystem, M value) {
        super(name, uuid, description, centralSystem, value);
    }

    public void bindReadNumberProperty(AbstractNumberProperty<? extends Number> property) {
        if(property!=null){
            synchronized (readlistener) {
                if(readnumproperty!=null) ubindReadNumberProperty();
                setValue(convertFromNumber(property.getValue()));
                property.addChangeEventListener(readlistener);
                readnumproperty = property;
            }
        }
    }

    public void bindWriteNumberProperty(AbstractNumberProperty<? extends Number> property){
        if(property!=null){
            synchronized (readlistener) {
                property.bindReadNumberProperty(this);
                writenumproperty = property;
            }
        }
    }

    abstract protected M convertFromNumber(Number value);


    public void ubindReadNumberProperty() {
        synchronized (readlistener) {
            if (readnumproperty != null) {
                readnumproperty.removeChangeEventListener(readlistener);
                readnumproperty = null;
            }
        }
    }

    public void ubindWriteNumberProperty() {
        synchronized (readlistener) {
            if (readnumproperty != null) {
                writenumproperty.ubindReadNumberProperty();
                readnumproperty = null;
            }
        }
    }


}

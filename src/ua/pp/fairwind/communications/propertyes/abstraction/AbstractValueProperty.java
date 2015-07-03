package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.event.EventType;
import ua.pp.fairwind.communications.propertyes.event.ValueChangeEvent;
import ua.pp.fairwind.communications.propertyes.event.ValueChangeListener;

import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Сергей on 30.06.2015.
 */
public abstract class AbstractValueProperty<T extends Comparable<? super T>> extends AbstractProperty implements ReadWriteProperty<T> {
    final CopyOnWriteArrayList<ValueChangeListener<? super T>> eventDispatcher=new CopyOnWriteArrayList<>();
    volatile T value;
    volatile Date lastChangeTime;
    volatile AbstractValueProperty<? extends T> bindedReadPoperty;
    volatile AbstractValueProperty<? super T> bindedWritePoperty;
    final private ValueChangeListener<T> readerlistener=new ValueChangeListener<T>() {
        @Override
        public void valueChange(ValueChangeEvent event) {
            setValue((T)event.getNewValue());
        }
    };

    public AbstractValueProperty(String name,MessageSubSystem centralSystem) {
        super(name,centralSystem);
    }

    public AbstractValueProperty(String name, String description,MessageSubSystem centralSystem) {
        super(name, description,centralSystem);
    }

    public AbstractValueProperty(String name, String uuid, String description,MessageSubSystem centralSystem) {
        super(name, uuid, description,centralSystem);
    }

    public AbstractValueProperty(String name,MessageSubSystem centralSystem,T value) {
        super(name,centralSystem);
        this.value=value;
    }

    public AbstractValueProperty(String name, String description,MessageSubSystem centralSystem,T value) {
        super(name, description,centralSystem);
        this.value=value;
    }

    public AbstractValueProperty(String name, String uuid, String description,MessageSubSystem centralSystem,T value) {
        super(name, uuid, description,centralSystem);
        this.value=value;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(final T value) {
        if(this.value==null && value!=null){
            this.value = value;
            lastChangeTime = new Date();
            fireChangeEvent(null, value);
        } else
        if(value!=null && value.compareTo(this.value)!=0) {
            T old=this.value;
            this.value = value;
            lastChangeTime = new Date();
            fireChangeEvent(old, value);
        }
    }

    public Date getLastChangeTime() {
        return lastChangeTime;
    }

    private void fireChangeEvent(T oldValue,T newValue){
        final ValueChangeEvent<T> event=new ValueChangeEvent<>(this.getUUIDString(),this.getName(),this,oldValue,newValue);
        for(ValueChangeListener<? super T> listener:eventDispatcher){
            listener.valueChange(event);
        }
        fireEvent(EventType.ELEMENT_CHANGE, newValue);
        if(bindedWritePoperty!=null){
            bindedWritePoperty.setValue(newValue);
        }
    }

    public void addChangeEventListener(ValueChangeListener<? super T> listener){
          eventDispatcher.add(listener);
    }


    public void removeChangeEventListener(ValueChangeListener<? super T> listener){
          eventDispatcher.remove(listener);
    }

    public void bindReadProperty(AbstractValueProperty<? extends T> property){
            if (property != null) {
                synchronized (readerlistener) {
                    if(bindedReadPoperty!=null) unbindReadProperty();
                    this.bindedReadPoperty = property;
                    setValue(property.getValue());
                    property.addChangeEventListener(readerlistener);
                }
            }
    }

    public void bindWriteProperty(AbstractValueProperty<? super T> property){
        if(property!=null) {
            synchronized (readerlistener) {
                if (bindedWritePoperty != null) unbindWriteProperty();
                this.bindedWritePoperty = property;
                property.bindReadProperty(this);
            }
        }
    }

    public  void unbindReadProperty(){
        synchronized (readerlistener) {
            if (this.bindedReadPoperty != null) {
                this.bindedReadPoperty.removeChangeEventListener(readerlistener);
                this.bindedReadPoperty = null;
            }
        }
    }

    public  void unbindWriteProperty(){
        synchronized (readerlistener) {
            if (this.bindedWritePoperty != null) {
                this.bindedWritePoperty.unbindReadProperty();
                this.bindedReadPoperty = null;
            }
        }
    }


    @Override
    public int compareTo(T o) {
        if(o!=null){
            return o.compareTo(value);
        } else {
            return ((value==null)?0:1);
        }
    }
}

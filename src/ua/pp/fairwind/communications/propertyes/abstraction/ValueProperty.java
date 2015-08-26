package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.event.EventType;
import ua.pp.fairwind.communications.propertyes.event.ValueChangeEvent;
import ua.pp.fairwind.communications.propertyes.event.ValueChangeListener;

import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Сергей on 30.06.2015.
 */
public abstract class ValueProperty<T extends Comparable<? super T>> extends AbstractProperty implements ValuePropertyInterface<T> {
    private final CopyOnWriteArrayList<ValueChangeListener<? super T>> eventDispatcher=new CopyOnWriteArrayList<>();
    final private AtomicReference<T> value=new AtomicReference<>();
    final private AtomicReference<T> oldvalue=new AtomicReference<>();
    final private AtomicLong lastChangeTime=new AtomicLong();
    final private AtomicBoolean active=new AtomicBoolean(true);
    final private AtomicBoolean valide=new AtomicBoolean(false);
    //private volatile T value;
    //private volatile long lastChangeTime;
    protected final boolean readonly;
    protected final boolean writeonly;

    //КОНСТРУКТОР
    public ValueProperty(String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly) {
        super(name, uuid, description, centralSystem);
        this.readonly = readonly;
        this.writeonly = writeonly;
    }

    public ValueProperty(String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly,T value) {
        super(name, uuid, description, centralSystem);
        this.readonly = readonly;
        this.writeonly = writeonly;
        this.value.set(value);
    }

    public ValueProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
        this.readonly = false;
        this.writeonly = false;
    }

    public ValueProperty(String name, String uuid, String description, MessageSubSystem centralSystem,T value) {
        super(name, uuid, description, centralSystem);
        this.readonly = false;
        this.writeonly = false;
        this.value.set(value);
    }

    public ValueProperty(String name, String uuid, String description) {
        super(name, uuid, description, null);
        this.readonly = false;
        this.writeonly = false;
    }

    public ValueProperty(String name, String uuid, String description,T value) {
        super(name, uuid, description, null);
        this.readonly = false;
        this.writeonly = false;
        this.value.set(value);
    }

    public ValueProperty(String name, String description) {
        super(name, null, description, null);
        this.readonly = false;
        this.writeonly = false;
    }

    public ValueProperty(String name, String description,T value) {
        super(name, null, description, null);
        this.readonly = false;
        this.writeonly = false;
        this.value.set(value);
    }

    public ValueProperty(String name) {
        super(name, null, null, null);
        this.readonly = false;
        this.writeonly = false;
    }

    public ValueProperty(String name, T value) {
        super(name, null, null, null);
        this.readonly = false;
        this.writeonly = false;
        this.value.set(value);
    }


    //Метод преобразует полученное значение в значение хранимого типа
    //Вызывается внутри обработчика сигнала об изменении значениея другого свойства при связывании.
    protected T convertValue(Object value){
        try {
            T resultValue = (T) value;
            return resultValue;
        }catch (ClassCastException ex){
            fireEvent(EventType.PARSE_ERROR,ex.getLocalizedMessage());
            return null;
        }
    }


    @Override
    protected void reciveValueFromBindingWrite(AbstractProperty property, Object valueForWtite, String formatForWrite, int radixForWrite, int positionForWrite, int lengthForWrite, boolean convertBoolToBinaryForWrite) {
        if(valueForWtite!=null){
            T newVal=convertValue(valueForWtite);
            if(newVal!=null) setValue(newVal);
        } else {
            if (property!=null && property instanceof ValueProperty<?>) {
                setValue(convertValue(((ValueProperty<T>) property).getValue()));
            }
        }
    }

    @Override
    protected void reciveValueFromBindingRead(AbstractProperty property, Object valueForWtite) {
        if(valueForWtite!=null){
            T newVal=convertValue(valueForWtite);
            if(newVal!=null) setValue(newVal);
        } else {
            if (property!=null && property instanceof ValueProperty<?>) {
                setValue(convertValue(((ValueProperty<T>) property).getValue()));
            }
        }
    }


    public T getInternalValue() {
        return value.get();
    }


    @Override
    public T getValue() {
        if(writeonly){
            fireEvent(EventType.ERROR,"Property is WRITEONLY!");
            return null;
        }
        return getInternalValue();
    }

    public void setHardWareInternalValue(final T value) {
        setInternalValue(value,false,true);
    }

    public void setSilentInternalValue(final T value) {
        setInternalValue(value,true,false);
    }

    public void setInternalValue(final T value) {
        setInternalValue(value,false,false);
    }

    void setInternalValue(final T value,boolean silent,boolean fromHardWare) {
        if(value==null) return;
        this.valide.set(true);
        if(silent){
            this.value.set(value);
            //lastChangeTime = System.currentTimeMillis();
            lastChangeTime.set(System.currentTimeMillis());
        } else {
            if (this.value.get() == null) {
                this.value.set(value);
                //lastChangeTime = System.currentTimeMillis();
                lastChangeTime.set(System.currentTimeMillis());
                fireChangeEvent(null, value,fromHardWare);
            } else if (value.compareTo(this.value.get()) != 0) {
                T old = this.value.get();
                this.value.set(value);
                this.oldvalue.set(old);
                //lastChangeTime = System.currentTimeMillis();
                lastChangeTime.set(System.currentTimeMillis());
                fireChangeEvent(old, value,fromHardWare);
            }
        }
    }

    @Override
    public void setValue(final T value) {
        if(readonly){
            fireEvent(EventType.ERROR,"Property is READONLY!");
            return;
        }
        setInternalValue(value,false,false);
    }

    @Override
    public boolean isValidProperty() {
        if(!valide.get()) return false;
        if(value.get()==null) return false;

        long lastchange=lastChangeTime.get();
        if(dataLifeTime>=0 && System.currentTimeMillis()-lastchange>dataLifeTime) return false;
        return true;
    }

    @Override
    public Date getLastChangeTime() {
        return new Date(lastChangeTime.get());
    }

    private void fireChangeEvent(T oldValue,T newValue,boolean fromHardWare){
        if(eventactive) {
            final ValueChangeEvent<T> event = new ValueChangeEvent<>(this.getUUIDString(), this.getName(), this, oldValue, newValue);
            for (ValueChangeListener<? super T> listener : eventDispatcher) {
                listener.valueChange(event);
            }
            if(fromHardWare){
                fireEvent(EventType.ELEMENT_CHANGE_FROM_HARDWARE, newValue);
            } else {
                fireEvent(EventType.ELEMENT_CHANGE, newValue);
            }
            writeBinding(newValue);
        }
    }

    @Override
    public void addChangeEventListener(ValueChangeListener<? super T> listener){
          eventDispatcher.add(listener);
    }


    @Override
    public void removeChangeEventListener(ValueChangeListener<? super T> listener){
          eventDispatcher.remove(listener);
    }

    @Override
    public void bindReadProperty(ValueProperty<? extends T> property){
        bindPropertyForRead(property);
    }

    @Override
    public void bindWriteProperty(ValueProperty<? super T> property){
        bindPropertyForWrite(property, null, 10, -1, 0, false);
    }

    @Override
    public  void unbindReadProperty(){
        unbindPropertyForRead();
    }

    @Override
    public  void unbindWriteProperty(){
        unbindPropertyForWrite();
    }


    @Override
    public int compareTo(T o) {
        if(o!=null){
            return o.compareTo(value.get());
        } else {
            return ((value==null)?0:1);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        eventDispatcher.clear();
        unbindReadProperty();
        unbindWriteProperty();
    }


    @Override
    public boolean isReadAccepted() {
        return !writeonly;
    }

    @Override
    public boolean isWriteAccepted() {
        return !readonly;
    }

    @Override
    public boolean isActive() {
        return active.get();
    }

    @Override
    public void setActive(boolean active) {
        this.active.set(active);
    }

    @Override
    public String toString() {
        return "P{Name:"+getName()+", Value:"+value.get()+" ,U:"+getUUIDString()+",D:"+getDescription()+"}";
    }

    public T getOldValue() {
        return oldvalue.get();
    }

    public void rollback(){
        invalidate();
        setInternalValue(oldvalue.get());
    }

    public void invalidate(){
        valide.set(false);
        fireEvent(EventType.INVALIDATE,null);
    }
}

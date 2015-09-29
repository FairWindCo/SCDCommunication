package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.internatianalisation.I18N;
import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.messagesystems.event.EventType;
import ua.pp.fairwind.communications.messagesystems.event.ValueChangeEvent;
import ua.pp.fairwind.communications.messagesystems.event.ValueChangeListener;

import java.util.Date;
import java.util.IllegalFormatConversionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Сергей on 30.06.2015.
 */
public abstract class ValueProperty<T extends Comparable<? super T>> extends AbstractProperty implements ValuePropertyInterface<T> {
    public enum SOFT_OPERATION_TYPE{
        READ_ONLY,
        WRITE_ONLY,
        READ_WRITE
    }
    final public static String MIN_VALUE="PROPERTY_MIN_VALUE";
    final public static String MAX_VALUE="PROPERTY_MAX_VALUE";
    final public static String VALUE_VALIDATOR="PROPERTY_VALUE_VALIDATOR";

    final private AtomicReference<T> value=new AtomicReference<>();
    final private AtomicReference<T> oldvalue=new AtomicReference<>();
    final private AtomicLong lastChangeTime=new AtomicLong();
    final private AtomicBoolean active=new AtomicBoolean(true);
    final private AtomicBoolean valide=new AtomicBoolean(false);
    //private volatile T value;
    //private volatile long lastChangeTime;
    protected final SOFT_OPERATION_TYPE softOperationType;


    //КОНСТРУКТОР
    protected ValueProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid);
        this.softOperationType = softOperationType;
    }

    protected ValueProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType,T value) {
        super(name, uuid);
        this.softOperationType = softOperationType;
        this.value.set(value);
    }

    protected ValueProperty(String name, String uuid) {
        super(name, uuid);
        this.softOperationType = SOFT_OPERATION_TYPE.READ_WRITE;
    }

    protected ValueProperty(String name, String uuid, T value) {
        super(name, uuid);
        this.softOperationType = SOFT_OPERATION_TYPE.READ_WRITE;
        this.value.set(value);
    }


    protected ValueProperty(String name) {
        super(name, null);
        this.softOperationType = SOFT_OPERATION_TYPE.READ_WRITE;
    }

    protected ValueProperty(String name, T value) {
        super(name, null);
        this.softOperationType = SOFT_OPERATION_TYPE.READ_WRITE;
        this.value.set(value);
    }


    protected ValueProperty(String name, SOFT_OPERATION_TYPE softOperationType) {
        super(name, null);
        this.softOperationType = softOperationType;
    }

    protected ValueProperty(String name, SOFT_OPERATION_TYPE softOperationType,T value) {
        super(name, null);
        this.value.set(value);
        this.softOperationType = softOperationType;
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
    protected void reciveValueFromBindingRead(AbstractProperty property, Object valueForWtite, Object bindingProcessor, Event parentEvent) {
        if(bindingProcessor!=null){
            if(bindingProcessor instanceof BindingValueProcessot){
                BindingValueProcessot processor=(BindingValueProcessot)bindingProcessor;
                try {
                    if (valueForWtite != null) {
                        T newVal = (T) processor.convertValue(valueForWtite);
                        if (newVal != null) setValueForBinding(newVal, parentEvent);
                    } else {
                        if (property != null && property instanceof ValueProperty<?>) {
                            setValueForBinding((T) processor.convertValue(((ValueProperty<T>) property).getValue()), parentEvent);
                        }
                    }
                }catch (IllegalFormatConversionException ex){
                    fireEvent(EventType.ERROR, String.format(I18N.getLocalizedString("binding.value.translate.error"),ex.getLocalizedMessage()));
                }
            } else if(bindingProcessor instanceof BindingPropertyProcessor){
                super.reciveValueFromBindingRead(property,valueForWtite,bindingProcessor,parentEvent);
            }
        } else {
            if (valueForWtite != null) {
                T newVal = convertValue(valueForWtite);
                if (newVal != null) setValueForBinding(newVal, parentEvent);
            } else {
                if (property != null && property instanceof ValueProperty<?>) {
                    setValueForBinding(convertValue(((ValueProperty<T>) property).getValue()), parentEvent);
                }
            }
        }
    }




    protected T getInternalValue() {
        return value.get();
    }


    @Override
    public T getValue() {
        if(softOperationType==SOFT_OPERATION_TYPE.WRITE_ONLY){
            fireEvent(EventType.ERROR,I18N.getLocalizedString("property.writeonly.error"));
            return null;
        }
        return getInternalValue();
    }

    protected void setInternalValue(final T value,Event parentEvent) {
        if(value==null) return;
        this.valide.set(true);
        if (this.value.get() == null) {
            this.value.set(value);
            //lastChangeTime = System.currentTimeMillis();
            lastChangeTime.set(System.currentTimeMillis());
            fireChangeEvent(null, value,parentEvent);
        } else if (value.compareTo(this.value.get()) != 0) {
            T old = this.value.get();
            this.value.set(value);
            this.oldvalue.set(old);
            //lastChangeTime = System.currentTimeMillis();
            lastChangeTime.set(System.currentTimeMillis());
            fireChangeEvent(old, value,parentEvent);
        }
    }

    protected void setInternalValue(final T value) {
        if(value==null) return;
        this.valide.set(true);
        if (this.value.get() == null) {
            this.value.set(value);
            //lastChangeTime = System.currentTimeMillis();
            lastChangeTime.set(System.currentTimeMillis());
            fireChangeEvent(null, value,null);
        } else if (value.compareTo(this.value.get()) != 0) {
            T old = this.value.get();
            this.value.set(value);
            this.oldvalue.set(old);
            //lastChangeTime = System.currentTimeMillis();
            lastChangeTime.set(System.currentTimeMillis());
            fireChangeEvent(old, value,null);
        }
    }

    @Override
    public void setValue(final T value) {
        if(softOperationType==SOFT_OPERATION_TYPE.READ_ONLY){
            fireEvent(EventType.ERROR, I18N.getLocalizedString("property.readonly.error"));
            return;
        }
        Object setupedvalidator=getAdditionalInfo(VALUE_VALIDATOR);

        if(setupedvalidator!=null && setupedvalidator instanceof ValueValidator) {
            try {
                ValueValidator<T> valueValidator=(ValueValidator<T>)setupedvalidator;
                T validate=valueValidator==null?value:valueValidator.validateNewValue(this.value.get(), value, additionalParameters,(event,param)->fireEvent(event,param));
                if(validate!=null) {
                    setInternalValue(validate);
                }
            } catch (ClassCastException ex){
                setInternalValue(value);
            }
        } else {
            setInternalValue(value);
        }
    }

    protected void setValueForBinding(final T value,Event event) {
        if(softOperationType==SOFT_OPERATION_TYPE.READ_ONLY){
            fireEvent(EventType.ERROR, I18N.getLocalizedString("property.readonly.error"));
            return;
        }
        Object setupedvalidator=getAdditionalInfo(VALUE_VALIDATOR);

        if(setupedvalidator!=null && setupedvalidator instanceof ValueValidator) {
            try {
                ValueValidator<T> valueValidator=(ValueValidator<T>)setupedvalidator;
                T validate=valueValidator==null?value:valueValidator.validateNewValue(this.value.get(), value, additionalParameters,(ev,param)->fireEvent(ev,param));
                if(validate!=null) {
                    setInternalValue(validate,event);
                }
            } catch (ClassCastException ex){
                setInternalValue(value,event);
            }
        } else {
            setInternalValue(value,event);
        }
    }

    @Override
    public boolean isValidProperty() {
        if(!valide.get()) return false;
        if(value.get()==null) return false;

        long lastchange=lastChangeTime.get();
        return !(dataLifeTime >= 0 && System.currentTimeMillis() - lastchange > dataLifeTime);
    }

    @Override
    public Date getLastChangeTime() {
        return new Date(lastChangeTime.get());
    }

    private void fireChangeEvent(T oldValue,T newValue,Event parentEvent){
        if(eventactive) {
            final ValueChangeEvent<T> event = new ValueChangeEvent<>(this.getUUIDString(), this.getName(), this, oldValue, newValue);
            centralSystem.fireEvent(event);
            fireEvent(EventType.ELEMENT_CHANGE, newValue,parentEvent);
        }
    }

    @Override
    public void addChangeEventListener(ValueChangeListener<? super T> listener){
        centralSystem.addChangeEventListener(listener);
    }


    @Override
    public void removeChangeEventListener(ValueChangeListener<? super T> listener){
        centralSystem.removeChangeEventListener(listener);
    }

    @Override
    public void bindReadProperty(ValueProperty<? extends T> property){
        bindPropertyForRead(property, null);
    }

    @Override
    public void bindWriteProperty(ValueProperty<? super T> property){
        bindPropertyForWrite(property, null);
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
        centralSystem.clear();
    }


    @Override
    public boolean isReadAccepted() {
        return softOperationType!=SOFT_OPERATION_TYPE.WRITE_ONLY;
    }

    @Override
    public boolean isWriteAccepted() {
        return softOperationType!=SOFT_OPERATION_TYPE.READ_ONLY;
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
        return String.format(I18N.getLocalizedString("property.description"),getName(),getUUIDString(),getDescription(),value.get());
    }

    public T getOldValue() {
        return oldvalue.get();
    }

    protected void rollback(Event parent){
        valide.set(false);
        //setHardWareInternalValue(oldvalue.get());
        T newVal = this.oldvalue.get();
        T oldVel = this.value.get();
        this.value.set(newVal);
        //lastChangeTime = System.currentTimeMillis();
        lastChangeTime.set(System.currentTimeMillis());
        fireChangeEvent(oldVel, newVal,parent);
    }


    protected void invalidate(Event parent){
        valide.set(false);
        fireEvent(EventType.INVALIDATE,null,parent);
    }

    public void checkValide(){
        if(isValidProperty()) fireEvent(EventType.INVALIDATE,null);
    }


}
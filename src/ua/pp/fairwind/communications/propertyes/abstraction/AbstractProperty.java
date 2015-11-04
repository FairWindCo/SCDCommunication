package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.abstractions.SystemEllement;
import ua.pp.fairwind.communications.internatianalisation.I18N;
import ua.pp.fairwind.communications.messagesystems.event.ElementEventListener;
import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.messagesystems.event.EventType;
import ua.pp.fairwind.communications.propertyes.abstraction.propertyTrunsactions.OPERATION_TYPE;
import ua.pp.fairwind.communications.propertyes.abstraction.propertyTrunsactions.OperationTrunsactions;
import ua.pp.fairwind.communications.propertyes.abstraction.propertyTrunsactions.OperationTrunsactionsSingle;

import java.util.IllegalFormatConversionException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by FairWindCo on 07.07.2015
 */
public abstract class AbstractProperty extends SystemEllement {
    final public static String PROPERTY_READ_TIME_OUT_ADDON = "PROPERTY_READ_TIME_OUT_ADDON";
    final public static String PROPERTY_PAUSE_BEFORE_READ_ADDON = "PROPERTY_PAUSE_BEFORE_READ_ADDON";
    final public static String PROPERTY_PAUSE_BEFORE_WRITE_ADDON = "PROPERTY_PAUSE_BEFORE_WRITE_ADDON";
    final public static String PROPERTY_READ_TIME_OUT = "PROPERTY_READ_TIME_OUT";
    final public static String PROPERTY_BUBLE_EVENT = "PROPERTY_BUBLE_EVENT";
    final public static String PROPERTY_PAUSE_BEFORE_READ = "PROPERTY_PAUSE_BEFORE_READ";
    final public static String PROPERTY_PAUSE_BEFORE_WRITE = "PROPERTY_PAUSE_BEFORE_WRITE";
    final public static String TIMER = "TIMER";
    final public static String TIMERS = "TIMERS";
    protected final Map<String, Object> additionalParameters = new ConcurrentHashMap<>();
    //Внутренний прослушиватель событий об изменении занчения другого свойства.
    //Используется для реализации функции связывания.
    final private ElementEventListener elementevent = (event, processingParameter) -> {
        if (event.getTypeEvent() == EventType.ELEMENT_CHANGE && event.getParams() != null) {
            ElementInterface element = event.getSourceElement();
            if (element != null && element instanceof AbstractProperty) {
                reciveValueFromBindingRead((AbstractProperty) element, event.getParams(), processingParameter, event);
            } else {
                reciveValueFromBindingRead(null, event.getParams(), processingParameter, event);
            }
        }
    };
    protected volatile long dataLifeTime;
    volatile private OperationTrunsactions requestTrunsaction = new OperationTrunsactionsSingle();

    protected AbstractProperty(String name, String uuid) {
        super(name, uuid);
    }

    public void readValueRequest() {
        fireEvent(EventType.NEED_READ_VALUE, null);
    }

    public void writeValueRequest() {
        fireEvent(EventType.NEED_WRITE_VALUE, null);
    }


    //Внутренний метод для связывания по чтению с другим методом
    protected void bindPropertyForRead(AbstractProperty property, Object bindingProcessor) {
        if (property != null) {
            property.addEventListener(elementevent, getUUID(), bindingProcessor, EventType.ELEMENT_CHANGE);
        }
    }

    //Внутренний метод для связывания по записи с другим методом
    protected void bindPropertyForWrite(AbstractProperty property, Object bindingProcessor) {
        if (property != null) {
            addEventListener(property.elementevent, getUUID(), bindingProcessor, EventType.ELEMENT_CHANGE);
        }
    }


    //Метод чтения значений для биндинга по значению
    //Если передано значение то оно анализируется (парметр newValue)
    //Если значения нет то пытаемся импользовать свойство
    //Метод позволяющий непосредственно осуществить соединение по записи
    //Необходим в том случае если для связывания необходимо передать некоторые параметр
    //Например такой как формат значения длс строков параметров.
    protected void reciveValueFromBindingRead(final AbstractProperty property, final Object valueForWtite, Object bindingProcessor, final Event parentEvent) {
        if (bindingProcessor != null && bindingProcessor instanceof BindingPropertyProcessor) {
            try {
                ((BindingPropertyProcessor) bindingProcessor).convert(property, this);
            } catch (IllegalFormatConversionException e) {
                fireEvent(EventType.ERROR, String.format(I18N.getLocalizedString("binding.value.translate.error"), e.getLocalizedMessage()));
            }
        }
    }

    public boolean isValidProperty() {
        return true;
    }


    public long getDataLifeTime() {
        return dataLifeTime;
    }

    public void setDataLifeTime(long dataLifeTime) {
        this.dataLifeTime = dataLifeTime;
    }

    //Разрыв связывания по чтению
    protected void unbindPropertyForRead(AbstractProperty property) {
        if (property != null) property.removeEventListener(elementevent);
    }

    //Разрыв связывания по записи
    protected void unbindPropertyForWrite(AbstractProperty property) {
        if (property != null) removeEventListener(property.elementevent);
    }

    public AbstractProperty setAdditionalInfo(String paramsName, Object value) {
        if (paramsName == null) return this;
        if (value == null) {
            additionalParameters.remove(paramsName);
        } else {
            additionalParameters.put(paramsName, value);
        }
        return this;
    }

    public Object getAdditionalInfo(String paramsName) {
        return additionalParameters.get(paramsName);
    }


    public long getPropertyPauseBeforeReadAddon() {
        if (additionalParameters.get(PROPERTY_PAUSE_BEFORE_READ_ADDON) == null) return 0;
        return (long) additionalParameters.get(PROPERTY_PAUSE_BEFORE_READ_ADDON);
    }

    //
    public void setPropertyPauseBeforeReadAddon(long value) {
        additionalParameters.put(PROPERTY_PAUSE_BEFORE_READ_ADDON, value);
    }

    public long getPropertyPauseBeforeRead() {
        if (additionalParameters.get(PROPERTY_PAUSE_BEFORE_READ) == null) return 0;
        return (long) additionalParameters.get(PROPERTY_PAUSE_BEFORE_READ);
    }

    public void setPropertyPauseBeforeRead(long value) {
        additionalParameters.put(PROPERTY_PAUSE_BEFORE_READ, value);
    }

    public long getPropertyTimeOutReadAddon() {
        if (additionalParameters.get(PROPERTY_READ_TIME_OUT_ADDON) == null) return 0;
        return (long) additionalParameters.get(PROPERTY_READ_TIME_OUT_ADDON);
    }

    public void setPropertyTimeOutReadAddon(long value) {
        additionalParameters.put(PROPERTY_READ_TIME_OUT_ADDON, value);
    }

    public long getPropertyTimeOutRead() {
        if (additionalParameters.get(PROPERTY_READ_TIME_OUT) == null) return 0;
        return (long) additionalParameters.get(PROPERTY_READ_TIME_OUT);
    }

    public void setPropertyTimeOutRead(long value) {
        additionalParameters.put(PROPERTY_READ_TIME_OUT, value);
    }

    public long getPropertyPauseBeforeWriteAddon() {
        if (additionalParameters.get(PROPERTY_PAUSE_BEFORE_WRITE_ADDON) == null) return 0;
        return (long) additionalParameters.get(PROPERTY_PAUSE_BEFORE_WRITE_ADDON);
    }

    public void setPropertyPauseBeforeWriteAddon(long value) {
        additionalParameters.put(PROPERTY_PAUSE_BEFORE_WRITE_ADDON, value);
    }

    public long getPropertyPauseBeforeWrite() {
        if (additionalParameters.get(PROPERTY_PAUSE_BEFORE_WRITE) == null) return 0;
        return (long) additionalParameters.get(PROPERTY_PAUSE_BEFORE_WRITE);
    }

    public void setPropertyPauseBeforeWrite(long value) {
        additionalParameters.put(PROPERTY_PAUSE_BEFORE_WRITE, value);
    }

    protected boolean isMultiRequestEnabled(OPERATION_TYPE type) {
        return requestTrunsaction.isMultiRequestEnabled(type);
    }

    protected void setMultiRequestEnabled(OPERATION_TYPE type, boolean state) {
        requestTrunsaction.setMultiRequestEnabled(type, state);
    }

    protected boolean isRequestActive(OPERATION_TYPE type) {
        return requestTrunsaction.isRequestActive(type);
    }

    protected boolean startRequest(OPERATION_TYPE type) {
        return requestTrunsaction.startRequest(type);
    }

    protected void endRequest(OPERATION_TYPE type) {
        requestTrunsaction.endRequest(type);
    }

    public void setRequestTrunsaction(OperationTrunsactions requestTrunsaction) {
        this.requestTrunsaction = requestTrunsaction;
    }

}

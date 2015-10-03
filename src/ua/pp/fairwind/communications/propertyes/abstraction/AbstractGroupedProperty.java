package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.messagesystems.event.ElementEventListener;
import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.messagesystems.event.EventType;
import ua.pp.fairwind.communications.propertyes.groups.GroupPropertyInterface;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * Created by Сергей on 20.09.2015.
 */
public abstract class AbstractGroupedProperty<BigValue extends Comparable<? super BigValue>, SmallValue extends Comparable<? super SmallValue>> extends ValueProperty<BigValue> implements GroupPropertyInterface {
    final private Map<String, ValueProperty<SmallValue>> properties = new ConcurrentHashMap<>();
    final private Map<UUID, ValueProperty<SmallValue>> propertiesUUID = new ConcurrentHashMap<>();

    final private ElementEventListener listener = (event, param) -> {
        if (event.getTypeEvent() == EventType.ELEMENT_CHANGE) {
            Object value = getAdditionalInfo(PROPERTY_BUBLE_EVENT);
            if (value != null && value instanceof Boolean && (boolean) value) {
                fireEvent(event.getTypeEvent(), event.getParams());
            }
            BigValue big = getInternalValue();
            for (ValueProperty<SmallValue> property : properties.values()) {
                big = formExternalValue(property.getInternalValue(), big, property);
            }
            if (isBubleEvent()) setInternalValue(big, event);
            else setInternalValue(big, event);
        }
    };

    public AbstractGroupedProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid, softOperationType);
    }


    public AbstractGroupedProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType, BigValue value) {
        super(name, uuid, softOperationType, value);
    }

    public AbstractGroupedProperty(String name, String uuid) {
        super(name, uuid);
    }


    public AbstractGroupedProperty(String name, String uuid, BigValue value) {
        super(name, uuid, value);
    }

    public AbstractGroupedProperty(String name) {
        super(name);
    }

    public AbstractGroupedProperty(String name, BigValue value) {
        super(name, value);
    }

    @Override
    protected void setInternalValue(BigValue value, Event parent) {
        for (ValueProperty<SmallValue> property : properties.values()) {
            SmallValue small = formInternalValue(value, property);
            property.setInternalValue(small, parent);
        }
        super.setInternalValue(value, parent);
    }

    protected abstract SmallValue formInternalValue(BigValue value, ValueProperty<SmallValue> internalProperty);

    protected abstract BigValue formExternalValue(SmallValue value, BigValue bigvalue, ValueProperty<SmallValue> internalProperty);

    protected void addPropertyies(final Collection<ValueProperty<SmallValue>> propertyList) {
        if (propertyList != null && propertyList.size() > 0) {
            for (ValueProperty<SmallValue> oneproperty : propertyList) {
                addProperty(oneproperty);
            }
        }
    }

    protected void addPropertyies(ValueProperty<SmallValue>... propertyList) {
        if (propertyList != null && propertyList.length > 0) {
            for (ValueProperty<SmallValue> oneproperty : propertyList) {
                addProperty(oneproperty);
            }
        }
    }

    protected void addProperty(ValueProperty<SmallValue> property) {
        if (property != null) {
            property.addEventListener(listener);
            properties.put(property.getName(), property);
            propertiesUUID.put(property.getUUID(), property);
        }
    }


    public AbstractProperty get(String name) {
        return properties.get(name);
    }

    @Override
    public AbstractProperty getByUUID(String name) {
        return propertiesUUID.get(name);
    }


    @Override
    public Stream<AbstractProperty> getStream() {
        return properties.values().stream().map((val) -> (AbstractProperty) val);
    }

    @Override
    public AbstractProperty getPopertyByIndex(int index) {
        Collection<ValueProperty<SmallValue>> list = properties.values();
        int in = 0;
        for (AbstractProperty prp : list) {
            if (in == index) return prp;
            in++;
        }
        return null;
    }

    public boolean isBubleEvent() {
        Object value = getAdditionalInfo(PROPERTY_BUBLE_EVENT);
        return value != null && value instanceof Boolean && (boolean) value;
    }

    public void setBubleEvent(boolean bubleEvent) {
        setAdditionalInfo(PROPERTY_BUBLE_EVENT, bubleEvent);
    }

}

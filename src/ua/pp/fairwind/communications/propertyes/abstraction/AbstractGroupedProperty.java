package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.event.ElementEventListener;
import ua.pp.fairwind.communications.propertyes.groups.GroupPropertyInterface;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * Created by Сергей on 20.09.2015.
 */
public abstract class AbstractGroupedProperty<BigValue extends Comparable<? super BigValue>,SmallValue extends Comparable<? super SmallValue>> extends ValueProperty<BigValue> implements GroupPropertyInterface {
    final private Map<String,ValueProperty<SmallValue>> properties=new ConcurrentHashMap<>();
    final private Map<UUID,ValueProperty<SmallValue>> propertiesUUID=new ConcurrentHashMap<>();

    final private ElementEventListener listener= (element, typeEvent, params) -> {
        Object value=getAdditionalInfo(PROPERTY_BUBLE_EVENT);
        if(value!=null&&value instanceof Boolean&&(boolean)value){
            fireEvent(typeEvent,params);
        }
        BigValue big=getInternalValue();
        for(ValueProperty<SmallValue> property:properties.values()){
            big=formExternalValue(property.getInternalValue(),big,property);
        }
        setInternalValue(big);
    };

    @Override
    protected void setInternalValue(BigValue value, boolean silent, boolean fromHardWare) {
        for(ValueProperty<SmallValue> property:properties.values()){
            SmallValue small=formInternalValue(value, property);
            property.setInternalValue(small,silent,fromHardWare);
        }
        super.setInternalValue(value, silent, fromHardWare);
    }

    abstract Collection<ValueProperty<SmallValue>> formInternalProperty();
    abstract SmallValue formInternalValue(BigValue   value,ValueProperty<SmallValue> internalProperty);
    abstract BigValue   formExternalValue(SmallValue value,BigValue bigvalue,ValueProperty<SmallValue> internalProperty);


    public AbstractGroupedProperty(String name, String uuid, String description, MessageSubSystem centralSystem, SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid, description, centralSystem, softOperationType);
        addPropertyies(formInternalProperty());
    }

    public AbstractGroupedProperty(String name, String uuid, String description, MessageSubSystem centralSystem, SOFT_OPERATION_TYPE softOperationType, BigValue value) {
        super(name, uuid, description, centralSystem, softOperationType, value);
        addPropertyies(formInternalProperty());
    }

    public AbstractGroupedProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
        addPropertyies(formInternalProperty());
    }

    public AbstractGroupedProperty(String name, String uuid, String description, MessageSubSystem centralSystem, BigValue value) {
        super(name, uuid, description, centralSystem, value);
        addPropertyies(formInternalProperty());
    }

    public AbstractGroupedProperty(String name, String uuid, String description) {
        super(name, uuid, description);
        addPropertyies(formInternalProperty());
    }

    public AbstractGroupedProperty(String name, String uuid, String description, BigValue value) {
        super(name, uuid, description, value);
        addPropertyies(formInternalProperty());
    }

    public AbstractGroupedProperty(String name, String description) {
        super(name, description);
        addPropertyies(formInternalProperty());
    }

    public AbstractGroupedProperty(String name, String description, BigValue value) {
        super(name, description, value);
        addPropertyies(formInternalProperty());
    }

    public AbstractGroupedProperty(String name) {
        super(name);
        addPropertyies(formInternalProperty());
    }

    public AbstractGroupedProperty(String name, BigValue value) {
        super(name, value);
        addPropertyies(formInternalProperty());
    }

    protected void addPropertyies(final Collection<ValueProperty<SmallValue>> propertyList){
        if(propertyList!=null && propertyList.size()>0){
            for(ValueProperty<SmallValue> property:propertyList){
                property.addEventListener(listener);
                properties.put(property.getName(), property);
                propertiesUUID.put(property.getUUID(), property);
            }
        }
    }


    public AbstractProperty get(String name){
        return properties.get(name);
    }

    @Override
    public AbstractProperty getByUUID(String name){
        return propertiesUUID.get(name);
    }


    @Override
    public Stream<AbstractProperty> getStream(){
        return properties.values().stream().map((val) -> (AbstractProperty) val);
    }

    @Override
    public AbstractProperty getPopertyByIndex(int index){
        Collection<ValueProperty<SmallValue>> list=properties.values();
        int in=0;
        for(AbstractProperty prp:list){
            if(in==index) return prp;
            in++;
        }
        return null;
    }

    public boolean isBubleEvent() {
        Object value=getAdditionalInfo(PROPERTY_BUBLE_EVENT);
        if(value!=null&&value instanceof Boolean&&(boolean)value){
            return true;
        } else {
            return false;
        }
    }

    public void setBubleEvent(boolean bubleEvent) {
        setAdditionalInfo(PROPERTY_BUBLE_EVENT,bubleEvent);
    }

}

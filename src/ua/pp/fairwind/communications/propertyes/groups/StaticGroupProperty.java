package ua.pp.fairwind.communications.propertyes.groups;


import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.event.ElementEventListener;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * Created by ������ on 26.06.2015.
 */
public abstract class StaticGroupProperty extends AbstractProperty implements GroupPropertyInterface {
    final private Map<String,AbstractProperty> properties;
    final private Map<UUID,AbstractProperty> propertiesUUID;

    final private ElementEventListener listener= (element, typeEvent, params) -> {
        Object value=getAdditionalInfo(PROPERTY_BUBLE_EVENT);
        if(value!=null&&value instanceof Boolean&&(boolean)value){
          fireEvent(typeEvent,params);
        }
    };


    @Override
    public AbstractProperty get(String name){
        return properties.get(name);
    }

    @Override
    public AbstractProperty getByUUID(String name){
        return propertiesUUID.get(name);
    }


    @Override
    public Stream<AbstractProperty> getStream(){
        return properties.values().stream();
    }

    @Override
    public AbstractProperty getPopertyByIndex(int index){
        Collection<AbstractProperty> list=properties.values();
        int in=0;
        for(AbstractProperty prp:list){
            if(in==index) return prp;
            in++;
        }
        return null;
    }

    public StaticGroupProperty(String name, String uuid,AbstractProperty... propertyList) {
        super(name, uuid);
        properties =new ConcurrentHashMap<>();
        propertiesUUID =new ConcurrentHashMap<>();
        addPropertyies(propertyList);
    }


    protected void addPropertyies(final AbstractProperty... propertyList){
        if(propertyList!=null && propertyList.length>0){
            for(AbstractProperty property:propertyList){
                property.addEventListener(listener);
                properties.put(property.getName(), property);
                propertiesUUID.put(property.getUUID(), property);
            }
        }
    }

    protected void addProperty(final AbstractProperty property){
        if(property!=null){
            property.addEventListener(listener);
            properties.put(property.getName(), property);
            propertiesUUID.put(property.getUUID(), property);
        }
    }

    protected void remove(final AbstractProperty property){
        if(property!=null) {
            property.removeEventListener(listener);
            properties.remove(property.getName());
            propertiesUUID.remove(property.getUUIDString());
        }
    }


    @Override
    protected void reciveValueFromBindingWrite(AbstractProperty property, Object valueForWtite, String formatForWrite, int radixForWrite, int positionForWrite, int lengthForWrite, boolean convertBoolToBinaryForWrite) {

    }

    @Override
    protected void reciveValueFromBindingRead(AbstractProperty property, Object valueForWtite) {

    }

    public boolean isBubleEvent() {
        Object value=getAdditionalInfo(PROPERTY_BUBLE_EVENT);
        return value != null && value instanceof Boolean && (boolean) value;
    }

    public void setBubleEvent(boolean bubleEvent) {
        setAdditionalInfo(PROPERTY_BUBLE_EVENT,bubleEvent);
    }
}

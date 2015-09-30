package ua.pp.fairwind.communications.propertyes.groups;


import ua.pp.fairwind.communications.messagesystems.event.ElementEventListener;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

/**
 * Created by ������ on 26.06.2015.
 */
public abstract class StaticGroupProperty extends AbstractProperty implements GroupPropertyInterface {
    final private Map<String,AbstractProperty> properties;
    final private Map<UUID,AbstractProperty> propertiesUUID;
    final private List<AbstractProperty> listproperty;

    final private ElementEventListener listener= (event,param) -> {
        Object value=getAdditionalInfo(PROPERTY_BUBLE_EVENT);
        if(value!=null&&value instanceof Boolean&&(boolean)value){
          fireEvent(event.getTypeEvent(),event.getParams());
        }
    };

    @Override
    public int propertyCount() {
        return listproperty.size();
    }

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
        return listproperty.get(index);
    }

    public StaticGroupProperty(String name, String uuid,AbstractProperty... propertyList) {
        super(name, uuid);
        properties =new ConcurrentHashMap<>();
        propertiesUUID =new ConcurrentHashMap<>();
        listproperty=new CopyOnWriteArrayList<>();
        addPropertyies(propertyList);
    }


    protected void addPropertyies(final AbstractProperty... propertyList){
        if(propertyList!=null && propertyList.length>0){
            for(AbstractProperty property:propertyList){
                property.addEventListener(listener);
                AbstractProperty previos=properties.put(property.getName(), property);
                if(previos!=null)listproperty.remove(previos);
                previos=propertiesUUID.put(property.getUUID(), property);
                if(previos!=null)listproperty.remove(previos);
                listproperty.add(property);
            }
        }
    }

    protected void addProperty(final AbstractProperty property){
        if(property!=null){
            property.addEventListener(listener);
            properties.put(property.getName(), property);
            propertiesUUID.put(property.getUUID(), property);
            AbstractProperty previos=properties.put(property.getName(), property);
            if(previos!=null)listproperty.remove(previos);
            previos=propertiesUUID.put(property.getUUID(), property);
            if(previos!=null)listproperty.remove(previos);
            listproperty.add(property);
        }
    }

    protected void remove(final AbstractProperty property){
        if(property!=null) {
            property.removeEventListener(listener);
            properties.remove(property.getName());
            propertiesUUID.remove(property.getUUIDString());
        }
    }


    public boolean isBubleEvent() {
        Object value=getAdditionalInfo(PROPERTY_BUBLE_EVENT);
        return value != null && value instanceof Boolean && (boolean) value;
    }

    public void setBubleEvent(boolean bubleEvent) {
        setAdditionalInfo(PROPERTY_BUBLE_EVENT,bubleEvent);
    }

}

package ua.pp.fairwind.communications.propertyes.groups;


import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Сергей on 26.06.2015.
 */
public class StaticGroupProperty extends AbstractProperty {
    final private Map<String,AbstractProperty> properties;
    final private Map<UUID,AbstractProperty> propertiesUUID;

    public AbstractProperty get(String name){
        return properties.get(name);
    };

    public AbstractProperty getByUUID(String name){
        return propertiesUUID.get(name);
    };

    public StaticGroupProperty(String name,MessageSubSystem centralSystem,AbstractProperty... propertyList) {
        super(name,centralSystem);
        properties =new ConcurrentHashMap<>();
        propertiesUUID =new ConcurrentHashMap<>();
        addPropertyies(propertyList);
    }

    public StaticGroupProperty(String name, String description,MessageSubSystem centralSystem,AbstractProperty... propertyList) {
        super(name, description,centralSystem);
        properties =new ConcurrentHashMap<>();
        propertiesUUID =new ConcurrentHashMap<>();
        addPropertyies(propertyList);
    }

    public StaticGroupProperty(String name, String uuid, String description,MessageSubSystem centralSystem,AbstractProperty... propertyList) {
        super(name, uuid, description,centralSystem);
        properties =new ConcurrentHashMap<>();
        propertiesUUID =new ConcurrentHashMap<>();
        addPropertyies(propertyList);
    }

    protected void addPropertyies(final AbstractProperty... propertyList){
        if(propertyList!=null && propertyList.length>0){
            for(AbstractProperty property:propertyList){
                properties.put(property.getName(), property);
                propertiesUUID.put(property.getUUID(), property);
            }
        }
    }

    protected void addProperty(final AbstractProperty property){
        if(property!=null){
            properties.put(property.getName(), property);
            propertiesUUID.put(property.getUUID(), property);
        }
    }

    protected void remove(final AbstractProperty property){
        if(property!=null) {
            properties.remove(property.getName());
            propertiesUUID.remove(property.getUUIDString());
        }
    }

    @Override
    protected void bindPropertyForRead(AbstractProperty property) {

    }

    @Override
    protected void bindPropertyForWrite(AbstractProperty property) {

    }

    @Override
    protected void unbindPropertyForRead() {

    }

    @Override
    protected void unbindPropertyForWrite() {

    }

    @Override
    protected void writeBindingOpearion(AbstractProperty property) {

    }
}

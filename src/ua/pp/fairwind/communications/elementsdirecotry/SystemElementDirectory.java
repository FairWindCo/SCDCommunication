package ua.pp.fairwind.communications.elementsdirecotry;

import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.abstractions.SystemEllement;
import ua.pp.fairwind.communications.devices.DeviceInterface;
import ua.pp.fairwind.communications.lines.LineInterface;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystemMultiDipatch;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Сергей on 01.09.2015.
 */
public class SystemElementDirectory extends SystemEllement{
    final private ConcurrentHashMap<UUID,ElementInterface> elements=new ConcurrentHashMap<>();
    final private ConcurrentHashMap<UUID,AbstractProperty> propertyes=new ConcurrentHashMap<>();
    final private ConcurrentHashMap<UUID,DeviceInterface> devices=new ConcurrentHashMap<>();
    final private ConcurrentHashMap<UUID,LineInterface> lines=new ConcurrentHashMap<>();
    final private ConcurrentHashMap<UUID,SystemElementDirectory> subsystems=new ConcurrentHashMap<>();

    public SystemElementDirectory(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public SystemElementDirectory(String name, String uuid, String description) {
        super(name, uuid, description, new MessageSubSystemMultiDipatch());
    }

    public SystemElementDirectory(String name, String description) {
        super(name, null, description, new MessageSubSystemMultiDipatch());
    }

    public MessageSubSystem getChileMessageSubsystems(){
        return centralSystem.getNewChild();
    }

    public void addElemnt(ElementInterface element){
        elements.put(element.getUUID(), element);
        if(element instanceof AbstractProperty){
            propertyes.put(element.getUUID(), (AbstractProperty)element);
        } else if(element instanceof DeviceInterface){
            devices.put(element.getUUID(),(DeviceInterface)element);
        } else if(element instanceof LineInterface){
            lines.put(element.getUUID(),(LineInterface)element);
        } else if(element instanceof SystemElementDirectory) {
            subsystems.put(element.getUUID(), (SystemElementDirectory) element);
        }
    }

    public void addElemnt(AbstractProperty element){
        elements.put(element.getUUID(), element);
        propertyes.put(element.getUUID(), element);
    }

    public void addElemnts(AbstractProperty... elements){
        for(AbstractProperty property:elements){
            addElemnt(property);
        }
    }

    public void addElemnts(List<AbstractProperty> elements){
        for(AbstractProperty property:elements){
            addElemnt(property);
        }
    }

    public void addElemnt(DeviceInterface element){
        elements.put(element.getUUID(), element);
        devices.put(element.getUUID(), element);
    }

    public void addElemnt(LineInterface element){
        elements.put(element.getUUID(), element);
        lines.put(element.getUUID(), element);
    }

    public void addElemnt(SystemElementDirectory element){
        elements.put(element.getUUID(), element);
        subsystems.put(element.getUUID(), element);
    }


    public void addremoveElemnt(ElementInterface element){
        elements.remove(element.getUUID());
        if(element instanceof AbstractProperty){
            propertyes.remove(element.getUUID());
        } else if(element instanceof DeviceInterface){
            devices.remove(element.getUUID());
        } else if(element instanceof LineInterface){
            lines.remove(element.getUUID());
        } else if(element instanceof SystemElementDirectory){
            subsystems.remove(element.getUUID());
        }
    }


    public ElementInterface getElement(String uuid){
        UUID ui=UUID.fromString(uuid);
        if(ui!=null){
            return elements.get(ui);
        } else {
            return null;
        }
    }

    public DeviceInterface getDevice(String uuid){
        UUID ui=UUID.fromString(uuid);
        if(ui!=null){
            return devices.get(ui);
        } else {
            return null;
        }
    }

    public LineInterface getLine(String uuid){
        UUID ui=UUID.fromString(uuid);
        if(ui!=null){
            return lines.get(ui);
        } else {
            return null;
        }
    }

    public AbstractProperty getPropertyes(String uuid){
        UUID ui=UUID.fromString(uuid);
        if(ui!=null){
            return propertyes.get(ui);
        } else {
            return null;
        }
    }

    public SystemElementDirectory getSubSytem(String uuid){
        UUID ui=UUID.fromString(uuid);
        if(ui!=null){
            return subsystems.get(ui);
        } else {
            return null;
        }
    }

    public void destroyAll(){
        elements.forEachValue(100,(elem)->elem.destroy());
        lines.clear();
        devices.clear();
        propertyes.clear();
        elements.clear();
        centralSystem.destroy();
    }


}

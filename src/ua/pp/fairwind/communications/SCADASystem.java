package ua.pp.fairwind.communications;

import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.propertyes.event.ElementEventListener;
import ua.pp.fairwind.communications.propertyes.event.EventType;

import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by ������ on 30.06.2015.
 */
public class SCADASystem implements ElementInterface{
    final private String name;
    private String description;
    final private UUID uuid;
    final CopyOnWriteArrayList<ElementEventListener> eventDispatcher=new CopyOnWriteArrayList<>();

    public SCADASystem(String name) {
        if(name==null || name.length()==0) throw new IllegalArgumentException("Name cannot be NULL or empty!");
        this.name = name;
        uuid=UUID.randomUUID();
    }

    public SCADASystem(String name, String description) {
        if(name==null || name.length()==0) throw new IllegalArgumentException("Name cannot be NULL or empty!");
        this.name = name;
        this.description = description;
        uuid=UUID.randomUUID();
    }

    public SCADASystem(String name, String uuid, String description) {
        this.name = name;
        this.description = description;
        if(name==null || name.length()==0) throw new IllegalArgumentException("Name cannot be NULL or empty!");
        UUID uid = UUID.fromString(uuid);
        if (uid == null) {
            this.uuid = UUID.randomUUID();
        } else {
            this.uuid = uid;
        }
    }

    public String getName() {
        return name;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getUUIDString() {
        return uuid.toString();
    }

    public String getDescription() {
        return description;
    }


    private void fireEvent(EventType type,Object param){
       for(ElementEventListener listener:eventDispatcher){
            listener.elementEvent(this,type,param);
       }
    }

    @Override
    public void addEventListener(ElementEventListener listener) {
        if(listener!=null){
            eventDispatcher.add(listener);
        }
    }

    @Override
    public void removeEventListener(ElementEventListener listener) {
        if(listener!=null){
            eventDispatcher.remove(listener);
        }
    }

    @Override
    public void destroy() {
        eventDispatcher.clear();
    }

    @Override
    public String getHardwareName() {
        return this.getClass().getSimpleName()+"("+name+","+uuid.toString()+")";
    }
}

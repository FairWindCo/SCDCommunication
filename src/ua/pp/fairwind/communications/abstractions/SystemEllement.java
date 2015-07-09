package ua.pp.fairwind.communications.abstractions;

import ua.pp.fairwind.communications.propertyes.event.ElementEventListener;
import ua.pp.fairwind.communications.propertyes.event.EventType;

import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by FairWindCo on 30.06.2015.
 */
public class SystemEllement implements ElementInterface{
    final private String name;
    final private UUID uuid;
    final private MessageSubSystem centralSystem;
    final CopyOnWriteArrayList<ElementEventListener> eventDispatcher=new CopyOnWriteArrayList<>();
    final private String description;
    protected volatile boolean eventactive=true;

    public SystemEllement(String name,MessageSubSystem centralSystem) {
        if(name==null || name.length()==0) throw new IllegalArgumentException("Name cannot be NULL or empty!");
        this.name = name;
        this.uuid=UUID.randomUUID();
        this.centralSystem=centralSystem;
        this.description="";
    }

    public SystemEllement(String name, String description,MessageSubSystem centralSystem) {
        if(name==null || name.length()==0) throw new IllegalArgumentException("Name cannot be NULL or empty!");
        this.name = name;
        this.description = description;
        this.uuid=UUID.randomUUID();
        this.centralSystem=centralSystem;
    }


    public SystemEllement(String name, String uuid,String description,MessageSubSystem centralSystem) {
        this.name = name;
        this.description = description;
        this.centralSystem=centralSystem;
        if(name==null || name.length()==0) throw new IllegalArgumentException("Name cannot be NULL or empty!");
        UUID uid=null;
        if(uuid!=null) uid = UUID.fromString(uuid);
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

    protected void fireEvent(EventType type,Object param){
        if(centralSystem!=null) centralSystem.sendMessage(this,param);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SystemEllement that = (SystemEllement) o;

        return !(uuid != null ? !uuid.equals(that.uuid) : that.uuid != null);

    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }
}

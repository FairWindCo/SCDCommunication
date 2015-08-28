package ua.pp.fairwind.communications.abstractions;

import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystemSimple;
import ua.pp.fairwind.communications.propertyes.event.ElementEventListener;
import ua.pp.fairwind.communications.propertyes.event.EventType;

import java.util.UUID;

/**
 * Created by FairWindCo on 30.06.2015.
 */
public class SystemEllement implements ElementInterface{
    final private String name;
    final private UUID uuid;
    final protected MessageSubSystem centralSystem;
    final private String description;
    protected volatile boolean eventactive=true;

    public SystemEllement(String name,MessageSubSystem centralSystem) {
        if(name==null || name.length()==0) throw new IllegalArgumentException("Name cannot be NULL or empty!");
        this.name = name;
        this.uuid=UUID.randomUUID();
        this.centralSystem=centralSystem!=null?centralSystem.getNewChild():new MessageSubSystemSimple();
        this.description="";
    }

    public SystemEllement(String name, String description,MessageSubSystem centralSystem) {
        if(name==null || name.length()==0) throw new IllegalArgumentException("Name cannot be NULL or empty!");
        this.name = name;
        this.description = description;
        this.uuid=UUID.randomUUID();
        this.centralSystem=centralSystem!=null?centralSystem.getNewChild():new MessageSubSystemSimple();
    }


    public SystemEllement(String name, String uuid,String description,MessageSubSystem centralSystem) {
        this.name = name;
        this.description = description;
        this.centralSystem=centralSystem!=null?centralSystem.getNewChild():new MessageSubSystemSimple();
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
        centralSystem.fireEvent(this,type,param);
    }

    @Override
    public void addEventListener(ElementEventListener listener) {
        centralSystem.addEventListener(listener);
    }

    @Override
    public void removeEventListener(ElementEventListener listener) {
        centralSystem.removeEventListener(listener);
    }

    @Override
    public void destroy() {
        centralSystem.clear();
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

    @Override
    public String getHardwareName() {
        return this.getClass().getSimpleName()+"("+name+","+uuid.toString()+")";
    }
}

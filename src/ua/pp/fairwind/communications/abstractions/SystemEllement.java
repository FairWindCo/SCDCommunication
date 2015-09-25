package ua.pp.fairwind.communications.abstractions;

import ua.pp.fairwind.communications.internatianalisation.I18N;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.messagesystems.MessageSystemManager;
import ua.pp.fairwind.communications.messagesystems.event.ElementEventListener;
import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.messagesystems.event.EventType;

import java.util.UUID;

/**
 * Created by FairWindCo on 30.06.2015.
 */
public abstract class SystemEllement implements ElementInterface{
    final private String name;
    final private UUID uuid;
    final protected MessageSubSystem centralSystem;
    final private String description;
    protected volatile boolean eventactive=true;
    final private String codename;

    protected SystemEllement(String codename) {
        if(codename==null || codename.length()==0) throw new IllegalArgumentException("Name cannot be NULL or empty!");
        this.codename=codename;
        this.name = localizeName(codename);
        this.description = localizeDescription(codename);
        this.uuid=I18N.getUUIDFromCodeNAme(codename);
        this.centralSystem= MessageSystemManager.getElementMessageSystem(uuid);
    }


    protected SystemEllement(String codename, String uuid) {
        this.codename=codename;
        this.name = localizeName(codename);
        this.description = localizeDescription(codename);
        if(codename==null || codename.length()==0) throw new IllegalArgumentException("Name cannot be NULL or empty!");
        this.uuid=I18N.getUUIDFromCodeNAme(uuid,codename);
        this.centralSystem= MessageSystemManager.getElementMessageSystem(this.uuid);
    }

    private String localizeName(String key){
        String str=I18N.getLocalizedString(key+".name");
        if(str==null || str.isEmpty()) return key;
        return str;
    }

    private String localizeDescription(String key){
        return I18N.getLocalizedString(key+".description");
    }

    public static String localizeError(String goupName,String key){
        return I18N.getLocalizedString(goupName+'.'+key+".error");
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
        if(eventactive)centralSystem.fireEvent(this,type,param);
    }

    protected void fireEvent(EventType type,Object param,Event parent){
        if(eventactive)centralSystem.fireEvent(this,type,param,parent);
    }

    @Override
    public void addEventListener(ElementEventListener listener) {
        centralSystem.addEventListener(listener);
    }

    @Override
    public void addEventListener(ElementEventListener listener, EventType... recivedEventsTypes) {
        centralSystem.addEventListener(listener,recivedEventsTypes);
    }

    @Override
    public void addEventListener(ElementEventListener listener,UUID ignore) {
        centralSystem.addEventListener(listener,ignore);
    }

    @Override
    public void addEventListener(ElementEventListener listener,UUID ignore, EventType... recivedEventsTypes) {
        centralSystem.addEventListener(listener,ignore,recivedEventsTypes);
    }

    @Override
    public void addEventListener(ElementEventListener listener,UUID ignore,Object processotParam) {
        centralSystem.addEventListener(listener,ignore);
    }

    @Override
    public void addEventListener(ElementEventListener listener,UUID ignore,Object processotParam, EventType... recivedEventsTypes) {
        centralSystem.addEventListener(listener,ignore,recivedEventsTypes);
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

    @Override
    public void setEnabled(boolean enabled) {
        eventactive=enabled;
    }

    @Override
    public boolean isEnabled() {
        return eventactive;
    }

    public String getCodename() {
        return codename;
    }
}

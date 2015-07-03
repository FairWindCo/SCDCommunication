package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.abstractions.SystemEllement;
import ua.pp.fairwind.communications.propertyes.event.EventType;

/**
 * Created by Сергей on 26.06.2015.
 */
public abstract class AbstractProperty extends SystemEllement{
    public AbstractProperty(String name,MessageSubSystem centralSystem) {
        super(name,centralSystem);
    }

    public AbstractProperty(String name, String description,MessageSubSystem centralSystem) {
        super(name, description,centralSystem);
    }

    public AbstractProperty(String name, String uuid, String description,MessageSubSystem centralSystem) {
        super(name, uuid, description,centralSystem);
    }

    public void readVelue(){
        fireEvent(EventType.NEED_READ_VALUE, null);
    }

    public void writeVelue(){
        fireEvent(EventType.NEED_WRITE_VALUE, null);
    }
}

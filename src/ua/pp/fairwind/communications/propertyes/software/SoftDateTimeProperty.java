package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;

import java.util.Date;

/**
 * Created by Сергей on 06.07.2015.
 */
public class SoftDateTimeProperty extends ValueProperty<Date> {
    public SoftDateTimeProperty(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public SoftDateTimeProperty(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public SoftDateTimeProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public SoftDateTimeProperty(String name, MessageSubSystem centralSystem, Date value) {
        super(name, centralSystem, value);
    }

    public SoftDateTimeProperty(String name, String description, MessageSubSystem centralSystem, Date value) {
        super(name, description, centralSystem, value);
    }

    public SoftDateTimeProperty(String name, String uuid, String description, MessageSubSystem centralSystem, Date value) {
        super(name, uuid, description, centralSystem, value);
    }
}

package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.groups.StaticGroupProperty;

/**
 * Created by Сергей on 06.07.2015.
 */
public class SoftByteArrayProperty extends StaticGroupProperty {
    public SoftByteArrayProperty(String name, MessageSubSystem centralSystem, AbstractProperty... propertyList) {
        super(name, centralSystem, propertyList);
    }

    public SoftByteArrayProperty(String name, String description, MessageSubSystem centralSystem, AbstractProperty... propertyList) {
        super(name, description, centralSystem, propertyList);
    }

    public SoftByteArrayProperty(String name, String uuid, String description, MessageSubSystem centralSystem, AbstractProperty... propertyList) {
        super(name, uuid, description, centralSystem, propertyList);
    }
}

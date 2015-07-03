package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractNumberProperty;

/**
 * Created by Сергей on 01.07.2015.
 */
public class SoftIntegerProperty extends AbstractNumberProperty<Integer> {
    public SoftIntegerProperty(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public SoftIntegerProperty(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public SoftIntegerProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    protected Integer convertFromNumber(Number value){
        if(value!=null) return value.intValue();return null;
    }
}

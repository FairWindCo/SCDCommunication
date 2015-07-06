package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;

/**
 * Created by Сергей on 06.07.2015.
 */
public class SoftBoolProperty extends ValueProperty<Boolean> {
    public SoftBoolProperty(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public SoftBoolProperty(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public SoftBoolProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public SoftBoolProperty(String name, MessageSubSystem centralSystem, Boolean value) {
        super(name, centralSystem, value);
    }

    public SoftBoolProperty(String name, String description, MessageSubSystem centralSystem, Boolean value) {
        super(name, description, centralSystem, value);
    }

    public SoftBoolProperty(String name, String uuid, String description, MessageSubSystem centralSystem, Boolean value) {
        super(name, uuid, description, centralSystem, value);
    }
}

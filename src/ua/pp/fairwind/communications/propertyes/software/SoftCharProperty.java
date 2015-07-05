package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;

/**
 * Created by Сергей on 04.07.2015.
 */
public class SoftCharProperty extends ValueProperty<Character> {
    public SoftCharProperty(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public SoftCharProperty(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public SoftCharProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public SoftCharProperty(String name, MessageSubSystem centralSystem, Character value) {
        super(name, centralSystem, value);
    }

    public SoftCharProperty(String name, String description, MessageSubSystem centralSystem, Character value) {
        super(name, description, centralSystem, value);
    }

    public SoftCharProperty(String name, String uuid, String description, MessageSubSystem centralSystem, Character value) {
        super(name, uuid, description, centralSystem, value);
    }
}

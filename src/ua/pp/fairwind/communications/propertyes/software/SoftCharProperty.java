package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;

/**
 * Created by ������ on 04.07.2015.
 */
public class SoftCharProperty extends ValueProperty<Character> {

    public SoftCharProperty(String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly) {
        super(name, uuid, description, centralSystem, readonly, writeonly);
    }

    public SoftCharProperty(String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly, Character value) {
        super(name, uuid, description, centralSystem, readonly, writeonly, value);
    }

    public SoftCharProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public SoftCharProperty(String name, String uuid, String description, MessageSubSystem centralSystem, Character value) {
        super(name, uuid, description, centralSystem, value);
    }

    public SoftCharProperty(String name, String uuid, String description) {
        super(name, uuid, description);
    }

    public SoftCharProperty(String name, String uuid, String description, Character value) {
        super(name, uuid, description, value);
    }

    public SoftCharProperty(String name, String description) {
        super(name, description);
    }

    public SoftCharProperty(String name, String description, Character value) {
        super(name, description, value);
    }

    public SoftCharProperty(String name) {
        super(name);
    }

    public SoftCharProperty(String name, Character value) {
        super(name, value);
    }
}

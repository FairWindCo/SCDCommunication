package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;

/**
 * Created by ������ on 04.07.2015.
 */
public class SoftCharProperty extends ValueProperty<Character> {

    public SoftCharProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid, softOperationType);
    }

    public SoftCharProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType, Character value) {
        super(name, uuid, softOperationType, value);
    }

    public SoftCharProperty(String name, String uuid) {
        super(name, uuid);
    }

    public SoftCharProperty(String name, String uuid, Character value) {
        super(name, uuid, value);
    }

    public SoftCharProperty(String name) {
        super(name);
    }

    public SoftCharProperty(String name, Character value) {
        super(name, value);
    }

    public SoftCharProperty(String name, SOFT_OPERATION_TYPE softOperationType) {
        super(name, softOperationType);
    }

    public SoftCharProperty(String name, SOFT_OPERATION_TYPE softOperationType, Character value) {
        super(name, softOperationType, value);
    }
}

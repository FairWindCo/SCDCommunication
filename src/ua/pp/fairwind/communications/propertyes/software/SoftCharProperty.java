package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.elementsdirecotry.SystemElementDirectory;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;

/**
 * Created by ������ on 04.07.2015.
 */
public class SoftCharProperty extends ValueProperty<Character> {

    public SoftCharProperty(String name, String uuid, String description, MessageSubSystem centralSystem, SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid, description, centralSystem, softOperationType);
    }

    public SoftCharProperty(String name, String uuid, String description, MessageSubSystem centralSystem, SOFT_OPERATION_TYPE softOperationType, Character value) {
        super(name, uuid, description, centralSystem, softOperationType, value);
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

    public static SoftCharProperty createShortProperty(String name, String uuid, String description, SystemElementDirectory parentSystem,SOFT_OPERATION_TYPE softOperationType, Character value){
        SoftCharProperty property=new SoftCharProperty(name,uuid,description,parentSystem.getChileMessageSubsystems(),softOperationType,value);
        parentSystem.addElemnt(property);
        return property;
    }

    public static SoftCharProperty createShortProperty(String name, String uuid, String description, SystemElementDirectory parentSystem,SOFT_OPERATION_TYPE softOperationType){
        SoftCharProperty property=new SoftCharProperty(name,uuid,description,parentSystem.getChileMessageSubsystems(),softOperationType);
        parentSystem.addElemnt(property);
        return property;
    }

    public static SoftCharProperty createShortProperty(String name, String uuid, String description, SystemElementDirectory parentSystem){
        SoftCharProperty property=new SoftCharProperty(name,uuid,description,parentSystem.getChileMessageSubsystems());
        parentSystem.addElemnt(property);
        return property;
    }

    public static SoftCharProperty createShortProperty(String name, String description, SystemElementDirectory parentSystem){
        SoftCharProperty property=new SoftCharProperty(name,null,description,parentSystem.getChileMessageSubsystems());
        parentSystem.addElemnt(property);
        return property;
    }
}

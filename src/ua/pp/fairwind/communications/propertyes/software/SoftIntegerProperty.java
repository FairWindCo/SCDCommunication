package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.elementsdirecotry.SystemElementDirectory;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;

/**
 * Created by FairWindCo on 07.07.2015
 */
public class SoftIntegerProperty extends NumberProperty<Integer> {
    public SoftIntegerProperty(String name, String uuid, String description, MessageSubSystem centralSystem, SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid, description, centralSystem, softOperationType);
    }

    public SoftIntegerProperty(String name, String uuid, String description, MessageSubSystem centralSystem, SOFT_OPERATION_TYPE softOperationType, Integer value) {
        super(name, uuid, description, centralSystem, softOperationType, value);
    }

    public SoftIntegerProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public SoftIntegerProperty(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public SoftIntegerProperty(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public SoftIntegerProperty(String name, String uuid, String description) {
        super(name, uuid, description);
    }

    public SoftIntegerProperty(String name, String description) {
        super(name, description);
    }

    public SoftIntegerProperty(String name) {
        super(name);
    }

    public SoftIntegerProperty(String name, String uuid, String description, MessageSubSystem centralSystem, Integer value) {
        super(name, uuid, description, centralSystem, value);
    }

    public SoftIntegerProperty(String name, String description, MessageSubSystem centralSystem, Integer value) {
        super(name, description, centralSystem, value);
    }

    public SoftIntegerProperty(String name, MessageSubSystem centralSystem, Integer value) {
        super(name, centralSystem, value);
    }

    public SoftIntegerProperty(String name, String uuid, String description, Integer value) {
        super(name, uuid, description, value);
    }

    public SoftIntegerProperty(String name, String description, Integer value) {
        super(name, description, value);
    }

    public SoftIntegerProperty(String name, Integer value) {
        super(name, value);
    }

    protected Integer convertFromNumber(Number value){
        if(value!=null) return value.intValue();return null;
    }

    @Override
    protected Integer convertFromString(String value, int radix) {
        if(value!=null) return Integer.parseInt(value,radix);
        return null;
    }

    public static SoftIntegerProperty createShortProperty(String name, String uuid, String description, SystemElementDirectory parentSystem,SOFT_OPERATION_TYPE softOperationType, Integer value){
        SoftIntegerProperty property=new SoftIntegerProperty(name,uuid,description,parentSystem.getChileMessageSubsystems(),softOperationType,value);
        parentSystem.addElemnt(property);
        return property;
    }

    public static SoftIntegerProperty createShortProperty(String name, String uuid, String description, SystemElementDirectory parentSystem,SOFT_OPERATION_TYPE softOperationType){
        SoftIntegerProperty property=new SoftIntegerProperty(name,uuid,description,parentSystem.getChileMessageSubsystems(),softOperationType);
        parentSystem.addElemnt(property);
        return property;
    }

    public static SoftIntegerProperty createShortProperty(String name, String uuid, String description, SystemElementDirectory parentSystem){
        SoftIntegerProperty property=new SoftIntegerProperty(name,uuid,description,parentSystem.getChileMessageSubsystems());
        parentSystem.addElemnt(property);
        return property;
    }

    public static SoftIntegerProperty createShortProperty(String name, String description, SystemElementDirectory parentSystem){
        SoftIntegerProperty property=new SoftIntegerProperty(name,description,parentSystem.getChileMessageSubsystems());
        parentSystem.addElemnt(property);
        return property;
    }
}

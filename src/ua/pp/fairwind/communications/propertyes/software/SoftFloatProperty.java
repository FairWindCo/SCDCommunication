package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.elementsdirecotry.SystemElementDirectory;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;

/**
 * Created by FairWindCo on 07.07.2015
 */
public class SoftFloatProperty extends NumberProperty<Float> {
    public SoftFloatProperty(String name, String uuid, String description, MessageSubSystem centralSystem, SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid, description, centralSystem, softOperationType);
    }

    public SoftFloatProperty(String name, String uuid, String description, MessageSubSystem centralSystem, SOFT_OPERATION_TYPE softOperationType, Float value) {
        super(name, uuid, description, centralSystem, softOperationType, value);
    }

    public SoftFloatProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public SoftFloatProperty(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public SoftFloatProperty(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public SoftFloatProperty(String name, String uuid, String description) {
        super(name, uuid, description);
    }

    public SoftFloatProperty(String name, String description) {
        super(name, description);
    }

    public SoftFloatProperty(String name) {
        super(name);
    }

    public SoftFloatProperty(String name, String uuid, String description, MessageSubSystem centralSystem, Float value) {
        super(name, uuid, description, centralSystem, value);
    }

    public SoftFloatProperty(String name, String description, MessageSubSystem centralSystem, Float value) {
        super(name, description, centralSystem, value);
    }

    public SoftFloatProperty(String name, MessageSubSystem centralSystem, Float value) {
        super(name, centralSystem, value);
    }

    public SoftFloatProperty(String name, String uuid, String description, Float value) {
        super(name, uuid, description, value);
    }

    public SoftFloatProperty(String name, String description, Float value) {
        super(name, description, value);
    }

    public SoftFloatProperty(String name, Float value) {
        super(name, value);
    }

    protected Float convertFromNumber(Number value){
        if(value!=null) return value.floatValue();return null;
    }

    @Override
    protected Float convertFromString(String value, int radix) {
        if(value!=null) return Float.parseFloat(value);
        return null;
    }

    public static SoftFloatProperty createShortProperty(String name, String uuid, String description, SystemElementDirectory parentSystem,SOFT_OPERATION_TYPE softOperationType, Float value){
        SoftFloatProperty property=new SoftFloatProperty(name,uuid,description,parentSystem.getChileMessageSubsystems(),softOperationType,value);
        parentSystem.addElemnt(property);
        return property;
    }

    public static SoftFloatProperty createShortProperty(String name, String uuid, String description, SystemElementDirectory parentSystem,SOFT_OPERATION_TYPE softOperationType){
        SoftFloatProperty property=new SoftFloatProperty(name,uuid,description,parentSystem.getChileMessageSubsystems(),softOperationType);
        parentSystem.addElemnt(property);
        return property;
    }

    public static SoftFloatProperty createShortProperty(String name, String uuid, String description, SystemElementDirectory parentSystem){
        SoftFloatProperty property=new SoftFloatProperty(name,uuid,description,parentSystem.getChileMessageSubsystems());
        parentSystem.addElemnt(property);
        return property;
    }

    public static SoftFloatProperty createShortProperty(String name, String description, SystemElementDirectory parentSystem){
        SoftFloatProperty property=new SoftFloatProperty(name,description,parentSystem.getChileMessageSubsystems());
        parentSystem.addElemnt(property);
        return property;
    }
}

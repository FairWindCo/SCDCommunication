package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.elementsdirecotry.SystemElementDirectory;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;

import java.math.BigInteger;

/**
 * Created by Сергей on 07.07.2015.
 */
public class SoftBigIntegerProperty extends NumberProperty<BigInteger>{

    public SoftBigIntegerProperty(String name, String uuid, String description, MessageSubSystem centralSystem, SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid, description, centralSystem, softOperationType);
    }

    public SoftBigIntegerProperty(String name, String uuid, String description, MessageSubSystem centralSystem, SOFT_OPERATION_TYPE softOperationType, BigInteger value) {
        super(name, uuid, description, centralSystem, softOperationType, value);
    }

    public SoftBigIntegerProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public SoftBigIntegerProperty(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public SoftBigIntegerProperty(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public SoftBigIntegerProperty(String name, String uuid, String description) {
        super(name, uuid, description);
    }

    public SoftBigIntegerProperty(String name, String description) {
        super(name, description);
    }

    public SoftBigIntegerProperty(String name) {
        super(name);
    }

    public SoftBigIntegerProperty(String name, String uuid, String description, MessageSubSystem centralSystem, BigInteger value) {
        super(name, uuid, description, centralSystem, value);
    }

    public SoftBigIntegerProperty(String name, String description, MessageSubSystem centralSystem, BigInteger value) {
        super(name, description, centralSystem, value);
    }

    public SoftBigIntegerProperty(String name, MessageSubSystem centralSystem, BigInteger value) {
        super(name, centralSystem, value);
    }

    public SoftBigIntegerProperty(String name, String uuid, String description, BigInteger value) {
        super(name, uuid, description, value);
    }

    public SoftBigIntegerProperty(String name, String description, BigInteger value) {
        super(name, description, value);
    }

    public SoftBigIntegerProperty(String name, BigInteger value) {
        super(name, value);
    }

    @Override
    protected BigInteger convertFromNumber(Number value) {
        if(value!=null) {
            return new BigInteger(value.toString());
        } else {
            return null;
        }
    }

    @Override
    protected BigInteger convertFromString(String value, int radix) {
        if(value!=null) {
            return new BigInteger(value, radix);
        } else {
            return null;
        }
    }

    public static SoftBigIntegerProperty createShortProperty(String name, String uuid, String description, SystemElementDirectory parentSystem,SOFT_OPERATION_TYPE softOperationType, BigInteger value){
        SoftBigIntegerProperty property=new SoftBigIntegerProperty(name,uuid,description,parentSystem.getChileMessageSubsystems(),softOperationType,value);
        parentSystem.addElemnt(property);
        return property;
    }

    public static SoftBigIntegerProperty createShortProperty(String name, String uuid, String description, SystemElementDirectory parentSystem,SOFT_OPERATION_TYPE softOperationType){
        SoftBigIntegerProperty property=new SoftBigIntegerProperty(name,uuid,description,parentSystem.getChileMessageSubsystems(),softOperationType);
        parentSystem.addElemnt(property);
        return property;
    }

    public static SoftBigIntegerProperty createShortProperty(String name, String uuid, String description, SystemElementDirectory parentSystem){
        SoftBigIntegerProperty property=new SoftBigIntegerProperty(name,uuid,description,parentSystem.getChileMessageSubsystems());
        parentSystem.addElemnt(property);
        return property;
    }

    public static SoftBigIntegerProperty createShortProperty(String name, String description, SystemElementDirectory parentSystem){
        SoftBigIntegerProperty property=new SoftBigIntegerProperty(name,description,parentSystem.getChileMessageSubsystems());
        parentSystem.addElemnt(property);
        return property;
    }
}

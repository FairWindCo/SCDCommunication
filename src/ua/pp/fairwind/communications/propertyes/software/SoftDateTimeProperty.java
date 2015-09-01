package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.elementsdirecotry.SystemElementDirectory;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.DatePropertyInterface;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.StringPropertyInterface;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;

import java.util.Date;

/**
 * Created by ������ on 06.07.2015.
 */
public class SoftDateTimeProperty extends ValueProperty<Date> implements DatePropertyInterface{
    public SoftDateTimeProperty(String name, String uuid, String description, MessageSubSystem centralSystem, SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid, description, centralSystem, softOperationType);
    }

    public SoftDateTimeProperty(String name, String uuid, String description, MessageSubSystem centralSystem, SOFT_OPERATION_TYPE softOperationType, Date value) {
        super(name, uuid, description, centralSystem, softOperationType, value);
    }

    public SoftDateTimeProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public SoftDateTimeProperty(String name, String uuid, String description, MessageSubSystem centralSystem, Date value) {
        super(name, uuid, description, centralSystem, value);
    }

    public SoftDateTimeProperty(String name, String uuid, String description) {
        super(name, uuid, description);
    }

    public SoftDateTimeProperty(String name, String uuid, String description, Date value) {
        super(name, uuid, description, value);
    }

    public SoftDateTimeProperty(String name, String description) {
        super(name, description);
    }

    public SoftDateTimeProperty(String name, String description, Date value) {
        super(name, description, value);
    }

    public SoftDateTimeProperty(String name) {
        super(name);
    }

    public SoftDateTimeProperty(String name, Date value) {
        super(name, value);
    }

    @Override
    public void bindReadNumberProperty(NumberProperty<?> property) {

    }

    @Override
    public void bindWriteNumberProperty(NumberProperty<?> property) {

    }

    @Override
    public void unbindWriteNumberProperty() {

    }

    @Override
    public void unbindReadNumberProperty() {

    }

    @Override
    public void bindReadStringProperty(StringPropertyInterface property) {

    }

    @Override
    public void bindWriteStringProperty(StringPropertyInterface property) {

    }

    @Override
    public void bindReadStringProperty(StringPropertyInterface property, String format) {

    }

    @Override
    public void bindWriteStringProperty(StringPropertyInterface property, String format) {

    }

    @Override
    public void unbindWriteStringProperty() {

    }

    @Override
    public void unbindReadStringProperty() {

    }

    public static SoftDateTimeProperty createShortProperty(String name, String uuid, String description, SystemElementDirectory parentSystem,SOFT_OPERATION_TYPE softOperationType, Date value){
        SoftDateTimeProperty property=new SoftDateTimeProperty(name,uuid,description,parentSystem.getChileMessageSubsystems(),softOperationType,value);
        parentSystem.addElemnt(property);
        return property;
    }

    public static SoftDateTimeProperty createShortProperty(String name, String uuid, String description, SystemElementDirectory parentSystem,SOFT_OPERATION_TYPE softOperationType){
        SoftDateTimeProperty property=new SoftDateTimeProperty(name,uuid,description,parentSystem.getChileMessageSubsystems(),softOperationType);
        parentSystem.addElemnt(property);
        return property;
    }

    public static SoftDateTimeProperty createShortProperty(String name, String uuid, String description, SystemElementDirectory parentSystem){
        SoftDateTimeProperty property=new SoftDateTimeProperty(name,uuid,description,parentSystem.getChileMessageSubsystems());
        parentSystem.addElemnt(property);
        return property;
    }

    public static SoftDateTimeProperty createShortProperty(String name, String description, SystemElementDirectory parentSystem){
        SoftDateTimeProperty property=new SoftDateTimeProperty(name,null,description,parentSystem.getChileMessageSubsystems());
        parentSystem.addElemnt(property);
        return property;
    }
}

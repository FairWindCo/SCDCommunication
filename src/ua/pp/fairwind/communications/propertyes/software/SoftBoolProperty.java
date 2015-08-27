package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.BooleanPropertyInterface;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.StringPropertyInterface;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;

/**
 * Created by Сергей on 06.07.2015.
 */
public class SoftBoolProperty extends ValueProperty<Boolean> implements BooleanPropertyInterface{


    public SoftBoolProperty(String name, String uuid, String description, MessageSubSystem centralSystem, SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid, description, centralSystem, softOperationType);
    }

    public SoftBoolProperty(String name, String uuid, String description, MessageSubSystem centralSystem, SOFT_OPERATION_TYPE softOperationType, Boolean value) {
        super(name, uuid, description, centralSystem, softOperationType, value);
    }

    public SoftBoolProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public SoftBoolProperty(String name, String uuid, String description, MessageSubSystem centralSystem, Boolean value) {
        super(name, uuid, description, centralSystem, value);
    }

    public SoftBoolProperty(String name, String uuid, String description) {
        super(name, uuid, description);
    }

    public SoftBoolProperty(String name, String uuid, String description, Boolean value) {
        super(name, uuid, description, value);
    }

    public SoftBoolProperty(String name, String description) {
        super(name, description);
    }

    public SoftBoolProperty(String name, String description, Boolean value) {
        super(name, description, value);
    }

    public SoftBoolProperty(String name) {
        super(name);
    }

    public SoftBoolProperty(String name, Boolean value) {
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
    public void bindReadStringProperty(StringPropertyInterface property, boolean binformat) {

    }

    @Override
    public void bindWriteStringProperty(StringPropertyInterface property, boolean binformat) {

    }

    @Override
    public void unbindWriteStringProperty() {

    }

    @Override
    public void unbindReadStringProperty() {

    }

    public void invertValue(){
        Boolean value=getValue();
        if(value==null){
            setValue(true);
        } else {
            setValue(!value);
        }

    }
}

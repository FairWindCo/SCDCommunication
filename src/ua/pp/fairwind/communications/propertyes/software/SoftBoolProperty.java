package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.propertyes.abstraction.BooleanPropertyInterface;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.StringPropertyInterface;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;

/**
 * Created by Сергей on 06.07.2015.
 */
public class SoftBoolProperty extends ValueProperty<Boolean> implements BooleanPropertyInterface {
    public static final String BIT_NUMBER="BIT_NUMBER";
    public SoftBoolProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid, softOperationType);
    }

    public SoftBoolProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType, Boolean value) {
        super(name, uuid, softOperationType, value);
    }

    public SoftBoolProperty(String name, String uuid) {
        super(name, uuid);
    }

    public SoftBoolProperty(String name, String uuid, Boolean value) {
        super(name, uuid, value);
    }

    public SoftBoolProperty(String name) {
        super(name);
    }

    public SoftBoolProperty(String name, Boolean value) {
        super(name, value);
    }

    public SoftBoolProperty(String name, SOFT_OPERATION_TYPE softOperationType) {
        super(name, softOperationType);
    }

    public SoftBoolProperty(String name, SOFT_OPERATION_TYPE softOperationType, Boolean value) {
        super(name, softOperationType, value);
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

    public void invertValue() {
        Boolean value = getValue();
        if (value == null) {
            setValue(true);
        } else {
            setValue(!value);
        }

    }
}

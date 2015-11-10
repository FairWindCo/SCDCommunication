package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.propertyes.abstraction.DatePropertyInterface;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.StringPropertyInterface;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.markers.DateValueInterface;

import java.util.Date;

/**
 * Created by ������ on 06.07.2015.
 */
public class SoftDateTimeProperty extends ValueProperty<Date> implements DatePropertyInterface,DateValueInterface {
    public SoftDateTimeProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid, softOperationType);
    }

    public SoftDateTimeProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType, Date value) {
        super(name, uuid, softOperationType, value);
    }

    public SoftDateTimeProperty(String name, String uuid) {
        super(name, uuid);
    }

    public SoftDateTimeProperty(String name, String uuid, Date value) {
        super(name, uuid, value);
    }

    public SoftDateTimeProperty(String name) {
        super(name);
    }

    public SoftDateTimeProperty(String name, Date value) {
        super(name, value);
    }

    public SoftDateTimeProperty(String name, SOFT_OPERATION_TYPE softOperationType) {
        super(name, softOperationType);
    }

    public SoftDateTimeProperty(String name, SOFT_OPERATION_TYPE softOperationType, Date value) {
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

}

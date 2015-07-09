package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.DatePropertyInterface;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.StringPropertyInterface;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;

import java.util.Date;

/**
 * Created by ������ on 06.07.2015.
 */
public class SoftDateTimeProperty extends ValueProperty<Date> implements DatePropertyInterface{
    public SoftDateTimeProperty(String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly) {
        super(name, uuid, description, centralSystem, readonly, writeonly);
    }

    public SoftDateTimeProperty(String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly, Date value) {
        super(name, uuid, description, centralSystem, readonly, writeonly, value);
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
}

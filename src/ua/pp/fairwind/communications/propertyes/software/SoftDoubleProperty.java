package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;

/**
 * Created by FairWindCo on 07.07.2015
 */
public class SoftDoubleProperty extends NumberProperty<Double>{
    public SoftDoubleProperty(String name, String uuid, String description, MessageSubSystem centralSystem, SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid, description, centralSystem, softOperationType);
    }

    public SoftDoubleProperty(String name, String uuid, String description, MessageSubSystem centralSystem, SOFT_OPERATION_TYPE softOperationType, Double value) {
        super(name, uuid, description, centralSystem,softOperationType, value);
    }

    public SoftDoubleProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public SoftDoubleProperty(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public SoftDoubleProperty(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public SoftDoubleProperty(String name, String uuid, String description) {
        super(name, uuid, description);
    }

    public SoftDoubleProperty(String name, String description) {
        super(name, description);
    }

    public SoftDoubleProperty(String name) {
        super(name);
    }

    public SoftDoubleProperty(String name, String uuid, String description, MessageSubSystem centralSystem, Double value) {
        super(name, uuid, description, centralSystem, value);
    }

    public SoftDoubleProperty(String name, String description, MessageSubSystem centralSystem, Double value) {
        super(name, description, centralSystem, value);
    }

    public SoftDoubleProperty(String name, MessageSubSystem centralSystem, Double value) {
        super(name, centralSystem, value);
    }

    public SoftDoubleProperty(String name, String uuid, String description, Double value) {
        super(name, uuid, description, value);
    }

    public SoftDoubleProperty(String name, String description, Double value) {
        super(name, description, value);
    }

    public SoftDoubleProperty(String name, Double value) {
        super(name, value);
    }

    @Override
    protected Double convertFromNumber(Number value) {
        if(value!=null) value.doubleValue();
        return null;
    }

    @Override
    protected Double convertFromString(String value, int radix) {
        if(value!=null) return Double.parseDouble(value);
        return null;
    }
}

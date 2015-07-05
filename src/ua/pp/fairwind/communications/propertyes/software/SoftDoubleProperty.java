package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;

/**
 * Created by Сергей on 04.07.2015.
 */
public class SoftDoubleProperty extends NumberProperty<Double>{
    public SoftDoubleProperty(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public SoftDoubleProperty(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public SoftDoubleProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public SoftDoubleProperty(String name, MessageSubSystem centralSystem, Double value) {
        super(name, centralSystem, value);
    }

    public SoftDoubleProperty(String name, String description, MessageSubSystem centralSystem, Double value) {
        super(name, description, centralSystem, value);
    }

    public SoftDoubleProperty(String name, String uuid, String description, MessageSubSystem centralSystem, Double value) {
        super(name, uuid, description, centralSystem, value);
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

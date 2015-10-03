package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;

/**
 * Created by FairWindCo on 07.07.2015
 */
public class SoftDoubleProperty extends NumberProperty<Double> {
    public SoftDoubleProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid, softOperationType);
    }

    public SoftDoubleProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType, Double value) {
        super(name, uuid, softOperationType, value);
    }

    public SoftDoubleProperty(String name, SOFT_OPERATION_TYPE softOperationType) {
        super(name, softOperationType);
    }

    public SoftDoubleProperty(String name, SOFT_OPERATION_TYPE softOperationType, Double value) {
        super(name, softOperationType, value);
    }

    public SoftDoubleProperty(String name, String uuid) {
        super(name, uuid);
    }

    public SoftDoubleProperty(String name) {
        super(name);
    }

    public SoftDoubleProperty(String name, String uuid, Double value) {
        super(name, uuid, value);
    }

    public SoftDoubleProperty(String name, Double value) {
        super(name, value);
    }

    @Override
    protected Double convertFromNumber(Number value) {
        if (value != null) value.doubleValue();
        return null;
    }

    @Override
    protected Double convertFromString(String value, int radix) {
        if (value != null) return Double.parseDouble(value);
        return null;
    }
}

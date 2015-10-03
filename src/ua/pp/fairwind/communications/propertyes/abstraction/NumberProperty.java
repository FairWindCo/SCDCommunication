package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.messagesystems.event.EventType;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * Created by ������ on 01.07.2015.
 */
public abstract class NumberProperty<M extends Number & Comparable<? super M>> extends ValueProperty<M> implements NumberPropertyInterface<M>, ValuePropertyInterface<M> {

    public NumberProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid, softOperationType);
    }

    public NumberProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType, M value) {
        super(name, uuid, softOperationType, value);
    }

    public NumberProperty(String name, SOFT_OPERATION_TYPE softOperationType) {
        super(name, null, softOperationType);
    }

    public NumberProperty(String name, SOFT_OPERATION_TYPE softOperationType, M value) {
        super(name, null, softOperationType, value);
    }


    public NumberProperty(String name, String uuid) {
        this(name, uuid, SOFT_OPERATION_TYPE.READ_WRITE);
    }

    public NumberProperty(String name) {
        this(name, null, SOFT_OPERATION_TYPE.READ_WRITE);
    }


    public NumberProperty(String name, String uuid, M value) {
        this(name, uuid, SOFT_OPERATION_TYPE.READ_WRITE, value);
    }

    public NumberProperty(String name, M value) {
        this(name, null, SOFT_OPERATION_TYPE.READ_WRITE, value);
    }

    @Override
    public void bindReadNumberProperty(NumberProperty<? extends Number> property) {
        bindPropertyForRead(property, null);
    }

    @Override
    public void bindWriteNumberProperty(NumberProperty<? extends Number> property) {
        bindPropertyForWrite(property, null);
    }

    abstract protected M convertFromNumber(Number value);

    abstract protected M convertFromString(String value, int radix);


    @Override
    protected void reciveValueFromBindingRead(AbstractProperty property, Object valueForWtite, Object bindingProcessor, Event parentEvent) {
        if (valueForWtite != null) {
            if (bindingProcessor == null) {
                setValueForBinding(convertValue(valueForWtite, "%f", 10), parentEvent);
                return;
            } else {
                if (bindingProcessor instanceof String) {
                    setValueForBinding(convertValue(valueForWtite, (String) bindingProcessor, 10), parentEvent);
                    return;
                } else if (bindingProcessor instanceof Integer) {
                    setValueForBinding(convertValue(valueForWtite, "%f", (Integer) bindingProcessor), parentEvent);
                    return;
                }
            }
        }
        super.reciveValueFromBindingRead(property, valueForWtite, bindingProcessor, parentEvent);
    }


    protected M convertValue(Object value, String format, int radix) {
        if (value instanceof Number) {
            return convertFromNumber((Number) value);
        } else if (value instanceof String) {
            if (format == null) {
                try {
                    return convertFromString((String) value, radix);
                } catch (NumberFormatException ex) {
                    fireEvent(EventType.PARSE_ERROR, ex.getLocalizedMessage());
                    return null;
                }
            } else {
                DecimalFormat frm = new DecimalFormat(format);
                try {
                    return convertFromNumber(frm.parse((String) value));
                } catch (ParseException e) {
                    fireEvent(EventType.PARSE_ERROR, e.getLocalizedMessage());
                    return null;
                }
            }
        }
        return super.convertValue(value);
    }


    @Override
    public void bindWriteStringProperty(StringPropertyInterface property, int radix) {
        bindPropertyForWrite((AbstractProperty) property, radix);
    }

    @Override
    public void bindReadStringProperty(StringPropertyInterface property, String format) {
        bindPropertyForRead((AbstractProperty) property, format);
    }

    @Override
    public void bindWriteStringProperty(StringPropertyInterface property, String format) {
        bindPropertyForWrite((AbstractProperty) property, format);
    }

    @Override
    public void bindReadStringProperty(StringPropertyInterface property) {
        bindPropertyForRead((AbstractProperty) property, null);
    }

    @Override
    public void bindWriteStringProperty(StringPropertyInterface property) {
        bindPropertyForWrite((AbstractProperty) property, null);
    }

    @Override
    public void bindReadStringProperty(StringPropertyInterface property, int radix) {
        bindPropertyForRead((AbstractProperty) property, radix);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}

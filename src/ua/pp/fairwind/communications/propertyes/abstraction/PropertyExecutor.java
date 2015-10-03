package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.abstractions.SystemEllement;
import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.propertyes.abstraction.propertyTrunsactions.OPERATION_TYPE;
import ua.pp.fairwind.communications.propertyes.abstraction.propertyTrunsactions.OperationTrunsactions;

/**
 * Created by Сергей on 13.09.2015.
 */
public abstract class PropertyExecutor extends SystemEllement {
    public PropertyExecutor(String codename) {
        super(codename);
    }

    public PropertyExecutor(String codename, String uuid) {
        super(codename, uuid);
    }

    protected void setInternalValue(ValueProperty property, final Comparable value) {
        property.setInternalValue(value, null);
    }


    protected void setHardWareInternalValue(ValueProperty property, final Comparable value, Event parent) {
        property.setInternalValue(value, parent);
    }


    protected Comparable getInternalValue(ValueProperty property) {
        return property.getInternalValue();
    }

    protected boolean isMultiRequestEnabled(AbstractProperty property, OPERATION_TYPE type) {
        return property.isMultiRequestEnabled(type);
    }

    protected void setMultiRequestEnabled(AbstractProperty property, OPERATION_TYPE type, boolean state) {
        property.setMultiRequestEnabled(type, state);
    }

    protected boolean isRequestActive(AbstractProperty property, OPERATION_TYPE type) {
        return property.isRequestActive(type);
    }

    protected boolean startRequest(AbstractProperty property, OPERATION_TYPE type) {
        return property.startRequest(type);
    }

    protected void endRequest(AbstractProperty property, OPERATION_TYPE type) {
        property.endRequest(type);
    }

    protected void setRequestTrunsaction(AbstractProperty property, OperationTrunsactions requestTrunsaction) {
        property.setRequestTrunsaction(requestTrunsaction);
    }

    protected void rollback(ValueProperty property, Event parent) {
        property.rollback(parent);
    }

    protected void invalidate(ValueProperty property, Event parent) {
        property.invalidate(parent);
    }

}

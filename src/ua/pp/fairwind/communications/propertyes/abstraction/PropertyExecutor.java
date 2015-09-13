package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.abstractions.SystemEllement;
import ua.pp.fairwind.communications.elementsdirecotry.SystemElementDirectory;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.propertyTrunsactions.OPERATION_TYPE;
import ua.pp.fairwind.communications.propertyes.abstraction.propertyTrunsactions.OperationTrunsactions;

import java.util.HashMap;

/**
 * Created by Сергей on 13.09.2015.
 */
public class PropertyExecutor extends SystemEllement {
    public PropertyExecutor(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public PropertyExecutor(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public PropertyExecutor(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public PropertyExecutor(String name, String uuid, String description, MessageSubSystem centralSystem, HashMap<String, String> uuids) {
        super(name, uuid, description, centralSystem, uuids);
    }

    public PropertyExecutor(String name, String uuid, String description, SystemElementDirectory centralSystem, HashMap<String, String> uuids) {
        super(name, uuid, description, centralSystem, uuids);
    }

    public PropertyExecutor(String name, String uuid, String description, SystemElementDirectory centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    protected void setInternalValue(ValueProperty property, final Comparable value,boolean silent,boolean fromHardWare){
        property.setInternalValue(value, silent, fromHardWare);
    }

    protected void setHardWareInternalValue(ValueProperty property, final Comparable value) {
        setInternalValue(property, value, false, true);
    }

    protected void setSilentInternalValue(ValueProperty property, final Comparable value) {
        setInternalValue(property, value, true, false);
    }

    protected void setInternalValue(ValueProperty property, final Comparable value) {
        setInternalValue(property, value, false, false);
    }

    protected Comparable getInternalValue(ValueProperty property) {
        return property.getInternalValue();
    }

    protected boolean isMultiRequestEnabled(AbstractProperty property,OPERATION_TYPE type){
        return property.isMultiRequestEnabled(type);
    }

    protected void setMultiRequestEnabled(AbstractProperty property,OPERATION_TYPE type,boolean state){
        property.setMultiRequestEnabled(type, state);
    }

    protected boolean isRequestActive(AbstractProperty property,OPERATION_TYPE type){
        return property.isRequestActive(type);
    }

    protected boolean startRequest(AbstractProperty property,OPERATION_TYPE type){
        return property.startRequest(type);
    }

    protected void endRequest(AbstractProperty property,OPERATION_TYPE type){
        property.endRequest(type);
    }

    protected void setRequestTrunsaction(AbstractProperty property,OperationTrunsactions requestTrunsaction) {
        property.setRequestTrunsaction(requestTrunsaction);
    }

    protected void rollback(ValueProperty property){
        property.rollback();
    }

    protected void invalidate(ValueProperty property){
        property.invalidate();
    }

}

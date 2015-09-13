package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.propertyes.abstraction.propertyTrunsactions.OPERATION_TYPE;
import ua.pp.fairwind.communications.propertyes.abstraction.propertyTrunsactions.OperationTrunsactions;

/**
 * Created by Сергей on 13.09.2015.
 */
public class AbstractTrunsactionExecutor {

    static protected boolean isMultiRequestEnabled(AbstractProperty property,OPERATION_TYPE type){
        return property.isMultiRequestEnabled(type);
    }

    static protected void setMultiRequestEnabled(AbstractProperty property,OPERATION_TYPE type,boolean state){
        property.setMultiRequestEnabled(type, state);
    }

    static protected boolean isRequestActive(AbstractProperty property,OPERATION_TYPE type){
        return property.isRequestActive(type);
    }

    static protected boolean startRequest(AbstractProperty property,OPERATION_TYPE type){
        return property.startRequest(type);
    }

    static protected void endRequest(AbstractProperty property,OPERATION_TYPE type){
        property.endRequest(type);
    }

    static protected void setRequestTrunsaction(AbstractProperty property,OperationTrunsactions requestTrunsaction) {
        property.setRequestTrunsaction( requestTrunsaction);
    }

    static protected void rollback(ValueProperty property){
        property.rollback();
    }

    static protected void invalidate(ValueProperty property){
        property.invalidate();
    }
}

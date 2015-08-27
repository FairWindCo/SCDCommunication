package ua.pp.fairwind.communications.propertyes.abstraction.propertyTrunsactions;

/**
 * Created by Сергей on 27.08.2015.
 */
public interface OperationTrunsactions {
    boolean isMultiRequestEnabled(OPERATION_TYPE type);

    void setMultiRequestEnabled(OPERATION_TYPE type, boolean state);

    boolean isRequestActive(OPERATION_TYPE type);

    boolean startRequest(OPERATION_TYPE type);

    void endRequest(OPERATION_TYPE type);
}

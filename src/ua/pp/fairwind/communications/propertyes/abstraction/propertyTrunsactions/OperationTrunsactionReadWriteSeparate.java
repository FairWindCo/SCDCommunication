package ua.pp.fairwind.communications.propertyes.abstraction.propertyTrunsactions;

/**
 * Created by Сергей on 27.08.2015.
 */
public class OperationTrunsactionReadWriteSeparate implements OperationTrunsactions {
    final private OperationTrunsactionsSingle read = new OperationTrunsactionsSingle();
    final private OperationTrunsactionsSingle write = new OperationTrunsactionsSingle();

    @Override
    public boolean isMultiRequestEnabled(OPERATION_TYPE type) {
        switch (type) {
            case READ_PROPERTY:
                return read.isMultiRequestEnabled(type);
            case WRITE_PROPERTY:
                return write.isMultiRequestEnabled(type);
            default:
                return false;
        }
    }

    @Override
    public void setMultiRequestEnabled(OPERATION_TYPE type, boolean state) {
        switch (type) {
            case READ_PROPERTY:
                read.setMultiRequestEnabled(type, state);
            case WRITE_PROPERTY:
                write.setMultiRequestEnabled(type, state);
        }
    }

    @Override
    public boolean isRequestActive(OPERATION_TYPE type) {
        switch (type) {
            case READ_PROPERTY:
                return read.isRequestActive(type);
            case WRITE_PROPERTY:
                return write.isRequestActive(type);
            default:
                return false;
        }
    }

    @Override
    public boolean startRequest(OPERATION_TYPE type) {
        switch (type) {
            case READ_PROPERTY:
                return read.startRequest(type);
            case WRITE_PROPERTY:
                return write.startRequest(type);
            default:
                return false;
        }
    }

    @Override
    public void endRequest(OPERATION_TYPE type) {
        switch (type) {
            case READ_PROPERTY:
                read.endRequest(type);
            case WRITE_PROPERTY:
                write.endRequest(type);
        }
    }
}

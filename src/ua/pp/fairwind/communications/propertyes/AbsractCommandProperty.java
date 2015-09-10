package ua.pp.fairwind.communications.propertyes;

import ua.pp.fairwind.communications.internatianalisation.I18N;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.propertyTrunsactions.OPERATION_TYPE;
import ua.pp.fairwind.communications.propertyes.event.EventType;
import ua.pp.fairwind.communications.propertyes.software.SoftBoolProperty;

/**
 * Created by FairWindCo on 07.07.2015
 */
abstract public class AbsractCommandProperty extends SoftBoolProperty {

    public AbsractCommandProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public void activate(){
        setValue(true);
    }
    public void executed(){
        setValue(false);
    }

    @Override
    protected void fireEvent(EventType type, Object param) {
        Boolean val=getValue();
        if(val!=null && val==true) {
            super.fireEvent(type, getName());
        }
    }

    @Override
    protected void bindPropertyForRead(AbstractProperty property) {
        fireEvent(EventType.PARSE_ERROR, I18N.getLocalizedString("command.binding.error"));
    }

    @Override
    protected void reciveValueFromBindingWrite(AbstractProperty property, Object valueForWtite, String formatForWrite, int radixForWrite, int positionForWrite, int lengthForWrite, boolean convertBoolToBinaryForWrite) {
        fireEvent(EventType.PARSE_ERROR,I18N.getLocalizedString("command.binding.error"));
    }

    @Override
    protected void reciveValueFromBindingRead(AbstractProperty property, Object valueForWtite) {
        fireEvent(EventType.PARSE_ERROR,I18N.getLocalizedString("command.binding.error"));
    }


    @Override
    protected void bindPropertyForWrite(AbstractProperty property, String formatForWrite, int radixForWrite, int positionForWrite, int lengthForWrite, boolean convertBoolToBinaryForWrite) {
        fireEvent(EventType.PARSE_ERROR, I18N.getLocalizedString("command.binding.error"));
    }


    @Override
    protected void unbindPropertyForRead() {
        fireEvent(EventType.PARSE_ERROR,I18N.getLocalizedString("command.binding.error"));
    }

    @Override
    protected void unbindPropertyForWrite() {
        fireEvent(EventType.PARSE_ERROR,I18N.getLocalizedString("command.binding.error"));
    }

    @Override
    public void endRequest(OPERATION_TYPE type) {
        if(type==OPERATION_TYPE.COMMAND_EXECUTE)setInternalValue(false);
        super.endRequest(type);
    }

    @Override
    public void invalidate() {
        setInternalValue(false);
        super.invalidate();
    }

    @Override
    public void rollback() {
        setInternalValue(false);
        super.invalidate();
    }
}

package ua.pp.fairwind.communications.propertyes;

import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.messagesystems.event.EventType;
import ua.pp.fairwind.communications.propertyes.abstraction.propertyTrunsactions.OPERATION_TYPE;
import ua.pp.fairwind.communications.propertyes.software.SoftBoolProperty;

/**
 * Created by FairWindCo on 07.07.2015
 */
abstract public class AbsractCommandProperty extends SoftBoolProperty {
    private final String command;
    public AbsractCommandProperty(String name, String uuid) {
        super(name, uuid);
        this.command=name;
    }

    public AbsractCommandProperty(String name, String uuid,String command) {
        super(name, uuid);
        this.command=command;
    }

    public void activate(){
        setInternalValue(true);
    }
    public void executed(){
        setInternalValue(false);
    }

    @Override
    protected void fireEvent(EventType type, Object param) {
        Boolean val=getValue();
        if(val!=null && val==true) {
            super.fireEvent(type, getName());
        }
    }

    @Override
    public void endRequest(OPERATION_TYPE type) {
        if(type==OPERATION_TYPE.COMMAND_EXECUTE)setInternalValue(false);
        super.endRequest(type);
    }
    @Override
    protected void invalidate(Event event) {
        setInternalValue(false,event);
        super.invalidate(event);
    }
    @Override
    protected void rollback(Event event) {
        setInternalValue(false,event);
        invalidate(event);
    }

    public String getCommand() {
        return command;
    }
}

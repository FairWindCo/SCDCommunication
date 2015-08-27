package ua.pp.fairwind.communications.propertyes;

import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;

/**
 * Created by FairWindCo on 07.07.2015
 */
public class DeviceNamedCommandProperty extends AbsractCommandProperty {


    public DeviceNamedCommandProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public void activate(){
        super.activate();
        //fireEvent(EventType.ELEMENT_CHANGE, getName());
    }

}

package ua.pp.fairwind.communications.propertyes;

/**
 * Created by FairWindCo on 07.07.2015
 */
public class DeviceNamedCommandProperty extends AbsractCommandProperty {


    public DeviceNamedCommandProperty(String name, String uuid) {
        super(name, uuid);
    }

    public DeviceNamedCommandProperty(String name) {
        super(name, null);
    }

    public DeviceNamedCommandProperty(String name, String uuid, String command) {
        super(name, uuid, command);
    }


    public void activate() {
        super.activate();
        //fireEvent(EventType.ELEMENT_CHANGE, getName());
    }

}

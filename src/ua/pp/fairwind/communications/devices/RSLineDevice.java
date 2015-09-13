package ua.pp.fairwind.communications.devices;

import ua.pp.fairwind.communications.elementsdirecotry.SystemElementDirectory;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;

import java.util.HashMap;

/**
 * Created by Сергей on 09.07.2015.
 */
public abstract class RSLineDevice extends AbstractDevice implements SerialDeviceInterface {
    protected final SoftLongProperty deviceAddress;


    public RSLineDevice(long address, String name, String uuid, String description, MessageSubSystem centralSystem) {
        this(address, name, uuid, description, centralSystem, null);
    }


    public RSLineDevice(long address, String name, String uuid, String description, MessageSubSystem centralSystem, HashMap<String, String> uuids) {
        super(name, uuid, description, centralSystem,uuids);
        deviceAddress=formLongProperty(-1, localizeName("device","address_property"),localizeDescription("device","address_property"),centralSystem,uuids,address);
        listOfPropertyes.add(deviceAddress);
    }

    protected RSLineDevice(long address, String name, String uuid, String description, SystemElementDirectory centralSystem) {
        this(address, name, uuid, description, centralSystem.getChileMessageSubsystems());
    }



    @Override
    public long getAddress() {
        Long adr= ((ValueProperty<Long>)deviceAddress).getInternalValue();
        if(adr==null) return -1;
        return adr;
    }

    @Override
    public void setAddress(long address) {
        ((ValueProperty<Long>)deviceAddress).setInternalValue(address);
    }



    public SoftLongProperty getDeviceAddressProperty(){
        return deviceAddress;
    }


}

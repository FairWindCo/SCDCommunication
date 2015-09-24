package ua.pp.fairwind.communications.devices.abstracts;

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



    public RSLineDevice(long address, String codename, String uuid) {
        super(codename, uuid);
        deviceAddress=formLongProperty(-1, "device.address_property",address);
        listOfPropertyes.add(deviceAddress);
    }

    protected RSLineDevice(long address, String codename) {
        this(address, codename,null);
    }



    @Override
    public long getAddress() {
        Long adr= (Long)getInternalValue(deviceAddress);
        if(adr==null) return -1;
        return adr;
    }

    @Override
    public void setAddress(long address) {
        setInternalValue(deviceAddress, address);
    }



    public SoftLongProperty getDeviceAddressProperty(){
        return deviceAddress;
    }


}

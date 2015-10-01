package ua.pp.fairwind.communications.devices.abstracts;

import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;

/**
 * Created by Сергей on 09.07.2015.
 */
public abstract class RSLineImmitatorDevice extends AbstractImmitatorDevice implements SerialDeviceInterface {
    protected final SoftLongProperty deviceAddress;
    protected final SoftLongProperty reserv=new SoftLongProperty("RESERV");


    public RSLineImmitatorDevice(long address, String codename, String uuid) {
        super(codename, uuid);
        deviceAddress=formLongProperty(-1, "device.address_property",address);
        listOfPropertyes.add(deviceAddress);
    }

    protected RSLineImmitatorDevice(long address, String codename) {
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

    @Override
    public SoftLongProperty getRetryCount() {
        return reserv;
    }

    @Override
    public SoftLongProperty getDeviceTimeOut() {
        return reserv;
    }

    @Override
    public SoftLongProperty getDeviceTimeOutPause() {
        return reserv;
    }

    @Override
    public SoftLongProperty getDeviceWritePause() {
        return reserv;
    }
}

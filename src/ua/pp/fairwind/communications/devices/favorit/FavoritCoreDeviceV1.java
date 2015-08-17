package ua.pp.fairwind.communications.devices.favorit;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.devices.AbstractDevice;
import ua.pp.fairwind.communications.devices.DeviceInterface;
import ua.pp.fairwind.communications.devices.RequestInformation;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.HardWarePropertyInfo;
import ua.pp.fairwind.communications.propertyes.hardware.HardBoolProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftBoolProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;

import java.nio.ByteBuffer;
import java.util.HashMap;

/**
 * Created by Сергей on 09.07.2015.
 */
public class FavoritCoreDeviceV1 extends AbstractDevice implements DeviceInterface {
    private final HardBoolProperty digitalInChanelN1;

    public FavoritCoreDeviceV1(long address, String name, String uuid, String description, MessageSubSystem centralSystem) {
        this(address, name, uuid, description, centralSystem, null);
    }

    public FavoritCoreDeviceV1(long address, String name, String uuid, String description, MessageSubSystem centralSystem, HashMap<String, String> uuids) {
        super(address, name, uuid, description, centralSystem, uuids);
        digitalInChanelN1=formBoolProperty(301,"DIGITAL_IN_2","Цифровой вход",centralSystem,uuids,false);
        addPropertys(digitalInChanelN1);
    }

    @Override
    protected HardBoolProperty formBoolProperty(long address, String name, String description, MessageSubSystem centralSystem, HashMap<String, String> uuids, boolean initialValue) {
        HardBoolProperty prp= super.formBoolProperty(address, name, description, centralSystem, uuids, initialValue);
        return prp;
    }

    protected void processRecivedMessage(final byte[] recivedMessage,final byte[] sendMessage,final AbstractProperty property){

    }

    @Override
    protected RequestInformation formReadRequest(HardWarePropertyInfo property) {
        if(property==null) return null;
        switch ((int)property.getAddress()){
            case 301:{
                ByteBuffer buffer=ByteBuffer.allocate(10);
                buffer.putChar('V');
                buffer.putChar('N');
                buffer.putChar('T');
                return new RequestInformation(buffer.array(),10);
            }
            default:return null;
        }
    }

    @Override
    protected RequestInformation formWriteRequest(HardWarePropertyInfo property) {
        if(property==null) return null;
        SoftLongProperty devaddress=(SoftLongProperty)deviceAddress.getProperty();
        long deviceaddress=devaddress.getValue();
        switch ((int)property.getAddress()){
            case 301:{
                ByteBuffer buffer=ByteBuffer.allocate(10);
                buffer.putChar('V');
                buffer.putChar('N');
                buffer.putChar('T');
                buffer.put((byte) (((deviceaddress & 0xF0) >> 4) + '0'));
                buffer.put((byte)((deviceaddress&0xF)+'0'));
                buffer.putChar('3');
                buffer.putChar('1');
                return new RequestInformation(buffer.array(),10);
            }
            default:return null;
        }
    }

    public SoftBoolProperty getDigitalInChanelN1() {
        return (SoftBoolProperty)digitalInChanelN1.getProperty();
    }
}

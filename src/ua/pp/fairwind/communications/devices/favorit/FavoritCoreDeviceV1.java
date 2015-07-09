package ua.pp.fairwind.communications.devices.favorit;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.devices.AbstractDevice;
import ua.pp.fairwind.communications.devices.DeviceInterface;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.HardWarePropertyInfo;

import java.util.HashMap;

/**
 * Created by Сергей on 09.07.2015.
 */
public class FavoritCoreDeviceV1 extends AbstractDevice implements DeviceInterface {
    public FavoritCoreDeviceV1(long address, String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(address, name, uuid, description, centralSystem);
    }

    public FavoritCoreDeviceV1(long address, String name, String uuid, String description, MessageSubSystem centralSystem, HashMap<String, String> uuids) {
        super(address, name, uuid, description, centralSystem, uuids);
    }

    protected void processRecivedMessage(final byte[] recivedMessage,final byte[] sendMessage,final AbstractProperty property){

    }

    protected byte[] formReadRequest(HardWarePropertyInfo property){
        return null;
    }


    protected byte[] formWriteRequest(HardWarePropertyInfo property){
        return null;
    }

}

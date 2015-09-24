package ua.pp.fairwind.communications.elementsdirecotry;

import ua.pp.fairwind.communications.devices.abstracts.DeviceInterface;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Сергей on 09.09.2015.
 */
public class DeviceAutoCreator implements AutoCreateDeviceFunction{
    final private ConcurrentHashMap<String,DeviceInterface> createdDevices=new ConcurrentHashMap<>();
    final private AutoCreateDeviceFunction createDeviceFunction;

    public DeviceAutoCreator(AutoCreateDeviceFunction createDeviceFunction) {
        this.createDeviceFunction = createDeviceFunction;
    }

    public DeviceAutoCreator() {
        this(null);
    }


    @Override
    public DeviceInterface createDevice(Long address, String typeOfDevice, String name) {
        if(name==null)return null;
        DeviceInterface device=createdDevices.get(name);
        if(device==null) {
            if (createDeviceFunction == null) {
                device = AutoCreateDeviceFunction.super.createAutoDevice(address, typeOfDevice, name);
            } else {
                device = createDeviceFunction.createDevice(address, typeOfDevice, name);
            }
            if(device!=null)createdDevices.put(name,device);
        }
        return device;
    }
}

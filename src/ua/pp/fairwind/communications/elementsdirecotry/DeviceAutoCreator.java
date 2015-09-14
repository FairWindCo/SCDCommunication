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
    private final HashMap<String,String> uuids;
    private final MessageSubSystem topLevel;

    public DeviceAutoCreator(AutoCreateDeviceFunction createDeviceFunction, HashMap<String, String> uuids, MessageSubSystem topLevel) {
        this.createDeviceFunction = createDeviceFunction;
        this.uuids = uuids;
        this.topLevel = topLevel;
    }

    public DeviceAutoCreator(MessageSubSystem topLevel,HashMap<String, String> uuids) {
        this(null,uuids,topLevel);
    }

    public DeviceInterface createDevice(Long address,String deviceType,String deviceName,String deviceDescription){
        return createDevice(address,deviceType,deviceName,deviceDescription,topLevel,uuids);
    }

    public DeviceInterface createDevice(String deviceType,String deviceName,String deviceDescription){
        return createDevice(null,deviceType,deviceName,deviceDescription,topLevel,uuids);
    }

    public DeviceInterface createDevice(Long address,String deviceType,String deviceName){
        return createDevice(address,deviceType,deviceName,null,topLevel,uuids);
    }

    public DeviceInterface createDevice(String deviceType,String deviceName){
        return createDevice(null,deviceType,deviceName,null,topLevel,uuids);
    }

    @Override
    public DeviceInterface createDevice(Long address, String typeOfDevice, String name, String description, MessageSubSystem ms,HashMap<String, String> uuids) {
        if(name==null)return null;
        DeviceInterface device=createdDevices.get(name);
        if(device==null) {
            if (createDeviceFunction == null) {
                device = AutoCreateDeviceFunction.super.createAutoDevice(address, typeOfDevice, name, description, ms, uuids);
            } else {
                device = createDeviceFunction.createDevice(address, typeOfDevice, name, description, topLevel, uuids);
            }
            if(device!=null)createdDevices.put(name,device);
        }
        return device;
    }
}

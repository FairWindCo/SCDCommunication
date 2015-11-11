package ua.pp.fairwind.communications.elementsdirecotry;

import ua.pp.fairwind.communications.devices.abstracts.AbstractDevice;
import ua.pp.fairwind.communications.devices.abstracts.LinedDeviceInterface;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Сергей on 11.11.2015.
 */
public class DeviceCreator implements AutoCreateDeviceFunction,DeviceCreatorInterface {
    private final AutoCreateDeviceFunction createDeviceFunction;
    final private ConcurrentHashMap<String, LinedDeviceInterface> createdDevices = new ConcurrentHashMap<>();

    public DeviceCreator(AutoCreateDeviceFunction createDeviceFunction) {
        this.createDeviceFunction = createDeviceFunction;
    }

    public LinedDeviceInterface createDevice(String deviceType, String deviceName) {
        return createDevice((Long)null, deviceType, deviceName);
    }

    @Override
    public AbstractDevice createDevice(String name, String typeOfDevice, Object... params) {
        if(params!=null&&params.length>1&&params[0] instanceof Number){
            Number p=(Number)params[0];
            return (AbstractDevice)createDevice(p.longValue(),typeOfDevice,name);
        }
        return null;
    }

    public LinedDeviceInterface createDevice(Long address, String typeOfDevice, String name) {
        if (name == null) return null;
        LinedDeviceInterface device = createdDevices.get(name);
        if (device == null) {
            if (createDeviceFunction == null) {
                device = createAutoDevice(address, typeOfDevice, name);
            } else {
                device = createDeviceFunction.createAutoDevice(address, typeOfDevice, name);
            }
            if (device != null) {
                createdDevices.put(name, device);
            }
        }
        return device;
    }
}

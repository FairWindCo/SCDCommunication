package ua.pp.fairwind.communications.elementsdirecotry;

import ua.pp.fairwind.communications.devices.abstracts.AbstractDevice;
import ua.pp.fairwind.communications.devices.abstracts.LinedDeviceInterface;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Сергей on 09.09.2015.
 */
public class DeviceAutoCreator implements AutoCreateDeviceFunction {
    final private ConcurrentHashMap<String, LinedDeviceInterface> createdDevices = new ConcurrentHashMap<>();
    final private AutoCreateDeviceFunction createDeviceFunction;

    public DeviceAutoCreator(AutoCreateDeviceFunction createDeviceFunction) {
        this.createDeviceFunction = createDeviceFunction;
    }

    public DeviceAutoCreator() {
        this(null);
    }


    public LinedDeviceInterface createDevice(Long address, String typeOfDevice, String name) {
        if (name == null) return null;
        LinedDeviceInterface device = createdDevices.get(name);
        if (device == null) {
            if (createDeviceFunction == null) {
                device = AutoCreateDeviceFunction.super.createAutoDevice(address, typeOfDevice, name);
            } else {
                device = createDeviceFunction.createAutoDevice(address, typeOfDevice, name);
            }
            if (device != null) createdDevices.put(name, device);
        }
        return device;
    }

    @Override
    public AbstractDevice createDevice(String name, String typeOfDevice, Object... params) {
        if(params!=null&&params.length>1&&params[0] instanceof Number){
            Number p=(Number)params[0];
            return (AbstractDevice)createDevice(p.longValue(),typeOfDevice,name);
        }
        return null;
    }
}

package ua.pp.fairwind.communications.elementsdirecotry;

import ua.pp.fairwind.communications.devices.abstracts.AbstractDevice;

/**
 * Created by Сергей on 11.11.2015.
 */
public interface DeviceCreatorInterface {
    AbstractDevice createDevice(String name, String typeOfDevice, Object... params);
}

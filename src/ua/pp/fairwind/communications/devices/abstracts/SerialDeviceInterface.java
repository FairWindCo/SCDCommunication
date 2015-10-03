package ua.pp.fairwind.communications.devices.abstracts;

/**
 * Created by Сергей on 07.07.2015.
 */
public interface SerialDeviceInterface extends DeviceInterface {
    long getAddress();

    void setAddress(long address);
}

package ua.pp.fairwind.communications.lines;

import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.devices.DeviceInterface;

import java.util.UUID;

/**
 * Created by Сергей on 07.07.2015.
 */
public interface LineInterface  extends ElementInterface {
    void startTransaction(UUID uuid) throws TrunsactionError;
    void endTransaction(UUID uuid);

    void sendMessage(UUID uuid,byte[] data,LineParameters params) throws TrunsactionError,LineTimeOutException,LineErrorException;
    byte[] reciveMessage(UUID uuid,long timeOut,long bytesForReadCount,LineParameters params) throws TrunsactionError,LineTimeOutException,LineErrorException;



    void async_communicate(CommunicationProtocol request);

    void addReadMonitoringDevice(DeviceInterface device);
    void addWriteMonitoringDevice(DeviceInterface device);
    void removeReadMonitoringDevice(DeviceInterface device);
    void removeWriteMonitoringDevice(DeviceInterface device);

    boolean isPaused();
    void setPaused(boolean pause);
}

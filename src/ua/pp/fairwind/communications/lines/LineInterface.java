package ua.pp.fairwind.communications.lines;

import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.devices.DeviceInterface;
import ua.pp.fairwind.communications.lines.exceptions.LineErrorException;
import ua.pp.fairwind.communications.lines.exceptions.LineTimeOutException;
import ua.pp.fairwind.communications.lines.exceptions.TrunsactionError;
import ua.pp.fairwind.communications.lines.operations.CommunicationProtocolRequest;

import java.util.UUID;

/**
 * Created by Сергей on 07.07.2015.
 */
public interface LineInterface  extends ElementInterface {
    void startTransaction(UUID uuid,long waitTime) throws TrunsactionError;
    void endTransaction(UUID uuid);

    void sendMessage(UUID uuid,byte[] data,LineParameters params) throws TrunsactionError,LineTimeOutException,LineErrorException;
    byte[] reciveMessage(UUID uuid,long timeOut,long bytesForReadCount,LineParameters params) throws TrunsactionError,LineTimeOutException,LineErrorException;



    void async_communicate(CommunicationProtocolRequest request);

    void addReadMonitoringDevice(DeviceInterface device);
    void addWriteMonitoringDevice(DeviceInterface device);
    void removeReadMonitoringDevice(DeviceInterface device);
    void removeWriteMonitoringDevice(DeviceInterface device);

    boolean isPaused();
    void setPaused(boolean pause);
}

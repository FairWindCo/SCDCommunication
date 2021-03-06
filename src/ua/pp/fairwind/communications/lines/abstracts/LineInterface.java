package ua.pp.fairwind.communications.lines.abstracts;

import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.devices.abstracts.ImitatorDevice;
import ua.pp.fairwind.communications.devices.abstracts.LinedDeviceInterface;
import ua.pp.fairwind.communications.lines.exceptions.LineErrorException;
import ua.pp.fairwind.communications.lines.exceptions.LineTimeOutException;
import ua.pp.fairwind.communications.lines.exceptions.TrunsactionError;
import ua.pp.fairwind.communications.lines.lineparams.LineParameters;
import ua.pp.fairwind.communications.lines.operations.CommunicationProtocolRequest;

import java.util.UUID;

/**
 * Created by Сергей on 07.07.2015.
 */
public interface LineInterface extends ElementInterface {
    void startTransaction(UUID uuid, long waitTime) throws TrunsactionError;

    void endTransaction(UUID uuid);

    void sendMessage(UUID uuid, byte[] data, LineParameters params) throws TrunsactionError, LineTimeOutException, LineErrorException;

    byte[] reciveMessage(UUID uuid, long timeOut, long bytesForReadCount, LineParameters params) throws TrunsactionError, LineTimeOutException, LineErrorException;

    void async_communicate(CommunicationProtocolRequest request);

    void addReadMonitoringDevice(LinedDeviceInterface device);

    void addWriteMonitoringDevice(LinedDeviceInterface device);

    void removeReadMonitoringDevice(LinedDeviceInterface device);

    void removeWriteMonitoringDevice(LinedDeviceInterface device);

    boolean isPaused();

    void setPaused(boolean pause);

    void setServiceMode(SERVICE_MODE mode, LineParameters serverLineParameter);

    void setClientMode();

    SERVICE_MODE getServiceMode();

    LineParameters getServerLineParameter();

    ImitatorDevice[] getDeivicesForService();

    void addDeviceToService(ImitatorDevice device);

    void removeDeviceToService(ImitatorDevice device);

    void closeLine();

    enum SERVICE_MODE {
        CLIENT_ONLY,
        SERVER_ONLY,
        CLIENT_SERVER
    }

}

package ua.pp.fairwind.communications.devices.logging;

import ua.pp.fairwind.communications.devices.RequestInformation;
import ua.pp.fairwind.communications.devices.abstracts.AbstractLinedDevice;
import ua.pp.fairwind.communications.lines.operations.CommunicationAnswer;
import ua.pp.fairwind.communications.lines.operations.CommunicationProtocolRequest;
import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;

/**
 * Created by Сергей on 13.08.2015.
 */
public class LoggingDevice extends AbstractLinedDevice {
    final LineMonitorInterface monitorInterface;

    public LoggingDevice(String name, String uuid, LineMonitorInterface monitorInterface) {
        super(name, uuid);
        this.monitorInterface = monitorInterface;
    }

    public LoggingDevice(String name, LineMonitorInterface monitorInterface) {
        super(name, null);
        this.monitorInterface = monitorInterface;
    }

    @Override
    protected boolean processRecivedMessage(byte[] recivedMessage, byte[] sendMessage, AbstractProperty property, final Event sourceEvent) {
        return true;
    }

    @Override
    protected RequestInformation formReadRequest(AbstractProperty property) {
        return null;
    }

    @Override
    protected RequestInformation formWriteRequest(AbstractProperty property) {
        return null;
    }

    @Override
    public void processRecivedMessage(CommunicationAnswer answer) {
        if (answer != null && monitorInterface != null) {
            if (answer.getStatus() == CommunicationAnswer.CommunicationResult.READ_MONITOR) {
                CommunicationProtocolRequest request = answer.getRequest();
                LineMonitoringEvent event = new LineMonitoringEvent(LineMonitoringEvent.ACTION_TYPE.READ, answer.getRecivedMessage(), answer.getCommunicateOverLine(), request != null ? request.getSenderDevice() : null, request != null ? request.getProperty() : null);
                monitorInterface.monitor(event);
            } else if (answer.getStatus() == CommunicationAnswer.CommunicationResult.WRITE_MONITOR) {
                CommunicationProtocolRequest request = answer.getRequest();
                LineMonitoringEvent event = new LineMonitoringEvent(LineMonitoringEvent.ACTION_TYPE.WRITE, request != null ? request.getBytesForSend() : null, answer.getCommunicateOverLine(), request != null ? request.getSenderDevice() : null, request != null ? request.getProperty() : null);
                monitorInterface.monitor(event);
            }
        }
    }

    @Override
    public String getDeviceType() {
        return "LOGGING DEVICE";
    }
}

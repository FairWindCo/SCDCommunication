package ua.pp.fairwind.communications.devices.logging;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.devices.AbstractDevice;
import ua.pp.fairwind.communications.devices.RequestInformation;
import ua.pp.fairwind.communications.lines.CommunicationAnswer;
import ua.pp.fairwind.communications.lines.CommunicationProtocolRequest;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;

import java.util.HashMap;

/**
 * Created by Сергей on 13.08.2015.
 */
public class LoggingDevice extends AbstractDevice {
    final LineMonitorInterface monitorInterface;

    public LoggingDevice(long address, String name, String uuid, String description, MessageSubSystem centralSystem,LineMonitorInterface monitorInterface) {
        super(address, name, uuid, description, centralSystem);
        this.monitorInterface=monitorInterface;
    }

    public LoggingDevice(long address, String name, String uuid, String description, MessageSubSystem centralSystem, HashMap<String, String> uuids,LineMonitorInterface monitorInterface) {
        super(address, name, uuid, description, centralSystem, uuids);
        this.monitorInterface=monitorInterface;
    }

    @Override
    protected boolean processRecivedMessage(byte[] recivedMessage, byte[] sendMessage, AbstractProperty property) {
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
        if(answer!=null && monitorInterface!=null){
            if(answer.getStatus()== CommunicationAnswer.CommunicationResult.READ_MONITOR){
                CommunicationProtocolRequest request=answer.getRequest();
                LineMonitoringEvent event=new LineMonitoringEvent(LineMonitoringEvent.ACTION_TYPE.READ,answer.getRecivedMessage(),answer.getCommunicateOverLine(),request!=null?request.getSenderDevice():null,request!=null?request.getProperty():null);
                monitorInterface.monitor(event);
            } else if(answer.getStatus()== CommunicationAnswer.CommunicationResult.WRITE_MONITOR){
                CommunicationProtocolRequest request=answer.getRequest();
                LineMonitoringEvent event=new LineMonitoringEvent(LineMonitoringEvent.ACTION_TYPE.WRITE,request!=null?request.getBytesForSend():null,answer.getCommunicateOverLine(),request!=null?request.getSenderDevice():null,request!=null?request.getProperty():null);
                monitorInterface.monitor(event);
            }
        }
    }
}

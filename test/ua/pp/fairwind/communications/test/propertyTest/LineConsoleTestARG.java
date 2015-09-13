package ua.pp.fairwind.communications.test.propertyTest;

import jssc.SerialPort;
import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.devices.hardwaredevices.arg.micro.ArgMicroDevice;
import ua.pp.fairwind.communications.devices.hardwaredevices.favorit.FavoritCoreDeviceV1;
import ua.pp.fairwind.communications.devices.logging.LineMonitorInterface;
import ua.pp.fairwind.communications.devices.logging.LineMonitoringEvent;
import ua.pp.fairwind.communications.devices.logging.LoggingDevice;
import ua.pp.fairwind.communications.lines.CommunicationLineParameters;
import ua.pp.fairwind.communications.lines.SerialLine;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystemMultiDipatch;
import ua.pp.fairwind.communications.propertyes.event.ElementEventListener;
import ua.pp.fairwind.communications.propertyes.event.EventType;

/**
 * Created by Сергей on 17.08.2015.
 */
public class LineConsoleTestARG {
    public static void main(String[] args) {
        MessageSubSystem ms=new MessageSubSystemMultiDipatch();
        SerialLine line=new SerialLine("com9","RS232 Line#1",null,"Serial port",ms,5000);
        FavoritCoreDeviceV1 favorit=new FavoritCoreDeviceV1(0x1L,"Favirit Ventel",null,"",ms);
        LoggingDevice ldev=new LoggingDevice("Logging Device", null, "", null, new LineMonitorInterface() {
            @Override
            public void monitor(LineMonitoringEvent event) {
                System.out.println(event);
            }
        });
        line.addWriteMonitoringDevice(ldev);
        line.addReadMonitoringDevice(ldev);
        line.setLineSelector(favorit);

        ArgMicroDevice argMicro=new ArgMicroDevice(10L,"ARG",null,"ARG Micro",ms,null);
        argMicro.setLineParameters(new CommunicationLineParameters(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE,
                SerialPort.FLOWCONTROL_NONE,4));
        argMicro.setReadTimeOut(1050);
        argMicro.setPrimerayLine(line);

        argMicro.addEventListener(new ElementEventListener() {
            @Override
            public void elementEvent(ElementInterface element, EventType typeEvent, Object params) {
                System.out.println(typeEvent + " : " + element.toString() + " - " + params);
            }
        });
        argMicro.getNumberMeasurementm().addEventListener((element, typeEvent, params) -> System.out.println(typeEvent + " : " + element.toString() + " - " + params));
        argMicro.getRate().addEventListener((element, typeEvent, params) -> System.out.println(typeEvent + " : " + element.toString() + " - " + params));
        argMicro.getRate().readValueRequest();

        //fav.getLineSelect().setRequestTrunsaction(new OperationTrunsactionReadWriteSeparate());

        /*fav.getRate().setPropertyTimeOutReadAddon(50);
        fav.getRate().setValue(5.6f);
        fav.getRate().writeValueRequest();
        fav.getRate().readValueRequest();*/

    }
}

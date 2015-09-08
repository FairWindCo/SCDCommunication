package ua.pp.fairwind.communications.test.propertyTest;

import jssc.SerialPort;
import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.devices.favorit.FavoritCoreDeviceV1;
import ua.pp.fairwind.communications.devices.logging.LineMonitorInterface;
import ua.pp.fairwind.communications.devices.logging.LineMonitoringEvent;
import ua.pp.fairwind.communications.devices.logging.LoggingDevice;
import ua.pp.fairwind.communications.devices.panDrive.StepDriver;
import ua.pp.fairwind.communications.lines.CommunicationLineParameters;
import ua.pp.fairwind.communications.lines.SerialLine;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystemMultiDipatch;
import ua.pp.fairwind.communications.propertyes.event.ElementEventListener;
import ua.pp.fairwind.communications.propertyes.event.EventType;
import ua.pp.fairwind.communications.utils.CommunicationUtils;

/**
 * Created by Сергей on 17.08.2015.
 */
public class LineConsoleTestStep {
    public static void main(String[] args) {
        MessageSubSystem ms=new MessageSubSystemMultiDipatch();
        SerialLine line=new SerialLine("com9","RS232 Line#1",null,"Serial port",ms,5000);
        FavoritCoreDeviceV1 favorit=new FavoritCoreDeviceV1(0x1L,"Favirit Ventel",null,"",ms);
        LoggingDevice ldev=new LoggingDevice(0x000, "Logging Device", null, "", null, new LineMonitorInterface() {
            @Override
            public void monitor(LineMonitoringEvent event) {
                System.out.println(event);
            }
        });
        //line.addWriteMonitoringDevice(ldev);
        //line.addReadMonitoringDevice(ldev);
        line.setLineSelector(favorit);

        StepDriver motorDrive=new StepDriver(1L,"StepDrive",null,"PanDrive Step Motor",ms,null);
        motorDrive.setReadTimeOut(250);
        motorDrive.setPrimerayLine(line);

        motorDrive.addEventListener(new ElementEventListener() {
            @Override
            public void elementEvent(ElementInterface element, EventType typeEvent, Object params) {
                System.out.println(typeEvent + " : " + element.toString() + " - " + params);
            }
        });
        motorDrive.setLineParameters(new CommunicationLineParameters(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE, SerialPort.FLOWCONTROL_NONE, 3));
        motorDrive.getSpeed().addEventListener((element, typeEvent, params) -> System.out.println(typeEvent + " : " + element.toString() + " - " + params));
        motorDrive.getPosition().addEventListener((element, typeEvent, params) -> System.out.println(typeEvent + " : " + element.toString() + " - " + params));
        motorDrive.getStatusCode().addEventListener((element, typeEvent, params) -> System.out.println(typeEvent + " : " + element.toString() + " - " + params));
        //motorDrive.getPosition().readValueRequest();

        motorDrive.getTickTimer().addEventListener((element, typeEvent, params) -> System.out.println(typeEvent + " : " + element.toString() + " - " + params));
        motorDrive.getTickTimer().readValueRequest();

        motorDrive.getSpeed().readValueRequest();
        motorDrive.getSpeed().setValue((short) 1000);
        motorDrive.getPosition().readValueRequest();
        motorDrive.getRotateRight().activate();

        CommunicationUtils.RealThreadPause(5000);
        motorDrive.getMotorStop().activate();
        CommunicationUtils.RealThreadPause(1000);
        motorDrive.getStep().setValue(1000L);
        motorDrive.getStep().writeValueRequest();
        //motorDrive.getStepRight().activate();


        //motorDrive.getLineSelect().setRequestTrunsaction(new OperationTrunsactionReadWriteSeparate());

        /*motorDrive.getRate().setPropertyTimeOutReadAddon(50);
        motorDrive.getRate().setValue(5.6f);
        motorDrive.getRate().writeValueRequest();
        motorDrive.getRate().readValueRequest();*/

    }
}

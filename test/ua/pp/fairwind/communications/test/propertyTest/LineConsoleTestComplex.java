package ua.pp.fairwind.communications.test.propertyTest;

import jssc.SerialPort;
import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.devices.hardwaredevices.Baumer.Encoder;
import ua.pp.fairwind.communications.devices.hardwaredevices.favorit.FavoritCoreDeviceV1;
import ua.pp.fairwind.communications.devices.hardwaredevices.panDrive.StepDriver;
import ua.pp.fairwind.communications.devices.logging.LineMonitorInterface;
import ua.pp.fairwind.communications.devices.logging.LineMonitoringEvent;
import ua.pp.fairwind.communications.devices.logging.LoggingDevice;
import ua.pp.fairwind.communications.lines.SerialLine;
import ua.pp.fairwind.communications.lines.lineparams.CommunicationLineParameters;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystemMultiDipatch;
import ua.pp.fairwind.communications.propertyes.event.ElementEventListener;
import ua.pp.fairwind.communications.propertyes.event.EventType;
import ua.pp.fairwind.communications.utils.CommunicationUtils;

import java.io.IOException;

/**
 * Created by Сергей on 17.08.2015.
 */
public class LineConsoleTestComplex {
    public static void main(String[] args) throws IOException {
        SerialLine line=new SerialLine("com9","RS232 Line#1",null,5000);
        FavoritCoreDeviceV1 favorit=new FavoritCoreDeviceV1(0x1L);
        LoggingDevice ldev=new LoggingDevice("Logging Device", null,new LineMonitorInterface() {
            @Override
            public void monitor(LineMonitoringEvent event) {
                System.out.println(event);
            }
        });
        line.addWriteMonitoringDevice(ldev);
        line.addReadMonitoringDevice(ldev);
        line.setLineSelector(favorit);
        favorit.setLineParameters(new CommunicationLineParameters(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_2, SerialPort.PARITY_NONE, SerialPort.FLOWCONTROL_NONE));
        Encoder encoder=new Encoder(3L,"Encoder",null);
        encoder.setPauseBeforeRead(100L);
        encoder.setReadTimeOut(3500L);
        encoder.setPrimerayLine(line);
        encoder.setLineParameters(new CommunicationLineParameters(SerialPort.BAUDRATE_38400, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE, SerialPort.FLOWCONTROL_NONE, 4));

        encoder.addEventListener(new ElementEventListener() {
            @Override
            public void elementEvent(ElementInterface element, EventType typeEvent, Object params) {
                System.out.println(typeEvent + " : " + element.toString() + " - " + params);
            }
        });

        encoder.getSteps().addEventListener((element, typeEvent, params) -> System.out.println(typeEvent + " : " + element.toString() + " - " + params));
        encoder.getRevolution().addEventListener((element, typeEvent, params) -> System.out.println(typeEvent + " : " + element.toString() + " - " + params));


        StepDriver motorDrive=new StepDriver(1L,"StepDrive",null);
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

        motorDrive.getSpeed().readValueRequest();
        motorDrive.getSpeed().setValue((short) 1000);
        motorDrive.getPosition().readValueRequest();
        motorDrive.getStep().setValue(1000L);
        CommunicationUtils.RealThreadPause(1000);
        do {
            //encoder.getSteps().readValueRequest();
            motorDrive.getSpeed().setValue((short) 1000);
            motorDrive.getRotateRight().activate();
            CommunicationUtils.RealThreadPause(5000);
            motorDrive.getMotorStop().activate();
            CommunicationUtils.RealThreadPause(1000);
            motorDrive.getStep().writeValueRequest();
            //motorDrive.getStepRight().activate();
            encoder.getSteps().readValueRequest();
            CommunicationUtils.RealThreadPause(1000);
        }while (System.in.available()==0);
        line.destroy();
        MessageSubSystemMultiDipatch.destroyAllService();
        System.out.println("ALL FINISH");
        //fav.getLineSelect().setRequestTrunsaction(new OperationTrunsactionReadWriteSeparate());

        /*fav.getRate().setPropertyTimeOutReadAddon(50);
        fav.getRate().setValue(5.6f);
        fav.getRate().writeValueRequest();
        fav.getRate().readValueRequest();*/

    }
}

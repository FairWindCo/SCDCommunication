package ua.pp.fairwind.communications.test.propertyTest;

import jssc.SerialPort;
import ua.pp.fairwind.communications.devices.hardwaredevices.favorit.FavoritCoreDeviceV1;
import ua.pp.fairwind.communications.devices.hardwaredevices.panDrive.StepDriver;
import ua.pp.fairwind.communications.devices.logging.LineMonitorInterface;
import ua.pp.fairwind.communications.devices.logging.LineMonitoringEvent;
import ua.pp.fairwind.communications.devices.logging.LoggingDevice;
import ua.pp.fairwind.communications.lines.SerialLine;
import ua.pp.fairwind.communications.lines.lineparams.CommunicationLineParameters;
import ua.pp.fairwind.communications.messagesystems.event.ElementEventListener;
import ua.pp.fairwind.communications.utils.CommunicationUtils;

/**
 * Created by Сергей on 17.08.2015.
 */
public class LineConsoleTestStep {
    public static void main(String[] args) {
        SerialLine line = new SerialLine("com9", "RS232 Line#1", null, 5000);
        FavoritCoreDeviceV1 favorit = new FavoritCoreDeviceV1(0x1L);
        LoggingDevice ldev = new LoggingDevice("Logging Device", null, new LineMonitorInterface() {
            @Override
            public void monitor(LineMonitoringEvent event) {
                System.out.println(event);
            }
        });
        //line.addWriteMonitoringDevice(ldev);
        //line.addReadMonitoringDevice(ldev);
        line.setLineSelector(favorit);

        StepDriver motorDrive = new StepDriver(1L, "StepDrive", null);
        motorDrive.setReadTimeOut(250);
        motorDrive.setPrimerayLine(line);

        motorDrive.addEventListener(ElementEventListener.println);
        motorDrive.setLineParameters(new CommunicationLineParameters(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE, SerialPort.FLOWCONTROL_NONE, 3));
        motorDrive.getSpeed().addEventListener(ElementEventListener.println);
        motorDrive.getPosition().addEventListener(ElementEventListener.println);
        motorDrive.getStatusCode().addEventListener(ElementEventListener.println);
        //motorDrive.getPosition().readValueRequest();

        motorDrive.getTickTimer().addEventListener(ElementEventListener.println);
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

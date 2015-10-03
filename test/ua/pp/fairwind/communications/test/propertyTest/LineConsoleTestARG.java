package ua.pp.fairwind.communications.test.propertyTest;

import jssc.SerialPort;
import ua.pp.fairwind.communications.devices.hardwaredevices.arg.micro.ArgMicroDevice;
import ua.pp.fairwind.communications.devices.hardwaredevices.favorit.FavoritCoreDeviceV1;
import ua.pp.fairwind.communications.devices.logging.LineMonitorInterface;
import ua.pp.fairwind.communications.devices.logging.LineMonitoringEvent;
import ua.pp.fairwind.communications.devices.logging.LoggingDevice;
import ua.pp.fairwind.communications.lines.SerialLine;
import ua.pp.fairwind.communications.lines.lineparams.CommunicationLineParameters;
import ua.pp.fairwind.communications.messagesystems.event.ElementEventListener;

/**
 * Created by Сергей on 17.08.2015.
 */
public class LineConsoleTestARG {
    public static void main(String[] args) {
        SerialLine line = new SerialLine("com9", "RS232 Line#1", null, 5000);
        FavoritCoreDeviceV1 favorit = new FavoritCoreDeviceV1(0x1L);
        LoggingDevice ldev = new LoggingDevice("Logging Device", null, new LineMonitorInterface() {
            @Override
            public void monitor(LineMonitoringEvent event) {
                System.out.println(event);
            }
        });
        line.addWriteMonitoringDevice(ldev);
        line.addReadMonitoringDevice(ldev);
        line.setLineSelector(favorit);

        ArgMicroDevice argMicro = new ArgMicroDevice(10L, "ARG", null);
        argMicro.setLineParameters(new CommunicationLineParameters(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE,
                SerialPort.FLOWCONTROL_NONE, 4));
        argMicro.setReadTimeOut(1050);
        argMicro.setPrimerayLine(line);

        argMicro.addEventListener(ElementEventListener.println);
        argMicro.getNumberMeasurementm().addEventListener(ElementEventListener.println);
        argMicro.getRate().addEventListener(ElementEventListener.println);
        argMicro.getRate().readValueRequest();

        //fav.getLineSelect().setRequestTrunsaction(new OperationTrunsactionReadWriteSeparate());

        /*fav.getRate().setPropertyTimeOutReadAddon(50);
        fav.getRate().setValue(5.6f);
        fav.getRate().writeValueRequest();
        fav.getRate().readValueRequest();*/

    }
}

package ua.pp.fairwind.communications.test.propertyTest;

import jssc.SerialPort;
import ua.pp.fairwind.communications.devices.hardwaredevices.Baumer.Encoder;
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
public class LineConsoleTestBaumer {
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

        Encoder fav = new Encoder(3L, "Encoder", null);
        fav.setPauseBeforeRead(100L);
        fav.setReadTimeOut(3500L);
        fav.setPrimerayLine(line);
        fav.setLineParameters(new CommunicationLineParameters(SerialPort.BAUDRATE_38400, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE, SerialPort.FLOWCONTROL_NONE, 4));

        fav.addEventListener(ElementEventListener.println);
        fav.getSteps().addEventListener(ElementEventListener.println);
        fav.getRevolution().addEventListener(ElementEventListener.println);
        fav.getSteps().readValueRequest();

        //fav.getLineSelect().setRequestTrunsaction(new OperationTrunsactionReadWriteSeparate());

        /*fav.getRate().setPropertyTimeOutReadAddon(50);
        fav.getRate().setValue(5.6f);
        fav.getRate().writeValueRequest();
        fav.getRate().readValueRequest();*/

    }
}

package ua.pp.fairwind.communications.test.propertyTest;

import jssc.SerialPort;
import ua.pp.fairwind.communications.devices.hardwaredevices.ecotest.BDBG09;
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
public class LineConsoleTestBDBG {
    public static void main(String[] args) {
        SerialLine line = new SerialLine("com6", "RS232 Line#1", null, 5000);
        FavoritCoreDeviceV1 favorit = new FavoritCoreDeviceV1(0x1L);
        LoggingDevice ldev = new LoggingDevice("Logging Device", null, new LineMonitorInterface() {
            @Override
            public void monitor(LineMonitoringEvent event) {
                System.out.println(event);
            }
        });
        line.addWriteMonitoringDevice(ldev);
        line.addReadMonitoringDevice(ldev);
        //line.setLineSelector(favorit);

        BDBG09 bdbg09 = new BDBG09(1L);
        bdbg09.setPauseBeforeRead(100L);
        bdbg09.setReadTimeOut(500L);
        bdbg09.setPrimerayLine(line);
        bdbg09.setLineParameters(new CommunicationLineParameters(SerialPort.BAUDRATE_19200, SerialPort.DATABITS_8, SerialPort.PARITY_NONE, SerialPort.STOPBITS_1, SerialPort.FLOWCONTROL_NONE, 4));

        bdbg09.addEventListener(ElementEventListener.println);
        bdbg09.getMed().addEventListener(ElementEventListener.println);
        bdbg09.getMiss().addEventListener(ElementEventListener.println);
        bdbg09.getTemp().addEventListener(ElementEventListener.println);
        bdbg09.getSerial_number().addEventListener(ElementEventListener.println);
        bdbg09.getGROUP_MED().readValueRequest();
        bdbg09.getSerial_number().readValueRequest();
        bdbg09.getTemp().readValueRequest();

        //bdbg09.getLineSelect().setRequestTrunsaction(new OperationTrunsactionReadWriteSeparate());

        /*bdbg09.getRate().setPropertyTimeOutReadAddon(50);
        bdbg09.getRate().setValue(5.6f);
        bdbg09.getRate().writeValueRequest();
        bdbg09.getRate().readValueRequest();*/

    }
}

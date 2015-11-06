package ua.pp.fairwind.communications.test.propertyTest;

import jssc.SerialPort;
import ua.pp.fairwind.communications.devices.hardwaredevices.positron.BDMG04;
import ua.pp.fairwind.communications.devices.logging.LineMonitorInterface;
import ua.pp.fairwind.communications.devices.logging.LineMonitoringEvent;
import ua.pp.fairwind.communications.devices.logging.LoggingDevice;
import ua.pp.fairwind.communications.lines.SerialLine;
import ua.pp.fairwind.communications.lines.lineparams.CommunicationLineParameters;

/**
 * Created by Сергей on 17.08.2015.
 */
public class LineConsoleTestBDMG04_MODBUS {
    public static void main(String[] args) {
        SerialLine line = new SerialLine("com9", "RS232 Line#1", null, 5000);
        BDMG04 bdmg04 = new BDMG04(0x4L, "Favirit Ventel", null);
        bdmg04.setLineParameters(new CommunicationLineParameters(SerialPort.BAUDRATE_19200,SerialPort.DATABITS_8,SerialPort.PARITY_NONE,SerialPort.STOPBITS_2));
        LoggingDevice ldev = new LoggingDevice("Logging Device", null, new LineMonitorInterface() {
            @Override
            public void monitor(LineMonitoringEvent event) {
                System.out.println(event);
            }
        });
        //line.addEventListener((element, type, param) -> System.err.println(type + " " + element + " " + param));
        line.addWriteMonitoringDevice(ldev);
        line.addReadMonitoringDevice(ldev);
        bdmg04.getBdmg04Protocol().setValue(BDMG04.PROTOCOL_VERSON_124);
        bdmg04.setPrimerayLine(line);
        bdmg04.getDeviceInfo().getPopertyByIndex(0).addEventListener((event, object) -> {
            System.out.println(event.getSourceElement());
        });
        bdmg04.getDeviceInfo().getPopertyByIndex(1).addEventListener((event, object) -> {
            System.out.println(event.getSourceElement());
        });
        bdmg04.getDeviceInfo().getPopertyByIndex(2).addEventListener((event, object) -> {
            System.out.println(event.getSourceElement());
        });
        bdmg04.getDeviceInfo().getPopertyByIndex(3).addEventListener((event, object) -> {
            System.out.println(event.getSourceElement());
        });
        bdmg04.getDeviceInfo().getPopertyByIndex(4).addEventListener((event, object) -> {
            System.out.println(event.getSourceElement());
        });
        bdmg04.getDeviceInfo().getPopertyByIndex(5).addEventListener((event, object) -> {
            System.out.println(event.getSourceElement());
        });
        for(int i=0;i<34;i++) {
            bdmg04.getConfiguration().getPopertyByIndex(i).addEventListener((event, object) -> {
                System.out.println(event.getSourceElement());
            });
        }

        //bdmg04.getConfiguration().readValueRequest();
        bdmg04.getDeviceInfo().readValueRequest();

    }
}

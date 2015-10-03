package ua.pp.fairwind.communications.test.propertyTest;

import ua.pp.fairwind.communications.devices.hardwaredevices.akon.AkonBase;
import ua.pp.fairwind.communications.devices.logging.LineMonitorInterface;
import ua.pp.fairwind.communications.devices.logging.LineMonitoringEvent;
import ua.pp.fairwind.communications.devices.logging.LoggingDevice;
import ua.pp.fairwind.communications.lines.SerialLine;
import ua.pp.fairwind.communications.propertyes.binding.ByteToLongConvertor;
import ua.pp.fairwind.communications.propertyes.software.SoftByteProperty;

/**
 * Created by Сергей on 17.08.2015.
 */
public class LineConsoleTestAKON_OBJ {
    public static void main(String[] args) {
        SerialLine line = new SerialLine("com9", "RS232 Line#1", null, 5000);
        AkonBase akon = new AkonBase(0xAL, "Favirit Ventel", null);
        LoggingDevice ldev = new LoggingDevice("Logging Device", null, new LineMonitorInterface() {
            @Override
            public void monitor(LineMonitoringEvent event) {
                System.out.println(event);
            }
        });
        //line.addEventListener((element, type, param) -> System.err.println(type + " " + element + " " + param));
        //line.addWriteMonitoringDevice(ldev);
        //line.addReadMonitoringDevice(ldev);
        akon.setPrimerayLine(line);
        akon.getSerialNumber().addChangeEventListener((event) -> System.out.println(event.getNameFrom() + " = " + event.getNewValue()));
        //akon.getSerialNumber().readValueRequest();
        SoftByteProperty bt = new SoftByteProperty("test");
        bt.addChangeEventListener((event) -> System.out.println(event.getNameFrom() + " = " + event.getNewValue()));
        akon.getSystemObject().get("SERIAL NUMBER").readValueRequest();
        akon.getSystemObject().get("TIME").readValueRequest();

        ByteToLongConvertor.createByteToLongConvertor(bt, akon.getSerialNumber(), 1).bind();
        bt.setValue((byte) 1);
        akon.getSystemObject().get("SERIAL NUMBER").readValueRequest();


        //fav.getLineSelect().setRequestTrunsaction(new OperationTrunsactionReadWriteSeparate());

        /*fav.getRate().setPropertyTimeOutReadAddon(50);
        fav.getRate().setValue(5.6f);
        fav.getRate().writeValueRequest();
        fav.getRate().readValueRequest();*/

    }
}

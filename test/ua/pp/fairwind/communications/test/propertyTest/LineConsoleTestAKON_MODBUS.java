package ua.pp.fairwind.communications.test.propertyTest;

import ua.pp.fairwind.communications.devices.hardwaredevices.akon.AkonBase;
import ua.pp.fairwind.communications.devices.logging.LineMonitorInterface;
import ua.pp.fairwind.communications.devices.logging.LineMonitoringEvent;
import ua.pp.fairwind.communications.devices.logging.LoggingDevice;
import ua.pp.fairwind.communications.lines.SerialLine;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystemMultiDipatch;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.utils.CommunicationUtils;

/**
 * Created by Сергей on 17.08.2015.
 */
public class LineConsoleTestAKON_MODBUS {
    public static void main(String[] args) {
        MessageSubSystem ms=new MessageSubSystemMultiDipatch();
        SerialLine line=new SerialLine("com9","RS232 Line#1",null,"Serial port",ms,5000);
        AkonBase akon=new AkonBase(0xAL,"Favirit Ventel",null,"",ms,null);
        LoggingDevice ldev=new LoggingDevice("Logging Device", null, "", null, new LineMonitorInterface() {
            @Override
            public void monitor(LineMonitoringEvent event) {
                System.out.println(event);
            }
        });
        //line.addEventListener((element, type, param) -> System.err.println(type + " " + element + " " + param));
        //line.addWriteMonitoringDevice(ldev);
        //line.addReadMonitoringDevice(ldev);
        akon.getAkonProtocol().setValue(AkonBase.MODBUS_PROTOCOL);
        akon.setPrimerayLine(line);
        akon.getSerialNumber().addChangeEventListener((event) -> System.out.println(event.getNameFrom() + " = " + event.getNewValue()));
        ((ValueProperty)(akon.getSystemObject().get("TIME"))).addChangeEventListener((event) -> System.out.println(event.getNameFrom() + " = " + event.getNewValue()));
        //akon.getSerialNumber().readValueRequest();
        //SoftByteProperty bt=new SoftByteProperty("test");
        //bt.addChangeEventListener((event) -> System.out.println(event.getNameFrom() + " = " + event.getNewValue()));
        akon.getAkonSetupedProtocol().readValueRequest();
        akon.getSystemObject().get("SERIAL NUMBER").readValueRequest();
        akon.getSystemObject().get("TIME").readValueRequest();
        akon.getAkonSetupedProtocol().setValue((byte) 1);
        akon.getAkonSetupedProtocol().writeValueRequest();

        CommunicationUtils.RealThreadPause(10000);
        ms.destroyService();

        //ByteToLongConvertor.createByteToLongConvertor(bt, akon.getSerialNumber(), 1, ms).bind();
        //bt.setValue((byte) 1);
        //akon.getSystemObject().get("SERIAL NUMBER").readValueRequest();


        //fav.getLineSelect().setRequestTrunsaction(new OperationTrunsactionReadWriteSeparate());

        /*fav.getRate().setPropertyTimeOutReadAddon(50);
        fav.getRate().setValue(5.6f);
        fav.getRate().writeValueRequest();
        fav.getRate().readValueRequest();*/

    }
}

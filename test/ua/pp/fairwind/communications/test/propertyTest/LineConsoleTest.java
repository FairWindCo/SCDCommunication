package ua.pp.fairwind.communications.test.propertyTest;

import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.devices.favorit.FavoritCoreDeviceV1;
import ua.pp.fairwind.communications.devices.logging.LineMonitorInterface;
import ua.pp.fairwind.communications.devices.logging.LineMonitoringEvent;
import ua.pp.fairwind.communications.devices.logging.LoggingDevice;
import ua.pp.fairwind.communications.lines.SerialLine;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.propertyTrunsactions.OperationTrunsactionReadWriteSeparate;
import ua.pp.fairwind.communications.propertyes.event.ElementEventListener;
import ua.pp.fairwind.communications.propertyes.event.EventType;

/**
 * Created by Сергей on 17.08.2015.
 */
public class LineConsoleTest {
    public static void main(String[] args) {
        SerialLine line=new SerialLine("com5","RS232 Line#1",null,"Serial port",null,5000);
        LoggingDevice ldev=new LoggingDevice(0x000, "Logging Device", null, "", null, new LineMonitorInterface() {
            @Override
            public void monitor(LineMonitoringEvent event) {
                System.out.println(event);
            }
        });
        //line.addWriteMonitoringDevice(ldev);
        FavoritCoreDeviceV1 fav=new FavoritCoreDeviceV1(1,"Ventil",null,"Ventil plate",(MessageSubSystem)null);
        fav.setReadTimeOut(150);

        fav.setPrimerayLine(line);
        fav.addEventListener(new ElementEventListener() {
            @Override
            public void elementEvent(ElementInterface element, EventType typeEvent, Object params) {
                System.out.println(typeEvent + " : " + element.toString() + " - " + params);
            }
        });
        fav.getDigitalInChanelN1().addEventListener((element, typeEvent, params) -> System.out.println(typeEvent + " : " + element.toString() + " - " + params));
        fav.getDigitalInChanelN1().readValueRequest();
        fav.getDigitalOutChanelN1().addEventListener((element, typeEvent, params) -> System.out.println(typeEvent + " : " + element.toString() + " - " + params));
        fav.getDigitalOutChanelN2().addEventListener((element, typeEvent, params) -> System.out.println(typeEvent + " : " + element.toString() + " - " + params));
        fav.getDigitalOutChanelN3().addEventListener((element, typeEvent, params) -> System.out.println(typeEvent + " : " + element.toString() + " - " + params));
        fav.getDigitalOutChanelN4().addEventListener((element, typeEvent, params) -> System.out.println(typeEvent + " : " + element.toString() + " - " + params));
        fav.getDigitalOutChanelN5().addEventListener((element, typeEvent, params) -> System.out.println(typeEvent + " : " + element.toString() + " - " + params));
        fav.getDigitalOutChanelN6().addEventListener((element, typeEvent, params) -> System.out.println(typeEvent + " : " + element.toString() + " - " + params));
        fav.getAnalogInChanelN1().addEventListener((element, typeEvent, params) -> System.out.println(typeEvent + " : " + element.toString() + " - " + params));
        fav.getAnalogInChanelN2().addEventListener((element, typeEvent, params) -> System.out.println(typeEvent + " : " + element.toString() + " - " + params));
        fav.getAnalogInChanelN3().addEventListener((element, typeEvent, params) -> System.out.println(typeEvent + " : " + element.toString() + " - " + params));
        fav.getAnalogInChanelN4().addEventListener((element, typeEvent, params) -> System.out.println(typeEvent + " : " + element.toString() + " - " + params));
        fav.getAnalogOutChanelN1().addEventListener((element, typeEvent, params) -> System.out.println(typeEvent + " : " + element.toString() + " - " + params));
        fav.getAnalogOutChanelN2().addEventListener((element, typeEvent, params) -> System.out.println(typeEvent + " : " + element.toString() + " - " + params));
        fav.getAnalogOutChanelN3().addEventListener((element, typeEvent, params) -> System.out.println(typeEvent + " : " + element.toString() + " - " + params));
        fav.getAnalogOutChanelN4().addEventListener((element, typeEvent, params) -> System.out.println(typeEvent + " : " + element.toString() + " - " + params));
        fav.getLineSelect().addEventListener((element, typeEvent, params) -> System.out.println(typeEvent + " : " + element.toString() + " - " + params));
        fav.getLineSelect().setRequestTrunsaction(new OperationTrunsactionReadWriteSeparate());

        fav.getDigitalOutChanelN1().setAdditionalInfo(FavoritCoreDeviceV1.IMIDIATLY_WRITE_FLAG, true);
        fav.getDigitalOutChanelN2().setAdditionalInfo(FavoritCoreDeviceV1.IMIDIATLY_WRITE_FLAG, true);
        fav.getDigitalOutChanelN3().setAdditionalInfo(FavoritCoreDeviceV1.IMIDIATLY_WRITE_FLAG, true);
        fav.getDigitalOutChanelN4().setAdditionalInfo(FavoritCoreDeviceV1.IMIDIATLY_WRITE_FLAG, true);
        fav.getDigitalOutChanelN5().setAdditionalInfo(FavoritCoreDeviceV1.IMIDIATLY_WRITE_FLAG, true);
        fav.getDigitalOutChanelN6().setAdditionalInfo(FavoritCoreDeviceV1.IMIDIATLY_WRITE_FLAG, true);

        /*
        fav.getDigitalOutChanelN1().writeValueRequest();
        fav.getDigitalOutChanelN2().writeValueRequest();
        fav.getDigitalOutChanelN3().writeValueRequest();
        fav.getDigitalOutChanelN4().writeValueRequest();
        fav.getDigitalOutChanelN5().writeValueRequest();
        fav.getDigitalOutChanelN6().writeValueRequest();
        */
/*
        fav.getDigitalOutChanelN1().setValue(false);
        fav.getDigitalOutChanelN2().setValue(false);
        fav.getDigitalOutChanelN3().setValue(false);
        fav.getDigitalOutChanelN4().setValue(false);
        fav.getDigitalOutChanelN5().setValue(false);
        fav.getDigitalOutChanelN6().setValue(false);
        */

        fav.getDigitalOutChanelN1().readValueRequest();
        fav.getDigitalOutChanelN2().readValueRequest();
        fav.getDigitalOutChanelN3().readValueRequest();
        fav.getDigitalOutChanelN4().readValueRequest();
        fav.getDigitalOutChanelN5().readValueRequest();
        fav.getDigitalOutChanelN6().readValueRequest();

        fav.getAnalogInChanelN1().readValueRequest();
        fav.getAnalogOutChanelN1().readValueRequest();
        fav.getAnalogOutChanelN1().setPropertyTimeOutReadAddon(50);
        fav.getAnalogOutChanelN1().setValue(5.6f);
        fav.getAnalogOutChanelN1().writeValueRequest();
        fav.getDigitalOutChanelN3().setValue(true);
        fav.getLineSelect().readValueRequest();
        fav.getLineSelect().setValue(3l);
        fav.getLineSelect().writeValueRequest();

        /*
        fav.getDigitalOutChanelN1().setValue(true);
        fav.getDigitalOutChanelN2().setValue(true);
        fav.getDigitalOutChanelN3().setValue(true);
        fav.getDigitalOutChanelN4().setValue(true);
        fav.getDigitalOutChanelN5().setValue(true);
        fav.getDigitalOutChanelN6().setValue(true);

        fav.getDigitalOutChanelN1().readValueRequest();
        fav.getDigitalOutChanelN2().readValueRequest();
        fav.getDigitalOutChanelN3().readValueRequest();
        fav.getDigitalOutChanelN4().readValueRequest();
        fav.getDigitalOutChanelN5().readValueRequest();
        fav.getDigitalOutChanelN6().readValueRequest();
        /*

        fav.getDigitalOutChanelN1().readValueRequest();
        fav.getDigitalOutChanelN2().readValueRequest();
        fav.getDigitalOutChanelN3().readValueRequest();
        fav.getDigitalOutChanelN4().readValueRequest();
        fav.getDigitalOutChanelN5().readValueRequest();
        fav.getDigitalOutChanelN6().readValueRequest();

        fav.getDigitalOutChanelN1().setValue(false);
        fav.getDigitalOutChanelN2().setValue(false);
        fav.getDigitalOutChanelN3().setValue(false);
        fav.getDigitalOutChanelN4().setValue(false);
        fav.getDigitalOutChanelN5().setValue(false);
        fav.getDigitalOutChanelN6().setValue(false);
        */
    }
}

package ua.pp.fairwind.communications.test.propertyTest;

import ua.pp.fairwind.communications.devices.hardwaredevices.favorit.FavoritCoreDeviceV1;
import ua.pp.fairwind.communications.devices.logging.LineMonitorInterface;
import ua.pp.fairwind.communications.devices.logging.LineMonitoringEvent;
import ua.pp.fairwind.communications.devices.logging.LoggingDevice;
import ua.pp.fairwind.communications.lines.SerialLine;
import ua.pp.fairwind.communications.messagesystems.event.ElementEventListener;
import ua.pp.fairwind.communications.propertyes.abstraction.propertyTrunsactions.OperationTrunsactionReadWriteSeparate;

/**
 * Created by Сергей on 17.08.2015.
 */
public class LineConsoleTest {
    public static void main(String[] args) {
        SerialLine line = new SerialLine("com5", "RS232 Line#1", null, 5000);
        LoggingDevice ldev = new LoggingDevice("Logging Device", null, new LineMonitorInterface() {
            @Override
            public void monitor(LineMonitoringEvent event) {
                System.out.println(event);
            }
        });
        //line.addWriteMonitoringDevice(ldev);
        FavoritCoreDeviceV1 fav = new FavoritCoreDeviceV1(1, "Ventil", null);
        fav.setReadTimeOut(150);

        fav.setPrimerayLine(line);
        fav.addEventListener(ElementEventListener.println);
        fav.getDigitalInChanelN1().addEventListener(ElementEventListener.println);
        fav.getDigitalInChanelN1().readValueRequest();
        fav.getDigitalOutChanelN1().addEventListener(ElementEventListener.println);
        fav.getDigitalOutChanelN2().addEventListener(ElementEventListener.println);
        fav.getDigitalOutChanelN3().addEventListener(ElementEventListener.println);
        fav.getDigitalOutChanelN4().addEventListener(ElementEventListener.println);
        fav.getDigitalOutChanelN5().addEventListener(ElementEventListener.println);
        fav.getDigitalOutChanelN6().addEventListener(ElementEventListener.println);
        fav.getAnalogInChanelN1().addEventListener(ElementEventListener.println);
        fav.getAnalogInChanelN2().addEventListener(ElementEventListener.println);
        fav.getAnalogInChanelN3().addEventListener(ElementEventListener.println);
        fav.getAnalogInChanelN4().addEventListener(ElementEventListener.println);
        fav.getAnalogOutChanelN1().addEventListener(ElementEventListener.println);
        fav.getAnalogOutChanelN2().addEventListener(ElementEventListener.println);
        fav.getAnalogOutChanelN3().addEventListener(ElementEventListener.println);
        fav.getAnalogOutChanelN4().addEventListener(ElementEventListener.println);
        fav.getLineSelect().addEventListener(ElementEventListener.println);
        fav.getLineSelect().setRequestTrunsaction(new OperationTrunsactionReadWriteSeparate());

        fav.getDigitalOutChanelN1().setAdditionalInfo(FavoritCoreDeviceV1.IMMEDIATELY_WRITE_FLAG, true);
        fav.getDigitalOutChanelN2().setAdditionalInfo(FavoritCoreDeviceV1.IMMEDIATELY_WRITE_FLAG, true);
        fav.getDigitalOutChanelN3().setAdditionalInfo(FavoritCoreDeviceV1.IMMEDIATELY_WRITE_FLAG, true);
        fav.getDigitalOutChanelN4().setAdditionalInfo(FavoritCoreDeviceV1.IMMEDIATELY_WRITE_FLAG, true);
        fav.getDigitalOutChanelN5().setAdditionalInfo(FavoritCoreDeviceV1.IMMEDIATELY_WRITE_FLAG, true);
        fav.getDigitalOutChanelN6().setAdditionalInfo(FavoritCoreDeviceV1.IMMEDIATELY_WRITE_FLAG, true);

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

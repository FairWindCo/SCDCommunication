package ua.pp.fairwind.communications.test.propertyTest;

import ua.pp.fairwind.communications.devices.favorit.FavoritCoreDeviceV1;
import ua.pp.fairwind.communications.lines.SerialLine;

/**
 * Created by Сергей on 17.08.2015.
 */
public class LineConsoleTest {
    public static void main(String[] args) {
        SerialLine line=new SerialLine("com1","RS232 Line#1",null,"Serial port",null,5000);
        FavoritCoreDeviceV1 fav=new FavoritCoreDeviceV1(1,"Ventil",null,"Ventil plate",null);
        fav.setPrimerayLine(line);

        fav.getDigitalInChanelN1().setValue(true);
        fav.getDigitalInChanelN1().writeValueRequest();
    }
}

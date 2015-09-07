package ua.pp.fairwind.communications.test.propertyTest;

import ua.pp.fairwind.communications.devices.favorit.FavoritCoreDeviceV1;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;

import java.util.Arrays;

/**
 * Created by Сергей on 09.07.2015.
 */
public class DeviceTest {
    public static void main(String[] args) {
        FavoritCoreDeviceV1 dev=new FavoritCoreDeviceV1(1L,"Test Device",null,"Device for test",(MessageSubSystem)null);
        String[] lst=dev.getPropertyesName();
        Arrays.stream(lst).forEach(System.out::println);
        String[] lstcmd=dev.getCommandsName();
        Arrays.stream(lstcmd).forEach(System.out::println);
        System.out.println(dev.getProperty("LAST_COMMUNICATION_STATUS"));
        System.out.println(dev.getProperty("LAST_COMMUNICATION_STATUS2"));
        System.out.println(dev.getCommand("VALIDATE_COMMAND"));
        System.out.println(dev.getCommand("VALIDATE_COMMAND2"));
    }
}

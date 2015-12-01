package ua.pp.fairwind.communications.devices.hardwaredevices.dp5;

import ua.pp.fairwind.communications.propertyes.groups.GroupProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;

/**
 * Created by Сергей on 01.12.2015.
 */
public class EthernetSettingsDP5Chanel extends GroupProperty{
    final private NewEthernetSettingsDP5Chanel settingsDP5Chanel=new NewEthernetSettingsDP5Chanel("Ethenet Setting of DP5 module IP Config",null);
    final private SoftShortProperty mac1=new SoftShortProperty("MAC address");
    final private SoftShortProperty mac2=new SoftShortProperty("MAC address");
    final private SoftShortProperty mac3=new SoftShortProperty("MAC address");
    final private SoftShortProperty mac4=new SoftShortProperty("MAC address");
    final private SoftShortProperty mac5=new SoftShortProperty("MAC address");
    final private SoftShortProperty mac6=new SoftShortProperty("MAC address");

    public EthernetSettingsDP5Chanel(String name) {
        super(name);
        addPropertyies(settingsDP5Chanel,mac1,mac2,mac3,mac4,mac5,mac6);
    }

    public NewEthernetSettingsDP5Chanel getSettingsDP5Chanel() {
        return settingsDP5Chanel;
    }

    public SoftShortProperty getMac1() {
        return mac1;
    }

    public SoftShortProperty getMac2() {
        return mac2;
    }

    public SoftShortProperty getMac3() {
        return mac3;
    }

    public SoftShortProperty getMac4() {
        return mac4;
    }

    public SoftShortProperty getMac5() {
        return mac5;
    }

    public SoftShortProperty getMac6() {
        return mac6;
    }
}

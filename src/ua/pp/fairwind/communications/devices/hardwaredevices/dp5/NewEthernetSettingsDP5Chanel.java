package ua.pp.fairwind.communications.devices.hardwaredevices.dp5;

import ua.pp.fairwind.communications.propertyes.groups.GroupProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftBoolProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;

/**
 * Created by Сергей on 01.12.2015.
 */
public class NewEthernetSettingsDP5Chanel extends GroupProperty {
    final private SoftBoolProperty useDHCP=new SoftBoolProperty("Ethernet DHCP Status", false);
    final private SoftShortProperty ip1=new SoftShortProperty("IP address");
    final private SoftShortProperty ip2=new SoftShortProperty("IP address");
    final private SoftShortProperty ip3=new SoftShortProperty("IP address");
    final private SoftShortProperty ip4=new SoftShortProperty("IP address");

    final private SoftShortProperty mask1=new SoftShortProperty("Subnet Mask");
    final private SoftShortProperty mask2=new SoftShortProperty("Subnet Mask");
    final private SoftShortProperty mask3=new SoftShortProperty("Subnet Mask");
    final private SoftShortProperty mask4=new SoftShortProperty("Subnet Mask");
    final private SoftShortProperty gate1=new SoftShortProperty("Default Gateway");
    final private SoftShortProperty gate2=new SoftShortProperty("Default Gateway");
    final private SoftShortProperty gate3=new SoftShortProperty("Default Gateway");
    final private SoftShortProperty gate4=new SoftShortProperty("Default Gateway");

    final private SoftShortProperty rip1=new SoftShortProperty("IP address", (short)0);
    final private SoftShortProperty rip2=new SoftShortProperty("IP address", (short)0);
    final private SoftShortProperty rip3=new SoftShortProperty("IP address", (short)0);
    final private SoftShortProperty rip4=new SoftShortProperty("IP address", (short)0);
    final private SoftShortProperty port=new SoftShortProperty("port",(short)10001);

    public NewEthernetSettingsDP5Chanel(String name, String uuid) {
        super(name, uuid);
        addPropertyies(useDHCP,ip1,ip2,ip3,ip4,mask1,mask2,mask3,mask4,gate1,gate2,gate3,gate4,rip1,rip2,rip3,rip4,port);
    }

    public SoftBoolProperty getUseDHCP() {
        return useDHCP;
    }

    public SoftShortProperty getIp1() {
        return ip1;
    }

    public SoftShortProperty getIp2() {
        return ip2;
    }

    public SoftShortProperty getIp3() {
        return ip3;
    }

    public SoftShortProperty getIp4() {
        return ip4;
    }

    public SoftShortProperty getMask1() {
        return mask1;
    }

    public SoftShortProperty getMask2() {
        return mask2;
    }

    public SoftShortProperty getMask3() {
        return mask3;
    }

    public SoftShortProperty getMask4() {
        return mask4;
    }

    public SoftShortProperty getGate1() {
        return gate1;
    }

    public SoftShortProperty getGate2() {
        return gate2;
    }

    public SoftShortProperty getGate3() {
        return gate3;
    }

    public SoftShortProperty getGate4() {
        return gate4;
    }

    public SoftShortProperty getRip1() {
        return rip1;
    }

    public SoftShortProperty getRip2() {
        return rip2;
    }

    public SoftShortProperty getRip3() {
        return rip3;
    }

    public SoftShortProperty getRip4() {
        return rip4;
    }

    public SoftShortProperty getPort() {
        return port;
    }
}

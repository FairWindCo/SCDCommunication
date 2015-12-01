package ua.pp.fairwind.communications.devices.hardwaredevices.dp5;

import ua.pp.fairwind.communications.propertyes.groups.GroupProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftByteProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftStringProperty;

/**
 * Created by Сергей on 01.12.2015.
 */
public class IdentifyDPChanel extends GroupProperty {
    final private SoftByteProperty connectType=new SoftByteProperty("Ethernet General UDP Port Status");
    final private SoftShortProperty rnd=new SoftShortProperty("Replication of Random 16-bit sequence ID received");
    final private SoftShortProperty dayEv1=new SoftShortProperty("Event 1 Days");
    final private SoftShortProperty hourEv1=new SoftShortProperty("Event 1 Hours");
    final private SoftShortProperty minEv1=new SoftShortProperty("Event 1 Minutes");
    final private SoftShortProperty dayEv2=new SoftShortProperty("Event 1 Days");
    final private SoftShortProperty hourEv2=new SoftShortProperty("Event 1 Hours");
    final private SoftShortProperty minEv2=new SoftShortProperty("Event 1 Minutes");
    final private SoftShortProperty secEv1=new SoftShortProperty("Event 1 Seconds");
    final private SoftShortProperty secEv2=new SoftShortProperty("Event 2 Seconds");

    final private SoftShortProperty mac1=new SoftShortProperty("MAC address");
    final private SoftShortProperty mac2=new SoftShortProperty("MAC address");
    final private SoftShortProperty mac3=new SoftShortProperty("MAC address");
    final private SoftShortProperty mac4=new SoftShortProperty("MAC address");
    final private SoftShortProperty mac5=new SoftShortProperty("MAC address");
    final private SoftShortProperty mac6=new SoftShortProperty("MAC address");

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

    final private SoftStringProperty nameModuleChanel=new SoftStringProperty("number");
    final private SoftStringProperty text=new SoftStringProperty("Description/misc text");
    final private SoftStringProperty event1=new SoftStringProperty("Event 1 description");
    final private SoftStringProperty event2=new SoftStringProperty("Event 2 description");

    public IdentifyDPChanel(String name, String uuid) {
        super(name, uuid,null);
        addPropertyies(connectType,rnd,dayEv1,hourEv1,minEv1,dayEv2,hourEv2,minEv2,secEv1,secEv2,mac1,mac2,mac3,mac4,mac5,mac6,
                ip1,ip2,ip3,ip4,mask1,mask2,mask3,mask4,gate1,gate2,gate3,gate4,nameModuleChanel,text,event1,event2);
    }

    public SoftByteProperty getConnectType() {
        return connectType;
    }

    public SoftShortProperty getRnd() {
        return rnd;
    }

    public SoftShortProperty getDayEv1() {
        return dayEv1;
    }

    public SoftShortProperty getHourEv1() {
        return hourEv1;
    }

    public SoftShortProperty getMinEv1() {
        return minEv1;
    }

    public SoftShortProperty getDayEv2() {
        return dayEv2;
    }

    public SoftShortProperty getHourEv2() {
        return hourEv2;
    }

    public SoftShortProperty getMinEv2() {
        return minEv2;
    }

    public SoftShortProperty getSecEv1() {
        return secEv1;
    }

    public SoftShortProperty getSecEv2() {
        return secEv2;
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

    public SoftStringProperty getNameModuleChanel() {
        return nameModuleChanel;
    }

    public SoftStringProperty getText() {
        return text;
    }

    public SoftStringProperty getEvent1() {
        return event1;
    }

    public SoftStringProperty getEvent2() {
        return event2;
    }
}

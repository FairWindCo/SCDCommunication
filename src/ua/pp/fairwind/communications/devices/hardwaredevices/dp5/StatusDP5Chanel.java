package ua.pp.fairwind.communications.devices.hardwaredevices.dp5;

import ua.pp.fairwind.communications.propertyes.groups.GroupProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftByteProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftIntegerProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;

/**
 * Created by Сергей on 04.10.2015.
 */
public class StatusDP5Chanel extends GroupProperty {
    final private SoftIntegerProperty fastCount=new SoftIntegerProperty("Fast Count");
    final private SoftIntegerProperty slowCount=new SoftIntegerProperty("Slow Count");
    final private SoftIntegerProperty gpCount=new SoftIntegerProperty("General Purpose Counter");
    final private SoftIntegerProperty accTime=new SoftIntegerProperty("ACC TIME");
    final private SoftIntegerProperty tbd=new SoftIntegerProperty("TBD");
    final private SoftIntegerProperty realtime=new SoftIntegerProperty("Realtime");
    final private SoftByteProperty firmware=new SoftByteProperty("Firmware version");
    final private SoftByteProperty fpga=new SoftByteProperty("FPGA version");
    final private SoftIntegerProperty serial=new SoftIntegerProperty("Serial Number");
    final private SoftShortProperty hv=new SoftShortProperty("HV 0.5V/count");
    final private SoftShortProperty dettemp=new SoftShortProperty("Detector temperature");
    final private SoftByteProperty boradtemp=new SoftByteProperty("Board temp");
    final private SoftByteProperty stat1=new SoftByteProperty("Status 1");
    final private SoftByteProperty stat2=new SoftByteProperty("Status 2");
    final private SoftByteProperty frimwarenum=new SoftByteProperty("Firmware Build Number");
    final private SoftByteProperty stat3=new SoftByteProperty("Status 3");
    final private SoftByteProperty deviceID=new SoftByteProperty("device id");
    final private SoftShortProperty px5voltage=new SoftShortProperty("PX5 TEC voltage");
    final private SoftByteProperty options=new SoftByteProperty("options installed");
    final private SoftByteProperty stat4=new SoftByteProperty("Status 4");
    final private SoftByteProperty nc1=new SoftByteProperty("NC 1");
    final private SoftByteProperty nc2=new SoftByteProperty("NC 2");
    final private SoftByteProperty nc3=new SoftByteProperty("NC 3");
    final private SoftByteProperty nc4=new SoftByteProperty("NC 4");
    final private SoftByteProperty nc5=new SoftByteProperty("NC 5");
    final private SoftByteProperty nc6=new SoftByteProperty("NC 6");
    final private SoftByteProperty nc7=new SoftByteProperty("NC 7");
    final private SoftByteProperty nc8=new SoftByteProperty("NC 8");
    final private SoftByteProperty nc9=new SoftByteProperty("NC 9");
    final private SoftByteProperty nc10=new SoftByteProperty("NC 10");
    final private SoftByteProperty nc11=new SoftByteProperty("NC 11");
    final private SoftByteProperty nc12=new SoftByteProperty("NC 12");
    final private SoftByteProperty nc13=new SoftByteProperty("NC 13");
    final private SoftByteProperty nc14=new SoftByteProperty("NC 14");
    final private SoftByteProperty nc15=new SoftByteProperty("NC 15");
    final private SoftByteProperty nc16=new SoftByteProperty("NC 16");
    final private SoftByteProperty nc17=new SoftByteProperty("NC 17");
    final private SoftByteProperty nc18=new SoftByteProperty("NC 18");
    final private SoftByteProperty nc19=new SoftByteProperty("NC 19");
    final private SoftByteProperty nc20=new SoftByteProperty("NC 20");


    public StatusDP5Chanel(String name, String uuid) {
        super(name, uuid,null);
        addPropertyies(fastCount,slowCount,gpCount,accTime,tbd,realtime,firmware,fpga,serial,dettemp,hv,boradtemp,stat1,stat2,frimwarenum,stat3,deviceID,px5voltage,options,stat4,nc1,nc2,nc3,nc4,nc5,nc6,nc7,nc8,nc9,nc10,nc11,nc12,nc13,nc14,nc15,nc16,nc17,nc18,nc19,nc20);
    }

    public SoftIntegerProperty getFastCount() {
        return fastCount;
    }

    public SoftIntegerProperty getSlowCount() {
        return slowCount;
    }

    public SoftIntegerProperty getGpCount() {
        return gpCount;
    }

    public SoftIntegerProperty getAccTime() {
        return accTime;
    }

    public SoftIntegerProperty getTbd() {
        return tbd;
    }

    public SoftIntegerProperty getRealtime() {
        return realtime;
    }

    public SoftByteProperty getFirmware() {
        return firmware;
    }

    public SoftByteProperty getFpga() {
        return fpga;
    }

    public SoftIntegerProperty getSerial() {
        return serial;
    }

    public SoftShortProperty getHv() {
        return hv;
    }

    public SoftShortProperty getDettemp() {
        return dettemp;
    }

    public SoftByteProperty getBoradtemp() {
        return boradtemp;
    }

    public SoftByteProperty getStat1() {
        return stat1;
    }

    public SoftByteProperty getStat2() {
        return stat2;
    }

    public SoftByteProperty getFrimwarenum() {
        return frimwarenum;
    }

    public SoftByteProperty getStat3() {
        return stat3;
    }

    public SoftByteProperty getDeviceID() {
        return deviceID;
    }

    public SoftShortProperty getPx5voltage() {
        return px5voltage;
    }

    public SoftByteProperty getOptions() {
        return options;
    }

    public SoftByteProperty getStat4() {
        return stat4;
    }

    public SoftByteProperty getNc1() {
        return nc1;
    }

    public SoftByteProperty getNc2() {
        return nc2;
    }

    public SoftByteProperty getNc3() {
        return nc3;
    }

    public SoftByteProperty getNc4() {
        return nc4;
    }

    public SoftByteProperty getNc5() {
        return nc5;
    }

    public SoftByteProperty getNc6() {
        return nc6;
    }

    public SoftByteProperty getNc7() {
        return nc7;
    }

    public SoftByteProperty getNc8() {
        return nc8;
    }

    public SoftByteProperty getNc9() {
        return nc9;
    }

    public SoftByteProperty getNc10() {
        return nc10;
    }

    public SoftByteProperty getNc11() {
        return nc11;
    }

    public SoftByteProperty getNc12() {
        return nc12;
    }

    public SoftByteProperty getNc13() {
        return nc13;
    }

    public SoftByteProperty getNc14() {
        return nc14;
    }

    public SoftByteProperty getNc15() {
        return nc15;
    }

    public SoftByteProperty getNc16() {
        return nc16;
    }

    public SoftByteProperty getNc17() {
        return nc17;
    }

    public SoftByteProperty getNc18() {
        return nc18;
    }

    public SoftByteProperty getNc19() {
        return nc19;
    }

    public SoftByteProperty getNc20() {
        return nc20;
    }
}

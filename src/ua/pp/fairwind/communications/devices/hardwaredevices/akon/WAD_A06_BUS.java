package ua.pp.fairwind.communications.devices.hardwaredevices.akon;

import ua.pp.fairwind.communications.internatianalisation.I18N;
import ua.pp.fairwind.communications.propertyes.groups.GroupProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftFloatProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftIntegerProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;

/**
 * Created by Сергей on 23.09.2015.
 */
public class WAD_A06_BUS extends AkonBase {
    protected final GroupProperty chanel1;
    protected final GroupProperty chanel2;
    protected final GroupProperty chanel3;
    protected final GroupProperty chanel4;
    protected final GroupProperty chanel5;
    protected final GroupProperty chanel6;
    protected final GroupProperty checkController;
    protected final GroupProperty modBusValue;

    public WAD_A06_BUS(long address, String codename, String uuid) {
        super(address, codename, uuid);
        chanel1=formChanelGroup(1,codename);
        chanel2=formChanelGroup(2,codename);
        chanel3=formChanelGroup(3,codename);
        chanel4=formChanelGroup(4,codename);
        chanel5=formChanelGroup(5,codename);
        chanel6=formChanelGroup(6,codename);
        checkController=formController(7, codename);
        modBusValue=formSpecial(codename);
    }

    public WAD_A06_BUS(long address) {
        this(address, "WAD_A06_BUS", null);
    }

    private GroupProperty formChanelGroup(int groupNum,String deviceName){
        String name=deviceName+".chanel"+groupNum;
        SoftFloatProperty value=new SoftFloatProperty(deviceName+".chanel");
        value.setAdditionalInfo(OBJECT_NUM,groupNum);
        value.setAdditionalInfo(PROPERTY_NUM,0);
        value.setAdditionalInfo(PROPERTY_MODBUS, 0x10+(groupNum-1)*0x20);
        SoftShortProperty outParams=new SoftShortProperty(deviceName+".chanel");
        outParams.setAdditionalInfo(OBJECT_NUM,groupNum);
        outParams.setAdditionalInfo(PROPERTY_NUM,1);
        outParams.setAdditionalInfo(PROPERTY_MODBUS, 0x12+(groupNum-1)*0x20);
        SoftShortProperty diapason=new SoftShortProperty(deviceName+".chanel");
        diapason.setAdditionalInfo(PROPERTY_MODBUS, 0x14+(groupNum-1)*0x20);
        SoftShortProperty diapasonCount=new SoftShortProperty(deviceName+".chanel");
        diapasonCount.setAdditionalInfo(OBJECT_NUM,groupNum);
        diapasonCount.setAdditionalInfo(PROPERTY_NUM,0xE);
        diapasonCount.setAdditionalInfo(PROPERTY_MODBUS, 0x18+(groupNum-1)*0x20);
        SoftShortProperty countingClass=new SoftShortProperty(deviceName+".chanel");
        countingClass.setAdditionalInfo(OBJECT_NUM,groupNum);
        countingClass.setAdditionalInfo(PROPERTY_NUM,0x10);
        countingClass.setAdditionalInfo(PROPERTY_MODBUS, 0x16+(groupNum-1)*0x20);
        SoftIntegerProperty index=new SoftIntegerProperty(deviceName+".chanel");
        index.setAdditionalInfo(OBJECT_NUM,groupNum);
        index.setAdditionalInfo(PROPERTY_NUM,0x30);
        index.setAdditionalInfo(PROPERTY_MODBUS, 0x1C+(groupNum-1)*0x20);
        SoftLongProperty indexLongValue=new SoftLongProperty(deviceName+".chanel");
        indexLongValue.setAdditionalInfo(OBJECT_NUM,groupNum);
        indexLongValue.setAdditionalInfo(PROPERTY_NUM,0x31);
        indexLongValue.setAdditionalInfo(PROPERTY_MODBUS, 0x1E+(groupNum-1)*0x20);
        SoftFloatProperty indexFloatValue=new SoftFloatProperty(deviceName+".chanel");
        indexFloatValue.setAdditionalInfo(OBJECT_NUM,groupNum);
        indexFloatValue.setAdditionalInfo(PROPERTY_NUM,0x31);
        indexFloatValue.setAdditionalInfo(PROPERTY_MODBUS, 0x1E+(groupNum-1)*0x20);
        addPropertys(value);
        addPropertys(outParams);
        addPropertys(diapasonCount);
        addPropertys(countingClass);
        addPropertys(index);
        addPropertys(indexLongValue);
        addPropertys(indexFloatValue);
        addPropertys(diapason);
        GroupProperty chanel=new GroupProperty(name,null,value,outParams,diapasonCount,countingClass,index,indexLongValue,indexFloatValue,diapason);
        addPropertys(chanel);
        return chanel;
    }

    private GroupProperty formController(int groupNum,String deviceName){
        String name=deviceName+".controller.name";
        SoftShortProperty timeout=new SoftShortProperty(deviceName+".chanel");
        timeout.setAdditionalInfo(OBJECT_NUM,groupNum);
        timeout.setAdditionalInfo(PROPERTY_NUM,0);
        timeout.setAdditionalInfo(PROPERTY_MODBUS, 0xD0);
        SoftShortProperty mode=new SoftShortProperty(deviceName+".chanel");
        mode.setAdditionalInfo(OBJECT_NUM,groupNum);
        mode.setAdditionalInfo(PROPERTY_NUM,1);
        mode.setAdditionalInfo(PROPERTY_MODBUS, 0xD2);
        SoftShortProperty chanel_num=new SoftShortProperty(deviceName+".chanel");
        chanel_num.setAdditionalInfo(OBJECT_NUM,groupNum);
        chanel_num.setAdditionalInfo(PROPERTY_NUM,2);
        chanel_num.setAdditionalInfo(PROPERTY_MODBUS, 0xD4);
        SoftShortProperty value=new SoftShortProperty(deviceName+".chanel");
        value.setAdditionalInfo(OBJECT_NUM,groupNum);
        value.setAdditionalInfo(PROPERTY_NUM,3);
        value.setAdditionalInfo(PROPERTY_MODBUS, 0xD6);
        SoftShortProperty mask=new SoftShortProperty(deviceName+".chanel");
        mask.setAdditionalInfo(OBJECT_NUM,groupNum);
        mask.setAdditionalInfo(PROPERTY_NUM, 4);
        mask.setAdditionalInfo(PROPERTY_MODBUS, 0xD8);

        addPropertys(timeout);
        addPropertys(mode);
        addPropertys(chanel_num);
        addPropertys(value);
        addPropertys(mask);

        GroupProperty chanel=new GroupProperty(name,null,value,timeout,mode,chanel_num,value,mask);
        addPropertys(chanel);
        return chanel;
    }

    private GroupProperty formSpecial(String deviceName){
        String name=I18N.getLocalizedString("WAD");
        SoftShortProperty options=new SoftShortProperty(deviceName+".chanel");
        options.setAdditionalInfo(PROPERTY_MODBUS, 0x2000);
        SoftFloatProperty floattemp=new SoftFloatProperty(deviceName+".chanel");
        floattemp.setAdditionalInfo(PROPERTY_MODBUS, 0x2001);
        SoftFloatProperty valueChanel1Float=new SoftFloatProperty(deviceName+".chanel");
        valueChanel1Float.setAdditionalInfo(PROPERTY_MODBUS, 0x2003);
        SoftFloatProperty valueChanel2Float=new SoftFloatProperty(deviceName+".chanel");
        valueChanel2Float.setAdditionalInfo(PROPERTY_MODBUS, 0x2005);
        SoftFloatProperty valueChanel3Float=new SoftFloatProperty(deviceName+".chanel");
        valueChanel3Float.setAdditionalInfo(PROPERTY_MODBUS, 0x2007);
        SoftFloatProperty valueChanel4Float=new SoftFloatProperty(deviceName+".chanel");
        valueChanel4Float.setAdditionalInfo(PROPERTY_MODBUS, 0x2009);
        SoftFloatProperty valueChanel5Float=new SoftFloatProperty(deviceName+".chanel");
        valueChanel5Float.setAdditionalInfo(PROPERTY_MODBUS, 0x200B);
        SoftFloatProperty valueChanel6Float=new SoftFloatProperty(deviceName+".chanel");
        valueChanel6Float.setAdditionalInfo(PROPERTY_MODBUS, 0x200D);

        SoftIntegerProperty wordtemp=new SoftIntegerProperty(deviceName+".chanel");
        wordtemp.setAdditionalInfo(PROPERTY_MODBUS, 0x200F);
        SoftIntegerProperty valueChanel1Word=new SoftIntegerProperty(deviceName+".chanel");
        valueChanel1Word.setAdditionalInfo(PROPERTY_MODBUS, 0x2010);
        SoftIntegerProperty valueChanel2Word=new SoftIntegerProperty(deviceName+".chanel");
        valueChanel2Word.setAdditionalInfo(PROPERTY_MODBUS, 0x2011);
        SoftIntegerProperty valueChanel3Word=new SoftIntegerProperty(deviceName+".chanel");
        valueChanel3Word.setAdditionalInfo(PROPERTY_MODBUS, 0x2012);
        SoftIntegerProperty valueChanel4Word=new SoftIntegerProperty(deviceName+".chanel");
        valueChanel4Word.setAdditionalInfo(PROPERTY_MODBUS, 0x2013);
        SoftIntegerProperty valueChanel5Word=new SoftIntegerProperty(deviceName+".chanel");
        valueChanel5Word.setAdditionalInfo(PROPERTY_MODBUS, 0x2014);
        SoftIntegerProperty valueChanel6Word=new SoftIntegerProperty(deviceName+".chanel");
        valueChanel6Word.setAdditionalInfo(PROPERTY_MODBUS, 0x2015);

        addPropertys(options);
        addPropertys(floattemp);
        addPropertys(valueChanel1Float);
        addPropertys(valueChanel2Float);
        addPropertys(valueChanel3Float);
        addPropertys(valueChanel4Float);
        addPropertys(valueChanel5Float);
        addPropertys(valueChanel6Float);
        addPropertys(wordtemp);
        addPropertys(valueChanel1Word);
        addPropertys(valueChanel2Word);
        addPropertys(valueChanel3Word);
        addPropertys(valueChanel4Word);
        addPropertys(valueChanel5Word);
        addPropertys(valueChanel6Word);

        SoftFloatProperty value1=(SoftFloatProperty) chanel1.get("VALUE");
        valueChanel1Float.bindReadProperty(value1);
        valueChanel1Float.bindWriteProperty(value1);

        SoftFloatProperty value2=(SoftFloatProperty) chanel2.get("VALUE");
        valueChanel1Float.bindReadProperty(value2);
        valueChanel1Float.bindWriteProperty(value2);

        SoftFloatProperty value3=(SoftFloatProperty) chanel3.get("VALUE");
        valueChanel1Float.bindReadProperty(value3);
        valueChanel1Float.bindWriteProperty(value3);

        SoftFloatProperty value4=(SoftFloatProperty) chanel4.get("VALUE");
        valueChanel1Float.bindReadProperty(value4);
        valueChanel1Float.bindWriteProperty(value4);

        SoftFloatProperty value5=(SoftFloatProperty) chanel5.get("VALUE");
        valueChanel1Float.bindReadProperty(value5);
        valueChanel1Float.bindWriteProperty(value5);

        SoftFloatProperty value6=(SoftFloatProperty) chanel6.get("VALUE");
        valueChanel1Float.bindReadProperty(value6);
        valueChanel1Float.bindWriteProperty(value6);

        GroupProperty chanel=new GroupProperty(name,null,options,floattemp,valueChanel1Float,valueChanel2Float,valueChanel3Float,valueChanel4Float,valueChanel5Float,valueChanel6Float,wordtemp,valueChanel1Word,valueChanel2Word,valueChanel3Word,valueChanel4Word,valueChanel5Word,valueChanel6Word);
        addPropertys(chanel);
        return chanel;
    }

    public GroupProperty getChanel1() {
        return chanel1;
    }

    public GroupProperty getChanel2() {
        return chanel2;
    }

    public GroupProperty getChanel3() {
        return chanel3;
    }

    public GroupProperty getChanel4() {
        return chanel4;
    }

    public GroupProperty getChanel5() {
        return chanel5;
    }

    public GroupProperty getChanel6() {
        return chanel6;
    }

    public GroupProperty getCheckController() {
        return checkController;
    }


}

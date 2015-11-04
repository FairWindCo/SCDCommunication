package ua.pp.fairwind.communications.devices.hardwaredevices.positron;

import ua.pp.fairwind.communications.devices.RequestInformation;
import ua.pp.fairwind.communications.devices.abstracts.RSLineDevice;
import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.messagesystems.event.EventType;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.groups.GroupProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftByteProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftFloatProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;

/**
 * Created by Сергей on 11.09.2015.
 */
public class BDMG04 extends RSLineDevice {
    public static final String MODBUS_ADDRESS="MODBUS_ADDRESS";
    public static final String MODBUS_READ_FUNCTION="MODBUS_READ_FUNCTION";
    public static final String MODBUS_WRITE_FUNCTION="MODBUS_WRITE_FUNCTION";
    public static final String MODBUS_SIZE="MODBUS_SIZE";
    public static final short PROTOCOL_VERSON_124 = 1;
    public static final short PROTOCOL_VERSON_125 = 2;
    protected final SoftShortProperty bdmg04Protocol;
    protected final GroupProperty deviceInfo;


    protected final SoftByteProperty deviceType=new SoftByteProperty("bdmg04.deviceType", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
    protected final SoftByteProperty systemBoardType=new SoftByteProperty("bdmg04.systemBoardType", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
    protected final SoftByteProperty firmwarePO1=new SoftByteProperty("bdmg04.firmwarePO1", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
    protected final SoftByteProperty firmwarePO2=new SoftByteProperty("bdmg04.firmwarePO2", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
    protected final SoftByteProperty firmwarePO3=new SoftByteProperty("bdmg04.firmwarePO3", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
    protected final SoftByteProperty chanelCount=new SoftByteProperty("bdmg04.chanelCount", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);

    protected final SoftFloatProperty chanel1_sensetive=new SoftFloatProperty("bdmg04.chanel1_sensetive", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftFloatProperty chanel1_dedtime=new SoftFloatProperty("bdmg04.chanel1_dedtime", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftFloatProperty chanel2_sensetive=new SoftFloatProperty("bdmg04.chanel2_sensetive", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftFloatProperty chanel2_dedtime=new SoftFloatProperty("bdmg04.chanel2_dedtime", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftFloatProperty hight_sun_zone=new SoftFloatProperty("bdmg04.hight_sun_zone", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftFloatProperty low_sun_zone=new SoftFloatProperty("bdmg04.low_sun_zone", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftFloatProperty hight_warning=new SoftFloatProperty("bdmg04.hight_warning", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftFloatProperty hight_alarm=new SoftFloatProperty("bdmg04.hight_alarm", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftLongProperty chanel1_avarage_count=new SoftLongProperty("bdmg04.chanel1_avarage_count",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftLongProperty chanel2_avarage_count=new SoftLongProperty("bdmg04.chanel2_avarage_count",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftLongProperty chanel1_downmode_count=new SoftLongProperty("bdmg04.chanel1_downmode_count",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftLongProperty chanel2_downmode_count=new SoftLongProperty("bdmg04.chanel2_downmode_count",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftLongProperty chanel1_minspeed_count=new SoftLongProperty("bdmg04.chanel1_minspeed_count",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftLongProperty chanel2_minspeed_count=new SoftLongProperty("bdmg04.chanel2_minspeed_count",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftShortProperty alarm_mode=new SoftShortProperty("bdmg04.alarm_mode",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftShortProperty descrite_time=new SoftShortProperty("bdmg04.descrite_time",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftLongProperty chanel1_test_addon=new SoftLongProperty("bdmg04.chanel1_test_addon",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftLongProperty chanel2_test_addon=new SoftLongProperty("bdmg04.chanel2_test_addon",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftShortProperty filtation=new SoftShortProperty("bdmg04.filtation",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftShortProperty hight_time=new SoftShortProperty("bdmg04.hight_time",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftShortProperty comparator_chanel1=new SoftShortProperty("bdmg04.comparator_chanel1",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftShortProperty comparator_chanel2=new SoftShortProperty("bdmg04.comparator_chanel2",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftLongProperty chanel1_error_time=new SoftLongProperty("bdmg04.chanel1_error_time",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftLongProperty chanel2_error_time=new SoftLongProperty("bdmg04.chanel2_error_time",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftShortProperty hight_sigm_chanel1=new SoftShortProperty("bdmg04.hight_sigm_chanel1",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftShortProperty low_sigm_chanel1=new SoftShortProperty("bdmg04.low_sigm",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftFloatProperty koif_chanel1=new SoftFloatProperty("bdmg04.koif_chanel1", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftFloatProperty koif_chanel2=new SoftFloatProperty("bdmg04.koif_chanel2", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftFloatProperty fon_chanel1=new SoftFloatProperty("bdmg04.fon_chanel1", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftFloatProperty fon_chanel2=new SoftFloatProperty("bdmg04.fon_chanel2", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftShortProperty hight_sigm_chanel2=new SoftShortProperty("bdmg04.hight_sigm_chanel2",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftShortProperty low_sigm_chanel2=new SoftShortProperty("bdmg04.low_sigm_chanel2",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftShortProperty min_time_chanel1=new SoftShortProperty("bdmg04.min_time_chanel1",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftShortProperty min_time_chanel2=new SoftShortProperty("bdmg04.min_time_chanel2",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);



    public BDMG04(long address, String codename, String uuid) {
        super(address, codename, uuid);
        bdmg04Protocol = formShortProperty(-30L, "bdmg04.PROTOCOL", PROTOCOL_VERSON_124);
        bdmg04Protocol.setAdditionalInfo(NO_RANDOM, true);


        deviceInfo=new GroupProperty("bdmg04.deviceInfo", null, deviceType, systemBoardType, firmwarePO1, firmwarePO2, firmwarePO3, chanelCount);
        deviceInfo.setAdditionalInfo(MODBUS_ADDRESS,0xA000).setAdditionalInfo(MODBUS_READ_FUNCTION,0x3).setAdditionalInfo(MODBUS_SIZE, 3);




        addPropertys(deviceType);
    }



    @Override
    protected boolean processRecivedMessage(byte[] recivedMessage, byte[] sendMessage, AbstractProperty property, final Event sourceEvent) {
        short protocol = bdmg04Protocol.getValue();
        long deviceAddress = super.deviceAddress.getValue();
        if (protocol == PROTOCOL_VERSON_124) {
            try {
                return ModBusProtocol.processResponse(recivedMessage, (ValueProperty) property, (int) deviceAddress, sourceEvent);
            } catch (IllegalArgumentException e) {
                fireEvent(EventType.ERROR, e.getLocalizedMessage());
                return false;
            }
        } else if (protocol == PROTOCOL_VERSON_125) {
            try {
                return ModBusProtocol.processResponse(recivedMessage, (ValueProperty) property, (int) deviceAddress, sourceEvent);
            } catch (IllegalArgumentException e) {
                fireEvent(EventType.ERROR, e.getLocalizedMessage());
                return false;
            }
        }
        return false;
    }

    @Override
    protected RequestInformation formReadRequest(AbstractProperty property) {
        short protocol = bdmg04Protocol.getValue();
        long deviceAddress = super.deviceAddress.getValue();
        if (protocol == PROTOCOL_VERSON_124) {
            if (property instanceof ValueProperty) {
                try {
                    byte[] request = ModBusProtocol.formReadRequestModBus((ValueProperty) property, (int) deviceAddress);
                    return new RequestInformation(request, 9, false);
                } catch (Exception e) {
                    fireEvent(EventType.ERROR, e.getLocalizedMessage());
                    return null;
                }
            }
            return null;
        }
        if (protocol == PROTOCOL_VERSON_125) {
            if (property instanceof ValueProperty) {
                try {
                    byte[] request = ModBusProtocol.formReadRequestModBus((ValueProperty) property, (int) deviceAddress);
                    return new RequestInformation(request, 9, false);
                } catch (Exception e) {
                    fireEvent(EventType.ERROR, e.getLocalizedMessage());
                    return null;
                }
            }
            return null;
        }
        return null;
    }

    @Override
    protected RequestInformation formWriteRequest(AbstractProperty property) {
        short protocol = bdmg04Protocol.getValue();
        long deviceAddress = super.deviceAddress.getValue();
        if (protocol == PROTOCOL_VERSON_124) {
            if (property instanceof ValueProperty) {
                try {
                    byte[] request = ua.pp.fairwind.communications.devices.hardwaredevices.akon.ModBusProtocol.formWriteRequestModBus((ValueProperty) property, (int) deviceAddress);
                    return new RequestInformation(request, 9, false);
                } catch (IllegalArgumentException e) {
                    fireEvent(EventType.ERROR, e.getLocalizedMessage());
                    return null;
                }
            }
            return null;
        }
        if (protocol == PROTOCOL_VERSON_124) {
            if (property instanceof ValueProperty) {
                try {
                    byte[] request = ua.pp.fairwind.communications.devices.hardwaredevices.akon.ModBusProtocol.formWriteRequestModBus((ValueProperty) property, (int) deviceAddress);
                    return new RequestInformation(request, 9, false);
                } catch (Exception e) {
                    fireEvent(EventType.ERROR, e.getLocalizedMessage());
                    return null;
                }
            }
            return null;
        }
        return null;
    }



    @Override
    public String getDeviceType() {
        return "AKON BASE DEVICE";
    }

    public SoftShortProperty getBdmg04Protocol() {
        return bdmg04Protocol;
    }

}

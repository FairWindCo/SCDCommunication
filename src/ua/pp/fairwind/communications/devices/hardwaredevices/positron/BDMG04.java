package ua.pp.fairwind.communications.devices.hardwaredevices.positron;

import jssc.SerialPort;
import ua.pp.fairwind.communications.devices.RequestInformation;
import ua.pp.fairwind.communications.devices.abstracts.RSLineDevice;
import ua.pp.fairwind.communications.lines.lineparams.CommunicationLineParameters;
import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.messagesystems.event.EventType;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.groups.GroupProperty;
import ua.pp.fairwind.communications.propertyes.software.*;
import ua.pp.fairwind.communications.propertyes.software.bittedproperty.SoftBitToIntegerProperty;
import ua.pp.fairwind.communications.utils.ModBusProtocol;

import java.io.*;

/**
 * Created by Сергей on 11.09.2015.
 */
public class BDMG04 extends RSLineDevice {
    public static final String MODBUS_ADDRESS="MODBUS_ADDRESS";
    public static final String MODBUS_READ_FUNCTION="MODBUS_READ_FUNCTION";
    public static final String MODBUS_WRITE_FUNCTION="MODBUS_WRITE_FUNCTION";
    public static final String MODBUS_BYTE_SIZE="MODBUS_BYTE_SIZE";
    public static final String MODBUS_BYTE_SIZE_V125="MODBUS_BYTE_SIZE_V125";
    public static final short PROTOCOL_VERSON_124 = 1;
    public static final short PROTOCOL_VERSON_125 = 2;
    protected final SoftShortProperty bdmg04Protocol;
    protected final GroupProperty deviceInfo;


    protected final SoftByteProperty deviceType=new SoftByteProperty("bdmg04.deviceType", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(MODBUS_BYTE_SIZE,1);
    protected final SoftByteProperty systemBoardType=new SoftByteProperty("bdmg04.systemBoardType", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(MODBUS_BYTE_SIZE,1);
    protected final SoftByteProperty firmwarePO1=new SoftByteProperty("bdmg04.firmwarePO1", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(MODBUS_BYTE_SIZE,1);
    protected final SoftByteProperty firmwarePO2=new SoftByteProperty("bdmg04.firmwarePO2", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(MODBUS_BYTE_SIZE,1);
    protected final SoftByteProperty firmwarePO3=new SoftByteProperty("bdmg04.firmwarePO3", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(MODBUS_BYTE_SIZE,1);
    protected final SoftByteProperty chanelCount=new SoftByteProperty("bdmg04.chanelCount", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(MODBUS_BYTE_SIZE,1);

    protected final SoftFloatProperty chanel1_sensetive=new SoftFloatProperty("bdmg04.chanel1_sensetive", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty chanel1_dedtime=new SoftFloatProperty("bdmg04.chanel1_dedtime", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty chanel2_sensetive=new SoftFloatProperty("bdmg04.chanel2_sensetive", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty chanel2_dedtime=new SoftFloatProperty("bdmg04.chanel2_dedtime", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty hight_sun_zone=new SoftFloatProperty("bdmg04.hight_sun_zone", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty low_sun_zone=new SoftFloatProperty("bdmg04.low_sun_zone", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty hight_warning=new SoftFloatProperty("bdmg04.hight_warning", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty hight_alarm=new SoftFloatProperty("bdmg04.hight_alarm", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftLongProperty chanel1_avarage_count=new SoftLongProperty("bdmg04.chanel1_avarage_count",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,2);
    protected final SoftLongProperty chanel2_avarage_count=new SoftLongProperty("bdmg04.chanel2_avarage_count",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,2);
    protected final SoftLongProperty chanel1_downmode_count=new SoftLongProperty("bdmg04.chanel1_downmode_count",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,2);
    protected final SoftLongProperty chanel2_downmode_count=new SoftLongProperty("bdmg04.chanel2_downmode_count",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,2);
    protected final SoftLongProperty chanel1_minspeed_count=new SoftLongProperty("bdmg04.chanel1_minspeed_count",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,2);
    protected final SoftLongProperty chanel2_minspeed_count=new SoftLongProperty("bdmg04.chanel2_minspeed_count",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,2);
    protected final SoftShortProperty alarm_mode=new SoftShortProperty("bdmg04.alarm_mode",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,1);
    protected final SoftShortProperty descrite_time=new SoftShortProperty("bdmg04.descrite_time",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,1);
    protected final SoftLongProperty chanel1_test_addon=new SoftLongProperty("bdmg04.chanel1_test_addon",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,2);
    protected final SoftLongProperty chanel2_test_addon=new SoftLongProperty("bdmg04.chanel2_test_addon",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,2);
    protected final SoftShortProperty filtation=new SoftShortProperty("bdmg04.filtation",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,1);
    protected final SoftShortProperty hight_time=new SoftShortProperty("bdmg04.hight_time",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,1);
    protected final SoftShortProperty comparator_chanel1=new SoftShortProperty("bdmg04.comparator_chanel1",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,1);
    protected final SoftShortProperty comparator_chanel2=new SoftShortProperty("bdmg04.comparator_chanel2",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,1);
    protected final SoftLongProperty chanel1_error_time=new SoftLongProperty("bdmg04.chanel1_error_time",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,2);
    protected final SoftLongProperty chanel2_error_time=new SoftLongProperty("bdmg04.chanel2_error_time",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,2);
    protected final SoftShortProperty hight_sigm_chanel1=new SoftShortProperty("bdmg04.hight_sigm_chanel1",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,1);
    protected final SoftShortProperty low_sigm_chanel1=new SoftShortProperty("bdmg04.low_sigm",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,1);
    protected final SoftFloatProperty koif_chanel1=new SoftFloatProperty("bdmg04.koif_chanel1", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty koif_chanel2=new SoftFloatProperty("bdmg04.koif_chanel2", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty fon_chanel1=new SoftFloatProperty("bdmg04.fon_chanel1", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty fon_chanel2=new SoftFloatProperty("bdmg04.fon_chanel2", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftShortProperty hight_sigm_chanel2=new SoftShortProperty("bdmg04.hight_sigm_chanel2",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,1);
    protected final SoftShortProperty low_sigm_chanel2=new SoftShortProperty("bdmg04.low_sigm_chanel2",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,1);
    protected final SoftShortProperty min_time_chanel1=new SoftShortProperty("bdmg04.min_time_chanel1",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,1);
    protected final SoftShortProperty min_time_chanel2=new SoftShortProperty("bdmg04.min_time_chanel2",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,1);
    protected final GroupProperty configuration;


    protected final SoftByteProperty boot_param_deviceType=new SoftByteProperty("bdmg04.deviceType", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,1);
    protected final SoftByteProperty boot_param_systemBoardType=new SoftByteProperty("bdmg04.systemBoardType", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,1);
    protected final SoftShortProperty boot_param_mode=new SoftShortProperty("bdmg04.mode", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,1);
    protected final SoftShortProperty boot_param_networkaddress=new SoftShortProperty("bdmg04.networkaddress", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,1);
    protected final SoftFloatProperty boot_processor_voltage=new SoftFloatProperty("bdmg04.boot_processor_voltage", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty boot_devision_chanel1=new SoftFloatProperty("bdmg04.devision_chanel1", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty boot_devision_chanel2=new SoftFloatProperty("bdmg04.devision_chanel2", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty boot_multage_chanel1=new SoftFloatProperty("bdmg04.multage_chanel1", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty boot_multage_chanel2=new SoftFloatProperty("bdmg04.multage_chanel2", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty boot_move_chanel1=new SoftFloatProperty("bdmg04.move_chanel1", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty boot_move_chanel2=new SoftFloatProperty("bdmg04.move_chanel2", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty boot_resistor=new SoftFloatProperty("bdmg04.resistor", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty boot_out_curent_hight=new SoftFloatProperty("bdmg04.out_curent_hight", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty boot_out_curent_low=new SoftFloatProperty("bdmg04.out_curent_low", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty boot_curent_detector_fail=new SoftFloatProperty("bdmg04.curent_detector_fail", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty boot_curent_iverflow=new SoftFloatProperty("bdmg04.curent_iverflow", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty boot_curent_correction=new SoftFloatProperty("bdmg04.curent_correction", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty boot_hi_volatege_max_chanel1=new SoftFloatProperty("bdmg04.hi_volatege_max_chanel1", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty boot_hi_volatege_min_chanel1=new SoftFloatProperty("bdmg04.hi_volatege_min_chanel1", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty boot_hi_volatege_max_chanel2=new SoftFloatProperty("bdmg04.hi_volatege_max_chanel2", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty boot_hi_volatege_min_chanel2=new SoftFloatProperty("bdmg04.hi_volatege_min_chanel2", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final GroupProperty bootparam;

    protected final SoftShortProperty command_mode=new SoftShortProperty("bdmg04.command_mode", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,1);
    protected final SoftShortProperty command_execute=new SoftShortProperty("bdmg04.command_execute", ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE).setAdditionalInfo(MODBUS_BYTE_SIZE,1);
    protected final GroupProperty command;

    protected final SoftFloatProperty state_med=new SoftFloatProperty("bdmg04.state_med", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftBitToIntegerProperty state_status=new SoftBitToIntegerProperty("bdmg04.state_status",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY,(byte)16,
                new SoftBoolProperty("bdmg04.state_status.ok",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(SoftBoolProperty.BIT_NUMBER,0),
                new SoftBoolProperty("bdmg04.state_status.vrg",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(SoftBoolProperty.BIT_NUMBER,1),
                new SoftBoolProperty("bdmg04.state_status.vag",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(SoftBoolProperty.BIT_NUMBER,2),
                new SoftBoolProperty("bdmg04.state_status.chanel_hi",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(SoftBoolProperty.BIT_NUMBER,3),
                new SoftBoolProperty("bdmg04.state_status.hi_chanel1",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(SoftBoolProperty.BIT_NUMBER,4),
                new SoftBoolProperty("bdmg04.state_status.hi_chanel2",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(SoftBoolProperty.BIT_NUMBER,5),
                new SoftBoolProperty("bdmg04.state_status.error_chanel1",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(SoftBoolProperty.BIT_NUMBER,6),
                new SoftBoolProperty("bdmg04.state_status.error_chanel2",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(SoftBoolProperty.BIT_NUMBER,7),
                new SoftBoolProperty("bdmg04.state_status.test",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(SoftBoolProperty.BIT_NUMBER,8),
                new SoftBoolProperty("bdmg04.state_status.alarm",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(SoftBoolProperty.BIT_NUMBER,9),
                new SoftBoolProperty("bdmg04.state_status.paramsok",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(SoftBoolProperty.BIT_NUMBER,10),
                new SoftBoolProperty("bdmg04.state_status.hi_chanel1_error",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(SoftBoolProperty.BIT_NUMBER,11),
                new SoftBoolProperty("bdmg04.state_status.filtration",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(SoftBoolProperty.BIT_NUMBER,12),
                new SoftBoolProperty("bdmg04.state_status.filtration_error",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(SoftBoolProperty.BIT_NUMBER,13),
                new SoftBoolProperty("bdmg04.state_status.table_chanel1",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(SoftBoolProperty.BIT_NUMBER,14),
                new SoftBoolProperty("bdmg04.state_status.table_chanel2",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(SoftBoolProperty.BIT_NUMBER,15)
            ).setAdditionalInfo(MODBUS_BYTE_SIZE,2);
    protected final SoftFloatProperty state_voltage_chanel1=new SoftFloatProperty("bdmg04.state_voltage_chanel1", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty state_voltage_chanel2=new SoftFloatProperty("bdmg04.state_voltage_chanel2", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftFloatProperty state_speed_count_chanel1=new SoftFloatProperty("bdmg04.state_speed_count_chanel1", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftLongProperty state_impouls_count_chanel1=new SoftLongProperty("bdmg04.state_impouls_count_chanel1",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(MODBUS_BYTE_SIZE,2);
    protected final SoftLongProperty state_time_chanel1=new SoftLongProperty("bdmg04.state_time_chanel1",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(MODBUS_BYTE_SIZE,2);
    protected final SoftFloatProperty state_speed_count_chanel2=new SoftFloatProperty("bdmg04.state_speed_count_chanel2", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(MODBUS_BYTE_SIZE,4);
    protected final SoftLongProperty state_impouls_count_chanel2=new SoftLongProperty("bdmg04.state_impouls_count_chanel2",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(MODBUS_BYTE_SIZE,2);
    protected final SoftLongProperty state_time_chanel2=new SoftLongProperty("bdmg04.state_time_chanel2",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(MODBUS_BYTE_SIZE,2);
    protected final SoftLongProperty state_last_sec_chanel1=new SoftLongProperty("bdmg04.state_last_sec_chanel1",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(MODBUS_BYTE_SIZE,2);
    protected final SoftLongProperty state_last_sec_chanel2=new SoftLongProperty("bdmg04.state_last_sec_chanel2",ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(MODBUS_BYTE_SIZE,2);
    protected final SoftShortProperty state_sheem_chanel1=new SoftShortProperty("bdmg04.state_sheem_chanel1", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(MODBUS_BYTE_SIZE,1);
    protected final SoftShortProperty state_sheem_chanel2=new SoftShortProperty("bdmg04.state_sheem_chanel2", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY).setAdditionalInfo(MODBUS_BYTE_SIZE,1);
    protected final GroupProperty state;



    public BDMG04(long address, String codename, String uuid) {
        super(address, codename, uuid);
        bdmg04Protocol = formShortProperty(-30L, "bdmg04.PROTOCOL", PROTOCOL_VERSON_124);
        bdmg04Protocol.setAdditionalInfo(NO_RANDOM, true);


        deviceInfo=new GroupProperty("bdmg04.deviceInfo", null, deviceType, systemBoardType, firmwarePO1, firmwarePO2, firmwarePO3, chanelCount);
        //deviceInfo=new GroupProperty("bdmg04.deviceInfo", null, systemBoardType,deviceType, firmwarePO2, firmwarePO1,chanelCount, firmwarePO3);
        deviceInfo.setAdditionalInfo(MODBUS_ADDRESS,0xA000).setAdditionalInfo(MODBUS_READ_FUNCTION,0x3).setAdditionalInfo(MODBUS_BYTE_SIZE, 6);
        deviceInfo.setChildAdditional(ValueProperty.NO_CONTROL_PROPERTY, true);
        deviceInfo.setReadAccepted(true);

        configuration=new GroupProperty("bdmg04.config", null, chanel1_sensetive,chanel1_dedtime,chanel2_sensetive,chanel2_dedtime,hight_sun_zone,low_sun_zone,
                hight_warning,hight_alarm,chanel1_avarage_count,chanel2_avarage_count,chanel1_downmode_count,chanel2_downmode_count,chanel1_minspeed_count,
                chanel2_minspeed_count,alarm_mode,descrite_time,chanel1_test_addon,chanel2_test_addon,filtation,hight_time,comparator_chanel1,comparator_chanel2,
                chanel1_error_time,chanel2_error_time,hight_sigm_chanel1,low_sigm_chanel1,koif_chanel1,koif_chanel2,fon_chanel1,fon_chanel2,hight_sigm_chanel2,
                low_sigm_chanel2,min_time_chanel1,min_time_chanel2);
        configuration.setAdditionalInfo(MODBUS_ADDRESS, 0x0000).setAdditionalInfo(MODBUS_READ_FUNCTION,0x3)
                .setAdditionalInfo(MODBUS_WRITE_FUNCTION, 0x10)
                .setAdditionalInfo(MODBUS_BYTE_SIZE, (8 * 4 + 6 * 2 + 2 + 2 * 2 + 4 + 2 * 2 + 2 + 4 * 4 + 4));
        configuration.setChildAdditional(ValueProperty.NO_CONTROL_PROPERTY, true);
        configuration.setReadAccepted(true);
        configuration.setWriteAccepted(true);
        //configuration.setPropertyTimeOutReadAddon(4000L);

        bootparam=new GroupProperty("bdmg04.bootparam",null,boot_param_deviceType,boot_param_systemBoardType,boot_param_mode,boot_param_networkaddress,boot_processor_voltage,
                boot_devision_chanel1,boot_devision_chanel2,boot_multage_chanel1,boot_multage_chanel2,boot_move_chanel1,boot_move_chanel2,boot_resistor,
                boot_out_curent_hight,boot_out_curent_low,boot_curent_detector_fail,boot_curent_iverflow,boot_curent_correction,
                boot_hi_volatege_max_chanel1,boot_hi_volatege_min_chanel1,boot_hi_volatege_max_chanel2,boot_hi_volatege_min_chanel2);
        bootparam.setAdditionalInfo(MODBUS_ADDRESS, 0x100).setAdditionalInfo(MODBUS_READ_FUNCTION, 0x3).setAdditionalInfo(MODBUS_WRITE_FUNCTION,0x10)
                .setAdditionalInfo(MODBUS_BYTE_SIZE, 4)
                .setAdditionalInfo(MODBUS_BYTE_SIZE_V125, 4 + (17 * 4));
        bootparam.setChildAdditional(ValueProperty.NO_CONTROL_PROPERTY, true);
        bootparam.setReadAccepted(true);
        bootparam.setWriteAccepted(true);
        //bootparam.setPropertyTimeOutReadAddon(4000L);


        command=new GroupProperty("bdmg04.command",null,command_mode,command_execute);
        command.setAdditionalInfo(MODBUS_ADDRESS, 0x200).setAdditionalInfo(MODBUS_READ_FUNCTION,0x4).setAdditionalInfo(MODBUS_WRITE_FUNCTION,0x10)
                .setAdditionalInfo(MODBUS_BYTE_SIZE, 1)
                .setAdditionalInfo(MODBUS_BYTE_SIZE_V125, 2);
        command.setChildAdditional(ValueProperty.NO_CONTROL_PROPERTY, true);
        command.setReadAccepted(true);
        command.setWriteAccepted(true);
        //command.setPropertyTimeOutReadAddon(4000L);


        state=new GroupProperty("bdmg04.state",null,state_med,state_status,state_voltage_chanel1,state_voltage_chanel2,
                state_speed_count_chanel1,state_impouls_count_chanel1,state_time_chanel1,
                state_speed_count_chanel2,state_impouls_count_chanel2,state_time_chanel2,
                state_last_sec_chanel1,state_last_sec_chanel2,state_sheem_chanel1,state_sheem_chanel2);
        state.setAdditionalInfo(MODBUS_ADDRESS, 0x000).setAdditionalInfo(MODBUS_READ_FUNCTION,0x4).setAdditionalInfo(MODBUS_WRITE_FUNCTION,0x10)
                .setAdditionalInfo(MODBUS_BYTE_SIZE, 4+2+2*4+4+2+2+4+2+2)
                .setAdditionalInfo(MODBUS_BYTE_SIZE_V125, 4+2+2*4+4+2+2+4+2+2+2*2+2);
        state.setChildAdditional(ValueProperty.NO_CONTROL_PROPERTY, true);
        state.setReadAccepted(true);
        state.setWriteAccepted(false);

        addPropertys(deviceInfo);
        addPropertys(configuration);
        addPropertys(bootparam);
        addPropertys(command);
        addPropertys(state);
        setLineParameters(new CommunicationLineParameters(SerialPort.BAUDRATE_38400, SerialPort.DATABITS_8, SerialPort.PARITY_NONE, SerialPort.STOPBITS_2));
        deviceTimeOut.setValue(2000L);
    }



    @Override
    protected boolean processRecivedMessage(byte[] recivedMessage, byte[] sendMessage, AbstractProperty property, final Event sourceEvent) {
        short protocol = bdmg04Protocol.getValue();
        long deviceAddress = super.deviceAddress.getValue();
        if (protocol == PROTOCOL_VERSON_124) {
            try {
                return ModBusProtocol.processResponse(recivedMessage, property, (int) deviceAddress, sourceEvent);
            } catch (IllegalArgumentException e) {
                fireEvent(EventType.ERROR, e.getLocalizedMessage());
                return false;
            }
        } else if (protocol == PROTOCOL_VERSON_125) {
            try {
                return ModBusProtocol.processResponse(recivedMessage, property, (int) deviceAddress, sourceEvent);
            } catch (IllegalArgumentException e) {
                fireEvent(EventType.ERROR, e.getLocalizedMessage());
                return false;
            }
        }
        return false;
    }

    @Override
    protected RequestInformation formReadRequest(AbstractProperty property) {
        if(property==null)return null;
        short protocol = bdmg04Protocol.getValue();
        long deviceAddress = super.deviceAddress.getValue();
        if (protocol == PROTOCOL_VERSON_124) {
            Object size = property.getAdditionalInfo(MODBUS_BYTE_SIZE);
            if (size == null && !(size instanceof Number)) {
                throw new IllegalArgumentException("NO PROPERTY_NUM IN PROPERTY");
            }
            int mobus_byte_size = ((Number) size).intValue();
            ModBusProtocol.ModBusProtocolRequestInformation request=ModBusProtocol.formReadRequestModBus(property,mobus_byte_size, (int) deviceAddress);
            return request==null?null:request.getRequest(false);
        }
        if (protocol == PROTOCOL_VERSON_125) {
            Object size = property.getAdditionalInfo(MODBUS_BYTE_SIZE_V125);
            if (size == null && !(size instanceof Number)) {
                size = property.getAdditionalInfo(MODBUS_BYTE_SIZE);
            }
            if (size == null && !(size instanceof Number)) {
                throw new IllegalArgumentException("NO PROPERTY_NUM IN PROPERTY");
            }
            int mobus_byte_size = ((Number) size).intValue();
            ModBusProtocol.ModBusProtocolRequestInformation request=ModBusProtocol.formReadRequestModBus(property,mobus_byte_size, (int) deviceAddress);
            return request==null?null:request.getRequest(false);
        }
        return null;
    }

    @Override
    protected RequestInformation formWriteRequest(AbstractProperty property) {
        short protocol = bdmg04Protocol.getValue();
        long deviceAddress = super.deviceAddress.getValue();
        if (protocol == PROTOCOL_VERSON_124) {
            Object size = property.getAdditionalInfo(MODBUS_BYTE_SIZE);
            if (size == null && !(size instanceof Number)) {
                throw new IllegalArgumentException("NO PROPERTY_NUM IN PROPERTY");
            }
            int mobus_byte_size = ((Number) size).intValue();
            ModBusProtocol.ModBusProtocolRequestInformation request=ModBusProtocol.formWriteRequestModBus(property, mobus_byte_size, (int) deviceAddress);
            return request==null?null:request.getRequest(false);
        }
        if (protocol == PROTOCOL_VERSON_125) {
            Object size = property.getAdditionalInfo(MODBUS_BYTE_SIZE_V125);
            if (size == null && !(size instanceof Number)) {
                size = property.getAdditionalInfo(MODBUS_BYTE_SIZE);
            }
            if (size == null && !(size instanceof Number)) {
                throw new IllegalArgumentException("NO PROPERTY_NUM IN PROPERTY");
            }
            int mobus_byte_size = ((Number) size).intValue();
            ModBusProtocol.ModBusProtocolRequestInformation request=ModBusProtocol.formWriteRequestModBus(property, mobus_byte_size, (int) deviceAddress);
            return request==null?null:request.getRequest(false);
        }
        return null;
    }



    @Override
    public String getDeviceType() {
        return "POSITRON BDMG04 DEVICE";
    }

    public SoftShortProperty getBdmg04Protocol() {
        return bdmg04Protocol;
    }

    public GroupProperty getDeviceInfo() {
        return deviceInfo;
    }

    public GroupProperty getConfiguration() {
        return configuration;
    }

    public GroupProperty getBootparam() {
        return bootparam;
    }

    public GroupProperty getCommand() {
        return command;
    }

    public GroupProperty getState() {
        return state;
    }


    public void saveBinaryParameters(File file){
        if(file!=null) {
            if(!file.exists()){
                try {
                    if (!file.createNewFile()) {
                        fireEvent(EventType.ERROR, "FILE CREATION ERROR"+file.getAbsolutePath());
                    }
                }catch (IOException e){
                    fireEvent(EventType.ERROR, e.getLocalizedMessage());
                }
            }

            if (file.canWrite()) {
                byte[] conf = ModBusProtocol.getValueForTransfer(configuration);
                byte[] boot = ModBusProtocol.getValueForTransfer(bootparam,4 + (17 * 4));
                try (OutputStream out = new FileOutputStream(file)) {
                    out.write(conf);
                    out.write(boot);
                } catch (IOException e) {
                    fireEvent(EventType.ERROR, e.getLocalizedMessage());
                }
            }
        }
    }

    public void loadBinaryParameters(File file){
        if(file!=null && file.canRead()){
            byte[] conf=new byte[ModBusProtocol.calcultaeBufferSize(configuration)];
            byte[] boot=new byte[4 + (17 * 4)];
            if(conf!=null && boot!=null && conf.length>0 && boot.length>0) {
                try (InputStream out = new FileInputStream(file)) {
                    out.read(conf);
                    out.read(boot);
                } catch (IOException e) {
                    fireEvent(EventType.ERROR, e.getLocalizedMessage());
                }
                ModBusProtocol.setValueFromTransfer(configuration, conf, 0, conf.length, null);
                ModBusProtocol.setValueFromTransfer(bootparam, boot, 0, boot.length, null);
            }
        }
    }
}

package ua.pp.fairwind.communications.devices.hardwaredevices.akon;

import ua.pp.fairwind.communications.devices.RequestInformation;
import ua.pp.fairwind.communications.devices.abstracts.RSLineDevice;
import ua.pp.fairwind.communications.internatianalisation.I18N;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.DeviceNamedCommandProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.event.EventType;
import ua.pp.fairwind.communications.propertyes.groups.GroupProperty;
import ua.pp.fairwind.communications.propertyes.software.*;
import ua.pp.fairwind.communications.propertyes.software.bytedpropertyes.SoftIntegerToByteProperty;

import java.util.HashMap;

/**
 * Created by Сергей on 11.09.2015.
 */
public class AkonBase extends RSLineDevice {
    public static String OBJECT_NUM="OBJECT_NUM";
    public static String PROPERTY_NUM="PROPERTY_NUM";
    public static String PROPERTY_MODBUS="PROPERTY_MODBUS";
    public static short OBJECTNET_PROTOCOL=0;
    public static short MODBUS_PROTOCOL=1;
    public static short TCPMODBUS_PROTOCOL=2;

    protected final SoftByteProperty akonAddress;
    protected final SoftByteProperty akonBaudRate;
    protected final SoftByteProperty akonSetupedProtocol;
    protected final SoftByteProperty akonParity;

    protected final SoftShortProperty akonProtocol;
    protected final SoftLongProperty deviceType;
    protected final SoftLongProperty serialNumber;
    protected final SoftLongProperty chanelMask;
    protected final SoftIntegerToByteProperty deviceconfig;
    protected final SoftLongProperty po;
    protected final SoftLongProperty additionalInfo;
    protected final SoftLongProperty reserv;
    protected final SoftLongProperty systemTime;

    private final DeviceNamedCommandProperty saveToFlash;
    private final DeviceNamedCommandProperty readFromFlash;

    protected final GroupProperty systemObject;

    public AkonBase(long address, String codename, String uuid) {
        super(address, codename, uuid);
        akonProtocol=formShortProperty(-30L,"baseakon.ENABLE_AKON_PROTOCOL", OBJECTNET_PROTOCOL);
        serialNumber=formLongConfigProperty(1L, "baseakon.SERIAL_NUMBER");
        serialNumber.setAdditionalInfo(OBJECT_NUM,0);
        serialNumber.setAdditionalInfo(PROPERTY_NUM, 1);
        serialNumber.setAdditionalInfo(PROPERTY_MODBUS, 0x2);
        deviceType=formLongConfigProperty(0L, "baseakon.TYPE");
        deviceType.setAdditionalInfo(OBJECT_NUM,0);
        deviceType.setAdditionalInfo(PROPERTY_NUM, 0);
        deviceType.setAdditionalInfo(PROPERTY_MODBUS, 0x0);
        chanelMask=formLongConfigProperty(2L, "baseakon.chanelMask");
        chanelMask.setAdditionalInfo(OBJECT_NUM,0);
        chanelMask.setAdditionalInfo(PROPERTY_NUM,2);
        chanelMask.setAdditionalInfo(PROPERTY_MODBUS, 0x4);

        po=formAkonLongR(0, 0x64, 0x10, "baseakon.po");
        additionalInfo=formAkonLongR(0, 0x2, 0x4, "baseakon.additional");
        reserv=formAkonLongR(0, 0x65, 0x12, "baseakon.reserv");
        akonAddress=formAkonByteRW(0x6, "baseakon.akonAddress");
        akonSetupedProtocol=formAkonByteRW(0xE,"baseakon.akonSetupedProtocol");
        akonBaudRate=formAkonByteRW(0L,"baseakon.akonBaudRate");
        akonParity=formAkonByteRW(0L,"baseakon.akonParity");
        deviceconfig = formAkonIntRWConfig("baseakon.deviceconfig", akonAddress,akonSetupedProtocol,akonBaudRate, akonParity);
        systemTime = formAkontimeRW(0x24, 0x66, 0x14, "baseakon.systemTime");
        systemObject=new GroupProperty("baseakon.systemObject",null,deviceType,serialNumber,chanelMask,deviceconfig,po,reserv,systemTime,akonAddress,akonProtocol,additionalInfo);

        saveToFlash=formCommandNameProperty("baseakon.saveToFlash");
        readFromFlash=formCommandNameProperty("baseakon.readFromFlash");
        saveToFlash.setAdditionalInfo(OBJECT_NUM,0);
        saveToFlash.setAdditionalInfo(PROPERTY_NUM,6);
        saveToFlash.setAdditionalInfo(PROPERTY_MODBUS,0x8);
        readFromFlash.setAdditionalInfo(OBJECT_NUM,0);
        readFromFlash.setAdditionalInfo(PROPERTY_NUM,5);
        readFromFlash.setAdditionalInfo(PROPERTY_MODBUS,0xA);

        addPropertys(akonSetupedProtocol);
        addPropertys(akonAddress);
        addPropertys(additionalInfo);
        addPropertys(deviceType);
        addPropertys(serialNumber);
        addPropertys(chanelMask);
        addPropertys(po);
        addPropertys(reserv);
        addPropertys(systemTime);
        addPropertys(deviceconfig);

        addCommands(saveToFlash);
        addCommands(readFromFlash);
    }

    protected SoftLongProperty formAkonLongR(int group,int property,long address,String name){
        SoftLongProperty command=new SoftLongProperty(name,ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
        command.setAdditionalInfo(PROPERTY_ADDRESS, address);
        command.setAdditionalInfo(PROPERTY_MODBUS, address);
        command.setAdditionalInfo(PROPERTY_NUM, property);
        command.setAdditionalInfo(OBJECT_NUM, group);
        return command;
    }
    protected SoftLongProperty formAkontimeRW(int group,int property,long address,String name){
        SoftLongProperty command=new SoftLongProperty(name,ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
        command.setAdditionalInfo(PROPERTY_ADDRESS, address);
        command.setAdditionalInfo(PROPERTY_MODBUS, address);
        command.setAdditionalInfo(PROPERTY_NUM, property);
        command.setAdditionalInfo(OBJECT_NUM, group);
        return command;
    }

    protected SoftByteProperty formAkonByteRW(long address,String name){
        SoftByteProperty command=new SoftByteProperty(name,ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        command.setAdditionalInfo(PROPERTY_ADDRESS, address);
        command.setAdditionalInfo(PROPERTY_MODBUS, address);
        return command;
    }

    protected SoftIntegerToByteProperty formAkonIntRWConfig(String name,SoftByteProperty byteAddress,SoftByteProperty byteprotocol,SoftByteProperty byteBitrate,SoftByteProperty byteParity){
        SoftIntegerToByteProperty command=new SoftIntegerToByteProperty(name,null,ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE,byteAddress,byteBitrate,byteprotocol,byteParity);
        command.setAdditionalInfo(PROPERTY_NUM, 0x3);
        command.setAdditionalInfo(OBJECT_NUM, 0x0);
        command.setAdditionalInfo(PROPERTY_MODBUS, 0x6);
        return command;
    }


    @Override
    protected boolean processRecivedMessage(byte[] recivedMessage, byte[] sendMessage, AbstractProperty property) {
        short protocol=akonProtocol.getValue();
        long deviceAddress=super.deviceAddress.getValue();
        if(protocol==OBJECTNET_PROTOCOL){
            try {
                return AkonProtocol.processResponse(recivedMessage,(ValueProperty)property,(int)deviceAddress);
            } catch (IllegalArgumentException e){
                fireEvent(EventType.ERROR,e.getLocalizedMessage());
                return false;
            }
        } else if(protocol==MODBUS_PROTOCOL) {
            try {
                return ModBusProtocol.processResponse(recivedMessage, (ValueProperty) property, (int) deviceAddress);
            } catch (IllegalArgumentException e) {
                fireEvent(EventType.ERROR, e.getLocalizedMessage());
                return false;
            }
        }
        return false;
    }

    @Override
    protected RequestInformation formReadRequest(AbstractProperty property) {
        short protocol=akonProtocol.getValue();
        long deviceAddress=super.deviceAddress.getValue();
        if(protocol==OBJECTNET_PROTOCOL){
            if(property instanceof ValueProperty) {
                try {
                    byte[] request=AkonProtocol.formReadRequestObjectNet((ValueProperty)property,(int)deviceAddress);
                    return new RequestInformation(request,11,false);
                } catch (Exception e){
                    fireEvent(EventType.ERROR,e.getLocalizedMessage());
                    return null;
                }
            }
            return null;
        }
        if(protocol==MODBUS_PROTOCOL){
            if(property instanceof ValueProperty) {
                try {
                    byte[] request=ModBusProtocol.formReadRequestModBus((ValueProperty) property, (int) deviceAddress);
                    return new RequestInformation(request,9,false);
                } catch (Exception e){
                    fireEvent(EventType.ERROR,e.getLocalizedMessage());
                    return null;
                }
            }
            return null;
        }
        return null;
    }

    @Override
    protected RequestInformation formWriteRequest(AbstractProperty property) {
        short protocol=akonProtocol.getValue();
        long deviceAddress=super.deviceAddress.getValue();
        if(protocol==OBJECTNET_PROTOCOL){
            if(property instanceof ValueProperty) {
                try {
                    byte[] request = AkonProtocol.formWriteRequestObjectNet((ValueProperty) property, (int) deviceAddress);
                    return new RequestInformation(request,11,false);
                } catch (IllegalArgumentException e){
                    fireEvent(EventType.ERROR,e.getLocalizedMessage());
                    return null;
                }
            }
            return null;
        }
        if(protocol==MODBUS_PROTOCOL){
            if(property instanceof ValueProperty) {
                try {
                    byte[] request=ModBusProtocol.formWriteRequestModBus((ValueProperty) property, (int) deviceAddress);
                    return new RequestInformation(request,9,false);
                } catch (Exception e){
                    fireEvent(EventType.ERROR,e.getLocalizedMessage());
                    return null;
                }
            }
            return null;
        }
        return null;
    }

    @Override
    protected RequestInformation processCommandRequest(String commandName) {

        switch (commandName){
            case "SAVE":
                short protocol=akonProtocol.getValue();
                long deviceAddress=super.deviceAddress.getValue();
                if(protocol==OBJECTNET_PROTOCOL) {
                    try {
                        byte[] request = AkonProtocol.formWriteRequestObjectNet(saveToFlash, (int) deviceAddress);
                        return new RequestInformation(request, 11, false);
                    } catch (IllegalArgumentException e) {
                        fireEvent(EventType.ERROR, e.getLocalizedMessage());
                        return null;
                    }
                } else
                if(protocol==MODBUS_PROTOCOL){
                        try {
                            byte[] request=ModBusProtocol.formWriteRequestModBus(saveToFlash, (int) deviceAddress);
                            return new RequestInformation(request,8,false);
                        } catch (Exception e){
                            fireEvent(EventType.ERROR,e.getLocalizedMessage());
                            return null;
                        }
                }
            case "LOAD":
                protocol=akonProtocol.getValue();
                deviceAddress=super.deviceAddress.getValue();
                if(protocol==OBJECTNET_PROTOCOL) {
                    try {
                        byte[] request = AkonProtocol.formWriteRequestObjectNet(readFromFlash, (int) deviceAddress);
                        return new RequestInformation(request, 11, false);
                    } catch (IllegalArgumentException e) {
                        fireEvent(EventType.ERROR, e.getLocalizedMessage());
                        return null;
                    }
                } else
                if(protocol==MODBUS_PROTOCOL){
                       try {
                            byte[] request=ModBusProtocol.formWriteRequestModBus(readFromFlash, (int) deviceAddress);
                            return new RequestInformation(request,8,false);
                        } catch (Exception e){
                            fireEvent(EventType.ERROR,e.getLocalizedMessage());
                            return null;
                        }
                }
        }
        return super.processCommandRequest(commandName);
    }

    public SoftLongProperty getDeviceTypeProperty(){
        return deviceType;
    }

    @Override
    public String getDeviceType() {
        return "AKON BASE DEVICE";
    }

    public SoftShortProperty getAkonProtocol() {
        return akonProtocol;
    }

    public SoftLongProperty getSerialNumber() {
        return serialNumber;
    }

    public SoftLongProperty getChanelMask() {
        return chanelMask;
    }

    public SoftLongProperty getType() {
        return deviceType;
    }

    public GroupProperty getSystemObject() {
        return systemObject;
    }

    public DeviceNamedCommandProperty getSaveToFlash() {
        return saveToFlash;
    }

    public DeviceNamedCommandProperty getReadFromFlash() {
        return readFromFlash;
    }

    public SoftByteProperty getAkonAddress() {
        return akonAddress;
    }

    public SoftByteProperty getAkonSetupedProtocol() {
        return akonSetupedProtocol;
    }

    public SoftByteProperty getAkonBaudRate() {
        return akonBaudRate;
    }

    public SoftByteProperty getAkonParity() {
        return akonParity;
    }

    public SoftIntegerToByteProperty getDeviceconfig() {
        return deviceconfig;
    }

    public SoftLongProperty getPo() {
        return po;
    }

    public SoftLongProperty getReserv() {
        return reserv;
    }

    public SoftLongProperty getSystemTime() {
        return systemTime;
    }

    public SoftLongProperty getAdditionalInfo() {
        return additionalInfo;
    }
}

package ua.pp.fairwind.communications.devices.hardwaredevices.akon;

import ua.pp.fairwind.communications.devices.RSLineDevice;
import ua.pp.fairwind.communications.devices.RequestInformation;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.DeviceNamedCommandProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.event.EventType;
import ua.pp.fairwind.communications.propertyes.groups.GroupProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftDateTimeProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftIntegerProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;

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

    protected final SoftShortProperty akonProtocol;
    protected final SoftLongProperty deviceType;
    protected final SoftLongProperty serialNumber;
    protected final SoftLongProperty chanelMask;
    protected final SoftIntegerProperty deviceconfig;
    protected final SoftLongProperty po;
    protected final SoftLongProperty reserv;
    protected final SoftDateTimeProperty systemTime;

    private final DeviceNamedCommandProperty saveToFlash;
    private final DeviceNamedCommandProperty readFromFlash;

    protected final GroupProperty systemObject;

    public AkonBase(long address, String name, String uuid, String description, MessageSubSystem centralSystem, HashMap<String, String> uuids) {
        super(address, name, uuid, description, centralSystem, uuids);
        akonProtocol=formShortProperty(-30L, "ENABLE_AKON_PROTOCOL", "Akon protocol selector", centralSystem, uuids, OBJECTNET_PROTOCOL);
        serialNumber=formLongConfigProperty(1L, "SERIAL NUMBER", "device serial number", centralSystem, uuids);
        serialNumber.setAdditionalInfo(OBJECT_NUM,0);
        serialNumber.setAdditionalInfo(PROPERTY_NUM, 1);
        serialNumber.setAdditionalInfo(PROPERTY_MODBUS, 0x2);
        deviceType=formLongConfigProperty(0L, "TYPE", "device type", centralSystem, uuids);
        deviceType.setAdditionalInfo(OBJECT_NUM,0);
        deviceType.setAdditionalInfo(PROPERTY_NUM, 0);
        deviceType.setAdditionalInfo(PROPERTY_MODBUS, 0x0);
        chanelMask=formLongConfigProperty(2L, "CHANEL'S MASK", "device chanel`s", centralSystem, uuids);
        chanelMask.setAdditionalInfo(OBJECT_NUM,0);
        chanelMask.setAdditionalInfo(PROPERTY_NUM,2);
        chanelMask.setAdditionalInfo(PROPERTY_MODBUS, 0x4);

        po=formAkonLongR(0, 0x64, 0x10, "PO", "software version", centralSystem, uuids);
        reserv=formAkonLongR(0, 0x65, 0x12, "RESERV", "reserved", centralSystem, uuids);
        deviceconfig=formAkonIntRW(0, 0x3, 0x6, "RS_CONFIG", "RS configuration property", centralSystem, uuids);
        systemTime=formAkontimeRW(0, 0x66, 0x14, "TIME", "module time", centralSystem, uuids);
        systemObject=new GroupProperty("SYSTEM OBJECT",null,"system config property group",centralSystem,deviceType,serialNumber,chanelMask,deviceconfig,po,reserv,systemTime);

        saveToFlash=formCommandNameProperty("SAVE", "SAVE TO FLASH MEMORY", centralSystem, uuids);
        readFromFlash=formCommandNameProperty("LOAD", "LOAD FROM FLASH MEMORY", centralSystem, uuids);
        saveToFlash.setAdditionalInfo(OBJECT_NUM,0);
        saveToFlash.setAdditionalInfo(PROPERTY_NUM,6);
        saveToFlash.setAdditionalInfo(PROPERTY_MODBUS,0x8);
        readFromFlash.setAdditionalInfo(OBJECT_NUM,0);
        readFromFlash.setAdditionalInfo(PROPERTY_NUM,5);
        readFromFlash.setAdditionalInfo(PROPERTY_MODBUS,0xA);

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

    protected SoftLongProperty formAkonLongR(int group,int property,long address,String name, String description, MessageSubSystem centralSystem,HashMap<String,String> uuids){
        SoftLongProperty command=new SoftLongProperty(name,getUiidFromMap(name,uuids),description,centralSystem,ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
        command.setAdditionalInfo(PROPERTY_ADDRESS, address);
        command.setAdditionalInfo(PROPERTY_MODBUS, address);
        command.setAdditionalInfo(PROPERTY_NUM, property);
        command.setAdditionalInfo(OBJECT_NUM, group);
        return command;
    }
    protected SoftDateTimeProperty formAkontimeRW(int group,int property,long address,String name, String description, MessageSubSystem centralSystem,HashMap<String,String> uuids){
        SoftDateTimeProperty command=new SoftDateTimeProperty(name,getUiidFromMap(name,uuids),description,centralSystem,ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        command.setAdditionalInfo(PROPERTY_ADDRESS, address);
        command.setAdditionalInfo(PROPERTY_MODBUS, address);
        command.setAdditionalInfo(PROPERTY_NUM, property);
        command.setAdditionalInfo(OBJECT_NUM, group);
        return command;
    }
    protected SoftIntegerProperty formAkonIntRW(int group,int property,long address,String name, String description, MessageSubSystem centralSystem,HashMap<String,String> uuids){
        SoftIntegerProperty command=new SoftIntegerProperty(name,getUiidFromMap(name,uuids),description,centralSystem,ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        command.setAdditionalInfo(PROPERTY_ADDRESS, address);
        command.setAdditionalInfo(PROPERTY_MODBUS, address);
        command.setAdditionalInfo(PROPERTY_NUM, property);
        command.setAdditionalInfo(OBJECT_NUM, group);
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
                        byte[] request = AkonProtocol.formWriteRequestObjectNet((ValueProperty) saveToFlash, (int) deviceAddress);
                        return new RequestInformation(request, 11, false);
                    } catch (IllegalArgumentException e) {
                        fireEvent(EventType.ERROR, e.getLocalizedMessage());
                        return null;
                    }
                }
            case "LOAD":
                protocol=akonProtocol.getValue();
                deviceAddress=super.deviceAddress.getValue();
                if(protocol==OBJECTNET_PROTOCOL) {
                    try {
                        byte[] request = AkonProtocol.formWriteRequestObjectNet((ValueProperty) readFromFlash, (int) deviceAddress);
                        return new RequestInformation(request, 11, false);
                    } catch (IllegalArgumentException e) {
                        fireEvent(EventType.ERROR, e.getLocalizedMessage());
                        return null;
                    }
                }
        }
        return super.processCommandRequest(commandName);
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
}

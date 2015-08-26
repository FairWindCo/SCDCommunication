package ua.pp.fairwind.communications.devices.favorit;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.devices.AbstractDevice;
import ua.pp.fairwind.communications.devices.DeviceInterface;
import ua.pp.fairwind.communications.devices.RequestInformation;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.event.EventType;
import ua.pp.fairwind.communications.propertyes.software.SoftBoolProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftFloatProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;
import ua.pp.fairwind.communications.utils.CommunicationUtils;

import java.util.HashMap;

/**
 * Created by Сергей on 09.07.2015.
 */
public class FavoritCoreDeviceV1 extends AbstractDevice implements DeviceInterface {
    private final SoftBoolProperty digitalInChanelN1;
    private final SoftBoolProperty digitalInChanelN2;
    private final SoftBoolProperty digitalInChanelN3;
    private final SoftBoolProperty digitalInChanelN4;
    private final SoftBoolProperty digitalInChanelN5;
    private final SoftBoolProperty digitalInChanelN6;
    private final SoftBoolProperty digitalOutChanelN1;
    private final SoftBoolProperty digitalOutChanelN2;
    private final SoftBoolProperty digitalOutChanelN3;
    private final SoftBoolProperty digitalOutChanelN4;
    private final SoftBoolProperty digitalOutChanelN5;
    private final SoftBoolProperty digitalOutChanelN6;

    private final SoftFloatProperty analogOutChanelN1;
    private final SoftFloatProperty analogOutChanelN2;
    private final SoftFloatProperty analogOutChanelN3;
    private final SoftFloatProperty analogOutChanelN4;

    private final SoftFloatProperty analogInChanelN1;
    private final SoftFloatProperty analogInChanelN2;
    private final SoftFloatProperty analogInChanelN3;
    private final SoftFloatProperty analogInChanelN4;

    private final SoftLongProperty lineSelect;
    private final SoftShortProperty configdeviceAddress;
    private final SoftShortProperty configdeviceSpeed;



    public FavoritCoreDeviceV1(long address, String name, String uuid, String description, MessageSubSystem centralSystem) {
        this(address, name, uuid, description, centralSystem, null);
    }

    protected SoftBoolProperty formDIProperty(long address,String name, String description, MessageSubSystem centralSystem,HashMap<String,String> uuids){
        SoftBoolProperty command=new SoftBoolProperty(name,getUiidFromMap(name,uuids),description,centralSystem,true,true);
        command.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, address);
        return command;
    }

    protected SoftBoolProperty formDOProperty(long address,String name, String description, MessageSubSystem centralSystem,HashMap<String,String> uuids){
        SoftBoolProperty command=new SoftBoolProperty(name,getUiidFromMap(name,uuids),description,centralSystem,false,false);
        command.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, address);
        return command;
    }

    protected SoftFloatProperty formAOProperty(long address,String name, String description, MessageSubSystem centralSystem,HashMap<String,String> uuids){
        SoftFloatProperty command=new SoftFloatProperty(name,getUiidFromMap(name,uuids),description,centralSystem,false,false);
        command.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, address);
        return command;
    }

    protected SoftFloatProperty formAIProperty(long address,String name, String description, MessageSubSystem centralSystem,HashMap<String,String> uuids){
        SoftFloatProperty command=new SoftFloatProperty(name,getUiidFromMap(name,uuids),description,centralSystem,true,true);
        command.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, address);
        return command;
    }

    public FavoritCoreDeviceV1(long address, String name, String uuid, String description, MessageSubSystem centralSystem, HashMap<String, String> uuids) {
        super(address, name, uuid, description, centralSystem, uuids);
        digitalOutChanelN1=formDOProperty(401, "DIGITAL_OUT_1", "Цифровой выход", centralSystem, uuids);
        digitalOutChanelN2=formDOProperty(402, "DIGITAL_OUT_2", "Цифровой выход", centralSystem, uuids);
        digitalOutChanelN3=formDOProperty(403, "DIGITAL_OUT_3", "Цифровой выход", centralSystem, uuids);
        digitalOutChanelN4=formDOProperty(404, "DIGITAL_OUT_4", "Цифровой выход", centralSystem, uuids);
        digitalOutChanelN5=formDOProperty(405, "DIGITAL_OUT_5", "Цифровой выход", centralSystem, uuids);
        digitalOutChanelN6=formDOProperty(406, "DIGITAL_OUT_6", "Цифровой выход", centralSystem, uuids);

        digitalInChanelN1=formDIProperty(301, "DIGITAL_IN_1", "Цифровой вход", centralSystem, uuids);
        digitalInChanelN2=formDIProperty(302, "DIGITAL_IN_2", "Цифровой вход", centralSystem, uuids);
        digitalInChanelN3=formDIProperty(303, "DIGITAL_IN_3", "Цифровой вход", centralSystem, uuids);
        digitalInChanelN4=formDIProperty(304, "DIGITAL_IN_4", "Цифровой вход", centralSystem, uuids);
        digitalInChanelN5=formDIProperty(305, "DIGITAL_IN_5", "Цифровой вход", centralSystem, uuids);
        digitalInChanelN6=formDIProperty(306, "DIGITAL_IN_6", "Цифровой вход", centralSystem, uuids);

        analogInChanelN1=formAIProperty(101, "ANALOG_IN_1", "Аналоговый вход", centralSystem, uuids);
        analogInChanelN2=formAIProperty(102, "ANALOG_IN_2", "Аналоговый вход", centralSystem, uuids);
        analogInChanelN3=formAIProperty(103, "ANALOG_IN_3", "Аналоговый вход", centralSystem, uuids);
        analogInChanelN4=formAIProperty(104, "ANALOG_IN_4", "Аналоговый вход", centralSystem, uuids);

        analogOutChanelN1=formAOProperty(201, "ANALOG_OUT_1", "Аналоговый выход", centralSystem, uuids);
        analogOutChanelN2=formAOProperty(202, "ANALOG_OUT_2", "Аналоговый выход", centralSystem, uuids);
        analogOutChanelN3=formAOProperty(203, "ANALOG_OUT_3", "Аналоговый выход", centralSystem, uuids);
        analogOutChanelN4=formAOProperty(204, "ANALOG_OUT_4", "Аналоговый выход", centralSystem, uuids);

        lineSelect=new SoftLongProperty("RS_LINE_SELECT",getUiidFromMap("RS_LINE_SELECT",uuids),"Выбор внешней RS линии",centralSystem,false,false);
        lineSelect.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 501L);

        configdeviceAddress=new SoftShortProperty("CONFIGURE_DEVICE_ADDRESS",getUiidFromMap("CONFIGURE_DEVICE_ADDRESS",uuids),"Установка адреса кстройства",centralSystem,false,false);
        configdeviceAddress.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 001L);

        configdeviceSpeed=new SoftShortProperty("CONFIGURE_DEVICE_SPEED",getUiidFromMap("CONFIGURE_DEVICE_SPEED",uuids),"Установка скорости устройства",centralSystem,false,false);
        configdeviceSpeed.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 002L);

        addPropertys(configdeviceAddress);
        addPropertys(configdeviceSpeed);
        addPropertys(analogInChanelN1);
        addPropertys(analogInChanelN2);
        addPropertys(analogInChanelN3);
        addPropertys(analogInChanelN4);
        addPropertys(analogOutChanelN1);
        addPropertys(analogOutChanelN2);
        addPropertys(analogOutChanelN3);
        addPropertys(analogOutChanelN4);
        addPropertys(digitalInChanelN1);
        addPropertys(digitalInChanelN2);
        addPropertys(digitalInChanelN3);
        addPropertys(digitalInChanelN4);
        addPropertys(digitalInChanelN5);
        addPropertys(digitalInChanelN6);
        addPropertys(digitalOutChanelN1);
        addPropertys(digitalOutChanelN2);
        addPropertys(digitalOutChanelN3);
        addPropertys(digitalOutChanelN4);
        addPropertys(digitalOutChanelN5);
        addPropertys(digitalOutChanelN6);
        addPropertys(lineSelect);
    }

    @Override
    protected SoftBoolProperty formBoolProperty(long address, String name, String description, MessageSubSystem centralSystem, HashMap<String, String> uuids, boolean initialValue) {
        SoftBoolProperty prp= super.formDeviceBoolProperty(address, name, description, centralSystem, uuids, initialValue);
        return prp;
    }

    private int calculateCrc(byte[] buffer,int pos,int length){
        if(buffer!=null&&buffer.length>6&&buffer.length>pos+length){
            int sum=0;
            for (int i=pos;i<pos+length;i++){
                sum+=(buffer[i]&0xFF);
            }
            return sum&0xFF;
        } else{
            return -1;
        }
    }

    protected boolean processRecivedMessage(final byte[] recivedMessage,final byte[] sendMessage,final AbstractProperty property){
        if(recivedMessage!=null && recivedMessage.length>10 && property!=null){
            int position=-1;
            for(int i=0;i<recivedMessage.length-3;i++){
                if(recivedMessage[i]=='V'&&recivedMessage[i+1]=='N'&&recivedMessage[i+2]=='T'){
                    position=i;
                    break;
                }
            }
            if(position>=0){
                SoftLongProperty devaddress=deviceAddress;
                long deviceaddress=devaddress.getValue();
                long property_address=(long)property.getAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS);
                int responseAddress=CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 3])<<4;
                responseAddress+=CommunicationUtils.getHalfByteFromChar((char)recivedMessage[position+4]);
                if(responseAddress==deviceaddress){
                    int command=CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 5])<<4;
                    command+=CommunicationUtils.getHalfByteFromChar((char)recivedMessage[position+6]);
                    switch (command){
                        case 0x11:
                        case 0x21:{
                            int propertyNum = CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 7]) << 4;
                            propertyNum += CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 8]);
                            if (position + 17 > recivedMessage.length) {
                                fireEvent(EventType.ERROR, "READ PROPERTY FAIL RESPONSE TOO SMALL! ");
                                return false;
                            }
                            if ((property_address % 10) != propertyNum || recivedMessage[position + 13] != 'O' || recivedMessage[position + 14] != 'K') {
                                return false;
                            } else {
                                int crc = CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 15]) << 4;
                                crc += CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 16]);
                                int calcCRC = calculateCrc(recivedMessage, position, 15);
                                if (crc != calcCRC) {
                                    fireEvent(EventType.ERROR, "READ PROPERTY FAIL: CRC ERROR!");
                                    return false;
                                } else {
                                    /*
                                    int value=CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 9])<<4;

                                    value+=CommunicationUtils.getHalfByteFromChar((char)recivedMessage[position+10]);
                                     */
                                    int val = CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 9]) << 12;
                                    val += CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 10]) << 8;
                                    val += CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 11]) << 4;
                                    val += CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 12]);
                                    float value = (val*10.0f) / 0x3FF ;
                                    ((ValueProperty<Float>) property).setHardWareInternalValue(value);
                                    return true;
                                }
                            }

                        }
                        case 0x31:
                        case 0x41: {
                            int propertyNum = CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 7]) << 4;
                            propertyNum += CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 8]);
                            if (position + 15 > recivedMessage.length) {
                                fireEvent(EventType.ERROR, "READ PROPERTY FAIL RESPONSE TOO SMALL! ");
                                return false;
                            }
                            if ((property_address % 10) != propertyNum || recivedMessage[position + 11] != 'O' || recivedMessage[position + 12] != 'K') {
                                return false;
                            } else {
                                int crc = CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 13]) << 4;
                                crc += CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 14]);
                                int calcCRC = calculateCrc(recivedMessage, position, 13);
                                if (crc != calcCRC) {
                                    fireEvent(EventType.ERROR, "READ PROPERTY FAIL: CRC ERROR!");
                                    return false;
                                } else {
                                    /*
                                    int value=CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 9])<<4;

                                    value+=CommunicationUtils.getHalfByteFromChar((char)recivedMessage[position+10]);
                                     */
                                    if (recivedMessage[position + 10] == '1') {
                                        ((ValueProperty<Boolean>) property).setHardWareInternalValue(true);
                                    } else {
                                        ((ValueProperty<Boolean>) property).setHardWareInternalValue(false);
                                    }
                                    return true;
                                }
                            }
                        }
                        case 0x23:
                        case 0x43: {
                            int propertyNum = CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 7]) << 4;
                            propertyNum += CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 8]);
                            if (position + 13 > recivedMessage.length) {
                                fireEvent(EventType.ERROR, "WRITE PROPERTY FAIL RESPONSE TOO SMALL! ");
                                return false;
                            }
                            if ((property_address % 10) != propertyNum || recivedMessage[position + 9] != 'O' || recivedMessage[position + 10] != 'K') {
                                fireEvent(EventType.ERROR, "WRITE PROPERTY FAIL: " + property);
                                return false;
                            } else {
                                int crc = CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 11]) << 4;
                                crc += CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 12]);
                                int calcCRC = calculateCrc(recivedMessage, position, 11);
                                if (crc != calcCRC) {
                                    fireEvent(EventType.ERROR, "WRITE PROPERTY FAIL: CRC ERROR!");
                                    return false;
                                }
                                return true;
                            }
                        }
                        case 0x50: {
                            if (position + 15 > recivedMessage.length) {
                                fireEvent(EventType.ERROR, "READ PROPERTY FAIL RESPONSE TOO SMALL! ");
                                return false;
                            }
                            if (recivedMessage[position + 11] != 'O' || recivedMessage[position + 12] != 'K') {
                                fireEvent(EventType.ERROR, "READ PROPERTY FAIL: " + property);
                                return false;
                            } else {
                                int crc = CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 13]) << 4;
                                crc += CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 14]);
                                int calcCRC = calculateCrc(recivedMessage, position, 13);
                                if (crc != calcCRC) {
                                    fireEvent(EventType.ERROR, "READ PROPERTY FAIL: CRC ERROR!");
                                    return false;
                                }
                                long val = CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 9]) << 4;
                                val += CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 10]);
                                ((ValueProperty<Long>)property).setHardWareInternalValue(val);
                                return true;
                            }
                        }
                        case 0x51: {
                            if (position + 13 > recivedMessage.length) {
                                fireEvent(EventType.ERROR, "WRITE PROPERTY FAIL RESPONSE TOO SMALL! ");
                                return false;
                            }
                            if (recivedMessage[position + 9] != 'O' || recivedMessage[position + 10] != 'K') {
                                fireEvent(EventType.ERROR, "WRITE PROPERTY FAIL: " + property);
                                return false;
                            } else {
                                int crc = CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 11]) << 4;
                                crc += CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 12]);
                                int calcCRC = calculateCrc(recivedMessage, position, 11);
                                if (crc != calcCRC) {
                                    fireEvent(EventType.ERROR, "WRITE PROPERTY FAIL: CRC ERROR!");
                                    return false;
                                }
                                long val = CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 7]) << 4;
                                val += CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 8]);
                                ((ValueProperty<Long>)property).setHardWareInternalValue(val);
                                return true;
                            }
                        }
                        default:
                            fireEvent(EventType.ERROR,"UNKNOWN COMMAND FROM HARDWARE!");
                            return false;
                    }
                } else  {
                    fireEvent(EventType.ERROR,"INCORRECT DEVICE ADDRESS IN RESPONSE FROM HARDWARE!");
                    return false;
                }
            } else {
                fireEvent(EventType.ERROR,"INCORRECT RESPONSE FROM HARDWARE!");
                return false;
            }
        }
        return false;
    }

    @Override
    protected RequestInformation formReadRequest(AbstractProperty property) {
        if(property==null) return null;
        SoftLongProperty devaddress=deviceAddress;
        long deviceaddress=devaddress.getValue();
        long property_address=(long)property.getAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS);
        int property_command=(int)((property_address/100)&0xF);
        switch (property_command){
            case 0:{
                //READ CONFIG
                byte[] buffer=new byte[12];
                buffer[0]='V';
                buffer[1]='N';
                buffer[2]='T';
                buffer[3]=CommunicationUtils.getHalfByteHex((deviceaddress & 0xF0) >> 4);
                buffer[4]=CommunicationUtils.getHalfByteHex(deviceaddress&0xF);
                //-----------------------------------------
                buffer[5]='0';
                buffer[6]=(property_address%10)==0?(byte)'0':(byte)'1';
                buffer[7]='F';
                buffer[8]='F';
                //-----------------------------------------
                int sum=0;
                for(int i=0;i<buffer.length-2;i++){sum+=buffer[i];}
                buffer[buffer.length-2]=CommunicationUtils.getHalfByteHex((sum & 0xF0) >> 4);
                buffer[buffer.length-1]=CommunicationUtils.getHalfByteHex(sum&0xF);
                return new RequestInformation(buffer,15);
            }
            case 1:{
                //READ AI
                byte[] buffer=new byte[13];
                buffer[0]='V';
                buffer[1]='N';
                buffer[2]='T';
                buffer[3]=CommunicationUtils.getHalfByteHex((deviceaddress & 0xF0) >> 4);
                buffer[4]=CommunicationUtils.getHalfByteHex(deviceaddress&0xF);
                //-----------------------------------------
                buffer[5]='1';
                buffer[6]='1';
                buffer[7]='0';
                buffer[8]=CommunicationUtils.getHalfByteHex(property_address%10);//-LINE NUMBER
                buffer[9]='F';
                buffer[10]='F';
                //-----------------------------------------
                int sum=0;
                for(int i=0;i<buffer.length-2;i++){sum+=buffer[i];}
                buffer[buffer.length-2]=CommunicationUtils.getHalfByteHex((sum & 0xF0) >> 4);
                buffer[buffer.length-1]=CommunicationUtils.getHalfByteHex(sum&0xF);
                return new RequestInformation(buffer,17);
            }
            case 2:{
                //READ AO
                byte[] buffer=new byte[13];
                buffer[0]='V';
                buffer[1]='N';
                buffer[2]='T';
                buffer[3]=CommunicationUtils.getHalfByteHex((deviceaddress & 0xF0) >> 4);
                buffer[4]=CommunicationUtils.getHalfByteHex(deviceaddress&0xF);
                //-----------------------------------------
                buffer[5]='2';
                buffer[6]='1';
                buffer[7]='0';
                buffer[8]=CommunicationUtils.getHalfByteHex(property_address%10);//-LINE NUMBER
                buffer[9]='F';
                buffer[10]='F';
                //-----------------------------------------
                int sum=0;
                for(int i=0;i<buffer.length-2;i++){sum+=buffer[i];}
                buffer[buffer.length-2]=CommunicationUtils.getHalfByteHex((sum & 0xF0) >> 4);
                buffer[buffer.length-1]=CommunicationUtils.getHalfByteHex(sum&0xF);
                return new RequestInformation(buffer,17);
            }
            case 3:{
                //READ DI
                byte[] buffer=new byte[13];
                buffer[0]='V';
                buffer[1]='N';
                buffer[2]='T';
                buffer[3]=CommunicationUtils.getHalfByteHex((deviceaddress & 0xF0) >> 4);
                buffer[4]=CommunicationUtils.getHalfByteHex(deviceaddress&0xF);
                //-----------------------------------------
                buffer[5]='3';
                buffer[6]='1';
                buffer[7]='0';
                buffer[8]=CommunicationUtils.getHalfByteHex(property_address%10);//-LINE NUMBER
                buffer[9]='F';
                buffer[10]='F';
                //-----------------------------------------
                int sum=0;
                for(int i=0;i<buffer.length-2;i++){sum+=buffer[i];}
                buffer[buffer.length-2]=CommunicationUtils.getHalfByteHex((sum & 0xF0) >> 4);
                buffer[buffer.length-1]=CommunicationUtils.getHalfByteHex(sum&0xF);
                return new RequestInformation(buffer,15);
            }
            case 4:{
                //READ DO
                byte[] buffer=new byte[13];
                buffer[0]='V';
                buffer[1]='N';
                buffer[2]='T';
                buffer[3]=CommunicationUtils.getHalfByteHex((deviceaddress & 0xF0) >> 4);
                buffer[4]=CommunicationUtils.getHalfByteHex(deviceaddress&0xF);
                //-----------------------------------------
                buffer[5]='4';
                buffer[6]='1';
                buffer[7]='0';
                buffer[8]=CommunicationUtils.getHalfByteHex(property_address%10);//-LINE NUMBER
                buffer[9]='F';
                buffer[10]='F';
                //-----------------------------------------
                int sum=0;
                for(int i=0;i<buffer.length-2;i++){sum+=buffer[i];}
                buffer[buffer.length-2]=CommunicationUtils.getHalfByteHex((sum & 0xF0) >> 4);
                buffer[buffer.length-1]=CommunicationUtils.getHalfByteHex(sum&0xF);
                return new RequestInformation(buffer,15);
            }
            case 5:{
                //READ LINE SELECT
                byte[] buffer=new byte[13];
                buffer[0]='V';
                buffer[1]='N';
                buffer[2]='T';
                buffer[3]=CommunicationUtils.getHalfByteHex((deviceaddress & 0xF0) >> 4);
                buffer[4]=CommunicationUtils.getHalfByteHex(deviceaddress&0xF);
                //-----------------------------------------
                buffer[5]='5';
                buffer[6]='0';
                buffer[7]='0';
                buffer[8]='0';
                buffer[9]='F';
                buffer[10]='F';
                //-----------------------------------------
                int sum=0;
                for(int i=0;i<buffer.length-2;i++){sum+=buffer[i];}
                buffer[buffer.length-2]=CommunicationUtils.getHalfByteHex((sum & 0xF0) >> 4);
                buffer[buffer.length-1]=CommunicationUtils.getHalfByteHex(sum&0xF);
                return new RequestInformation(buffer,15);
            }
            default:return null;
        }
    }

    @Override
    protected RequestInformation formWriteRequest(AbstractProperty property) {
        if(property==null) return null;
        SoftLongProperty devaddress=deviceAddress;
        long deviceaddress=devaddress.getValue();
        long property_address=(long)property.getAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS);

        int property_command=(int)((property_address/100)&0xF);
        switch (property_command){
            case 0:{
                //WRITE CONFIG
                SoftLongProperty out=(SoftLongProperty)property;
                long val=out.getValue()==null?0:out.getValue();
                byte[] buffer=new byte[12];
                buffer[0]='V';
                buffer[1]='N';
                buffer[2]='T';
                buffer[3]=CommunicationUtils.getHalfByteHex((deviceaddress & 0xF0) >> 4);
                buffer[4]=CommunicationUtils.getHalfByteHex(deviceaddress&0xF);
                //-----------------------------------------
                buffer[5]='0';
                if(property_address==1){
                    if(val<1) val=1;
                    if(val>4) val=4;
                    buffer[6]='0';
                } else {
                    buffer[6]='2';
                }

                buffer[7]=CommunicationUtils.getHalfByteHex((val>>4)&0xF);
                buffer[8]=CommunicationUtils.getHalfByteHex(val&0xF);
                buffer[9]='F';
                buffer[10]='F';
                //-----------------------------------------
                int sum=0;
                for(int i=0;i<buffer.length-2;i++){sum+=buffer[i];}
                buffer[buffer.length-2]=CommunicationUtils.getHalfByteHex((sum & 0xF0) >> 4);
                buffer[buffer.length-1]=CommunicationUtils.getHalfByteHex(sum&0xF);
                return new RequestInformation(buffer,13,true);
            }
            case 2:{
                //WRITE AO
                SoftFloatProperty out=(SoftFloatProperty)property;
                float value=out.getValue()==null?0:out.getValue();
                int val=(int)((value/10.0f)*0x3FF);

                if(value>10)val=0x3FF;
                if(val<0)val=0;

                byte[] buffer=new byte[17];
                buffer[0]='V';
                buffer[1]='N';
                buffer[2]='T';
                buffer[3]=CommunicationUtils.getHalfByteHex((deviceaddress & 0xF0) >> 4);
                buffer[4]=CommunicationUtils.getHalfByteHex(deviceaddress&0xF);
                //-----------------------------------------
                buffer[5]='2';
                buffer[6]='3';
                buffer[7]='0';
                buffer[8]=CommunicationUtils.getHalfByteHex(property_address%10);//-LINE NUMBER
                buffer[9]=CommunicationUtils.getHalfByteHex((val>>12)&0xF);
                buffer[10]=CommunicationUtils.getHalfByteHex((val>>8)&0xF);
                buffer[11]=CommunicationUtils.getHalfByteHex((val>>4)&0xF);
                buffer[12]=CommunicationUtils.getHalfByteHex(val&0xF);
                buffer[13]='F';
                buffer[14]='F';
                //-----------------------------------------
                int sum=0;
                for(int i=0;i<buffer.length-2;i++){sum+=buffer[i];}
                buffer[buffer.length-2]=CommunicationUtils.getHalfByteHex((sum & 0xF0) >> 4);
                buffer[buffer.length-1]=CommunicationUtils.getHalfByteHex(sum&0xF);
                return new RequestInformation(buffer,13,true);
            }
            case 4:{
                //WRITE DO
                SoftBoolProperty dig_out=(SoftBoolProperty)property;
                boolean value=dig_out.getValue()==null?false:dig_out.getValue();
                byte[] buffer=new byte[15];
                buffer[0]='V';
                buffer[1]='N';
                buffer[2]='T';
                buffer[3]=CommunicationUtils.getHalfByteHex((deviceaddress & 0xF0) >> 4);
                buffer[4]=CommunicationUtils.getHalfByteHex(deviceaddress&0xF);
                //-----------------------------------------
                buffer[5]='4';
                buffer[6]='3';
                buffer[7]='0';
                buffer[8]=CommunicationUtils.getHalfByteHex(property_address%10);//-LINE NUMBER
                buffer[9]='0';
                buffer[10]=(byte)(value?'1':'0');
                buffer[11]='F';
                buffer[12]='F';
                //-----------------------------------------
                int sum=0;
                for(int i=0;i<buffer.length-2;i++){sum+=buffer[i];}
                buffer[buffer.length-2]=CommunicationUtils.getHalfByteHex((sum & 0xF0) >> 4);
                buffer[buffer.length-1]=CommunicationUtils.getHalfByteHex(sum&0xF);
                return new RequestInformation(buffer,13,true);
            }
            case 5:{
                //WRITE LINE SELECT
                SoftLongProperty out=(SoftLongProperty)property;
                long val=out.getValue()==null?0:out.getValue();
                if(val>4)val=4;
                if(val<0)val=0;

                byte[] buffer=new byte[13];
                buffer[0]='V';
                buffer[1]='N';
                buffer[2]='T';
                buffer[3]=CommunicationUtils.getHalfByteHex((deviceaddress & 0xF0) >> 4);
                buffer[4]=CommunicationUtils.getHalfByteHex(deviceaddress&0xF);
                //-----------------------------------------
                buffer[5]='5';
                buffer[6]='1';
                buffer[7]=CommunicationUtils.getHalfByteHex((val>>4)&0xF);
                buffer[8]=CommunicationUtils.getHalfByteHex(val&0xF);
                buffer[9]='F';
                buffer[10]='F';
                //-----------------------------------------
                int sum=0;
                for(int i=0;i<buffer.length-2;i++){sum+=buffer[i];}
                buffer[buffer.length-2]=CommunicationUtils.getHalfByteHex((sum & 0xF0) >> 4);
                buffer[buffer.length-1]=CommunicationUtils.getHalfByteHex(sum&0xF);
                return new RequestInformation(buffer,13,true);
            }
            default:return null;
        }
    }

    public SoftBoolProperty getDigitalInChanelN1() {
        return digitalInChanelN1;
    }

    public SoftBoolProperty getDigitalInChanelN2() {
        return digitalInChanelN2;
    }

    public SoftBoolProperty getDigitalInChanelN3() {
        return digitalInChanelN3;
    }

    public SoftBoolProperty getDigitalInChanelN4() {
        return digitalInChanelN4;
    }

    public SoftBoolProperty getDigitalInChanelN5() {
        return digitalInChanelN5;
    }

    public SoftBoolProperty getDigitalInChanelN6() {
        return digitalInChanelN6;
    }

    public SoftBoolProperty getDigitalOutChanelN1() {
        return digitalOutChanelN1;
    }

    public SoftBoolProperty getDigitalOutChanelN2() {
        return digitalOutChanelN2;
    }

    public SoftBoolProperty getDigitalOutChanelN3() {
        return digitalOutChanelN3;
    }

    public SoftBoolProperty getDigitalOutChanelN4() {
        return digitalOutChanelN4;
    }

    public SoftBoolProperty getDigitalOutChanelN5() {
        return digitalOutChanelN5;
    }

    public SoftBoolProperty getDigitalOutChanelN6() {
        return digitalOutChanelN6;
    }

    public SoftFloatProperty getAnalogOutChanelN1() {
        return analogOutChanelN1;
    }

    public SoftFloatProperty getAnalogOutChanelN2() {
        return analogOutChanelN2;
    }

    public SoftFloatProperty getAnalogOutChanelN3() {
        return analogOutChanelN3;
    }

    public SoftFloatProperty getAnalogOutChanelN4() {
        return analogOutChanelN4;
    }

    public SoftFloatProperty getAnalogInChanelN1() {
        return analogInChanelN1;
    }

    public SoftFloatProperty getAnalogInChanelN2() {
        return analogInChanelN2;
    }

    public SoftFloatProperty getAnalogInChanelN3() {
        return analogInChanelN3;
    }

    public SoftFloatProperty getAnalogInChanelN4() {
        return analogInChanelN4;
    }

    public SoftLongProperty getLineSelect() {
        return lineSelect;
    }

    public SoftShortProperty getConfigdeviceAddress() {
        return configdeviceAddress;
    }

    public SoftShortProperty getConfigdeviceSpeed() {
        return configdeviceSpeed;
    }
}

package ua.pp.fairwind.communications.devices.hardwaredevices.favorit;

import jssc.SerialPort;
import ua.pp.fairwind.communications.abstractions.LineSelector;
import ua.pp.fairwind.communications.devices.RequestInformation;
import ua.pp.fairwind.communications.devices.abstracts.AbstractDevice;
import ua.pp.fairwind.communications.devices.abstracts.LineSelectDevice;
import ua.pp.fairwind.communications.devices.abstracts.RSLineDevice;
import ua.pp.fairwind.communications.lines.lineparams.CommunicationLineParameters;
import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.messagesystems.event.EventType;
import ua.pp.fairwind.communications.propertyes.DeviceNamedCommandProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftBoolProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftFloatProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;
import ua.pp.fairwind.communications.utils.CommunicationUtils;

/**
 * Created by Сергей on 09.07.2015.
 */
public class FavoritCoreDeviceV1 extends RSLineDevice implements LineSelectDevice {
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

    private final DeviceNamedCommandProperty readAllAI;
    private final DeviceNamedCommandProperty readAllAO;
    private final DeviceNamedCommandProperty readAllDI;
    private final DeviceNamedCommandProperty readAllDO;
    private final DeviceNamedCommandProperty writeAllAO;
    private final DeviceNamedCommandProperty writeAllDO;



    public static FavoritCoreDeviceV1 createFavoritCoreDiveceV1(long address, String name, String uuid){
        FavoritCoreDeviceV1 property=new FavoritCoreDeviceV1(address, name, uuid);
        return property;
    }

    protected SoftBoolProperty formDIProperty(long address,String name){
        SoftBoolProperty command=new SoftBoolProperty(name,ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
        command.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, address);
        return command;
    }

    protected SoftBoolProperty formDOProperty(long address,String name){
        SoftBoolProperty command=new SoftBoolProperty(name, ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        command.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, address);
        return command;
    }

    protected SoftFloatProperty formAOProperty(long address,String name){
        SoftFloatProperty command=new SoftFloatProperty(name,ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        command.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, address);
        return command;
    }

    protected SoftFloatProperty formAIProperty(long address,String name){
        SoftFloatProperty command=new SoftFloatProperty(name,ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
        command.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, address);
        return command;
    }

    public FavoritCoreDeviceV1(long address){
        this(address,"FAVORIT_VENTIL",null);
    }

    public FavoritCoreDeviceV1(long address, String codename, String uuid) {
        super(address, codename, uuid);
        digitalOutChanelN1=formDOProperty(401, codename+".DIGITAL_OUT_1");
        digitalOutChanelN2=formDOProperty(402, codename+".DIGITAL_OUT_2");
        digitalOutChanelN3=formDOProperty(403, codename+".DIGITAL_OUT_3");
        digitalOutChanelN4=formDOProperty(404, codename+".DIGITAL_OUT_4");
        digitalOutChanelN5=formDOProperty(405, codename+".DIGITAL_OUT_5");
        digitalOutChanelN6=formDOProperty(406, codename + ".DIGITAL_OUT_6");

        digitalInChanelN1=formDIProperty(301, codename + ".DIGITAL_IN_1");
        digitalInChanelN2=formDIProperty(302, codename + ".DIGITAL_IN_2");
        digitalInChanelN3=formDIProperty(303, codename + ".DIGITAL_IN_3");
        digitalInChanelN4=formDIProperty(304, codename + ".DIGITAL_IN_4");
        digitalInChanelN5=formDIProperty(305, codename + ".DIGITAL_IN_5");
        digitalInChanelN6=formDIProperty(306, codename + ".DIGITAL_IN_6");

        analogInChanelN1=formAIProperty(101, codename + ".ANALOG_IN_1");
        analogInChanelN2=formAIProperty(102, codename + ".ANALOG_IN_2");
        analogInChanelN3=formAIProperty(103, codename + ".ANALOG_IN_3");
        analogInChanelN4=formAIProperty(104, codename + ".ANALOG_IN_4");

        analogOutChanelN1=formAOProperty(201, codename + ".ANALOG_OUT_1");
        analogOutChanelN2=formAOProperty(202, codename + ".ANALOG_OUT_2");
        analogOutChanelN3=formAOProperty(203, codename + ".ANALOG_OUT_3");
        analogOutChanelN4=formAOProperty(204, codename + ".ANALOG_OUT_4");

        lineSelect=new SoftLongProperty(codename+".RS_LINE_SELECT",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        lineSelect.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 501L);

        configdeviceAddress=new SoftShortProperty(codename+".CONFIGURE_DEVICE_ADDRESS",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        configdeviceAddress.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 001L);

        configdeviceSpeed=new SoftShortProperty(codename+".CONFIGURE_DEVICE_SPEED",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        configdeviceSpeed.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 002L);

        readAllDI=formCommandNameProperty(codename+".READ_ALL_DI","READ_ALL_DI");
        readAllDO=formCommandNameProperty(codename+".READ_ALL_DO","READ_ALL_DO");
        readAllAI=formCommandNameProperty(codename+".READ_ALL_AI","READ_ALL_AI");
        readAllAO=formCommandNameProperty(codename+".READ_ALL_AO","READ_ALL_AO");
        writeAllDO=formCommandNameProperty(codename+".WRITE_ALL_DO","WRITE_ALL_DO");
        writeAllAO=formCommandNameProperty(codename+".WRITE_ALL_AO","WRITE_ALL_AO");
        addCommands(readAllAI);
        addCommands(readAllAO);
        addCommands(readAllDI);
        addCommands(readAllDO);
        addCommands(writeAllAO);
        addCommands(writeAllDO);

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
        setLineParameters(new CommunicationLineParameters(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.PARITY_NONE,SerialPort.STOPBITS_2, SerialPort.FLOWCONTROL_NONE));
    }

    @Override
    protected SoftBoolProperty formBoolProperty(long address, String name, boolean initialValue) {
        SoftBoolProperty prp= super.formDeviceBoolProperty(address, name, initialValue);
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

    @Override
    public LineSelector selectLine(Object needLine) {
        if(needLine!=null && needLine instanceof Number){
            long deviceaddress=deviceAddress.getValue();
            long val=((Number)needLine).longValue();
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

            byte[] buffer_test=new byte[13];
            buffer_test[0]='V';
            buffer_test[1]='N';
            buffer_test[2]='T';
            buffer_test[3]=CommunicationUtils.getHalfByteHex((deviceaddress & 0xF0) >> 4);
            buffer_test[4]=CommunicationUtils.getHalfByteHex(deviceaddress&0xF);
            //-----------------------------------------
            buffer_test[5]='5';
            buffer_test[6]='1';
            buffer_test[7]=CommunicationUtils.getHalfByteHex((val>>4)&0xF);
            buffer_test[8]=CommunicationUtils.getHalfByteHex(val&0xF);
            buffer_test[9]='O';
            buffer_test[10]='K';
            //-----------------------------------------
            sum=0;
            for(int i=0;i<buffer.length-2;i++){sum+=buffer_test[i];}
            buffer_test[buffer_test.length-2]=CommunicationUtils.getHalfByteHex((sum & 0xF0) >> 4);
            buffer_test[buffer_test.length-1]=CommunicationUtils.getHalfByteHex(sum&0xF);
            Long devTO=(Long)getInternalValue(deviceTimeOut);
            Long devTOP=(Long)getInternalValue(deviceTimeOutPause);
            Long devWP=(Long)getInternalValue(deviceWritePause);
            long maxRetry=(Long)getInternalValue(retryCount);
            LineSelector ls=new LineSelector(buffer,buffer_test,lineparams,devTO+lineSelect.getPropertyTimeOutRead(),devTOP+lineSelect.getPropertyPauseBeforeRead(),devWP+lineSelect.getPropertyPauseBeforeWrite());
            return ls;
        }
        return null;
    }

    protected boolean processRecivedMessage(final byte[] recivedMessage,final byte[] sendMessage,final AbstractProperty property,final Event sourceEvent){
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
                Long p_address=(Long)property.getAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS);
                long property_address=p_address!=null?(long)p_address:-1;
                int responseAddress=CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 3])<<4;
                responseAddress+=CommunicationUtils.getHalfByteFromChar((char)recivedMessage[position+4]);
                if(responseAddress==deviceaddress){
                    int command=CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 5])<<4;
                    command+=CommunicationUtils.getHalfByteFromChar((char)recivedMessage[position+6]);
                    switch (command){
                        case 0x00:
                        case 0x01:
                        case 0x02:
                        case 0x03:{
                            if (position + 13 > recivedMessage.length) {
                                fireEvent(EventType.ERROR, "READ PROPERTY FAIL RESPONSE TOO SMALL! ");
                                return false;
                            }
                            if ((recivedMessage[position + 9] != 'O' || recivedMessage[position + 10] != 'K')){
                                return false;
                            } else {
                                int crc = CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 9]) << 4;
                                crc += CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 10]);
                                int calcCRC = calculateCrc(recivedMessage, position, 15);
                                if (crc != calcCRC) {
                                    fireEvent(EventType.ERROR, "READ PROPERTY FAIL: CRC ERROR!");
                                    return false;
                                } else {
                                    if(property_address==2 && (command==0x02||command==0x03)){
                                        int val = CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 7]) << 4;
                                        val += CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 8]);
                                        if(val<0)val=0;
                                        if(val>4)val=4;
                                        setHardWareInternalValue(((ValueProperty<Short>)property),(short)val,sourceEvent);
                                        return true;
                                    }
                                    if(property_address==1 && (command==0x00||command==0x01)){
                                        int val = CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 7]) << 4;
                                        val += CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 8]);
                                        if(val<0)val=0;
                                        if(val>255)val=255;
                                        setHardWareInternalValue(((ValueProperty<Short>) property), (short) val,sourceEvent);
                                        return true;
                                    }
                                    return false;
                                }
                            }
                        }
                        case 0x10:
                        case 0x20:{
                            int propertyNum = CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 7]) << 4;
                            propertyNum += CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 8]);
                            if (position + 29 > recivedMessage.length) {
                                fireEvent(EventType.ERROR, "READ PROPERTY FAIL RESPONSE TOO SMALL! ");
                                return false;
                            }
                            if (propertyNum != 0 || recivedMessage[position + 25] != 'O' || recivedMessage[position + 26] != 'K') {
                                return false;
                            } else {
                                int crc = CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 27]) << 4;
                                crc += CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 28]);
                                int calcCRC = calculateCrc(recivedMessage, position, 27);
                                if (crc != calcCRC) {
                                    fireEvent(EventType.ERROR, "READ PROPERTY FAIL: CRC ERROR!");
                                    return false;
                                } else {
                                    int pos=position+9;
                                    if(command==0x10) {
                                        pos = getAOValue(recivedMessage, pos, analogInChanelN1,sourceEvent);
                                        pos = getAOValue(recivedMessage, pos, analogInChanelN2,sourceEvent);
                                        pos = getAOValue(recivedMessage, pos, analogInChanelN3,sourceEvent);
                                        pos = getAOValue(recivedMessage, pos, analogInChanelN4,sourceEvent);
                                        if(pos!=(position+9+4*4))return false;
                                    } else {
                                        pos = getAOValue(recivedMessage, pos, analogOutChanelN1,sourceEvent);
                                        pos = getAOValue(recivedMessage, pos, analogOutChanelN2,sourceEvent);
                                        pos = getAOValue(recivedMessage, pos, analogOutChanelN3,sourceEvent);
                                        pos = getAOValue(recivedMessage, pos, analogOutChanelN4,sourceEvent);
                                        if(pos!=(position+9+4*4))return false;
                                    }
                                    return true;
                                }
                            }
                        }
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
                                    setHardWareInternalValue((ValueProperty<Float>) property,value,sourceEvent);
                                    return true;
                                }
                            }

                        }
                        case 0x30:
                        case 0x40:{
                            int propertyNum = CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 7]) << 4;
                            propertyNum += CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 8]);
                            if (position + 25 > recivedMessage.length) {
                                fireEvent(EventType.ERROR, "READ ALL PROPERTY FAIL RESPONSE TOO SMALL! ");
                                return false;
                            }
                            if (propertyNum != 0 || recivedMessage[position + 21] != 'O' || recivedMessage[position + 22] != 'K') {
                                return false;
                            } else {
                                int crc = CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 23]) << 4;
                                crc += CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 24]);
                                int calcCRC = calculateCrc(recivedMessage, position, 23);
                                if (crc != calcCRC) {
                                    fireEvent(EventType.ERROR, "READ ALL PROPERTY FAIL: CRC ERROR!");
                                    return false;
                                } else {
                                    int pos=position+9;
                                    if(command==0x10) {
                                        pos = getDOValue(recivedMessage, pos, digitalInChanelN1,sourceEvent);
                                        pos = getDOValue(recivedMessage, pos, digitalInChanelN2,sourceEvent);
                                        pos = getDOValue(recivedMessage, pos, digitalInChanelN3,sourceEvent);
                                        pos = getDOValue(recivedMessage, pos, digitalInChanelN4,sourceEvent);
                                        pos = getDOValue(recivedMessage, pos, digitalInChanelN5,sourceEvent);
                                        pos = getDOValue(recivedMessage, pos, digitalInChanelN6,sourceEvent);
                                        if(pos!=(position+9+2*6))return false;
                                    } else {
                                        pos = getDOValue(recivedMessage, pos, digitalOutChanelN1,sourceEvent);
                                        pos = getDOValue(recivedMessage, pos, digitalOutChanelN2,sourceEvent);
                                        pos = getDOValue(recivedMessage, pos, digitalOutChanelN3,sourceEvent);
                                        pos = getDOValue(recivedMessage, pos, digitalOutChanelN4,sourceEvent);
                                        pos = getDOValue(recivedMessage, pos, digitalOutChanelN5,sourceEvent);
                                        pos = getDOValue(recivedMessage, pos, digitalOutChanelN6,sourceEvent);
                                        if(pos!=(position+9+2*6))return false;
                                    }
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
                                        setHardWareInternalValue((ValueProperty<Boolean>) property,true,sourceEvent);
                                    } else {
                                        setHardWareInternalValue((ValueProperty<Boolean>) property, false,sourceEvent);
                                    }
                                    return true;
                                }
                            }
                        }
                        case 0x22:
                        case 0x42: {
                            if (position + 13 > recivedMessage.length) {
                                fireEvent(EventType.ERROR, "WRITE ALL PROPERTY FAIL RESPONSE TOO SMALL! ");
                                return false;
                            }
                            if (recivedMessage[position + 9] != 'O' || recivedMessage[position + 10] != 'K') {
                                fireEvent(EventType.ERROR, "WRITE ALL PROPERTY FAIL: " + property);
                                return false;
                            } else {
                                int crc = CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 11]) << 4;
                                crc += CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 12]);
                                int calcCRC = calculateCrc(recivedMessage, position, 11);
                                if (crc != calcCRC) {
                                    fireEvent(EventType.ERROR, "WRITE ALL PROPERTY FAIL: CRC ERROR!");
                                    return false;
                                }
                                return true;
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
                                setHardWareInternalValue((ValueProperty<Long>)property,val,sourceEvent);
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
                                setHardWareInternalValue((ValueProperty<Long>)property,val,sourceEvent);
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
                buffer[6]=(property_address%10)==1?(byte)'0':(byte)'2';
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
                SoftShortProperty out=(SoftShortProperty)property;
                long val=out.getValue()==null?0:out.getValue();
                byte[] buffer=new byte[12];
                buffer[0]='V';
                buffer[1]='N';
                buffer[2]='T';
                buffer[3]=CommunicationUtils.getHalfByteHex((deviceaddress & 0xF0) >> 4);
                buffer[4]=CommunicationUtils.getHalfByteHex(deviceaddress&0xF);
                //-----------------------------------------
                buffer[5]='0';
                if(property_address==2){
                    if(val<1) val=1;
                    if(val>4) val=4;
                    buffer[6]='3';
                } else {
                    if(val<0)val=0;
                    if(val>255)val=255;
                    buffer[6]='1';
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

    private int setDOValue(byte[] buffer,int pos,SoftBoolProperty property){
        if(property==null)return pos;
        boolean value=property.getValue()==null?false:property.getValue();
        buffer[pos++]='0';
        buffer[pos++]=(byte)(value?'1':'0');
        return pos;
    }

    private int setAOValue(byte[] buffer,int pos,SoftFloatProperty property){
        if(property==null)return pos;
        float value=property.getValue()==null?0:property.getValue();
        int val=(int)((value/10.0f)*0x3FF);

        if(value>10)val=0x3FF;
        if(val<0)val=0;
        buffer[pos++]=CommunicationUtils.getHalfByteHex((val>>12)&0xF);
        buffer[pos++]=CommunicationUtils.getHalfByteHex((val>>8)&0xF);
        buffer[pos++]=CommunicationUtils.getHalfByteHex((val>>4)&0xF);
        buffer[pos++]=CommunicationUtils.getHalfByteHex(val&0xF);
        return pos;
    }

    private int getDOValue(byte[] buffer,int pos,SoftBoolProperty property,final Event sourceEvent){
        if(property==null)return pos;
        pos++;
        if (buffer[pos++] == '1') {
            setHardWareInternalValue(property, true,sourceEvent);
        } else {
            setHardWareInternalValue(property, false,sourceEvent);
        }
        return pos;
    }

    private int getAOValue(byte[] recivedMessage,int pos,SoftFloatProperty property,final Event sourceEvent){
        if(property==null)return pos;
        int val = CommunicationUtils.getHalfByteFromChar((char) recivedMessage[pos++]) << 12;
        val += CommunicationUtils.getHalfByteFromChar((char) recivedMessage[pos++]) << 8;
        val += CommunicationUtils.getHalfByteFromChar((char) recivedMessage[pos++]) << 4;
        val += CommunicationUtils.getHalfByteFromChar((char) recivedMessage[pos++]);
        float value = (val*10.0f) / 0x3FF ;
        setHardWareInternalValue(property,value,sourceEvent);
        return pos;
    }

    @Override
    protected RequestInformation processCommandRequest(String commandName) {
        SoftLongProperty devaddress=deviceAddress;
        long deviceaddress=devaddress.getValue();
        switch (commandName){
            case "READ_ALL_DI":{
                //READ AI
                byte[] buffer = new byte[13];
                buffer[0] = 'V';
                buffer[1] = 'N';
                buffer[2] = 'T';
                buffer[3] = CommunicationUtils.getHalfByteHex((deviceaddress & 0xF0) >> 4);
                buffer[4] = CommunicationUtils.getHalfByteHex(deviceaddress & 0xF);
                //-----------------------------------------
                buffer[5] = '3';
                buffer[6] = '0';
                buffer[7] = '0';
                buffer[8] = '0';
                buffer[9] = 'F';
                buffer[10] = 'F';
                //-----------------------------------------
                int sum = 0;
                for (int i = 0; i < buffer.length - 2; i++) {
                    sum += buffer[i];
                }
                buffer[buffer.length - 2] = CommunicationUtils.getHalfByteHex((sum & 0xF0) >> 4);
                buffer[buffer.length - 1] = CommunicationUtils.getHalfByteHex(sum & 0xF);
                return new RequestInformation(buffer, 25);
            }
            case "READ_ALL_AI": {
                //READ AI
                byte[] buffer = new byte[13];
                buffer[0] = 'V';
                buffer[1] = 'N';
                buffer[2] = 'T';
                buffer[3] = CommunicationUtils.getHalfByteHex((deviceaddress & 0xF0) >> 4);
                buffer[4] = CommunicationUtils.getHalfByteHex(deviceaddress & 0xF);
                //-----------------------------------------
                buffer[5] = '1';
                buffer[6] = '0';
                buffer[7] = '0';
                buffer[8] = '0';
                buffer[9] = 'F';
                buffer[10] = 'F';
                //-----------------------------------------
                int sum = 0;
                for (int i = 0; i < buffer.length - 2; i++) {
                    sum += buffer[i];
                }
                buffer[buffer.length - 2] = CommunicationUtils.getHalfByteHex((sum & 0xF0) >> 4);
                buffer[buffer.length - 1] = CommunicationUtils.getHalfByteHex(sum & 0xF);
                return new RequestInformation(buffer, 29);
            }
            case "READ_ALL_DO":{
                //READ AI
                byte[] buffer = new byte[13];
                buffer[0] = 'V';
                buffer[1] = 'N';
                buffer[2] = 'T';
                buffer[3] = CommunicationUtils.getHalfByteHex((deviceaddress & 0xF0) >> 4);
                buffer[4] = CommunicationUtils.getHalfByteHex(deviceaddress & 0xF);
                //-----------------------------------------
                buffer[5] = '4';
                buffer[6] = '0';
                buffer[7] = '0';
                buffer[8] = '0';
                buffer[9] = 'F';
                buffer[10] = 'F';
                //-----------------------------------------
                int sum = 0;
                for (int i = 0; i < buffer.length - 2; i++) {
                    sum += buffer[i];
                }
                buffer[buffer.length - 2] = CommunicationUtils.getHalfByteHex((sum & 0xF0) >> 4);
                buffer[buffer.length - 1] = CommunicationUtils.getHalfByteHex(sum & 0xF);
                return new RequestInformation(buffer, 25);
            }
            case "READ_ALL_AO":{
                //READ AI
                byte[] buffer = new byte[13];
                buffer[0] = 'V';
                buffer[1] = 'N';
                buffer[2] = 'T';
                buffer[3] = CommunicationUtils.getHalfByteHex((deviceaddress & 0xF0) >> 4);
                buffer[4] = CommunicationUtils.getHalfByteHex(deviceaddress & 0xF);
                //-----------------------------------------
                buffer[5] = '2';
                buffer[6] = '0';
                buffer[7] = '0';
                buffer[8] = '0';
                buffer[9] = 'F';
                buffer[10] = 'F';
                //-----------------------------------------
                int sum = 0;
                for (int i = 0; i < buffer.length - 2; i++) {
                    sum += buffer[i];
                }
                buffer[buffer.length - 2] = CommunicationUtils.getHalfByteHex((sum & 0xF0) >> 4);
                buffer[buffer.length - 1] = CommunicationUtils.getHalfByteHex(sum & 0xF);
                return new RequestInformation(buffer, 29);
            }
            case "WRITE_ALL_DO":{
                //WRITE DO
                byte[] buffer = new byte[25];
                buffer[0] = 'V';
                buffer[1] = 'N';
                buffer[2] = 'T';
                buffer[3] = CommunicationUtils.getHalfByteHex((deviceaddress & 0xF0) >> 4);
                buffer[4] = CommunicationUtils.getHalfByteHex(deviceaddress & 0xF);
                //-----------------------------------------
                buffer[5] = '4';
                buffer[6] = '2';
                buffer[7] = '0';
                buffer[8] = '0';
                int index=9;
                index=setDOValue(buffer,index,digitalOutChanelN1);
                index=setDOValue(buffer,index,digitalOutChanelN2);
                index=setDOValue(buffer,index,digitalOutChanelN3);
                index=setDOValue(buffer,index,digitalOutChanelN4);
                index=setDOValue(buffer,index,digitalOutChanelN5);
                index=setDOValue(buffer,index,digitalOutChanelN6);
                //index=9+2*6=21
                buffer[index++] = 'F';
                buffer[index++] = 'F';
                //-----------------------------------------
                int sum = 0;
                for (int i = 0; i < buffer.length - 2; i++) {
                    sum += buffer[i];
                }
                buffer[buffer.length - 2] = CommunicationUtils.getHalfByteHex((sum & 0xF0) >> 4);
                buffer[buffer.length - 1] = CommunicationUtils.getHalfByteHex(sum & 0xF);
                return new RequestInformation(buffer, 13);
            }
            case "WRITE_ALL_AO":{
                //WRITE AI
                byte[] buffer = new byte[29];//25+2+2=29
                buffer[0] = 'V';
                buffer[1] = 'N';
                buffer[2] = 'T';
                buffer[3] = CommunicationUtils.getHalfByteHex((deviceaddress & 0xF0) >> 4);
                buffer[4] = CommunicationUtils.getHalfByteHex(deviceaddress & 0xF);
                //-----------------------------------------
                buffer[5] = '2';
                buffer[6] = '2';
                buffer[7] = '0';
                buffer[8] = '0';
                int index=9;
                index=setAOValue(buffer, index, analogOutChanelN1);
                index=setAOValue(buffer, index, analogOutChanelN2);
                index=setAOValue(buffer, index, analogOutChanelN3);
                index=setAOValue(buffer, index, analogOutChanelN4);
                //index=9+4*4=16+9=25
                buffer[index++] = 'F';
                buffer[index++] = 'F';
                //-----------------------------------------
                int sum = 0;
                for (int i = 0; i < buffer.length - 2; i++) {
                    sum += buffer[i];
                }
                buffer[buffer.length - 2] = CommunicationUtils.getHalfByteHex((sum & 0xF0) >> 4);
                buffer[buffer.length - 1] = CommunicationUtils.getHalfByteHex(sum & 0xF);
                return new RequestInformation(buffer, 13);

            }
            case "REFRESH":{
                lineSelect.readValueRequest();
                readAllAI.activate();
                readAllAO.activate();
                readAllDI.activate();
                readAllDO.activate();
                setInternalValue(refreshCommand,false);
                return null;
            }
        }
        return super.processCommandRequest(commandName);
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

    @Override
    public String getDeviceType() {
        return "Favorit Ventil Device";
    }

    public DeviceNamedCommandProperty getReadAllAI() {
        return readAllAI;
    }

    public DeviceNamedCommandProperty getReadAllAO() {
        return readAllAO;
    }

    public DeviceNamedCommandProperty getReadAllDI() {
        return readAllDI;
    }

    public DeviceNamedCommandProperty getReadAllDO() {
        return readAllDO;
    }

    public DeviceNamedCommandProperty getWriteAllAO() {
        return writeAllAO;
    }

    public DeviceNamedCommandProperty getWriteAllDO() {
        return writeAllDO;
    }
}

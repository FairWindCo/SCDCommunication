package ua.pp.fairwind.communications.devices.favorit;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.devices.DeviceInterface;
import ua.pp.fairwind.communications.devices.RequestInformation;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractDevice;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftBoolProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;
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
                        case 0x31:
                            int propertyNum=CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 7])<<4;
                            propertyNum+=CommunicationUtils.getHalfByteFromChar((char)recivedMessage[position+8]);
                            if((property_address%10)!=propertyNum || recivedMessage[position+11]!='O' || recivedMessage[position+12]!='K'){
                                return false;
                            } else {
                                int crc=CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 13])<<4;
                                crc+=CommunicationUtils.getHalfByteFromChar((char)recivedMessage[position+14]);
                                int calcCRC=calculateCrc(recivedMessage,position,13);
                                if(crc!=calcCRC){
                                    return false;
                                } else {
                                    /*
                                    int value=CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 9])<<4;

                                    value+=CommunicationUtils.getHalfByteFromChar((char)recivedMessage[position+10]);
                                     */
                                    if(recivedMessage[position+10]=='1'){
                                        ((ValueProperty<Boolean>)property).setInternalValue(true);
                                    } else {
                                        ((ValueProperty<Boolean>)property).setInternalValue(false);
                                    }
                                    return true;
                                }
                            }
                        case 0x41:
                            propertyNum=CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 7])<<4;
                            propertyNum+=CommunicationUtils.getHalfByteFromChar((char)recivedMessage[position+8]);
                            if((property_address%10)!=propertyNum || recivedMessage[position+11]!='O' || recivedMessage[position+12]!='K'){
                                return false;
                            } else {
                                int crc=CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 13])<<4;
                                crc+=CommunicationUtils.getHalfByteFromChar((char)recivedMessage[position+14]);
                                int calcCRC=calculateCrc(recivedMessage,position,13);
                                if(crc!=calcCRC){
                                    return false;
                                } else {
                                    /*
                                    int value=CommunicationUtils.getHalfByteFromChar((char) recivedMessage[position + 9])<<4;

                                    value+=CommunicationUtils.getHalfByteFromChar((char)recivedMessage[position+10]);
                                     */
                                    if(recivedMessage[position+10]=='1'){
                                        ((ValueProperty<Boolean>)property).setInternalValue(true);
                                    } else {
                                        ((ValueProperty<Boolean>)property).setInternalValue(false);
                                    }
                                    return true;
                                }
                            }
                        default:
                            return false;
                    }
                } else  return false;
            } else {
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
                return new RequestInformation(buffer,13);
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
}

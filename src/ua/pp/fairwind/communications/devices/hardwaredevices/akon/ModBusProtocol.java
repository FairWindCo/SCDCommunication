package ua.pp.fairwind.communications.devices.hardwaredevices.akon;


import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractValuePropertyExecutor;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.software.*;
import ua.pp.fairwind.communications.utils.CommunicationUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by Сергей on 11.09.2015.
 */
public class ModBusProtocol extends AbstractValuePropertyExecutor {
    static final int READ_PROPERTY=0x03;
    static final int WRITE_PROPERTY=0x06;
    static final int WRITE_PROPERTYS=0x10;

    public static byte[] formReadRequestModBus(ValueProperty property,int device_addess) throws IllegalArgumentException{
        Object modbusproperty_address = property.getAdditionalInfo(AkonBase.PROPERTY_MODBUS);
        if(modbusproperty_address==null&&!(modbusproperty_address instanceof Number)){
            throw new IllegalArgumentException("NO PROPERTY_NUM IN PROPERTY");
        }
        int mobus_addr=((Number)modbusproperty_address).intValue();
        byte[] buffer=new byte[8];
        buffer[0]=(byte)(device_addess&0xFF);
        buffer[1]=READ_PROPERTY;
        buffer[2]=(byte)((mobus_addr>>8)&0xFF);
        buffer[3]=(byte)((mobus_addr   )&0xFF);
        buffer[4]=0;
        buffer[5]=2;
        int crc=(CommunicationUtils.calculate_crc16_IBM(buffer, 0, 6) & 0xFFFF);
        buffer[6]=(byte)((crc>>8)&0xFF);
        buffer[7]=(byte)((crc   )&0xFF);
        return buffer;
    }

    public static boolean processResponse(byte[] recivedBuffer,ValueProperty property,int device_addess,Event modificator){
        if(recivedBuffer==null||recivedBuffer.length<8)return false;

        for(int i=0;i<recivedBuffer.length-7;i++){
            if(recivedBuffer[i]==(device_addess&0xFF)){
                byte mfunction=recivedBuffer[i+1];
                if(mfunction==READ_PROPERTY){
                    int numbytes=(recivedBuffer[i+2])&0xFF;
                    if(numbytes!=4)continue;
                    if(i+numbytes>recivedBuffer.length-2) continue;
                    int ccrc=(CommunicationUtils.calculate_crc16_IBM(recivedBuffer, i, 3+numbytes) & 0xFFFF);
                    int crc=((recivedBuffer[i+3+numbytes]&0xFF)<<8)|(recivedBuffer[i+4+numbytes]&0xFF);
                    if(crc==ccrc){
                        long value=0;
                        value|=((recivedBuffer[i+3]&0xFF)<<24);
                        value|=((recivedBuffer[i+4]&0xFF)<<16);
                        value|=((recivedBuffer[i+5]&0xFF)<<8);
                        value|=((recivedBuffer[i+6]&0xFF));
                        setValueFromTransfer(property,value,modificator);
                        return true;
                    }
                } else if(mfunction==WRITE_PROPERTYS){
                    int ccrc=(CommunicationUtils.calculate_crc16_IBM(recivedBuffer, i, 6) & 0xFFFF);
                    int crc=((recivedBuffer[i+6]&0xFF)<<9)|(recivedBuffer[i+7]&0xFF);
                    if(crc==ccrc){
                        Object modbusproperty_address = property.getAdditionalInfo(AkonBase.PROPERTY_MODBUS);
                        if(modbusproperty_address==null&&!(modbusproperty_address instanceof Number)){
                            throw new IllegalArgumentException("NO PROPERTY_NUM IN PROPERTY");
                        }
                        int mobus_addr=((Number)modbusproperty_address).intValue();
                        int property_addr=((recivedBuffer[i+2]&0xFF)<<8);
                        property_addr|=((recivedBuffer[i+3]&0xFF));
                        int property_count=((recivedBuffer[i+4]&0xFF)<<8);
                        property_count|=((recivedBuffer[i+5]&0xFF));
                        if(property_count==2 && mobus_addr==property_addr){
                            return true;
                        }
                    }
                } else {
                    continue;
                }

            }
        }
        return false;
    }

    public static byte[] formWriteRequestModBus(ValueProperty property,int device_addess) throws IllegalArgumentException{
        Object modbusproperty_address = property.getAdditionalInfo(AkonBase.PROPERTY_MODBUS);
        if(modbusproperty_address==null&&!(modbusproperty_address instanceof Number)){
            throw new IllegalArgumentException("NO PROPERTY_NUM IN PROPERTY");
        }
        int mobus_addr=((Number)modbusproperty_address).intValue();
        long value=getValueForTransfer(property);
        byte[] buffer=new byte[13];
        buffer[0]=(byte)(device_addess&0xFF);
        buffer[1]=WRITE_PROPERTYS;
        buffer[2]=(byte)((mobus_addr>>8)&0xFF);
        buffer[3]=(byte)((mobus_addr   )&0xFF);
        buffer[4]=0;
        buffer[5]=2;
        buffer[6]=4;
        buffer[7]=(byte)((value>>24)&0xFF);
        buffer[8]=(byte)((value>>16)&0xFF);
        buffer[9]=(byte)((value>>8 )&0xFF);
        buffer[10]=(byte)((value    )&0xFF);
        int crc=(CommunicationUtils.calculate_crc16_IBM(buffer, 0, 11) & 0xFFFF);
        buffer[11]=(byte)((crc>>8)&0xFF);
        buffer[12]=(byte)((crc   )&0xFF);
        return buffer;
    }

    static public long getValueForTransfer(ValueProperty property){
        Object value=property.getValue();
        if(property==null||value==null)return 0;
        if(value instanceof Number){
            return ((Number) value).longValue();
        } else if(value instanceof Boolean){
            return ((Boolean)value)?1:0;
        } else if(value instanceof Character){
            return ((Character)value).charValue();
        } else if(value instanceof String){
            try{
                return new Long((String)value);
            } catch (NumberFormatException e){
                return 0;
            }
        }
        return 0;
    }

    static public void setValueFromTransfer(ValueProperty property,long value,Event modificator){
        if(property==null)return;
        if(property instanceof SoftBoolProperty){
            if(value==0){setInternalValue(property, false,modificator);
            } else {
                setInternalValue(property, true,modificator);
            }
        } else if(property instanceof SoftLongProperty){
            setInternalValue(property, value,modificator);
        } else if(property instanceof SoftShortProperty){
            setInternalValue(property, (short) value,modificator);
        } else if(property instanceof SoftIntegerProperty){
            setInternalValue(property, (int) value,modificator);
        } else if(property instanceof SoftByteProperty){
            setInternalValue(property, (byte) value,modificator);
        } else if(property instanceof SoftCharProperty){
            setInternalValue(property, (char) value,modificator);
        } else if(property instanceof SoftFloatProperty){
            setInternalValue(property, Float.intBitsToFloat((int) value),modificator);
        } else if(property instanceof SoftDoubleProperty){
            setInternalValue(property, Double.longBitsToDouble(value),modificator);
        } else if(property instanceof SoftDateTimeProperty){
            setInternalValue(property, new Date(value),modificator);
        } else if(property instanceof SoftBigDecimalProperty){
            setInternalValue(property, BigDecimal.valueOf(value),modificator);
        } else if(property instanceof SoftBigIntegerProperty){
            setInternalValue(property, BigInteger.valueOf(value),modificator);
        }
    }
}

package ua.pp.fairwind.communications.devices.hardwaredevices.akon;


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
public class AkonProtocol extends AbstractValuePropertyExecutor {
    static final int READ_PROPERTY=0;
    static final int WRITE_PROPERTY=1;

    public static byte[] formReadRequestObjectNet(ValueProperty property,int device_addess) throws IllegalArgumentException{
        Object objval =property.getAdditionalInfo(AkonBase.OBJECT_NUM);
        Object propval=property.getAdditionalInfo(AkonBase.PROPERTY_NUM);
        if(propval==null&&!(propval instanceof Number)){
            throw new IllegalArgumentException("NO PROPERTY_NUM IN PROPERTY");
        }
        if(objval==null&&!(objval instanceof Number)){
            throw new IllegalArgumentException("NO OBJECT_NUM IN PROPERTY");
        }
        int obj=((Number)objval).intValue();
        int prp=((Number)propval).intValue();

        byte[] buffer=new byte[11];
        buffer[0]=(byte)(device_addess&0xFF);
        buffer[1]=READ_PROPERTY;
        buffer[2]=(byte)(obj&0xFF);
        buffer[3]=(byte)((prp>>8)&0xFF);
        buffer[4]=(byte)((prp   )&0xFF);
        buffer[5]=0;
        buffer[6]=0;
        buffer[7]=0;
        buffer[8]=0;
        int crc=(CommunicationUtils.calculate_crc16_IBM(buffer, 0, 9) & 0xFFFF);
        buffer[9 ]=(byte)((crc>>8)&0xFF);
        buffer[10]=(byte)((crc   )&0xFF);
        return buffer;
    }

    public static boolean processResponse(byte[] recivedBuffer,ValueProperty property,int device_addess){
        if(recivedBuffer==null||recivedBuffer.length<11)return false;
        for(int i=0;i<recivedBuffer.length-10;i++){
            if(recivedBuffer[i]==(device_addess&0xFF)&& (recivedBuffer[i+1]==0||recivedBuffer[i+1]==1)){
                int ccrc=(CommunicationUtils.calculate_crc16_IBM(recivedBuffer, i, 9) & 0xFFFF);
                int crc=((recivedBuffer[i+9]&0xFF)<<8)|(recivedBuffer[i+10]&0xFF);
                if(crc==ccrc){
                    Object objval =property.getAdditionalInfo(AkonBase.OBJECT_NUM);
                    Object propval=property.getAdditionalInfo(AkonBase.PROPERTY_NUM);
                    if(propval==null&&!(propval instanceof Number)){
                        throw new IllegalArgumentException("NO PROPERTY_NUM IN PROPERTY");
                    }
                    if(objval==null&&!(objval instanceof Number)){
                        throw new IllegalArgumentException("NO OBJECT_NUM IN PROPERTY");
                    }
                    int obj=((Number)objval).intValue();
                    int prp=((Number)propval).intValue();
                    int prp_num=((recivedBuffer[i+3]&0xFF)<<8)|(recivedBuffer[i+4]&0xFF);
                    if(recivedBuffer[i+2]==obj && prp==prp_num){
                        if(recivedBuffer[i+1]==0){
                            long value=0;
                            value|=((recivedBuffer[i+5]&0xFF)<<24);
                            value|=((recivedBuffer[i+6]&0xFF)<<16);
                            value|=((recivedBuffer[i+7]&0xFF)<<8);
                            value|=((recivedBuffer[i+8]&0xFF));
                            setValueFromTransfer(property,value);
                            return true;
                        } else {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static byte[] formWriteRequestObjectNet(ValueProperty property,int device_addess) throws IllegalArgumentException{
        Object objval =property.getAdditionalInfo(AkonBase.OBJECT_NUM);
        Object propval=property.getAdditionalInfo(AkonBase.PROPERTY_NUM);
        if(propval==null&&!(propval instanceof Number)){
            throw new IllegalArgumentException("NO PROPERTY_NUM IN PROPERTY");
        }
        if(objval==null&&!(objval instanceof Number)){
            throw new IllegalArgumentException("NO OBJECT_NUM IN PROPERTY");
        }
        int obj=((Number)objval).intValue();
        int prp=((Number)propval).intValue();
        long value=getValueForTransfer(property);
        byte[] buffer=new byte[11];
        buffer[0]=(byte)(device_addess&0xFF);
        buffer[1]=WRITE_PROPERTY;
        buffer[2]=(byte)(obj&0xFF);
        buffer[3]=(byte)((prp>>8)&0xFF);
        buffer[4]=(byte)((prp   )&0xFF);
        buffer[5]=(byte)((value>>24)&0xFF);
        buffer[6]=(byte)((value>>16)&0xFF);
        buffer[7]=(byte)((value>>8 )&0xFF);
        buffer[8]=(byte)((value    )&0xFF);
        int crc=(CommunicationUtils.calculate_crc16_IBM(buffer, 0, 9) & 0xFFFF);
        buffer[9 ]=(byte)((crc>>8)&0xFF);
        buffer[10]=(byte)((crc   )&0xFF);
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

    static public void setValueFromTransfer(ValueProperty property,long value){
        if(property==null)return;
        if(property instanceof SoftBoolProperty){
            if(value==0){setHardWareInternalValue(property,false);
            } else {
                setHardWareInternalValue(property,true);
            }
        } else if(property instanceof SoftLongProperty){
            setHardWareInternalValue(property, value);
        } else if(property instanceof SoftShortProperty){
            setHardWareInternalValue(property, (short) value);
        } else if(property instanceof SoftIntegerProperty){
            setHardWareInternalValue(property, (int) value);
        } else if(property instanceof SoftByteProperty){
            setHardWareInternalValue(property, (byte) value);
        } else if(property instanceof SoftCharProperty){
            setHardWareInternalValue(property, (char) value);
        } else if(property instanceof SoftFloatProperty){
            setHardWareInternalValue(property, Float.intBitsToFloat((int)value));
        } else if(property instanceof SoftDoubleProperty){
            setHardWareInternalValue(property,Double.longBitsToDouble(value));
        } else if(property instanceof SoftDateTimeProperty){
            setHardWareInternalValue(property,new Date(value));
        } else if(property instanceof SoftBigDecimalProperty){
            setHardWareInternalValue(property,BigDecimal.valueOf(value));
        } else if(property instanceof SoftBigIntegerProperty){
            setHardWareInternalValue(property,BigInteger.valueOf(value));
        }
    }
}

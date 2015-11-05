package ua.pp.fairwind.communications.devices.hardwaredevices.positron;


import ua.pp.fairwind.communications.devices.RequestInformation;
import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractValuePropertyExecutor;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.groups.GroupProperty;
import ua.pp.fairwind.communications.propertyes.software.*;
import ua.pp.fairwind.communications.utils.CommunicationUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Сергей on 11.09.2015.
 */
public class ModBusProtocol extends AbstractValuePropertyExecutor {
    public static final String MODBUS_ADDRESS="MODBUS_ADDRESS";
    public static final String MODBUS_READ_FUNCTION="MODBUS_READ_FUNCTION";
    public static final String MODBUS_WRITE_FUNCTION="MODBUS_WRITE_FUNCTION";
    public static final String MODBUS_BYTE_SIZE="MODBUS_BYTE_SIZE";

    static final int READ_COIL_STATUS = 0x01;
    static final int READ_DESCRITE_INPUT = 0x02;
    static final int READ_HOLDING_REGISTER = 0x03;
    static final int READ_INPUT_REGISTER = 0x04;

    static final int WRITE_SINGLE_COIL = 0x05;
    static final int WRITE_SINGLE_REGISTER = 0x06;
    static final int WRITE_MULTI_COIL = 0x0F;
    static final int WRITE_MULTI_REGISTER = 0x10;

    static public class ModBusProtocolRequestInformation{
        final byte[] request;
        final int responseLengthInByte;

        public ModBusProtocolRequestInformation(byte[] request, int responseLengthInByte) {
            this.request = request;
            this.responseLengthInByte = responseLengthInByte;
        }

        public RequestInformation getRequest(boolean needRollBack){
            return new RequestInformation(request,responseLengthInByte,needRollBack);
        }
    }

    public static ModBusProtocolRequestInformation formReadRequestModBus(int function,int register_address,int size, int device_addess) throws IllegalArgumentException {
        if(function!=READ_COIL_STATUS && function!=READ_DESCRITE_INPUT && function!=READ_HOLDING_REGISTER && function!=READ_INPUT_REGISTER || size<=0){
            throw new IllegalArgumentException();
        }
        int response=3+size+2;
        if(function==READ_HOLDING_REGISTER || function==READ_INPUT_REGISTER){
            size=size/2+(size%2>0?1:0);
        } else {
            size=size/8+(size%8>0?1:0);
            response=3+size+2;
        }
        byte[] buffer = new byte[8];
        buffer[0] = (byte) (device_addess & 0xFF);
        buffer[1] = (byte) (function&0xF);
        buffer[2] = (byte) ((register_address >> 8) & 0xFF);
        buffer[3] = (byte) ((register_address) & 0xFF);
        buffer[4] = (byte) ((size >> 8) & 0xFF);
        buffer[5] = (byte) ((size) & 0xFF);
        int crc = (CommunicationUtils.calculate_crc16_IBM(buffer, 0, 6) & 0xFFFF);
        buffer[6] = (byte) ((crc >> 8) & 0xFF);
        buffer[7] = (byte) ((crc) & 0xFF);
        return new ModBusProtocolRequestInformation(buffer,response);
    }

    public static ModBusProtocolRequestInformation formReadRequestModBus(AbstractProperty property, int device_addess) throws IllegalArgumentException {
        Object modbus_address = property.getAdditionalInfo(MODBUS_ADDRESS);
        Object modbusfunction = property.getAdditionalInfo(MODBUS_READ_FUNCTION);
        if (modbus_address == null || !(modbus_address instanceof Number)) {
            throw new IllegalArgumentException("NO PROPERTY_NUM IN PROPERTY");
        }
        if (modbusfunction == null || !(modbusfunction instanceof Number)) {
            throw new IllegalArgumentException("NO PROPERTY_NUM IN PROPERTY");
        }
        Object size = property.getAdditionalInfo(MODBUS_BYTE_SIZE);
        if (size == null && !(size instanceof Number)) {
            throw new IllegalArgumentException("NO PROPERTY_NUM IN PROPERTY");
        }
        int mobus_addr = ((Number) modbus_address).intValue();
        int mobus_func = ((Number) modbusfunction).intValue();
        int mobus_size = ((Number) size).intValue();
        return formReadRequestModBus(mobus_func, mobus_addr, mobus_size, device_addess);
    }

    public static ModBusProtocolRequestInformation formReadRequestModBus(AbstractProperty property,int size, int device_addess) throws IllegalArgumentException {
        Object modbus_address = property.getAdditionalInfo(MODBUS_ADDRESS);
        Object modbusfunction = property.getAdditionalInfo(MODBUS_READ_FUNCTION);
        if (modbus_address == null || !(modbus_address instanceof Number)) {
            throw new IllegalArgumentException("NO PROPERTY_NUM IN PROPERTY");
        }
        if (modbusfunction == null || !(modbusfunction instanceof Number)) {
            throw new IllegalArgumentException("NO PROPERTY_NUM IN PROPERTY");
        }
        int mobus_addr = ((Number) modbus_address).intValue();
        int mobus_func = ((Number) modbusfunction).intValue();
        return formReadRequestModBus(mobus_func, mobus_addr, size, device_addess);
    }

    public static boolean processResponse(byte[] recivedBuffer, ValueProperty property, int device_addess, Event modificator) {
        Object sze = property.getAdditionalInfo(MODBUS_BYTE_SIZE);
        if (sze == null || !(sze instanceof Number)) {
            return false;
        }
        int subsize=((Number)sze).intValue();
        return processResponse(recivedBuffer, property, device_addess, subsize, modificator);
    }


    public static boolean processResponse(byte[] recivedBuffer, ValueProperty property, int device_addess,int size, Event modificator) {
        if (recivedBuffer == null || recivedBuffer.length < 8) return false;

        for (int i = 0; i < recivedBuffer.length - 7; i++) {
            if (recivedBuffer[i] == (device_addess & 0xFF)) {
                byte mfunction = recivedBuffer[i + 1];
                switch (mfunction){
                    case READ_COIL_STATUS:{

                    }break;
                    case READ_DESCRITE_INPUT:{

                    }break;
                    case READ_HOLDING_REGISTER:
                    case READ_INPUT_REGISTER:{
                        int numbytes = (recivedBuffer[i + 2]) & 0xFF;
                        if (numbytes <=0) continue;
                        if (i + numbytes > recivedBuffer.length - 2) continue;
                        int ccrc = (CommunicationUtils.calculate_crc16_IBM(recivedBuffer, i, 3 + numbytes) & 0xFFFF);
                        int crc = ((recivedBuffer[i + 3 + numbytes] & 0xFF) << 8) | (recivedBuffer[i + 4 + numbytes] & 0xFF);
                        if (crc == ccrc) {
                            byte[] value=Arrays.copyOfRange(recivedBuffer, i + 3, numbytes);
                            setValueFromTransfer(property, value,0,size, modificator);
                            return true;
                        }
                    }break;
                    case WRITE_SINGLE_COIL:
                    case WRITE_SINGLE_REGISTER:
                    case WRITE_MULTI_COIL:
                    case WRITE_MULTI_REGISTER:
                        int ccrc = (CommunicationUtils.calculate_crc16_IBM(recivedBuffer, i, 6) & 0xFFFF);
                        int crc = ((recivedBuffer[i + 6] & 0xFF) << 9) | (recivedBuffer[i + 7] & 0xFF);
                        if (crc == ccrc) {
                            Object modbusproperty_address = property.getAdditionalInfo(MODBUS_ADDRESS);
                            if (modbusproperty_address == null && !(modbusproperty_address instanceof Number)) {
                                throw new IllegalArgumentException("NO MODBUS_ADDRESS IN PROPERTY");
                            }
                            int mobus_addr = ((Number) modbusproperty_address).intValue();
                            int property_addr = ((recivedBuffer[i + 2] & 0xFF) << 8);
                            property_addr |= ((recivedBuffer[i + 3] & 0xFF));
                            int property_count = ((recivedBuffer[i + 4] & 0xFF) << 8);
                            property_count |= ((recivedBuffer[i + 5] & 0xFF));
                            if(mfunction==WRITE_MULTI_COIL || mfunction==WRITE_MULTI_REGISTER){
                                if(property_count==0){
                                    return false;
                                }
                                if(mfunction==WRITE_MULTI_REGISTER && property_count<(size/2)){
                                    return false;
                                }
                            }
                            if (mobus_addr == property_addr) {
                                return true;
                            }
                        }
                    break;
                }
            }
        }
        return false;
    }

    public static ModBusProtocolRequestInformation formWriteRequestModBus(int function,int register_address, int device_addess,byte... value) throws IllegalArgumentException {
        if(function!=WRITE_MULTI_COIL && function!=WRITE_MULTI_REGISTER && function!=WRITE_SINGLE_COIL && function!=WRITE_SINGLE_REGISTER || value==null || value.length==0){
            throw new IllegalArgumentException();
        }
        int response=3+4+2;
        if(function==WRITE_SINGLE_COIL || function==WRITE_SINGLE_REGISTER){
            byte[] buffer = new byte[8];
            buffer[0] = (byte) (device_addess & 0xFF);
            buffer[1] = (byte) (function&0xF);
            buffer[2] = (byte) ((register_address >> 8) & 0xFF);
            buffer[3] = (byte) ((register_address) & 0xFF);
            buffer[4] = value.length>1?value[value.length-2]:0;
            buffer[5] = value[value.length-1];
            int crc = (CommunicationUtils.calculate_crc16_IBM(buffer, 0, 6) & 0xFFFF);
            buffer[6] = (byte) ((crc >> 8) & 0xFF);
            buffer[7] = (byte) ((crc) & 0xFF);
            return new ModBusProtocolRequestInformation(buffer,response);
        } else {
            int size=value.length;
            byte[] buffer = new byte[7+size];
            buffer[0] = (byte) (device_addess & 0xFF);
            buffer[1] = (byte) (function&0xF);
            buffer[2] = (byte) ((register_address >> 8) & 0xFF);
            buffer[3] = (byte) ((register_address) & 0xFF);
            buffer[4] = (byte) (size & 0xFF);
            for(int i=0;i<size;i++){
                buffer[5+i]=value[size-i-1];
            }
            int crc = (CommunicationUtils.calculate_crc16_IBM(buffer, 0, 5+size) & 0xFFFF);
            buffer[5+size] = (byte) ((crc >> 8) & 0xFF);
            buffer[6+size] = (byte) ((crc) & 0xFF);
            return new ModBusProtocolRequestInformation(buffer,response);
        }
    }

    public static ModBusProtocolRequestInformation formWriteRequestModBus(AbstractProperty property, int device_addess) throws IllegalArgumentException {
        Object modbus_address = property.getAdditionalInfo(MODBUS_ADDRESS);
        Object modbusfunction = property.getAdditionalInfo(MODBUS_READ_FUNCTION);
        if (modbus_address == null && !(modbus_address instanceof Number)) {
            throw new IllegalArgumentException("NO PROPERTY_NUM IN PROPERTY");
        }
        if (modbusfunction == null && !(modbusfunction instanceof Number)) {
            throw new IllegalArgumentException("NO PROPERTY_NUM IN PROPERTY");
        }
        int mobus_addr = ((Number) modbus_address).intValue();
        int mobus_func = ((Number) modbusfunction).intValue();
        byte[] buffer_value=getValueForTransfer(property);
        return formWriteRequestModBus(mobus_func, mobus_addr, device_addess, buffer_value);
    }

    public static ModBusProtocolRequestInformation formWriteRequestModBus(AbstractProperty property,int size, int device_addess) throws IllegalArgumentException {
        Object modbus_address = property.getAdditionalInfo(MODBUS_ADDRESS);
        Object modbusfunction = property.getAdditionalInfo(MODBUS_READ_FUNCTION);
        if (modbus_address == null && !(modbus_address instanceof Number)) {
            throw new IllegalArgumentException("NO PROPERTY_NUM IN PROPERTY");
        }
        if (modbusfunction == null && !(modbusfunction instanceof Number)) {
            throw new IllegalArgumentException("NO PROPERTY_NUM IN PROPERTY");
        }
        int mobus_addr = ((Number) modbus_address).intValue();
        int mobus_func = ((Number) modbusfunction).intValue();
        byte[] buffer_value=getValueForTransfer(property,size);
        return formWriteRequestModBus(mobus_func, mobus_addr, device_addess, buffer_value);
    }



    static public Long getValueForTransfer(ValueProperty property) {
        Object value = property.getValue();
        if (property == null || value == null) return null;
        if (value instanceof Number) {
            return ((Number) value).longValue();
        } else if (value instanceof Boolean) {
            return ((Boolean) value) ? 1L : 0L;
        } else if (value instanceof Date) {
            return ((Date) value).getTime();
        } else if (value instanceof Character) {
            return (long)((Character) value).charValue();
        } else if (value instanceof String) {
            try {
                return new Long((String) value);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    static public byte[] getValueForTransfer(AbstractProperty property) {
        Object sze = property.getAdditionalInfo(MODBUS_BYTE_SIZE);
        if (sze == null || !(sze instanceof Number)) {
            return null;
        }
        int subsize=((Number)sze).intValue();
        return getValueForTransfer(property,subsize);
    }

    static public byte[] getValueForTransfer(AbstractProperty property,int size) {
        if(property!=null){
            if(property instanceof ValueProperty){
                return getValueForTransfer((ValueProperty)property,size);
            } else if(property instanceof GroupProperty){
                GroupProperty group=(GroupProperty)property;
                int count=group.propertyCount();
                if(count>0){
                    byte[] result=new byte[size];
                    int pos=0;
                    for(int i=0;i<count;i++) {
                        Object sze = property.getAdditionalInfo(MODBUS_BYTE_SIZE);
                        if (sze == null && !(sze instanceof Number)) {
                            continue;
                        }
                        int subsize=((Number)sze).intValue();
                        if(subsize>0&&pos+subsize<size) {
                            byte[] tmp=getValueForTransfer(group.getPopertyByIndex(i),subsize);
                            if(tmp!=null){
                                System.arraycopy(tmp,0,result,pos,subsize);
                                pos+=subsize;
                            }
                        }
                    }
                    return result;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    static public byte[] getValueForTransfer(ValueProperty property,int size) {
        if(size<1)return null;
        Object value = property.getValue();
        if(value==null)return null;
        if(value instanceof String){
            byte[] temp=((String) value).getBytes();
            return Arrays.copyOf(temp,size);
        } else if(value instanceof BigInteger){
            byte[] temp=((BigInteger) value).toByteArray();
            return Arrays.copyOf(temp,size);
        } else if(value instanceof BigDecimal){
            byte[] temp=((BigDecimal) value).toBigInteger().toByteArray();
            return Arrays.copyOf(temp,size);
        } else {
            Long lvalue=getValueForTransfer(property);
            if(lvalue==null)return null;
            long val=lvalue;
            byte[] temp=new byte[8];
            temp[0]=(byte)(val&0xFF);
            temp[1]=(byte)((val>>8)&0xFF);
            temp[2]=(byte)((val>>16)&0xFF);
            temp[3]=(byte)((val>>24)&0xFF);
            temp[4]=(byte)((val>>32)&0xFF);
            temp[5]=(byte)((val>>40)&0xFF);
            temp[6]=(byte)((val>>48)&0xFF);
            temp[7]=(byte)((val>>56)&0xFF);
            return Arrays.copyOf(temp,size);
        }
    }

    private static Long getTemporryValue(byte[] value,int pos,int size){
        if(value==null || value.length==0 || size<=0 || pos+size>=value.length){
            return null;
        } else {
            long val=0;
            size=size>8?8:size;
            int mask=0;
            for(int i=pos;i<pos+size;i++){
                val|=value[pos]<<mask;
                mask++;
            }
            return val;
        }
    }

    static public int setValueFromTransfer(AbstractProperty property, byte[] value,int pos,int size, Event modificator) {
        if(property instanceof ValueProperty){
            return setValueFromTransfer((ValueProperty)property,value,pos,size,modificator);
        } else {
           if(property instanceof GroupProperty){
               GroupProperty group=(GroupProperty)property;
               int count=group.propertyCount();
               if(count>0){
                   for(int i=0;i<count;i++) {
                       Object sze = property.getAdditionalInfo(MODBUS_BYTE_SIZE);
                       if (sze == null && !(sze instanceof Number)) {
                           continue;
                       }
                       int subsize=((Number)sze).intValue();
                       if(subsize>0) {
                           pos = setValueFromTransfer(group.getPopertyByIndex(i), value, pos, subsize, modificator);
                       }
                   }
               }
           }
        }
        return pos;
    }
    static public int setValueFromTransfer(ValueProperty property, byte[] value,int pos,int size, Event modificator) {
        if (property == null||value==null||value.length==0) return pos;
        if (property instanceof SoftBoolProperty) {
            if (value[0] == 0) {
                setInternalValue(property, false, modificator);
            } else {
                setInternalValue(property, true, modificator);
            }
        } else if (property instanceof SoftLongProperty) {
            Long val=getTemporryValue(value,pos,size);
            if(val==null)return pos;
            setInternalValue(property, val, modificator);
        } else if (property instanceof SoftShortProperty) {
            Long val=getTemporryValue(value,pos,size);
            if(val==null)return pos;
            setInternalValue(property, val.shortValue(), modificator);
        } else if (property instanceof SoftIntegerProperty) {
            Long val=getTemporryValue(value,pos,size);
            if(val==null)return pos;
            setInternalValue(property, val.intValue(), modificator);
        } else if (property instanceof SoftByteProperty) {
            setInternalValue(property, value[0], modificator);
        } else if (property instanceof SoftCharProperty) {
            setInternalValue(property, (char) value[0], modificator);
        } else if (property instanceof SoftFloatProperty) {
            Long val=getTemporryValue(value,pos,size);
            if(val==null)return pos;
            setInternalValue(property, val.floatValue(), modificator);
        } else if (property instanceof SoftDoubleProperty) {
            Long val=getTemporryValue(value,pos,size);
            if(val==null)return pos;
            setInternalValue(property,val.doubleValue(), modificator);
        } else if (property instanceof SoftDateTimeProperty) {
            Long val=getTemporryValue(value,pos,size);
            if(val==null)return pos;
            setInternalValue(property, new Date(val.longValue()), modificator);
        } else if (property instanceof SoftBigDecimalProperty) {
            if(value==null || value.length==0 || size<=0 || pos+size>=value.length){
                return pos;
            }
            setInternalValue(property, new BigDecimal(new BigInteger(Arrays.copyOfRange(value,pos,size))), modificator);
        } else if (property instanceof SoftBigIntegerProperty) {
            if(value==null || value.length==0 || size<=0 || pos+size>=value.length){
                return pos;
            }
            setInternalValue(property, new BigInteger(Arrays.copyOfRange(value,pos,size)), modificator);
        } else if(property instanceof SoftStringProperty){
            if(value==null || value.length==0 || size<=0 || pos+size>=value.length){
                return pos;
            }
            setInternalValue(property,new String(Arrays.copyOfRange(value,pos,size)),modificator);
        }
        return pos+size;
    }
}

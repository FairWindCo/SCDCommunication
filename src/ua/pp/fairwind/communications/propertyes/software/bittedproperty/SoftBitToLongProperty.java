package ua.pp.fairwind.communications.propertyes.software.bittedproperty;

import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractGroupedProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.markers.LongValueInterface;
import ua.pp.fairwind.communications.propertyes.software.SoftBoolProperty;

import java.util.BitSet;

/**
 * Created by Сергей on 09.11.2015.
 */
public class SoftBitToLongProperty extends AbstractGroupedProperty<Long, Boolean> implements LongValueInterface {
    private final short bitCount;
    private final short propertyCount;
    final private  BitSet setBits;


    public SoftBitToLongProperty(String name, SOFT_OPERATION_TYPE softOperationType, byte bits, ValueProperty<Boolean>... properties) {
        super(name, null, softOperationType);
        if(bits>64)bits=64;
        bitCount=bits;
        setBits=new BitSet(bits);
        if(properties==null)throw new IllegalArgumentException();
        propertyCount=(short)properties.length;
        addPropertyies(properties);
    }

    public SoftBitToLongProperty(String name, SOFT_OPERATION_TYPE softOperationType) {
        super(name, null, softOperationType);
        setBits=new BitSet(64);
        bitCount=64;
        ValueProperty<Boolean>[] properties=new ValueProperty[32];
        for(int i=0;i<64;i++){
            properties[i]=new SoftBoolProperty("bit_"+i,softOperationType);
        }
        propertyCount=(short)properties.length;
        addPropertyies(properties);
    }

    public SoftBitToLongProperty(String name, SOFT_OPERATION_TYPE softOperationType, ValueProperty<Boolean>... properties) {
        super(name, null, softOperationType);
        int bits=properties.length;
        if(bits>64)bits=64;
        bitCount=(short)bits;
        setBits=new BitSet(bitCount);
        if(properties==null)throw new IllegalArgumentException();
        propertyCount=(short)properties.length;
        addPropertyies(properties);
    }

    @Override
    protected Boolean formInternalValue(Long integer, ValueProperty<Boolean> internalProperty) {
        int index=getBitIndex(internalProperty);
        if(index>=0){
            Boolean bol=internalProperty.getValue();
            return setBits.get(index);
        } else {
            return false;
        }
    }

    private void formBitSet(Long integer){
        if(integer!=null) {
            byte[] arr=new byte[8];
            arr[0]=(byte)((integer)&0xFF);
            arr[1]=(byte)((integer>>8)&0xFF);
            arr[2]=(byte)((integer>>16)&0xFF);
            arr[3]=(byte)((integer>>24)&0xFF);
            arr[4]=(byte)((integer>>32)&0xFF);
            arr[5]=(byte)((integer>>40)&0xFF);
            arr[6]=(byte)((integer>>48)&0xFF);
            arr[7]=(byte)((integer>>56)&0xFF);
            setBits.clear();
            setBits.or(BitSet.valueOf(arr));
        }
    }

    private int getBitIndex(ValueProperty<Boolean> internalProperty){
        Object o=internalProperty.getAdditionalInfo(SoftBoolProperty.BIT_NUMBER);
        if(o!=null&&o instanceof Number){
            int index=((Number) o).intValue();
            if(index>setBits.length())return -1;
            return index;
        } else {
            return -1;
        }
    }

    @Override
    protected void setSilentValue(Long value) {
        formBitSet(value);
        super.setSilentValue(value);
    }

    @Override
    protected void setInternalValue(Long integer, Event parent) {
        formBitSet(integer);
        super.setInternalValue(integer, parent);
    }

    @Override
    protected Long formExternalValue(Boolean aBoolean, Long bigvalue, ValueProperty<Boolean> internalProperty) {
        int index=getBitIndex(internalProperty);
        if(index>=0){
            Boolean bol=internalProperty.getValue();
            setBits.set(index,bol!=null?bol:false);
        }
        byte[] bytes=setBits.toByteArray();
        if(bytes==null||bytes.length<1)return null;
        int mask=0;
        long value=0;
        for(int i=0;i<8&i<bytes.length;i++){
            value|=bytes[i]<<mask;
            mask+=8;
        }
        return value;
    }

    @Override
    public SoftBitToLongProperty setAdditionalInfo(String paramsName, Object value) {
        super.setAdditionalInfo(paramsName, value);
        return this;
    }
}

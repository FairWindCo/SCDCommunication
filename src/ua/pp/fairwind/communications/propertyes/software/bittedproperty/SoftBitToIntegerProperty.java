package ua.pp.fairwind.communications.propertyes.software.bittedproperty;

import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractGroupedProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftBoolProperty;

import java.util.BitSet;

/**
 * Created by Сергей on 09.11.2015.
 */
public class SoftBitToIntegerProperty extends AbstractGroupedProperty<Integer, Boolean> {
    private final short bitCount;
    private final short propertyCount;
    final private  BitSet setBits;


    public SoftBitToIntegerProperty(String name, SOFT_OPERATION_TYPE softOperationType,byte bits,ValueProperty<Boolean>... properties) {
        super(name, null, softOperationType);
        if(bits>32)bits=32;
        bitCount=bits;
        setBits=new BitSet(bits);
        if(properties==null)throw new IllegalArgumentException();
        propertyCount=(short)properties.length;
        addPropertyies(properties);
    }

    public SoftBitToIntegerProperty(String name, SOFT_OPERATION_TYPE softOperationType,ValueProperty<Boolean>... properties) {
        super(name, null, softOperationType);
        int bits=properties.length;
        if(bits>32)bits=32;
        bitCount=(short)bits;
        setBits=new BitSet(bitCount);
        if(properties==null)throw new IllegalArgumentException();
        propertyCount=(short)properties.length;
        addPropertyies(properties);
    }

    @Override
    protected Boolean formInternalValue(Integer integer, ValueProperty<Boolean> internalProperty) {
        int index=getBitIndex(internalProperty);
        if(index>=0){
            Boolean bol=internalProperty.getValue();
            return setBits.get(index);
        } else {
            return false;
        }
    }

    private void formBitSet(Integer integer){
        if(integer!=null) {
            byte[] arr=new byte[4];
            arr[0]=(byte)((integer)&0xFF);
            arr[1]=(byte)((integer>>8)&0xFF);
            arr[2]=(byte)((integer>>16)&0xFF);
            arr[3]=(byte)((integer>>24)&0xFF);
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
    protected void setSilentValue(Integer value) {
        formBitSet(value);
        super.setSilentValue(value);
    }

    @Override
    protected void setInternalValue(Integer integer, Event parent) {
        formBitSet(integer);
        super.setInternalValue(integer, parent);
    }

    @Override
    protected Integer formExternalValue(Boolean aBoolean, Integer bigvalue, ValueProperty<Boolean> internalProperty) {
        int index=getBitIndex(internalProperty);
        if(index>=0){
            Boolean bol=internalProperty.getValue();
            setBits.set(index,bol!=null?bol:false);
        }
        byte[] bytes=setBits.toByteArray();
        if(bytes==null||bytes.length<1)return null;
        int mask=0;
        long value=0;
        for(int i=0;i<4&i<bytes.length;i++){
            value|=bytes[i]<<mask;
            mask+=8;
        }
        return (int)(value&0xFFFFFFFF);
    }


}

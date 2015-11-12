package ua.pp.fairwind.communications.propertyes.software.bittedproperty;

import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractGroupedProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.markers.ShortValueInterface;
import ua.pp.fairwind.communications.propertyes.software.SoftBoolProperty;

import java.util.BitSet;

/**
 * Created by Сергей on 09.11.2015.
 */
public class SoftBitToShortProperty extends AbstractGroupedProperty<Short, Boolean> implements ShortValueInterface {
    private final short bitCount;
    private final short propertyCount;
    final private  BitSet setBits;


    public SoftBitToShortProperty(String name, SOFT_OPERATION_TYPE softOperationType, byte bits, ValueProperty<Boolean>... properties) {
        super(name, null, softOperationType);
        if(bits>16)bits=16;
        bitCount=bits;
        setBits=new BitSet(bits);
        if(properties==null)throw new IllegalArgumentException();
        propertyCount=(short)properties.length;
        addPropertyies(properties);
    }

    public SoftBitToShortProperty(String name, SOFT_OPERATION_TYPE softOperationType) {
        super(name, null, softOperationType);
        setBits=new BitSet(16);
        bitCount=16;
        ValueProperty<Boolean>[] properties=new ValueProperty[32];
        for(int i=0;i<16;i++){
            properties[i]=new SoftBoolProperty("bit_"+i,softOperationType);
        }
        propertyCount=(short)properties.length;
        addPropertyies(properties);
    }

    public SoftBitToShortProperty(String name, SOFT_OPERATION_TYPE softOperationType, ValueProperty<Boolean>... properties) {
        super(name, null, softOperationType);
        int bits=properties.length;
        if(bits>16)bits=16;
        bitCount=(short)bits;
        setBits=new BitSet(bitCount);
        if(properties==null)throw new IllegalArgumentException();
        propertyCount=(short)properties.length;
        addPropertyies(properties);
    }

    @Override
    protected Boolean formInternalValue(Short integer, ValueProperty<Boolean> internalProperty) {
        int index=getBitIndex(internalProperty);
        if(index>=0){
            Boolean bol=internalProperty.getValue();
            return setBits.get(index);
        } else {
            return false;
        }
    }

    private void formBitSet(Short integer){
        if(integer!=null) {
            byte[] arr=new byte[2];
            arr[0]=(byte)((integer)&0xFF);
            arr[1]=(byte)((integer>>8)&0xFF);
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
    protected void setSilentValue(Short value) {
        formBitSet(value);
        super.setSilentValue(value);
    }

    @Override
    protected void setInternalValue(Short integer, Event parent) {
        formBitSet(integer);
        super.setInternalValue(integer, parent);
    }

    @Override
    protected Short formExternalValue(Boolean aBoolean, Short bigvalue, ValueProperty<Boolean> internalProperty) {
        int index=getBitIndex(internalProperty);
        if(index>=0){
            Boolean bol=internalProperty.getValue();
            setBits.set(index,bol!=null?bol:false);
        }
        byte[] bytes=setBits.toByteArray();
        if(bytes==null||bytes.length<1)return null;
        int mask=0;
        long value=0;
        for(int i=0;i<2&i<bytes.length;i++){
            value|=bytes[i]<<mask;
            mask+=8;
        }
        return (short)(value&0xFFFF);
    }

    @Override
    public SoftBitToShortProperty setAdditionalInfo(String paramsName, Object value) {
        super.setAdditionalInfo(paramsName, value);
        return this;
    }
}

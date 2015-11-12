package ua.pp.fairwind.communications.propertyes.software.bittedproperty;

import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractGroupedProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.markers.ByteValueInterface;
import ua.pp.fairwind.communications.propertyes.software.SoftBoolProperty;

import java.util.BitSet;

/**
 * Created by Сергей on 09.11.2015.
 */
public class SoftBitToByteProperty extends AbstractGroupedProperty<Byte, Boolean> implements ByteValueInterface {
    private final short bitCount;
    private final short propertyCount;
    final private  BitSet setBits;


    public SoftBitToByteProperty(String name, SOFT_OPERATION_TYPE softOperationType, byte bits, ValueProperty<Boolean>... properties) {
        super(name, null, softOperationType);
        if(bits>8)bits=8;
        bitCount=bits;
        setBits=new BitSet(bits);
        if(properties==null)throw new IllegalArgumentException();
        propertyCount=(short)properties.length;
        addPropertyies(properties);
    }

    public SoftBitToByteProperty(String name, SOFT_OPERATION_TYPE softOperationType) {
        super(name, null, softOperationType);
        setBits=new BitSet(8);
        bitCount=8;
        ValueProperty<Boolean>[] properties=new ValueProperty[32];
        for(int i=0;i<8;i++){
            properties[i]=new SoftBoolProperty("bit_"+i,softOperationType);
        }
        propertyCount=(short)properties.length;
        addPropertyies(properties);
    }

    public SoftBitToByteProperty(String name, SOFT_OPERATION_TYPE softOperationType, ValueProperty<Boolean>... properties) {
        super(name, null, softOperationType);
        int bits=properties.length;
        if(bits>8)bits=8;
        bitCount=(short)bits;
        setBits=new BitSet(bitCount);
        if(properties==null)throw new IllegalArgumentException();
        propertyCount=(short)properties.length;
        addPropertyies(properties);
    }

    @Override
    protected Boolean formInternalValue(Byte integer, ValueProperty<Boolean> internalProperty) {
        int index=getBitIndex(internalProperty);
        if(index>=0){
            Boolean bol=internalProperty.getValue();
            return setBits.get(index);
        } else {
            return false;
        }
    }

    private void formBitSet(Byte integer){
        if(integer!=null) {
            byte[] arr=new byte[1];
            arr[0]=(byte)((integer)&0xFF);
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
    protected void setSilentValue(Byte value) {
        formBitSet(value);
        super.setSilentValue(value);
    }

    @Override
    protected void setInternalValue(Byte integer, Event parent) {
        formBitSet(integer);
        super.setInternalValue(integer, parent);
    }

    @Override
    protected Byte formExternalValue(Boolean aBoolean, Byte bigvalue, ValueProperty<Boolean> internalProperty) {
        int index=getBitIndex(internalProperty);
        if(index>=0){
            Boolean bol=internalProperty.getValue();
            setBits.set(index,bol!=null?bol:false);
        }
        byte[] bytes=setBits.toByteArray();
        if(bytes==null||bytes.length<1)return null;
        long value=bytes[0];
        return (byte)(value&0xFF);
    }

    @Override
    public SoftBitToByteProperty setAdditionalInfo(String paramsName, Object value) {
        super.setAdditionalInfo(paramsName, value);
        return this;
    }
}

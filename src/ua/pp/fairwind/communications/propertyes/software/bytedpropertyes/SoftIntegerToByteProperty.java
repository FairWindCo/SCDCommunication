package ua.pp.fairwind.communications.propertyes.software.bytedpropertyes;

import ua.pp.fairwind.communications.propertyes.abstraction.AbstractGroupedProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftByteProperty;

/**
 * Created by Сергей on 21.09.2015.
 */
public class SoftIntegerToByteProperty extends AbstractGroupedProperty<Integer,Byte> {
    final private SoftByteProperty byte1;
    final private SoftByteProperty byte2;
    final private SoftByteProperty byte3;
    final private SoftByteProperty byte4;

    public SoftIntegerToByteProperty(String name, String uuid, SOFT_OPERATION_TYPE softOperationType, SoftByteProperty byte1, SoftByteProperty byte2, SoftByteProperty byte3, SoftByteProperty byte4) {
        super(name, uuid, softOperationType);
        this.byte1 = byte1;
        this.byte2 = byte2;
        this.byte3 = byte3;
        this.byte4 = byte4;
        addPropertyies(byte1,byte2,byte3,byte4);
    }

    public SoftIntegerToByteProperty(String name, String uuid,  SOFT_OPERATION_TYPE softOperationType, Integer integer, SoftByteProperty byte1, SoftByteProperty byte2, SoftByteProperty byte3, SoftByteProperty byte4) {
        super(name, uuid, softOperationType, integer);
        this.byte1 = byte1;
        this.byte2 = byte2;
        this.byte3 = byte3;
        this.byte4 = byte4;
        addPropertyies(byte1,byte2,byte3,byte4);
    }

    public SoftIntegerToByteProperty(String name, String uuid,  Integer integer, SoftByteProperty byte1, SoftByteProperty byte2, SoftByteProperty byte3, SoftByteProperty byte4) {
        super(name, uuid, integer);
        this.byte1 = byte1;
        this.byte2 = byte2;
        this.byte3 = byte3;
        this.byte4 = byte4;
        addPropertyies(byte1,byte2,byte3,byte4);
    }

    public SoftIntegerToByteProperty(String name, String uuid,  SoftByteProperty byte1, SoftByteProperty byte2, SoftByteProperty byte3, SoftByteProperty byte4) {
        super(name, uuid);
        this.byte1 = byte1;
        this.byte2 = byte2;
        this.byte3 = byte3;
        this.byte4 = byte4;
        addPropertyies(byte1,byte2,byte3,byte4);
    }

    public SoftIntegerToByteProperty(String name, String uuid,  SOFT_OPERATION_TYPE softOperationType) {
        super(name, uuid, softOperationType);
        byte1=new SoftByteProperty("byte1");
        byte2=new SoftByteProperty("byte2");
        byte3=new SoftByteProperty("byte3");
        byte4=new SoftByteProperty("byte4");
        addPropertyies(byte1,byte2,byte3,byte4);
    }

    public SoftIntegerToByteProperty(String name, String uuid,  SOFT_OPERATION_TYPE softOperationType, Integer integer) {
        super(name, uuid, softOperationType, integer);
        byte1=new SoftByteProperty("byte1");
        byte2=new SoftByteProperty("byte2");
        byte3=new SoftByteProperty("byte3");
        byte4=new SoftByteProperty("byte4");
        addPropertyies(byte1,byte2,byte3,byte4);
    }

    public SoftIntegerToByteProperty(String name, String uuid) {
        super(name, uuid);
        byte1=new SoftByteProperty("byte1");
        byte2=new SoftByteProperty("byte2");
        byte3=new SoftByteProperty("byte3");
        byte4=new SoftByteProperty("byte4");
        addPropertyies(byte1,byte2,byte3,byte4);
    }

    public SoftIntegerToByteProperty(String name, String uuid,  Integer integer) {
        super(name, uuid, integer);
        byte1=new SoftByteProperty("byte1");
        byte2=new SoftByteProperty("byte2");
        byte3=new SoftByteProperty("byte3");
        byte4=new SoftByteProperty("byte4");
        addPropertyies(byte1,byte2,byte3,byte4);
    }


    protected  Byte formInternalValue(Integer value, ValueProperty<Byte> internalProperty){
        int val=value==null?0:value;
        if(internalProperty==byte1)return (byte)((val    )&0xFF);
        if(internalProperty==byte2)return (byte)((val>>8 )&0xFF);
        if(internalProperty==byte3)return (byte)((val>>16)&0xFF);
        if(internalProperty==byte4)return (byte)((val>>24)&0xFF);
        return null;
    }
    protected Integer   formExternalValue(Byte value,Integer bigvalue,ValueProperty<Byte> internalProperty){
        int big=bigvalue==null?0:bigvalue;
        int val=value==null?0:value;
        if(internalProperty==byte1)return ((big    )&0xFFFFFF00)| (val&0xFF);
        if(internalProperty==byte2)return ((big    )&0xFFFF00FF)|((val&0xFF)<<8 );
        if(internalProperty==byte3)return ((big    )&0xFF00FFFF)|((val&0xFF)<<16);
        if(internalProperty==byte4)return ((big    )&0x00FFFFFF)|((val&0xFF)<<24);
        return bigvalue;
    }

    public SoftByteProperty getByte1() {
        return byte1;
    }

    public SoftByteProperty getByte2() {
        return byte2;
    }

    public SoftByteProperty getByte3() {
        return byte3;
    }

    public SoftByteProperty getByte4() {
        return byte4;
    }

    @Override
    public int propertyCount() {
        return 4;
    }
}

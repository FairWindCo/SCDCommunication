package ua.pp.fairwind.communications.propertyes.binding;

import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftByteProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;

/**
 * Created by Сергей on 11.09.2015.
 */
public class ByteToLongConvertor extends PropertyValueBindingElement<Byte,Long> {

    private ByteToLongConvertor(ValueProperty<Byte> readingProperty, ValueProperty<Long> writingProperty, ValueConvertor<Byte, Long> readConvertor, ValueConvertor<Long, Byte> writeConvertor, MessageSubSystem centralSystem, int byteNumber) {
        super(readingProperty, writingProperty, readConvertor, writeConvertor, centralSystem);
        int byteNumber1 = byteNumber;

    }

    static public ByteToLongConvertor createByteToLongConvertor(SoftByteProperty from,SoftLongProperty to,int byteNumber, MessageSubSystem centralSystem){
        final ValueConvertor<Byte, Long> readConvertor=(FROM,OLD_TO)->{
            if(FROM==null) return null;
            if(OLD_TO==null)OLD_TO=0L;
            return longFromBytePos(FROM, OLD_TO, byteNumber);
        };
        final ValueConvertor<Long, Byte> writeConvertor=(FROM,OLD_TO)->{
            if(FROM==null) return null;
            byte val=(byte)((((long)FROM)>>byteNumber)&0xFF);
            return val;
        };
        return new ByteToLongConvertor(from,to,readConvertor,writeConvertor,centralSystem,byteNumber);
    }

    public static long longFromBytePos(byte newValue,long oldValue,int pos){
        long mask=0xFFFFFFFFFFFFFFL;
        long submask=0xFFFFFFFFFFFFFFFFL;
        if(pos==0){
            mask=0xFFFFFFFFFFFFFF00L;
        } else if(pos>=56){
            mask=0xFFFFFFFFFFFFL;
        } else {
            mask=(mask<<(pos+9));
            submask=(submask>>>(64-pos));
            mask|=submask;
        }

        long val=(newValue&0xFF)<<pos;
        //System.out.printf("MASK: %h SAUMASK: %h\n", mask,submask);
        return (oldValue&mask)|val;
    }
}

package ua.pp.fairwind.communications.propertyes.binding;

import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftByteProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftIntegerProperty;

/**
 * Created by Сергей on 11.09.2015.
 */
public class ByteToIntegerConvertor extends PropertyValueBindingElement<Byte,Integer> {

    private ByteToIntegerConvertor(ValueProperty<Byte> readingProperty, ValueProperty<Integer> writingProperty, ValueConvertor<Byte, Integer> readConvertor, ValueConvertor<Integer, Byte> writeConvertor, int byteNumber) {
        super(readingProperty, writingProperty, readConvertor, writeConvertor);
        int byteNumber1 = byteNumber;

    }

    static public ByteToIntegerConvertor createByteToIntegerConvertor(SoftByteProperty from,SoftIntegerProperty to,int byteNumber){
        final ValueConvertor<Byte, Integer> readConvertor=(FROM,OLD_TO)->{
            if(FROM==null) return null;
            if(OLD_TO==null)OLD_TO=0;
            return intFromBytePos(FROM,OLD_TO,byteNumber);
        };
        final ValueConvertor<Integer, Byte> writeConvertor=(FROM,OLD_TO)->{
            if(FROM==null) return null;
            if(OLD_TO==null)OLD_TO=0;
            byte val=(byte)((FROM >>byteNumber)&0xFF);
            return val;
        };
        return new ByteToIntegerConvertor(from,to,readConvertor,writeConvertor,byteNumber);
    }

    public static int intFromBytePos(byte newValue,int oldValue,int pos){
        int mask=0xFFFFFFFF;
        int submask=0xFFFFFFFF;
        if(pos==0){
            mask=0xFFFFFF00;
        } else if(pos>=24){
            mask=0xFFFFFF;
        } else {
            mask=(mask<<(pos+9));
            submask=(submask>>>(32-pos));
            mask|=submask;
        }

        int val=(newValue&0xFF)<<pos;
        //System.out.printf("MASK: %h SAUMASK: %h\n", mask,submask);
        return (oldValue&mask)|val;
    }
}

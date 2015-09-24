package ua.pp.fairwind.communications.test.propertyTest;

import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystemMultiDipatch;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.binding.ByteToIntegerConvertor;
import ua.pp.fairwind.communications.propertyes.software.SoftByteProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftIntegerProperty;
import ua.pp.fairwind.communications.utils.CommunicationUtils;

/**
 * Created by Сергей on 11.09.2015.
 */
public class BindingTest {
    public static void main(String[] args) {
        SoftIntegerProperty i1=new SoftIntegerProperty("test int",ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        SoftByteProperty b1=new SoftByteProperty("test byte");
        i1.addChangeEventListener((event) -> System.out.println(event.getNameFrom() + " = " + event.getNewValue()));
        b1.addChangeEventListener((event) -> System.out.println(event.getNameFrom() + " = " + event.getNewValue()));

        ByteToIntegerConvertor.createByteToIntegerConvertor(b1, i1, 0).bind();

        b1.setValue((byte) 100);

        CommunicationUtils.RealThreadPause(1000);

        i1.setValue(1231241);

        int aa=0x34534;
        byte bb=0x3E;
        int val=intFromBytePos(bb,aa,0);
        System.out.printf("RESULT: %h %h \n",aa,val);
        val=intFromBytePos(bb,aa,8);
        System.out.printf("RESULT: %h %h \n",aa,val);
        val=intFromBytePos(bb,aa,16);
        System.out.printf("RESULT: %h %h \n",aa,val);
        val=intFromBytePos(bb,aa,24);
        System.out.printf("RESULT: %h %h \n",aa,val);
        val=intFromBytePos(bb,aa,3);
        System.out.printf("RESULT: %h %h \n",aa,val);
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

        int val=newValue<<pos;
        System.out.printf("MASK: %h SAUMASK: %h\n", mask,submask);
        return (oldValue&mask)|val;
    }
}

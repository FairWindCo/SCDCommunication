package ua.pp.fairwind.communications.test.propertyTest;

import ua.pp.fairwind.communications.messagesystems.event.ElementEventListener;
import ua.pp.fairwind.communications.propertyes.software.bytedpropertyes.SoftIntegerToByteProperty;

/**
 * Created by Сергей on 21.09.2015.
 */
public class ToByteTest {
    public static void main(String[] args) {
        SoftIntegerToByteProperty itb = new SoftIntegerToByteProperty("test", (String)null);
        itb.addEventListener(ElementEventListener.println);
        itb.getByte1().addEventListener(ElementEventListener.println);
        itb.getByte2().addEventListener(ElementEventListener.println);
        itb.getByte3().addEventListener(ElementEventListener.println);
        itb.getByte4().addEventListener(ElementEventListener.println);

        itb.setValue(65540);
        itb.setBubleEvent(false);

        itb.getByte4().setValue((byte) 0x31);
        itb.getByte3().setValue((byte) 0x23);
        itb.getByte2().setValue((byte) 0x15);
        itb.getByte1().setValue((byte) 0x96);
        if (itb.getValue() == 824382870) {
            System.out.println("SUCCESS!");
        } else {
            System.out.println("ERROR:" + itb.getValue());
        }

    }
}

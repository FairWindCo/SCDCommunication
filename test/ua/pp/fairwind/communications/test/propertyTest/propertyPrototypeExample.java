package ua.pp.fairwind.communications.test.propertyTest;

import ua.pp.fairwind.communications.messagesystems.event.ValueChangeEvent;
import ua.pp.fairwind.communications.messagesystems.event.ValueChangeListener;
import ua.pp.fairwind.communications.propertyes.software.SoftFloatProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftIntegerProperty;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * Created by ������ on 01.07.2015.
 */
public class propertyPrototypeExample {

    public static void main(String[] args) {
        SoftIntegerProperty p1=new SoftIntegerProperty("test 1");
        SoftIntegerProperty p2=new SoftIntegerProperty("bind test 1");
        SoftIntegerProperty p3=new SoftIntegerProperty("bind test 2");
        SoftFloatProperty f1=new SoftFloatProperty("bind float 1");
        ValueChangeListener<Number> listener=new ValueChangeListener<Number>() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                System.out.println("EVENT FROM: "+event.getNameFrom()+" OLD VALUE="+event.getOldValue()+" NEW VALUE="+event.getNewValue()+ " UUID="+event.getUuidFrom());
            }
        };
        p1.addChangeEventListener(listener);
        p2.addChangeEventListener(listener);
        p3.addChangeEventListener(listener);
        f1.addChangeEventListener(listener);
        p2.bindReadProperty(p1);
        p2.bindWriteProperty(p3);
        p3.bindWriteProperty(p2);
        f1.bindReadNumberProperty(p1);
        f1.bindWriteNumberProperty(p2);
        p1.setValue(10);
        p1.setValue(20);
        p1.setValue(30);
        DecimalFormat fm=new DecimalFormat("delta ##.###");
        String str=fm.format(53.45);
        System.out.println(str);
        try {
            System.out.println(fm.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /**/

    }
}

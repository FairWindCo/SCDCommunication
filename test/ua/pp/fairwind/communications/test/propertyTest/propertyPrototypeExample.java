package ua.pp.fairwind.communications.test.propertyTest;

import ua.pp.fairwind.communications.propertyes.event.ValueChangeEvent;
import ua.pp.fairwind.communications.propertyes.event.ValueChangeListener;
import ua.pp.fairwind.communications.propertyes.software.SoftFloatProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftIntegerProperty;

/**
 * Created by Сергей on 01.07.2015.
 */
public class propertyPrototypeExample {

    public static void main(String[] args) {
        SoftIntegerProperty p1=new SoftIntegerProperty("test 1",null);
        SoftIntegerProperty p2=new SoftIntegerProperty("bind test 1",null);
        SoftIntegerProperty p3=new SoftIntegerProperty("bind test 2",null);
        SoftFloatProperty f1=new SoftFloatProperty("bind float 1",null);
        ValueChangeListener<Number> listener=new ValueChangeListener<Number>() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                System.out.println("EVENT FROM: "+event.getNameFrom()+" OLD VALUE="+event.getOldValue()+" NEW VALUE="+event.getNewValue()+ " UUID="+event.getUuidFrom());
            }
        };
        p1.addChangeEventListener(listener);
        p2.addChangeEventListener(listener);
        p3.addChangeEventListener(listener);
        p2.bindReadProperty(p1);
        p2.bindWriteProperty(p3);
        p3.bindWriteProperty(p2);
        p1.setValue(10);
        p1.setValue(20);
        p1.setValue(30);
        f1.bindReadNumberProperty(p1);
        f1.bindWriteNumberProperty(p2);
        Number n=new Integer(5);
        Float m=n.floatValue();

    }
}

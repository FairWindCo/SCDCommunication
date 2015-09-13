package ua.pp.fairwind.communications.test.propertyTest;

import ua.pp.fairwind.communications.propertyes.abstraction.ValuedPropertyConvertor;
import ua.pp.fairwind.communications.propertyes.binding.NumberPropertyBindingelement;
import ua.pp.fairwind.communications.propertyes.binding.ValueConvertor;
import ua.pp.fairwind.communications.propertyes.event.ValueChangeEvent;
import ua.pp.fairwind.communications.propertyes.event.ValueChangeListener;
import ua.pp.fairwind.communications.propertyes.software.SoftFloatProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftIntegerProperty;

/**
 * Created by ������ on 01.07.2015.
 */
public class propertyBindingPrototypeExample {

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
        new NumberPropertyBindingelement<>(p1,p2,(fromValue1,oldValue) -> ValuedPropertyConvertor.convertIntegerFromNumber(fromValue1),ValueConvertor::convertIntegerFromNumber).bind();
        new NumberPropertyBindingelement<>(p2,p3,(fromValue1,oldValue) -> ValuedPropertyConvertor.convertIntegerFromNumber(fromValue1),ValueConvertor::convertIntegerFromNumber).bind();
        new NumberPropertyBindingelement<>(p3,f1,(fromValue1,oldValue) -> ValuedPropertyConvertor.convertFloatFromNumber(fromValue1),ValueConvertor::convertIntegerFromNumber).bindWrite();

        p1.setValue(10);
        p1.setValue(20);
        p1.setValue(30);
        f1.setValue(3.14f);
    }
}

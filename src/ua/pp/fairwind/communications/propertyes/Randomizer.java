package ua.pp.fairwind.communications.propertyes;

import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractValuePropertyExecutor;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.groups.GroupPropertyInterface;
import ua.pp.fairwind.communications.propertyes.software.*;

import java.util.Date;
import java.util.Random;

/**
 * Created by Сергей on 01.10.2015.
 */
public class Randomizer extends AbstractValuePropertyExecutor{
    private static final Random rnd=new Random();
    public static void randomizeProperty(AbstractProperty property){
        if(property instanceof ValueProperty){
            if(property instanceof SoftByteProperty){
                byte val= (byte)rnd.nextInt();
                AbstractValuePropertyExecutor.setInternalValue((ValueProperty)property,val,null);
            } else if(property instanceof SoftShortProperty){
                short val= (short)rnd.nextInt();
                AbstractValuePropertyExecutor.setInternalValue((ValueProperty)property,val,null);
            } else if(property instanceof SoftIntegerProperty){
                int val= rnd.nextInt();
                AbstractValuePropertyExecutor.setInternalValue((ValueProperty)property,val,null);
            } else if(property instanceof SoftLongProperty){
                long val= rnd.nextLong();
                AbstractValuePropertyExecutor.setInternalValue((ValueProperty)property,val,null);
            } else if(property instanceof SoftBigIntegerProperty){
                long val= rnd.nextLong();
                AbstractValuePropertyExecutor.setInternalValue((ValueProperty)property,val,null);
            } else if(property instanceof SoftFloatProperty){
                float val= rnd.nextFloat();
                AbstractValuePropertyExecutor.setInternalValue((ValueProperty)property,val,null);
            } else if(property instanceof SoftDoubleProperty){
                double val= rnd.nextDouble();
                AbstractValuePropertyExecutor.setInternalValue((ValueProperty)property,val,null);
            } else if(property instanceof SoftBigDecimalProperty){
                double val= rnd.nextDouble();
                AbstractValuePropertyExecutor.setInternalValue((ValueProperty)property,val,null);
            } else if(property instanceof SoftDateTimeProperty){
                long val= rnd.nextLong();
                Date date=new Date(val);
                AbstractValuePropertyExecutor.setInternalValue((ValueProperty)property,date,null);
            } else if(property instanceof SoftCharProperty){
                char val= (char)rnd.nextInt();
                AbstractValuePropertyExecutor.setInternalValue((ValueProperty)property,val,null);
            } else if(property instanceof SoftStringProperty){
                String val= getRandomString();
                AbstractValuePropertyExecutor.setInternalValue((ValueProperty)property,val,null);
            } else if(property instanceof SoftByteArrayProperty){

            }
        }
        if(property instanceof GroupPropertyInterface){
            ((GroupPropertyInterface) property).getStream().forEach(prop->randomizeProperty(prop));
        }
    }

    public static String getRandomString(){
        int count=Math.abs(rnd.nextInt());
        if(count==0)return "";
        return rnd.ints(count).map(element->(byte)element).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();

    }

}

package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.propertyes.binding.BindingConvertExceptions;
import ua.pp.fairwind.communications.propertyes.binding.PropertyConvertor;
import ua.pp.fairwind.communications.propertyes.binding.ValueConvertor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Сергей on 16.07.2015.
 */
public class ValuedPropertyConvertor<FROM extends Comparable<? super FROM>,TO extends Comparable<? super TO>> implements PropertyConvertor<ValueProperty<FROM>,ValueProperty<TO>> {
    private final ValueConvertor<FROM,TO> convertor;

    public ValuedPropertyConvertor(ValueConvertor<FROM, TO> convertor) {
        this.convertor = convertor;
    }

    @Override
    public boolean convertValue(ValueProperty<FROM> fromValue, ValueProperty<TO> toValue) throws BindingConvertExceptions {
        if(convertor==null) return false;
        toValue.setValue(convertor.convertValue(fromValue.getValue(),toValue.getValue()));
        return true;
    }

    public static String convertNumberToString(Number value,String format){
        if(value==null) return null;
        if(format==null) return value.toString();
        DecimalFormat fm=new DecimalFormat(format);
        return fm.format(value);
    }

    public static String convertNumberToString(Number value){
        if(value==null) return null;
        return value.toString();
    }

    public static String convertNumberToString(Number value,int radix){
        if(value==null) return null;
        String newval=null;
        if (value instanceof Integer) {
            newval = Integer.toString((Integer) value, radix);
        } else if (value instanceof Short) {
            newval = Integer.toString(((Short) value).shortValue(), radix);
        } else if (value instanceof Long) {
            newval = Long.toString((Long) value, radix);
        } else if (value instanceof Byte) {
            newval = Integer.toString(((Byte) value).byteValue(), radix);
        } else if (value instanceof BigInteger) {
            newval = ((BigInteger) value).toString(radix);
        } else if (value instanceof BigDecimal) {
            newval = value.toString();
        } else if (value instanceof Float) {
            newval = value.toString();
        } else if (value instanceof Double) {
            newval = value.toString();
        }
        return newval;
    }

    public static String converBooleanToString(Boolean value){
        if(value==null) return null;
        return value.toString();
    }

    public static String converBooleanToStringBIN(Boolean value){
        if(value==null) return null;
        if(((Boolean) value).booleanValue()){
            return "1";
        } else {
            return "0";
        }
    }


    public static String converDateToString(Date value){
        if(value==null) return null;
        return value.toString();
    }

    public static String converDateToString(Date value,String format){
        if(value==null) return null;
        if(format!=null) {
            DateFormat fm = new SimpleDateFormat(format);
            return fm.format(value);
        } return null;

    }

    public static Byte convertByteFromNumber(Number value) {
        if(value!=null)return value.byteValue();
        return null;
    }

    public static Short convertShortFromNumber(Number value) {
        if(value!=null)return value.shortValue();
        return null;
    }

    public static Integer convertIntegerFromNumber(Number value) {
        if(value!=null)return value.intValue();
        return null;
    }

    public static Long convertLongFromNumber(Number value) {
        if(value!=null)return value.longValue();
        return null;
    }

    public static Float convertFloatFromNumber(Number value) {
        if(value!=null)return value.floatValue();
        return null;
    }

    public static Double convertDoubleFromNumber(Number value) {
        if(value!=null)return value.doubleValue();
        return null;
    }

    public static BigInteger convertBigIntegerFromNumber(Number value) {
        if(value!=null)return new BigInteger(value.toString());
        return null;
    }

    public static BigDecimal convertBigDecimalFromNumber(Number value) {
        if(value!=null)return new BigDecimal(value.toString());
        return null;
    }
}


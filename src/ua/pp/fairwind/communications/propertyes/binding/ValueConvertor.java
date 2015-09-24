package ua.pp.fairwind.communications.propertyes.binding;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by Сергей on 16.07.2015.
 */
@FunctionalInterface
public interface ValueConvertor<FROM extends Comparable<? super FROM>,TO extends Comparable<? super TO>> {
    TO convertValue(FROM fromValue,TO oldTOValue) throws BindingConvertExceptions;

    static  String convertNumberToString(Number value){
        if(value==null) return null;
        return value.toString();
    }


    static String converBooleanToString(Boolean value,Object oldVal){
        if(value==null) return null;
        return value.toString();
    }

    static String converBooleanToStringBIN(Boolean value,Object oldVal){
        if(value==null) return null;
        if(value.booleanValue()){
            return "1";
        } else {
            return "0";
        }
    }


    static  String converDateToString(Date value,Object oldVal){
        if(value==null) return null;
        return value.toString();
    }

    static  Byte convertByteFromNumber(Number value,Object oldVal) {
        if(value!=null)return value.byteValue();
        return null;
    }

    static  Short convertShortFromNumber(Number value,Object oldVal) {
        if(value!=null)return value.shortValue();
        return null;
    }

    static Integer convertIntegerFromNumber(Number value,Object oldVal) {
        if(value!=null)return value.intValue();
        return null;
    }

    static Long convertLongFromNumber(Number value) {
        if(value!=null)return value.longValue();
        return null;
    }

    static  Float convertFloatFromNumber(Number value) {
        if(value!=null)return value.floatValue();
        return null;
    }

    static  Double convertDoubleFromNumber(Number value) {
        if(value!=null)return value.doubleValue();
        return null;
    }

    static  BigInteger convertBigIntegerFromNumber(Number value) {
        if(value!=null)return new BigInteger(value.toString());
        return null;
    }

    static  BigDecimal convertBigDecimalFromNumber(Number value) {
        if(value!=null)return new BigDecimal(value.toString());
        return null;
    }

}

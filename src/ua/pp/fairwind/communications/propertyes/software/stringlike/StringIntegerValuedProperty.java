package ua.pp.fairwind.communications.propertyes.software.stringlike;

import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;

/**
 * Created by Wind on 28.07.2014.
 */
public class StringIntegerValuedProperty extends StringValuedPropertry {

    public StringIntegerValuedProperty(String name, Integer minValue,Integer maxValue, String defauleValue, String format, String... correctValues) {
        super(name, new IntegerValueConverter(minValue,maxValue), defauleValue, format, correctValues);
        if(minValue==null && maxValue==null){
            setConverter(null);
        }
    }

    public StringIntegerValuedProperty(String name, Integer minValue,Integer maxValue, String format, String... correctValues) {
        this(name, minValue, maxValue, null, format, correctValues);
    }

    public StringIntegerValuedProperty(String name,String description, Integer minValue,Integer maxValue, String defauleValue, String format, String... correctValues) {
        super(name, new IntegerValueConverter(minValue, maxValue), defauleValue, format, correctValues);
        if(minValue==null && maxValue==null){
            setConverter(null);
        }
    }

    public StringIntegerValuedProperty(String name,String description,String defauleValue,  Integer minValue,Integer maxValue, String... correctValues) {
        super(name, new IntegerValueConverter(minValue, maxValue), defauleValue, "%s", correctValues);
        if(minValue==null && maxValue==null){
            setConverter(null);
        }
    }

    public StringIntegerValuedProperty(String name,String description, String defauleValue,  String... correctValues) {
        super(name, null, defauleValue, "%s", correctValues);
    }

    public StringIntegerValuedProperty(String name, String uuid, String description, MessageSubSystem centralSystem, String defauleValue, String format, Integer minValue,Integer maxValue, String... correctValues) {
        super(name, uuid, defauleValue, format, new IntegerValueConverter(minValue, maxValue), correctValues);
        if(minValue==null && maxValue==null){
            setConverter(null);
        }
    }
}

package ua.pp.fairwind.communications.propertyes.software.stringlike;


import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;

/**
 * Created by Wind on 28.07.2014.
 */
public class StringLongValuedProperty extends StringValuedPropertry {

    public StringLongValuedProperty(String name, Long minValue, Long maxValue, String defauleValue, String format, String... correctValues) {
        super(name, new LongValueConverter(minValue,maxValue), defauleValue, format, correctValues);
        if(minValue==null && maxValue==null){
            setConverter(null);
        }
    }

    public StringLongValuedProperty(String name, Long minValue, Long maxValue, String format, String... correctValues) {
        this(name, minValue, maxValue, null, format, correctValues);
    }

    public StringLongValuedProperty(String name, Long minValue, Long maxValue, String... correctValues) {
        this(name, minValue, maxValue, null, "%d", correctValues);
    }

    public StringLongValuedProperty(String name,String description,String defaultValue, Long minValue, Long maxValue, String... correctValues) {
        this(name, minValue, maxValue, defaultValue, "%d", correctValues);
    }

    public StringLongValuedProperty(String name, String uuid, String description, MessageSubSystem centralSystem, String defauleValue, String format, Long minValue, Long maxValue, String... correctValues) {
        super(name, uuid, defauleValue, format, new LongValueConverter(minValue,maxValue), correctValues);
        if(minValue==null && maxValue==null){
            setConverter(null);
        }
    }
}

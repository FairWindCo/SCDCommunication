package ua.pp.fairwind.communications.propertyes.software.stringlike;

/**
 * Created by Wind on 28.07.2014.
 */
public class StringFloatValuedProperty extends StringValuedPropertry {

    public StringFloatValuedProperty(String name, Float minValue, Float maxValue, String defauleValue, String format, String... correctValues) {
        super(name, new FloatValueConverter(minValue, maxValue), defauleValue, format, correctValues);
        if (minValue == null && maxValue == null) {
            setConverter(null);
        }
    }

    public StringFloatValuedProperty(String name, Float minValue, Float maxValue, String format, String... correctValues) {
        this(name, minValue, maxValue, null, format, correctValues);
    }


    public StringFloatValuedProperty(String name, String description, String defautValue, Float minValue, Float maxValue, String... correctValues) {
        this(name, minValue, maxValue, defautValue, "%f", correctValues);
    }

    public StringFloatValuedProperty(String name, String description, String defautValue, String... correctValues) {
        super(name, null, defautValue, "%d", correctValues);
    }

}

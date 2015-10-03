package ua.pp.fairwind.javafx.converters;

import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

public class NumberToShortFromStringConverter extends StringConverter<Number> {
    IntegerStringConverter internalConverter = new IntegerStringConverter();

    @Override
    public Number fromString(String arg0) {
        return internalConverter.fromString(arg0);
    }

    @Override
    public String toString(Number arg0) {
        if (arg0 != null)
            return internalConverter.toString(arg0.intValue());
        else
            return null;
    }

}

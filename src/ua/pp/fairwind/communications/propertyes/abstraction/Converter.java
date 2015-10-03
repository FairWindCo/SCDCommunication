package ua.pp.fairwind.communications.propertyes.abstraction;

/**
 * Created by Сергей on 16.07.2015.
 */
public interface Converter<FROM extends AbstractProperty, TO extends AbstractProperty> {
    void convertValue(FROM convertFrom, TO convertTo);
}

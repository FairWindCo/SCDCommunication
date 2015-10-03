package ua.pp.fairwind.communications.propertyes.software.stringlike;

public interface NumberValueConverter<T extends Number> {
    boolean checkValue(String val);

    String getValue(String val, String format, String defaultValue);

    T getMinValue();

    void setMinValue(T val);

    T getMaxValue();

    void setMaxValue(T val);
}

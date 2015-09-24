package ua.pp.fairwind.communications.propertyes.software.stringlike;

public interface NumberValueConverter<T extends Number> {
	boolean checkValue(String val);
	String getValue(String val, String format, String defaultValue);
	void setMinValue(T val);
	void setMaxValue(T val);
	T getMinValue();
	T getMaxValue();
}

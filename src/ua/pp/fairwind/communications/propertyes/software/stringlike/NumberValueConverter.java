package ua.pp.fairwind.communications.propertyes.software.stringlike;

public interface NumberValueConverter<T extends Number> {
	public boolean checkValue(String val);
	public String getValue(String val,String format,String defaultValue);
	public void setMinValue(T val);
	public void setMaxValue(T val);
	public T getMinValue();
	public T getMaxValue();
}

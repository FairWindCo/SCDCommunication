package ua.pp.fairwind.communications.propertyes.software.stringlike;


public class FloatValueConverter implements NumberValueConverter<Float> {
	private Float minValue;
	private Float maxValue;
	
	
	
	public FloatValueConverter(Float minValue, Float maxValue) {
		super();
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	@Override
	public boolean checkValue(String val) {
		Float vals;
		try{
			vals=Float.valueOf(val);
		} catch (NumberFormatException ex){
			return false;
		}
		if(vals!=null){
			if(maxValue!=null && vals>maxValue){
				return false;
			}
			if(minValue!=null && vals<minValue){
				return false;
			}
		}
		return true;
	}

	@Override
	public String getValue(String val, String format,String defaultValue) {
		Float vals;
		try{
			vals=Float.valueOf(val);
		} catch (NumberFormatException ex){
			String res;
			if(defaultValue !=null){
				res= defaultValue;
			} else if(minValue!=null){
				res=minValue.toString();
			} else if(maxValue!=null){
				res=maxValue.toString();
			} else {
				res="0";
			}	
			return res;
		}
		if(vals!=null){
			if(maxValue!=null && vals>maxValue){
				vals=maxValue;
			}
			if(minValue!=null && vals<minValue){
				vals=minValue;
			}
		}
		if(vals==null){
			return "null";
		}
		return (format!=null?String.format(format, vals):String.valueOf(vals));
	}

	public Float getMinValue() {
		return minValue;
	}

	public void setMinValue(Float minValue) {
		this.minValue = minValue;
	}

	public Float getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Float maxValue) {
		this.maxValue = maxValue;
	}



}

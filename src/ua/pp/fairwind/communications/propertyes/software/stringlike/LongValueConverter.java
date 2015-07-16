package ua.pp.fairwind.communications.propertyes.software.stringlike;

public class LongValueConverter implements NumberValueConverter<Long> {
	private Long minValue;
	private Long maxValue;
	
	
	@Override
	public boolean checkValue(String val) {
		Long vals;
		try{
			vals=Long.valueOf(val);
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
		Long vals;
		try{
			vals=Long.valueOf(val);
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

	
	
	public LongValueConverter(Long minValue, Long maxValue) {
		super();
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public Long getMinValue() {
		return minValue;
	}

	public void setMinValue(Long minValue) {
		this.minValue = minValue;
	}

	public Long getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Long maxValue) {
		this.maxValue = maxValue;
	}



}

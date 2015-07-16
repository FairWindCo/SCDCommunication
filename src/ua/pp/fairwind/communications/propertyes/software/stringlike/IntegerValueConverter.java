package ua.pp.fairwind.communications.propertyes.software.stringlike;

public class IntegerValueConverter implements NumberValueConverter<Integer> {
	private Integer minValue;
	private Integer maxValue;
	
	
	@Override
	public boolean checkValue(String val) {
		Integer vals;
		try{
			vals=Integer.valueOf(val);
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
		Integer vals;
		try{
			vals=Integer.valueOf(val);
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

	
	
	public IntegerValueConverter(Integer minValue, Integer maxValue) {
		super();
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public Integer getMinValue() {
		return minValue;
	}

	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}

	public Integer getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}



}

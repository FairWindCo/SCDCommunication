package ua.pp.fairwind.javafx.I18N;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public enum I18N {
	COMMON_RESOURCE("images.application",null),
	APPLICATION_RESOURCE("images.application",null),
	APPLICATION("ua.pp.fairwind.javafx.internatianalisation.application",null),
	COMMON("ua.pp.fairwind.communications.javafx.common",new ua.pp.fairwind.javafx.I18N.MyResources());


	final private ResourceBundle resourceBundle;

	I18N(String bundleFile,ResourceBundle defaultresourceBundle) {
		ResourceBundle resource=null;
		try {
			resource = ResourceBundle.getBundle(bundleFile);
		}catch (MissingResourceException ex){
			try {
				resource = ResourceBundle.getBundle(bundleFile, Locale.ENGLISH);
			} catch (MissingResourceException e){
				resource=defaultresourceBundle;
			}

		}
		resourceBundle=resource;
	}  
	

	public String getString(String key) {
		if(resourceBundle==null)return key;
		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			return key;
		}
	}

	private String getStringInternal(String key) {
		if(resourceBundle==null)return null;
		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			return null;
		}
	}

	public Object getValue(String key) {
		if(resourceBundle==null)return key;
		try {
			return resourceBundle.getObject(key);
		} catch (MissingResourceException e) {
			return key;
		}
	}

	private Object getValueInternal(String key) {
		if(resourceBundle==null)return null;
		try {
			return resourceBundle.getObject(key);
		} catch (MissingResourceException e) {
			return null;
		}
	}

	public static String getLocalizedString(String key){
		String value=null;
		for(I18N oneenum: I18N.values()){
			value=oneenum.getStringInternal(key);
			if(value!=null)break;
		}
		if(value==null)value=key;
		return value;
	}

	public static String getLocalizedStringErr(String key){
		String value=null;
		for(I18N oneenum: I18N.values()){
			value=oneenum.getStringInternal(key);
			if(value!=null)break;
		}
		if(value==null){
			System.err.println(key);
			value=key;
		}
		return value;
	}

	public static Object getObject(String key){
		Object value=null;
		for(I18N oneenum: I18N.values()){
			value=oneenum.getValueInternal(key);
			if(value!=null)break;
		}
		return value;
	}

	public static Object getObjectErr(String key){
		Object value=null;
		for(I18N oneenum: I18N.values()){
			value=oneenum.getValueInternal(key);
			if(value!=null)break;
		}
		if(value==null){
			System.err.println(key);
		}
		return value;
	}
}

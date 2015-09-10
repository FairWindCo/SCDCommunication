package ua.pp.fairwind.communications.internatianalisation;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public enum I18N {
	APPLICATION("ua.pp.fairwind.communications.internatianalisation.application",null),
	COMMON("ua.pp.fairwind.communications.internatianalisation.common",new MyResources());


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
}

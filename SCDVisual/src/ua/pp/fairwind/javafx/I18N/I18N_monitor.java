package ua.pp.fairwind.javafx.I18N;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public enum I18N_monitor {
	COMMON("ua.pp.fairwind.javafx.I18N.common");
	//APPLICATION("ua.pp.fairwind.communication.I18N.application");
	
	private ResourceBundle resourceBundle;  
	
	I18N_monitor(String bundleFile) {
		try {
			resourceBundle = ResourceBundle.getBundle(bundleFile);
		}catch (MissingResourceException ex){
			resourceBundle = ResourceBundle.getBundle(bundleFile, Locale.ENGLISH);
		}
	}  
	
	public String getString(String key) {  
	        try {  
	            return resourceBundle.getString(key);  
	        } catch (MissingResourceException e) {  
	            System.err.println(e);
	            return "err#"+key;
	        }  
	}

	public String getStringEx(String key) {
		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			//System.err.println(e);
			return key;
		}
	}
}

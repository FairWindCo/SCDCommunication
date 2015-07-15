package ua.pp.fairwind.javafx.I18N;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public enum I18N_monitor {
	COMMON("ua.pp.fairwind.communication.I18N.common");
	
	private ResourceBundle resourceBundle;  
	
	I18N_monitor(String bundleFile) {  
	        resourceBundle = ResourceBundle.getBundle(bundleFile);  
	}  
	
	public String getString(String key) {  
	        try {  
	            return resourceBundle.getString(key);  
	        } catch (MissingResourceException e) {  
	            System.err.println(e);  
	  
	            return "err#";  
	        }  
	}  
}

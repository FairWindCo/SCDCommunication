package ua.pp.fairwind.communications.devices.hardwaredevices.panDrive.internatianalisation;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public enum I18N_panDrive {
	COMMON("ua.pp.fairwind.communications.devices.hardwaredevices.panDrive.internatianalisation.common");
	//APPLICATION("ua.pp.fairwind.communication.I18N.application");

	private ResourceBundle resourceBundle;

	I18N_panDrive(String bundleFile) {
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

package ua.pp.fairwind.javafx.I18N;

import ua.pp.fairwind.communications.internatianalisation.UTF8Control;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public enum I18N_FX {
    COMMON_RESOURCE("ua.pp.fairwind.communications.javafx.common", null),
    APPLICATION_CONFIG("images.config", new MyResources()),
    APPLICATION_RESOURCE("images.application", null),
    APPLICATION_STANDART("ua.pp.fairwind.javafx.I18N.application", null),
    COMMON_STANDART("ua.pp.fairwind.javafx.I18N.common", null),
    APPLICATION_LIB("ua.pp.fairwind.communications.internatianalisation.application", null),
    COMMON_LIB("ua.pp.fairwind.communications.internatianalisation.common", null);


    final private ResourceBundle resourceBundle;

    I18N_FX(String bundleFile, ResourceBundle defaultresourceBundle) {
        ResourceBundle resource = null;
        try {
            resource = ResourceBundle.getBundle(bundleFile, new UTF8Control());
        } catch (MissingResourceException ex) {
            try {
                resource = ResourceBundle.getBundle(bundleFile, Locale.ENGLISH, new UTF8Control());
            } catch (MissingResourceException e) {
                resource = defaultresourceBundle;
            }

        }
        resourceBundle = resource;
    }

    public static String getLocalizedString(String key) {
        String value = null;
        for (I18N_FX oneenum : I18N_FX.values()) {
            value = oneenum.getStringInternal(key);
            if (value != null) break;
        }
        if (value == null) value = key;
        return value;
    }

    public static String getLocalizedStringErr(String key) {
        String value = null;
        for (I18N_FX oneenum : I18N_FX.values()) {
            value = oneenum.getStringInternal(key);
            if (value != null) break;
        }
        if (value == null) {
            System.err.println(key);
            value = key;
        }
        return value;
    }

    public static Object getObject(String key) {
        Object value = null;
        for (I18N_FX oneenum : I18N_FX.values()) {
            value = oneenum.getValueInternal(key);
            if (value != null) break;
        }
        return value;
    }

    public static Object getObjectErr(String key) {
        Object value = null;
        for (I18N_FX oneenum : I18N_FX.values()) {
            value = oneenum.getValueInternal(key);
            if (value != null) break;
        }
        if (value == null) {
            System.err.println(key);
        }
        return value;
    }

    public String getString(String key) {
        if (resourceBundle == null) return key;
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return key;
        }
    }

    private String getStringInternal(String key) {
        if (resourceBundle == null) return null;
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    public Object getValue(String key) {
        if (resourceBundle == null) return key;
        try {
            return resourceBundle.getObject(key);
        } catch (MissingResourceException e) {
            return key;
        }
    }

    private Object getValueInternal(String key) {
        if (resourceBundle == null) return null;
        try {
            return resourceBundle.getObject(key);
        } catch (MissingResourceException e) {
            return null;
        }
    }
}

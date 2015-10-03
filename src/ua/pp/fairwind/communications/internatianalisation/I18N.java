package ua.pp.fairwind.communications.internatianalisation;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.UUID;

public enum I18N {
    APPLICATION("ua.pp.fairwind.communications.internatianalisation.application", null),
    COMMON("ua.pp.fairwind.communications.internatianalisation.common", new MyResources()),
    APPLICATION_UUID("ua.pp.fairwind.communications.internatianalisation.uuids", null);


    final private ResourceBundle resourceBundle;


    I18N(String bundleFile, ResourceBundle defaultresourceBundle) {
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
        for (I18N oneenum : I18N.values()) {
            value = oneenum.getStringInternal(key);
            if (value != null) break;
        }
        if (value == null) value = key;
        return value;
    }

    public static String getLocalizedStringErr(String key) {
        String value = null;
        for (I18N oneenum : I18N.values()) {
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
        for (I18N oneenum : I18N.values()) {
            value = oneenum.getValueInternal(key);
            if (value != null) break;
        }
        return value;
    }

    public static Object getObjectErr(String key) {
        Object value = null;
        for (I18N oneenum : I18N.values()) {
            value = oneenum.getValueInternal(key);
            if (value != null) break;
        }
        if (value == null) {
            System.err.println(key);
        }
        return value;
    }

    public static UUID getUUIDFromCodeNAme(String codename) {
        String uuids = APPLICATION_UUID.getStringUUID(codename);
        UUID uid = null;
        if (uuids != null && !uuids.isEmpty()) {
            try {
                uid = UUID.fromString(uuids);
            } catch (IllegalArgumentException e) {
                System.err.println(uuids + " : " + e.getLocalizedMessage());
            }
        }
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        return uid;
    }

    public static UUID getUUIDFromCodeNAme(String uuid, String codename) {
        UUID uid = null;
        if (uuid != null && !uuid.isEmpty()) uid = UUID.fromString(uuid);
        if (uid == null) {
            uid = getUUIDFromCodeNAme(codename);
        }
        return uid;
    }

    public String getString(String key) {
        if (resourceBundle == null) return key;
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return key;
        }
    }

    public String getStringUUID(String key) {
        if (resourceBundle == null) return null;
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return null;
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

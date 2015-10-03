package ua.pp.fairwind.javafx.guiElements;

import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MyBaseResourceLoader {
    protected String baseNam = "ua.pp.fairwind.communication.I18N_FX.common";
    private ResourceBundle resourceBundle;

    public MyBaseResourceLoader(ResourceBundle resourceBundle) {
        super();
        this.resourceBundle = resourceBundle;
    }

    public MyBaseResourceLoader(String resourceBundleName) {
        this(ResourceBundle.getBundle(resourceBundleName));
    }


    public MyBaseResourceLoader() {
        super();
        this.resourceBundle = ResourceBundle.getBundle(baseNam);
    }

    public String getExternalResourceURILink(String filename) {
        URL uri = this.getClass().getResource(filename);
        if (uri != null) {
            return uri.toExternalForm();
        } else {
            return "";
        }
    }

    public String getString(String key) {
        if (resourceBundle != null) {
            try {
                return resourceBundle.getString(key);
            } catch (MissingResourceException e) {
                System.err.println(e);

                return "err#";
            }
        } else {
            return "err#noresbunlde";
        }
    }

    public String getStringEmpty(String key) {
        if (resourceBundle != null) {
            try {
                return resourceBundle.getString(key);
            } catch (MissingResourceException e) {
                //System.err.println(e);
                return "";
            }
        } else {
            return "";
        }
    }
}

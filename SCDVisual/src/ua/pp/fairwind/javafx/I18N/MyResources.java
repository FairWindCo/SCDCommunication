package ua.pp.fairwind.javafx.I18N;

import java.util.ListResourceBundle;

/**
 * Created by Сергей on 10.09.2015.
 */
public class MyResources extends ListResourceBundle {
    protected Object[][] getContents() {
        return new Object[][] {
                // LOCALIZE THIS
                {"LOGGING_ALL_EVENT", true},               // LOG ALL EVENT THAT OCCURRED IN SYSTEM
                {"LOGGING_ALL_ERROR", true},               // LOG ALL ERROR THAT OCCURRED IN SYSTEM
                {"LOGGING_LINEDEVICE_ERROR", true},               // LOG ALL ERROR THAT OCCURRED IN SYSTEM
                // END OF MATERIAL TO LOCALIZE
        };
    }
}

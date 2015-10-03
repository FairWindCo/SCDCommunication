package ua.pp.fairwind.io.javafx.propertys;

import ua.pp.fairwind.communications.propertyes.software.stringlike.StringValuedPropertry;
import ua.pp.fairwind.io.javafx.propertys.base.AbstractFXPropertyAdapter;

/**
 * Created by Wind on 29.07.2014.
 */
public class StringValuedPropertyFXAdapter extends AbstractFXPropertyAdapter<String> {

    public StringValuedPropertyFXAdapter(StringValuedPropertry property) {
        super(property);
    }

    public String getDescription() {
        return property.getDescription();
    }

    public String getDiapason() {
        if (property instanceof StringValuedPropertry) {
            return ((StringValuedPropertry) property).getDiapason();
        } else {
            return "";
        }
    }
}

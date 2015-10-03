package ua.pp.fairwind.io.javafx.propertys.special;

import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;
import ua.pp.fairwind.io.javafx.propertys.base.AbstractFXPropertyAdapter;

/**
 * Created by Wind on 22.07.2014.
 */
public class ShortPropertyFXAdapterSpec extends AbstractFXPropertyAdapter<Short> {

    public ShortPropertyFXAdapterSpec(NumberProperty<Short> property) {
        super(property);
    }

    @Override
    public void setValue(Short t) {
        super.setValue(t != null ? t : null);
    }
}

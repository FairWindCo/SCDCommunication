package ua.pp.fairwind.io.javafx.propertys;

import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.io.javafx.propertys.base.AbstractFXPropertyAdapter;

/**
 * Created by Wind on 22.07.2014.
 */
public class BooleanPropertyFXAdapter extends AbstractFXPropertyAdapter<Boolean> {
    public BooleanPropertyFXAdapter(ValueProperty<Boolean> property) {
        super(property);
    }
}

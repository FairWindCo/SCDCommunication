package ua.pp.fairwind.io.javafx.propertys;

import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;
import ua.pp.fairwind.io.javafx.propertys.base.NumberFXPropertyAdapter;

/**
 * Created by Wind on 22.07.2014.
 */
public class FloatPropertyFXAdapter extends NumberFXPropertyAdapter<Float> {
    public FloatPropertyFXAdapter(NumberProperty<Float> property) {
        super(property);
    }

    @Override
    public void setValue(Number t) {
        super.setValue(t == null ? null : t.floatValue());
    }
}

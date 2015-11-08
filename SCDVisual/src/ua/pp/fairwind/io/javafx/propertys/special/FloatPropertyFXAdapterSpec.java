package ua.pp.fairwind.io.javafx.propertys.special;

import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;
import ua.pp.fairwind.io.javafx.propertys.base.AbstractFXPropertyAdapter;

/**
 * Created by Wind on 22.07.2014.
 */
public class FloatPropertyFXAdapterSpec extends AbstractFXPropertyAdapter<Float> {

    public FloatPropertyFXAdapterSpec(NumberProperty<Float> property) {
        super(property);
    }

    @Override
    public void setValue(Float t) {
        super.setValue(t != null ? t : null);
    }
}

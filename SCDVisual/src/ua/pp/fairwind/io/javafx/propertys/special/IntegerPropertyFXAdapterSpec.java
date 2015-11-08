package ua.pp.fairwind.io.javafx.propertys.special;

import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;
import ua.pp.fairwind.io.javafx.propertys.base.AbstractFXPropertyAdapter;

/**
 * Created by Wind on 22.07.2014.
 */
public class IntegerPropertyFXAdapterSpec extends AbstractFXPropertyAdapter<Integer> {

    public IntegerPropertyFXAdapterSpec(NumberProperty<Integer> property) {
        super(property);
    }

    @Override
    public void setValue(Integer t) {
        super.setValue(t != null ? t : null);
    }
}

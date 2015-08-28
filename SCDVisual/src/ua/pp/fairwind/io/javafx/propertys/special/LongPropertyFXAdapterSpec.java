package ua.pp.fairwind.io.javafx.propertys.special;

import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;
import ua.pp.fairwind.io.javafx.propertys.base.AbstractFXPropertyAdapter;

/**
 * Created by Wind on 22.07.2014.
 */
public class LongPropertyFXAdapterSpec extends AbstractFXPropertyAdapter<Long> {

    public LongPropertyFXAdapterSpec(NumberProperty<Long> property) {
        super(property);
    }

    @Override
    public void setValue(Long t) {
        super.setValue(t!=null?t:null);
    }
}

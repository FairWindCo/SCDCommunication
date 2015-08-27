package ua.pp.fairwind.io.javafx.propertys.base;

import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;

/**
 * Created by Wind on 22.07.2014.
 */
public class LongPropertyFXAdapter extends NumberFXPropertyAdapter<Long> {

    public LongPropertyFXAdapter(NumberProperty<Long> property) {
        super(property);
    }

    @Override
    public void setValue(Number t) {
        super.setValue(t!=null?t.intValue():null);
    }
}

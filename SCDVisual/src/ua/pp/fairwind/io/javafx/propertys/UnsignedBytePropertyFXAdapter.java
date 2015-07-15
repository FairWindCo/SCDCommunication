package ua.pp.fairwind.io.javafx.propertys;

import ua.pp.fairwind.io.javafx.propertys.base.NumberFXPropertyAdapter;
import ua.pp.fairwind.io.property.UnsignedByteProperty;

/**
 * Created by Wind on 22.07.2014.
 */
public class UnsignedBytePropertyFXAdapter extends NumberFXPropertyAdapter<Integer> {

    public UnsignedBytePropertyFXAdapter(UnsignedByteProperty property) {
        super(property);
    }

    @Override
    public void setValue(Number t) {
        super.setValue(t!=null?t.intValue():null);
    }
}

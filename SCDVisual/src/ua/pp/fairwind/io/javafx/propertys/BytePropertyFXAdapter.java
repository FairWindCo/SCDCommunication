package ua.pp.fairwind.io.javafx.propertys;

import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;
import ua.pp.fairwind.io.javafx.propertys.base.NumberFXPropertyAdapter;

/**
 * Created by Wind on 22.07.2014.
 */
public class BytePropertyFXAdapter extends NumberFXPropertyAdapter<Byte> {

    public BytePropertyFXAdapter(NumberProperty<Byte> property) {
        super(property);
    }

    @Override
    public void setValue(Number t) {
        super.setValue(t!=null?t.byteValue():null);
    }
}

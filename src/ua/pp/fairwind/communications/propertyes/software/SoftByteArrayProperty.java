package ua.pp.fairwind.communications.propertyes.software;

import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ByteArrayPropertyInterface;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberPropertyInterface;
import ua.pp.fairwind.communications.propertyes.abstraction.StringPropertyInterface;

/**
 * Created by FairWindCo on 07.07.2015
 */
public class SoftByteArrayProperty extends AbstractProperty implements ByteArrayPropertyInterface {

    public SoftByteArrayProperty(String name, String uuid) {
        super(name, uuid);
    }


    @Override
    public void bindReadCharProperty(StringPropertyInterface property) {

    }

    @Override
    public void bindWriteCharProperty(StringPropertyInterface property) {

    }

    @Override
    public void bindReadCharProperty(StringPropertyInterface property, int position) {

    }

    @Override
    public void bindWriteCharProperty(StringPropertyInterface property, int position) {

    }

    @Override
    public void bindReadCharProperty(StringPropertyInterface property, int position, int length) {

    }

    @Override
    public void bindWriteCharProperty(StringPropertyInterface property, int position, int length) {

    }

    @Override
    public Byte getValue(int index) {
        return null;
    }

    @Override
    public void setValue(Byte value, int index) {

    }

    @Override
    public void bindReadStringProperty(StringPropertyInterface property) {

    }

    @Override
    public void bindWriteStringProperty(StringPropertyInterface property) {

    }

    @Override
    public void bindReadStringProperty(StringPropertyInterface property, int position) {

    }

    @Override
    public void bindWriteStringProperty(StringPropertyInterface property, int position) {

    }

    @Override
    public void bindReadStringProperty(StringPropertyInterface property, int position, int length) {

    }

    @Override
    public void bindWriteStringProperty(StringPropertyInterface property, int position, int length) {

    }


    @Override
    public void bindReadNumberProperty(NumberPropertyInterface<?> property) {

    }

    @Override
    public void bindWriteNumberProperty(NumberPropertyInterface<?> property) {

    }

    @Override
    public void bindReadNumberProperty(NumberPropertyInterface<?> property, int position) {

    }

    @Override
    public void bindWriteNumberProperty(NumberPropertyInterface<?> property, int position) {

    }

    @Override
    public void bindReadNumberProperty(NumberPropertyInterface<?> property, int position, int length) {

    }

    @Override
    public void bindWriteNumberProperty(NumberPropertyInterface<?> property, int position, int length) {

    }

}

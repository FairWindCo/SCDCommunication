package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.abstractions.ElementInterface;

/**
 * Created by ������ on 04.07.2015.
 */
public interface NumberPropertyInterface<M extends Number & Comparable<? super M>> extends ElementInterface, ValuePropertyInterface<M>, ReadWriteProperty<M> {
    void bindReadNumberProperty(NumberProperty<? extends Number> property);

    void bindWriteNumberProperty(NumberProperty<? extends Number> property);

    void bindReadStringProperty(StringPropertyInterface property);

    void bindWriteStringProperty(StringPropertyInterface property);

    void bindReadStringProperty(StringPropertyInterface property, int radix);

    void bindWriteStringProperty(StringPropertyInterface property, int radix);

    void bindReadStringProperty(StringPropertyInterface property, String format);

    void bindWriteStringProperty(StringPropertyInterface property, String format);

}

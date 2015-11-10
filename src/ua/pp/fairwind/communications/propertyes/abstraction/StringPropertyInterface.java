package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.propertyes.abstraction.markers.StringValueInterface;

/**
 * Created by ������ on 04.07.2015.
 */
public interface StringPropertyInterface extends ValuePropertyInterface<String>,StringValueInterface {
    void bindReadNumberProperty(NumberProperty<?> property, int radix);

    void bindWriteNumberProperty(NumberProperty<?> property, int radix);

    void bindReadNumberProperty(NumberProperty<?> property, String format);

    void bindWriteNumberProperty(NumberProperty<?> property, String format);

    void bindReadNumberProperty(NumberProperty<?> property);

    void bindWriteNumberProperty(NumberProperty<?> property);
}

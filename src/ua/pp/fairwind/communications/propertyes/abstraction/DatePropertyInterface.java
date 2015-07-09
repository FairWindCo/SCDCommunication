package ua.pp.fairwind.communications.propertyes.abstraction;

import java.util.Date;

/**
 * Created by Сергей on 07.07.2015.
 */
public interface DatePropertyInterface extends ValuePropertyInterface<Date> {
    void bindReadNumberProperty(NumberProperty<?> property);
    void bindWriteNumberProperty(NumberProperty<?> property);
    void unbindWriteNumberProperty();
    void unbindReadNumberProperty();

    void bindReadStringProperty(StringPropertyInterface property);
    void bindWriteStringProperty(StringPropertyInterface property);
    void bindReadStringProperty(StringPropertyInterface property,String format);
    void bindWriteStringProperty(StringPropertyInterface property,String format);
    void unbindWriteStringProperty();
    void unbindReadStringProperty();
}

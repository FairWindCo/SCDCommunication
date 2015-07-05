package ua.pp.fairwind.communications.propertyes.abstraction;

/**
 * Created by Сергей on 04.07.2015.
 */
public interface StringPropertyInterface extends ValuePropertyInterface<String> {
    void bindReadNumberProperty(NumberProperty<?> property,int radix);
    void bindWriteNumberProperty(NumberProperty<?> property,int  radix);
    void bindReadNumberProperty(NumberProperty<?> property,String format);
    void bindWriteNumberProperty(NumberProperty<?> property,String format);
    void bindReadNumberProperty(NumberProperty<?> property);
    void bindWriteNumberProperty(NumberProperty<?> property);

    void unbindReadNumberProperty();
    void unbindWriteNumberProperty();
}

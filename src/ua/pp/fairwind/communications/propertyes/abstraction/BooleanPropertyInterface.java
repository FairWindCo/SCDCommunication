package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.propertyes.abstraction.markers.BooleanValueInterface;

/**
 * Created by Сергей on 07.07.2015.
 */
public interface BooleanPropertyInterface extends ValuePropertyInterface<Boolean>,BooleanValueInterface {
    void bindReadNumberProperty(NumberProperty<?> property);

    void bindWriteNumberProperty(NumberProperty<?> property);

    void unbindWriteNumberProperty();

    void unbindReadNumberProperty();

    void bindReadStringProperty(StringPropertyInterface property);

    void bindWriteStringProperty(StringPropertyInterface property);

    void bindReadStringProperty(StringPropertyInterface property, boolean binformat);

    void bindWriteStringProperty(StringPropertyInterface property, boolean binformat);

    void unbindWriteStringProperty();

    void unbindReadStringProperty();

}

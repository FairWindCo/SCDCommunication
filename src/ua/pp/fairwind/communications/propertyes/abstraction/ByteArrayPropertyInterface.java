package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.abstractions.ElementInterface;

/**
 * Created by Сергей on 07.07.2015.
 */
public interface ByteArrayPropertyInterface extends ElementInterface{
    Byte getValue(int index);
    void setValue(Byte value,int index);


    void bindReadStringProperty(StringPropertyInterface property);
    void bindWriteStringProperty(StringPropertyInterface property);
    void bindReadStringProperty(StringPropertyInterface property,int position);
    void bindWriteStringProperty(StringPropertyInterface property,int position);
    void bindReadStringProperty(StringPropertyInterface property,int position,int length);
    void bindWriteStringProperty(StringPropertyInterface property,int position,int length);


    void bindReadNumberProperty(NumberPropertyInterface<?> property);
    void bindWriteNumberProperty(NumberPropertyInterface<?> property);
    void bindReadNumberProperty(NumberPropertyInterface<?> property,int position);
    void bindWriteNumberProperty(NumberPropertyInterface<?> property,int position);
    void bindReadNumberProperty(NumberPropertyInterface<?> property,int position,int length);
    void bindWriteNumberProperty(NumberPropertyInterface<?> property,int position,int length);


    void bindReadCharProperty(StringPropertyInterface property);
    void bindWriteCharProperty(StringPropertyInterface property);
    void bindReadCharProperty(StringPropertyInterface property,int position);
    void bindWriteCharProperty(StringPropertyInterface property,int position);
    void bindReadCharProperty(StringPropertyInterface property,int position,int length);
    void bindWriteCharProperty(StringPropertyInterface property,int position,int length);
}

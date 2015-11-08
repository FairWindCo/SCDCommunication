package ua.pp.fairwind.javafx.panels.propertypanels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;
import ua.pp.fairwind.communications.propertyes.groups.GroupProperty;
import ua.pp.fairwind.communications.propertyes.software.*;
import ua.pp.fairwind.io.javafx.propertys.special.*;

/**
 * Created by Сергей on 08.11.2015.
 */
public class ConvertorSoftPropertyValue {
    static public ObservableValue getSoftPropertyAdapter(AbstractProperty property){
        if(property instanceof SoftByteProperty){
            return new BytePropertyFXAdapterSpec((NumberProperty)property);
        } else if (property instanceof SoftShortProperty){
            return new ShortPropertyFXAdapterSpec((NumberProperty)property);
        }else if (property instanceof SoftLongProperty){
            return new LongPropertyFXAdapterSpec((NumberProperty)property);
        }else if (property instanceof SoftFloatProperty){
            return new FloatPropertyFXAdapterSpec((NumberProperty)property);
        }else if (property instanceof SoftIntegerProperty){
            return new IntegerPropertyFXAdapterSpec((NumberProperty)property);
        }else if (property instanceof GroupProperty){
            return new SimpleStringProperty("GROUP PROPERTY");
        }else {
            return new SimpleStringProperty("NO TYPE SUPPORTED!");
        }
    }
}

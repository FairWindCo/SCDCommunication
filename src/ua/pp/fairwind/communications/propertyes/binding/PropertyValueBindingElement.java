package ua.pp.fairwind.communications.propertyes.binding;

import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValuedPropertyConvertor;

/**
 * Created by Сергей on 16.07.2015.
 */
public class PropertyValueBindingElement<FROM extends Comparable<? super FROM>, TO extends Comparable<? super TO>> extends PropertyBindingElement<ValueProperty<FROM>, ValueProperty<TO>> {


    public PropertyValueBindingElement(ValueProperty<FROM> readingProperty, ValueProperty<TO> writingProperty, ValueConvertor<FROM, TO> readConvertor, ValueConvertor<TO, FROM> writeConvertor) {
        super(readingProperty, writingProperty, new ValuedPropertyConvertor<FROM, TO>(readConvertor), new ValuedPropertyConvertor<TO, FROM>(writeConvertor));
    }


}

package ua.pp.fairwind.communications.propertyes.binding;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;

/**
 * Created by Сергей on 16.07.2015.
 */
public class NumberPropertyBindingelement<FROM extends Number & Comparable<? super FROM>,TO extends Number & Comparable<? super TO>> extends PropertyValueBindingElement<FROM,TO> {
    public NumberPropertyBindingelement(NumberProperty<FROM> readingProperty, NumberProperty<TO> writingProperty, ValueConvertor<FROM, TO> readConvertor, ValueConvertor<TO, FROM> writeConvertor, MessageSubSystem centralSystem) {
        super(readingProperty, writingProperty, readConvertor, writeConvertor, centralSystem);
    }

    public NumberPropertyBindingelement(NumberProperty<FROM> readingProperty, NumberProperty<TO> writingProperty, ValueConvertor<FROM, TO> readConvertor, ValueConvertor<TO, FROM> writeConvertor) {
        super(readingProperty, writingProperty, readConvertor, writeConvertor);
    }
}

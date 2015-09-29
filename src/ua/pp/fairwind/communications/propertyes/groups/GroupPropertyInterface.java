package ua.pp.fairwind.communications.propertyes.groups;

import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;

import java.util.stream.Stream;

/**
 * Created by Сергей on 13.09.2015.
 */
public interface GroupPropertyInterface {
    AbstractProperty get(String name);

    AbstractProperty getByUUID(String name);

    Stream<AbstractProperty> getStream();

    AbstractProperty getPopertyByIndex(int index);

    boolean isBubleEvent();
    void setBubleEvent(boolean bubleEvent);
    int propertyCount();
}

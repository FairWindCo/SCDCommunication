package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.abstractions.EventSender;

import java.util.Map;

/**
 * Created by Сергей on 27.08.2015.
 */
public interface ValueValidator<T> {
    T validateNewValue(T oldValue,T newValue,Map<String,Object> additionalParameters,EventSender eventSender);
}

package ua.pp.fairwind.io.javafx.propertys.base;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;

/**
 * Created by Wind on 22.07.2014.
 */
public class AbstractFXPropertyAdapter<T extends Comparable<? super T>> extends AbstractFXObservableAdapter<T> implements Property<T> {
    private ObservableValue<T> observable = null;
    private InvalidationListener listener = null;

    public AbstractFXPropertyAdapter(ValueProperty<T> property) {
        super(property);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void bind(ObservableValue<? extends T> observableValue) {
        if (observable == null) {
            throw new NullPointerException("Cannot bind to null");
        }
        if (!observable.equals(this)) {
            unbind();

            this.observable =  (ObservableValue<T>) observableValue;
            if (listener == null) {
                listener = new Listener();
            }
            observable.addListener(listener);
            markInvalid();
        }
    }

    @Override
    public void unbind() {
        if (observable != null) {
            setValue(observable.getValue());
            observable.removeListener(listener);
            observable = null;
        }
    }

    @Override
    public boolean isBound() {
        return observable != null;
    }

    @Override
    public void bindBidirectional(Property<T> tProperty) {
        Bindings.bindBidirectional(this, tProperty);
    }

    @Override
    public void unbindBidirectional(Property<T> tProperty) {
        Bindings.unbindBidirectional(this, tProperty);
    }

    @Override
    public Object getBean() {
        return this;
    }

    @Override
    public String getName() {
        return property.getName();
    }

    @Override
    public void setValue(T t) {
        property.setValue(t);
    }

    public void markInvalid(){
        invalidated();
        //fireInvalidate();
        //fireValueChangedEvent();
    }

    protected void invalidated() {
    }

    private class Listener implements InvalidationListener {
        @Override
        public void invalidated(Observable valueModel) {
            markInvalid();
        }
    }


}

package ua.pp.fairwind.io.javafx.propertys.base;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import ua.pp.fairwind.communications.propertyes.abstraction.NumberProperty;

/**
 * Created by Wind on 22.07.2014.
 */
public class NumberFXPropertyAdapter<T extends Number & Comparable<? super T>> extends NumberFXObservableAdapter<T> implements Property<Number> {
    private ObservableValue<T> observable = null;
    private InvalidationListener listener = null;

    public NumberFXPropertyAdapter(NumberProperty<T> property) {
        super(property);
    }

    @Override
    public void bind(ObservableValue<? extends Number> observableValue) {
        if (observable == null) {
            throw new NullPointerException("Cannot bind to null");
        }
        if (!observable.equals(this)) {
            unbind();
            this.observable = (ObservableValue<T>) observableValue;
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
    public void bindBidirectional(Property<Number> tProperty) {
        Bindings.bindBidirectional(this, tProperty);
    }

    @Override
    public void unbindBidirectional(Property<Number> tProperty) {
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
    public void setValue(Number t) {
        T val = (T) t;
        property.setValue(val);
    }

    public void markInvalid() {
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

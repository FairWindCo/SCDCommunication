package ua.pp.fairwind.io.javafx.propertys.base;

import com.sun.javafx.binding.ExpressionHelper;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ua.pp.fairwind.communications.messagesystems.event.ValueChangeEvent;
import ua.pp.fairwind.communications.messagesystems.event.ValueChangeListener;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Wind on 22.07.2014.
 */
public class AbstractFXObservableAdapter<T extends Comparable<? super T>> implements ObservableValue<T>, ValueChangeListener<T> {
    final protected ValueProperty<T> property;
    private final CopyOnWriteArrayList<InvalidationListener> ivalidation = new CopyOnWriteArrayList<>();
    private ExpressionHelper<T> helper = null;

    public AbstractFXObservableAdapter(ValueProperty<T> property) {
        this.property = property;
        if (property == null) throw new IllegalArgumentException("property is null");
        property.addChangeEventListener(this);
    }

    public void release() {
        property.removeChangeEventListener(this);
    }

    @Override
    protected void finalize() throws Throwable {
        release();
        super.finalize();
    }

    @Override
    public void valueChange(ValueChangeEvent event) {
        try {
            //System.out.println("!!!!!!!VALIDATE VALUE ADAPTER");
            fireInvalidate();
            fireValueChangedEvent();
        } catch (Exception ex) {
            System.out.println(ex);
            //ex.printStackTrace();
        }
    }


    @Override
    public void addListener(ChangeListener<? super T> changeListener) {
        helper = ExpressionHelper.addListener(helper, this, changeListener);
    }

    @Override
    public void removeListener(ChangeListener<? super T> changeListener) {
        helper = ExpressionHelper.removeListener(helper, changeListener);
    }

    @Override
    public T getValue() {
        return property.getValue();
    }

    @Override
    public void addListener(InvalidationListener invalidationListener) {
        ivalidation.add(invalidationListener);
    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {
        ivalidation.remove(invalidationListener);
    }

    protected void fireValueChangedEvent() {
        if (helper != null) {
            if (Platform.isFxApplicationThread()) {
                ExpressionHelper.fireValueChangedEvent(helper);
            } else {
                try {
                    Platform.runLater(() -> ExpressionHelper.fireValueChangedEvent(helper));
                } catch (IllegalStateException ex) {
                    ExpressionHelper.fireValueChangedEvent(helper);
                }
            }
        }
    }

    protected void fireInvalidate() {
        if (Platform.isFxApplicationThread()) {
            for (InvalidationListener listener : ivalidation) {
                listener.invalidated(this);
            }
        } else {
            try {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        for (InvalidationListener list : ivalidation) {
                            list.invalidated(AbstractFXObservableAdapter.this);
                        }
                    }
                });
            } catch (IllegalStateException ex) {
                for (InvalidationListener list : ivalidation) {
                    list.invalidated(AbstractFXObservableAdapter.this);
                }
            }
        }
    }

    public ValueProperty<T> getProperty() {
        return property;
    }
}

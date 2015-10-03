package ua.pp.fairwind.javafx.guiElements.editorSoftProperty;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import ua.pp.fairwind.communications.messagesystems.event.ValueChangeListener;
import ua.pp.fairwind.communications.propertyes.software.SoftStringProperty;
import ua.pp.fairwind.javafx.VisualControls;


public class SoftStringInputText extends TextField implements ChangeListener<String> {
    final private SoftStringProperty property;
    ValueChangeListener<String> eventListener = event -> {
        if (event.getNewValue() != null) {
            VisualControls.executeInJavaFXThread(() ->
                    setText((String) event.getNewValue()));
        }
    };


    public SoftStringInputText(SoftStringProperty property) {
        super(property.getValue() == null ? null : property.getValue().toString());
        this.property = property;
        onInitialisation();
    }

    private void onInitialisation() {
        this.textProperty().addListener(this);
        property.addChangeEventListener(eventListener);
    }

    @Override
    public void changed(ObservableValue<? extends String> value, String olds, String newValue) {
        if (newValue == null || newValue.isEmpty()) {
            setText("");
            property.setValue("");
            return;
        } else {
            property.setValue(newValue);
        }
    }

    public SoftStringProperty getFloatValueProperty() {
        return property;
    }

    public void destroy() {
        if (property != null) property.removeChangeEventListener(eventListener);
    }

    @Override
    protected void finalize() throws Throwable {
        destroy();
        super.finalize();
    }
}

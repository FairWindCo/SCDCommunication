package ua.pp.fairwind.javafx.guiElements.editorSoftProperty;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import ua.pp.fairwind.communications.messagesystems.event.ValueChangeListener;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValuePropertyModificator;
import ua.pp.fairwind.javafx.VisualControls;


public class SoftIntInputText extends TextField implements EventHandler<KeyEvent>, ChangeListener<String>{
    final private static String DIGITPATERN = "[-]?[0123456789]{1,11}";
    final private static String EMPTYSTRING = "";
    final private ValueProperty<Integer> property;
    private int maxValue = Integer.MAX_VALUE;
    private int minValue = Integer.MIN_VALUE;
    ValueChangeListener<Integer> eventListener = event -> {
        if (event.getNewValue() != null)
            VisualControls.executeInJavaFXThread(() ->
                    setIntVal((Integer) event.getNewValue()));
    };

    public SoftIntInputText(ValueProperty<Integer> property) {
        super(property.getValue() == null ? null : property.getValue().toString());
        this.property = property;
        onInitialisation();
    }

    public SoftIntInputText(ValueProperty<Integer> property, int minVal, int maxVal) {
        super(property.getValue() == null ? null : property.getValue().toString());
        this.property = property;
        this.maxValue = maxVal;
        this.minValue = minVal;
        onInitialisation();
    }

    public SoftIntInputText(ValueProperty<Integer> property, int maxVal) {
        super(property.getValue() == null ? null : property.getValue().toString());
        this.property = property;
        this.maxValue = maxVal;
        onInitialisation();
    }

    public static String getDigitpatern() {
        return DIGITPATERN;
    }

    public static String getEmptystring() {
        return EMPTYSTRING;
    }

    private void checkConstraints() {
        if (minValue > maxValue) {
            int v = maxValue;
            maxValue = minValue;
            minValue = v;
        }
    }

    private int parseString(String str) {
        if (str == null || EMPTYSTRING.equals(str)) {
            setText("0");
            return 0;
        }
        int intVal = Integer.parseInt(str);
        if (intVal < minValue) {
            return minValue;
        }
        if(intVal>maxValue){
            setText(String.valueOf(maxValue));
            return maxValue;
        }
        return intVal;
    }

    private void setIntVal(Integer val) {
        if (val >= minValue && val <= maxValue) {
            setText(Long.toString(val));
        } else {
            //property.setValue(parseString(getText()));
            ValuePropertyModificator.setInternalValue(property,parseString(getText()),null);
        }
    }

    private void onInitialisation() {
        checkConstraints();
        this.addEventFilter(KeyEvent.KEY_TYPED, this);
        this.textProperty().addListener(this);
        property.addChangeEventListener(eventListener);
    }
/*
    @Override
	public void replaceText(IndexRange arg0, String text) {
		if(text.matches(DIGITPATERN)||text.equals(EMPTYSTRING)){
			super.replaceText(arg0, text);
		}
	}/**/

    @Override
    public void replaceSelection(String text) {
        if (text.matches(DIGITPATERN) || text.equals(EMPTYSTRING)) {
            super.replaceSelection(text);
        }
    }

    @Override
    public void replaceText(int arg0, int arg1, String text) {
        if (text.equals("-") || text.matches(DIGITPATERN) || text.equals(EMPTYSTRING)) {
            super.replaceText(arg0, arg1, text);
        }
    }

    @Override
    public void handle(KeyEvent keyevent) {
        if (!"-0123456789".contains(keyevent.getCharacter())) {
            keyevent.consume();
        }
		/*
		if(maxSize>0 && this.getText()!=null && this.getText().length()>=maxSize){
			keyevent.consume();
		}
		*/

    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        checkConstraints();
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
        checkConstraints();
    }

    @Override
    public void changed(ObservableValue<? extends String> value, String olds, String newValue) {
        if(olds==newValue || olds!=null&&olds.equals(newValue))return;
        if("".equals(newValue)&&"0".equals(olds)){
            return;
        }
        if (newValue == null || EMPTYSTRING.equals(newValue)) {
            setText("0");
            property.setValue(0);
            return;
        }
        if ("-".equals(newValue)) {
            if (minValue >= 0) {
                setText(EMPTYSTRING);
            }
        } else {
            int intVal = Integer.parseInt(newValue);
            if (intVal < minValue) {
                setText(String.valueOf(minValue));
                return;
            }
            if(intVal>maxValue){
                setText(String.valueOf(maxValue));
                return;
            }
            ValuePropertyModificator.setSilentValue(property, parseString(newValue));
        }
    }

    public ValueProperty<Integer> getIntegerValueProperty() {
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

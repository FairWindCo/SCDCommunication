package ua.pp.fairwind.javafx.guiElements.editorSoftProperty;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import ua.pp.fairwind.communications.messagesystems.event.ValueChangeListener;
import ua.pp.fairwind.communications.propertyes.abstraction.ValuePropertyModificator;
import ua.pp.fairwind.communications.propertyes.software.SoftFloatProperty;
import ua.pp.fairwind.javafx.VisualControls;


public class SoftFloatInputText extends TextField implements EventHandler<KeyEvent>, ChangeListener<String> {
    final private static String DIGITPATERN = "[-]?[0123456789]*[.]?[0123456789]*";
    final private static String EMPTYSTRING = "";
    final private SoftFloatProperty property;
    private float maxValue = Float.MAX_VALUE;
    private float minValue = Float.MIN_VALUE;
    ValueChangeListener<Float> eventListener = event -> {
        if (event.getNewValue() != null)
            VisualControls.executeInJavaFXThread(() ->
                    setIntVal((Float) event.getNewValue()));
    };

    public SoftFloatInputText(SoftFloatProperty property) {
        super(property.getValue() == null ? null : property.getValue().toString());
        this.property = property;
        onInitialisation();
    }

    public SoftFloatInputText(SoftFloatProperty property, float minVal, float maxVal) {
        super(property.getValue() == null ? null : property.getValue().toString());
        this.property = property;
        this.maxValue = maxVal;
        this.minValue = minVal;
        onInitialisation();
    }

    public SoftFloatInputText(SoftFloatProperty property, float maxVal) {
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
            float v = maxValue;
            maxValue = minValue;
            minValue = v;
        }
    }

    private Float parseString(String str) {
        if (str == null || EMPTYSTRING.equals(str)) {
            setText("0");
            return 0f;
        }
        Float intVal = Float.parseFloat(str);
        if (intVal < minValue) {
            return minValue;
        }
        if(intVal>maxValue){
            setText(String.valueOf(maxValue));
            return maxValue;
        }
        return intVal;
    }

    private void setIntVal(Float val) {
        if (val >= minValue && val <= maxValue) {
            String str=Float.toString(val);
            if(str!=null){
                String curent=getText();
                if(!str.equals(curent)){
                    setText(str);
                }
            }

        } else {
            property.setValue(parseString(getText()));
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
        if (!"-0123456789.".contains(keyevent.getCharacter())) {
            keyevent.consume();
        }
		/*
		if(maxSize>0 && this.getText()!=null && this.getText().length()>=maxSize){
			keyevent.consume();
		}
		*/

    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
        checkConstraints();
    }

    public float getMinValue() {
        return minValue;
    }

    public void setMinValue(float minValue) {
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
            property.setValue((float) 0);
            return;
        }
        if ("-".equals(newValue)) {
            if (minValue >= 0) {
                setText(EMPTYSTRING);
            }
        } else {
            Float intVal = Float.parseFloat(newValue);
            if (intVal < minValue) {
                setText(String.valueOf(minValue));
                return;
            }
            if(intVal>maxValue){
                setText(String.valueOf(maxValue));
                return;
            }
            //property.setValue(parseString(getText()));
            ValuePropertyModificator.setSilentValue(property,parseString(newValue));
        }
    }

    public SoftFloatProperty getFloatValueProperty() {
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

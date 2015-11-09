package ua.pp.fairwind.javafx.guiElements.editors;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;


public class FloatInputText extends TextField implements EventHandler<KeyEvent>, ChangeListener<String> {
    final private static String DIGITPATERN = "[-]?[0123456789]*[.]?[0123456789]+";
    final private static String EMPTYSTRING = "";
    private float maxValue = Float.MAX_VALUE;
    private float minValue = -Float.MAX_VALUE;
    private SimpleFloatProperty internalValueProperty = new SimpleFloatProperty(0);

    public FloatInputText() {
        super();
        onInitialisation();
    }

    public FloatInputText(Property property) {
        super(property.getValue() != null ? property.getValue().toString() : "");
        this.internalValueProperty.bindBidirectional(property);
        onInitialisation();
    }

    public FloatInputText(Float arg0) {
        super(arg0 == null ? null : arg0.toString());
        if(arg0!=null)internalValueProperty.setValue(arg0);
        onInitialisation();
    }

    public FloatInputText(Float arg0, float maxVal) {
        super(arg0 == null ? null : arg0.toString());
        if(arg0!=null)internalValueProperty.setValue(arg0);
        this.maxValue = maxVal;
        onInitialisation();
    }

    public FloatInputText(float maxVal) {
        super();
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

    private float parseString(String str) {
        if (str == null || EMPTYSTRING.equals(str)) {
            setText("0");
            return 0;
        }
        float intVal = Float.parseFloat(str);
        if (intVal < minValue || intVal > maxValue) {
            return 0;
        }
        return intVal;
    }

    private void setIntVal(Float val) {
        if (val >= minValue && val <= maxValue) {
            setText(Float.toString(val));
        } else {
            internalValueProperty.setValue(parseString(getText()));
        }
    }

    private void onInitialisation() {
        checkConstraints();
        this.addEventFilter(KeyEvent.KEY_TYPED, this);
        this.textProperty().addListener(this);
        internalValueProperty.addListener((arg0, arg1, newval) -> {
            if (newval != null)
                setIntVal(newval.floatValue());
        });

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
            internalValueProperty.set(0);
            return;
        }
        if ("-".equals(newValue)) {
            if (minValue >= 0) {
                setText(EMPTYSTRING);
            }
        } else {
            float intVal = Float.parseFloat(newValue);
            if (intVal < minValue) {
                setText(String.valueOf(minValue));
                return;
            }
            if(intVal>maxValue){
                setText(String.valueOf(maxValue));
                return;
            }
            internalValueProperty.set(intVal);
        }
    }

    public SimpleFloatProperty getInternalValueProperty() {
        return internalValueProperty;
    }

    public float getValue() {
        return internalValueProperty.getValue() == null ? 0 : internalValueProperty.get();
    }

    public void setValue(Float value){
        if(value!=null) {
            internalValueProperty.setValue(value);
        } else {
            setText("0");
        }
    }


}

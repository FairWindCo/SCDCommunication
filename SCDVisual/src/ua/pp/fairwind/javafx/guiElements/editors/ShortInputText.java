package ua.pp.fairwind.javafx.guiElements.editors;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;


public class ShortInputText extends TextField implements EventHandler<KeyEvent>, ChangeListener<String> {
    final private static String DIGITPATERN = "[-]{0,1}[0123456789]{1,}";
    final private static String EMPTYSTRING = "";
    private long maxValue = Long.MAX_VALUE;
    private long minValue = Long.MIN_VALUE;
    private SimpleIntegerProperty integerValueProperty = new SimpleIntegerProperty(0);

    public ShortInputText() {
        super();
        onInitialisation();
    }

    public ShortInputText(Property property) {
        super(property.getValue() != null ? property.getValue().toString() : "");
        this.integerValueProperty.bindBidirectional(property);
        onInitialisation();
    }

    public ShortInputText(Long arg0) {
        super(arg0.toString());
        onInitialisation();
    }

    public ShortInputText(Short arg0, short maxVal) {
        super(arg0 == null ? null : arg0.toString());
        this.maxValue = maxVal;
        onInitialisation();
    }

    public ShortInputText(short maxVal) {
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
            long v = maxValue;
            maxValue = minValue;
            minValue = v;
        }
    }

    private long parseString(String str) {
        if (str == null || EMPTYSTRING.equals(str)) {
            setText("0");
            return 0;
        }
        long intVal = Short.parseShort(str);
        if (intVal < minValue || intVal > maxValue) {
            return 0;
        }
        return intVal;
    }

    private void setIntVal(Short val) {
        if (val >= minValue && val <= maxValue) {
            setText(Integer.toString(val));
        } else {
            integerValueProperty.setValue(parseString(getText()));
        }
    }

    private void onInitialisation() {
        checkConstraints();
        this.addEventFilter(KeyEvent.KEY_TYPED, this);
        this.textProperty().addListener(this);
        integerValueProperty.addListener((arg0, arg1, newval) -> {
            if (newval != null)
                setIntVal(newval.shortValue());
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
        if (!"-0123456789".contains(keyevent.getCharacter())) {
            keyevent.consume();
        }
		/*
		if(maxSize>0 && this.getText()!=null && this.getText().length()>=maxSize){
			keyevent.consume();
		}
		*/

    }

    public long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(long maxValue) {
        this.maxValue = maxValue;
        checkConstraints();
    }

    public long getMinValue() {
        return minValue;
    }

    public void setMinValue(long minValue) {
        this.minValue = minValue;
        checkConstraints();
    }

    @Override
    public void changed(ObservableValue<? extends String> value, String olds, String newValue) {
        if (newValue == null || EMPTYSTRING.equals(newValue)) {
            setText("0");
            integerValueProperty.set(0);
            return;
        }
        if ("-".equals(newValue)) {
            if (minValue >= 0) {
                setText(EMPTYSTRING);
            }
        } else {
            short intVal = Short.parseShort(newValue);
            if (intVal < minValue || intVal > maxValue) {
                setText(olds);
                return;
            }
            integerValueProperty.set(intVal);
        }
    }

    public SimpleIntegerProperty getIntegerValueProperty() {
        return integerValueProperty;
    }

    public long getValue() {
        return integerValueProperty.getValue() == null ? 0 : integerValueProperty.get();
    }


}

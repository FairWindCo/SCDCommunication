package ua.pp.fairwind.javafx.guiElements.editors;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;


public class IntegerInputNullText extends TextField implements EventHandler<KeyEvent>, ChangeListener<String> {
    final private static String DIGITPATERN = "[-]{0,1}[0123456789]{1,}";
    final private static String EMPTYSTRING = "";
    private int maxValue = Integer.MAX_VALUE;
    private int minValue = Integer.MIN_VALUE;
    private SimpleIntegerProperty integerValueProperty = new SimpleIntegerProperty(0);
    private boolean empty = true;

    public IntegerInputNullText() {
        super();
        onInitialisation();
    }


    public IntegerInputNullText(Integer arg0) {
        super(arg0.toString());
        onInitialisation();
    }

    public IntegerInputNullText(Integer arg0, int maxVal) {
        super(arg0 == null ? null : arg0.toString());
        this.maxValue = maxVal;
        onInitialisation();
    }

    public IntegerInputNullText(int maxVal) {
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
        if (intVal < minValue || intVal > maxValue) {
            return 0;
        }
        return intVal;
    }

    private void setIntVal(int val) {
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
                setIntVal(newval.intValue());
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
        if (newValue == null || EMPTYSTRING.equals(newValue)) {
            setText(EMPTYSTRING);
            integerValueProperty.setValue(null);
            empty = true;
            return;
        }
        if ("-".equals(newValue)) {
            if (minValue >= 0) {
                setText(EMPTYSTRING);
                empty = true;
            }
        } else {
            int intVal = Integer.parseInt(newValue);
            if (intVal < minValue || intVal > maxValue) {
                setText(olds);
                return;
            }
            integerValueProperty.set(intVal);
            empty = false;
        }
    }

    public SimpleIntegerProperty getIntegerValueProperty() {
        return integerValueProperty;
    }

    public Integer getValue() {
        if (empty) return null;
        return integerValueProperty.getValue();
    }


}

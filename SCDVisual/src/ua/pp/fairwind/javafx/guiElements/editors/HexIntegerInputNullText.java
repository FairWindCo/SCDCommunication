package ua.pp.fairwind.javafx.guiElements.editors;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;


public class HexIntegerInputNullText extends TextField implements EventHandler<KeyEvent>, ChangeListener<String> {
    final private static String DIGITPATERN = "[0123456789AaBbCcDdEeFf]{1,}";
    final private static String EMPTYSTRING = "";
    private int maxValue = Integer.MAX_VALUE;
    private SimpleIntegerProperty integerValueProperty = new SimpleIntegerProperty(0);
    private boolean empty = true;

    public HexIntegerInputNullText() {
        super();
        onInitialisation();
    }


    public HexIntegerInputNullText(Integer arg0) {
        super(arg0.toString());
        onInitialisation();
    }

    public HexIntegerInputNullText(Integer arg0, int maxVal) {
        super(arg0 == null ? null : arg0.toString());
        this.maxValue = maxVal;
        onInitialisation();
    }

    public HexIntegerInputNullText(int maxVal) {
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


    private int parseString(String str) {
        if (str == null || EMPTYSTRING.equals(str)) {
            setText("0");
            return 0;
        }
        int intVal = Integer.parseUnsignedInt(str, 16);
        if (intVal > maxValue) {
            return maxValue;
        }
        return intVal;
    }

    private void setIntVal(int val) {
        if (val <= maxValue) {
            setText(Integer.toHexString(val));
        } else {
            integerValueProperty.setValue(parseString(getText()));
        }
    }

    private void onInitialisation() {
        this.addEventFilter(KeyEvent.KEY_TYPED, this);
        this.textProperty().addListener(this);
        integerValueProperty.addListener((arg0, arg1, newval) -> {
            if (newval != null)
                setIntVal(newval.intValue());
        });

    }


    @Override
    public void replaceSelection(String text) {
        if (text.matches(DIGITPATERN) || text.equals(EMPTYSTRING)) {
            super.replaceSelection(text);
        }
    }

    @Override
    public void replaceText(int arg0, int arg1, String text) {
        if (text.matches(DIGITPATERN) || text.equals(EMPTYSTRING)) {
            super.replaceText(arg0, arg1, text);
        }
    }

    @Override
    public void handle(KeyEvent keyevent) {
        if (!"0123456789AaBbCcDdEeFf".contains(keyevent.getCharacter())) {
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
    }



    @Override
    public void changed(ObservableValue<? extends String> value, String olds, String newValue) {
        if (newValue == null || EMPTYSTRING.equals(newValue)) {
            setText(EMPTYSTRING);
            integerValueProperty.setValue(null);
            empty = true;
            return;
        }

        int intVal = Integer.parseUnsignedInt(newValue,16);
        if (intVal > maxValue) {
                setText(olds);
                return;
        }
        integerValueProperty.set(intVal);
        empty = false;
    }

    public SimpleIntegerProperty getIntegerValueProperty() {
        return integerValueProperty;
    }

    public Integer getValue() {
        if (empty) return null;
        return integerValueProperty.getValue();
    }


}

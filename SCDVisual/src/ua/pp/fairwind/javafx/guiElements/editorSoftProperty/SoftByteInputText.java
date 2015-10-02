package ua.pp.fairwind.javafx.guiElements.editorSoftProperty;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import ua.pp.fairwind.communications.messagesystems.event.ValueChangeListener;
import ua.pp.fairwind.communications.propertyes.software.SoftByteProperty;
import ua.pp.fairwind.javafx.VisualControls;


public class SoftByteInputText extends TextField implements EventHandler<KeyEvent>,ChangeListener<String> {
	final private static String DIGITPATERN="[-]?[012]?[0123456789]{1,2}";
	final private static String EMPTYSTRING="";
	private byte maxValue=Byte.MAX_VALUE;
	private byte minValue=Byte.MIN_VALUE;
	final private SoftByteProperty property;


	public SoftByteInputText(SoftByteProperty property) {
		super(property.getValue() == null ? null : property.getValue().toString());
		this.property=property;
		onInitialisation();
	}

	public SoftByteInputText(SoftByteProperty property, byte minVal, byte maxVal) {
		super(property.getValue() == null ? null : property.getValue().toString());
		this.property=property;
		this.maxValue = maxVal;
		this.minValue = minVal;
		onInitialisation();
	}

	public SoftByteInputText(SoftByteProperty property, byte maxVal) {
		super(property.getValue() == null ? null : property.getValue().toString());
		this.property=property;
		this.maxValue = maxVal;
		onInitialisation();
	}

	private void checkConstraints(){
		if(minValue>maxValue){
			byte v=maxValue;
			maxValue=minValue;
			minValue=v;
		}
	}
	
	private Byte parseString(String str){
		if(str==null || EMPTYSTRING.equals(str)){
			setText("0");
			return 0;
		}
		Byte intVal=Byte.parseByte(str);
		if(intVal<minValue || intVal>maxValue){
			return 0;
		}
		return intVal;
	}

	private void setIntVal(Byte val){
		if(val>=minValue && val<=maxValue){
			setText(Long.toString(val));
		} else {
			property.setValue(parseString(getText()));
		}
	}

	ValueChangeListener<Byte> eventListener=event -> {
		if (event.getNewValue()!= null)
			VisualControls.executeInJavaFXThread(() ->
				setIntVal((Byte) event.getNewValue()));
	};


	private void onInitialisation(){
		checkConstraints();
		this.addEventFilter(KeyEvent.KEY_TYPED, this);
		this.textProperty().addListener(this);
		property.addChangeEventListener(eventListener);
	}
	
	@Override
	public void replaceSelection(String text) {
		if(text.matches(DIGITPATERN)||text.equals(EMPTYSTRING)){
			super.replaceSelection(text);
		}
	}

	
	
	@Override
	public void replaceText(int arg0, int arg1, String text) {
		if(text.equals("-")||text.matches(DIGITPATERN)||text.equals(EMPTYSTRING)){
			super.replaceText(arg0, arg1, text);
		}
	}
/*
	@Override
	public void replaceText(IndexRange arg0, String text) {
		if(text.matches(DIGITPATERN)||text.equals(EMPTYSTRING)){
			super.replaceText(arg0, text);
		}
	}/**/

	
	@Override
	public void handle(KeyEvent keyevent) {
		if(!"-0123456789".contains(keyevent.getCharacter())){
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

	public void setMaxValue(byte maxValue) {
		this.maxValue = maxValue;
		checkConstraints();
	}

	public long getMinValue() {
		return minValue;
	}

	public void setMinValue(byte minValue) {
		this.minValue = minValue;
		checkConstraints();
	}

	public static String getDigitpatern() {
		return DIGITPATERN;
	}

	public static String getEmptystring() {
		return EMPTYSTRING;
	}

	@Override
	public void changed(ObservableValue<? extends String> value, String olds,String newValue) {
		if(newValue==null || EMPTYSTRING.equals(newValue)){
			setText("0");
			property.setValue((byte)0);
			return;
		}
		if("-".equals(newValue)){
			if(minValue>=0){
				setText(EMPTYSTRING);
			}
		} else {
			Byte intVal = Byte.parseByte(newValue);
			if (intVal < minValue || intVal > maxValue) {
				setText(olds);
				return;
			}
			property.setValue(intVal);
		}
	}

	public SoftByteProperty getByteValueProperty() {
		return property;
	}
	
	public void destroy(){
		if(property!=null)property.removeChangeEventListener(eventListener);
	}

	@Override
	protected void finalize() throws Throwable {
		destroy();
		super.finalize();
	}
}

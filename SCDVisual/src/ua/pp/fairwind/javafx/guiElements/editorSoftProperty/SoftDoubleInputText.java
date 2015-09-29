package ua.pp.fairwind.javafx.guiElements.editorSoftProperty;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import ua.pp.fairwind.communications.messagesystems.event.ValueChangeListener;
import ua.pp.fairwind.communications.propertyes.software.SoftDoubleProperty;


public class SoftDoubleInputText extends TextField implements EventHandler<KeyEvent>,ChangeListener<String> {
	final private static String DIGITPATERN="[-]?[0123456789]*[.]?[0123456789]+";
	final private static String EMPTYSTRING="";
	private Double maxValue=Double.MAX_VALUE;
	private Double minValue=Double.MIN_VALUE;
	final private SoftDoubleProperty property;


	public SoftDoubleInputText(SoftDoubleProperty property) {
		super(property.getValue() == null ? null : property.getValue().toString());
		this.property=property;
		onInitialisation();
	}

	public SoftDoubleInputText(SoftDoubleProperty property, Double minVal, Double maxVal) {
		super(property.getValue() == null ? null : property.getValue().toString());
		this.property=property;
		this.maxValue = maxVal;
		this.minValue = minVal;
		onInitialisation();
	}

	public SoftDoubleInputText(SoftDoubleProperty property, Double maxVal) {
		super(property.getValue() == null ? null : property.getValue().toString());
		this.property=property;
		this.maxValue = maxVal;
		onInitialisation();
	}

	private void checkConstraints(){
		if(minValue>maxValue){
			Double v=maxValue;
			maxValue=minValue;
			minValue=v;
		}
	}
	
	private Double parseString(String str){
		if(str==null || EMPTYSTRING.equals(str)){
			setText("0");
			return 0d;
		}
		Double intVal=Double.parseDouble(str);
		if(intVal<minValue || intVal>maxValue){
			return 0d;
		}
		return intVal;
	}

	private void setIntVal(Double val){
		if(val>=minValue && val<=maxValue){
			setText(Double.toString(val));
		} else {
			property.setValue(parseString(getText()));
		}
	}

	ValueChangeListener<Double> eventListener=event -> {
		if (event.getNewValue()!= null)
			setIntVal((Double)event.getNewValue());
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

	
	
	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
		checkConstraints();
	}

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(double minValue) {
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
			property.setValue((double)0);
			return;
		}
		if("-".equals(newValue)){
			if(minValue>=0){
				setText(EMPTYSTRING);
			}
		} else {
			Double intVal = Double.parseDouble(newValue);
			if (intVal < minValue || intVal > maxValue) {
				setText(olds);
				return;
			}
			property.setValue(intVal);
		}
	}

	public SoftDoubleProperty getDoubleValueProperty() {
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

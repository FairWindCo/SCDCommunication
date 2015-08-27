package ua.pp.fairwind.communications.propertyes.software.stringlike;

import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.software.SoftStringProperty;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Wind on 28.07.2014.
 */
public class StringValuedPropertry extends SoftStringProperty {
    private List<String> correctValues;
    protected NumberValueConverter<?> converter;
    final private String defauleValue;
    final private String format;


    public StringValuedPropertry(String name, NumberValueConverter<?> converter, String defauleValue, String format,String... correctValues) {
        super(name);
        this.correctValues = Arrays.asList(correctValues);
        this.converter = converter;
        this.defauleValue = defauleValue;
        this.format = format;
    }

    public StringValuedPropertry(String name, String uuid, String description, MessageSubSystem centralSystem, String defauleValue, String format, NumberValueConverter<?> converter, String... correctValues) {
        super(name, uuid, description, centralSystem);
        this.defauleValue = defauleValue;
        this.format = format;
        this.converter = converter;
        this.correctValues = Arrays.asList(correctValues);
    }

    public StringValuedPropertry(String name, String uuid, String description, MessageSubSystem centralSystem, String value, String defauleValue, String format, NumberValueConverter<?> converter, String... correctValues) {
        super(name, uuid, description, centralSystem, value);
        this.defauleValue = defauleValue;
        this.format = format;
        this.converter = converter;
        this.correctValues = Arrays.asList(correctValues);
    }

    public StringValuedPropertry(String name,String description, NumberValueConverter<?> converter, String defauleValue, String format,String... correctValues) {
        this(name,null,description,null,null,defauleValue,format,converter,correctValues);
    }




    protected String readMaskedValue(String val){
        if(converter==null){
            return defauleValue!=null?defauleValue:"null";
        }
        return converter.getValue(val, format,defauleValue);
    }

    protected String checkValueForCorrect(String value){
        String result="null";
        if(value!=null && value.length()>0){
            if(correctValues!=null){
                boolean find=false;
                for(String val:correctValues){
                    if(value.equalsIgnoreCase(val)){
                        find=true;
                        if(value.contains("#")){
                            result=readMaskedValue(defauleValue);
                        } else {
                            result=val;
                        }
                        break;
                    }
                }
                if(!find){
                    result=readMaskedValue(value);
                }
            } else {
                result=readMaskedValue(value);
            }
        }
        return result;
    }

    protected boolean checkNumberPart(String value){
        return converter != null && converter.checkValue(value);
    }

    public boolean isCorrectValue(String value){
        boolean result=false;
        if(value!=null && value.length()>0){
            if(correctValues!=null){
                boolean find=false;
                for(String val:correctValues){
                    if(value.equalsIgnoreCase(val)){
                        find=true;
                        result=true;
                        break;
                    }
                }
                if(!find){
                    result=checkNumberPart(value);
                }
            } else {
                result=checkNumberPart(value);
            }
        }
        return result;
    }

    public String getDiapason(){
        StringBuilder build=new StringBuilder();
        if(correctValues!=null){
            for(String s:correctValues){
                if(s!=null){
                    if(s.contains("#") && converter!=null){
                        s=s+"["+converter.getMinValue()+" : "+converter.getMaxValue()+"]";
                    }
                    if(build.length()>0)build.append(" | ");
                    build.append(s);
                }
            }
        }
        return build.toString();
    }

    public void checkInternalValue(){
        String val=getValue();
        String newVal=checkValueForCorrect(val);
        if(val!=newVal && val!=null && !val.equals(newVal)){
            setValue(newVal);
        }
    }

    protected String restoreValueFromString(String buffer, int radix) throws NumberFormatException {
        if(getName()==null || buffer==null || buffer.length()==0){
            return null;
        }

        String[] parts=buffer.split("=");
        if(parts.length == 2){
            if(getName().equalsIgnoreCase(parts[0])){
                return parts[1];
            } else {
                throw new NumberFormatException("Incorrect Property Name");
            }
        } else {
            throw new NumberFormatException("Incorrect Property Format");
        }
    }

    public String prepareValueForRequest() {
        if(getName()!=null){
            StringBuilder build=new StringBuilder();
            build.append(getName());
            if("SCAI".equals(getName()) && getValue()!=null){
                build.append("=");
                build.append(getValue());
            }
            return build.toString();
        }
        return "null";
    }

    public String formStringValue(String format) throws NumberFormatException {
        if(getName()!=null){
            StringBuilder build=new StringBuilder();
            build.append(getName());
            build.append("=");
            if(getValue()!=null){
                build.append(formStringValue(format));
            } else {
                build.append("null");
            }
            return build.toString();
        }
        return "null=null";
    }

    public List<String> getCorrectValues() {
        return correctValues;
    }

    public String getDefauleValue() {
        return defauleValue;
    }

    public NumberValueConverter<?> getConverter() {
        return converter;
    }

    public void setConverter(NumberValueConverter<?> converter) {
        this.converter = converter;
    }

    public String getFormat() {
        return format;
    }
}

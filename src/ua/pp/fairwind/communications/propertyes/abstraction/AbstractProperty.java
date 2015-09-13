package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.abstractions.SystemEllement;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.propertyTrunsactions.OPERATION_TYPE;
import ua.pp.fairwind.communications.propertyes.abstraction.propertyTrunsactions.OperationTrunsactions;
import ua.pp.fairwind.communications.propertyes.abstraction.propertyTrunsactions.OperationTrunsactionsSingle;
import ua.pp.fairwind.communications.propertyes.event.ElementEventListener;
import ua.pp.fairwind.communications.propertyes.event.EventType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by FairWindCo on 07.07.2015
 */
public abstract class AbstractProperty extends SystemEllement{
    final public static String PROPERTY_READ_TIME_OUT_ADDON="PROPERTY_READ_TIME_OUT_ADDON";
    final public static String PROPERTY_PAUSE_BEFORE_READ_ADDON="PROPERTY_PAUSE_BEFORE_READ_ADDON";
    final public static String PROPERTY_PAUSE_BEFORE_WRITE_ADDON="PROPERTY_PAUSE_BEFORE_WRITE_ADDON";
    final public static String PROPERTY_READ_TIME_OUT="PROPERTY_READ_TIME_OUT";
    final public static String PROPERTY_PAUSE_BEFORE_READ="PROPERTY_PAUSE_BEFORE_READ";
    final public static String PROPERTY_PAUSE_BEFORE_WRITE="PROPERTY_PAUSE_BEFORE_WRITE";
    final public static String TIMER="TIMER";
    final public static String TIMERS="TIMERS";
    private AbstractProperty bindedForReadPoperty;
    private AbstractProperty bindedForWritePoperty;
    private String formatForWrite=null;
    private int radixForWrite=10;
    private int positionForWrite=0;
    private int lengthForWrite=0;
    private boolean convertBoolToBinaryForWrite=false;
    protected volatile long dataLifeTime=-1;
    volatile private OperationTrunsactions requestTrunsaction=new OperationTrunsactionsSingle();

    protected final Map<String,Object> additionalParameters=new ConcurrentHashMap<>();



    protected AbstractProperty(String name, String uuid, String description,MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public void readValueRequest(){
        fireEvent(EventType.NEED_READ_VALUE, null);
    }

    public void writeValueRequest(){
        fireEvent(EventType.NEED_WRITE_VALUE, null);
    }

    //Внутренний прослушиватель событий об изменении занчения другого свойства.
    //Используется для реализации функции связывания.
    final private ElementEventListener elementevent=(element,typeEvent,params)->{
        if(typeEvent==EventType.ELEMENT_CHANGE && params!=null){
            reciveValueFromBindingRead(null, params);
        }
    };




    //Внутренний метод для связывания по чтению с другим методом
    protected void bindPropertyForRead(AbstractProperty property){
        if(property!=null){
            synchronized (elementevent) {
                if(bindedForReadPoperty!=null) unbindPropertyForRead();
                this.bindedForReadPoperty = property;
                reciveValueFromBindingRead(property, null);
                property.addEventListener(elementevent);
            }
        }
    }
    //Внутренний метод для связывания по записи с другим методом
    protected void bindPropertyForWrite(AbstractProperty property, String formatForWrite,int radixForWrite,int positionForWrite,int lengthForWrite,boolean convertBoolToBinaryForWrite){
        if(property!=null){
            synchronized (elementevent) {
                if(bindedForWritePoperty!=null) unbindPropertyForWrite();
                this.formatForWrite=formatForWrite;
                this.radixForWrite=radixForWrite;
                this.positionForWrite=positionForWrite;
                this.lengthForWrite=lengthForWrite;
                this.convertBoolToBinaryForWrite=convertBoolToBinaryForWrite;
                this.bindedForWritePoperty = property;
            }
        }
    }


    //Метод чтения значений для биндинга по зенвчению
    //Если передано значение то анализируется она (парметр newValue)
    //Если значения нет то пытаемся импользовать свойство
    //Метод позволяющий непосредственно осуществить соединение по записи
    //Необходим в том случае если для связывания необходимо передать некоторые параметр
    //Например такой как формат значения длс строков параметров.
    abstract protected void reciveValueFromBindingWrite(final AbstractProperty property,final Object valueForWtite, String formatForWrite,int radixForWrite,int positionForWrite,int lengthForWrite,boolean convertBoolToBinaryForWrite);
    abstract protected void reciveValueFromBindingRead(final AbstractProperty property,final Object valueForWtite);

    public boolean isValidProperty(){
        return true;
    }


    public long getDataLifeTime() {
        return dataLifeTime;
    }

    public void setDataLifeTime(long dataLifeTime) {
        this.dataLifeTime = dataLifeTime;
    }

    protected void writeBinding(Object valueForWtite){
        synchronized (elementevent) {
            if (bindedForWritePoperty != null) {
                bindedForWritePoperty.reciveValueFromBindingWrite(this, valueForWtite, formatForWrite, radixForWrite, positionForWrite, lengthForWrite,convertBoolToBinaryForWrite);
            }
        }
    }


    //Разрыв связывания по чтению
    protected void unbindPropertyForRead(){
        synchronized (elementevent) {
            if(bindedForReadPoperty!=null){
                bindedForReadPoperty.removeEventListener(elementevent);
                bindedForReadPoperty=null;
            }
        }
    }
    //Разрыв связывания по записи
    protected void unbindPropertyForWrite(){
        synchronized (elementevent) {
            if(bindedForWritePoperty!=null){
                bindedForWritePoperty=null;
                formatForWrite=null;
                radixForWrite=10;
                positionForWrite=0;
                lengthForWrite=0;
                convertBoolToBinaryForWrite=false;
            }
        }
    }

    public void setAdditionalInfo(String paramsName,Object value){
        if(paramsName==null)return;
        if(value==null) {
            additionalParameters.remove(paramsName);
        } else {
            additionalParameters.put(paramsName, value);
        }
    }

    public Object getAdditionalInfo(String paramsName){
        return additionalParameters.get(paramsName);
    }


    public long getPropertyPauseBeforeReadAddon(){
        if(additionalParameters.get(PROPERTY_PAUSE_BEFORE_READ_ADDON)==null) return 0;
        return (long)additionalParameters.get(PROPERTY_PAUSE_BEFORE_READ_ADDON);
    }

    public long getPropertyPauseBeforeRead(){
        if(additionalParameters.get(PROPERTY_PAUSE_BEFORE_READ)==null) return 0;
        return (long)additionalParameters.get(PROPERTY_PAUSE_BEFORE_READ);
    }

    public long getPropertyTimeOutReadAddon(){
        if(additionalParameters.get(PROPERTY_READ_TIME_OUT_ADDON)==null) return 0;
        return (long)additionalParameters.get(PROPERTY_READ_TIME_OUT_ADDON);
    }

    public long getPropertyTimeOutRead(){
        if(additionalParameters.get(PROPERTY_READ_TIME_OUT)==null) return 0;
        return (long)additionalParameters.get(PROPERTY_READ_TIME_OUT);
    }

    public long getPropertyPauseBeforeWriteAddon(){
        if(additionalParameters.get(PROPERTY_PAUSE_BEFORE_WRITE_ADDON)==null) return 0;
        return (long)additionalParameters.get(PROPERTY_PAUSE_BEFORE_WRITE_ADDON);
    }

    public long getPropertyPauseBeforeWrite(){
        if(additionalParameters.get(PROPERTY_PAUSE_BEFORE_WRITE)==null) return 0;
        return (long)additionalParameters.get(PROPERTY_PAUSE_BEFORE_WRITE);
    }

    //
    public void setPropertyPauseBeforeReadAddon(long value){
        additionalParameters.put(PROPERTY_PAUSE_BEFORE_READ_ADDON, value);
    }

    public void setPropertyPauseBeforeRead(long value){
        additionalParameters.put(PROPERTY_PAUSE_BEFORE_READ, value);
    }

    public void setPropertyTimeOutReadAddon(long value){
        additionalParameters.put(PROPERTY_READ_TIME_OUT_ADDON, value);
    }

    public void setPropertyTimeOutRead(long value){
        additionalParameters.put(PROPERTY_READ_TIME_OUT, value);
    }

    public void setPropertyPauseBeforeWriteAddon(long value){
        additionalParameters.put(PROPERTY_PAUSE_BEFORE_WRITE_ADDON, value);
    }

    public void setPropertyPauseBeforeWrite(long value){
        additionalParameters.put(PROPERTY_PAUSE_BEFORE_WRITE, value);
    }

    public boolean isMultiRequestEnabled(OPERATION_TYPE type){
        return requestTrunsaction.isMultiRequestEnabled(type);
    }

    public void setMultiRequestEnabled(OPERATION_TYPE type,boolean state){
        requestTrunsaction.setMultiRequestEnabled(type,state);
    }

    public boolean isRequestActive(OPERATION_TYPE type){
        return requestTrunsaction.isRequestActive(type);
    }

    public boolean startRequest(OPERATION_TYPE type){
        return requestTrunsaction.startRequest(type);
    }

    public void endRequest(OPERATION_TYPE type){
        requestTrunsaction.endRequest(type);
    }

    public void setRequestTrunsaction(OperationTrunsactions requestTrunsaction) {
        this.requestTrunsaction = requestTrunsaction;
    }
}

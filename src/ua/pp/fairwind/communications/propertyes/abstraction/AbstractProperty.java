package ua.pp.fairwind.communications.propertyes.abstraction;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.abstractions.SystemEllement;
import ua.pp.fairwind.communications.propertyes.event.ElementEventListener;
import ua.pp.fairwind.communications.propertyes.event.EventType;

/**
 * Created by FairWindCo on 07.07.2015
 */
public abstract class AbstractProperty extends SystemEllement{
    private AbstractProperty bindedForReadPoperty;
    private AbstractProperty bindedForWritePoperty;
    private String formatForWrite=null;
    private int radixForWrite=10;
    private int positionForWrite=0;
    private int lengthForWrite=0;
    private boolean convertBoolToBinaryForWrite=false;
    protected volatile long dataLifeTime=-1;




    public AbstractProperty(String name, String uuid, String description,MessageSubSystem centralSystem) {
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

}

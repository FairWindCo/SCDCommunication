package ua.pp.fairwind.communications.propertyes.binding;

import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.abstractions.SystemEllement;
import ua.pp.fairwind.communications.internatianalisation.I18N;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.event.ElementEventListener;
import ua.pp.fairwind.communications.propertyes.event.EventType;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Сергей on 16.07.2015.
 */
public class PropertyBindingElement<FROM extends AbstractProperty,TO extends AbstractProperty> extends SystemEllement implements ElementEventListener{
    private final FROM readingProperty;
    private final TO writingProperty;
    private final AtomicBoolean readbinded=new AtomicBoolean(false);
    private final AtomicBoolean writebinded=new AtomicBoolean(false);
    private final PropertyConvertor<FROM,TO> readConvertor;
    private final PropertyConvertor<TO,FROM> writeConvertor;


    public PropertyBindingElement(FROM readingProperty, TO writingProperty, PropertyConvertor<FROM, TO> readConvertor, PropertyConvertor<TO, FROM> writeConvertor, MessageSubSystem centralSystem) {
        super(String.format(I18N.getLocalizedString("binding.name"),readingProperty!=null?readingProperty.getName():"",writingProperty!=null?writingProperty.getName():""), centralSystem);
        if(readingProperty==null || writingProperty==null || readConvertor==null || writeConvertor==null) throw new IllegalArgumentException(I18N.getLocalizedString("binding.create.error"));
        this.readingProperty = readingProperty;
        this.writingProperty = writingProperty;
        this.readConvertor=readConvertor;
        this.writeConvertor=writeConvertor;
    }

    public PropertyBindingElement(FROM readingProperty, TO writingProperty, PropertyConvertor<FROM, TO> readConvertor, PropertyConvertor<TO, FROM> writeConvertor) {
        super(String.format(I18N.getLocalizedString("binding.name"),readingProperty!=null?readingProperty.getName():"",writingProperty!=null?writingProperty.getName():""), null);
        if(readingProperty==null || writingProperty==null || readConvertor==null || writeConvertor==null) throw new IllegalArgumentException(I18N.getLocalizedString("binding.create.error"));
        this.readingProperty = readingProperty;
        this.writingProperty = writingProperty;
        this.readConvertor=readConvertor;
        this.writeConvertor=writeConvertor;
    }

    public void bindRead(){
        if(readbinded.compareAndSet(false,true)) {
            readingProperty.addEventListener(this);
            elementEvent(readingProperty, EventType.ELEMENT_CHANGE, null);
        }
    }

    public void unbindRead(){
        if(readbinded.compareAndSet(true,false)) {
            readingProperty.removeEventListener(this);
        }
    }

    public void bindWrite(){
        if(writebinded.compareAndSet(false,true)) {
            writingProperty.addEventListener(this);
            elementEvent(writingProperty,EventType.ELEMENT_CHANGE,null);
        }
    }

    public void unbindWrite(){
        if(writebinded.compareAndSet(true,false)) {
            readingProperty.removeEventListener(this);
        }
    }

    public void bind(){
        bindRead();
        bindWrite();
    }

    public void unbind(){
        unbindRead();
        unbindWrite();
    }




    @Override
    public void elementEvent(ElementInterface element, EventType typeEvent, Object params) {
           if((typeEvent==EventType.ELEMENT_CHANGE || typeEvent==EventType.ELEMENT_CHANGE_FROM_HARDWARE)&& element!=null){
               System.out.printf("EVENT FROM %s\n",element);
               try {
                   boolean res=false;
                   if(element==readingProperty){
                       res=readConvertor.convertValue(readingProperty, writingProperty);
                   }
                   if(element==writingProperty){
                       res=writeConvertor.convertValue(writingProperty, readingProperty);
                   }
                   if(!res){
                       fireEvent(EventType.PARSE_ERROR,String.format(I18N.getLocalizedString("binding.translate.error"), readingProperty.getName(), writingProperty.getName()));
                   }
               } catch (BindingConvertExceptions ex){
                   fireEvent(EventType.ERROR,ex.getLocalizedMessage());
               }

           }
    }

    @Override
    public void destroy() {
        readingProperty.removeEventListener(this);
        writingProperty.removeEventListener(this);
        super.destroy();
    }
}

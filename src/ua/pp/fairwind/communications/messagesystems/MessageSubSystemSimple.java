package ua.pp.fairwind.communications.messagesystems;

import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.propertyes.event.ElementEventListener;
import ua.pp.fairwind.communications.propertyes.event.EventType;
import ua.pp.fairwind.communications.propertyes.event.ValueChangeEvent;
import ua.pp.fairwind.communications.propertyes.event.ValueChangeListener;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Сергей on 27.08.2015.
 */
public class MessageSubSystemSimple implements MessageSubSystem{
    protected final CopyOnWriteArrayList<ElementEventListener> eventDispatcher=new CopyOnWriteArrayList<>();
    protected final CopyOnWriteArrayList<ValueChangeListener<?>> calueEventDispatcher=new CopyOnWriteArrayList<>();

    protected void fireEventExecute(ElementInterface element,EventType type,Object param){
        for(ElementEventListener listener:eventDispatcher){
            listener.elementEvent(element, type, param);
        }
    }

    protected void fireEventExecute(final ValueChangeEvent<?> event){
        for(ValueChangeListener listener:calueEventDispatcher){
            listener.valueChange(event);
        }
    }


    @Override
    public void fireEvent(ElementInterface element,EventType type,Object param){
        fireEventExecute(element,type,param);
    }

    @Override
    public void fireEvent(final ValueChangeEvent<?> event){
        fireEventExecute(event);
    }

    @Override
    public void addEventListener(ElementEventListener listener) {
        if(listener!=null){
            eventDispatcher.add(listener);
        }
    }
    @Override
    public void removeEventListener(ElementEventListener listener) {
        if(listener!=null){
            eventDispatcher.remove(listener);
        }
    }

    @Override
    public void clear() {
        eventDispatcher.clear();
        calueEventDispatcher.clear();
    }
    @Override
    public void destroy() {
        eventDispatcher.clear();
        calueEventDispatcher.clear();
    }




    public void addChangeEventListener(ValueChangeListener<?> listener){
        calueEventDispatcher.add(listener);
    }



    public void removeChangeEventListener(ValueChangeListener<?> listener){
        calueEventDispatcher.remove(listener);
    }

    @Override
    public void destroyService() {

    }

    @Override
    public MessageSubSystem getNewChild() {
        return new MessageSubSystemSimple();
    }
}

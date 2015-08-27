package ua.pp.fairwind.communications.messagesystems;

import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.propertyes.event.ElementEventListener;
import ua.pp.fairwind.communications.propertyes.event.EventType;
import ua.pp.fairwind.communications.propertyes.event.ValueChangeEvent;
import ua.pp.fairwind.communications.propertyes.event.ValueChangeListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Сергей on 27.08.2015.
 */
public class MessageSubSystemMultiDipatch extends MessageSubSystemSimple{
    static private final ExecutorService service= Executors.newCachedThreadPool();

    @Override
    protected void fireEventExecute(ElementInterface element,EventType type,Object param){
        for(ElementEventListener listener:eventDispatcher){
            service.submit(() -> {
                try {
                    System.out.println("EVENT "+type+" FROM "+element +" PARAM: "+param);
                    listener.elementEvent(element, type, param);
                }catch (Exception ex){
                    System.err.println(ex.toString());
                }
            });
        }
    }

    protected void fireEventExecute(final ValueChangeEvent<?> event){
        for(ValueChangeListener listener:calueEventDispatcher){
            service.submit(() -> {
                try {
                    System.out.println("EVENT FROM" + event);
                    listener.valueChange(event);
                } catch (Exception ex) {
                    System.err.println(ex.toString());
                }
            });

        }
    }

    @Override
    public void destroy() {
        super.destroy();
        service.shutdownNow();
    }
}

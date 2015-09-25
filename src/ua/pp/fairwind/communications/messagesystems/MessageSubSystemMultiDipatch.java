package ua.pp.fairwind.communications.messagesystems;

import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.messagesystems.event.ValueChangeEvent;
import ua.pp.fairwind.communications.messagesystems.event.ValueChangeListener;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Сергей on 27.08.2015.
 */
public class MessageSubSystemMultiDipatch extends MessageSubSystemSimple{
    static private final ExecutorService service= Executors.newCachedThreadPool();



    @Override
    public void fireEvent(Event event) {
        for(ListenerHolder listener:eventDispatcher){
            service.submit(() -> {
                try {
                    //System.out.println("EVENT "+type+" FROM "+element +" PARAM: "+param);
                    listener.executeEvent(event);
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
                    //System.out.println("EVENT FROM" + event);
                    listener.valueChange(event);
                } catch (Exception ex) {
                    System.err.println(ex.toString());
                }
            });

        }
    }


    @Override
    public void destroyService() {
        service.shutdownNow();
    }

    public static void destroyAllService() {
        service.shutdownNow();
    }

    @Override
    public MessageSubSystem getNewChild(UUID requestedElement) {
        return new MessageSubSystemMultiDipatch();
    }

}

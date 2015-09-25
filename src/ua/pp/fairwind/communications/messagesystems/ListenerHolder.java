package ua.pp.fairwind.communications.messagesystems;

import ua.pp.fairwind.communications.messagesystems.event.ElementEventListener;
import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.messagesystems.event.EventType;

import java.util.*;

/**
 * Created by Сергей on 25.09.2015.
 */
public class ListenerHolder {
    private final Set<EventType> filter;
    private final ElementEventListener listener;
    private final UUID ignore;
    private final Object processingParameter;

    public ListenerHolder(ElementEventListener listener,UUID ignore,EventType... interestedEventTypes) {
        if(interestedEventTypes==null||interestedEventTypes.length==0){
            this.filter = Collections.unmodifiableSet(EnumSet.allOf(EventType.class));
        } else {
            if(interestedEventTypes.length==1) {
                this.filter = Collections.unmodifiableSet(EnumSet.of(interestedEventTypes[0]));
            } else {
                Collection<EventType> sets= Arrays.asList(interestedEventTypes);
                this.filter = Collections.unmodifiableSet(EnumSet.copyOf(sets));
            }
        }
        if(listener==null)throw new IllegalArgumentException("LISTENER CAN`T BE NULL!");
        this.listener = listener;
        this.ignore=ignore;
        this.processingParameter=null;
    }

    public ListenerHolder(ElementEventListener listener,UUID ignore,Object params,EventType... interestedEventTypes) {
        if(interestedEventTypes==null||interestedEventTypes.length==0){
            this.filter = Collections.unmodifiableSet(EnumSet.allOf(EventType.class));
        } else {
            if(interestedEventTypes.length==1) {
                this.filter = Collections.unmodifiableSet(EnumSet.of(interestedEventTypes[0]));
            } else {
                Collection<EventType> sets= Arrays.asList(interestedEventTypes);
                this.filter = Collections.unmodifiableSet(EnumSet.copyOf(sets));
            }
        }
        if(listener==null)throw new IllegalArgumentException("LISTENER CAN`T BE NULL!");
        this.listener = listener;
        this.ignore=ignore;
        this.processingParameter=params;
    }

    private boolean testUUID(Event event){
        if(ignore==null)return true;
        UUID testUuid=event.sourceElementUUID;
        if(ignore.equals(testUuid))return false;
        if(event.getParent()==null) return true;
        return testUUID(event.getParent());
    }

    public void executeEvent(Event event){
        if(event!=null&&event.getTypeEvent()!=null){
            if(filter.contains(event.getTypeEvent())){
                if(testUUID(event)){
                    listener.elementEvent(event,processingParameter);
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListenerHolder that = (ListenerHolder) o;

        return listener.equals(that.listener);

    }

    @Override
    public int hashCode() {
        return listener.hashCode();
    }

    public ElementEventListener getListener() {
        return listener;
    }
}

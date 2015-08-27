package ua.pp.fairwind.communications.timeaction;

import ua.pp.fairwind.communications.propertyes.AbsractCommandProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Сергей on 27.08.2015.
 */
public class PropertyTimer extends TimerTask{
    private final static Timer timerService=new Timer(true);
    private final TIMER_ACTION timerAction;
    private final long period;
    private final AbstractProperty property;
    private boolean isRead=false;

    synchronized public static PropertyTimer[] getTimersForProperty(AbstractProperty property){
        if(property==null)return null;
        PropertyTimer oneTimer=(PropertyTimer)property.getAdditionalInfo(AbstractProperty.TIMER);
        if(oneTimer!=null)return new PropertyTimer[]{oneTimer};
        PropertyTimerHolder holder = (PropertyTimerHolder) property.getAdditionalInfo(AbstractProperty.TIMERS);
        if(holder!=null)return holder.getTimers();
        return null;
    }

    public static PropertyTimer createPropertyTimer(AbstractProperty property, TIMER_ACTION action,long period){
        if(property==null)return null;
        if(action==TIMER_ACTION.VALIDATE &&!(property instanceof ValueProperty<?>))return null;
        PropertyTimer propertyTimer=new PropertyTimer(action,period,property);
        timerService.scheduleAtFixedRate(propertyTimer, 0, period);
        synchronized (property) {
            PropertyTimerHolder holder = (PropertyTimerHolder) property.getAdditionalInfo(AbstractProperty.TIMERS);
            if (holder == null) {
                PropertyTimer oldTimer=(PropertyTimer)property.getAdditionalInfo(AbstractProperty.TIMER);
                if(oldTimer!=null){
                   holder=new PropertyTimerHolder();
                    holder.addPropertyTimer(oldTimer);
                    holder.addPropertyTimer(propertyTimer);
                    property.setAdditionalInfo(AbstractProperty.TIMER, null);
                    property.setAdditionalInfo(AbstractProperty.TIMERS,holder);
                } else {
                    property.setAdditionalInfo(AbstractProperty.TIMER, propertyTimer);
                }
            } else {
                holder.addPropertyTimer(propertyTimer);
            }
            property.getAdditionalInfo(AbstractProperty.TIMER);
            property.getAdditionalInfo(AbstractProperty.TIMERS);
        }
        return propertyTimer;
    }

    public PropertyTimer(TIMER_ACTION timerAction, long period, AbstractProperty property) {
        this.timerAction = timerAction;
        this.period = period;
        this.property = property;
    }

    @Override
    public void run() {
        switch (timerAction) {
            case EXECUTE:{
                ((AbsractCommandProperty)property).activate();
            }
            case WRITE:
                property.writeValueRequest();
            case READ: {
                property.readValueRequest();
            }
            case WRITE_READ:{
                if(!isRead){
                    isRead=true;
                    property.readValueRequest();
                } else {
                    isRead=false;
                    property.writeValueRequest();
                }
            }
            case VALIDATE:{
                ((ValueProperty<?>)property).checkValide();
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        destroy();
        super.finalize();
    }

    public void destroy(){
        if(cancel()){
            synchronized (property){
                PropertyTimer oneTimer=(PropertyTimer)property.getAdditionalInfo(AbstractProperty.TIMER);
                if(oneTimer==null){
                    PropertyTimerHolder holder = (PropertyTimerHolder) property.getAdditionalInfo(AbstractProperty.TIMERS);
                    if (holder != null) {
                        holder.removePropertyTimer(this);
                    }
                } else {
                    property.setAdditionalInfo(AbstractProperty.TIMER, null);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "TIMER " +timerAction +" period=" + period +" ms";
    }
}

package ua.pp.fairwind.communications.timeaction;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Сергей on 27.08.2015.
 */
public class PropertyTimerHolder {
    private final CopyOnWriteArrayList<PropertyTimer> timerHolder=new CopyOnWriteArrayList<>();

    public void addPropertyTimer(PropertyTimer timer){
        timerHolder.add(timer);
    }

    public void removePropertyTimer(PropertyTimer timer){
        timerHolder.remove(timer);
    }

    public void removePropertyTimer(int timerIndex){
        timerHolder.remove(timerIndex);
    }

    public PropertyTimer[] getTimers(){
        return timerHolder.toArray(new PropertyTimer[timerHolder.size()]);
    }

    public void destroy(){
        timerHolder.stream().forEach(timer->timer.cancel());
        timerHolder.clear();
    }

    @Override
    protected void finalize() throws Throwable {
        destroy();
        super.finalize();
    }
}

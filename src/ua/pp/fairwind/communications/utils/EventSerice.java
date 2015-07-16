package ua.pp.fairwind.communications.utils;

import java.util.concurrent.*;

/**
 * Created by Сергей on 04.09.2014.
 */
public enum EventSerice {
    PROCESSOR;

    EventSerice(){
        pool=createPool(16);
    }


    private final ScheduledThreadPoolExecutor pool;
    private final ConcurrentHashMap<String,Object> runningProcess=new ConcurrentHashMap<>();
    private volatile boolean enable=true;
    private volatile boolean isDestroy=false;


    private ScheduledThreadPoolExecutor createPool(int threadCount){
        ScheduledThreadPoolExecutor pool=new ScheduledThreadPoolExecutor(threadCount);
        pool.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
        pool.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        pool.setRemoveOnCancelPolicy(true);
        return pool;
    }

    public void runAction(WorkWithPollAccess... actions){
        if(actions!=null && !isDestroy) {
            for(WorkWithPollAccess action:actions) {
                pool.submit(() -> action.process(pool));
            }
        }
    }

    public void runActionWork(Work... actions){
        if(actions!=null && !isDestroy) {
            for(Work action:actions) {
                pool.submit(action::action);
            }
        }
    }

    public void runAction(Runnable... actions){
        if(actions!=null && !isDestroy) {
            for(Runnable action:actions) {
                pool.submit(action);
            }
        }
    }

    public void runAction(Callable... actions){
        if(actions!=null && !isDestroy) {
            for(Callable action:actions) {
                pool.submit(action);
            }
        }
    }

    synchronized public void scheduleActionWithPoolAccess(final String id,WorkWithPollAccess action,long pause,long period,TimeUnit timeUnit){
        if(action==null || isDestroy) return;
        Object threads=runningProcess.get(id);
        if(threads==null){
            ScheduledFuture<?> funutre=pool.scheduleWithFixedDelay(() -> {
                if(EventSerice.this.enable)action.process(EventSerice.this.pool);
            }, 1, period, timeUnit);
            runningProcess.put(id,funutre);
        } else {
            CopyOnWriteArrayList<Future> list;
            if (threads instanceof Future) {
                list=new CopyOnWriteArrayList<>();
                list.add((Future) threads);
            } else if(threads instanceof CopyOnWriteArrayList){
                list=(CopyOnWriteArrayList)threads;
                runningProcess.put(id,list);
            } else {
                throw new IllegalArgumentException("Incorect argument in future list");
            }
            ScheduledFuture<?> funutre=pool.scheduleWithFixedDelay(() -> {
                if(EventSerice.this.enable)action.process(EventSerice.this.pool);
            }, pause, period, timeUnit);
            list.add(funutre);
        }
    }

    synchronized public void scheduleActionWithPoolAccess(final String id,long pause,long period,TimeUnit timeUnit,WorkWithPollAccess... actions){
        if(actions==null || isDestroy) return;
        Object threads=runningProcess.get(id);
        CopyOnWriteArrayList<Future> list;
        if(threads!=null){
            if (threads instanceof Future) {
                list=new CopyOnWriteArrayList<>();
                list.add((Future) threads);
                runningProcess.put(id,list);
            } else if(threads instanceof CopyOnWriteArrayList){
                list=(CopyOnWriteArrayList)threads;
            } else {
                throw new IllegalArgumentException("Incorect argument in future list");
            }
        } else {
            list = new CopyOnWriteArrayList<>();
        }

        for(WorkWithPollAccess action:actions) {

            ScheduledFuture<?> funutre = pool.scheduleWithFixedDelay/*scheduleAtFixedRate*/(() -> {
                if (EventSerice.this.enable)
                    action.process(EventSerice.this.pool);
            }, pause, period, timeUnit);
            list.add(funutre);
        }
    }

    synchronized public void scheduleAction(final String id,Work action,long pause,long period,TimeUnit timeUnit){
        if(action==null || isDestroy) return;
        Object threads=runningProcess.get(id);
        if(threads==null){
            ScheduledFuture<?> funutre=pool.scheduleWithFixedDelay(() -> {
                if(EventSerice.this.enable)action.action();
            }, 1, period, timeUnit);
            runningProcess.put(id,funutre);
        } else {
            CopyOnWriteArrayList<Future> list;
            if (threads instanceof Future) {
                list=new CopyOnWriteArrayList<>();
                list.add((Future) threads);
            } else if(threads instanceof CopyOnWriteArrayList){
                list=(CopyOnWriteArrayList)threads;
                runningProcess.put(id,list);
            } else {
                throw new IllegalArgumentException("Incorect argument in future list");
            }
            ScheduledFuture<?> funutre=pool.scheduleWithFixedDelay(() -> {
                if(EventSerice.this.enable)action.action();
            }, pause, period, timeUnit);
            list.add(funutre);
        }
    }

    synchronized public void scheduleAction(final String id,long pause,long period,TimeUnit timeUnit,Work... actions){
        if(actions==null || isDestroy) return;
        Object threads=runningProcess.get(id);
        CopyOnWriteArrayList<Future> list;
        if(threads!=null){
            if (threads instanceof Future) {
                list=new CopyOnWriteArrayList<>();
                list.add((Future) threads);
                runningProcess.put(id,list);
            } else if(threads instanceof CopyOnWriteArrayList){
                list=(CopyOnWriteArrayList)threads;
            } else {
                throw new IllegalArgumentException("Incorect argument in future list");
            }
        } else {
            list = new CopyOnWriteArrayList<>();
        }

        for(Work action:actions) {
            ScheduledFuture<?> funutre = pool.scheduleWithFixedDelay(() -> {
                if (EventSerice.this.enable) action.action();
            }, pause, period, timeUnit);
            list.add(funutre);
        }
    }

    synchronized public void scheduleAction(final String id,Runnable action,long pause,long period,TimeUnit timeUnit){
        if(action==null || isDestroy) return;
        Object threads=runningProcess.get(id);
        if(threads==null){
            ScheduledFuture<?> funutre=pool.scheduleWithFixedDelay(action, pause, period, timeUnit);
            runningProcess.put(id,funutre);
        } else {
            CopyOnWriteArrayList<Future> list;
            if (threads instanceof Future) {
                list=new CopyOnWriteArrayList<>();
                list.add((Future) threads);
            } else if(threads instanceof CopyOnWriteArrayList){
                list=(CopyOnWriteArrayList)threads;
                runningProcess.put(id,list);
            } else {
                throw new IllegalArgumentException("Incorect argument in future list");
            }
            ScheduledFuture<?> funutre=pool.scheduleWithFixedDelay(action, 1, period, timeUnit);
            list.add(funutre);
        }
    }

    synchronized public void scheduleAction(final String id,long pause,long period,TimeUnit timeUnit,Runnable... actions){
        if(actions==null || isDestroy) return;
        Object threads=runningProcess.get(id);
        CopyOnWriteArrayList<Future> list;
        if(threads!=null){
            if (threads instanceof Future) {
                list=new CopyOnWriteArrayList<>();
                list.add((Future) threads);
                runningProcess.put(id,list);
            } else if(threads instanceof CopyOnWriteArrayList){
                list=(CopyOnWriteArrayList)threads;
            } else {
                throw new IllegalArgumentException("Incorect argument in future list");
            }
        } else {
            list = new CopyOnWriteArrayList<>();
        }
        for(Runnable action:actions) {
            ScheduledFuture<?> funutre = pool.scheduleWithFixedDelay(action, pause, period, timeUnit);
            list.add(funutre);
        }
    }

    synchronized public void scheduleAction(final String id,Callable action,long pause,TimeUnit timeUnit){
        if(action==null || isDestroy) return;
        Object threads=runningProcess.get(id);
        if(threads==null){
            ScheduledFuture<?> funutre=pool.schedule(action, pause, timeUnit);
            runningProcess.put(id,funutre);
        } else {
            CopyOnWriteArrayList<Future> list;
            if (threads instanceof Future) {
                list=new CopyOnWriteArrayList<>();
                list.add((Future) threads);
            } else if(threads instanceof CopyOnWriteArrayList){
                list=(CopyOnWriteArrayList)threads;
                runningProcess.put(id,list);
            } else {
                throw new IllegalArgumentException("Incorect argument in future list");
            }
            ScheduledFuture<?> funutre=pool.schedule(action, pause, timeUnit);
            list.add(funutre);
        }
    }

    synchronized public void scheduleAction(final String id,long pause,TimeUnit timeUnit,Callable... actions){
        if(actions==null || isDestroy) return;
        Object threads=runningProcess.get(id);
        CopyOnWriteArrayList<Future> list;
        if(threads!=null){
            if (threads instanceof Future) {
                list=new CopyOnWriteArrayList<>();
                list.add((Future) threads);
                runningProcess.put(id,list);
            } else if(threads instanceof CopyOnWriteArrayList){
                list=(CopyOnWriteArrayList)threads;
            } else {
                throw new IllegalArgumentException("Incorect argument in future list");
            }
        } else {
            list = new CopyOnWriteArrayList<>();
        }
        for(Callable action:actions) {
            ScheduledFuture<?> funutre = pool.schedule(action, pause, timeUnit);
            list.add(funutre);
        }
    }

    synchronized public void stopScheduled(String id){
        Object futurelist=runningProcess.get(id);
        if(futurelist==null) return;
        if(futurelist instanceof CopyOnWriteArrayList){
            for(Future future:(CopyOnWriteArrayList<Future>)futurelist){
                future.cancel(true);
            }
            ((CopyOnWriteArrayList<Future>) futurelist).clear();
        } else if(futurelist instanceof Future){
            ((Future) futurelist).cancel(true);

        } else {
            throw new IllegalArgumentException("Incorrect argument type in future list");
        }
        runningProcess.remove(id);
    }

    synchronized public void stopAllScheduled(){
        for(Object futurelist:runningProcess.values()) {
            if (futurelist == null) return;
            if (futurelist instanceof CopyOnWriteArrayList) {
                for (Future future : (CopyOnWriteArrayList<Future>) futurelist) {
                    future.cancel(true);
                }
                ((CopyOnWriteArrayList<Future>) futurelist).clear();
            } else if (futurelist instanceof Future) {
                ((Future) futurelist).cancel(true);

            } else {
                throw new IllegalArgumentException("Incorrect argument type in future list");
            }
        }
        runningProcess.clear();
    }

    public void terminate() {
        isDestroy=true;
        enable=false;
        stopAllScheduled();
        runningProcess.clear();
        pool.shutdown();
    }

    public void start(){
        enable=(true);
    }

    public void stop(){
        enable=(false);
    }
}

package ua.pp.fairwind.communications.propertyes.abstraction.propertyTrunsactions;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Сергей on 27.08.2015.
 */
public class OperationTrunsactionsSingle implements OperationTrunsactions {
    final private AtomicBoolean multiRequest=new AtomicBoolean(false);
    final private AtomicInteger activeRequest=new AtomicInteger(0);

    @Override
    public boolean isMultiRequestEnabled(OPERATION_TYPE type){
        return multiRequest.get();
    }

    @Override
    public void setMultiRequestEnabled(OPERATION_TYPE type, boolean state){
        multiRequest.compareAndSet(!state,state);
    }

    @Override
    public boolean isRequestActive(OPERATION_TYPE type){
        return activeRequest.get()>0;
    }

    @Override
    public boolean startRequest(OPERATION_TYPE type){
        if(multiRequest.get()) {
            activeRequest.incrementAndGet();
            return true;
        } else {
            return activeRequest.compareAndSet(0,1);
        }
    }

    @Override
    public void endRequest(OPERATION_TYPE type){
        int val=activeRequest.decrementAndGet();
        if(val<0) activeRequest.compareAndSet(val,0);
    }
}

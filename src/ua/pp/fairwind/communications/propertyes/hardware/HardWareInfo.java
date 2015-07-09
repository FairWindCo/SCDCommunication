package ua.pp.fairwind.communications.propertyes.hardware;

import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.HardwareProperty;

/**
 * Created by Сергей on 09.07.2015.
 */
public class HardWareInfo implements HardwareProperty {
    private final long address;
    final private AbstractProperty property;
    volatile long timeout;
    volatile boolean actived;



    public HardWareInfo(long address,AbstractProperty property)
    {
        this.address = address;
        this.property = property;
    }

    @Override
    public long getAddress() {
        return address;
    }

    @Override
    public long getTimeOutForOperation() {
        return timeout;
    }

    @Override
    public void getTimeOutForOperation(long timeOut) {
        this.timeout=timeOut;
    }

    @Override
    public void setActive(boolean active) {
        this.actived=active;
    }

    @Override
    public boolean isActive() {
        return actived;
    }

    @Override
    public long getTimeOutForData() {
        return property.getDataLifeTime();
    }

    @Override
    public void getTimeOutForData(long timeOut) {
        property.setDataLifeTime(timeOut);
    }

    public AbstractProperty getProperty() {
        return property;
    }

    public long getTimeout() {
        return timeout;
    }

    public boolean isActived() {
        return actived;
    }

    @Override
    public boolean isValid() {
        return property.isValidProperty();
    }
}

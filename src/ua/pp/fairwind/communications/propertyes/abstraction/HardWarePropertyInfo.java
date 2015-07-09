package ua.pp.fairwind.communications.propertyes.abstraction;

/**
 * Created by Сергей on 09.07.2015.
 */
public class HardWarePropertyInfo implements HardwareProperty {
    private final long address;
    final protected AbstractProperty property;
    volatile long timeout;
    volatile boolean actived;
    volatile boolean imidiatlyVrite=true;

    public HardWarePropertyInfo(long address, AbstractProperty property) {
        if(property==null) throw new IllegalArgumentException("property cannot be NULL!!!");
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

    @Override
    public boolean isValid() {
        return property.isValidProperty();
    }

    public AbstractProperty getProperty() {
        return property;
    }

    public boolean isImidiatlyVrite() {
        return imidiatlyVrite;
    }

    public void setImidiatlyVrite(boolean imidiatlyVrite) {
        this.imidiatlyVrite = imidiatlyVrite;
    }
}

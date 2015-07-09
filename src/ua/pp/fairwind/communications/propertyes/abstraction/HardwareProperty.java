package ua.pp.fairwind.communications.propertyes.abstraction;

/**
 * Created by FairWindCo on 09.07.2015
 */
public interface HardwareProperty {
    long getAddress();
    long getTimeOutForOperation();
    void getTimeOutForOperation(long timeOut);
    void setActive(boolean active);
    boolean isActive();
    long getTimeOutForData();
    void getTimeOutForData(long timeOut);
    boolean isValid();
}

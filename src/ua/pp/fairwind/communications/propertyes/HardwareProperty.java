package ua.pp.fairwind.communications.propertyes;

/**
 * Created by ������ on 26.06.2015.
 */
public interface HardwareProperty {
    long getAddress();
    long getTimeOutForOperation();
    void getTimeOutForOperation(long timeOut);
    boolean isValide();
    void setActive(boolean active);
    boolean isActive();

}

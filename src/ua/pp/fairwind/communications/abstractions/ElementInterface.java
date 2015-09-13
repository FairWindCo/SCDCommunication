package ua.pp.fairwind.communications.abstractions;

import ua.pp.fairwind.communications.propertyes.event.ElementEventListener;

import java.util.UUID;

/**
 * Created by ������ on 30.06.2015.
 */
public interface ElementInterface {
    String getName();
    UUID getUUID();
    String getUUIDString();
    String getDescription();

    void addEventListener(ElementEventListener listener);
    void removeEventListener(ElementEventListener listener);
    void destroy();

    void setEnabled(boolean enabled);
    boolean isEnabled();
    String getHardwareName();
}

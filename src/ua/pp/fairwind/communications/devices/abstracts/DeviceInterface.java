package ua.pp.fairwind.communications.devices.abstracts;

import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.propertyes.AbsractCommandProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;

/**
 * Created by Сергей on 11.11.2015.
 */
public interface DeviceInterface  extends ElementInterface {
    String NO_RANDOM = "NO_RANDOM";

    String[] getCommandsName();

    String[] getPropertyesName();

    AbsractCommandProperty getCommand(String name);

    AbstractProperty getProperty(String name);

    AbsractCommandProperty getCommandByCodeName(String name);

    AbstractProperty getPropertyByCodeName(String name);

    AbsractCommandProperty[] getCommands();

    AbstractProperty[] getPropertys();

    void setActivete(boolean activeted);

    boolean isActive();

    long getReadTimeOut();

    void setReadTimeOut(long timeOut);

    long getMaxRetry();

    void setMaxRetry(long pause);

    Long getLastSuccessExchangeTime();

    Long getLastTryExchangeTime();

    SoftLongProperty getRetryCount();

    SoftLongProperty getDeviceTimeOut();
}

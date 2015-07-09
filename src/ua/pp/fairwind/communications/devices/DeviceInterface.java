package ua.pp.fairwind.communications.devices;

import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.lines.CommunicationAnswer;
import ua.pp.fairwind.communications.lines.LineInterface;
import ua.pp.fairwind.communications.lines.LineParameters;
import ua.pp.fairwind.communications.propertyes.AbsractCommandProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.HardwareProperty;

/**
 * Created by Сергей on 07.07.2015.
 */
public interface DeviceInterface extends ElementInterface{
    String[] getCommandsName();
    String[] getPropertyesName();
    AbsractCommandProperty getCommand(String name);
    HardwareProperty getPropertyInfo(String name);
    AbstractProperty getProperty(String name);

    void processRecivedMessage(CommunicationAnswer answer);
    long getAddress();
    void setAddress(long address);

    long getReadTimeOut();
    void setReadTimeOut(long timeOut);


    long getPauseBeforeRead();
    void setPauseBeforeRead(long pause);

    void setPrimerayLine(LineInterface line);
    void setSecondaryLine(LineInterface line);

    Long getLastSuccessExchangeTime();
    Long getLastTryExchangeTime();

    void setLineParameters(LineParameters params);
    LineParameters getLineParameters();
}

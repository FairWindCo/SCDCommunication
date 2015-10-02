package ua.pp.fairwind.communications.devices.abstracts;

import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.communications.lines.lineparams.LineParameters;
import ua.pp.fairwind.communications.lines.operations.CommunicationAnswer;
import ua.pp.fairwind.communications.propertyes.AbsractCommandProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;

/**
 * Created by Сергей on 07.07.2015.
 */
public interface DeviceInterface extends ElementInterface{
    String NO_RANDOM="NO_RANDOM";
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

    void processRecivedMessage(CommunicationAnswer answer);

    long getReadTimeOut();
    void setReadTimeOut(long timeOut);
    long getPauseBeforeRead();
    void setPauseBeforeRead(long pause);
    long getPauseBeforeWrite();
    void setPauseBeforeWrite(long pause);
    long getMaxRetry();
    void setMaxRetry(long pause);

    void setPrimerayLine(LineInterface line);
    void setSecondaryLine(LineInterface line);

    Long getLastSuccessExchangeTime();
    Long getLastTryExchangeTime();

    void setLineParameters(LineParameters params);
    LineParameters getLineParameters();



    SoftLongProperty getRetryCount();
    SoftLongProperty getDeviceTimeOut();
    SoftLongProperty getDeviceTimeOutPause();
    SoftLongProperty getDeviceWritePause();


}

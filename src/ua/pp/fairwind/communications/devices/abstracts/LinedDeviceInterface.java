package ua.pp.fairwind.communications.devices.abstracts;

import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.communications.lines.lineparams.LineParameters;
import ua.pp.fairwind.communications.lines.operations.CommunicationAnswer;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;

/**
 * Created by Сергей on 07.07.2015.
 */
public interface LinedDeviceInterface extends DeviceInterface{

    void processRecivedMessage(CommunicationAnswer answer);

    long getPauseBeforeRead();

    void setPauseBeforeRead(long pause);

    long getPauseBeforeWrite();

    void setPauseBeforeWrite(long pause);


    void setPrimerayLine(LineInterface line);

    void setSecondaryLine(LineInterface line);


    LineParameters getLineParameters();

    void setLineParameters(LineParameters params);

    SoftLongProperty getDeviceTimeOutPause();

    SoftLongProperty getDeviceWritePause();


}

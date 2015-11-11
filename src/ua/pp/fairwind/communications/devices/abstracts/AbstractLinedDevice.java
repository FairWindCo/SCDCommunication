package ua.pp.fairwind.communications.devices.abstracts;

import ua.pp.fairwind.communications.devices.RequestInformation;
import ua.pp.fairwind.communications.internatianalisation.I18N;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.communications.lines.lineparams.LineParameters;
import ua.pp.fairwind.communications.lines.operations.CommunicationAnswer;
import ua.pp.fairwind.communications.lines.operations.CommunicationProtocolRequest;
import ua.pp.fairwind.communications.messagesystems.event.ElementEventListener;
import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.messagesystems.event.EventType;
import ua.pp.fairwind.communications.propertyes.DeviceNamedCommandProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftBoolProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;

import java.util.ArrayList;

/**
 * Created by Сергей on 09.07.2015.
 */
public abstract class AbstractLinedDevice extends AbstractDevice {
    public static final String COMMAND_VALIDATE_LINE1 = "VALIDATE_LINE1";
    public static final String COMMAND_VALIDATE_LINE2 = "VALIDATE_LINE2";
    public static final String COMMAND_VALIDATE_ALL = "VALIDATE";

    protected final SoftLongProperty deviceTimeOutPause;
    protected final SoftLongProperty deviceWritePause;
    private final SoftBoolProperty lastCommunicationStatusLine1;
    private final SoftBoolProperty errorCommunicationStatusLine1;
    private final SoftBoolProperty lastCommunicationStatusLine2;
    private final SoftBoolProperty errorCommunicationStatusLine2;
    private final DeviceNamedCommandProperty validateErrorCommandLine1;
    private final DeviceNamedCommandProperty validateErrorCommandLine2;
    private final DeviceNamedCommandProperty validateAllErrorCommand;
    protected volatile LineInterface primaryLine;
    protected volatile LineInterface secondaryLine;
    protected volatile LineParameters lineparams;

    private final ElementEventListener changeListener = (event, param) -> {
        if (event.sourceElement != null) {
            if (event.sourceElement instanceof ValueProperty) {
                ValueProperty<?> hardwarePoperty = (ValueProperty<?>) event.sourceElement;
                boolean isImidiatlyWrite = hardwarePoperty.getAdditionalInfo(IMMEDIATELY_WRITE_FLAG) == null ? false : (boolean) hardwarePoperty.getAdditionalInfo(IMMEDIATELY_WRITE_FLAG);
                if (hardwarePoperty.isActive()) {
                    if (event.typeEvent == EventType.ELEMENT_CHANGE || event.typeEvent == EventType.NEED_WRITE_VALUE) {
                        if (event.typeEvent == EventType.ELEMENT_CHANGE && !isImidiatlyWrite) {
                            return;
                        }
                        if (getInternalValue(hardwarePoperty) != null) writeProperty(hardwarePoperty, event);
                    } else if (event.typeEvent == EventType.NEED_READ_VALUE) {
                        readProperty(hardwarePoperty, event);
                    }
                }
            } else if (event.sourceElement instanceof AbstractProperty) {
                AbstractProperty hardwarePoperty = (AbstractProperty) event.sourceElement;
                boolean isImidiatlyWrite = hardwarePoperty.getAdditionalInfo(IMMEDIATELY_WRITE_FLAG) == null ? false : (boolean) hardwarePoperty.getAdditionalInfo(IMMEDIATELY_WRITE_FLAG);
                if (event.typeEvent == EventType.ELEMENT_CHANGE || event.typeEvent == EventType.NEED_WRITE_VALUE) {
                    if (event.typeEvent == EventType.ELEMENT_CHANGE && !isImidiatlyWrite) {
                        return;
                    }
                    writeProperty(hardwarePoperty, event);
                } else if (event.typeEvent == EventType.NEED_READ_VALUE) {
                    readProperty(hardwarePoperty, event);
                }
            }
        }
    };

    private final ElementEventListener commandListener = (event, param) -> {
        if (event.sourceElement != null && event.sourceElement instanceof DeviceNamedCommandProperty) {
            DeviceNamedCommandProperty hardwareCommaand = (DeviceNamedCommandProperty) event.sourceElement;
            if (event.typeEvent == EventType.ELEMENT_CHANGE || event.typeEvent == EventType.NEED_WRITE_VALUE || event.typeEvent == EventType.NEED_READ_VALUE) {
                executeCommandName(hardwareCommaand, event);
            }
        }
    };


    public AbstractLinedDevice(String codename, String uuid) {
        super(codename, uuid);
        deviceTimeOutPause = formLongProperty(-3, "device.bausebeforeread_property", 0L);
        validateErrorCommandLine1 = formCommandNameProperty(COMMAND_VALIDATE_LINE1);
        validateErrorCommandLine2 = formCommandNameProperty(COMMAND_VALIDATE_LINE2);
        validateAllErrorCommand = formCommandNameProperty(COMMAND_VALIDATE_ALL);
        lastCommunicationStatusLine1 = formIndicatorProperty(-8, "device.lastcommunicationstatusline1_property", false);
        lastCommunicationStatusLine2 = formIndicatorProperty(-9, "device.lastcommunicationstatusline2_property", false);
        errorCommunicationStatusLine1 = formIndicatorProperty(-10, "device.lasterrorcommunicationstatusline1_property", false);
        errorCommunicationStatusLine2 = formIndicatorProperty(-11, "device.lasterrorcommunicationstatusline2_property", false);
        deviceWritePause = formLongProperty(-13, "device.bausebeforewrite_property", 0L);

        ArrayList<AbstractProperty> list = new ArrayList<>();
        list.add(deviceTimeOutPause);
        list.add(lastCommunicationStatusLine1);
        list.add(lastCommunicationStatusLine2);
        list.add(errorCommunicationStatusLine1);
        list.add(errorCommunicationStatusLine2);
        list.add(deviceWritePause);
        listOfPropertyes.addAll(list);

        ArrayList<DeviceNamedCommandProperty> cmds = new ArrayList<>();
        cmds.add(validateErrorCommandLine1);
        cmds.add(validateErrorCommandLine2);
        cmds.add(validateAllErrorCommand);
        listOfCommands.addAll(cmds);
    }

    protected CommunicationProtocolRequest formCommunicationProtocolRequest(CommunicationProtocolRequest.REQUEST_TYPE reqType, RequestInformation needOperation, AbstractProperty property, Event sourceEvent) {
        if (needOperation == null) return null;
        CommunicationProtocolRequest subrequest = formCommunicationProtocolRequest(reqType, needOperation.getSubrequest(), property, sourceEvent);
        CommunicationProtocolRequest request;
        byte[] buffer = needOperation.getBufferForWrite();
        if (buffer != null) {
            Long devTO = (Long) getInternalValue(deviceTimeOut);
            Long devTOP = (Long) getInternalValue(deviceTimeOutPause);
            Long devWP = (Long) getInternalValue(deviceWritePause);
            long maxRetry = (Long) getInternalValue(retryCount);
            long needReadByteCount = needOperation.getNeddedByteForRead();
            boolean needRollBack = needOperation.isNeedRollBack();
            if (property != null) {
                devTOP = property.getPropertyPauseBeforeRead() > 0 ? property.getPropertyPauseBeforeRead() : devTOP + property.getPropertyPauseBeforeReadAddon();
                devTO = property.getPropertyTimeOutRead() > 0 ? property.getPropertyTimeOutRead() : devTO + property.getPropertyTimeOutReadAddon();
                devWP = property.getPropertyPauseBeforeWrite() > 0 ? property.getPropertyPauseBeforeWrite() : devWP + property.getPropertyPauseBeforeWriteAddon();
                request = CommunicationProtocolRequest.createReuest(reqType, buffer, needReadByteCount, this, devTO, devTOP, devWP, lineparams, property, primaryLine != null ? secondaryLine : null, needRollBack, maxRetry, subrequest, sourceEvent);
                return request;
            } else {
                request = CommunicationProtocolRequest.createReuest(reqType, buffer, needReadByteCount, this, devTO, devTOP, devWP, lineparams, null, primaryLine != null ? secondaryLine : null, needRollBack, maxRetry, subrequest, sourceEvent);
                return request;
            }
        } else {
            return subrequest;
        }
    }

    protected void sendBuffer(CommunicationProtocolRequest.REQUEST_TYPE reqType, RequestInformation requestInformation, AbstractProperty property, Event sourceEvent) {
        CommunicationProtocolRequest request = formCommunicationProtocolRequest(reqType, requestInformation, property, sourceEvent);
        if (request != null) {
            if (primaryLine != null) {
                setInternalValue(deviceLastTryCommunicateTime, System.currentTimeMillis());
                primaryLine.async_communicate(request);
            } else if (secondaryLine != null) {
                setInternalValue(deviceLastTryCommunicateTime, System.currentTimeMillis());
                secondaryLine.async_communicate(request);
            } else {
                if (property != null) endRequest(property, reqType.getPropertyOperationType());
                fireEvent(EventType.FATAL_ERROR, I18N.getLocalizedString("device.no_line_specified.error"));
            }
        }
    }

    protected void readProperty(AbstractProperty property, Event sourceEvent) {
        RequestInformation req = formReadRequest(property);
        if (req != null)
            sendBuffer(CommunicationProtocolRequest.REQUEST_TYPE.READ_PROPERTY, req, property, sourceEvent);
    }

    protected void writeProperty(AbstractProperty property, Event sourceEvent) {
        RequestInformation req = formWriteRequest(property);
        if (req != null)
            sendBuffer(CommunicationProtocolRequest.REQUEST_TYPE.WRITE_PROPERTY, req, property, sourceEvent);
    }

    protected void executeCommandName(DeviceNamedCommandProperty property, Event sourceEvent) {
        RequestInformation req = processCommandRequest(property.getCommand());
        if (req == null) {
            property.executed();
        } else {
            sendBuffer(CommunicationProtocolRequest.REQUEST_TYPE.COMMAND_EXECUTE, req, property, sourceEvent);
        }
    }

    private void setLine1Error() {
        setInternalValue(lastCommunicationStatus, false);
        setInternalValue(errorCommunicationStatus, true);
        setInternalValue(lastCommunicationStatusLine1, false);
        setInternalValue(errorCommunicationStatusLine1, true);
    }

    private void setLine2Error() {
        setInternalValue(lastCommunicationStatus, false);
        setInternalValue(errorCommunicationStatus, true);
        setInternalValue(lastCommunicationStatusLine1, false);
        setInternalValue(errorCommunicationStatusLine1, true);
    }

    private void setLine1Success() {
        setInternalValue(lastCommunicationStatus, true);
        setInternalValue(lastCommunicationStatusLine1, true);
        setInternalValue(deviceLastSuccessCommunicateTime, System.currentTimeMillis());
    }

    private void setLine2Success() {
        setInternalValue(lastCommunicationStatus, true);
        setInternalValue(lastCommunicationStatusLine2, true);
        setInternalValue(deviceLastSuccessCommunicateTime, System.currentTimeMillis());
    }

    @Override
    public void processRecivedMessage(CommunicationAnswer answer) {
        try {
            if (answer != null) {
                if (answer.getStatus() != CommunicationAnswer.CommunicationResult.SUCCESS) {
                    answer.invalidate();
                    LineInterface line = answer.getCommunicateOverLine();
                    if (line != null) {
                        if (line.equals(primaryLine)) {
                            setLine1Error();
                        }
                        if (line.equals(secondaryLine)) {
                            setLine2Error();
                        }
                    }
                    if (!(line != null ? line.equals(secondaryLine) : false)) {
                        if (!answer.sendOverReservLine()) setLine2Error();
                    }
                    if (answer.getStatus() == CommunicationAnswer.CommunicationResult.TIMEOUT) {
                        fireEvent(EventType.TIMEOUT, answer.getInformationMesssage());
                    } else {
                        fireEvent(EventType.ERROR, answer.getInformationMesssage());
                    }
                } else {
                    LineInterface line = answer.getCommunicateOverLine();
                    if (line != null) {
                        if (line.equals(primaryLine)) {
                            byte[] readBuf = answer.getRecivedMessage();
                            byte[] sendBuf = answer.getRequest() == null ? null : answer.getRequest().getBytesForSend();
                            AbstractProperty property = answer.getRequest() == null ? null : answer.getRequest().getProperty();
                            if (!processRecivedMessage(readBuf, sendBuf, property, answer.getSourceEvent())) {
                                answer.sendOverReservLine();
                                byte[] readBuf1 = answer.getRecivedMessage();
                                byte[] sendBuf1 = answer.getRequest() == null ? null : answer.getRequest().getBytesForSend();
                                AbstractProperty property1 = answer.getRequest() == null ? null : answer.getRequest().getProperty();
                                try {
                                    if (!processRecivedMessage(readBuf1, sendBuf1, property1, answer.getSourceEvent())) {
                                        setLine1Error();
                                        if (!line.equals(secondaryLine)) {
                                            if (!answer.sendOverReservLine()) setLine2Error();
                                        }
                                    } else {
                                        setLine2Success();
                                    }
                                } catch (Exception e) {
                                    setLine1Error();
                                    answer.invalidate();
                                    fireEvent(EventType.FATAL_ERROR, e);
                                }
                            } else {
                                setLine1Success();
                            }

                        } else if (line.equals(secondaryLine)) {
                            byte[] readBuf = answer.getRecivedMessage();
                            byte[] sendBuf = answer.getRequest() == null ? null : answer.getRequest().getBytesForSend();
                            AbstractProperty property = answer.getRequest() == null ? null : answer.getRequest().getProperty();
                            try {
                                if (!processRecivedMessage(readBuf, sendBuf, property, answer.getSourceEvent())) {
                                    setLine2Error();
                                    answer.invalidate();
                                } else {
                                    setLine2Success();
                                }
                            } catch (Exception e) {
                                setLine1Error();
                                answer.invalidate();
                                fireEvent(EventType.FATAL_ERROR, e);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            answer.invalidate();
            fireEvent(EventType.FATAL_ERROR, ex);
        } finally {
            answer.destroy();
        }
    }

    protected abstract boolean processRecivedMessage(final byte[] recivedMessage, final byte[] sendMessage, final AbstractProperty property, final Event sourceEvent);

    protected abstract RequestInformation formReadRequest(AbstractProperty property);

    protected abstract RequestInformation formWriteRequest(AbstractProperty property);


    protected RequestInformation processCommandRequest(String commandName) {
        if(super.processCommandRequest(commandName)==null) {
            switch (commandName) {
                case COMMAND_VALIDATE_LINE1: {
                    setInternalValue(errorCommunicationStatusLine1, false);
                    break;
                }
                case COMMAND_VALIDATE_LINE2: {
                    setInternalValue(errorCommunicationStatusLine1, false);
                    break;
                }
                case COMMAND_VALIDATE_ALL: {
                    setInternalValue(errorCommunicationStatus, false);
                    setInternalValue(errorCommunicationStatusLine1, false);
                    setInternalValue(errorCommunicationStatusLine2, false);
                    break;
                }
            }
        }
        return null;
    }


    @Override
    public void setPrimerayLine(LineInterface line) {
        this.primaryLine = line;
    }

    @Override
    public long getPauseBeforeRead() {
        return (Long) getInternalValue(deviceTimeOutPause);
    }

    @Override
    public void setPauseBeforeRead(long pause) {
        setInternalValue(deviceTimeOutPause, pause);
    }

    @Override
    public Long getLastSuccessExchangeTime() {
        return (Long) getInternalValue(deviceLastSuccessCommunicateTime);
    }

    @Override
    public Long getLastTryExchangeTime() {
        return (Long) getInternalValue(deviceLastSuccessCommunicateTime);
    }


    public SoftLongProperty getDevicePauseBeforeReadProperty() {
        return deviceTimeOutPause;
    }

    public SoftLongProperty getDevicePauseBeforeWriteProperty() {
        return deviceWritePause;
    }


    @Override
    public LineParameters getLineParameters() {
        return lineparams;
    }

    @Override
    public void setLineParameters(LineParameters params) {
        this.lineparams = params;
    }


    public void validateAll() {
        validateAllErrorCommand.activate();
    }

    public void validateErrorLine1() {
        validateErrorCommandLine1.activate();
    }

    public void validateErrorLine2() {
        validateErrorCommandLine2.activate();
    }


    public SoftBoolProperty getLastCommunicationStatusLine1() {
        return lastCommunicationStatusLine1;
    }

    public SoftBoolProperty getErrorCommunicationStatusLine1() {
        return errorCommunicationStatusLine1;
    }

    public SoftBoolProperty getLastCommunicationStatusLine2() {
        return lastCommunicationStatusLine2;
    }

    public SoftBoolProperty getErrorCommunicationStatusLine2() {
        return errorCommunicationStatusLine2;
    }

    public DeviceNamedCommandProperty getValidateErrorCommandLine1() {
        return validateErrorCommandLine1;
    }

    public DeviceNamedCommandProperty getValidateErrorCommandLine2() {
        return validateErrorCommandLine2;
    }

    public DeviceNamedCommandProperty getValidateAllErrorCommand() {
        return validateAllErrorCommand;
    }

    public LineInterface getPrimaryLine() {
        return primaryLine;
    }

    public LineInterface getSecondaryLine() {
        return secondaryLine;
    }

    @Override
    public void setSecondaryLine(LineInterface line) {
        this.secondaryLine = line;
    }


    public SoftLongProperty getDeviceTimeOutPause() {
        return deviceTimeOutPause;
    }

    public SoftLongProperty getDeviceWritePause() {
        return deviceWritePause;
    }

    @Override
    public long getPauseBeforeWrite() {
        return deviceWritePause.getValue();
    }

    public void setPauseBeforeWrite(long pause) {
        setInternalValue(deviceWritePause, pause);
    }

}

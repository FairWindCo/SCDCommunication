package ua.pp.fairwind.communications.devices.abstracts;

import ua.pp.fairwind.communications.abstractions.annottations.Device;
import ua.pp.fairwind.communications.devices.RequestInformation;
import ua.pp.fairwind.communications.messagesystems.event.ElementEventListener;
import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.messagesystems.event.EventType;
import ua.pp.fairwind.communications.propertyes.AbsractCommandProperty;
import ua.pp.fairwind.communications.propertyes.DeviceNamedCommandProperty;
import ua.pp.fairwind.communications.propertyes.Randomizer;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.PropertyExecutor;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftBoolProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Сергей on 09.07.2015.
 */
@Device(value = "abstract_device")
public abstract class AbstractDevice extends PropertyExecutor implements DeviceInterface {
    public static final String IMMEDIATELY_WRITE_FLAG = "immediatelyWrite";
    public static final String PROPERTY_ADDRESS = "propertyAddress";
    public static final String COMMAND_REFRESH = "REFRESH";
    public static final String COMMAND_VALIDATE = "VALIDATE_COMMAND";
    public static final String COMMAND_VALIDATE_ALL = "VALIDATE";
    public static final String COMMAND_RANDOM = "COMMAND_RANDOM";

    protected final SoftLongProperty deviceTimeOut;
    protected final SoftLongProperty deviceLastTryCommunicateTime;
    protected final SoftLongProperty deviceLastSuccessCommunicateTime;
    protected final SoftBoolProperty activate;
    protected final DeviceNamedCommandProperty refreshCommand;
    protected final SoftLongProperty retryCount;
    protected final CopyOnWriteArrayList<AbstractProperty> listOfPropertyes = new CopyOnWriteArrayList<>();
    protected final CopyOnWriteArrayList<DeviceNamedCommandProperty> listOfCommands = new CopyOnWriteArrayList<>();
    protected final SoftBoolProperty lastCommunicationStatus;
    protected final SoftBoolProperty errorCommunicationStatus;
    protected final DeviceNamedCommandProperty validateErrorCommand;
    protected final DeviceNamedCommandProperty randomCommand;

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


    public AbstractDevice(String codename, String uuid) {
        super(codename, uuid);
        deviceTimeOut = formLongProperty(-2, "device.timeout_property", 500L);
        deviceLastTryCommunicateTime = formLongConfigProperty(-4, "device.lastcommunicationtime_property");
        deviceLastSuccessCommunicateTime = formLongConfigProperty(-5, "device.lastsuccesscommunicationtime_property");
        refreshCommand = formCommandNameProperty(COMMAND_REFRESH);
        validateErrorCommand = formCommandNameProperty(COMMAND_VALIDATE);
        randomCommand = formCommandNameProperty(COMMAND_RANDOM);
        lastCommunicationStatus = formIndicatorProperty(-6, "device.lastcommunicationstatus_property", false);
        errorCommunicationStatus = formIndicatorProperty(-7, "device.lasterrorcommunicationstatus_property", false);
        activate = formBoolProperty(-12, "device.activate_property", true);
        activate.addEventListener((event, param) -> {
            if (event.getTypeEvent() == EventType.ELEMENT_CHANGE)
                super.setEnabled(getInternalValue(activate) == null ? false : (Boolean) getInternalValue(activate));
        }, EventType.ELEMENT_CHANGE);
        retryCount = formLongProperty(-14, "device.retrycount_property", 1);
        ArrayList<AbstractProperty> list = new ArrayList<>();
        list.add(deviceTimeOut);
        list.add(deviceLastTryCommunicateTime);
        list.add(deviceLastSuccessCommunicateTime);
        list.add(lastCommunicationStatus);
        list.add(errorCommunicationStatus);
        list.add(activate);
        list.add(retryCount);
        listOfPropertyes.addAll(list);

        ArrayList<DeviceNamedCommandProperty> cmds = new ArrayList<>();
        cmds.add(refreshCommand);
        cmds.add(validateErrorCommand);
        cmds.add(randomCommand);
        listOfCommands.addAll(cmds);
    }

    static protected String getUiidFromMap(String deviceName, String name, HashMap<String, String> uuids) {
        name = deviceName + ":" + name;
        if (uuids == null) return null;
        return uuids.get(name);
    }

    protected DeviceNamedCommandProperty formCommandNameProperty(String name, String command) {
        DeviceNamedCommandProperty cmd = new DeviceNamedCommandProperty(name, null, command);
        cmd.addEventListener(commandListener, EventType.ELEMENT_CHANGE, EventType.NEED_READ_VALUE, EventType.NEED_WRITE_VALUE);
        return cmd;
    }

    protected DeviceNamedCommandProperty formCommandNameProperty(String name) {
        DeviceNamedCommandProperty cmd = new DeviceNamedCommandProperty(name);
        cmd.addEventListener(commandListener, EventType.ELEMENT_CHANGE, EventType.NEED_READ_VALUE, EventType.NEED_WRITE_VALUE);
        return cmd;
    }

    protected SoftBoolProperty formIndicatorProperty(long address, String name, boolean initialValue) {
        SoftBoolProperty command = new SoftBoolProperty(name, ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY, initialValue);
        command.setAdditionalInfo(PROPERTY_ADDRESS, address);
        return command;
    }

    protected SoftBoolProperty formBoolProperty(long address, String name, boolean initialValue) {
        SoftBoolProperty command = new SoftBoolProperty(name, ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE, initialValue);
        command.setAdditionalInfo(PROPERTY_ADDRESS, address);
        return command;
    }

    protected SoftShortProperty formShortProperty(long address, String name, short initialValue) {
        SoftShortProperty command = new SoftShortProperty(name, ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE, initialValue);
        command.setAdditionalInfo(PROPERTY_ADDRESS, address);
        return command;
    }

    protected SoftBoolProperty formDeviceBoolProperty(long address, String name, boolean initialValue) {
        SoftBoolProperty command = new SoftBoolProperty(name, ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE, initialValue);
        command.setAdditionalInfo(PROPERTY_ADDRESS, address);
        //command.addEventListener(changeListener);
        return command;
    }

    protected SoftLongProperty formLongProperty(long address, String name, long initialValue) {
        SoftLongProperty command = new SoftLongProperty(name, ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE, initialValue);
        command.setAdditionalInfo(PROPERTY_ADDRESS, address);
        return command;
    }

    protected SoftLongProperty formLongConfigProperty(long address, String name) {
        SoftLongProperty command = new SoftLongProperty(name, ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
        command.setAdditionalInfo(PROPERTY_ADDRESS, address);
        return command;
    }



    protected abstract void readProperty(AbstractProperty property, Event sourceEvent);

    protected abstract void writeProperty(AbstractProperty property, Event sourceEvent);

    protected void executeCommandName(DeviceNamedCommandProperty property, Event sourceEvent) {
        RequestInformation req = processCommandRequest(property.getCommand());
        if (req == null) {
            property.executed();
        }
    }


    public abstract String getDeviceType();

    protected RequestInformation processCommandRequest(String commandName) {
        switch (commandName) {
            case COMMAND_REFRESH: {
                break;
            }
            case COMMAND_VALIDATE: {
                setInternalValue(errorCommunicationStatus, false);
                break;
            }
            case COMMAND_VALIDATE_ALL: {
                setInternalValue(errorCommunicationStatus, false);
            }
            case COMMAND_RANDOM: {
                listOfPropertyes.parallelStream().forEach(property -> {
                    if (!(property instanceof AbsractCommandProperty) && (property.getAdditionalInfo("NO_RANDOM") == null || !((Boolean) property.getAdditionalInfo("NO_RANDOM"))))
                        Randomizer.randomizeProperty(property);
                });
                break;
            }
        }
        return null;
    }

    protected String getUiidFromMap(String name, HashMap<String, String> uuids) {
        if (name == null || uuids == null) return null;
        return getUiidFromMap(getName(), name, uuids);
    }

    protected void addCommands(DeviceNamedCommandProperty commands) {
        if (commands != null) {
            listOfCommands.add(commands);
            commands.addEventListener(commandListener, EventType.ELEMENT_CHANGE);
        }
    }

    protected void addPropertys(AbstractProperty property) {
        if (property != null) {
            listOfPropertyes.add(property);
            property.addEventListener(changeListener, EventType.ELEMENT_CHANGE, EventType.NEED_READ_VALUE, EventType.NEED_WRITE_VALUE);
        }
    }

    @Override
    public String[] getCommandsName() {
        return listOfCommands.parallelStream().map(command -> command.getName()).toArray(size -> new String[size]);
    }

    @Override
    public String[] getPropertyesName() {
        return listOfPropertyes.parallelStream().map(command -> command.getName()).toArray(size -> new String[size]);
    }

    @Override
    public AbsractCommandProperty getCommand(final String name) {
        if (name != null) {
            try {
                AbsractCommandProperty result = listOfCommands.parallelStream().filter(command -> name.equals(command.getName())).findFirst().get();
                return result;
            } catch (NoSuchElementException ex) {
                return null;
            }
        } else return null;
    }

    @Override
    public AbstractProperty getProperty(String name) {
        if (name != null) {
            try {
                AbstractProperty result = listOfPropertyes.parallelStream().filter(command -> name.equals(command.getName())).findFirst().get();
                return result == null ? null : result;
            } catch (NoSuchElementException ex) {
                return null;
            }
        } else return null;
    }

    @Override
    public AbsractCommandProperty getCommandByCodeName(final String name) {
        if (name != null) {
            try {
                AbsractCommandProperty result = listOfCommands.parallelStream().filter(command -> name.equals(command.getCodename())).findFirst().get();
                return result;
            } catch (NoSuchElementException ex) {
                return null;
            }
        } else return null;
    }

    @Override
    public AbstractProperty getPropertyByCodeName(String name) {
        if (name != null) {
            try {
                AbstractProperty result = listOfPropertyes.parallelStream().filter(command -> name.equals(command.getCodename())).findFirst().get();
                return result == null ? null : result;
            } catch (NoSuchElementException ex) {
                return null;
            }
        } else return null;
    }



    @Override
    public long getReadTimeOut() {
        return (Long) getInternalValue(deviceTimeOut);
    }

    @Override
    public void setReadTimeOut(long timeout) {
        setInternalValue(deviceTimeOut, timeout);
    }

    @Override
    public Long getLastSuccessExchangeTime() {
        return (Long) getInternalValue(deviceLastSuccessCommunicateTime);
    }

    @Override
    public Long getLastTryExchangeTime() {
        return (Long) getInternalValue(deviceLastSuccessCommunicateTime);
    }

    public SoftLongProperty getDeviceTimeOutProperty() {
        return deviceTimeOut;
    }


    public SoftLongProperty getDeviceLastExchangeTimeProperty() {
        return deviceLastSuccessCommunicateTime;
    }

    public SoftLongProperty getDeviceLastTryExchangeProperty() {
        return deviceLastTryCommunicateTime;
    }



    public void refreshData() {
        refreshCommand.activate();
    }


    public void validateError() {
        validateErrorCommand.activate();
    }


    @Override
    public void setActivete(boolean activeted) {
        setInternalValue(activate, activeted);
    }

    @Override
    public boolean isActive() {
        return (Boolean) getInternalValue(activate);
    }

    @Override
    public String toString() {
        return "DEV{Name:" + getName() + " ,U:" + getUUIDString() + ",D:" + getDescription() + "}";
    }

    public SoftLongProperty getRetryCount() {
        return retryCount;
    }

    public SoftBoolProperty getLastCommunicationStatus() {
        return lastCommunicationStatus;
    }

    public SoftBoolProperty getErrorCommunicationStatus() {
        return errorCommunicationStatus;
    }


    public DeviceNamedCommandProperty getRefreshCommand() {
        return refreshCommand;
    }

    public DeviceNamedCommandProperty getValidateErrorCommand() {
        return validateErrorCommand;
    }


    @Override
    public AbsractCommandProperty[] getCommands() {
        if (listOfCommands == null || listOfCommands.size() == 0) return null;
        return listOfCommands.toArray(new AbsractCommandProperty[listOfCommands.size()]);
    }

    @Override
    public AbstractProperty[] getPropertys() {
        if (listOfPropertyes == null || listOfPropertyes.size() == 0) return null;
        return listOfPropertyes.toArray(new AbstractProperty[listOfPropertyes.size()]);
    }

    @Override
    public void setEnabled(boolean enabled) {
        activate.setValue(enabled);
    }

    public SoftLongProperty getDeviceTimeOut() {
        return deviceTimeOut;
    }


    @Override
    public long getMaxRetry() {
        return retryCount.getValue();
    }

    @Override
    public void setMaxRetry(long pause) {
        retryCount.setValue(pause);
    }
}

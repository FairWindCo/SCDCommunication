package ua.pp.fairwind.communications.devices.abstracts;

import ua.pp.fairwind.communications.devices.RequestInformation;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.communications.lines.lineparams.LineParameters;
import ua.pp.fairwind.communications.lines.operations.CommunicationAnswer;
import ua.pp.fairwind.communications.lines.operations.CommunicationProtocolRequest;
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
public abstract class AbstractImmitatorDevice extends PropertyExecutor implements ImitatorDevice {
    public static final String IMMEDIATELY_WRITE_FLAG = "immediatelyWrite";
    public static final String PROPERTY_ADDRESS = "propertyAddress";
    public static final String COMMAND_VALIDATE = "VALIDATE";
    public static final String COMMAND_RANDOM = "COMMAND_RANDOM";


    protected final SoftBoolProperty activate;
    final protected CopyOnWriteArrayList<AbstractProperty> listOfPropertyes = new CopyOnWriteArrayList<>();
    final protected CopyOnWriteArrayList<DeviceNamedCommandProperty> listOfCommands = new CopyOnWriteArrayList<>();
    private final SoftBoolProperty lastCommunicationStatus;
    private final SoftBoolProperty errorCommunicationStatus;
    private final DeviceNamedCommandProperty validateErrorCommand;
    private final DeviceNamedCommandProperty randomCommand;
    private final ElementEventListener commandListener = (event, param) -> {
        if (event.sourceElement != null && event.sourceElement instanceof DeviceNamedCommandProperty) {
            DeviceNamedCommandProperty hardwareCommaand = (DeviceNamedCommandProperty) event.sourceElement;
            if (event.typeEvent == EventType.ELEMENT_CHANGE || event.typeEvent == EventType.NEED_WRITE_VALUE || event.typeEvent == EventType.NEED_READ_VALUE) {
                executeCommandName(hardwareCommaand, event);
            }
        }
    };


    public AbstractImmitatorDevice(String codename, String uuid) {
        super(codename, uuid);
        validateErrorCommand = formCommandNameProperty(COMMAND_VALIDATE);
        randomCommand = formCommandNameProperty(COMMAND_RANDOM);
        lastCommunicationStatus = formIndicatorProperty(-6, "device.lastcommunicationstatus_property", false);
        errorCommunicationStatus = formIndicatorProperty(-7, "device.lasterrorcommunicationstatus_property", false);
        activate = formBoolProperty(-12, "device.activate_property", true);
        activate.addEventListener((event, param) -> {
            if (event.getTypeEvent() == EventType.ELEMENT_CHANGE)
                super.setEnabled(getInternalValue(activate) == null ? false : (Boolean) getInternalValue(activate));
        }, EventType.ELEMENT_CHANGE);

        ArrayList<AbstractProperty> list = new ArrayList<>();
        list.add(lastCommunicationStatus);
        list.add(errorCommunicationStatus);
        list.add(activate);
        listOfPropertyes.addAll(list);

        ArrayList<DeviceNamedCommandProperty> cmds = new ArrayList<>();
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

    protected CommunicationProtocolRequest formCommunicationProtocolRequest(CommunicationProtocolRequest.REQUEST_TYPE reqType, RequestInformation needOperation, AbstractProperty property, Event sourceEvent) {
        return null;
    }

    protected void executeCommandName(DeviceNamedCommandProperty property, Event sourceEvent) {
        RequestInformation req = processCommandRequest(property.getCommand());
        if (req == null) {
            property.executed();
        }
    }

    @Override
    public void processRecivedMessage(CommunicationAnswer answer) {

    }

    public abstract String getDeviceType();

    protected RequestInformation processCommandRequest(String commandName) {
        switch (commandName) {
            case COMMAND_VALIDATE: {
                setInternalValue(errorCommunicationStatus, false);
                break;
            }
            case COMMAND_RANDOM: {
                listOfPropertyes.parallelStream().forEach(property ->
                {
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
    public void setPrimerayLine(LineInterface line) {/*do nothing*/}

    @Override
    public void setSecondaryLine(LineInterface line) {
        /*do nothing*/
    }

    @Override
    public long getReadTimeOut() {
        return 0;
    }

    @Override
    public void setReadTimeOut(long timeout) {
        /*do nothing*/
    }

    @Override
    public long getPauseBeforeRead() {
        return 0;
    }

    @Override
    public void setPauseBeforeRead(long pause) {
        /*do nothing*/
    }

    @Override
    public Long getLastSuccessExchangeTime() {
        return 0L;
    }

    @Override
    public Long getLastTryExchangeTime() {
        return 0L;
    }

    @Override
    public LineParameters getLineParameters() {
        return null;
    }

    @Override
    public void setLineParameters(LineParameters params) {
        /*do nothing*/
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

    public SoftBoolProperty getLastCommunicationStatus() {
        return lastCommunicationStatus;
    }

    public SoftBoolProperty getErrorCommunicationStatus() {
        return errorCommunicationStatus;
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

    @Override
    public long getPauseBeforeWrite() {
        return 0;
    }

    public void setPauseBeforeWrite(long pause) {
        /*do nothing*/
    }

    @Override
    public long getMaxRetry() {
        return 0;
    }

    @Override
    public void setMaxRetry(long pause) {
        /*do nothing*/
        ;
    }

    @Override
    public void errorDuringSend() {
        setInternalValue(lastCommunicationStatus, false);
        setInternalValue(errorCommunicationStatus, true);
    }

    @Override
    public byte[] process_line_data(byte[] data_from_line) {
        byte[] result = processDataFromLine(data_from_line);
        if (result != null) setInternalValue(lastCommunicationStatus, true);
        return result;
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

    public DeviceNamedCommandProperty getRandomCommand() {
        return randomCommand;
    }

    abstract protected byte[] processDataFromLine(byte[] data_from_line);


}

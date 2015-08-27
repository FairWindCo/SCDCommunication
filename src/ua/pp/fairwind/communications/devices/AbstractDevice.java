package ua.pp.fairwind.communications.devices;

import ua.pp.fairwind.communications.abstractions.SystemEllement;
import ua.pp.fairwind.communications.lines.CommunicationAnswer;
import ua.pp.fairwind.communications.lines.CommunicationProtocolRequest;
import ua.pp.fairwind.communications.lines.LineInterface;
import ua.pp.fairwind.communications.lines.LineParameters;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.AbsractCommandProperty;
import ua.pp.fairwind.communications.propertyes.DeviceNamedCommandProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.event.ElementEventListener;
import ua.pp.fairwind.communications.propertyes.event.EventType;
import ua.pp.fairwind.communications.propertyes.software.SoftBoolProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Сергей on 09.07.2015.
 */
public abstract class AbstractDevice extends SystemEllement implements DeviceInterface {
    public static final String IMIDIATLY_WRITE_FLAG="imidiatlyWrite";
    public static final String PROPERTY_ADDRESS="propertyAddress";
    protected final SoftLongProperty deviceAddress;
    protected final SoftLongProperty deviceTimeOut;
    protected final SoftLongProperty deviceTimeOutPause;
    protected final SoftLongProperty deviceWritePause;
    protected final SoftLongProperty deviceLastTryCommunicateTime;
    protected final SoftLongProperty deviceLastSuccessCommunicateTime;
    protected final SoftBoolProperty activate;
    private final SoftBoolProperty lastCommunicationStatus;
    private final SoftBoolProperty errorCommunicationStatus;
    private final SoftBoolProperty lastCommunicationStatusLine1;
    private final SoftBoolProperty errorCommunicationStatusLine1;
    private final SoftBoolProperty lastCommunicationStatusLine2;
    private final SoftBoolProperty errorCommunicationStatusLine2;
    private final DeviceNamedCommandProperty refreshCommand;
    private final DeviceNamedCommandProperty validateErrorCommand;
    private final DeviceNamedCommandProperty validateErrorCommandLine1;
    private final DeviceNamedCommandProperty validateErrorCommandLine2;
    private final DeviceNamedCommandProperty validateAllErrorCommand;
    protected final SoftLongProperty retryCount;

    final protected CopyOnWriteArrayList<AbstractProperty> listOfPropertyes=new CopyOnWriteArrayList<>();
    final protected CopyOnWriteArrayList<DeviceNamedCommandProperty> listOfCommands=new CopyOnWriteArrayList<>();
    protected volatile LineInterface primaryLine;
    protected volatile LineInterface secondaryLine;
    protected volatile LineParameters lineparams;

    private final ElementEventListener changeListener=(element,type,param)->{
        if(element!=null && element instanceof ValueProperty) {
            ValueProperty<?> hardwarePoperty=(ValueProperty<?>)element;
            boolean isImidiatlyWrite=hardwarePoperty.getAdditionalInfo(IMIDIATLY_WRITE_FLAG)==null?false:(boolean)hardwarePoperty.getAdditionalInfo(IMIDIATLY_WRITE_FLAG);
            if(hardwarePoperty.isActive()) {
                if (type == EventType.ELEMENT_CHANGE || type == EventType.NEED_WRITE_VALUE) {
                    if(type == EventType.ELEMENT_CHANGE && !isImidiatlyWrite){
                        return;
                    }
                    writeProperty(hardwarePoperty);
                } else if (type == EventType.NEED_READ_VALUE) {
                    readProperty(hardwarePoperty);
                }
            }
        }
    };

    private final ElementEventListener commandListener=(element,type,param) ->{
        if(element!=null && element instanceof DeviceNamedCommandProperty) {
            DeviceNamedCommandProperty hardwareCommaand=(DeviceNamedCommandProperty)element;
                if (type == EventType.ELEMENT_CHANGE || type == EventType.NEED_WRITE_VALUE || type == EventType.NEED_READ_VALUE) {
                    executeCommandName(hardwareCommaand);
                }
        }
    };

    public AbstractDevice(long address,String name, String uuid, String description, MessageSubSystem centralSystem) {
        this(address, name, uuid, description, centralSystem, null);
    }

    protected DeviceNamedCommandProperty formCommandNameProperty(String name, String description, MessageSubSystem centralSystem,HashMap<String,String> uuids){
        DeviceNamedCommandProperty command=new DeviceNamedCommandProperty(name,getUiidFromMap(name,uuids),description,centralSystem);
        command.addEventListener(commandListener);
        return command;
    }

    protected SoftBoolProperty formIndicatorProperty(long address,String name, String description, MessageSubSystem centralSystem,HashMap<String,String> uuids,boolean initialValue){
        SoftBoolProperty command=new SoftBoolProperty(name,getUiidFromMap(name,uuids),description,centralSystem, ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY,initialValue);
        command.setAdditionalInfo("propertyAddress", address);
        return command;
    }

    protected SoftBoolProperty formBoolProperty(long address,String name, String description, MessageSubSystem centralSystem,HashMap<String,String> uuids,boolean initialValue){
        SoftBoolProperty command=new SoftBoolProperty(name,getUiidFromMap(name,uuids),description,centralSystem,ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE,initialValue);
        command.setAdditionalInfo("propertyAddress",address);
        return command;
    }

    protected SoftBoolProperty formDeviceBoolProperty(long address,String name, String description, MessageSubSystem centralSystem,HashMap<String,String> uuids,boolean initialValue){
        SoftBoolProperty command=new SoftBoolProperty(name,getUiidFromMap(name,uuids),description,centralSystem, ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE,initialValue);
        command.setAdditionalInfo("propertyAddress", address);
        //command.addEventListener(changeListener);
        return command;
    }

    protected SoftLongProperty formLongProperty(long address,String name, String description, MessageSubSystem centralSystem,HashMap<String,String> uuids,long initialValue){
        SoftLongProperty command=new SoftLongProperty(name,getUiidFromMap(name,uuids),description,centralSystem, ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE,initialValue);
        command.setAdditionalInfo("propertyAddress", address);
        return command;
    }

    protected SoftLongProperty formLongConfigProperty(long address,String name, String description, MessageSubSystem centralSystem,HashMap<String,String> uuids){
        SoftLongProperty command=new SoftLongProperty(name,getUiidFromMap(name,uuids),description,centralSystem,ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
        command.setAdditionalInfo("propertyAddress", address);
        return command;
    }

    public AbstractDevice(long address,String name, String uuid, String description, MessageSubSystem centralSystem,HashMap<String,String> uuids) {
        super(name, uuid, description, centralSystem);
        deviceAddress=formLongProperty(-1,"Address","The Device Address used for communication",centralSystem,uuids,address);
        deviceTimeOut=formLongProperty(-2,"TimeOut","The Time Out for ReadLine operation",centralSystem,uuids,500L);
        deviceTimeOutPause=formLongProperty(-3, "BeforeReadPause",  "Wait pause after WriteLine operation", centralSystem,uuids, 0L);
        deviceLastTryCommunicateTime=formLongConfigProperty(-4, "LastTryCommunicateTime", "Wait pause after WriteLine operation", centralSystem, uuids);
        deviceLastSuccessCommunicateTime=formLongConfigProperty(-5,"LastSuccessCommunicateTime","Wait pause after WriteLine operation",centralSystem,uuids);
        refreshCommand=formCommandNameProperty("REFRESH", "The Refresh Device Data Command", centralSystem, uuids);
        validateErrorCommand=formCommandNameProperty("VALIDATE_COMMAND", "Validate last command error", centralSystem, uuids);
        validateErrorCommandLine1=formCommandNameProperty("VALIDATE_LINE1", "Validate last command error on line 1", centralSystem, uuids);
        validateErrorCommandLine2=formCommandNameProperty("VALIDATE_LINE2", "Validate last command error on line 2", centralSystem, uuids);
        validateAllErrorCommand=formCommandNameProperty("VALIDATE", "Validate all error", centralSystem, uuids);
        lastCommunicationStatus      =  formIndicatorProperty(-6, "LAST_COMMUNICATION_STATUS", "Status indicator of error during last comminication", centralSystem, uuids, false);
        errorCommunicationStatus     =  formIndicatorProperty(-7, "COMMUNICATION_STATUS", "Status indicator of error comminication with validation", centralSystem, uuids, false);
        lastCommunicationStatusLine1 =  formIndicatorProperty(-8,"COMMUNICATION_STATUS_LINE_1","Status indicator of error during last comminication on line 1",centralSystem,uuids,false);
        lastCommunicationStatusLine2  =  formIndicatorProperty(-9,"COMMUNICATION_STATUS_LINE_2","Status indicator of error during last comminication on line 2",centralSystem,uuids,false);
        errorCommunicationStatusLine1 =  formIndicatorProperty(-10,"LAST_COMMUNICATION_STATUS_LINE1","Status indicator of error comminication with validation on line 1",centralSystem,uuids,false);
        errorCommunicationStatusLine2 =  formIndicatorProperty(-11, "LAST_COMMUNICATION_STATUS_LINE2", "Status indicator of error comminication with validation on line 2", centralSystem, uuids, false);
        activate = formBoolProperty(-12, "ACTIVATE DEVICE", "Status indicator of error comminication with validation on line 2", centralSystem, uuids, true);
        deviceWritePause=formLongProperty(-13, "BeforeWritePause", "Wait pause before WriteLine operation", centralSystem, uuids, 0L);
        retryCount=formLongProperty(-14,"CountOfTimeOutRetry","Count of re try operation before timeout error recived",centralSystem,uuids,1);

        ArrayList<AbstractProperty> list=new ArrayList<>();
        list.add(deviceAddress);
        list.add(deviceTimeOut);
        list.add(deviceTimeOutPause);
        list.add(deviceLastTryCommunicateTime);
        list.add(deviceLastSuccessCommunicateTime);
        list.add(lastCommunicationStatus);
        list.add(errorCommunicationStatus);
        list.add(lastCommunicationStatusLine1);
        list.add(lastCommunicationStatusLine2);
        list.add(errorCommunicationStatusLine1);
        list.add(errorCommunicationStatusLine2);
        list.add(activate);
        list.add(deviceWritePause);
        list.add(retryCount);
        listOfPropertyes.addAll(list);

        ArrayList<DeviceNamedCommandProperty> cmds=new ArrayList<>();
        cmds.add(refreshCommand);
        cmds.add(validateErrorCommand);
        cmds.add(validateErrorCommandLine1);
        cmds.add(validateErrorCommandLine2);
        cmds.add(validateAllErrorCommand);
        listOfCommands.addAll(cmds);
    }

    protected void sendBuffer(CommunicationProtocolRequest.REQUEST_TYPE reqType,byte[] buffer,long needReadByteCount,AbstractProperty property,boolean needRollBack){
        if(buffer!=null){
            Long devTO=((ValueProperty<Long>)deviceTimeOut).getInternalValue();
            Long devTOP=((ValueProperty<Long>)deviceTimeOutPause).getInternalValue();
            Long devWP=((ValueProperty<Long>)deviceWritePause).getInternalValue();
            long maxRetry=retryCount.getInternalValue();
            if(property!=null){
                devTOP=property.getPropertyPauseBeforeRead()>0?property.getPropertyPauseBeforeRead():devTOP+property.getPropertyPauseBeforeReadAddon();
                devTO=property.getPropertyTimeOutRead()>0?property.getPropertyTimeOutRead():devTO+property.getPropertyTimeOutReadAddon();
                devWP=property.getPropertyPauseBeforeWrite()>0?property.getPropertyPauseBeforeWrite():devWP+property.getPropertyPauseBeforeWriteAddon();

            }
                CommunicationProtocolRequest request=CommunicationProtocolRequest.createReuest(reqType,buffer,needReadByteCount,this, devTO,devTOP,devWP,lineparams,property,primaryLine!=null?secondaryLine:null,needRollBack,maxRetry);
                if(request!=null) {
                    if (primaryLine != null) {
                        deviceLastTryCommunicateTime.setInternalValue(System.currentTimeMillis());
                        primaryLine.async_communicate(request);
                    } else if (secondaryLine != null) {
                        deviceLastTryCommunicateTime.setInternalValue(System.currentTimeMillis());
                        secondaryLine.async_communicate(request);
                    }
                }
        }
    }

    protected void readProperty(AbstractProperty property){
        RequestInformation req=formReadRequest(property);
        if(req!=null) sendBuffer(CommunicationProtocolRequest.REQUEST_TYPE.READ_PROPERTY,req.getBufferForWrite(),req.getNeddedByteForRead(),property,req.isNeedRollBack());
    }

    protected void writeProperty(AbstractProperty property) {
        RequestInformation req=formWriteRequest(property);
        if(req!=null) sendBuffer(CommunicationProtocolRequest.REQUEST_TYPE.WRITE_PROPERTY,req.getBufferForWrite(),req.getNeddedByteForRead(),property,req.isNeedRollBack());
    }

    protected void executeCommandName(DeviceNamedCommandProperty property){
        RequestInformation req=processCommandRequest(property.getName());
        if(req==null){
            property.executed();
        } else {
            sendBuffer(CommunicationProtocolRequest.REQUEST_TYPE.COMMAND_EXECUTE,req.getBufferForWrite(),req.getNeddedByteForRead(),property,req.isNeedRollBack());
        }
    }


    private void setLine1Error(){
        (lastCommunicationStatus).setInternalValue(false);
        (errorCommunicationStatus).setInternalValue(true);
        (lastCommunicationStatusLine1).setInternalValue(false);
        (errorCommunicationStatusLine1).setInternalValue(true);
    }

    private void setLine2Error(){
        (lastCommunicationStatus).setInternalValue(false);
        (errorCommunicationStatus).setInternalValue(true);
        (lastCommunicationStatusLine1).setInternalValue(false);
        (errorCommunicationStatusLine1).setInternalValue(true);
    }

    private void setLine1Success(){
        (lastCommunicationStatus).setInternalValue(true);
        (lastCommunicationStatusLine1).setInternalValue(true);
        (deviceLastSuccessCommunicateTime).setInternalValue(System.currentTimeMillis());
    }

    private void setLine2Success(){
        (lastCommunicationStatus).setInternalValue(true);
        (lastCommunicationStatusLine2).setInternalValue(true);
        (deviceLastSuccessCommunicateTime).setInternalValue(System.currentTimeMillis());
    }

    @Override
    public void processRecivedMessage(CommunicationAnswer answer) {
        try{
        if(answer != null){
            if(answer.getStatus()!= CommunicationAnswer.CommunicationResult.SUCCESS){
                answer.invalidate();
                LineInterface line=answer.getCommunicateOverLine();
                if(line!=null){
                    if(line.equals(primaryLine)){
                        setLine1Error();
                    }
                    if(line.equals(secondaryLine)){
                        setLine2Error();
                    }
                }
                answer.sendOverReservLine();
                if(answer.getStatus()== CommunicationAnswer.CommunicationResult.TIMEOUT){
                    fireEvent(EventType.TIMEOUT, answer.getInformationMesssage());
                } else {
                    fireEvent(EventType.ERROR, answer.getInformationMesssage());
                }
            } else {
                LineInterface line=answer.getCommunicateOverLine();
                if(line!=null){
                    answer.destroy();
                    if(line.equals(primaryLine)){
                        byte[] readBuf=answer.getRecivedMessage();
                        byte[] sendBuf=answer.getRequest()==null?null:answer.getRequest().getBytesForSend();
                        AbstractProperty property=answer.getRequest()==null?null:answer.getRequest().getProperty();
                        if(!processRecivedMessage(readBuf,sendBuf,property)){
                            answer.sendOverReservLine();
                            byte[] readBuf1=answer.getRecivedMessage();
                            byte[] sendBuf1=answer.getRequest()==null?null:answer.getRequest().getBytesForSend();
                            AbstractProperty property1=answer.getRequest()==null?null:answer.getRequest().getProperty();
                            if(!processRecivedMessage(readBuf1,sendBuf1,property1)){
                                setLine1Error();
                                answer.sendOverReservLine();
                            } else {
                                setLine2Success();
                            }
                        } else {
                            setLine1Success();
                        }

                    }
                    if(line.equals(secondaryLine)){
                        byte[] readBuf=answer.getRecivedMessage();
                        byte[] sendBuf=answer.getRequest()==null?null:answer.getRequest().getBytesForSend();
                        AbstractProperty property=answer.getRequest()==null?null:answer.getRequest().getProperty();
                        if(!processRecivedMessage(readBuf,sendBuf,property)){
                            setLine2Error();
                            answer.invalidate();
                        } else {
                            setLine2Success();
                        }
                    }
                }
            }
        }
        }catch (Exception ex){
            answer.invalidate();
            fireEvent(EventType.FATAL_ERROR,ex);
        }
    }
    protected abstract boolean processRecivedMessage(final byte[] recivedMessage,final byte[] sendMessage,final AbstractProperty property);
    protected abstract RequestInformation formReadRequest(AbstractProperty property);
    protected abstract RequestInformation formWriteRequest(AbstractProperty  property);
    public abstract String getDeviceType();

    protected RequestInformation processCommandRequest(String commandName){
        RequestInformation result=null;
        switch(commandName){
            case "REFRESH":{
                break;
            }
            case "VALIDATE_COMMAND": {
                ((ValueProperty<Boolean>)errorCommunicationStatus).setInternalValue(false);
                break;
            }
            case "VALIDATE_LINE1":{
                ((ValueProperty<Boolean>)errorCommunicationStatusLine1).setInternalValue(false);
                break;
            }
            case "VALIDATE_LINE2":{
                ((ValueProperty<Boolean>)errorCommunicationStatusLine1).setInternalValue(false);
                break;
            }
            case "VALIDATE": {
                ((ValueProperty<Boolean>)errorCommunicationStatus).setInternalValue(false);
                ((ValueProperty<Boolean>)errorCommunicationStatusLine1).setInternalValue(false);
                ((ValueProperty<Boolean>)errorCommunicationStatusLine2).setInternalValue(false);
                break;
            }
        }
        return result;
    }



    static protected String getUiidFromMap(String name,HashMap<String,String> uuids){
        if(name==null || uuids==null) return null;
        return uuids.get(name);
    }

    protected void addCommands(DeviceNamedCommandProperty commands){
        if(commands!=null){
            listOfCommands.add(commands);
            commands.addEventListener(commandListener);
        }
    }

    protected void addPropertys(AbstractProperty property){
        if (property!=null){
            listOfPropertyes.add(property);
            if(property!=null){
                property.addEventListener(changeListener);
            }
        }
    }

    @Override
    public String[] getCommandsName() {
        return listOfCommands.parallelStream().map(command->command.getName()).toArray(size -> new String[size]);
    }

    @Override
    public String[] getPropertyesName() {
        return listOfPropertyes.parallelStream().map(command->command.getName()).toArray(size -> new String[size]);
    }

    @Override
    public AbsractCommandProperty getCommand(final String name) {
        if(name!=null){
            try{
                AbsractCommandProperty result=listOfCommands.parallelStream().filter(command->name.equals(command.getName())).findFirst().get();
                return result;
            } catch (NoSuchElementException ex){
                return null;
            }
        } else return null;
    }

    @Override
    public AbstractProperty getPropertyInfo(String name) {
        if(name!=null){
            try {
                AbstractProperty result = listOfPropertyes.parallelStream().filter(command -> name.equals(command.getName())).findFirst().get();
                return result;
            } catch (NoSuchElementException ex){
                return null;
            }
        } else return null;
    }

    @Override
    public AbstractProperty getProperty(String name) {
        if(name!=null){
            try {
                AbstractProperty result=listOfPropertyes.parallelStream().filter(command->name.equals(command.getName())).findFirst().get();
            return result==null?null:result;
            } catch (NoSuchElementException ex){
                return null;
            }
        } else return null;
    }



     @Override
    public long getAddress() {
        Long adr= ((ValueProperty<Long>)deviceAddress).getInternalValue();
        if(adr==null) return -1;
        return adr;
    }

    @Override
    public void setAddress(long address) {
        ((ValueProperty<Long>)deviceAddress).setInternalValue(address);
    }

    @Override
    public void setPrimerayLine(LineInterface line) {
        this.primaryLine=line;
    }

    @Override
    public void setSecondaryLine(LineInterface line) {
        this.secondaryLine=line;
    }

    @Override
    public long getReadTimeOut() {
        return ((ValueProperty<Long>)deviceTimeOut).getInternalValue();
    }

    @Override
    public void setReadTimeOut(long timeout) {
        ((ValueProperty<Long>)deviceTimeOut).setInternalValue(timeout);
    }

    @Override
    public long getPauseBeforeRead() { return ((ValueProperty<Long>)deviceTimeOutPause).getInternalValue();   }

    @Override
    public void setPauseBeforeRead(long pause) {
        ((ValueProperty<Long>)deviceTimeOutPause).setInternalValue(pause);
    }

    public void setPauseBeforeWrite(long pause) {
        ((ValueProperty<Long>)deviceWritePause).setInternalValue(pause);
    }

    @Override
    public Long getLastSuccessExchangeTime() {
        return ((ValueProperty<Long>)deviceLastSuccessCommunicateTime).getInternalValue();
    }

    @Override
    public Long getLastTryExchangeTime() {
        return ((ValueProperty<Long>)deviceLastSuccessCommunicateTime).getInternalValue();
    }


    public SoftLongProperty getDeviceAddressProperty(){
        return deviceAddress;
    }

    public SoftLongProperty getDeviceTimeOutProperty(){
        return deviceTimeOut;
    }

    public SoftLongProperty getDevicePauseBeforeReadProperty(){
        return deviceTimeOutPause;
    }

    public SoftLongProperty getDevicePauseBeforeWriteProperty(){
        return deviceWritePause;
    }

    public SoftLongProperty getDeviceLastExchangeTimeProperty(){
        return deviceLastSuccessCommunicateTime;
    }

    public SoftLongProperty getDeviceLastTryExchangeProperty(){
        return deviceLastTryCommunicateTime;
    }



    @Override
    public void setLineParameters(LineParameters params) {
        this.lineparams=params;
    }

    @Override
    public LineParameters getLineParameters() {
        return lineparams;
    }

    public void refreshData(){
        refreshCommand.activate();
    }

    public void validateAll(){
        validateAllErrorCommand.activate();
    }

    public void validateError(){
        validateErrorCommand.activate();
    }

    public void validateErrorLine1(){
        validateErrorCommandLine1.activate();
    }

    public void validateErrorLine2(){
        validateErrorCommandLine2.activate();
    }

    @Override
    public void setActivete(boolean activeted) {
        ((ValueProperty<Boolean>)activate).setInternalValue(activeted);
    }

    @Override
    public boolean isActive() {
        return ((ValueProperty<Boolean>)activate).getInternalValue();
    }

    @Override
    public String toString() {
        return "DEV{Name:"+getName()+" ,U:"+getUUIDString()+",D:"+getDescription()+"}";
    }

    public SoftLongProperty getRetryCount(){
        return retryCount;
    }

    public SoftBoolProperty getLastCommunicationStatus() {
        return lastCommunicationStatus;
    }

    public SoftBoolProperty getErrorCommunicationStatus() {
        return errorCommunicationStatus;
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

    public DeviceNamedCommandProperty getRefreshCommand() {
        return refreshCommand;
    }

    public DeviceNamedCommandProperty getValidateErrorCommand() {
        return validateErrorCommand;
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
}

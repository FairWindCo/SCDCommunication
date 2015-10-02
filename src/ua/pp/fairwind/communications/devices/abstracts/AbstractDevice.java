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
public abstract class AbstractDevice extends PropertyExecutor implements DeviceInterface {
    public static final String IMMEDIATELY_WRITE_FLAG ="immediatelyWrite";
    public static final String PROPERTY_ADDRESS="propertyAddress";
    public static final String COMMAND_REFRESH="REFRESH";
    public static final String COMMAND_VALIDATE="VALIDATE_COMMAND";
    public static final String COMMAND_VALIDATE_LINE1="VALIDATE_LINE1";
    public static final String COMMAND_VALIDATE_LINE2="VALIDATE_LINE2";
    public static final String COMMAND_VALIDATE_ALL="VALIDATE";
    public static final String COMMAND_RANDOM="COMMAND_RANDOM";

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
    protected final DeviceNamedCommandProperty refreshCommand;
    private final DeviceNamedCommandProperty validateErrorCommand;
    private final DeviceNamedCommandProperty validateErrorCommandLine1;
    private final DeviceNamedCommandProperty validateErrorCommandLine2;
    private final DeviceNamedCommandProperty validateAllErrorCommand;
    private final DeviceNamedCommandProperty randomCommand;
    protected final SoftLongProperty retryCount;

    final protected CopyOnWriteArrayList<AbstractProperty> listOfPropertyes=new CopyOnWriteArrayList<>();
    final protected CopyOnWriteArrayList<DeviceNamedCommandProperty> listOfCommands=new CopyOnWriteArrayList<>();
    protected volatile LineInterface primaryLine;
    protected volatile LineInterface secondaryLine;
    protected volatile LineParameters lineparams;

    private final ElementEventListener changeListener=(event,param)->{
        if(event.sourceElement!=null){
            if(event.sourceElement instanceof ValueProperty) {
                ValueProperty<?> hardwarePoperty=(ValueProperty<?>)event.sourceElement;
                boolean isImidiatlyWrite=hardwarePoperty.getAdditionalInfo(IMMEDIATELY_WRITE_FLAG)==null?false:(boolean)hardwarePoperty.getAdditionalInfo(IMMEDIATELY_WRITE_FLAG);
                if(hardwarePoperty.isActive()) {
                    if (event.typeEvent== EventType.ELEMENT_CHANGE || event.typeEvent == EventType.NEED_WRITE_VALUE) {
                        if(event.typeEvent == EventType.ELEMENT_CHANGE && !isImidiatlyWrite){
                            return;
                        }
                        if(getInternalValue(hardwarePoperty)!=null)writeProperty(hardwarePoperty,event);
                    } else if (event.typeEvent == EventType.NEED_READ_VALUE) {
                        readProperty(hardwarePoperty,event);
                    }
                }
            } else if(event.sourceElement instanceof AbstractProperty) {
                AbstractProperty hardwarePoperty=(AbstractProperty)event.sourceElement;
                boolean isImidiatlyWrite=hardwarePoperty.getAdditionalInfo(IMMEDIATELY_WRITE_FLAG)==null?false:(boolean)hardwarePoperty.getAdditionalInfo(IMMEDIATELY_WRITE_FLAG);
                if (event.typeEvent== EventType.ELEMENT_CHANGE || event.typeEvent == EventType.NEED_WRITE_VALUE) {
                        if(event.typeEvent == EventType.ELEMENT_CHANGE && !isImidiatlyWrite){
                            return;
                        }
                        writeProperty(hardwarePoperty,event);
                } else if (event.typeEvent == EventType.NEED_READ_VALUE) {
                        readProperty(hardwarePoperty,event);
                }
            }
        }
    };

    private final ElementEventListener commandListener=(event,param) ->{
        if(event.sourceElement!=null && event.sourceElement instanceof DeviceNamedCommandProperty) {
            DeviceNamedCommandProperty hardwareCommaand=(DeviceNamedCommandProperty)event.sourceElement;
                if (event.typeEvent == EventType.ELEMENT_CHANGE || event.typeEvent == EventType.NEED_WRITE_VALUE || event.typeEvent == EventType.NEED_READ_VALUE) {
                    executeCommandName(hardwareCommaand,event);
                }
        }
    };


    protected DeviceNamedCommandProperty formCommandNameProperty(String name,String command){
        DeviceNamedCommandProperty cmd=new DeviceNamedCommandProperty(name,null,command);
        cmd.addEventListener(commandListener,EventType.ELEMENT_CHANGE,EventType.NEED_READ_VALUE,EventType.NEED_WRITE_VALUE);
        return cmd;
    }

    protected DeviceNamedCommandProperty formCommandNameProperty(String name){
        DeviceNamedCommandProperty cmd=new DeviceNamedCommandProperty(name);
        cmd.addEventListener(commandListener,EventType.ELEMENT_CHANGE,EventType.NEED_READ_VALUE,EventType.NEED_WRITE_VALUE);
        return cmd;
    }

    protected SoftBoolProperty formIndicatorProperty(long address,String name ,boolean initialValue){
        SoftBoolProperty command=new SoftBoolProperty(name, ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY,initialValue);
        command.setAdditionalInfo(PROPERTY_ADDRESS, address);
        return command;
    }

    protected SoftBoolProperty formBoolProperty(long address,String name ,boolean initialValue){
        SoftBoolProperty command=new SoftBoolProperty(name,ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE,initialValue);
        command.setAdditionalInfo(PROPERTY_ADDRESS, address);
        return command;
    }

    protected SoftShortProperty formShortProperty(long address,String name,short initialValue){
        SoftShortProperty command=new SoftShortProperty(name,ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE,initialValue);
        command.setAdditionalInfo(PROPERTY_ADDRESS,address);
        return command;
    }

    protected SoftBoolProperty formDeviceBoolProperty(long address,String name ,boolean initialValue){
        SoftBoolProperty command=new SoftBoolProperty(name, ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE,initialValue);
        command.setAdditionalInfo(PROPERTY_ADDRESS, address);
        //command.addEventListener(changeListener);
        return command;
    }

    protected SoftLongProperty formLongProperty(long address,String name,long initialValue){
        SoftLongProperty command=new SoftLongProperty(name, ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE,initialValue);
        command.setAdditionalInfo(PROPERTY_ADDRESS, address);
        return command;
    }

    protected SoftLongProperty formLongConfigProperty(long address,String name){
        SoftLongProperty command=new SoftLongProperty(name,ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
        command.setAdditionalInfo(PROPERTY_ADDRESS, address);
        return command;
    }

    public AbstractDevice(String codename, String uuid) {
        super(codename, uuid);
        deviceTimeOut=formLongProperty(-2, "device.timeout_property",500L);
        deviceTimeOutPause=formLongProperty(-3, "device.bausebeforeread_property", 0L);
        deviceLastTryCommunicateTime = formLongConfigProperty(-4, "device.lastcommunicationtime_property");
        deviceLastSuccessCommunicateTime=formLongConfigProperty(-5,"device.lastsuccesscommunicationtime_property");
        refreshCommand=formCommandNameProperty(COMMAND_REFRESH);
        validateErrorCommand=formCommandNameProperty(COMMAND_VALIDATE);
        validateErrorCommandLine1=formCommandNameProperty(COMMAND_VALIDATE_LINE1);
        validateErrorCommandLine2=formCommandNameProperty(COMMAND_VALIDATE_LINE2);
        validateAllErrorCommand=formCommandNameProperty(COMMAND_VALIDATE_ALL);
        randomCommand=formCommandNameProperty(COMMAND_RANDOM);
        lastCommunicationStatus      =  formIndicatorProperty(-6, "device.lastcommunicationstatus_property", false);
        errorCommunicationStatus     =  formIndicatorProperty(-7, "device.lasterrorcommunicationstatus_property", false);
        lastCommunicationStatusLine1 =  formIndicatorProperty(-8,"device.lastcommunicationstatusline1_property",false);
        lastCommunicationStatusLine2  =  formIndicatorProperty(-9,"device.lastcommunicationstatusline2_property",false);
        errorCommunicationStatusLine1 =  formIndicatorProperty(-10,"device.lasterrorcommunicationstatusline1_property",false);
        errorCommunicationStatusLine2 =  formIndicatorProperty(-11, "device.lasterrorcommunicationstatusline2_property", false);
        activate = formBoolProperty(-12, "device.activate_property", true);
        activate.addEventListener((event,param)->{if(event.getTypeEvent()==EventType.ELEMENT_CHANGE)super.setEnabled(getInternalValue(activate) == null ? false : (Boolean) getInternalValue(activate));},EventType.ELEMENT_CHANGE);
        deviceWritePause=formLongProperty(-13, "device.bausebeforewrite_property", 0L);
        retryCount=formLongProperty(-14,"device.retrycount_property",1);

        ArrayList<AbstractProperty> list=new ArrayList<>();
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
        cmds.add(randomCommand);
        listOfCommands.addAll(cmds);
    }

    protected CommunicationProtocolRequest formCommunicationProtocolRequest(CommunicationProtocolRequest.REQUEST_TYPE reqType,RequestInformation needOperation,AbstractProperty property,Event sourceEvent){
        if(needOperation==null) return null;
        CommunicationProtocolRequest subrequest=formCommunicationProtocolRequest(reqType,needOperation.getSubrequest(),property,sourceEvent);
        CommunicationProtocolRequest request;
        byte[] buffer=needOperation.getBufferForWrite();
        if(buffer!=null){
            Long devTO=(Long)getInternalValue(deviceTimeOut);
            Long devTOP=(Long)getInternalValue(deviceTimeOutPause);
            Long devWP=(Long)getInternalValue(deviceWritePause);
            long maxRetry=(Long)getInternalValue(retryCount);
            long needReadByteCount=needOperation.getNeddedByteForRead();
            boolean needRollBack=needOperation.isNeedRollBack();
            if(property!=null){
                devTOP=property.getPropertyPauseBeforeRead()>0?property.getPropertyPauseBeforeRead():devTOP+property.getPropertyPauseBeforeReadAddon();
                devTO=property.getPropertyTimeOutRead()>0?property.getPropertyTimeOutRead():devTO+property.getPropertyTimeOutReadAddon();
                devWP=property.getPropertyPauseBeforeWrite()>0?property.getPropertyPauseBeforeWrite():devWP+property.getPropertyPauseBeforeWriteAddon();
                request=CommunicationProtocolRequest.createReuest(reqType,buffer,needReadByteCount,this, devTO,devTOP,devWP,lineparams,property,primaryLine!=null?secondaryLine:null,needRollBack,maxRetry,subrequest,sourceEvent);
                return request;
            } else {
                request=CommunicationProtocolRequest.createReuest(reqType,buffer,needReadByteCount,this, devTO,devTOP,devWP,lineparams,null,primaryLine!=null?secondaryLine:null,needRollBack,maxRetry,subrequest,sourceEvent);
                return request;
            }
        } else {
            return subrequest;
        }
    }

    protected void sendBuffer(CommunicationProtocolRequest.REQUEST_TYPE reqType,RequestInformation requestInformation,AbstractProperty property,Event sourceEvent){
        CommunicationProtocolRequest request=formCommunicationProtocolRequest(reqType,requestInformation,property,sourceEvent);
        if(request!=null) {
            if (primaryLine != null) {
                setInternalValue(deviceLastTryCommunicateTime,System.currentTimeMillis());
                primaryLine.async_communicate(request);
            } else if (secondaryLine != null) {
                setInternalValue(deviceLastTryCommunicateTime,System.currentTimeMillis());
                secondaryLine.async_communicate(request);
            } else {
                if(property!=null)endRequest(property,reqType.getPropertyOperationType());
                fireEvent(EventType.FATAL_ERROR,I18N.getLocalizedString("device.no_line_specified.error"));
            }
        }
    }

    protected void readProperty(AbstractProperty property,Event sourceEvent){
        RequestInformation req=formReadRequest(property);
        if(req!=null) sendBuffer(CommunicationProtocolRequest.REQUEST_TYPE.READ_PROPERTY,req,property,sourceEvent);
    }

    protected void writeProperty(AbstractProperty property,Event sourceEvent) {
        RequestInformation req=formWriteRequest(property);
        if(req!=null) sendBuffer(CommunicationProtocolRequest.REQUEST_TYPE.WRITE_PROPERTY,req,property,sourceEvent);
    }

    protected void executeCommandName(DeviceNamedCommandProperty property,Event sourceEvent){
        RequestInformation req=processCommandRequest(property.getCommand());
        if(req==null){
            property.executed();
        } else {
            sendBuffer(CommunicationProtocolRequest.REQUEST_TYPE.COMMAND_EXECUTE,req,property,sourceEvent);
        }
    }



    private void setLine1Error(){
        setInternalValue(lastCommunicationStatus,false);
        setInternalValue(errorCommunicationStatus, true);
        setInternalValue(lastCommunicationStatusLine1, false);
        setInternalValue(errorCommunicationStatusLine1, true);
    }

    private void setLine2Error(){
        setInternalValue(lastCommunicationStatus,false);
        setInternalValue(errorCommunicationStatus,true);
        setInternalValue(lastCommunicationStatusLine1,false);
        setInternalValue(errorCommunicationStatusLine1,true);
    }

    private void setLine1Success(){
        setInternalValue(lastCommunicationStatus,true);
        setInternalValue(lastCommunicationStatusLine1,true);
        setInternalValue(deviceLastSuccessCommunicateTime,System.currentTimeMillis());
    }

    private void setLine2Success(){
        setInternalValue(lastCommunicationStatus,true);
        setInternalValue(lastCommunicationStatusLine2,true);
        setInternalValue(deviceLastSuccessCommunicateTime,System.currentTimeMillis());
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
                if(!(line != null ? line.equals(secondaryLine) : false)) {
                    if(!answer.sendOverReservLine()) setLine2Error();
                }
                if(answer.getStatus()== CommunicationAnswer.CommunicationResult.TIMEOUT){
                    fireEvent(EventType.TIMEOUT, answer.getInformationMesssage());
                } else {
                    fireEvent(EventType.ERROR, answer.getInformationMesssage());
                }
            } else {
                LineInterface line=answer.getCommunicateOverLine();
                if(line!=null){
                    if(line.equals(primaryLine)){
                        byte[] readBuf=answer.getRecivedMessage();
                        byte[] sendBuf=answer.getRequest()==null?null:answer.getRequest().getBytesForSend();
                        AbstractProperty property=answer.getRequest()==null?null:answer.getRequest().getProperty();
                        if(!processRecivedMessage(readBuf,sendBuf,property,answer.getSourceEvent())){
                            answer.sendOverReservLine();
                            byte[] readBuf1=answer.getRecivedMessage();
                            byte[] sendBuf1=answer.getRequest()==null?null:answer.getRequest().getBytesForSend();
                            AbstractProperty property1=answer.getRequest()==null?null:answer.getRequest().getProperty();
                            try {
                                if (!processRecivedMessage(readBuf1, sendBuf1, property1,answer.getSourceEvent())) {
                                    setLine1Error();
                                    if(!line.equals(secondaryLine)) {
                                        if(!answer.sendOverReservLine()) setLine2Error();
                                    }
                                } else {
                                    setLine2Success();
                                }
                            }catch (Exception e){
                                setLine1Error();
                                answer.invalidate();
                                fireEvent(EventType.FATAL_ERROR,e);
                            }
                        } else {
                            setLine1Success();
                        }

                    } else if(line.equals(secondaryLine)){
                        byte[] readBuf=answer.getRecivedMessage();
                        byte[] sendBuf=answer.getRequest()==null?null:answer.getRequest().getBytesForSend();
                        AbstractProperty property=answer.getRequest()==null?null:answer.getRequest().getProperty();
                        try{
                            if(!processRecivedMessage(readBuf,sendBuf,property,answer.getSourceEvent())){
                                setLine2Error();
                                answer.invalidate();
                            } else {
                                setLine2Success();
                            }
                        }catch (Exception e){
                            setLine1Error();
                            answer.invalidate();
                            fireEvent(EventType.FATAL_ERROR,e);
                        }
                    }
                }
            }
        }
        }catch (Exception ex){
            answer.invalidate();
            fireEvent(EventType.FATAL_ERROR,ex);
        }finally {
            answer.destroy();
        }
    }
    protected abstract boolean processRecivedMessage(final byte[] recivedMessage,final byte[] sendMessage,final AbstractProperty property,final Event sourceEvent);
    protected abstract RequestInformation formReadRequest(AbstractProperty property);
    protected abstract RequestInformation formWriteRequest(AbstractProperty  property);
    public abstract String getDeviceType();

    protected RequestInformation processCommandRequest(String commandName){
        switch(commandName){
            case COMMAND_REFRESH:{
                break;
            }
            case COMMAND_VALIDATE: {
                setInternalValue(errorCommunicationStatus,false);
                break;
            }
            case COMMAND_VALIDATE_LINE1:{
                setInternalValue(errorCommunicationStatusLine1,false);
                break;
            }
            case COMMAND_VALIDATE_LINE2:{
                setInternalValue(errorCommunicationStatusLine1,false);
                break;
            }
            case COMMAND_VALIDATE_ALL: {
                setInternalValue(errorCommunicationStatus,false);
                setInternalValue(errorCommunicationStatusLine1,false);
                setInternalValue(errorCommunicationStatusLine2,false);
                break;
            }
            case COMMAND_RANDOM:{
                listOfPropertyes.parallelStream().forEach(property -> {
                    if(!(property instanceof AbsractCommandProperty)&&(property.getAdditionalInfo("NO_RANDOM")==null||!((Boolean)property.getAdditionalInfo("NO_RANDOM"))))
                    Randomizer.randomizeProperty(property);
                });
                break;
            }
        }
        return null;
    }



    static protected String getUiidFromMap(String deviceName,String name,HashMap<String,String> uuids){
        name=deviceName+":"+name;
        if(uuids==null) return null;
        return uuids.get(name);
    }

    protected String getUiidFromMap(String name,HashMap<String,String> uuids){
        if(name==null || uuids==null) return null;
        return getUiidFromMap(getName(),name,uuids);
    }

    protected void addCommands(DeviceNamedCommandProperty commands){
        if(commands!=null){
            listOfCommands.add(commands);
            commands.addEventListener(commandListener,EventType.ELEMENT_CHANGE);
        }
    }

    protected void addPropertys(AbstractProperty property){
        if (property!=null){
            listOfPropertyes.add(property);
            property.addEventListener(changeListener,EventType.ELEMENT_CHANGE,EventType.NEED_READ_VALUE,EventType.NEED_WRITE_VALUE);
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
    public AbsractCommandProperty getCommandByCodeName(final String name) {
        if(name!=null){
            try{
                AbsractCommandProperty result=listOfCommands.parallelStream().filter(command->name.equals(command.getCodename())).findFirst().get();
                return result;
            } catch (NoSuchElementException ex){
                return null;
            }
        } else return null;
    }

    @Override
    public AbstractProperty getPropertyByCodeName(String name) {
        if(name!=null){
            try {
                AbstractProperty result=listOfPropertyes.parallelStream().filter(command->name.equals(command.getCodename())).findFirst().get();
                return result==null?null:result;
            } catch (NoSuchElementException ex){
                return null;
            }
        } else return null;
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
        return (Long)getInternalValue(deviceTimeOut);
    }

    @Override
    public void setReadTimeOut(long timeout) {
        setInternalValue(deviceTimeOut, timeout);
    }

    @Override
    public long getPauseBeforeRead() { return (Long)getInternalValue(deviceTimeOutPause);   }

    @Override
    public void setPauseBeforeRead(long pause) {
        setInternalValue(deviceTimeOutPause, pause);
    }

    public void setPauseBeforeWrite(long pause) {
        setInternalValue(deviceWritePause, pause);
    }

    @Override
    public Long getLastSuccessExchangeTime() {
        return (Long)getInternalValue(deviceLastSuccessCommunicateTime);
    }

    @Override
    public Long getLastTryExchangeTime() {
        return (Long)getInternalValue(deviceLastSuccessCommunicateTime);
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
        setInternalValue(activate, activeted);
    }

    @Override
    public boolean isActive() {
        return (Boolean)getInternalValue(activate);
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

    public LineInterface getPrimaryLine() {
        return primaryLine;
    }

    public LineInterface getSecondaryLine() {
        return secondaryLine;
    }

    @Override
    public AbsractCommandProperty[] getCommands() {
        if(listOfCommands==null || listOfCommands.size()==0) return null;
        return listOfCommands.toArray(new AbsractCommandProperty[listOfCommands.size()]);
    }

    @Override
    public AbstractProperty[] getPropertys() {
        if(listOfPropertyes==null || listOfPropertyes.size()==0) return null;
        return listOfPropertyes.toArray(new AbstractProperty[listOfPropertyes.size()]);
    }



    @Override
    public void setEnabled(boolean enabled) {
        activate.setValue(enabled);
    }

    public SoftLongProperty getDeviceTimeOut() {
        return deviceTimeOut;
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

    @Override
    public long getMaxRetry() {
        return retryCount.getValue();
    }

    @Override
    public void setMaxRetry(long pause) {
        retryCount.setValue(pause);
    }
}

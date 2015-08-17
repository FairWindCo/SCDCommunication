package ua.pp.fairwind.communications.devices;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.abstractions.SystemEllement;
import ua.pp.fairwind.communications.lines.CommunicationAnswer;
import ua.pp.fairwind.communications.lines.CommunicationProtocol;
import ua.pp.fairwind.communications.lines.LineInterface;
import ua.pp.fairwind.communications.lines.LineParameters;
import ua.pp.fairwind.communications.propertyes.AbsractCommandProperty;
import ua.pp.fairwind.communications.propertyes.DeviceNamedCommandProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.HardWarePropertyInfo;
import ua.pp.fairwind.communications.propertyes.abstraction.HardwareProperty;
import ua.pp.fairwind.communications.propertyes.event.ElementEventListener;
import ua.pp.fairwind.communications.propertyes.event.EventType;
import ua.pp.fairwind.communications.propertyes.hardware.HardBoolProperty;
import ua.pp.fairwind.communications.propertyes.hardware.HardLongProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Сергей on 09.07.2015.
 */
public abstract class AbstractDevice extends SystemEllement implements DeviceInterface {
    protected final HardLongProperty deviceAddress;
    protected final HardLongProperty deviceTimeOut;
    protected final HardLongProperty deviceTimeOutPause;
    protected final HardLongProperty deviceLastTryCommunicateTime;
    protected final HardLongProperty deviceLastSuccessCommunicateTime;
    protected final HardBoolProperty activate;
    private final HardBoolProperty lastCommunicationStatus;
    private final HardBoolProperty errorCommunicationStatus;
    private final HardBoolProperty lastCommunicationStatusLine1;
    private final HardBoolProperty errorCommunicationStatusLine1;
    private final HardBoolProperty lastCommunicationStatusLine2;
    private final HardBoolProperty errorCommunicationStatusLine2;
    private final DeviceNamedCommandProperty refreshCommand;
    private final DeviceNamedCommandProperty validateErrorCommand;
    private final DeviceNamedCommandProperty validateErrorCommandLine1;
    private final DeviceNamedCommandProperty validateErrorCommandLine2;
    private final DeviceNamedCommandProperty validateAllErrorCommand;

    final protected CopyOnWriteArrayList<HardWarePropertyInfo> listOfPropertyes=new CopyOnWriteArrayList<>();
    final protected CopyOnWriteArrayList<DeviceNamedCommandProperty> listOfCommands=new CopyOnWriteArrayList<>();
    protected volatile LineInterface primaryLine;
    protected volatile LineInterface secondaryLine;
    protected volatile LineParameters lineparams;

    private final ElementEventListener changeListener=(element,type,param)->{
        if(element!=null && element instanceof HardWarePropertyInfo) {
            HardWarePropertyInfo hardwarePoperty=(HardWarePropertyInfo)element;

            if(hardwarePoperty.isActive()) {
                if (type == EventType.ELEMENT_CHANGE || type == EventType.NEED_WRITE_VALUE) {
                    if(type == EventType.ELEMENT_CHANGE && !hardwarePoperty.isImidiatlyVrite()){
                        return;
                    }
                    writeProperty(hardwarePoperty);
                } else if (type == EventType.NEED_READ_VALUE) {
                    readProperty(hardwarePoperty);
                }
            }
        }
    };

    private final ElementEventListener commandListener=(element,type,param)->{
        if(element!=null && element instanceof DeviceNamedCommandProperty) {
            DeviceNamedCommandProperty hardwareCommaand=(DeviceNamedCommandProperty)element;
                if (type == EventType.ELEMENT_CHANGE || type == EventType.NEED_WRITE_VALUE || type == EventType.NEED_READ_VALUE) {


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

    protected HardBoolProperty formIndicatorProperty(long address,String name, String description, MessageSubSystem centralSystem,HashMap<String,String> uuids,boolean initialValue){
        HardBoolProperty command=new HardBoolProperty(address,name,getUiidFromMap(name,uuids),description,centralSystem,true,false,initialValue);
        return command;
    }

    protected HardBoolProperty formBoolProperty(long address,String name, String description, MessageSubSystem centralSystem,HashMap<String,String> uuids,boolean initialValue){
        HardBoolProperty command=new HardBoolProperty(address,name,getUiidFromMap(name,uuids),description,centralSystem,true,true,initialValue);
        return command;
    }

    public AbstractDevice(long address,String name, String uuid, String description, MessageSubSystem centralSystem,HashMap<String,String> uuids) {
        super(name, uuid, description, centralSystem);
        deviceAddress=new HardLongProperty(-1,"Address",getUiidFromMap("Address",uuids),"The Device Address used for communication",centralSystem,address);
        deviceTimeOut=new HardLongProperty(-2,"TimeOut",getUiidFromMap("TimeOut",uuids),"The Time Out for ReadLine operation",centralSystem,500L);
        deviceTimeOutPause=new HardLongProperty(-3,"TimeOutPause",getUiidFromMap("ReeadPause",uuids),"Wait pause after WriteLine operation",centralSystem,0L);
        deviceLastTryCommunicateTime=new HardLongProperty(-4,"LastTryCommunicateTime",getUiidFromMap("ReeadPause",uuids),"Wait pause after WriteLine operation",centralSystem,true,false);
        deviceLastSuccessCommunicateTime=new HardLongProperty(-5,"LastSuccessCommunicateTime",getUiidFromMap("ReeadPause",uuids),"Wait pause after WriteLine operation",centralSystem,true,false);
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
        activate = formBoolProperty(-12,"ACTIVATE DEVICE","Status indicator of error comminication with validation on line 2",centralSystem,uuids,true);
        ArrayList<HardWarePropertyInfo> list=new ArrayList<>();
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
        listOfPropertyes.addAll(list);

        ArrayList<DeviceNamedCommandProperty> cmds=new ArrayList<>();
        cmds.add(refreshCommand);
        cmds.add(validateErrorCommand);
        cmds.add(validateErrorCommandLine1);
        cmds.add(validateErrorCommandLine2);
        cmds.add(validateAllErrorCommand);
        listOfCommands.addAll(cmds);
    }

    protected void sendBuffer(byte[] buffer,long needReadByteCount,AbstractProperty property){
        if(buffer!=null){
            CommunicationProtocol request=new CommunicationProtocol(buffer,needReadByteCount,this,deviceTimeOut.getInternalValue(),deviceTimeOutPause.getInternalValue(),lineparams,primaryLine!=null?secondaryLine:null);
            if(primaryLine!=null){
                deviceLastTryCommunicateTime.setInternalValue(System.currentTimeMillis());
                primaryLine.async_communicate(request);
            } else if(secondaryLine!=null){
                deviceLastTryCommunicateTime.setInternalValue(System.currentTimeMillis());
                secondaryLine.async_communicate(request);
            }
        }
    }

    protected void readProperty(HardWarePropertyInfo property){
        RequestInformation req=formReadRequest(property);
        if(req!=null) sendBuffer(req.getBufferForWrite(),req.getNeddedByteForRead(),property.getProperty());
    }

    protected void writeProperty(HardWarePropertyInfo property){
        RequestInformation req=formWriteRequest(property);
        if(req!=null) sendBuffer(req.getBufferForWrite(),req.getNeddedByteForRead(),property.getProperty());
    }

    protected void executeCommandName(DeviceNamedCommandProperty property){
        RequestInformation req=processCommandRequest(property.getName());
        if(req==null){
            property.executed();
        } else {
            sendBuffer(req.getBufferForWrite(),req.getNeddedByteForRead(),property);
        }
    }

    @Override
    public void processRecivedMessage(CommunicationAnswer answer) {
        if(answer!=null){
            lastCommunicationStatus.setInternalValue(true);
            errorCommunicationStatus.setInternalValue(true);
            if(answer.getStatus()!= CommunicationAnswer.CommunicationResult.SUCCESS){
                LineInterface line=answer.getCommunicateOverLine();
                if(line!=null){
                    if(line.equals(primaryLine)){
                        lastCommunicationStatusLine1.setInternalValue(true);
                        errorCommunicationStatusLine1.setInternalValue(true);
                    }
                    if(line.equals(secondaryLine)){
                        lastCommunicationStatusLine2.setInternalValue(true);
                        errorCommunicationStatusLine1.setInternalValue(true);
                    }
                }
                answer.sendOverReservLine();
                if(answer.getStatus()== CommunicationAnswer.CommunicationResult.TIMEOUT){
                    fireEvent(EventType.TIMEOUT, answer.getInformationMesssage());
                } else {
                    fireEvent(EventType.ERROR, answer.getInformationMesssage());
                }
            } else {
                lastCommunicationStatus.setInternalValue(false);
                deviceLastSuccessCommunicateTime.setInternalValue(System.currentTimeMillis());
                LineInterface line=answer.getCommunicateOverLine();
                if(line!=null){
                    if(line.equals(primaryLine)){
                        lastCommunicationStatusLine1.setInternalValue(false);
                    }
                    if(line.equals(secondaryLine)){
                        lastCommunicationStatusLine2.setInternalValue(false);
                    }
                }
                byte[] readBuf=answer.getRecivedMessage();
                byte[] sendBuf=answer.getRequest()==null?null:answer.getRequest().getBytesForSend();
                AbstractProperty property=answer.getRequest()==null?null:answer.getRequest().getProperty();
                processRecivedMessage(readBuf,sendBuf,property);
            }
        }
    }
    protected abstract void processRecivedMessage(final byte[] recivedMessage,final byte[] sendMessage,final AbstractProperty property);
    protected abstract RequestInformation formReadRequest(HardWarePropertyInfo property);
    protected abstract RequestInformation formWriteRequest(HardWarePropertyInfo property);

    protected RequestInformation processCommandRequest(String commandName){
        RequestInformation result=null;
        switch(commandName){
            case "REFRESH":{
                break;
            }
            case "VALIDATE_COMMAND":{
                errorCommunicationStatus.setInternalValue(false);
                break;
            }
            case "VALIDATE_LINE1":{
                errorCommunicationStatusLine1.setInternalValue(false);
                break;
            }
            case "VALIDATE_LINE2":{
                errorCommunicationStatusLine1.setInternalValue(false);
                break;
            }
            case "VALIDATE":{
                errorCommunicationStatus.setInternalValue(false);
                errorCommunicationStatusLine1.setInternalValue(false);
                errorCommunicationStatusLine2.setInternalValue(false);
                break;
            }
        }
        return result;
    }



    private String getUiidFromMap(String name,HashMap<String,String> uuids){
        if(name==null || uuids==null) return null;
        return uuids.get(name);
    }

    protected void addCommands(DeviceNamedCommandProperty commands){
        if(commands!=null){
            listOfCommands.add(commands);
            commands.addEventListener(commandListener);
        }
    }

    protected void addPropertys(HardWarePropertyInfo property){
        if(property!=null){
            listOfPropertyes.add(property);
            if(property.getProperty()!=null){
                property.getProperty().addEventListener(changeListener);
            }
        }
    }

    @Override
    public String[] getCommandsName() {
        return listOfCommands.parallelStream().map(command->command.getName()).toArray(size -> new String[size]);
    }

    @Override
    public String[] getPropertyesName() {
        return listOfPropertyes.parallelStream().map(command->command.getProperty().getName()).toArray(size -> new String[size]);
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
    public HardwareProperty getPropertyInfo(String name) {
        if(name!=null){
            try {
                HardWarePropertyInfo result = listOfPropertyes.parallelStream().filter(command -> name.equals(command.getProperty().getName())).findFirst().get();
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
            HardWarePropertyInfo result=listOfPropertyes.parallelStream().filter(command->name.equals(command.getProperty().getName())).findFirst().get();
            return result==null?null:result.getProperty();
            } catch (NoSuchElementException ex){
                return null;
            }
        } else return null;
    }



     @Override
    public long getAddress() {
        if(deviceAddress.getInternalValue()==null) return -1;
        return deviceAddress.getInternalValue();
    }

    @Override
    public void setAddress(long address) {
        deviceAddress.setInternalValue(address);
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
        return deviceTimeOut.getInternalValue();
    }

    @Override
    public void setReadTimeOut(long timeout) {
        deviceTimeOut.setInternalValue(timeout);
    }

    @Override
    public long getPauseBeforeRead() {
        return deviceTimeOutPause.getInternalValue();
    }

    @Override
    public void setPauseBeforeRead(long pause) {
        deviceTimeOutPause.setInternalValue(pause);
    }

    @Override
    public Long getLastSuccessExchangeTime() {
        return deviceLastSuccessCommunicateTime.getInternalValue();
    }

    @Override
    public Long getLastTryExchangeTime() {
        return deviceLastSuccessCommunicateTime.getInternalValue();
    }


    public SoftLongProperty getDeviceAddressProperty(){
        return (SoftLongProperty)deviceAddress.getProperty();
    }

    public SoftLongProperty getDeviceTimeOutProperty(){
        return (SoftLongProperty)deviceTimeOut.getProperty();
    }

    public SoftLongProperty getDevicePauseBeforeReadProperty(){
        return (SoftLongProperty)deviceTimeOutPause.getProperty();
    }

    public SoftLongProperty getDeviceLastExchangeTimeProperty(){
        return (SoftLongProperty)deviceLastSuccessCommunicateTime.getProperty();
    }

    public SoftLongProperty getDeviceLastTryExchangeProperty(){
        return (SoftLongProperty)deviceLastTryCommunicateTime.getProperty();
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
        activate.setValue(activeted);
    }

    @Override
    public boolean isActive() {
        return false;
    }
}

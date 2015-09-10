package ua.pp.fairwind.communications.devices.hardwaredevices.panDrive;

import jssc.SerialPort;
import ua.pp.fairwind.communications.devices.AbstractDevice;
import ua.pp.fairwind.communications.devices.RSLineDevice;
import ua.pp.fairwind.communications.devices.RequestInformation;
import ua.pp.fairwind.communications.devices.hardwaredevices.panDrive.internatianalisation.I18N_panDrive;
import ua.pp.fairwind.communications.lines.CommunicationLineParameters;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.DeviceNamedCommandProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftBoolProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;

import java.util.HashMap;

/**
 * Created by Сергей on 07.09.2015.
 */
public class StepDriver extends RSLineDevice {
    private final SoftShortProperty speed;
    private final SoftShortProperty statusCode;
    private final SoftLongProperty position;
    private final SoftLongProperty step;

    //CONFIGURATION PARAMETERS
    private final SoftShortProperty magic;
    private final SoftShortProperty rsspeed;
    private final SoftShortProperty rsadress;
    private final SoftShortProperty mode;
    private final SoftLongProperty serialHeartbeat;
    private final SoftShortProperty telegramPauseTime;
    private final SoftShortProperty serialHostAdress;
    private final SoftShortProperty serialSecondAdress;
    private final SoftLongProperty tickTimer;
    private final SoftLongProperty randomNumber;
    private final SoftLongProperty TMCLprogramCounter;
    private final SoftShortProperty TMCLaplicationStatus;
    private final SoftBoolProperty dowloadMode;
    private final SoftBoolProperty endSwichPolarity;
    private final SoftBoolProperty autoStartTMCL;
    private final SoftBoolProperty configurationEpromLock;




    private final DeviceNamedCommandProperty rotateLeft;
    private final DeviceNamedCommandProperty rotateRight;
    private final DeviceNamedCommandProperty motorStop;
    private final DeviceNamedCommandProperty referenceSearch;
    private final DeviceNamedCommandProperty stepLeft;
    private final DeviceNamedCommandProperty stepRight;


    public StepDriver(long address, String name, String uuid, String description, MessageSubSystem centralSystem, HashMap<String, String> uuids) {
        super(address, name, uuid, description, centralSystem, uuids);
        speed=new SoftShortProperty(I18N_panDrive.COMMON.getStringEx("speed"),getUiidFromMap(I18N_panDrive.COMMON.getStringEx("speed"), uuids),I18N_panDrive.COMMON.getStringEx("speed_title"),centralSystem, ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        speed.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 1L);
        position=new SoftLongProperty("POSITION",getUiidFromMap("POSITION",uuids),"Номер измерения",centralSystem,ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        position.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 2L);
        statusCode=new SoftShortProperty("STATUSCODE",getUiidFromMap("STATUSCODE",uuids),"Статус последней команды",centralSystem,ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
        statusCode.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 3L);
        step=new SoftLongProperty("STEP",getUiidFromMap("STEP",uuids),"Шаг",centralSystem,ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        step.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 4L);

        /*CONFIGURATION PROPERTY*/
        magic=new SoftShortProperty("MAGIC",getUiidFromMap("MAGIC",uuids),"Setting this parameter to a different value as\n" +
                "$E4 will cause re-initialization of the axis and\n" +
                "global parameters (to factory defaults) after\n" +
                "the next power up. This is useful in case of\n" +
                "miss-configuration",centralSystem, ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        magic.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 64L);
        rsspeed=new SoftShortProperty("RS_BAUD_RATE",getUiidFromMap("RS_BAUD_RATE",uuids),"RS485 baud rate",centralSystem, ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        rsspeed.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 65L);
        rsadress=new SoftShortProperty("RS_ADDRESS",getUiidFromMap("RS_ADDRESS",uuids),"RS485 Serial address",centralSystem, ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        rsadress.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 66L);
        mode=new SoftShortProperty("MODE",getUiidFromMap("MODE",uuids),"ASCII mode",centralSystem,ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        mode.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 67L);
        serialHeartbeat=new SoftLongProperty("HEATBEAT",getUiidFromMap("HEATBEAT",uuids),"Serial heartbeat for the RS485 interface. If this\n" +
                "time limit is up and no further command is\n" +
                "noticed the motor will be stopped.\n" +
                "0 – parameter is disabled",centralSystem,ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        serialHeartbeat.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 68L);
        configurationEpromLock=new SoftBoolProperty("EPROMLOCK",getUiidFromMap("EPROMLOCK",uuids),"Configuration\n" +
                "EEPROM lock flag",centralSystem,ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        configurationEpromLock.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 73L);
        telegramPauseTime=new SoftShortProperty("RS_PAUSE",getUiidFromMap("RS_PAUSE",uuids),"Pause time before the reply via RS485 is sent.\n" +
                "For RS485 it is often necessary to set it to 15\n" +
                "(for RS485 adapters controlled by the RTS pin).",centralSystem,ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        telegramPauseTime.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 75L);
        serialHostAdress=new SoftShortProperty("RS_HOSTADDRESS",getUiidFromMap("RS_HOSTADDRESS",uuids),"Host address used in the reply telegrams sent\n" +
                "back via RS485.",centralSystem, ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        serialHostAdress.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 76L);
        serialSecondAdress=new SoftShortProperty("RS_SECOND_ADDRESS",getUiidFromMap("RS_SECOND_ADDRESS",uuids),"RS485 Second module (target) address. This is the\n" +
                "group or broadcast address of the module. ",centralSystem, ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        serialSecondAdress.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 87L);

        TMCLaplicationStatus=new SoftShortProperty("TMCL_STATUS",getUiidFromMap("TMCL_STATUS",uuids),"TMCL application status",centralSystem, ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        TMCLaplicationStatus.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 128L);
        dowloadMode=new SoftBoolProperty("D_MODE",getUiidFromMap("D_MODE",uuids),"Download mode",centralSystem, ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        dowloadMode.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 129L);
        autoStartTMCL=new SoftBoolProperty("TMCL_AutoStart",getUiidFromMap("TMCL_AutoStart",uuids),"Auto start mode",centralSystem, ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        autoStartTMCL.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 77L);
        endSwichPolarity=new SoftBoolProperty("SWITCH_POLARITY",getUiidFromMap("SWITCH_POLARITY",uuids),"End switch polarity",centralSystem, ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        endSwichPolarity.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 79L);

        tickTimer=new SoftLongProperty("TICK",getUiidFromMap("TICK",uuids),"A 32 bit counter that gets incremented by one\n" +
                "every millisecond. It can also be reset to any\n" +
                "start value",centralSystem,ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        tickTimer.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 132L);
        randomNumber=new SoftLongProperty("RANDOM",getUiidFromMap("RANDOM",uuids),"Choose a random number.",centralSystem,ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        randomNumber.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 133L);
        TMCLprogramCounter=new SoftLongProperty("PROGRAM_COUNTER",getUiidFromMap("PROGRAM_COUNTER",uuids),"The index of the currently executed TMCL\n" +
                "instruction. ",centralSystem,ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
        TMCLprogramCounter.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 130L);
        //-----------------------------------------------------------------------------
        rotateLeft=formCommandNameProperty("ROL", "ROTATE LEFT", centralSystem, uuids);
        rotateRight=formCommandNameProperty("ROR", "ROTATE RIGHT", centralSystem, uuids);
        motorStop=formCommandNameProperty("MST", "MOTOR STOP", centralSystem, uuids);
        referenceSearch=formCommandNameProperty("MST", "REFERENCE SEARCH", centralSystem, uuids);
        stepLeft=formCommandNameProperty("STPL", "MOTOR STEP LEFT", centralSystem, uuids);
        stepRight=formCommandNameProperty("STPR", "MOTOR STEP RIGHT", centralSystem, uuids);
        setLineParameters(new CommunicationLineParameters(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE,
                SerialPort.FLOWCONTROL_NONE));
        addPropertys(position);
        addPropertys(step);
        addPropertys(speed);

        addPropertys(magic);
        addPropertys(rsspeed);
        addPropertys(rsadress);
        addPropertys(mode);
        addPropertys(serialHeartbeat);
        addPropertys(telegramPauseTime);
        addPropertys(serialHostAdress);
        addPropertys(serialSecondAdress);
        addPropertys(tickTimer);
        addPropertys(randomNumber);
        addPropertys(TMCLprogramCounter);
        addPropertys(TMCLaplicationStatus);
        addPropertys(dowloadMode);
        addPropertys(endSwichPolarity);
        addPropertys(autoStartTMCL);
        addPropertys(configurationEpromLock);

        addCommands(rotateLeft);
        addCommands(rotateRight);
        addCommands(motorStop);
        addCommands(referenceSearch);
        addCommands(stepLeft);
        addCommands(stepRight);
    }

    public int calculateCRC(byte[] buf,int pos,int len) {
        if(buf==null || len==0) {
            return 0;
        }
        if(buf.length<len) len=buf.length;
        int sum=0;
        for(int i=pos;i<len;i++) {
            sum+=(buf[i] & 0xFF);
        }
        return sum & 0xFF;
    }

    @Override
    protected boolean processRecivedMessage(byte[] recivedMessage, byte[] sendMessage, AbstractProperty property) {
        if(recivedMessage!=null && recivedMessage.length>8) {
            long deviceaddress = deviceAddress.getValue();
            for(int i=0;i<recivedMessage.length-8;i++) {
                if(recivedMessage[i+1]==(deviceaddress &0xFF)){
                    int crc=calculateCRC(recivedMessage, i, 8);
                    int bcrc=(recivedMessage[i+8])&0xFF;
                    if(crc==bcrc) {
                        byte ststus=recivedMessage[i+2];
                        //byte module=recivedMessage[i+1];
                        byte commandNum=recivedMessage[i+3];
                        statusCode.setHardWareInternalValue((short)ststus);
                        switch (commandNum){
                            case 6:{
                                long value=0;
                                value|=((recivedMessage[i+4]&0xFF)<<24);
                                value|=((recivedMessage[i+5]&0xFF)<<16);
                                value|=((recivedMessage[i+6]&0xFF)<< 8);
                                value|=((recivedMessage[i+7]&0xFF)    );
                                if(property==speed){
                                    speed.setHardWareInternalValue((short)(value&0xFFFF));
                                } else
                                if(property==position){
                                    position.setHardWareInternalValue(value);
                                }
                                break;
                            }
                            case 10:{
                                Object vv=property.getAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS);
                                if(vv==null)return false;
                                int module=((Long)vv).intValue();
                                long value=0;
                                value|=((recivedMessage[i+4]&0xFF)<<24);
                                value|=((recivedMessage[i+5]&0xFF)<<16);
                                value|=((recivedMessage[i+6]&0xFF)<< 8);
                                value|=((recivedMessage[i+7]&0xFF)    );
                                setupConfigurationParameters(module,value);
                                break;
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected RequestInformation formReadRequest(AbstractProperty property) {
        if(property==position) {
            long deviceaddress = deviceAddress.getValue();
            //SET ABSOLUTE POSITION
            byte[] buffer = new byte[9];
            buffer[0] = (byte) (deviceaddress & 0xFF);
            buffer[1] = (byte) 0x06;
            buffer[2] = (byte) 0x01;
            buffer[3] = (byte) 0x00;
            //VALUE-----------------------------------------
            buffer[4] = (byte)0x00;
            buffer[5] = (byte)0x00;
            buffer[6] = (byte)0x00;
            buffer[7] = (byte)0x00;
            //CRC-----------------------------------------
            buffer[8] =(byte)(calculateCRC(buffer,0,8)&0xFF);
            return new RequestInformation(buffer, 9);
        } else if(property==speed) {
            long deviceaddress = deviceAddress.getValue();
            //SET RELATIVE POSITION
            byte[] buffer = new byte[9];
            buffer[0] = (byte) (deviceaddress & 0xFF);
            buffer[1] = (byte) 0x06;
            buffer[2] = (byte) 0x03;
            buffer[3] = (byte) 0x00;
            //VALUE-----------------------------------------
            buffer[4] = (byte)0x00;
            buffer[5] = (byte)0x00;
            buffer[6] = (byte)0x00;
            buffer[7] = (byte)0x00;
            //CRC-----------------------------------------
            buffer[8] =(byte)(calculateCRC(buffer,0,8)&0xFF);
            return new RequestInformation(buffer, 9);
        } else {
            return formReadRequestForConfigurationParameters(property);
        }
    }

    @Override
    protected RequestInformation formWriteRequest(AbstractProperty property) {
        if(property==position) {
            long deviceaddress = deviceAddress.getValue();
            long pos=position.getValue()!=null?position.getValue():0;
            //SET ABSOLUTE POSITION
            byte[] buffer = new byte[9];
            buffer[0] = (byte) (deviceaddress & 0xFF);
            buffer[1] = (byte) 0x04;
            buffer[2] = (byte) 0x00;
            buffer[3] = (byte) 0x00;
            //VALUE-----------------------------------------
            buffer[4] = (byte)((pos>>24)&0xFF);
            buffer[5] = (byte)((pos>>16)&0xFF);
            buffer[6] = (byte)((pos>> 8)&0xFF);
            buffer[7] = (byte)((pos    )&0xFF);
            //CRC-----------------------------------------
            buffer[8] =(byte)(calculateCRC(buffer,0,8)&0xFF);
            return new RequestInformation(buffer, 9);
        } else if(property==step) {
            long deviceaddress = deviceAddress.getValue();
            long pos=step.getValue()!=null?step.getValue():0;
            //SET RELATIVE POSITION
            byte[] buffer = new byte[9];
            buffer[0] = (byte) (deviceaddress & 0xFF);
            buffer[1] = (byte) 0x04;
            buffer[2] = (byte) 0x01;
            buffer[3] = (byte) 0x00;
            //VALUE-----------------------------------------
            buffer[4] = (byte)((pos>>24)&0xFF);
            buffer[5] = (byte)((pos>>16)&0xFF);
            buffer[6] = (byte)((pos>> 8)&0xFF);
            buffer[7] = (byte)((pos    )&0xFF);
            //CRC-----------------------------------------
            buffer[8] =(byte)(calculateCRC(buffer,0,8)&0xFF);
            return new RequestInformation(buffer, 9);
        } else if(property==speed) {
            long deviceaddress = deviceAddress.getValue();
            long pos=speed.getValue()!=null?speed.getValue():0;
            //SET SPEED
            byte[] buffer = new byte[9];
            buffer[0] = (byte) (deviceaddress & 0xFF);
            buffer[1] = (byte) 0x05;
            buffer[2] = (byte) 0x03;
            buffer[3] = (byte) 0x00;
            //VALUE-----------------------------------------
            buffer[4] = (byte)0x00;
            buffer[5] = (byte)0x00;
            buffer[6] = (byte)((pos>> 8)&0xFF);
            buffer[7] = (byte)((pos    )&0xFF);
            //CRC-----------------------------------------
            buffer[8] =(byte)(calculateCRC(buffer,0,8)&0xFF);
            return new RequestInformation(buffer, 9);
        } else {
            return formWriteRequestForConfigurationParameters(property);
        }
    }

    @Override
    public String getDeviceType() {
        return "PanDrive Step Motor";
    }

    @Override
    protected RequestInformation processCommandRequest(String commandName) {
        long deviceaddress = deviceAddress.getValue();
        short speeding=speed.getValue()!=null?speed.getValue():0;
        switch (commandName) {
            case "ROL": {
                //ROTATE LEFT
                byte[] buffer = new byte[9];
                buffer[0] = (byte) (deviceaddress & 0xFF);
                buffer[1] = (byte) 0x01;
                buffer[2] = (byte) 0x00;
                buffer[3] = (byte) 0x00;
                //VALUE-----------------------------------------
                buffer[4] = (byte)0x00;//((speeding>>24)&0xFF);
                buffer[5] = (byte)0x00;//((speeding>>16)&0xFF);
                buffer[6] = (byte)((speeding>> 8)&0xFF);
                buffer[7] = (byte)((speeding    )&0xFF);
                //CRC-----------------------------------------
                buffer[8] =(byte)(calculateCRC(buffer,0,8)&0xFF);
                return new RequestInformation(buffer, 9);
            }
            case "ROR": {
                //ROTATE RIGHT
                byte[] buffer = new byte[9];
                buffer[0] = (byte) (deviceaddress & 0xFF);
                buffer[1] = (byte) 0x02;
                buffer[2] = (byte) 0x00;
                buffer[3] = (byte) 0x00;
                //VALUE-----------------------------------------
                buffer[4] = (byte)0x00;//((speeding>>24)&0xFF);
                buffer[5] = (byte)0x00;//((speeding>>16)&0xFF);
                buffer[6] = (byte)((speeding>> 8)&0xFF);
                buffer[7] = (byte)((speeding    )&0xFF);
                //CRC-----------------------------------------
                buffer[8] =(byte)(calculateCRC(buffer,0,8)&0xFF);
                return new RequestInformation(buffer, 9);
            }
            case "MST": {
                //MOTOR STOP
                byte[] buffer = new byte[9];
                buffer[0] = (byte) (deviceaddress & 0xFF);
                buffer[1] = (byte) 0x03;
                buffer[2] = (byte) 0x00;
                buffer[3] = (byte) 0x00;
                //VALUE-----------------------------------------
                buffer[4] = (byte)0x00;
                buffer[5] = (byte)0x00;
                buffer[6] = (byte)0x00;
                buffer[7] = (byte)0x00;
                //CRC-----------------------------------------
                buffer[8] =(byte)(calculateCRC(buffer,0,8)&0xFF);
                return new RequestInformation(buffer, 9);
            }
        }
        return super.processCommandRequest(commandName);
    }

    private void setupConfigurationParameters(int number,long value){
        switch (number){
            case 64:magic.setHardWareInternalValue((short) value);break;
            case 65:rsspeed.setHardWareInternalValue((short)value);break;
            case 66:rsadress.setHardWareInternalValue((short)value);break;
            case 67:mode.setHardWareInternalValue((short)value);break;
            case 68:serialHeartbeat.setHardWareInternalValue(value);break;
            case 73:{
                if(value==0){
                    configurationEpromLock.setHardWareInternalValue(false);
                } else {
                    configurationEpromLock.setHardWareInternalValue(true);
                }
                break;
            }
            case 75:telegramPauseTime.setHardWareInternalValue((short)value);break;
            case 76:serialHostAdress.setHardWareInternalValue((short)value);break;
            case 77:{
                if(value==0){
                    autoStartTMCL.setHardWareInternalValue(false);
                } else {
                    autoStartTMCL.setHardWareInternalValue(true);
                }
                break;
            }
            case 79:{
                if(value==0){
                    endSwichPolarity.setHardWareInternalValue(false);
                } else {
                    endSwichPolarity.setHardWareInternalValue(true);
                }
                break;
            }
            case 128:TMCLaplicationStatus.setHardWareInternalValue((short)value);break;
            case 87:serialSecondAdress.setHardWareInternalValue((short)value);break;
            case 129:{
                if(value==0){
                    dowloadMode.setHardWareInternalValue(false);
                } else {
                    dowloadMode.setHardWareInternalValue(true);
                }
                break;
            }
            case 130:TMCLprogramCounter.setHardWareInternalValue(value);break;
            case 132:tickTimer.setHardWareInternalValue(value);break;
            case 133:randomNumber.setHardWareInternalValue(value);break;
        }
    }

    private RequestInformation formReadRequestForConfigurationParameters(int number){
        byte[] buffer = new byte[9];
        long deviceaddress = deviceAddress.getValue();
        buffer[0] = (byte) (deviceaddress & 0xFF);
        buffer[1] = (byte) 0x0A;
        buffer[2] = (byte) (number&0xFF);
        buffer[3] = (byte) 0x00;
        //VALUE-----------------------------------------
        buffer[4] = (byte)0x00;
        buffer[5] = (byte)0x00;
        buffer[6] = (byte)0x00;
        buffer[7] = (byte)0x00;
        //CRC-----------------------------------------
        buffer[8] =(byte)(calculateCRC(buffer,0,8)&0xFF);
        return new RequestInformation(buffer, 9);
    }
    private RequestInformation formWriteRequestForConfigurationParameters(int number,long value){
        byte[] buffer = new byte[9];
        long deviceaddress = deviceAddress.getValue();
        buffer[0] = (byte) (deviceaddress & 0xFF);
        buffer[1] = (byte) 0x09;
        buffer[2] = (byte) number;
        buffer[3] = (byte) 0x00;
        //VALUE-----------------------------------------
        buffer[4] = (byte)((value>>24)&0xFF);
        buffer[5] = (byte)((value>>16)&0xFF);
        buffer[6] = (byte)((value>> 8)&0xFF);
        buffer[7] = (byte)((value    )&0xFF);
        //CRC-----------------------------------------
        buffer[8] =(byte)(calculateCRC(buffer,0,8)&0xFF);
        return new RequestInformation(buffer, 9);
    }

    private RequestInformation formReadRequestForConfigurationParameters(AbstractProperty property){
        if(property!=null){
            Object value=property.getAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS);
            if(value==null || !(value instanceof Long)) return null;
            Long paramAddress=(Long)value;
            int padress=paramAddress.intValue();
            return formReadRequestForConfigurationParameters(padress);
        } else {
            return null;
        }
    }

    private RequestInformation formWriteRequestForConfigurationParameters(AbstractProperty property){
        if(property!=null){
            Object value=property.getAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS);
            if(value==null || !(value instanceof Long)) return null;
            Long paramAddress=(Long)value;
            int padress=paramAddress.intValue();
            switch (padress){
                case 64:{
                    long setupValue=magic.getValue()==null?0:magic.getValue();
                    return formWriteRequestForConfigurationParameters(padress,setupValue);
                }
                case 65:{
                    long setupValue=rsspeed.getValue()==null?0:rsspeed.getValue()&0xF;
                    return formWriteRequestForConfigurationParameters(padress,setupValue);
                }
                case 66:{
                    long setupValue=rsadress.getValue()==null?0:rsadress.getValue()&0xFF;
                    return formWriteRequestForConfigurationParameters(padress,setupValue);
                }
                case 67:{
                    long setupValue=mode.getValue()==null?0:mode.getValue()&0xFF;
                    return formWriteRequestForConfigurationParameters(padress,setupValue);
                }
                case 68:{
                    long setupValue=serialHeartbeat.getValue()==null?0:serialHeartbeat.getValue();
                    return formWriteRequestForConfigurationParameters(padress,setupValue);
                }
                case 73:{
                    boolean val=configurationEpromLock.getValue()==null?false:configurationEpromLock.getValue();
                    long setupValue=val?0x1234:0x4321;
                    return formWriteRequestForConfigurationParameters(padress,setupValue);
                }
                case 75:{
                    long setupValue=telegramPauseTime.getValue()==null?0:telegramPauseTime.getValue()&0xFF;
                    return formWriteRequestForConfigurationParameters(padress,setupValue);
                }
                case 76:{
                    long setupValue=serialHostAdress.getValue()==null?0:serialHostAdress.getValue()&0xFF;
                    return formWriteRequestForConfigurationParameters(padress,setupValue);
                }
                case 77:{
                    boolean val=autoStartTMCL.getValue()==null?false:autoStartTMCL.getValue();
                    long setupValue=val?0:1;
                    return formWriteRequestForConfigurationParameters(padress,setupValue);
                }
                case 79:{
                    boolean val=endSwichPolarity.getValue()==null?false:endSwichPolarity.getValue();
                    long setupValue=val?0:1;
                    return formWriteRequestForConfigurationParameters(padress,setupValue);
                }
                case 87:{
                    long setupValue=serialSecondAdress.getValue()==null?0:serialSecondAdress.getValue()&0xFF;
                    return formWriteRequestForConfigurationParameters(padress,setupValue);
                }
                case 132:{
                    long setupValue=tickTimer.getValue()==null?0:tickTimer.getValue();
                    return formWriteRequestForConfigurationParameters(padress,setupValue);
                }
                case 133:{
                    long setupValue=randomNumber.getValue()==null?0:randomNumber.getValue();
                    return formWriteRequestForConfigurationParameters(padress,setupValue);
                }
            }
            return null;
        } else {
            return null;
        }
    }

    public SoftShortProperty getSpeed() {
        return speed;
    }

    public SoftShortProperty getStatusCode() {
        return statusCode;
    }

    public SoftLongProperty getPosition() {
        return position;
    }

    public SoftLongProperty getStep() {
        return step;
    }

    public DeviceNamedCommandProperty getRotateLeft() {
        return rotateLeft;
    }

    public DeviceNamedCommandProperty getRotateRight() {
        return rotateRight;
    }

    public DeviceNamedCommandProperty getMotorStop() {
        return motorStop;
    }

    public DeviceNamedCommandProperty getReferenceSearch() {
        return referenceSearch;
    }

    public DeviceNamedCommandProperty getStepLeft() {
        return stepLeft;
    }

    public DeviceNamedCommandProperty getStepRight() {
        return stepRight;
    }

    public SoftShortProperty getMagic() {
        return magic;
    }

    public SoftShortProperty getRsspeed() {
        return rsspeed;
    }

    public SoftShortProperty getRsadress() {
        return rsadress;
    }

    public SoftShortProperty getMode() {
        return mode;
    }

    public SoftLongProperty getSerialHeartbeat() {
        return serialHeartbeat;
    }

    public SoftShortProperty getTelegramPauseTime() {
        return telegramPauseTime;
    }

    public SoftShortProperty getSerialHostAdress() {
        return serialHostAdress;
    }

    public SoftShortProperty getSerialSecondAdress() {
        return serialSecondAdress;
    }

    public SoftLongProperty getTickTimer() {
        return tickTimer;
    }

    public SoftLongProperty getRandomNumber() {
        return randomNumber;
    }

    public SoftLongProperty getTMCLprogramCounter() {
        return TMCLprogramCounter;
    }

    public SoftShortProperty getTMCLaplicationStatus() {
        return TMCLaplicationStatus;
    }

    public SoftBoolProperty getDowloadMode() {
        return dowloadMode;
    }

    public SoftBoolProperty getEndSwichPolarity() {
        return endSwichPolarity;
    }

    public SoftBoolProperty getAutoStartTMCL() {
        return autoStartTMCL;
    }

    public SoftBoolProperty getConfigurationEpromLock() {
        return configurationEpromLock;
    }
}

package ua.pp.fairwind.communications.devices.panDrive;

import jssc.SerialPort;
import ua.pp.fairwind.communications.devices.AbstractDevice;
import ua.pp.fairwind.communications.devices.RequestInformation;
import ua.pp.fairwind.communications.lines.CommunicationLineParameters;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.DeviceNamedCommandProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;

import java.util.HashMap;

/**
 * Created by Сергей on 07.09.2015.
 */
public class StepDriver extends AbstractDevice {
    private final SoftShortProperty speed;
    private final SoftShortProperty statusCode;
    private final SoftLongProperty position;
    private final SoftLongProperty step;
    private final DeviceNamedCommandProperty rotateLeft;
    private final DeviceNamedCommandProperty rotateRight;
    private final DeviceNamedCommandProperty motorStop;
    private final DeviceNamedCommandProperty referenceSearch;
    private final DeviceNamedCommandProperty stepLeft;
    private final DeviceNamedCommandProperty stepRight;


    public StepDriver(long address, String name, String uuid, String description, MessageSubSystem centralSystem, HashMap<String, String> uuids) {
        super(address, name, uuid, description, centralSystem, uuids);
        speed=new SoftShortProperty("SPEED",getUiidFromMap("SPEED",uuids),"Номер измерения",centralSystem, ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
        speed.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 001L);
        position=new SoftLongProperty("POSITION",getUiidFromMap("POSITION",uuids),"Номер измерения",centralSystem,ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        position.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 002L);
        statusCode=new SoftShortProperty("POSITION",getUiidFromMap("POSITION",uuids),"Номер измерения",centralSystem,ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
        statusCode.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 003L);
        step=new SoftLongProperty("POSITION",getUiidFromMap("POSITION",uuids),"Номер измерения",centralSystem,ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
        step.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 004L);
        rotateLeft=formCommandNameProperty("ROL", "ROTATE LEFT", centralSystem, uuids);
        rotateRight=formCommandNameProperty("ROR", "ROTATE RIGHT", centralSystem, uuids);
        motorStop=formCommandNameProperty("MST", "MOTOR STOP", centralSystem, uuids);
        referenceSearch=formCommandNameProperty("MST", "REFERENCE SEARCH", centralSystem, uuids);
        stepLeft=formCommandNameProperty("STPL", "MOTOR STEP LEFT", centralSystem, uuids);
        stepRight=formCommandNameProperty("STPR", "MOTOR STEP RIGHT", centralSystem, uuids);
        setLineParameters(new CommunicationLineParameters(SerialPort.BAUDRATE_9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE,
                SerialPort.FLOWCONTROL_NONE));
        addPropertys(position);
        addPropertys(step);
        addPropertys(speed);

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
                    int bcrc=recivedMessage[i+8];
                    if(crc==bcrc) {
                        byte ststus=recivedMessage[i+2];
                        //byte module=recivedMessage[i+1];
                        byte commandNum=recivedMessage[i+3];
                        statusCode.setHardWareInternalValue((short)ststus);
                        switch (commandNum){
                            case 6:{
                                long value=0;
                                value+=((recivedMessage[i+4]<<24)&0xFF);
                                value+=((recivedMessage[i+4]<<16)&0xFF);
                                value+=((recivedMessage[i+4]<< 8)&0xFF);
                                value+=((recivedMessage[i+4]    )&0xFF);
                                if(property==speed){
                                    speed.setHardWareInternalValue((short)(value&0xFFFF));
                                } else
                                if(property==position){
                                    position.setHardWareInternalValue(value);
                                }
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
            return null;
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
            //SET RELATIVE POSITION
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
            return null;
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
}

package ua.pp.fairwind.communications.devices.hardwaredevices.modbus;

import jssc.SerialPort;
<<<<<<< HEAD
import ua.pp.fairwind.communications.abstractions.annottations.Device;
=======
>>>>>>> origin/master
import ua.pp.fairwind.communications.devices.RequestInformation;
import ua.pp.fairwind.communications.devices.abstracts.RSLineDevice;
import ua.pp.fairwind.communications.lines.lineparams.CommunicationLineParameters;
import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.messagesystems.event.EventType;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.groups.GroupProperty;
import ua.pp.fairwind.communications.utils.ModBusProtocol;

import java.io.*;

/**
 * Created by Сергей on 11.09.2015.
 */
<<<<<<< HEAD
@Device("MODBUS")
=======
>>>>>>> origin/master
public class ModBusDevice extends RSLineDevice {
    public static final String MODBUS_ADDRESS="MODBUS_ADDRESS";
    public static final String MODBUS_READ_FUNCTION="MODBUS_READ_FUNCTION";
    public static final String MODBUS_WRITE_FUNCTION="MODBUS_WRITE_FUNCTION";
    public static final String MODBUS_BYTE_SIZE="MODBUS_BYTE_SIZE";
    protected final GroupProperty deviceProperties;


    public ModBusDevice(long address, String codename, String uuid) {
        super(address, codename, uuid);

        deviceProperties =new GroupProperty("modbus.deviceProperties", null);
        deviceProperties.setReadAccepted(false);
        deviceProperties.setWriteAccepted(false);
        addPropertys(deviceProperties);

        setLineParameters(new CommunicationLineParameters(SerialPort.BAUDRATE_19200, SerialPort.DATABITS_8, SerialPort.PARITY_NONE, SerialPort.STOPBITS_2));
        deviceTimeOut.setValue(500L);
    }



    @Override
    protected boolean processRecivedMessage(byte[] recivedMessage, byte[] sendMessage, AbstractProperty property, final Event sourceEvent) {
        long deviceAddress = super.deviceAddress.getValue();
        try {
            return ModBusProtocol.processResponse(recivedMessage, property, (int) deviceAddress, sourceEvent);
        } catch (IllegalArgumentException e) {
            fireEvent(EventType.ERROR, e.getLocalizedMessage());
            return false;
        }
    }

    @Override
    protected RequestInformation formReadRequest(AbstractProperty property) {
        if(property==null)return null;
        long deviceAddress = super.deviceAddress.getValue();
        Object size = property.getAdditionalInfo(MODBUS_BYTE_SIZE);
        if (size == null && !(size instanceof Number)) {
            throw new IllegalArgumentException("NO PROPERTY_NUM IN PROPERTY");
        }
        int mobus_byte_size = ((Number) size).intValue();
        ModBusProtocol.ModBusProtocolRequestInformation request=ModBusProtocol.formReadRequestModBus(property, mobus_byte_size, (int) deviceAddress);
        return request==null?null:request.getRequest(false);
    }

    @Override
    protected RequestInformation formWriteRequest(AbstractProperty property) {
        long deviceAddress = super.deviceAddress.getValue();
        Object size = property.getAdditionalInfo(MODBUS_BYTE_SIZE);
        if (size == null && !(size instanceof Number)) {
            throw new IllegalArgumentException("NO PROPERTY_NUM IN PROPERTY");
        }
        int mobus_byte_size = ((Number) size).intValue();
        ModBusProtocol.ModBusProtocolRequestInformation request=ModBusProtocol.formWriteRequestModBus(property, mobus_byte_size, (int) deviceAddress);
        return request==null?null:request.getRequest(false);
    }



    @Override
    public String getDeviceType() {
        return "MODBUS DEVICE";
    }


    public GroupProperty getDeviceProperties() {
        return deviceProperties;
    }


    public void saveBinaryParameters(File file){
        if(file!=null) {
            if(!file.exists()){
                try {
                    if (!file.createNewFile()) {
                        fireEvent(EventType.ERROR, "FILE CREATION ERROR"+file.getAbsolutePath());
                    }
                }catch (IOException e){
                    fireEvent(EventType.ERROR, e.getLocalizedMessage());
                }
            }

            if (file.canWrite()) {
                byte[] conf = ModBusProtocol.getValueForTransfer(deviceProperties);
                try (OutputStream out = new FileOutputStream(file)) {
                    out.write(conf);
                } catch (IOException e) {
                    fireEvent(EventType.ERROR, e.getLocalizedMessage());
                }
            }
        }
    }

    public void loadBinaryParameters(File file){
        if(file!=null && file.canRead()){
            byte[] conf=new byte[ModBusProtocol.calcultaeBufferSize(deviceProperties)];
            byte[] boot=new byte[4 + (17 * 4)];
            if(conf!=null && boot!=null && conf.length>0 && boot.length>0) {
                try (InputStream out = new FileInputStream(file)) {
                    out.read(conf);
                    out.read(boot);
                } catch (IOException e) {
                    fireEvent(EventType.ERROR, e.getLocalizedMessage());
                }
                ModBusProtocol.setValueFromTransfer(deviceProperties, conf, 0, conf.length, null);
            }
        }
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> origin/master

package ua.pp.fairwind.communications.devices.hardwaredevices.arg.micro;

import jssc.SerialPort;
import ua.pp.fairwind.communications.abstractions.annottations.Device;
import ua.pp.fairwind.communications.devices.RequestInformation;
import ua.pp.fairwind.communications.devices.abstracts.AbstractDevice;
import ua.pp.fairwind.communications.devices.abstracts.RSLineDevice;
import ua.pp.fairwind.communications.lines.lineparams.CommunicationLineParameters;
import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftFloatProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;
import ua.pp.fairwind.communications.utils.CommunicationUtils;

/**
 * Created by Сергей on 07.09.2015.
 */
@Device("ArgMicro")
public class ArgMicroDevice extends RSLineDevice {
    private final SoftFloatProperty rate;
    private final SoftShortProperty numberMeasurementm;

    public ArgMicroDevice(long address, String codename, String uuid) {
        super(address, codename, uuid);
        rate = formRateProperty(002L, "ARGMICRO.RATE");
        numberMeasurementm = new SoftShortProperty("ARGMICRO.NUMBER_MEASUREMENT", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
        numberMeasurementm.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 001L);
        setLineParameters(new CommunicationLineParameters(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.PARITY_NONE, SerialPort.STOPBITS_1,
                SerialPort.FLOWCONTROL_NONE));
        addPropertys(rate);
        addPropertys(numberMeasurementm);
    }

    public static byte CRC(byte[] buf, int pos, int length) {
        byte res = 0;
        if (buf != null && buf.length > 0 && pos >= 0 && (pos + length) <= buf.length) {
            int CRC = 0;
            for (int i = pos; i < pos + length; i++) {
                CRC += buf[i];
            }
            res = (byte) (CRC & 0xFF);
        }
        return res;
    }

    protected SoftFloatProperty formRateProperty(long address, String name) {
        SoftFloatProperty property = new SoftFloatProperty(name, ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
        property.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, address);
        return property;
    }

    @Override
    protected boolean processRecivedMessage(byte[] recivedMessage, byte[] sendMessage, AbstractProperty property, final Event sourceEvent) {
        long deviceaddress = deviceAddress.getValue();
        if (recivedMessage != null && recivedMessage.length >= 10) {
            for (int i = 0; i < recivedMessage.length; i++) {
                if (recivedMessage[i] == 0x3E && recivedMessage[i + 9] == 0xD) {
                    byte ccrc = CRC(recivedMessage, i, 8);
                    if (ccrc == recivedMessage[i + 8]) {
                        short mnumberMeasurement = (short) (recivedMessage[i + 1] & 0xFF);
                        this.setHardWareInternalValue(numberMeasurementm, mnumberMeasurement, sourceEvent);
                        try {
                            float val = Float.parseFloat(CommunicationUtils.formStringFomByteBuffer(recivedMessage, i + 2, 6));
                            setHardWareInternalValue(rate, val, sourceEvent);
                            return true;
                        } catch (NumberFormatException ex) {
                            setHardWareInternalValue(rate, 0.0f, sourceEvent);
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected RequestInformation formReadRequest(AbstractProperty property) {
        long deviceaddress = deviceAddress.getValue();
        byte[] request = new byte[3];
        request[0] = (byte) 0x23;
        request[1] = (byte) (deviceaddress & 0xFF);
        request[2] = (byte) 0x0D;
        RequestInformation req = new RequestInformation(request, 10, true);
        return req;
    }

    @Override
    protected RequestInformation formWriteRequest(AbstractProperty property) {
        return null;
    }

    @Override
    public String getDeviceType() {
        return "ARG Micro";
    }

    public SoftFloatProperty getRate() {
        return rate;
    }

    public SoftShortProperty getNumberMeasurementm() {
        return numberMeasurementm;
    }
}

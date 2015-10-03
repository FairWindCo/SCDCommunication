package ua.pp.fairwind.communications.devices.hardwaredevices.Baumer;

import jssc.SerialPort;
import ua.pp.fairwind.communications.devices.RequestInformation;
import ua.pp.fairwind.communications.devices.abstracts.AbstractDevice;
import ua.pp.fairwind.communications.devices.abstracts.RSLineDevice;
import ua.pp.fairwind.communications.lines.lineparams.CommunicationLineParameters;
import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;

/**
 * Created by Сергей on 07.09.2015.
 */
/*
Basic settings of RS485 serial interface:
• 1 start bit
• 8 data bits (least significant bit first)
• 1 stop bit
• Parity none
• Baud rate: 38.4 kBaud
 *
 *
 * Structur of data fields
 * Demand from master
 *  SOH 80h ADR 80h EOT
 *  Reply of encoder
 *   SOH EAD MT_H MT_L ST_H ST_L LRC EOT
 *   Explanation:
SOH Value = 01h
ADR Address of encoder, value 02h - 05h
EOT Value = 04h
EAD Bit 0 - 3 response of encoder address (bit 4 -7 not defined)
MT_H High byte revolution
MT_L Low byte revolution
ST_H High byte steps
ST_L Low byte steps
LRC EAD XOR MT_H XOR M_T_L XOR ST_H XOR ST_L
 */
public class Encoder extends RSLineDevice {
    private final SoftShortProperty revolution;
    private final SoftShortProperty steps;

    public Encoder(long address, String codename, String uuid) {
        super(address, codename, uuid);
        revolution = new SoftShortProperty("BAUMER.REVOLUTION", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
        revolution.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 001L);
        steps = new SoftShortProperty("BAUMER.STEPS", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
        steps.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 002L);
        deviceTimeOut.setValue(350L);
        setLineParameters(new CommunicationLineParameters(SerialPort.BAUDRATE_38400, SerialPort.DATABITS_8, SerialPort.PARITY_NONE, SerialPort.STOPBITS_1, SerialPort.FLOWCONTROL_NONE));
        addPropertys(revolution);
        addPropertys(steps);
    }

    @Override
    protected boolean processRecivedMessage(byte[] recivedMessage, byte[] sendMessage, AbstractProperty property, final Event sourceEvent) {
        long deviceaddress = deviceAddress.getValue();
        if (recivedMessage != null && recivedMessage.length >= 8) {
            for (int i = 0; i < recivedMessage.length - 7; i++) {
                if (recivedMessage[i] == 0x1 && recivedMessage[i + 7] == 0x4 && (recivedMessage[i + 1] - 0x30) == (deviceaddress & 0x7)) {
                    byte ccrc = (byte) ((recivedMessage[i + 1] ^ recivedMessage[i + 2] ^ recivedMessage[i + 3] ^ recivedMessage[i + 4] ^ recivedMessage[i + 5]) & 0xFF);
                    if (ccrc == recivedMessage[i + 6]) {
                        short revolve = (short) (((recivedMessage[i + 2] << 8) + recivedMessage[i + 3]) & 0xFF);
                        short steps = (short) (((recivedMessage[i + 4] << 8) + recivedMessage[i + 5]) & 0xFF);
                        setHardWareInternalValue(this.revolution, revolve, sourceEvent);
                        setHardWareInternalValue(this.steps, steps, sourceEvent);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected RequestInformation formReadRequest(AbstractProperty property) {
        long deviceaddress = deviceAddress.getValue();
        byte[] request = new byte[5];
        request[0] = (byte) 0x01;
        request[1] = (byte) 0x80;
        request[2] = (byte) (deviceaddress & 0x07);
        request[3] = (byte) 0x80;
        request[4] = (byte) 0x04;
        RequestInformation req = new RequestInformation(request, 8, true);
        return req;
    }

    @Override
    protected RequestInformation formWriteRequest(AbstractProperty property) {
        return null;
    }

    @Override
    public String getDeviceType() {
        return "BAUMER Encoder";
    }

    public SoftShortProperty getRevolution() {
        return revolution;
    }

    public SoftShortProperty getSteps() {
        return steps;
    }
}

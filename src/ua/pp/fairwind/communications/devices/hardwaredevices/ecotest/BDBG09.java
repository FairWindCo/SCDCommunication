package ua.pp.fairwind.communications.devices.hardwaredevices.ecotest;

import jssc.SerialPort;
import ua.pp.fairwind.communications.devices.RequestInformation;
import ua.pp.fairwind.communications.devices.abstracts.RSLineDevice;
import ua.pp.fairwind.communications.internatianalisation.I18N;
import ua.pp.fairwind.communications.lines.lineparams.CommunicationLineParameters;
import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.messagesystems.event.EventType;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.groups.GroupProperty;
import ua.pp.fairwind.communications.propertyes.software.*;

/**
 * Created by Сергей on 28.09.2015.
 */
public class BDBG09 extends RSLineDevice {
    final public static short PROTOCOL_V1 = 1;
    final public static short PROTOCOL_V2 = 2;
    final public static short PROTOCOL_V3 = 3;
    final public static String FRAME_CODE = "FRAME_CODE";
    //Основные свойства
    protected final SoftShortProperty protocol = new SoftShortProperty("BDBG09_PROTOCOL_SELECTOR", PROTOCOL_V3);
    protected final SoftFloatProperty med = new SoftFloatProperty("BDBG09.MED", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
    protected final SoftByteProperty miss = new SoftByteProperty("BDBG09.MISS", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
    protected final SoftBoolProperty status = new SoftBoolProperty("BDBG09.STATUS", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
    protected final SoftBoolProperty HIGHT_ERROR = new SoftBoolProperty("BDBG09.HIGHT_ERROR", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
    protected final SoftBoolProperty LOW_ERROR = new SoftBoolProperty("BDBG09.LOW_ERROR", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
    protected final SoftBoolProperty TEMP_ERROR = new SoftBoolProperty("BDBG09.TEMP_ERROR", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
    protected final SoftBoolProperty MULTI = new SoftBoolProperty("BDBG09.MULTI", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
    //Грепповое свойство MED (чтение за раз)
    protected final GroupProperty GROUP_MED = new GroupProperty("GROUP_MED", null, med, miss, status, HIGHT_ERROR, LOW_ERROR, MULTI);

    //protected final SoftLongProperty serial_number=new SoftLongProperty("BDBG09.SERIAL_NUMBER", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
    protected final SoftStringProperty serial_number = new SoftStringProperty("BDBG09.SERIAL_NUMBER", null, ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE);
    protected final SoftFloatProperty temp = new SoftFloatProperty("BDBG09.TEMP", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
    protected final SoftShortProperty configured_device_address = new SoftShortProperty("BDBG09.CONFIGURED_DEVICE_ADDRESS");
    protected final SoftShortProperty configured_response_constant = new SoftShortProperty("BDBG09.configured_response_constant");

    //Свойства коифициенты устройства
    protected final SoftFloatProperty koif_hight_detect = new SoftFloatProperty("BDBG09.koif_hight_detect");
    protected final SoftFloatProperty ded_time_hight_detect = new SoftFloatProperty("BDBG09.ded_time_hight_detect");
    protected final SoftIntegerProperty ded_time_phis_hight_detect = new SoftIntegerProperty("BDBG09.ded_time_phis_hight_detect");
    protected final SoftIntegerProperty ded_time_phis3_hight_detect = new SoftIntegerProperty("BDBG09.ded_time_phis3_hight_detect");
    protected final SoftFloatProperty fon__hight_detect = new SoftFloatProperty("BDBG09.fon__hight_detect");
    protected final SoftFloatProperty base_fon__hight_detect = new SoftFloatProperty("BDBG09.base_fon__hight_detect");
    protected final SoftFloatProperty koif_low_detect = new SoftFloatProperty("BDBG09.koif_low_detect");
    protected final SoftFloatProperty ded_time_low_detect = new SoftFloatProperty("BDBG09.ded_time_low_detect");
    protected final SoftIntegerProperty ded_time_phis_low_detect = new SoftIntegerProperty("BDBG09.ded_time_phis_low_detect");
    protected final SoftIntegerProperty ded_time_phis3_low_detect = new SoftIntegerProperty("BDBG09.ded_time_phis3_low_detect");
    protected final SoftFloatProperty fon_low_detect = new SoftFloatProperty("BDBG09.fon_low_detect");
    //Грепповое свойство коифифценты (запись всех коифициентов и чтение за раз)
    protected final GroupProperty GROUP_KOIF = new GroupProperty("GROUP_KOIF", null, koif_hight_detect, ded_time_hight_detect, ded_time_phis_hight_detect, ded_time_phis3_hight_detect, fon__hight_detect, base_fon__hight_detect, koif_low_detect, ded_time_low_detect, ded_time_phis_low_detect, ded_time_phis3_low_detect, fon_low_detect);

    public BDBG09(long address, String codename, String uuid) {
        super(address, codename, uuid);
        protocol.setAdditionalInfo(NO_RANDOM, true);
        addPropertys(GROUP_MED);
        addPropertys(temp);
        addPropertys(serial_number);
        addPropertys(configured_device_address);
        addPropertys(GROUP_KOIF);

        GROUP_MED.setAdditionalInfo(FRAME_CODE, 0x0);
        serial_number.setAdditionalInfo(FRAME_CODE, 0x5);
        configured_device_address.setAdditionalInfo(FRAME_CODE, 0x6);
        temp.setAdditionalInfo(FRAME_CODE, 0x8);
        GROUP_KOIF.setAdditionalInfo(FRAME_CODE, 0x4);
        setLineParameters(new CommunicationLineParameters(SerialPort.BAUDRATE_19200, SerialPort.DATABITS_8, SerialPort.PARITY_NONE, SerialPort.STOPBITS_1, SerialPort.FLOWCONTROL_NONE));
    }

    public BDBG09(long address, String codename) {
        this(address, codename, null);
    }

    public BDBG09(long address) {
        this(address, "BDBG09", null);
    }


    @Override
    protected boolean processRecivedMessage(byte[] recivedMessage, byte[] sendMessage, AbstractProperty property, Event sourceEvent) {
        short protocol = this.protocol.getValue();
        long deviceAddress = super.deviceAddress.getValue();
        if (protocol == PROTOCOL_V1) {
            if (recivedMessage != null && recivedMessage.length >= 3) {
                for (int startpos = 0; startpos < recivedMessage.length - 3; startpos++) {
                    if (recivedMessage[startpos] == (byte) 0x55 && recivedMessage[startpos + 1] == (byte) 0xAA && (recivedMessage[startpos + 2] & 0xF) == (deviceAddress & 0xF)) {
                        int frame_code = (recivedMessage[startpos + 2] >> 4) & 0xF;
                        switch (frame_code) {
                            case 0x1: {
                                if (startpos + 10 <= recivedMessage.length && BDBG09_protocol.checkCRC(recivedMessage, startpos, 9)) {
                                    readMED(recivedMessage, startpos + 3, sourceEvent);
                                    return true;
                                }
                            }
                            break;
                            case 0x2: {
                                if (startpos + 40 <= recivedMessage.length && BDBG09_protocol.checkCRC(recivedMessage, startpos, 39)) {
                                    readKoificient(recivedMessage, startpos + 3, sourceEvent);
                                    return true;
                                }
                            }
                            break;
                            case 0x5: {
                                if (startpos + 8 <= recivedMessage.length && BDBG09_protocol.checkCRC(recivedMessage, startpos, 7)) {
                                    readSERIAL(recivedMessage, startpos + 3, sourceEvent);
                                    return true;
                                }
                            }
                            break;
                            case 0x3: {
                                return true;
                            }
                            case 0xB: {
                                fireEvent(EventType.ERROR, I18N.getLocalizedString("BDBG09.VALUE_SET_ERROR"));
                                return false;
                            }
                        }
                    }
                }
            }
        } else if (protocol == PROTOCOL_V2) {
            if (recivedMessage != null && recivedMessage.length >= 3) {
                for (int startpos = 0; startpos < recivedMessage.length - 3; startpos++) {
                    if (recivedMessage[startpos] == (byte) 0x55 && recivedMessage[startpos + 1] == (byte) 0xAA && (recivedMessage[startpos + 2] & 0xF) == (deviceAddress & 0xF)) {
                        int frame_code = (recivedMessage[startpos + 2] >> 4) & 0xF;
                        switch (frame_code) {
                            case 1: {
                                if (startpos + 10 <= recivedMessage.length && BDBG09_protocol.checkCRC(recivedMessage, startpos, 9)) {
                                    readMED(recivedMessage, startpos + 3, sourceEvent);
                                    return true;
                                }
                            }
                            break;
                            case 0x2: {
                                if (startpos + 40 <= recivedMessage.length && BDBG09_protocol.checkCRC(recivedMessage, startpos, 39)) {
                                    readKoificient(recivedMessage, startpos + 3, sourceEvent);
                                    return true;
                                }
                            }
                            break;
                            case 0x5: {
                                if (startpos + 8 <= recivedMessage.length && BDBG09_protocol.checkCRC(recivedMessage, startpos, 7)) {
                                    readSERIAL(recivedMessage, startpos + 3, sourceEvent);
                                    return true;
                                }
                            }
                            break;
                            case 0x8: {
                                if (startpos + 6 <= recivedMessage.length && BDBG09_protocol.checkCRC(recivedMessage, startpos, 5)) {
                                    readTEMP(recivedMessage, startpos + 3, sourceEvent);
                                    return true;
                                }
                            }
                            break;
                            case 0x3: {
                                return true;
                            }
                            case 0xB: {
                                fireEvent(EventType.ERROR, I18N.getLocalizedString("BDBG09.VALUE_SET_ERROR"));
                                return false;
                            }
                        }
                    }
                }
            }
        } else if (protocol == PROTOCOL_V3) {
            if (recivedMessage != null && recivedMessage.length >= 5) {
                for (int startpos = 0; startpos < recivedMessage.length - 5; startpos++) {
                    if (recivedMessage[startpos] == (byte) 0x55 && recivedMessage[startpos + 1] == (byte) 0xAA && recivedMessage[startpos + 2] == (byte) 0x70 && (recivedMessage[startpos + 3] & 0xFF) == (deviceAddress & 0xFF)) {
                        int frame_code = recivedMessage[startpos + 4] & 0xFF;
                        switch (frame_code) {
                            case 0x1: {
                                if (startpos + 12 <= recivedMessage.length && BDBG09_protocol.checkCRC(recivedMessage, startpos, 11)) {
                                    readMED(recivedMessage, startpos + 5, sourceEvent);
                                    return true;
                                }
                            }
                            break;
                            case 0x2: {
                                if (startpos + 42 <= recivedMessage.length && BDBG09_protocol.checkCRC(recivedMessage, startpos, 41)) {
                                    readKoificient(recivedMessage, startpos + 5, sourceEvent);
                                    return true;
                                }
                            }
                            break;
                            case 0x3: {
                                if (BDBG09_protocol.checkCRC(recivedMessage, startpos, 5)) {
                                    return true;
                                }
                            }
                            case 0x5: {
                                if (startpos + 11 <= recivedMessage.length && BDBG09_protocol.checkCRC(recivedMessage, startpos, 10)) {
                                    readSERIAL_v3(recivedMessage, startpos + 5, sourceEvent);
                                    return true;
                                }
                            }
                            break;
                            case 0x8: {
                                if (startpos + 8 <= recivedMessage.length && BDBG09_protocol.checkCRC(recivedMessage, startpos, 7)) {
                                    readTEMP(recivedMessage, startpos + 5, sourceEvent);
                                    return true;
                                }
                            }
                            break;
                            case 0x43: {
                                if (BDBG09_protocol.checkCRC(recivedMessage, startpos, 5)) {
                                    fireEvent(EventType.ERROR, I18N.getLocalizedString("BDBG09.VALUE_SET_ERROR"));
                                }
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private void readTEMP(byte[] recivedMessage, int pos, Event sourceEvent) {
        float res = 0.0f;
        int bits = (recivedMessage[pos + 1] << 8) & 0xFF00;
        bits += (recivedMessage[pos + 0]) & 0xFF;
        int j = -4;
        int mask = 1;
        for (int i = 0; i < 10; i++) {
            res += Math.pow(2, j) * (bits & mask);
            mask = mask << 1;
        }
        if ((bits & mask) > 0) {
            res = -res;
        }
        setHardWareInternalValue(this.temp, res, sourceEvent);
        boolean det_error = (bits & 0b1000_0000_0000_0000) == 0 ? false : true;
        setHardWareInternalValue(this.getTEMP_ERROR(), det_error, sourceEvent);
    }

    private void readMED(byte[] recivedMessage, int pos, Event sourceEvent) {
        long med = (recivedMessage[pos++]);
        med += (recivedMessage[pos++] << 8);
        med += (recivedMessage[pos++] << 16);
        med += (recivedMessage[pos++] << 24);
        short miss = (short) (recivedMessage[pos++] & 0xFF);
        byte flag = recivedMessage[pos++];
        boolean err_hi = (flag & 0b0000_0001) == 0 ? false : true;
        boolean err_lo = (flag & 0b0000_0010) == 0 ? false : true;
        boolean stat = (flag & 0b0000_0100) == 0 ? false : true;
        boolean mul = (flag & 0b1000_0000) == 0 ? false : true;
        float f_med = med * (mul ? 0.1f : 0.01f);
        setHardWareInternalValue(this.med, f_med, sourceEvent);
        setHardWareInternalValue(this.miss, miss, sourceEvent);
        setHardWareInternalValue(this.status, stat, sourceEvent);
        setHardWareInternalValue(this.HIGHT_ERROR, err_hi, sourceEvent);
        setHardWareInternalValue(this.LOW_ERROR, err_lo, sourceEvent);
        setHardWareInternalValue(this.MULTI, mul, sourceEvent);
    }

    private void readSERIAL(byte[] recivedMessage, int pos, Event sourceEvent) {
        long ser = (recivedMessage[pos++]);
        ser += (recivedMessage[pos++] << 8);
        ser += (recivedMessage[pos++] << 16);
        ser += (recivedMessage[pos++] << 24);
        String serial = String.format("%h", ser);
        setHardWareInternalValue(this.serial_number, serial, sourceEvent);
    }

    private void readSERIAL_v3(byte[] recivedMessage, int pos, Event sourceEvent) {
        long ser = (recivedMessage[pos++]);
        ser += (recivedMessage[pos++] << 8);
        ser += (recivedMessage[pos++] << 16);
        ser += (recivedMessage[pos++] << 24);
        String serial = String.format("%h", ser);
        setHardWareInternalValue(this.serial_number, serial, sourceEvent);
        int respconst = recivedMessage[pos] & 0xFF;
        setHardWareInternalValue(this.configured_response_constant, respconst, sourceEvent);
    }


    private void readKoificient(byte[] recivedMessage, int pos, Event sourceEvent) {
        float f_koif_umn_hi = BDBG09_protocol.parseMSPFloat(recivedMessage, pos);
        pos += 4;
        float f_ded_time_hi = BDBG09_protocol.parseMSPFloat(recivedMessage, pos);
        pos += 4;
        int f_ded_ftime_hi = BDBG09_protocol.parseWord(recivedMessage, pos);
        pos += 2;
        int f_ded_ftime3_hi = BDBG09_protocol.parseWord(recivedMessage, pos);
        pos += 2;
        float f_fon_hi = BDBG09_protocol.parseMSPFloat(recivedMessage, pos);
        pos += 4;
        float f_self_fon_hi = BDBG09_protocol.parseMSPFloat(recivedMessage, pos);
        pos += 4;
        float f_koif_umn_lo = BDBG09_protocol.parseMSPFloat(recivedMessage, pos);
        pos += 4;
        float f_ded_time_lo = BDBG09_protocol.parseMSPFloat(recivedMessage, pos);
        pos += 4;
        int f_ded_ftime_lo = BDBG09_protocol.parseWord(recivedMessage, pos);
        pos += 2;
        int f_ded_ftime3_lo = BDBG09_protocol.parseWord(recivedMessage, pos);
        pos += 2;
        float f_fon_lo = BDBG09_protocol.parseMSPFloat(recivedMessage, pos);
        setHardWareInternalValue(this.koif_hight_detect, f_koif_umn_hi, sourceEvent);
        setHardWareInternalValue(this.ded_time_hight_detect, f_ded_time_hi, sourceEvent);
        setHardWareInternalValue(this.ded_time_phis_hight_detect, f_ded_ftime_hi, sourceEvent);
        setHardWareInternalValue(this.ded_time_phis3_hight_detect, f_ded_ftime3_hi, sourceEvent);
        setHardWareInternalValue(this.fon__hight_detect, f_fon_hi, sourceEvent);
        setHardWareInternalValue(this.base_fon__hight_detect, f_self_fon_hi, sourceEvent);
        setHardWareInternalValue(this.koif_low_detect, f_koif_umn_lo, sourceEvent);
        setHardWareInternalValue(this.ded_time_low_detect, f_ded_time_lo, sourceEvent);
        setHardWareInternalValue(this.ded_time_phis_low_detect, f_ded_ftime_lo, sourceEvent);
        setHardWareInternalValue(this.ded_time_phis3_low_detect, f_ded_ftime3_lo, sourceEvent);
        setHardWareInternalValue(this.fon_low_detect, f_fon_lo, sourceEvent);
    }

    @Override
    protected RequestInformation formReadRequest(AbstractProperty property) {
        short protocol = this.protocol.getValue();
        long deviceAddress = super.deviceAddress.getValue();
        Object code = property.getAdditionalInfo(FRAME_CODE);
        if (code != null && code instanceof Number) {
            byte frame_code = ((Number) code).byteValue();
            if (protocol == PROTOCOL_V1) {
                if (frame_code == 0x0 || frame_code == 0x4 || frame_code == 0x5)
                    return BDBG09_protocol.READREQUEST(frame_code, (byte) (deviceAddress & 0xF));
            } else if (protocol == PROTOCOL_V2) {
                return BDBG09_protocol.READREQUEST(frame_code, (byte) (deviceAddress & 0xF));
            } else if (protocol == PROTOCOL_V3) {
                return BDBG09_protocol.READREQUEST_v3(frame_code, (byte) (deviceAddress & 0xFF));
            }
        }
        return null;
    }

    @Override
    protected RequestInformation formWriteRequest(AbstractProperty property) {
        if (property == GROUP_KOIF || property == configured_device_address || property == configured_response_constant) {
            long deviceAddress = super.deviceAddress.getValue();
            short protocol = this.protocol.getValue();
            if (property == GROUP_KOIF) {
                if (koif_hight_detect.getValue() == null) return null;
                if (ded_time_hight_detect.getValue() == null) return null;
                if (ded_time_phis_hight_detect.getValue() == null) return null;
                if (ded_time_phis3_hight_detect.getValue() == null) return null;
                if (fon__hight_detect.getValue() == null) return null;
                if (base_fon__hight_detect.getValue() == null) return null;

                if (koif_low_detect.getValue() == null) return null;
                if (ded_time_low_detect.getValue() == null) return null;
                if (ded_time_phis_low_detect.getValue() == null) return null;
                if (ded_time_phis3_low_detect.getValue() == null) return null;
                if (fon_low_detect.getValue() == null) return null;
            } else {
                if (property == configured_device_address) {
                    if (configured_device_address.getValue() == null) return null;
                }
            }
            if (protocol == PROTOCOL_V1) {
                if (property == GROUP_KOIF) {
                    return BDBG09_protocol.WRITEREQUEST((byte) (deviceAddress & 0xF),
                            koif_hight_detect.getValue(),
                            ded_time_hight_detect.getValue(),
                            ded_time_phis_hight_detect.getValue(),
                            ded_time_phis3_hight_detect.getValue(),
                            fon__hight_detect.getValue(),
                            base_fon__hight_detect.getValue(),
                            koif_low_detect.getValue(),
                            ded_time_low_detect.getValue(),
                            ded_time_phis_low_detect.getValue(),
                            ded_time_phis3_low_detect.getValue(),
                            fon_low_detect.getValue());

                } else if (property == configured_device_address) {
                    return BDBG09_protocol.WRITEREQUEST((byte) (deviceAddress & 0xF),
                            (byte) (configured_device_address.getValue() & 0xF));
                } else if (property == configured_response_constant) return null;
            } else if (protocol == PROTOCOL_V2) {
                if (property == GROUP_KOIF) {
                    return BDBG09_protocol.WRITEREQUEST((byte) (deviceAddress & 0xF),
                            koif_hight_detect.getValue(),
                            ded_time_hight_detect.getValue(),
                            ded_time_phis_hight_detect.getValue(),
                            ded_time_phis3_hight_detect.getValue(),
                            fon__hight_detect.getValue(),
                            base_fon__hight_detect.getValue(),
                            koif_low_detect.getValue(),
                            ded_time_low_detect.getValue(),
                            ded_time_phis_low_detect.getValue(),
                            ded_time_phis3_low_detect.getValue(),
                            fon_low_detect.getValue());

                } else if (property == configured_device_address) {
                    return BDBG09_protocol.WRITEREQUEST((byte) (deviceAddress & 0xF),
                            (byte) (configured_device_address.getValue() & 0xF));
                } else if (property == configured_response_constant) return null;
            } else if (protocol == PROTOCOL_V3) {
                if (property == GROUP_KOIF) {
                    return BDBG09_protocol.WRITEREQUEST_v3((byte) (deviceAddress & 0xF),
                            koif_hight_detect.getValue(),
                            ded_time_hight_detect.getValue(),
                            ded_time_phis_hight_detect.getValue(),
                            ded_time_phis3_hight_detect.getValue(),
                            fon__hight_detect.getValue(),
                            base_fon__hight_detect.getValue(),
                            koif_low_detect.getValue(),
                            ded_time_low_detect.getValue(),
                            ded_time_phis_low_detect.getValue(),
                            ded_time_phis3_low_detect.getValue(),
                            fon_low_detect.getValue());

                } else {
                    if (configured_response_constant.getValue() == null) return null;
                    return BDBG09_protocol.WRITEREQUEST_v3((byte) (deviceAddress & 0xF),
                            (byte) (configured_device_address.getValue() & 0xFF),
                            (byte) (configured_response_constant.getValue() & 0xFF));
                }
            }
        }
        return null;
    }

    @Override
    public String getDeviceType() {
        return "BDBG09";
    }

    public SoftShortProperty getProtocol() {
        return protocol;
    }

    public SoftFloatProperty getMed() {
        return med;
    }

    public SoftByteProperty getMiss() {
        return miss;
    }

    public SoftStringProperty getSerial_number() {
        return serial_number;
    }

    public SoftFloatProperty getTemp() {
        return temp;
    }

    public SoftShortProperty getConfigured_device_address() {
        return configured_device_address;
    }

    public SoftBoolProperty getStatus() {
        return status;
    }

    public SoftBoolProperty getHIGHT_ERROR() {
        return HIGHT_ERROR;
    }

    public SoftBoolProperty getLOW_ERROR() {
        return LOW_ERROR;
    }

    public SoftBoolProperty getMULTI() {
        return MULTI;
    }

    public GroupProperty getGROUP_KOIF() {
        return GROUP_KOIF;
    }

    public SoftBoolProperty getTEMP_ERROR() {
        return TEMP_ERROR;
    }

    public GroupProperty getGROUP_MED() {
        return GROUP_MED;
    }
}

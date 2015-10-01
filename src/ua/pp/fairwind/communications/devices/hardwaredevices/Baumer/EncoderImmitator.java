package ua.pp.fairwind.communications.devices.hardwaredevices.Baumer;

import ua.pp.fairwind.communications.devices.abstracts.AbstractDevice;
import ua.pp.fairwind.communications.devices.abstracts.RSLineImmitatorDevice;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;

/**
 * Created by Сергей on 01.10.2015.
 */
public class EncoderImmitator extends RSLineImmitatorDevice {
    private final SoftShortProperty revolution;
    private final SoftShortProperty steps;
    private volatile int curentState=0;

    public EncoderImmitator(long address, String codename, String uuid) {
        super(address, codename, uuid);
        revolution=new SoftShortProperty("BAUMER.REVOLUTION", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
        revolution.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 001L);
        steps=new SoftShortProperty("BAUMER.STEPS", ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY);
        steps.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 002L);
        addPropertys(revolution);
        addPropertys(steps);
    }

    @Override
    public String getDeviceType() {
        return "BAUMER Encoder Imitator";
    }

    @Override
    protected byte[] processDataFromLine(byte[] data_from_line) {
        return new byte[0];
    }

    private byte[] setState(byte curentByte){
        switch (curentState){
            default:
                curentState=0;
        }
        return null;
    }
}

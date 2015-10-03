package ua.pp.fairwind.communications.devices.hardwaredevices;

import ua.pp.fairwind.communications.devices.abstracts.ImitatorDevice;
import ua.pp.fairwind.communications.devices.hardwaredevices.Baumer.EncoderImmitator;

/**
 * Created by Сергей on 02.10.2015.
 */
public class ImitatorCreator {
    public String[] getAviableImitator() {
        return new String[]{"ENCODER"};
    }

    public ImitatorDevice getImitatorDevice(String imitatorName, Long adress) {
        switch (imitatorName) {
            case "ENCODER":
                return new EncoderImmitator(adress, "ENCODER", null);
        }
        return null;
    }
}

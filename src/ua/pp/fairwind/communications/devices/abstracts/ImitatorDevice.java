package ua.pp.fairwind.communications.devices.abstracts;

/**
 * Created by Сергей on 10.09.2015.
 */
public interface ImitatorDevice extends LinedDeviceInterface {
    byte[] process_line_data(byte[] data_from_line);

    void errorDuringSend();
}

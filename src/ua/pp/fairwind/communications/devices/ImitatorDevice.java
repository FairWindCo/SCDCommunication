package ua.pp.fairwind.communications.devices;

/**
 * Created by Сергей on 10.09.2015.
 */
public interface ImitatorDevice extends DeviceInterface{
    byte[] process_line_data(byte[] data_from_line);
    void errorDuringSend();
}

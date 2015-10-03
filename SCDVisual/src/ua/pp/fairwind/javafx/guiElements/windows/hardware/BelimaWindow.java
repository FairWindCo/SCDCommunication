package ua.pp.fairwind.javafx.guiElements.windows.hardware;

import javafx.scene.Node;
import ua.pp.fairwind.communications.SCADASystem;
import ua.pp.fairwind.communications.devices.hardwaredevices.Baumer.Encoder;
import ua.pp.fairwind.communications.elementsdirecotry.AutoCreateDeviceFunction;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.javafx.guiElements.windows.SimpleHardWareViewMenu;
import ua.pp.fairwind.javafx.panels.devices.belima.BelimaEncoderComplexPanel;

import java.util.List;

/**
 * Created by Сергей on 10.09.2015.
 */
public class BelimaWindow extends SimpleHardWareViewMenu {

    public BelimaWindow(String menuItem, SCADASystem centralsystem, String deviceName) {
        super(menuItem, "", centralsystem, AutoCreateDeviceFunction.BELIMA_ENCODER, deviceName);
    }

    public BelimaWindow(String menuItem, SCADASystem centralsystem) {
        super(menuItem, "", centralsystem, AutoCreateDeviceFunction.BELIMA_ENCODER, "BAUMER");
    }

    @Override
    protected Node createView() {
        Encoder device = (Encoder) centralsystem.createDevice(deviceType, deviceName);
        List<LineInterface> lines = centralsystem.getListLines();
        LineInterface oneLine = lines.get(0);
        BelimaEncoderComplexPanel panel = new BelimaEncoderComplexPanel(oneLine, device, lines);
        panel.setId("basePanel");
        return panel;
    }
}

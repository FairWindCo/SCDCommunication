package ua.pp.fairwind.javafx.guiElements.windows.hardware;

import javafx.scene.Node;
import ua.pp.fairwind.communications.SCADASystem;
import ua.pp.fairwind.communications.devices.hardwaredevices.positron.BDMG04;
import ua.pp.fairwind.communications.elementsdirecotry.AutoCreateDeviceFunction;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.javafx.guiElements.windows.SimpleHardWareViewMenu;
import ua.pp.fairwind.javafx.panels.devices.bdmg.BBMGComplexPanel;

import java.util.List;

/**
 * Created by Сергей on 10.09.2015.
 */
public class BDMG04Window extends SimpleHardWareViewMenu {

    public BDMG04Window(String menuItem, SCADASystem centralsystem, String deviceName) {
        super(menuItem, "", centralsystem, AutoCreateDeviceFunction.BDMG04, deviceName);
    }

    public BDMG04Window(String menuItem, SCADASystem centralsystem) {
        super(menuItem, "", centralsystem, AutoCreateDeviceFunction.BDMG04, "BDMG04");
    }

    @Override
    protected Node createView() {
        BDMG04 device = (BDMG04) centralsystem.createDevice(deviceType, deviceName);
        List<LineInterface> lines = centralsystem.getListLines();
        LineInterface oneLine = lines.get(0);
        BBMGComplexPanel panel = new BBMGComplexPanel(oneLine, device, lines);
        panel.setId("basePanel");
        return panel;
    }
}

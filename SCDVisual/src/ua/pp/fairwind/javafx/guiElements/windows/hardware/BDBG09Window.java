package ua.pp.fairwind.javafx.guiElements.windows.hardware;

import javafx.scene.Node;
import ua.pp.fairwind.communications.SCADASystem;
import ua.pp.fairwind.communications.devices.hardwaredevices.ecotest.BDBG09;
import ua.pp.fairwind.communications.elementsdirecotry.AutoCreateDeviceFunction;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.javafx.guiElements.windows.SimpleHardWareViewMenu;
import ua.pp.fairwind.javafx.panels.devices.bgbg.BDBGComplexPanel;

import java.util.List;

/**
 * Created by Сергей on 10.09.2015.
 */
public class BDBG09Window extends SimpleHardWareViewMenu {

    public BDBG09Window(String menuItem, SCADASystem centralsystem, String deviceName) {
        super(menuItem, "", centralsystem, AutoCreateDeviceFunction.BDBG09, deviceName);
    }

    public BDBG09Window(String menuItem, SCADASystem centralsystem) {
        super(menuItem, "", centralsystem, AutoCreateDeviceFunction.BDBG09, "BDBG09");
    }

    @Override
    protected Node createView() {
        BDBG09 device = (BDBG09) centralsystem.createDevice(deviceType, deviceName);
        List<LineInterface> lines = centralsystem.getListLines();
        LineInterface oneLine = lines.get(0);
        BDBGComplexPanel panel = new BDBGComplexPanel(oneLine, device, lines);
        panel.setId("basePanel");
        return panel;
    }
}

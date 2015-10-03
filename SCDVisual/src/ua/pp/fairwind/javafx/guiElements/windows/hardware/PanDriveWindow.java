package ua.pp.fairwind.javafx.guiElements.windows.hardware;

import javafx.scene.Node;
import ua.pp.fairwind.communications.SCADASystem;
import ua.pp.fairwind.communications.devices.hardwaredevices.panDrive.StepDriver;
import ua.pp.fairwind.communications.elementsdirecotry.AutoCreateDeviceFunction;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.javafx.guiElements.windows.SimpleHardWareViewMenu;
import ua.pp.fairwind.javafx.panels.devices.panDrive.PanDriveComplexPanel;

import java.util.List;

/**
 * Created by Сергей on 10.09.2015.
 */
public class PanDriveWindow extends SimpleHardWareViewMenu {

    public PanDriveWindow(String menuItem, SCADASystem centralsystem, String deviceName) {
        super(menuItem, "", centralsystem, AutoCreateDeviceFunction.PANDRIVE_MOTOR, deviceName);
    }

    public PanDriveWindow(String menuItem, SCADASystem centralsystem) {
        super(menuItem, "", centralsystem, AutoCreateDeviceFunction.PANDRIVE_MOTOR, "PANDRIVE");
    }


    @Override
    protected Node createView() {
        StepDriver device = (StepDriver) centralsystem.createDevice(deviceType, deviceName);
        List<LineInterface> lines = centralsystem.getListLines();
        LineInterface oneLine = lines.get(0);
        PanDriveComplexPanel panel = new PanDriveComplexPanel(oneLine, device, lines);
        panel.setId("basePanel");
        return panel;
    }
}

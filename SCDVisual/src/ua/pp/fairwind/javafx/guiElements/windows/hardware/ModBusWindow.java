package ua.pp.fairwind.javafx.guiElements.windows.hardware;

import javafx.scene.Node;
import ua.pp.fairwind.communications.SCADASystem;
import ua.pp.fairwind.communications.devices.hardwaredevices.modbus.ModBusDevice;
import ua.pp.fairwind.communications.elementsdirecotry.AutoCreateDeviceFunction;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.javafx.guiElements.windows.SimpleHardWareViewMenu;
import ua.pp.fairwind.javafx.panels.devices.modbus.ModBusComplexPanel;

import java.util.List;

/**
 * Created by Сергей on 10.09.2015.
 */
public class ModBusWindow extends SimpleHardWareViewMenu {

    public ModBusWindow(String menuItem, SCADASystem centralsystem, String deviceName) {
        super(menuItem, "", centralsystem, AutoCreateDeviceFunction.MODBUS, deviceName);
    }

    public ModBusWindow(String menuItem, SCADASystem centralsystem) {
        super(menuItem, "", centralsystem, AutoCreateDeviceFunction.MODBUS, "MODBUS");
    }

    @Override
    protected Node createView() {
        ModBusDevice device = (ModBusDevice) centralsystem.createDevice(deviceType, deviceName);
        List<LineInterface> lines = centralsystem.getListLines();
        LineInterface oneLine = lines.get(0);
        ModBusComplexPanel panel = new ModBusComplexPanel(oneLine, device,centralsystem.getElementsCreator(), lines);
        panel.setId("basePanel");
        return panel;
    }
}
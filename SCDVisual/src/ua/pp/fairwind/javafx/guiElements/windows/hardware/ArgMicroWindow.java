package ua.pp.fairwind.javafx.guiElements.windows.hardware;

import javafx.scene.Node;
import ua.pp.fairwind.communications.SCADASystem;
import ua.pp.fairwind.communications.devices.hardwaredevices.arg.micro.ArgMicroDevice;
import ua.pp.fairwind.communications.elementsdirecotry.AutoCreateDeviceFunction;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.javafx.guiElements.windows.SimpleHardWareViewMenu;
import ua.pp.fairwind.javafx.panels.devices.argMicro.ArgMicroComplexPanel;

import java.util.List;

/**
 * Created by Сергей on 10.09.2015.
 */
public class ArgMicroWindow extends SimpleHardWareViewMenu {

    public ArgMicroWindow(String menuItem, SCADASystem centralsystem, String deviceName) {
        super(menuItem,"", centralsystem,AutoCreateDeviceFunction.ARG_MICRO, deviceName);
    }

    public ArgMicroWindow(String menuItem, SCADASystem centralsystem) {
        super(menuItem,"", centralsystem,AutoCreateDeviceFunction.ARG_MICRO, "ARGMICRO");
    }

    @Override
    protected Node createView() {
        ArgMicroDevice device=(ArgMicroDevice)centralsystem.createDevice(deviceType, deviceName);
        List<LineInterface> lines=centralsystem.getListLines();
        LineInterface oneLine=lines.get(0);
        ArgMicroComplexPanel panel=new ArgMicroComplexPanel(oneLine,device, lines);
        panel.setId("basePanel");
        return panel;
    }
}

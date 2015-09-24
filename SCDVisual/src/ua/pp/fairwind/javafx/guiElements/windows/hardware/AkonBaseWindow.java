package ua.pp.fairwind.javafx.guiElements.windows.hardware;

import javafx.scene.Node;
import ua.pp.fairwind.communications.SCADASystem;
import ua.pp.fairwind.communications.devices.hardwaredevices.akon.AkonBase;
import ua.pp.fairwind.communications.devices.hardwaredevices.arg.micro.ArgMicroDevice;
import ua.pp.fairwind.communications.elementsdirecotry.AutoCreateDeviceFunction;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.javafx.guiElements.windows.SimpleHardWareViewMenu;
import ua.pp.fairwind.javafx.panels.devices.akon.AkonBaseComplexPanel;
import ua.pp.fairwind.javafx.panels.devices.argMicro.ArgMicroComplexPanel;

import java.util.List;

/**
 * Created by Сергей on 10.09.2015.
 */
public class AkonBaseWindow extends SimpleHardWareViewMenu {

    public AkonBaseWindow(String menuItem, SCADASystem centralsystem, String deviceName) {
        super(menuItem,"", centralsystem,AutoCreateDeviceFunction.AKON, deviceName);
    }

    public AkonBaseWindow(String menuItem, SCADASystem centralsystem) {
        super(menuItem,"", centralsystem,AutoCreateDeviceFunction.AKON, "baseakon");
    }

    @Override
    protected Node createView() {
        AkonBase device=(AkonBase)centralsystem.createDevice(deviceType, deviceName);
        List<LineInterface> lines=centralsystem.getListLines();
        LineInterface oneLine=lines.get(0);
        AkonBaseComplexPanel panel=new AkonBaseComplexPanel(oneLine,device, lines);
        panel.setId("basePanel");
        return panel;
    }
}

package ua.pp.fairwind.javafx.guiElements.windows.hardware;

import javafx.scene.Node;
import ua.pp.fairwind.communications.SCADASystem;
import ua.pp.fairwind.communications.devices.hardwaredevices.favorit.FavoritCoreDeviceV1;
import ua.pp.fairwind.communications.elementsdirecotry.AutoCreateDeviceFunction;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.javafx.guiElements.windows.SimpleHardWareViewMenu;
import ua.pp.fairwind.javafx.panels.devices.FavoritComplexPanel;

import java.util.List;

/**
 * Created by Сергей on 10.09.2015.
 */
public class FavoritPlate extends SimpleHardWareViewMenu {

    public FavoritPlate(String menuItem, SCADASystem centralsystem, String deviceName) {
        super(menuItem,"", centralsystem,AutoCreateDeviceFunction.FAVORIT_VENTIL_V1, deviceName);
    }

    public FavoritPlate(String menuItem, SCADASystem centralsystem) {
        super(menuItem,"", centralsystem,AutoCreateDeviceFunction.FAVORIT_VENTIL_V1, "FAVORIT_VENTIL");
    }

    @Override
    protected Node createView() {
        FavoritCoreDeviceV1 device=(FavoritCoreDeviceV1)centralsystem.createDevice(deviceType, deviceName);
        List<LineInterface> lines=centralsystem.getListLines();
        LineInterface oneLine=lines.get(0);
        FavoritComplexPanel panel=new FavoritComplexPanel(oneLine,device, lines);
        panel.setId("basePanel");
        return panel;
    }
}

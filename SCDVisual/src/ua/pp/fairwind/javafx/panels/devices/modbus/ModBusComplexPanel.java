package ua.pp.fairwind.javafx.panels.devices.modbus;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ua.pp.fairwind.communications.devices.hardwaredevices.modbus.ModBusDevice;
import ua.pp.fairwind.communications.lines.abstracts.AbstractLine;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.javafx.panels.LineInfoBar;
import ua.pp.fairwind.javafx.panels.devices.SimpleDeviceConfigPanel;

import java.util.List;

/**
 * Created by Сергей on 28.08.2015.
 */
public class ModBusComplexPanel extends BorderPane {
    private final LineInterface line;
    private final ModBusDevice device;
    private final List<LineInterface> lines;

    private final SimpleDeviceConfigPanel devpanel;
    private final ModBusPanel panel;
    private final LineInfoBar infoBarP;
    private final LineInfoBar infoBarS;
    SimpleDeviceConfigPanel.LineChange listner = new SimpleDeviceConfigPanel.LineChange() {
        @Override
        public void primaryLineChange(LineInterface newline) {
            infoBarP.setupLine(newline);
        }

        @Override
        public void secondaryLineChange(LineInterface newline) {

            infoBarS.setupLine(newline);
        }
    };

    public ModBusComplexPanel(AbstractLine line, ModBusDevice device) {
        this(line, device, null);
    }

    public ModBusComplexPanel(LineInterface line, ModBusDevice device, List<LineInterface> lines) {
        if (device == null) throw new RuntimeException("DEVICE CAN`T BE NULL!");
        if (line != null) device.setPrimerayLine(line);
        this.line = line;
        this.lines = lines;
        this.device = device;
        devpanel = new SimpleDeviceConfigPanel(device, line, lines, listner);
        infoBarP = new LineInfoBar(35, device.getPrimaryLine());
        infoBarS = new LineInfoBar(35, device.getSecondaryLine());
        panel = new ModBusPanel(device);
        initControl();
    }

    private void initControl() {

        setTop(devpanel);
        setCenter(panel);
        setAlignment(panel, Pos.CENTER);
        VBox down = new VBox();
        down.getChildren().addAll(infoBarP, infoBarS);

        setBottom(down);
    }



}


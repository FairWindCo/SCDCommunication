package ua.pp.fairwind.javafx.panels.devices.akon;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ua.pp.fairwind.communications.devices.hardwaredevices.akon.AkonBase;
import ua.pp.fairwind.communications.lines.abstracts.AbstractLine;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.javafx.panels.LineInfoBar;

import java.util.List;

/**
 * Created by Сергей on 28.08.2015.
 */
public class AkonBaseComplexPanel extends BorderPane {

    private final SimpleAkonDeviceConfigPanel devpanel;
    private final AkonBasePanel panel;
    private final LineInfoBar infoBarP;
    private final LineInfoBar infoBarS;
    SimpleAkonDeviceConfigPanel.LineChange listner = new SimpleAkonDeviceConfigPanel.LineChange() {
        @Override
        public void primaryLineChange(LineInterface newline) {
            infoBarP.setupLine(newline);
        }

        @Override
        public void secondaryLineChange(LineInterface newline) {

            infoBarS.setupLine(newline);
        }
    };

    public AkonBaseComplexPanel(AbstractLine line, AkonBase device) {
        this(line, device, null);
    }

    public AkonBaseComplexPanel(LineInterface line, AkonBase device, List<LineInterface> lines) {
        if (device == null) throw new RuntimeException("DEVICE CAN`T BE NULL!");
        if (line != null) device.setPrimerayLine(line);
        LineInterface line1 = line;
        List<LineInterface> lines1 = lines;
        AkonBase device1 = device;
        devpanel = new SimpleAkonDeviceConfigPanel(device, line, lines, listner);
        infoBarP = new LineInfoBar(35, device.getPrimaryLine());
        infoBarS = new LineInfoBar(35, device.getSecondaryLine());
        panel = new AkonBasePanel(device);
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

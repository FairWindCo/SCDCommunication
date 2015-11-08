package ua.pp.fairwind.javafx.panels.devices.bdmg;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ua.pp.fairwind.communications.devices.hardwaredevices.positron.BDMG04;
import ua.pp.fairwind.communications.lines.abstracts.AbstractLine;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.javafx.panels.LineInfoBar;

import java.util.List;

/**
 * Created by Сергей on 28.08.2015.
 */
public class BBMGComplexPanel extends BorderPane {

    private final SimpleBDMGDeviceConfigPanel devpanel;
    private final BDMGPanel panel;
    private final LineInfoBar infoBarP;
    private final LineInfoBar infoBarS;
    SimpleBDMGDeviceConfigPanel.LineChange listner = new SimpleBDMGDeviceConfigPanel.LineChange() {
        @Override
        public void primaryLineChange(LineInterface newline) {
            infoBarP.setupLine(newline);
        }

        @Override
        public void secondaryLineChange(LineInterface newline) {

            infoBarS.setupLine(newline);
        }
    };

    public BBMGComplexPanel(AbstractLine line, BDMG04 device) {
        this(line, device, null);
    }

    public BBMGComplexPanel(LineInterface line, BDMG04 device, List<LineInterface> lines) {
        if (device == null) throw new RuntimeException("DEVICE CAN`T BE NULL!");
        if (line != null) device.setPrimerayLine(line);
        LineInterface line1 = line;
        List<LineInterface> lines1 = lines;
        BDMG04 device1 = device;
        devpanel = new SimpleBDMGDeviceConfigPanel(device, line, lines, listner);
        infoBarP = new LineInfoBar(35, device.getPrimaryLine());
        infoBarS = new LineInfoBar(35, device.getSecondaryLine());
        panel = new BDMGPanel(device);
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

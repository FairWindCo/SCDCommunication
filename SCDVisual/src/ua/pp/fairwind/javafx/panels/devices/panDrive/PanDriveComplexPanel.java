package ua.pp.fairwind.javafx.panels.devices.panDrive;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ua.pp.fairwind.communications.devices.hardwaredevices.panDrive.StepDriver;
import ua.pp.fairwind.communications.lines.AbstractLine;
import ua.pp.fairwind.communications.lines.LineInterface;
import ua.pp.fairwind.javafx.panels.LineInfoBar;
import ua.pp.fairwind.javafx.panels.SimpleDeviceConfigPanel;

import java.util.List;

/**
 * Created by Сергей on 28.08.2015.
 */
public class PanDriveComplexPanel extends BorderPane {
    private final LineInterface line;
    private final StepDriver device;
    private final List<LineInterface> lines;

    private final SimpleDeviceConfigPanel devpanel;
    private final PanDrivePanel panel;
    private final LineInfoBar infoBarP;
    private final LineInfoBar infoBarS;


    public PanDriveComplexPanel(AbstractLine line, StepDriver device) {
        this(line, device, null);
    }

    SimpleDeviceConfigPanel.LineChange listner=new SimpleDeviceConfigPanel.LineChange() {
        @Override
        public void primaryLineChange(LineInterface newline) {
            infoBarP.setupLine(newline);
        }

        @Override
        public void secondaryLineChange(LineInterface newline) {

            infoBarS.setupLine(newline);
        }
    };

    public PanDriveComplexPanel(LineInterface line, StepDriver device, List<LineInterface> lines) {
        if(device==null)throw new RuntimeException("DEVICE CAN`T BE NULL!");
        this.line=line;
        this.lines=lines;
        this.device=device;
        devpanel=new SimpleDeviceConfigPanel(device,line,lines,listner);
        infoBarP=new LineInfoBar(35,device.getPrimaryLine());
        infoBarS=new LineInfoBar(35,device.getSecondaryLine());
        panel=new PanDrivePanel(device);
        initControl();
    }

    private void initControl(){

        setTop(devpanel);
        setCenter(panel);
        setAlignment(panel, Pos.CENTER);
        VBox down=new VBox();
        down.getChildren().addAll(infoBarP, infoBarS);

        setBottom(down);
    }

}

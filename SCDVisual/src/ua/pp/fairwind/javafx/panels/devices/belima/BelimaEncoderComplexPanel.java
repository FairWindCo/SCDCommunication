package ua.pp.fairwind.javafx.panels.devices.belima;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ua.pp.fairwind.communications.devices.hardwaredevices.Baumer.Encoder;
import ua.pp.fairwind.communications.lines.abstracts.AbstractLine;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.javafx.panels.LineInfoBar;
import ua.pp.fairwind.javafx.panels.devices.SimpleDeviceConfigPanel;

import java.util.List;

/**
 * Created by Сергей on 28.08.2015.
 */
public class BelimaEncoderComplexPanel extends BorderPane {

    private final SimpleDeviceConfigPanel devpanel;
    private final BelimaEncoderPanel panel;
    private final LineInfoBar infoBarP;
    private final LineInfoBar infoBarS;


    public BelimaEncoderComplexPanel(AbstractLine line, Encoder device) {
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

    public BelimaEncoderComplexPanel(LineInterface line, Encoder device, List<LineInterface> lines) {
        if(device==null)throw new RuntimeException("DEVICE CAN`T BE NULL!");
        if(line!=null)device.setPrimerayLine(line);
        LineInterface line1 = line;
        List<LineInterface> lines1 = lines;
        Encoder device1 = device;
        devpanel=new SimpleDeviceConfigPanel(device,line,lines,listner);
        infoBarP=new LineInfoBar(35,device.getPrimaryLine());
        infoBarS=new LineInfoBar(35,device.getSecondaryLine());
        panel=new BelimaEncoderPanel(device);
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

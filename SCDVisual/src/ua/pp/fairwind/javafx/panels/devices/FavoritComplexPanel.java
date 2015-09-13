package ua.pp.fairwind.javafx.panels.devices;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ua.pp.fairwind.communications.devices.hardwaredevices.favorit.FavoritCoreDeviceV1;
import ua.pp.fairwind.communications.lines.AbstractLine;
import ua.pp.fairwind.communications.lines.LineInterface;
import ua.pp.fairwind.javafx.panels.LineInfoBar;

import java.util.List;

/**
 * Created by Сергей on 28.08.2015.
 */
public class FavoritComplexPanel extends BorderPane {
    private final LineInterface line;
    private final FavoritCoreDeviceV1 device;
    private final List<LineInterface> lines;

    private final SimpleDeviceConfigPanel devpanel;
    private final FavoritPanel panel;
    private final LineInfoBar infoBarP;
    private final LineInfoBar infoBarS;


    public FavoritComplexPanel(AbstractLine line,FavoritCoreDeviceV1 device) {
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

    public FavoritComplexPanel(LineInterface line,FavoritCoreDeviceV1 device,List<LineInterface> lines) {
        if(device==null)throw new RuntimeException("DEVICE CAN`T BE NULL!");
        if(line!=null)device.setPrimerayLine(line);
        this.line=line;
        this.lines=lines;
        this.device=device;
        devpanel=new SimpleDeviceConfigPanel(device,line,lines,listner);
        infoBarP=new LineInfoBar(35,device.getPrimaryLine());
        infoBarS=new LineInfoBar(35,device.getSecondaryLine());
        panel=new FavoritPanel(device);
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

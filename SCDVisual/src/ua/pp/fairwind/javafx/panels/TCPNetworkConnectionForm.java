package ua.pp.fairwind.javafx.panels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import ua.pp.fairwind.communications.devices.abstracts.DeviceInterface;
import ua.pp.fairwind.communications.lines.lineparams.CommunicationLineParameters;
import ua.pp.fairwind.communications.lines.lineparams.LineParameters;
import ua.pp.fairwind.javafx.guiElements.editors.IntegerInputText;
import ua.pp.fairwind.javafx.guiElements.menu.MenuExecutor;
import ua.pp.fairwind.javafx.guiElements.menu.PrefferedSize;
import ua.pp.fairwind.javafx.guiElements.windows.SimpleView;


public class TCPNetworkConnectionForm extends SimpleView {
    StringProperty address = new SimpleStringProperty();
    IntegerProperty port = new SimpleIntegerProperty();
    DeviceInterface dev = null;
    LineParameters safeparams;

    public TCPNetworkConnectionForm(String title, Image icon,
                                    PrefferedSize prefferedSize, DeviceInterface comuunicateDevice) {
        super(title, icon, prefferedSize);
        this.dev = comuunicateDevice;
    }


    public TCPNetworkConnectionForm(String title, DeviceInterface comuunicateDevice) {
        super(title, null, (PrefferedSize) null);
        this.dev = comuunicateDevice;
    }

    @Override
    public void onShow(MenuExecutor executr) {
        if (dev != null) {
            safeparams = dev.getLineParameters();
            if (safeparams != null) {
                address.setValue(safeparams.getLineParameter(LineParameters.IP_ADDRESS).toString());
                port.setValue((Integer) safeparams.getLineParameter(LineParameters.IP_PORT));
            }
        } else {
            dev = null;
        }
        super.onShow(executr);
    }

    @Override
    public void onHide() {
        if (dev != null) {
            if (dev != null) {
                //comline.start();
            }
            dev = null;
        }
        super.onHide();
    }


    @Override
    protected Node createView() {
        if (dev != null) {
            GridPane pane = new GridPane();
            pane.setId("formGrid");
            pane.setAlignment(Pos.CENTER);
            pane.setVgap(10);
            pane.setHgap(10);
            pane.setPadding(new Insets(5, 5, 5, 5));
            Label lbl_IP = new Label("ADDRESS:");
            lbl_IP.setId("formLabel");
            pane.add(lbl_IP, 0, 0);
            TextField adr = new TextField();
            adr.textProperty().bindBidirectional(address);
            pane.add(adr, 1, 0);
            Label lbl_Port = new Label("PORT:");
            lbl_Port.setId("formLabel");
            pane.add(lbl_Port, 0, 1);
            IntegerInputText prt = new IntegerInputText();
            prt.getIntegerValueProperty().bindBidirectional(port);
            pane.add(prt, 1, 1);
            Button ok = new Button("SAVE");
            ok.onActionProperty().setValue(arg0 -> {
                if (dev != null) {
                    CommunicationLineParameters newparams = new CommunicationLineParameters(address.getValue(), port.intValue());
                    dev.setLineParameters(newparams);
                    closeWindow();
                }
            });
            Button cancel = new Button("CANCEL");
            cancel.onActionProperty().setValue(arg0 -> {
                if (dev != null) {
                    //CommunicationLineParameters newparams=new CommunicationLineParameters(address.getValue(), port.intValue(),0);
                    dev.setLineParameters(safeparams);
                    closeWindow();
                }
            });
            pane.add(ok, 0, 2);
            pane.add(cancel, 1, 2);
            return pane;
        } else return null;
    }


    public StringProperty getAddress() {
        return address;
    }


    public IntegerProperty getPort() {
        return port;
    }


}

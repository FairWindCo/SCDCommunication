package ua.pp.fairwind.javafx.panels;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import ua.pp.fairwind.communications.devices.abstracts.LinedDeviceInterface;
import ua.pp.fairwind.communications.lines.lineparams.CommunicationLineParameters;
import ua.pp.fairwind.communications.lines.lineparams.LineParameters;
import ua.pp.fairwind.javafx.guiElements.menu.MenuExecutor;
import ua.pp.fairwind.javafx.guiElements.menu.PrefferedSize;
import ua.pp.fairwind.javafx.guiElements.windows.SimpleView;

public class RSConnectionForm extends SimpleView {
    final LinedDeviceInterface dev;
    final private ComboBox<String> ports = new ComboBox<>();
    final private ComboBox<ValueHolder> portBaud = new ComboBox<>();
    final private ComboBox<ValueHolder> portdataBits = new ComboBox<>();
    final private ComboBox<ValueHolder> portstopBits = new ComboBox<>();
    final private ComboBox<ValueHolder> portparity = new ComboBox<>();
    LineParameters safeparams;

    public RSConnectionForm(String title, Image icon,
                            PrefferedSize prefferedSize, LinedDeviceInterface comuunicateDevice) {
        super(title, icon, prefferedSize);
        this.dev = comuunicateDevice;
        formControlElements();
    }


    public RSConnectionForm(String title, LinedDeviceInterface comuunicateDevice) {
        super(title, null, (PrefferedSize) null);
        this.dev = comuunicateDevice;
        formControlElements();
    }

    @Override
    public void onShow(MenuExecutor executr) {
        if (dev != null) {
            safeparams = dev.getLineParameters();
            setBaud(((CommunicationLineParameters) safeparams).getBaoudrate());
            setDataBit(((CommunicationLineParameters) safeparams).getDatabits());
            setParity(((CommunicationLineParameters) safeparams).getParitytype());
            setStopBit(((CommunicationLineParameters) safeparams).getStopbit());
        }
        super.onShow(executr);
    }

    @Override
    public void onHide() {
        super.onHide();
    }


    public void setBaud(int index) {
        //System.out.println("index"+index);
        /*
    	if(portBaud.getItems().get(index)!=null){
    		System.out.println("select index"+index);
    		portBaud.setValue(portBaud.getItems().get(index));
    	}/**/
        portBaud.getSelectionModel().clearAndSelect(index);
    }

    public void setDataBit(int index) {
        portdataBits.getSelectionModel().clearAndSelect(index);
    }

    public void setStopBit(int index) {
        portstopBits.getSelectionModel().clearAndSelect(index);
    }

    public void setParity(int index) {
        portparity.getSelectionModel().clearAndSelect(index);
    }

    public void setPortIndex(int index) {
        ports.getSelectionModel().clearAndSelect(index);
    }


    public RSConnectionForm setupDataBit(int index) {
        portdataBits.getSelectionModel().clearAndSelect(index);
        return this;
    }

    public RSConnectionForm setupStopBit(int index) {
        portstopBits.getSelectionModel().clearAndSelect(index);
        return this;
    }

    public RSConnectionForm setupParity(int index) {
        portparity.getSelectionModel().clearAndSelect(index);
        return this;
    }

    public RSConnectionForm setupPortIndex(int index) {
        ports.getSelectionModel().clearAndSelect(index);
        return this;
    }


    protected void formControlElements() {
        portBaud.itemsProperty().setValue(FXCollections.observableArrayList(new ValueHolder(110, "BAUDRATE_110"), new ValueHolder(300, "BAUDRATE_300"), new ValueHolder(600, "BAUDRATE_600"), new ValueHolder(1200, "BAUDRATE_1200")
                , new ValueHolder(4800, "BAUDRATE_4800"), new ValueHolder(9600, "BAUDRATE_9600"), new ValueHolder(14400, "BAUDRATE_14400")
                , new ValueHolder(19200, "BAUDRATE_19200"), new ValueHolder(38400, "BAUDRATE_38400"), new ValueHolder(57600, "BAUDRATE_57600")
                , new ValueHolder(115200, "BAUDRATE_115200"), new ValueHolder(128000, "BAUDRATE_128000"), new ValueHolder(256000, "BAUDRATE_256000")));
        portBaud.setValue(new ValueHolder(9600, "BAUDRATE_9600"));

        portdataBits.itemsProperty().setValue(FXCollections.observableArrayList(new ValueHolder(5, "DATABITS_5"), new ValueHolder(6, "DATABITS_6"), new ValueHolder(7, "DATABITS_7"), new ValueHolder(8, "DATABITS_8")));
        portdataBits.setValue(new ValueHolder(8, "DATABITS_8"));

        portstopBits.itemsProperty().setValue(FXCollections.observableArrayList(new ValueHolder(1, "STOPBITS_1"), new ValueHolder(2, "STOPBITS_2"), new ValueHolder(3, "STOPBITS_1_5")));
        portstopBits.setValue(new ValueHolder(1, "STOPBITS_1"));

        portparity.itemsProperty().setValue(FXCollections.observableArrayList(new ValueHolder(0, "PARITY_NONE"), new ValueHolder(1, "PARITY_ODD"), new ValueHolder(2, "PARITY_EVEN"), new ValueHolder(3, "PARITY_MARK"), new ValueHolder(4, "PARITY_SPACE")));
        portparity.setValue(new ValueHolder(0, "PARITY_NONE"));
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
            Label lbl_IP = new Label("COM PORT:");
            lbl_IP.setId("formLabel");
            pane.add(lbl_IP, 0, 0);


            pane.add(ports, 1, 0);
            Label lbl_Port = new Label("BAUDRATE:");
            lbl_Port.setId("formLabel");
            pane.add(lbl_Port, 0, 1);
            pane.add(portBaud, 1, 1);


            Label lbl_DB = new Label("COM DATA BIT:");
            lbl_DB.setId("formLabel");
            pane.add(lbl_DB, 0, 2);
            pane.add(portdataBits, 1, 2);


            Label lbl_SB = new Label("COM STOP BIT:");
            lbl_SB.setId("formLabel");
            pane.add(lbl_SB, 0, 3);
            pane.add(portstopBits, 1, 3);


            Label lbl_P = new Label("COM PARITY:");
            lbl_P.setId("formLabel");
            pane.add(lbl_P, 0, 4);
            pane.add(portparity, 1, 4);

            Button ok = new Button("SAVE");
            ok.onActionProperty().setValue(arg0 -> {
                CommunicationLineParameters newparams = new CommunicationLineParameters(portBaud.getValue().getCode(), portdataBits.getValue().getCode(), portparity.getValue().getCode(), portstopBits.getValue().getCode());
                dev.setLineParameters(newparams);
                closeWindow();
            });
            Button cancel = new Button("CANCEL");
            cancel.onActionProperty().setValue(arg0 -> {
                MenuExecutor exec = getExecutr();
                if (exec != null) exec.closeView(RSConnectionForm.this);
            });
            pane.add(ok, 0, 5);
            pane.add(cancel, 1, 5);
            return pane;
        } else return null;
    }

    private class ValueHolder {
        private int code;
        private String name;

        public ValueHolder(int code, String name) {
            super();
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        @SuppressWarnings("unused")
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }


    }
}

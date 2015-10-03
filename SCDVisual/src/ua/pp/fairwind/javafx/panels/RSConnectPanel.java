package ua.pp.fairwind.javafx.panels;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import jssc.SerialPortList;
import ua.pp.fairwind.communications.devices.abstracts.DeviceInterface;
import ua.pp.fairwind.communications.lines.lineparams.CommunicationLineParameters;
import ua.pp.fairwind.javafx.I18N.I18N_FX;

public class RSConnectPanel extends StackPane {
    private Label portLabel = new Label(I18N_FX.getLocalizedString("rsPortLabel"));
    private ComboBox<String> ports = new ComboBox<>();
    private Label baudLabel = new Label(I18N_FX.getLocalizedString("baud"));
    private ComboBox<ValueHolder> portBaud = new ComboBox<>();
    private Label dataBitsLabel = new Label(I18N_FX.getLocalizedString("dataBits"));
    private ComboBox<ValueHolder> portdataBits = new ComboBox<>();
    private Label stopBitsLabel = new Label(I18N_FX.getLocalizedString("stopBits"));
    private ComboBox<ValueHolder> portstopBits = new ComboBox<>();
    private Label parityLabel = new Label(I18N_FX.getLocalizedString("parity"));
    private ComboBox<ValueHolder> portparity = new ComboBox<>();
    private DeviceInterface dev;
    private int pauseBeforeCommand = 0;


    public RSConnectPanel(DeviceInterface deviceInterface) {
        super();
        this.dev = deviceInterface;
        if (dev == null) throw new IllegalArgumentException("NULL Line not allowed!");
        initConrols();
    }

    public static String[] getPorts() {
        return SerialPortList.getPortNames();
    }

    private void initConrols() {
        Pane apanel = new Pane();
        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color: #88EE53;");
        GridPane grid = new GridPane();
        vbox.getChildren().add(grid);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(portLabel, 0, 0);
        grid.add(ports, 1, 0);
        grid.add(baudLabel, 0, 1);
        grid.add(portBaud, 1, 1);
        grid.add(dataBitsLabel, 0, 2);
        grid.add(portdataBits, 1, 2);
        grid.add(stopBitsLabel, 0, 3);
        grid.add(portstopBits, 1, 3);
        grid.add(parityLabel, 0, 4);
        grid.add(portparity, 1, 4);
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");
        Button buttonConnect = new Button(I18N_FX.getLocalizedString("CONNECT"));
        buttonConnect.setPrefSize(100, 20);

        String[] names = getPorts();
        ports.itemsProperty().setValue(FXCollections.observableArrayList(names));
        /*public static final int BAUDRATE_110 = 110;
    public static final int BAUDRATE_300 = 300;
    public static final int BAUDRATE_600 = 600;
    public static final int BAUDRATE_1200 = 1200;
    public static final int BAUDRATE_4800 = 4800;
    public static final int BAUDRATE_9600 = 9600;
    public static final int BAUDRATE_14400 = 14400;
    public static final int BAUDRATE_19200 = 19200;
    public static final int BAUDRATE_38400 = 38400;
    public static final int BAUDRATE_57600 = 57600;
    public static final int BAUDRATE_115200 = 115200;
    public static final int BAUDRATE_128000 = 128000;
    public static final int BAUDRATE_256000 = 256000;
		 * */
        int i = ports.itemsProperty().getValue().size();
        if (i > 0) {
            ports.setValue(ports.itemsProperty().getValue().get(0));
        } else {
            buttonConnect.setDisable(true);
        }
        portBaud.itemsProperty().setValue(FXCollections.observableArrayList(new ValueHolder(110, "BAUDRATE_110"), new ValueHolder(300, "BAUDRATE_300"), new ValueHolder(600, "BAUDRATE_600"), new ValueHolder(1200, "BAUDRATE_1200")
                , new ValueHolder(4800, "BAUDRATE_4800"), new ValueHolder(9600, "BAUDRATE_9600"), new ValueHolder(14400, "BAUDRATE_14400")
                , new ValueHolder(19200, "BAUDRATE_19200"), new ValueHolder(38400, "BAUDRATE_38400"), new ValueHolder(57600, "BAUDRATE_57600")
                , new ValueHolder(115200, "BAUDRATE_115200"), new ValueHolder(128000, "BAUDRATE_128000"), new ValueHolder(256000, "BAUDRATE_256000")));

        portBaud.setValue(new ValueHolder(115200, "BAUDRATE_115200"));
		
		/*
	    public static final int DATABITS_5 = 5;
	    public static final int DATABITS_6 = 6;
	    public static final int DATABITS_7 = 7;
	    public static final int DATABITS_8 = 8;
	    /**/
        portdataBits.itemsProperty().setValue(FXCollections.observableArrayList(new ValueHolder(5, "DATABITS_5"), new ValueHolder(6, "DATABITS_6"), new ValueHolder(7, "DATABITS_7"), new ValueHolder(8, "DATABITS_8")));
        portdataBits.setValue(new ValueHolder(8, "DATABITS_8"));
		
		
		/*
	    public static final int STOPBITS_1 = 1;
	    public static final int STOPBITS_2 = 2;
	    public static final int STOPBITS_1_5 = 3;
	    /**/
        portstopBits.itemsProperty().setValue(FXCollections.observableArrayList(new ValueHolder(1, "STOPBITS_1"), new ValueHolder(2, "STOPBITS_2"), new ValueHolder(3, "STOPBITS_1_5")));
        portstopBits.setValue(new ValueHolder(1, "STOPBITS_1"));
		
		/*    public static final int PARITY_NONE = 0;
			    public static final int PARITY_ODD = 1;
			    public static final int PARITY_EVEN = 2;
			    public static final int PARITY_MARK = 3;
			    public static final int PARITY_SPACE = 4;
		 * */
        portparity.itemsProperty().setValue(FXCollections.observableArrayList(new ValueHolder(0, "PARITY_NONE"), new ValueHolder(1, "PARITY_ODD"), new ValueHolder(2, "PARITY_EVEN"), new ValueHolder(3, "PARITY_MARK"), new ValueHolder(4, "PARITY_SPACE")));
        portparity.setValue(new ValueHolder(0, "PARITY_NONE"));
        hbox.getChildren().addAll(buttonConnect);
        vbox.getChildren().add(hbox);
        apanel.getChildren().add(vbox);
        this.getChildren().add(apanel);


        buttonConnect.setOnAction(event -> {
            CommunicationLineParameters newparams = new CommunicationLineParameters(portBaud.getValue().getCode(), portdataBits.getValue().getCode(), portparity.getValue().getCode(), portstopBits.getValue().getCode());
            dev.setLineParameters(newparams);
        });

    }

    public void abort() {

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

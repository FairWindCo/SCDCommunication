package ua.pp.fairwind.javafx.panels.devices.akon;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import ua.pp.fairwind.communications.devices.hardwaredevices.akon.AkonBase;
import ua.pp.fairwind.communications.propertyes.software.SoftByteProperty;
import ua.pp.fairwind.io.javafx.propertys.special.BytePropertyFXAdapterSpec;
import ua.pp.fairwind.javafx.I18N.I18N_FX;
import ua.pp.fairwind.javafx.VisualControls;
import ua.pp.fairwind.javafx.panels.TupicalPanels;

/**
 * Created by Сергей on 27.08.2015.
 */
public class AkonBasePanel extends HBox {
    final private AkonBase device;
    final private TabPane tabs = new TabPane();

    public AkonBasePanel(AkonBase device) {
        super();
        this.device = device;
        initControl();
    }

    public static ComboBox<Byte> createSpeedSelect(SoftByteProperty addressProperty) {
        ComboBox<Byte> box = new ComboBox<>();
        for (int i = 0x5; i < 0xE; i++) box.getItems().add((byte) i);
        box.setConverter(new StringConverter<Byte>() {
            @Override
            public String toString(Byte value) {
                switch (value) {
                    case 0x5:
                        return "BAUD RATE 4800";
                    case 0x6:
                        return "BAUD RATE 9600";
                    case 0x7:
                        return "BAUD RATE 14400";
                    case 0x8:
                        return "BAUD RATE 19200";
                    case 0x9:
                        return "BAUD RATE 38400";
                    case 0xA:
                        return "BAUD RATE 56000";
                    case 0xB:
                        return "BAUD RATE 57600";
                    case 0xC:
                        return "BAUD RATE 115200";
                    default:
                        return "BAUD RATE 9600";
                }
            }

            @Override
            public Byte fromString(String value) {
                switch (value) {
                    case "BAUD RATE 4800":
                        return 0x5;
                    case "BAUD RATE 9600":
                        return 0x6;
                    case "BAUD RATE 14400":
                        return 0x7;
                    case "BAUD RATE 19200":
                        return 0x8;
                    case "BAUD RATE 38400":
                        return 0x9;
                    case "BAUD RATE 56000":
                        return 0xA;
                    case "BAUD RATE 57600":
                        return 0xB;
                    case "BAUD RATE 115200":
                        return 0XC;
                    default:
                        return 0x6;
                }
            }
        });
        box.valueProperty().bindBidirectional(new BytePropertyFXAdapterSpec(addressProperty));
        Tooltip.install(box, new Tooltip(addressProperty.getDescription()));
        return box;
    }

    public static ComboBox<Byte> createProtoclSelect(SoftByteProperty addressProperty) {
        ComboBox<Byte> box = new ComboBox<>();
        for (int i = 0; i < 2; i++) box.getItems().add((byte) i);
        box.setConverter(new StringConverter<Byte>() {
            @Override
            public String toString(Byte value) {
                switch (value) {
                    case 0x0:
                        return "ObjectNet";
                    case 0x1:
                        return "MobBus RTU";
                    default:
                        return "ObjectNet";
                }
            }

            @Override
            public Byte fromString(String value) {
                switch (value) {
                    case "ObjectNet":
                        return 0x0;
                    case "MobBus RTU":
                        return 0x1;
                    default:
                        return 0x0;
                }
            }
        });
        box.valueProperty().bindBidirectional(new BytePropertyFXAdapterSpec(addressProperty));
        Tooltip.install(box, new Tooltip(addressProperty.getDescription()));
        return box;
    }

    public static ComboBox<Byte> createAddressSelect(SoftByteProperty addressProperty) {
        ComboBox<Byte> box = new ComboBox<>();
        for (int i = 0; i < 256; i++) box.getItems().add((byte) i);
        box.valueProperty().bindBidirectional(new BytePropertyFXAdapterSpec(addressProperty));
        return box;
    }

    public static ComboBox<Byte> createParitySelect(SoftByteProperty addressProperty) {
        ComboBox<Byte> box = new ComboBox<>();
        for (int i = 0; i < 5; i++) box.getItems().add((byte) i);
        box.setConverter(new StringConverter<Byte>() {
            @Override
            public String toString(Byte value) {
                switch (value) {
                    case 0x0:
                        return "ptNone";
                    case 0x1:
                        return "ptOdd";
                    case 0x2:
                        return "ptEven";
                    case 0x3:
                        return "ptMark";
                    case 0x4:
                        return "ptSpace";
                    default:
                        return "ptNone";
                }
            }

            @Override
            public Byte fromString(String value) {
                switch (value) {
                    case "ptNone":
                        return 0x0;
                    case "ptOdd":
                        return 0x1;
                    case "ptEven":
                        return 0x2;
                    case "ptMark":
                        return 0x3;
                    case "ptSpace":
                        return 0x4;
                    default:
                        return 0x0;
                }
            }
        });
        box.valueProperty().bindBidirectional(new BytePropertyFXAdapterSpec(addressProperty));
        Tooltip.install(box, new Tooltip(addressProperty.getDescription()));
        return box;
    }

    private void intiStatusPane() {
        final Tab initTab = new Tab(I18N_FX.getLocalizedString("STASUS"));
        tabs.getTabs().add(initTab);
        initTab.setClosable(false);
        initTab.setContent(TupicalPanels.createDeviceStatusPane(device));
    }

    private void intiDeviceControlPane() {
        final Tab initTab = new Tab(I18N_FX.getLocalizedString("DEVICE_INFO"));
        tabs.getTabs().add(initTab);
        initTab.setClosable(false);
        Platform.runLater(() -> {
            final GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setId("formGrid");
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(10, 10, 10, 10));

            int rowIndex = 0;
            rowIndex = TupicalPanels.setChanelControlRO(grid, device.getDeviceTypeProperty(), "DEVICE_TYPE", rowIndex, 0);
            rowIndex = TupicalPanels.setChanelControlRO(grid, device.getSerialNumber(), "SERIAL_NUM", rowIndex, 0);
            rowIndex = TupicalPanels.setChanelControlRO(grid, device.getAdditionalInfo(), "ADDITIONAL", rowIndex, 0);
            rowIndex = TupicalPanels.setChanelControlRO(grid, device.getPo(), "PO", rowIndex, 0);
            rowIndex = TupicalPanels.setChanelControlRO(grid, device.getReserv(), "RESERV", rowIndex, 0);
            rowIndex = TupicalPanels.setChanelControlRO(grid, device.getSystemTime(), "MODULE_TIME", rowIndex, 0);
            /*rowIndex=setAOChanelControl(grid, device.getAnalogOutChanelN1(), I18N_FX.getLocalizedStringEx("AO1"), rowIndex++, 0);
            rowIndex=setAOChanelControl(grid, device.getAnalogOutChanelN2(), I18N_FX.getLocalizedStringEx("AO2"), rowIndex++, 0);
            rowIndex=setAOChanelControl(grid, device.getAnalogOutChanelN3(), I18N_FX.getLocalizedStringEx("AO3"), rowIndex++, 0);
            rowIndex=setAOChanelControl(grid, device.getAnalogOutChanelN4(), I18N_FX.getLocalizedStringEx("AO4"), rowIndex++, 0);
            grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getReadAllAO()), 0, rowIndex, 3, 1);
            grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getWriteAllAO()), 2, rowIndex++,3,1);/**/
            /*
            rowIndex=setLongSpeedChanelControl(grid, device.getSpeed(), I18N_FX.getLocalizedString("SPEED"), rowIndex++, 0);
            rowIndex=setLongChanelControl(grid, device.getPosition(), I18N_FX.getLocalizedString("POSITION"), rowIndex++, 0);
            rowIndex=setLongChanelControl(grid, device.getStep(), I18N_FX.getLocalizedString("STEP"), rowIndex++, 0);
            grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getRotateLeft()), 0, rowIndex);
            grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getMotorStop()), 1, rowIndex);
            grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getRotateRight()), 2, rowIndex++);/***/
            initTab.setContent(grid);
        });
    }

    private void intiDeviceConfigPane() {
        final Tab initTab = new Tab(I18N_FX.getLocalizedString("SETUP"));
        tabs.getTabs().add(initTab);
        initTab.setClosable(false);
        Platform.runLater(() -> {
            final GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setId("formGrid");
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(10, 10, 10, 10));
            int rowindex = 0;
            grid.add(new Label(I18N_FX.getLocalizedString("DEVICE_ADDRESS")), 0, rowindex);

            grid.add(createAddressSelect(device.getAkonAddress()), 1, rowindex);
            grid.add(VisualControls.createReReadButton(device.getAkonAddress()), 2, rowindex);
            grid.add(VisualControls.createReWriteButton(device.getAkonAddress()), 3, rowindex++);

            grid.add(new Label(I18N_FX.getLocalizedString("DEVICE_SPEED")), 0, rowindex);
            grid.add(createSpeedSelect(device.getAkonBaudRate()), 1, rowindex);
            grid.add(VisualControls.createReReadButton(device.getAkonBaudRate()), 2, rowindex);
            grid.add(VisualControls.createReWriteButton(device.getAkonBaudRate()), 3, rowindex++);

            grid.add(new Label(I18N_FX.getLocalizedString("DEVICE_PARITY")), 0, rowindex);
            grid.add(createParitySelect(device.getAkonParity()), 1, rowindex);
            grid.add(VisualControls.createReReadButton(device.getAkonParity()), 2, rowindex);
            grid.add(VisualControls.createReWriteButton(device.getAkonParity()), 3, rowindex++);

            grid.add(new Label(I18N_FX.getLocalizedString("DEVICE_PROTOCOL")), 0, rowindex);
            grid.add(createProtoclSelect(device.getAkonSetupedProtocol()), 1, rowindex);
            grid.add(VisualControls.createReReadButton(device.getAkonSetupedProtocol()), 2, rowindex);
            grid.add(VisualControls.createReWriteButton(device.getAkonSetupedProtocol()), 3, rowindex++);

            grid.add(VisualControls.createReReadButton(device.getDeviceconfig()), 0, rowindex, 2, 1);
            grid.add(VisualControls.createReWriteButton(device.getDeviceconfig()), 2, rowindex++, 2, 1);
            /*
            grid.add(new Label(I18N_FX.getLocalizedString("DEVICE HOST ADDRESS")), 0, rowindex);
            grid.add(createAddressSelect(device.getSerialHostAdress()), 1, rowindex);
            grid.add(createReReadButton(device.getSerialHostAdress()), 2, rowindex);
            grid.add(createReWriteButton(device.getSerialHostAdress()), 3, rowindex++);

            setDOChanelControl(grid, device.getAutoStartTMCL(), I18N_FX.getLocalizedString("AutoStartTMCL"), rowindex++, 0);
            setDOChanelControl(grid, device.getConfigurationEpromLock(), I18N_FX.getLocalizedString("ConfigurationEpromLock"), rowindex++, 0);
            setDOChanelControl(grid, device.getEndSwichPolarity(), I18N_FX.getLocalizedString("EndSwichPolarity"), rowindex++, 0);
            setDIChanelControl(grid, device.getDowloadMode(), I18N_FX.getLocalizedString("DowloadMode"), rowindex++, 0);
            rowindex=setLongChanelControl(grid, device.getTickTimer(), I18N_FX.getLocalizedString("Ticktimer"), rowindex, 0);
            rowindex=setLongChanelControl(grid, device.getRandomNumber(), I18N_FX.getLocalizedString("RANDOM"), rowindex, 0);/**/
            initTab.setContent(grid);
        });
    }

    private void initControl() {
        setAlignment(Pos.CENTER);
        intiStatusPane();
        intiDeviceControlPane();
        intiDeviceConfigPane();
        tabs.setPrefHeight(430);
        getChildren().add(tabs);
    }

}

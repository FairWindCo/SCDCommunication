package ua.pp.fairwind.javafx.panels.devices.panDrive;

import eu.hansolo.enzo.canvasled.Led;
import eu.hansolo.enzo.lcd.Lcd;
import eu.hansolo.enzo.lcd.LcdBuilder;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import ua.pp.fairwind.communications.devices.AbstractDevice;
import ua.pp.fairwind.communications.devices.RSLineDevice;
import ua.pp.fairwind.communications.devices.hardwaredevices.panDrive.StepDriver;
import ua.pp.fairwind.communications.propertyes.DeviceNamedCommandProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftBoolProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;
import ua.pp.fairwind.io.javafx.propertys.BooleanPropertyFXAdapter;
import ua.pp.fairwind.io.javafx.propertys.LongPropertyFXAdapter;
import ua.pp.fairwind.io.javafx.propertys.ShortPropertyFXAdapter;
import ua.pp.fairwind.io.javafx.propertys.special.LongPropertyFXAdapterSpec;
import ua.pp.fairwind.io.javafx.propertys.special.ShortPropertyFXAdapterSpec;
import ua.pp.fairwind.javafx.I18N.I18N;
import ua.pp.fairwind.javafx.controls.slidecheckbox.SlideCheckBox;
import ua.pp.fairwind.javafx.panels.devices.DeviceConfigPanel;
import ua.pp.fairwind.javafx.panels.dialogs.PropertyConfigDialog;

import static ua.pp.fairwind.javafx.panels.devices.DeviceConfigPanel.createAddressSelect;


/**
 * Created by Сергей on 27.08.2015.
 */
public class PanDrivePanel extends HBox {
    final private StepDriver device;
    final private TabPane tabs=new TabPane();

    public PanDrivePanel(StepDriver device) {
        super();
        this.device = device;
        initControl();
    }

    private void  intiStatusPane(){
        final Tab initTab=new Tab(I18N.getLocalizedString("STASUS"));
        tabs.getTabs().add(initTab);
        initTab.setClosable(false);
        initTab.setContent(createDeviceStatusPane(device));
    }

    public static Pane createDeviceStatusPane(RSLineDevice device){
        final GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setPadding(new Insets(30, 20, 10, 10));
        int rowindex=0;
        grid.setId("formGrid");
        grid.add(new Label(device.getDeviceType() + " : " + device.getName() + " UUID=" + device.getUUID()), 0, rowindex++, 3, 1);
        grid.add(new Label(device.getDescription()),0,rowindex++,3,1);
        grid.add(new Label(I18N.COMMON.getString("DEVICE_ADDRES")), 0, rowindex);
        grid.add(createAddressSelect(device.getDeviceAddressProperty()), 1, rowindex++);

        grid.add(new Label(I18N.COMMON.getString("DEVICE_STATUS")), 0, rowindex);
        grid.add(new Label(I18N.COMMON.getString("DEVICE_LINE1_STATUS")), 1, rowindex);
        grid.add(new Label(I18N.COMMON.getString("DEVICE_LINE2_STATUS")), 2, rowindex++);

        grid.add(createLedIndicator(device.getLastCommunicationStatus(),Color.GREENYELLOW), 0, rowindex);
        grid.add(createLedIndicator(device.getLastCommunicationStatusLine1(),Color.GREENYELLOW), 1, rowindex);
        grid.add(createLedIndicator(device.getLastCommunicationStatusLine2(),Color.GREENYELLOW), 2, rowindex++);

        grid.add(new Label(I18N.COMMON.getString("DEVICE_ERROR")), 0, rowindex);
        grid.add(new Label(I18N.COMMON.getString("DEVICE_LINE1_ERROR")), 1, rowindex);
        grid.add(new Label(I18N.COMMON.getString("DEVICE_LINE2_ERROR")), 2, rowindex++);


        grid.add(createLedIndicator(device.getErrorCommunicationStatus(),Color.RED), 0, rowindex);
        grid.add(createLedIndicator(device.getErrorCommunicationStatusLine1(),Color.RED), 1, rowindex);
        grid.add(createLedIndicator(device.getErrorCommunicationStatusLine2(),Color.RED), 2, rowindex++);

        grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getValidateErrorCommand()), 0, rowindex);
        grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getValidateErrorCommandLine1()), 1, rowindex);
        grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getValidateErrorCommandLine2()), 2, rowindex++);

        grid.add(new Label(I18N.COMMON.getString("LAST_COMMUNICATE_TIME")), 0, rowindex++);

        grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getRefreshCommand()), 0, rowindex);
        grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getValidateAllErrorCommand()), 2, rowindex);
        return grid;
    }

    private void intiDeviceControlPane(){
        final Tab initTab=new Tab(I18N.getLocalizedString("DEVICE VALUE"));
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
            /*rowIndex=setAOChanelControl(grid, device.getAnalogOutChanelN1(), I18N.COMMON.getStringEx("AO1"), rowIndex++, 0);
            rowIndex=setAOChanelControl(grid, device.getAnalogOutChanelN2(), I18N.COMMON.getStringEx("AO2"), rowIndex++, 0);
            rowIndex=setAOChanelControl(grid, device.getAnalogOutChanelN3(), I18N.COMMON.getStringEx("AO3"), rowIndex++, 0);
            rowIndex=setAOChanelControl(grid, device.getAnalogOutChanelN4(), I18N.COMMON.getStringEx("AO4"), rowIndex++, 0);
            grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getReadAllAO()), 0, rowIndex, 3, 1);
            grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getWriteAllAO()), 2, rowIndex++,3,1);/**/
            rowIndex=setLongSpeedChanelControl(grid, device.getSpeed(), I18N.getLocalizedString("SPEED"), rowIndex++, 0);
            rowIndex=setLongChanelControl(grid, device.getPosition(), I18N.getLocalizedString("POSITION"), rowIndex++, 0);
            rowIndex=setLongChanelControl(grid, device.getStep(), I18N.getLocalizedString("STEP"), rowIndex++, 0);
            grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getRotateLeft()), 0, rowIndex);
            grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getMotorStop()), 1, rowIndex);
            grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getRotateRight()), 2, rowIndex++);
            initTab.setContent(grid);
        });
    }

    private void intiDeviceConfigPane(){
        final Tab initTab=new Tab(I18N.getLocalizedString("SETUP"));
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
            grid.add(new Label(I18N.getLocalizedString("DEVICE ADDRESS")), 0, rowindex);
            grid.add(createAddressSelect(device.getRsadress()), 1, rowindex);
            grid.add(createReReadButton(device.getRsadress()), 2, rowindex);
            grid.add(createReWriteButton(device.getRsadress()), 3, rowindex++);

            grid.add(new Label(I18N.getLocalizedString("DEVICE SPEED")), 0, rowindex);
            grid.add(createSpeedSelect(device.getRsspeed()), 1, rowindex);
            grid.add(createReReadButton(device.getRsspeed()), 2, rowindex);
            grid.add(createReWriteButton(device.getRsspeed()), 3, rowindex++);

            grid.add(new Label(I18N.getLocalizedString("DEVICE HOST ADDRESS")), 0, rowindex);
            grid.add(createAddressSelect(device.getSerialHostAdress()), 1, rowindex);
            grid.add(createReReadButton(device.getSerialHostAdress()), 2, rowindex);
            grid.add(createReWriteButton(device.getSerialHostAdress()), 3, rowindex++);

            grid.add(new Label(I18N.getLocalizedString("DEVICE BROADCAST ADDRESS")), 0, rowindex);
            grid.add(createAddressSelect(device.getSerialSecondAdress()), 1, rowindex);
            grid.add(createReReadButton(device.getSerialSecondAdress()), 2, rowindex);
            grid.add(createReWriteButton(device.getSerialSecondAdress()), 3, rowindex++);


            setDOChanelControl(grid, device.getAutoStartTMCL(), I18N.getLocalizedString("AutoStartTMCL"), rowindex++, 0);
            setDOChanelControl(grid, device.getConfigurationEpromLock(), I18N.getLocalizedString("ConfigurationEpromLock"), rowindex++, 0);
            setDOChanelControl(grid, device.getEndSwichPolarity(), I18N.getLocalizedString("EndSwichPolarity"), rowindex++, 0);
            setDIChanelControl(grid, device.getDowloadMode(), I18N.getLocalizedString("DowloadMode"), rowindex++, 0);
            rowindex=setLongChanelControl(grid, device.getTickTimer(), I18N.getLocalizedString("Ticktimer"), rowindex, 0);
            rowindex=setLongChanelControl(grid, device.getRandomNumber(), I18N.getLocalizedString("RANDOM"), rowindex, 0);
            initTab.setContent(grid);
        });
    }


    private void setDIChanelControl(GridPane grid,SoftBoolProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(name), col+0, rowindex);
        grid.add(createLedIndicator(chanel, Color.CORAL), col + 1, rowindex);
        grid.add(createReReadButton(chanel), col + 2, rowindex);
        grid.add(createConfigureProppearty(chanel), col + 3, rowindex);
    }

    private void setDOChanelControl(GridPane grid,SoftBoolProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(name), col++, rowindex);
        grid.add(createLedIndicator(chanel, Color.AQUA), col++, rowindex);
        grid.add(createSlideIndicator(chanel), col++, rowindex);
        grid.add(createReReadButton(chanel), col++, rowindex);
        grid.add(createReWriteButton(chanel), col++, rowindex);
    }

    private int setLongChanelControl(GridPane grid,SoftLongProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(name), col++, rowindex);
        grid.add(createLcdIndicator(chanel), col++, rowindex);

        //grid.add(createBoolChangeCommandButton(chanel), col++, rowindex);
        grid.add(createReReadButton(chanel), col++, rowindex);
        grid.add(createReWriteButton(chanel), col++, rowindex);
        grid.add(createConfigureProppearty(chanel), col++, rowindex++);
        grid.add(createSliderControl(chanel, 0, Integer.MAX_VALUE, Integer.MAX_VALUE/1000, 10000000, 10), 0, rowindex++,5,1);
        return rowindex;
    }

    private int setLongSpeedChanelControl(GridPane grid,SoftShortProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(name), col++, rowindex);
        grid.add(createLcdIndicator(chanel), col++, rowindex);

        //grid.add(createBoolChangeCommandButton(chanel), col++, rowindex);
        grid.add(createReReadButton(chanel), col++, rowindex);
        grid.add(createReWriteButton(chanel), col++, rowindex);
        grid.add(createConfigureProppearty(chanel), col++, rowindex++);
        grid.add(createSliderControl(chanel, 0, 2047, 100, 100, 10), 0, rowindex++,5,1);
        return rowindex;
    }

    private int setShortChanelControl(GridPane grid,SoftShortProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(name), col++, rowindex);
        grid.add(createLcdIndicator(chanel), col++, rowindex);

        //grid.add(createBoolChangeCommandButton(chanel), col++, rowindex);
        grid.add(createReReadButton(chanel), col++, rowindex);
        grid.add(createReWriteButton(chanel), col++, rowindex);
        grid.add(createConfigureProppearty(chanel), col++, rowindex++);
        grid.add(createSliderControl(chanel, 0, 10, 1, 10, 10), 0, rowindex++,5,1);
        return rowindex;
    }







    private void initControl(){
        setAlignment(Pos.CENTER);
        intiStatusPane();
        intiDeviceControlPane();
        intiDeviceConfigPane();
        tabs.setPrefHeight(430);
        getChildren().add(tabs);
    }


    private Button createConfigureProppearty(AbstractProperty command){
        return PropertyConfigDialog.crateConfigButton(command);
    }

    private Button createCommandExecuteButton(DeviceNamedCommandProperty command){
        Button button=new Button(command.getDescription());
        button.setOnAction(event->command.activate());
        return button;
    }


    private Button createSetLinebuttonImidiatly(SoftLongProperty property,final long value){
        Button button=new Button(String.valueOf(value));
        button.setOnAction(event->{
            property.setValue(value);
            Boolean imid=(Boolean)property.getAdditionalInfo(AbstractDevice.IMMEDIATELY_WRITE_FLAG);
            if(imid==null||!imid){
                property.writeValueRequest();
            }
        });
        return button;
    }

    private Button createBoolChangeCommandButton(SoftBoolProperty property){
        Button button=new Button(I18N.getLocalizedString("CHANGE"));
        button.setOnAction(event->property.invertValue());
        return button;
    }

    private Button createReReadButton(AbstractProperty command){
        Button button=new Button(I18N.getLocalizedString("READ"));
        button.setOnAction(event->command.readValueRequest());
        return button;
    }

    private Button createReWriteButton(AbstractProperty command){
        Button button=new Button(I18N.getLocalizedString("SAVE"));
        button.setOnAction(event -> command.writeValueRequest());
        return button;
    }

    public static Led createLedIndicator(SoftBoolProperty property,Color color){
        Led led = new Led();
        led.setLedColor(color);
        led.onProperty().bind(new BooleanPropertyFXAdapter(property));
        return led;
    }

    private SlideCheckBox createSlideIndicator(SoftBoolProperty property){
        SlideCheckBox led = new SlideCheckBox();
        led.setScaleX(0.4);
        led.setScaleY(0.4);
        led.selectedProperty().bindBidirectional(new BooleanPropertyFXAdapter(property));
        return led;
    }

    public static Lcd  createLcdIndicator(SoftShortProperty property){
        Lcd lcd = LcdBuilder.create()
                .prefWidth(170)
                .prefHeight(50)
                .styleClass(Lcd.STYLE_CLASS_STANDARD)
                .backgroundVisible(true)
                .foregroundShadowVisible(true)
                .crystalOverlayVisible(true)
                .title(property.getDescription())
                .titleVisible(true)
                .decimals(0)
                .valueFont(Lcd.LcdFont.LCD)
                .animated(true)
                .build();
        lcd.valueProperty().bind(new ShortPropertyFXAdapterSpec(property));
        return lcd;
    }

    public static Lcd  createLcdIndicator(SoftLongProperty property){
        Lcd lcd = LcdBuilder.create()
                .prefWidth(170)
                .prefHeight(50)
                .styleClass(Lcd.STYLE_CLASS_STANDARD)
                .backgroundVisible(true)
                .foregroundShadowVisible(true)
                .crystalOverlayVisible(true)
                .title(property.getDescription())
                .titleVisible(true)
                .decimals(0)
                .valueFont(Lcd.LcdFont.LCD)
                .animated(true)
                .build();
        lcd.valueProperty().bind(new LongPropertyFXAdapterSpec(property));
        return lcd;
    }

    static public Slider createSliderControl(SoftLongProperty property,int min,int max,int blockincrement,int majorTick,int minorTick){
        Slider slider = new Slider();
        slider.setMin(min);
        slider.setMax(max);
        slider.valueProperty().bindBidirectional(new LongPropertyFXAdapter(property));
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(majorTick);
        slider.setMinorTickCount(minorTick);
        slider.setBlockIncrement(blockincrement);
        return slider;
    }

    static public Slider createSliderControl(SoftShortProperty property,int min,int max,int blockincrement,int majorTick,int minorTick){
        Slider slider = new Slider();
        slider.setMin(min);
        slider.setMax(max);
        slider.valueProperty().bindBidirectional(new ShortPropertyFXAdapter(property));
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(majorTick);
        slider.setMinorTickCount(minorTick);
        slider.setBlockIncrement(blockincrement);
        return slider;
    }

    static public void executeInJavaFXThread(Runnable acriton){
        if(Platform.isFxApplicationThread()){
            acriton.run();
        } else {
            try{
                Platform.runLater(acriton);
            } catch (IllegalStateException ex){
                acriton.run();
            }
        }
    }

    public static ComboBox<Short> createSpeedSelect(SoftShortProperty addressProperty){
        ComboBox<Short> box=new ComboBox<>();
        for(int i=1;i<5;i++)box.getItems().add((short)i);
        box.setConverter(new StringConverter<Short>() {
            @Override
            public String toString(Short value) {
                switch (value){
                    case 0:return"BAUD RATE 9600";
                    case 1:return"BAUD RATE 14400";
                    case 2:return"BAUD RATE 19200";
                    case 3:return"BAUD RATE 28800";
                    case 4:return"BAUD RATE 38400";
                    case 5:return"BAUD RATE 57600";
                    case 6:return"BAUD RATE 76800";
                    case 7:return"BAUD RATE 115000";
                    case 8:return"BAUD RATE 230400";
                    case 9:return"BAUD RATE 250000";
                    case 10:return"BAUD RATE 500000";
                    case 11:return"BAUD RATE 100000";
                    default:return "BAUD RATE 9600";
                }
            }

            @Override
            public Short fromString(String value) {
                switch (value){
                    case "BAUD RATE 9600":return 0;
                    case "BAUD RATE 14400":return 1;
                    case "BAUD RATE 19200":return 2;
                    case "BAUD RATE 28800":return 3;
                    case "BAUD RATE 38400":return 4;
                    case "BAUD RATE 57600":return 5;
                    case "BAUD RATE 76800":return 6;
                    case "BAUD RATE 115000":return 7;
                    case "BAUD RATE 230400":return 8;
                    case "BAUD RATE 250000":return 9;
                    case "BAUD RATE 500000":return 10;
                    case "BAUD RATE 100000":return 11;
                    default:return 1;
                }
            }
        });
        box.valueProperty().bindBidirectional(new ShortPropertyFXAdapterSpec(addressProperty));
        return box;
    }
}

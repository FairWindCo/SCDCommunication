package ua.pp.fairwind.javafx.panels.devices;

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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import ua.pp.fairwind.communications.devices.AbstractDevice;
import ua.pp.fairwind.communications.devices.RSLineDevice;
import ua.pp.fairwind.communications.devices.hardwaredevices.favorit.FavoritCoreDeviceV1;
import ua.pp.fairwind.communications.propertyes.DeviceNamedCommandProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftBoolProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftFloatProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;
import ua.pp.fairwind.io.javafx.propertys.BooleanPropertyFXAdapter;
import ua.pp.fairwind.io.javafx.propertys.FloatPropertyFXAdapter;
import ua.pp.fairwind.io.javafx.propertys.LongPropertyFXAdapter;
import ua.pp.fairwind.io.javafx.propertys.special.ShortPropertyFXAdapterSpec;
import ua.pp.fairwind.javafx.I18N.I18N_monitor;
import ua.pp.fairwind.javafx.controls.slidecheckbox.SlideCheckBox;
import ua.pp.fairwind.javafx.panels.DeviceConfigPanel;
import ua.pp.fairwind.javafx.panels.dialogs.PropertyConfigDialog;

import static ua.pp.fairwind.javafx.panels.DeviceConfigPanel.createAddressSelect;


/**
 * Created by Сергей on 27.08.2015.
 */
public class FavoritPanel extends HBox {
    final private FavoritCoreDeviceV1 device;
    final private TabPane tabs=new TabPane();
    public FavoritPanel(FavoritCoreDeviceV1 device) {
        super();
        this.device = device;
        initControl();
    }

    private void  intiStatusPane(){
        final Tab initTab=new Tab(I18N_monitor.COMMON.getStringEx("STASUS"));
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
        grid.add(new Label(I18N_monitor.COMMON.getString("DEVICE_ADDRES")), 0, rowindex);
        grid.add(createAddressSelect(device.getDeviceAddressProperty()), 1, rowindex++);

        grid.add(new Label(I18N_monitor.COMMON.getString("DEVICE_STATUS")), 0, rowindex);
        grid.add(new Label(I18N_monitor.COMMON.getString("DEVICE_LINE1_STATUS")), 1, rowindex);
        grid.add(new Label(I18N_monitor.COMMON.getString("DEVICE_LINE2_STATUS")), 2, rowindex++);

        grid.add(createLedIndicator(device.getLastCommunicationStatus(),Color.GREENYELLOW), 0, rowindex);
        grid.add(createLedIndicator(device.getLastCommunicationStatusLine1(),Color.GREENYELLOW), 1, rowindex);
        grid.add(createLedIndicator(device.getLastCommunicationStatusLine2(),Color.GREENYELLOW), 2, rowindex++);

        grid.add(new Label(I18N_monitor.COMMON.getString("DEVICE_ERROR")), 0, rowindex);
        grid.add(new Label(I18N_monitor.COMMON.getString("DEVICE_LINE1_ERROR")), 1, rowindex);
        grid.add(new Label(I18N_monitor.COMMON.getString("DEVICE_LINE2_ERROR")), 2, rowindex++);


        grid.add(createLedIndicator(device.getErrorCommunicationStatus(),Color.RED), 0, rowindex);
        grid.add(createLedIndicator(device.getErrorCommunicationStatusLine1(),Color.RED), 1, rowindex);
        grid.add(createLedIndicator(device.getErrorCommunicationStatusLine2(),Color.RED), 2, rowindex++);

        grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getValidateErrorCommand()), 0, rowindex);
        grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getValidateErrorCommandLine1()), 1, rowindex);
        grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getValidateErrorCommandLine2()), 2, rowindex++);

        grid.add(new Label(I18N_monitor.COMMON.getString("LAST_COMMUNICATE_TIME")), 0, rowindex++);

        grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getRefreshCommand()), 0, rowindex);
        grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getValidateAllErrorCommand()), 2, rowindex);
        return grid;
    }

    private void  intiDIPane(){
        final Tab initTab=new Tab(I18N_monitor.COMMON.getStringEx("DIGITAL IN"));
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
            setDIChanelControl(grid, device.getDigitalInChanelN1(), I18N_monitor.COMMON.getStringEx("DI1"), rowIndex++, 0);
            setDIChanelControl(grid, device.getDigitalInChanelN2(), I18N_monitor.COMMON.getStringEx("DI2"), rowIndex++, 0);
            setDIChanelControl(grid, device.getDigitalInChanelN3(), I18N_monitor.COMMON.getStringEx("DI3"), rowIndex++, 0);
            setDIChanelControl(grid, device.getDigitalInChanelN4(), I18N_monitor.COMMON.getStringEx("DI4"), rowIndex++, 0);
            setDIChanelControl(grid, device.getDigitalInChanelN5(), I18N_monitor.COMMON.getStringEx("DI5"), rowIndex++, 0);
            setDIChanelControl(grid, device.getDigitalInChanelN6(), I18N_monitor.COMMON.getStringEx("DI6"), rowIndex++, 0);
            grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getReadAllDI()), 0, rowIndex++,3,1);
            initTab.setContent(grid);
        });
    }

    private void  intiDOPane(){
        final Tab initTab=new Tab(I18N_monitor.COMMON.getStringEx("DIGITAL OUT"));
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
            setDOChanelControl(grid, device.getDigitalOutChanelN1(), I18N_monitor.COMMON.getStringEx("DO1"), rowIndex++, 0);
            setDOChanelControl(grid, device.getDigitalOutChanelN2(), I18N_monitor.COMMON.getStringEx("DO2"), rowIndex++, 0);
            setDOChanelControl(grid, device.getDigitalOutChanelN3(), I18N_monitor.COMMON.getStringEx("DO3"), rowIndex++, 0);
            setDOChanelControl(grid, device.getDigitalOutChanelN4(), I18N_monitor.COMMON.getStringEx("DO4"), rowIndex++, 0);
            setDOChanelControl(grid, device.getDigitalOutChanelN5(), I18N_monitor.COMMON.getStringEx("DO5"), rowIndex++, 0);
            setDOChanelControl(grid, device.getDigitalOutChanelN6(), I18N_monitor.COMMON.getStringEx("DO6"), rowIndex++, 0);
            grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getReadAllDO()), 0, rowIndex, 3, 1);
            grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getWriteAllDO()), 4, rowIndex++,3,1);
            initTab.setContent(grid);
        });
    }

    private void intiAOPane(){
        final Tab initTab=new Tab(I18N_monitor.COMMON.getStringEx("ANALOG OUT"));
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
            rowIndex=setAOChanelControl(grid, device.getAnalogOutChanelN1(), I18N_monitor.COMMON.getStringEx("AO1"), rowIndex++, 0);
            rowIndex=setAOChanelControl(grid, device.getAnalogOutChanelN2(), I18N_monitor.COMMON.getStringEx("AO2"), rowIndex++, 0);
            rowIndex=setAOChanelControl(grid, device.getAnalogOutChanelN3(), I18N_monitor.COMMON.getStringEx("AO3"), rowIndex++, 0);
            rowIndex=setAOChanelControl(grid, device.getAnalogOutChanelN4(), I18N_monitor.COMMON.getStringEx("AO4"), rowIndex++, 0);
            grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getReadAllAO()), 0, rowIndex,3,1);
            grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getWriteAllAO()), 2, rowIndex++,3,1);
            initTab.setContent(grid);
        });
    }

    private void intiAIPane(){
        final Tab initTab=new Tab(I18N_monitor.COMMON.getStringEx("ANALOG IN"));
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
            setAIChanelControl(grid, device.getAnalogInChanelN1(), I18N_monitor.COMMON.getStringEx("AI1"), rowIndex++, 0);
            setAIChanelControl(grid, device.getAnalogInChanelN2(), I18N_monitor.COMMON.getStringEx("AI2"), rowIndex++, 0);
            setAIChanelControl(grid, device.getAnalogInChanelN3(), I18N_monitor.COMMON.getStringEx("AI3"), rowIndex++, 0);
            setAIChanelControl(grid, device.getAnalogInChanelN4(), I18N_monitor.COMMON.getStringEx("AI4"), rowIndex++, 0);
            grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getReadAllAI()), 0, rowIndex++, 3, 1);
            initTab.setContent(grid);
        });
    }

    private void intiLinePane(){
        final Tab initTab=new Tab(I18N_monitor.COMMON.getStringEx("LINE CONTROL"));
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
            setLineSelectChanelControl(grid, device.getLineSelect(), I18N_monitor.COMMON.getStringEx("LINE_CONTROL"), rowIndex++, 0);
            initTab.setContent(grid);
        });
    }

    private Pane createImidiatlyLineSelectPane(){
        final GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setId("formGrid");
        grid.setStyle("-fx-border-width: 1; -fx-border-style: solid;");
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        int rowIndex = 0;
        setLineSelectChanelControlImid(grid, device.getLineSelect(), I18N_monitor.COMMON.getStringEx("LINE_CONTROL"), rowIndex++, 0);
        return grid;
    }

    private void intiDeviceConfigPane(){
        final Tab initTab=new Tab(I18N_monitor.COMMON.getStringEx("SETUP"));
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
            grid.add(new Label(I18N_monitor.COMMON.getStringEx("DEVICE ADDRESS")), 0, rowindex);
            grid.add(createAddressSelect(device.getConfigdeviceAddress()), 1, rowindex);
            grid.add(createReReadButton(device.getConfigdeviceAddress()), 2, rowindex);
            grid.add(createReWriteButton(device.getConfigdeviceAddress()), 3, rowindex++);

            grid.add(new Label(I18N_monitor.COMMON.getStringEx("DEVICE SPEED")), 0, rowindex);
            grid.add(createSpeedSelect(device.getConfigdeviceSpeed()), 1, rowindex);
            grid.add(createReReadButton(device.getConfigdeviceSpeed()), 2, rowindex);
            grid.add(createReWriteButton(device.getConfigdeviceSpeed()), 3, rowindex);
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
        grid.add(createLedIndicator(chanel,Color.AQUA), col++, rowindex);
        grid.add(createSlideIndicator(chanel), col++, rowindex);
        grid.add(createBoolChangeCommandButton(chanel), col++, rowindex);
        grid.add(createReReadButton(chanel), col++, rowindex);
        grid.add(createReWriteButton(chanel), col++, rowindex);
        grid.add(createConfigureProppearty(chanel), col++, rowindex);
    }

    private int setAOChanelControl(GridPane grid,SoftFloatProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(name), col++, rowindex);
        grid.add(createLcdIndicator(chanel), col++, rowindex);

        //grid.add(createBoolChangeCommandButton(chanel), col++, rowindex);
        grid.add(createReReadButton(chanel), col++, rowindex);
        grid.add(createReWriteButton(chanel), col++, rowindex);
        grid.add(createConfigureProppearty(chanel), col++, rowindex++);
        grid.add(createSliderControl(chanel, 0, 10, 1, 10, 10), 0, rowindex++,5,1);
        return rowindex;
    }



    private void setAIChanelControl(GridPane grid,SoftFloatProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(name), col+0, rowindex);
        grid.add(createLcdIndicator(chanel), col+1, rowindex);
        grid.add(createReReadButton(chanel), col + 2, rowindex);
        grid.add(createConfigureProppearty(chanel), col + 3, rowindex);
    }


    private void setLineSelectChanelControl(GridPane grid,SoftLongProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(name), col++, rowindex);
        grid.add(createLineIndicator(chanel), col++, rowindex);
        grid.add(createReReadButton(chanel), col++, rowindex);
        grid.add(createReWriteButton(chanel), col++, rowindex);
        grid.add(createConfigureProppearty(chanel), col++, rowindex++);
        col=2;
        grid.add(createSetLinebutton(device.getLineSelect(), 0), col, rowindex);
        col++;
        grid.add(createSetLinebutton(device.getLineSelect(), 1), col++, rowindex);
        grid.add(createSetLinebutton(device.getLineSelect(), 2), col++, rowindex);
        grid.add(createSetLinebutton(device.getLineSelect(), 3), col++, rowindex);
        grid.add(createSetLinebutton(device.getLineSelect(), 4), col++, rowindex);
    }

    private void setLineSelectChanelControlImid(GridPane grid,SoftLongProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(name), col++, rowindex);
        grid.add(createLineIndicator(chanel), col++, rowindex);
        col++;
        grid.add(createSetLinebuttonImidiatly(device.getLineSelect(), 0), col, rowindex);
        col++;
        grid.add(createSetLinebuttonImidiatly(device.getLineSelect(), 1), col++, rowindex);
        grid.add(createSetLinebuttonImidiatly(device.getLineSelect(), 2), col++, rowindex);
        grid.add(createSetLinebuttonImidiatly(device.getLineSelect(), 3), col++, rowindex);
        grid.add(createSetLinebuttonImidiatly(device.getLineSelect(), 4), col++, rowindex);
    }

    private void initControl(){
        setAlignment(Pos.CENTER);
        intiStatusPane();
        intiDIPane();
        intiDOPane();
        intiAIPane();
        intiAOPane();
        intiLinePane();
        intiDeviceConfigPane();
        VBox hbox=new VBox();
        hbox.setAlignment(Pos.CENTER);
        tabs.setPrefHeight(430);
        hbox.setSpacing(10);
        hbox.setStyle("-fx-border-width: 1; -fx-border-style: solid;");
        hbox.getChildren().add(createImidiatlyLineSelectPane());
        hbox.getChildren().add(tabs);
        hbox.setAlignment(Pos.CENTER);
        getChildren().add(hbox);
    }




    private Button createConfigureProppearty(AbstractProperty command){
        return PropertyConfigDialog.crateConfigButton(command);
    }

    private Button createCommandExecuteButton(DeviceNamedCommandProperty command){
        Button button=new Button(command.getDescription());
        button.setOnAction(event->command.activate());
        return button;
    }

    private Button createSetLinebutton(SoftLongProperty property,final long value){
        Button button=new Button(String.valueOf(value));
        button.setOnAction(event->property.setValue(value));
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
        Button button=new Button(I18N_monitor.COMMON.getStringEx("CHANGE"));
        button.setOnAction(event->property.invertValue());
        return button;
    }

    private Button createReReadButton(AbstractProperty command){
        Button button=new Button(I18N_monitor.COMMON.getStringEx("READ"));
        button.setOnAction(event->command.readValueRequest());
        return button;
    }

    private Button createReWriteButton(AbstractProperty command){
        Button button=new Button(I18N_monitor.COMMON.getStringEx("SAVE"));
        button.setOnAction(event->command.writeValueRequest());
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
        led.setPrefSize(100, 50);
        led.selectedProperty().bindBidirectional(new BooleanPropertyFXAdapter(property));
        return led;
    }

    public static Lcd  createLcdIndicator(SoftFloatProperty property){
        Lcd lcd = LcdBuilder.create()
                .prefWidth(170)
                .prefHeight(50)
                .styleClass(Lcd.STYLE_CLASS_STANDARD)
                .backgroundVisible(true)
                .foregroundShadowVisible(true)
                .crystalOverlayVisible(true)
                .title(property.getDescription())
                .titleVisible(true)
                .decimals(4)
                .valueFont(Lcd.LcdFont.LCD)
                .animated(true)
                .build();
        lcd.valueProperty().bind(new FloatPropertyFXAdapter(property));
        return lcd;
    }

    private Lcd  createLineIndicator(SoftLongProperty property){
        Lcd lcd = LcdBuilder.create()
                .prefWidth(130)
                .prefHeight(40)
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
        lcd.valueProperty().bind(new LongPropertyFXAdapter(property));
        return lcd;
    }

    static public Slider createSliderControl(SoftFloatProperty property,float min,float max,float blockincrement,int majorTick,int minorTick){
        Slider slider = new Slider();
        slider.setMin(min);
        slider.setMax(max);
        slider.valueProperty().bindBidirectional(new FloatPropertyFXAdapter(property));
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
                    case 1:return"BAUD RATE 9600";
                    case 2:return"BAUD RATE 19200";
                    case 3:return"BAUD RATE 57000";
                    case 4:return"BAUD RATE 115000";
                    default:return "BAUD RATE 9600";
                }
            }

            @Override
            public Short fromString(String value) {
                switch (value){
                    case "BAUD RATE 9600":return 1;
                    case "BAUD RATE 19200":return 2;
                    case "BAUD RATE 57000":return 3;
                    case "BAUD RATE 115000":return 4;
                    default:return 1;
                }
            }
        });
        box.valueProperty().bindBidirectional(new ShortPropertyFXAdapterSpec(addressProperty));
        return box;
    }
}

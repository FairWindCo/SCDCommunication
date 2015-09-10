package ua.pp.fairwind.javafx.panels.devices.belima;

import eu.hansolo.enzo.canvasled.Led;
import eu.hansolo.enzo.lcd.Lcd;
import eu.hansolo.enzo.lcd.LcdBuilder;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import ua.pp.fairwind.communications.devices.RSLineDevice;
import ua.pp.fairwind.communications.devices.hardwaredevices.Baumer.Encoder;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftBoolProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftFloatProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;
import ua.pp.fairwind.io.javafx.propertys.BooleanPropertyFXAdapter;
import ua.pp.fairwind.io.javafx.propertys.FloatPropertyFXAdapter;
import ua.pp.fairwind.io.javafx.propertys.special.ShortPropertyFXAdapterSpec;
import ua.pp.fairwind.javafx.I18N.I18N;
import ua.pp.fairwind.javafx.panels.devices.DeviceConfigPanel;
import ua.pp.fairwind.javafx.panels.dialogs.PropertyConfigDialog;

import static ua.pp.fairwind.javafx.panels.devices.DeviceConfigPanel.createAddressSelect;


/**
 * Created by Сергей on 27.08.2015.
 */
public class BelimaEncoderPanel extends HBox {
    final private Encoder device;
    final private TabPane tabs=new TabPane();

    public BelimaEncoderPanel(Encoder device) {
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
            rowIndex = setShortChanelControl(grid, device.getSteps(), I18N.getLocalizedString("STEPS"), rowIndex++, 0);
            rowIndex = setShortChanelControl(grid, device.getRevolution(), I18N.getLocalizedString("REVOLUTION"), rowIndex++, 0);
            initTab.setContent(grid);
        });
    }

    private int setShortChanelControl(GridPane grid,SoftShortProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(name), col++, rowindex);
        grid.add(createLcdIndicator(chanel), col++, rowindex);
        grid.add(createReReadButton(chanel), col++, rowindex);
        grid.add(createReWriteButton(chanel), col++, rowindex);
        grid.add(createConfigureProppearty(chanel), col++, rowindex++);
        return rowindex;
    }

    private int setFloatChanelControl(GridPane grid,SoftFloatProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(name), col++, rowindex);
        grid.add(createLcdIndicator(chanel), col++, rowindex);
        grid.add(createReReadButton(chanel), col++, rowindex);
        grid.add(createReWriteButton(chanel), col++, rowindex);
        grid.add(createConfigureProppearty(chanel), col++, rowindex++);
        return rowindex;
    }


    private Button createConfigureProppearty(AbstractProperty command){
        return PropertyConfigDialog.crateConfigButton(command);
    }


    private void initControl(){
        setAlignment(Pos.CENTER);
        intiDeviceControlPane();
        intiStatusPane();
        tabs.setPrefHeight(430);
        getChildren().add(tabs);
    }




    private Button createReReadButton(AbstractProperty command){
        Button button=new Button(I18N.getLocalizedString("READ"));
        button.setOnAction(event -> command.readValueRequest());
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


    public static Lcd  createLcdIndicator(SoftFloatProperty property){
        Lcd lcd = LcdBuilder.create()
                .prefWidth(170)
                .prefHeight(50)
                .styleClass(Lcd.STYLE_CLASS_BLUE_LIGHTBLUE2)
                .backgroundVisible(true)
                .foregroundShadowVisible(true)
                .crystalOverlayVisible(true)
                .title(property.getDescription())
                .titleVisible(true)
                .decimals(2)
                .valueFont(Lcd.LcdFont.LCD)
                .animated(true)
                .build();
        lcd.valueProperty().bind(new FloatPropertyFXAdapter(property));
        return lcd;
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

}

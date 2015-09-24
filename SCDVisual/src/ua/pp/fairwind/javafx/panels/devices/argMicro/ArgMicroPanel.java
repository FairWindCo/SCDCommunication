package ua.pp.fairwind.javafx.panels.devices.argMicro;


import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import ua.pp.fairwind.communications.devices.abstracts.RSLineDevice;
import ua.pp.fairwind.communications.devices.hardwaredevices.arg.micro.ArgMicroDevice;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftFloatProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;
import ua.pp.fairwind.javafx.I18N.I18N_FX;
import ua.pp.fairwind.javafx.VisualControls;
import ua.pp.fairwind.javafx.panels.TupicalPanels;
import ua.pp.fairwind.javafx.panels.devices.DeviceConfigPanel;
import ua.pp.fairwind.javafx.panels.dialogs.PropertyConfigDialog;



/**
 * Created by Сергей on 27.08.2015.
 */
public class ArgMicroPanel extends HBox {
    final private ArgMicroDevice device;
    final private TabPane tabs=new TabPane();

    public ArgMicroPanel(ArgMicroDevice device) {
        super();
        this.device = device;
        initControl();
    }

    private void  intiStatusPane(){
        final Tab initTab=new Tab(I18N_FX.getLocalizedString("STASUS"));
        tabs.getTabs().add(initTab);
        initTab.setClosable(false);
        initTab.setContent(TupicalPanels.createDeviceStatusPane(device));
    }

    private void intiDeviceControlPane(){
        final Tab initTab=new Tab(I18N_FX.getLocalizedString("DEVICE_VALUE"));
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
            /*rowIndex=setAOChanelControl(grid, device.getAnalogOutChanelN1(), I18N_FX.getLocalizedStringEx("AO1"), rowIndex++, 0);
            rowIndex=setAOChanelControl(grid, device.getAnalogOutChanelN2(), I18N_FX.getLocalizedStringEx("AO2"), rowIndex++, 0);
            rowIndex=setAOChanelControl(grid, device.getAnalogOutChanelN3(), I18N_FX.getLocalizedStringEx("AO3"), rowIndex++, 0);
            rowIndex=setAOChanelControl(grid, device.getAnalogOutChanelN4(), I18N_FX.getLocalizedStringEx("AO4"), rowIndex++, 0);
            grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getReadAllAO()), 0, rowIndex, 3, 1);
            grid.add(DeviceConfigPanel.createCommandExecuteButton(device.getWriteAllAO()), 2, rowIndex++,3,1);/**/
            rowIndex = TupicalPanels.setFloatChanelControl(grid, device.getRate(), I18N_FX.getLocalizedString("RATE"), rowIndex++, 0);
            rowIndex = TupicalPanels.setShortChanelControl(grid, device.getNumberMeasurementm(), I18N_FX.getLocalizedString("NUMBER_MEASUREMENT"), rowIndex++, 0);
            initTab.setContent(grid);
        });
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







}

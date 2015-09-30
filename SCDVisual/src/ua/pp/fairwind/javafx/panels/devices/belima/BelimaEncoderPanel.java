package ua.pp.fairwind.javafx.panels.devices.belima;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import ua.pp.fairwind.communications.devices.hardwaredevices.Baumer.Encoder;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftFloatProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;
import ua.pp.fairwind.javafx.I18N.I18N_FX;
import ua.pp.fairwind.javafx.VisualControls;
import ua.pp.fairwind.javafx.panels.TupicalPanels;
import ua.pp.fairwind.javafx.panels.dialogs.PropertyConfigDialog;



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
            rowIndex = setShortChanelControlRO(grid, device.getSteps(), I18N_FX.getLocalizedString("STEPS"), rowIndex++, 0);
            rowIndex = setShortChanelControlRO(grid, device.getRevolution(), I18N_FX.getLocalizedString("REVOLUTION"), rowIndex++, 0);
            initTab.setContent(grid);
        });
    }

    private int setShortChanelControl(GridPane grid,SoftShortProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(name), col++, rowindex);
        grid.add(VisualControls.createLcdIndicator(chanel), col++, rowindex);
        grid.add(VisualControls.createReReadButton(chanel), col++, rowindex);
        grid.add(VisualControls.createReWriteButton(chanel), col++, rowindex);
        grid.add(createConfigureProppearty(chanel), col++, rowindex++);
        return rowindex;
    }

    private int setShortChanelControlRO(GridPane grid,SoftShortProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(name), col++, rowindex);
        grid.add(VisualControls.createLcdIndicator(chanel), col++, rowindex);
        grid.add(VisualControls.createReReadButton(chanel), col++, rowindex);
        grid.add(createConfigureProppearty(chanel), col++, rowindex++);
        return rowindex;
    }

    private int setFloatChanelControl(GridPane grid,SoftFloatProperty chanel,String name,int rowindex,int col){
        grid.add(new Label(name), col++, rowindex);
        grid.add(VisualControls.createLcdIndicator(chanel), col++, rowindex);
        grid.add(VisualControls.createReReadButton(chanel), col++, rowindex);
        grid.add(VisualControls.createReWriteButton(chanel), col++, rowindex);
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









}

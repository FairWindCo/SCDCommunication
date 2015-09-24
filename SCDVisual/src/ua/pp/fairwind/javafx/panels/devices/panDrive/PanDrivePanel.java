package ua.pp.fairwind.javafx.panels.devices.panDrive;

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
import ua.pp.fairwind.communications.devices.abstracts.RSLineDevice;
import ua.pp.fairwind.communications.devices.hardwaredevices.panDrive.StepDriver;
import ua.pp.fairwind.communications.propertyes.software.SoftBoolProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;
import ua.pp.fairwind.io.javafx.propertys.LongPropertyFXAdapter;
import ua.pp.fairwind.io.javafx.propertys.ShortPropertyFXAdapter;
import ua.pp.fairwind.io.javafx.propertys.special.LongPropertyFXAdapterSpec;
import ua.pp.fairwind.io.javafx.propertys.special.ShortPropertyFXAdapterSpec;
import ua.pp.fairwind.javafx.I18N.I18N_FX;
import ua.pp.fairwind.javafx.VisualControls;
import ua.pp.fairwind.javafx.guiElements.editors.IntegerInputText;
import ua.pp.fairwind.javafx.panels.TupicalPanels;
import ua.pp.fairwind.javafx.panels.devices.DeviceConfigPanel;


/**
 * Created by Сергей on 27.08.2015.
 */
public class PanDrivePanel extends HBox {
    final private StepDriver device;
    final private TabPane tabs = new TabPane();

    public PanDrivePanel(StepDriver device) {
        super();
        this.device = device;
        initControl();
    }

    private void intiStatusPane() {
        final Tab initTab = new Tab(I18N_FX.getLocalizedString("STASUS"));
        tabs.getTabs().add(initTab);
        initTab.setClosable(false);
        initTab.setContent(TupicalPanels.createDeviceStatusPane(device));
    }


    private void intiDeviceControlPane() {
        final Tab initTab = new Tab(I18N_FX.getLocalizedString("DEVICE_VALUE"));
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
            rowIndex = setLongSpeedChanelControl(grid, device.getSpeed(), I18N_FX.getLocalizedString("SPEED"), rowIndex++, 0);
            rowIndex = setLongChanelControl(grid, device.getPosition(), I18N_FX.getLocalizedString("POSITION"), rowIndex++, 0);
            rowIndex = setLongChanelControl(grid, device.getStep(), I18N_FX.getLocalizedString("STEP"), rowIndex++, 0);
            grid.add(VisualControls.createCommandExecuteButton(device.getRotateLeft()), 0, rowIndex);
            grid.add(VisualControls.createCommandExecuteButton(device.getMotorStop()), 1, rowIndex);
            grid.add(VisualControls.createCommandExecuteButton(device.getRotateRight()), 2, rowIndex++);
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
            grid.add(VisualControls.createAddressSelect(device.getRsadress()), 1, rowindex);
            grid.add(VisualControls.createReReadButton(device.getRsadress()), 2, rowindex);
            grid.add(VisualControls.createReWriteButton(device.getRsadress()), 3, rowindex++);

            grid.add(new Label(I18N_FX.getLocalizedString("DEVICE_SPEED")), 0, rowindex);
            grid.add(VisualControls.createPANDRIVESpeedSelect(device.getRsspeed()), 1, rowindex);
            grid.add(VisualControls.createReReadButton(device.getRsspeed()), 2, rowindex);
            grid.add(VisualControls.createReWriteButton(device.getRsspeed()), 3, rowindex++);

            grid.add(new Label(I18N_FX.getLocalizedString("RS_HOSTADDRESS")), 0, rowindex);
            grid.add(VisualControls.createAddressSelect(device.getSerialHostAdress()), 1, rowindex);
            grid.add(VisualControls.createReReadButton(device.getSerialHostAdress()), 2, rowindex);
            grid.add(VisualControls.createReWriteButton(device.getSerialHostAdress()), 3, rowindex++);

            grid.add(new Label(I18N_FX.getLocalizedString("RS_SECOND_ADDRESS")), 0, rowindex);
            grid.add(VisualControls.createAddressSelect(device.getSerialSecondAdress()), 1, rowindex);
            grid.add(VisualControls.createReReadButton(device.getSerialSecondAdress()), 2, rowindex);
            grid.add(VisualControls.createReWriteButton(device.getSerialSecondAdress()), 3, rowindex++);


            setDOChanelControl(grid, device.getAutoStartTMCL(), I18N_FX.getLocalizedString("AutoStartTMCL"), rowindex++, 0);
            setDOChanelControl(grid, device.getConfigurationEpromLock(), I18N_FX.getLocalizedString("ConfigurationEpromLock"), rowindex++, 0);
            setDOChanelControl(grid, device.getEndSwichPolarity(), I18N_FX.getLocalizedString("EndSwichPolarity"), rowindex++, 0);
            setDIChanelControl(grid, device.getDowloadMode(), I18N_FX.getLocalizedString("DowloadMode"), rowindex++, 0);
            rowindex = setLongChanelControl(grid, device.getTickTimer(), I18N_FX.getLocalizedString("Ticktimer"), rowindex, 0);
            rowindex = setLongChanelControl(grid, device.getRandomNumber(), I18N_FX.getLocalizedString("RANDOM"), rowindex, 0);
            initTab.setContent(grid);
        });
    }


    private void setDIChanelControl(GridPane grid, SoftBoolProperty chanel, String name, int rowindex, int col) {
        grid.add(new Label(name), col + 0, rowindex);
        grid.add(VisualControls.createLedIndicator(chanel, Color.CORAL), col + 1, rowindex);
        grid.add(VisualControls.createReReadButton(chanel), col + 2, rowindex);
        grid.add(VisualControls.createConfigureProppearty(chanel), col + 3, rowindex);
    }

    private void setDOChanelControl(GridPane grid, SoftBoolProperty chanel, String name, int rowindex, int col) {
        grid.add(new Label(name), col++, rowindex);
        grid.add(VisualControls.createLedIndicator(chanel, Color.AQUA), col++, rowindex);
        grid.add(VisualControls.createSlideIndicator(chanel), col++, rowindex);
        grid.add(VisualControls.createReReadButton(chanel), col++, rowindex);
        grid.add(VisualControls.createReWriteButton(chanel), col++, rowindex);
    }

    private int setLongChanelControl(GridPane grid, SoftLongProperty chanel, String name, int rowindex, int col) {
        grid.add(new Label(name), col++, rowindex);
        grid.add(VisualControls.createLcdIndicator(chanel), col++, rowindex);

        //grid.add(createBoolChangeCommandButton(chanel), col++, rowindex);
        grid.add(VisualControls.createIntegerEditControl(chanel, Integer.MIN_VALUE, Integer.MAX_VALUE), col++, rowindex);
        grid.add(VisualControls.createReReadButton(chanel), col++, rowindex);
        grid.add(VisualControls.createReWriteButton(chanel), col++, rowindex);
        grid.add(VisualControls.createConfigureProppearty(chanel), col++, rowindex++);
        //grid.add(createSliderControl(chanel, 0, Integer.MAX_VALUE, Integer.MAX_VALUE / 1000, 10000000, 10), 0, rowindex++,5,1);
        return rowindex;
    }

    private int setLongSpeedChanelControl(GridPane grid, SoftShortProperty chanel, String name, int rowindex, int col) {
        grid.add(new Label(name), col++, rowindex);
        grid.add(VisualControls.createLcdIndicator(chanel), col++, rowindex);
        grid.add(VisualControls.createIntegerEditControl(chanel, 0, 2047), col++, rowindex);
        //grid.add(createBoolChangeCommandButton(chanel), col++, rowindex);
        grid.add(VisualControls.createReReadButton(chanel), col++, rowindex);
        grid.add(VisualControls.createReWriteButton(chanel), col++, rowindex);
        grid.add(VisualControls.createConfigureProppearty(chanel), col++, rowindex++);
        //grid.add(createSliderControl(chanel, 0, 2047, 100, 100, 10), 0, rowindex++,5,1);
        return rowindex;
    }

    private int setShortChanelControl(GridPane grid, SoftShortProperty chanel, String name, int rowindex, int col) {
        grid.add(new Label(name), col++, rowindex);
        grid.add(VisualControls.createLcdIndicator(chanel), col++, rowindex);

        //grid.add(createBoolChangeCommandButton(chanel), col++, rowindex);
        grid.add(VisualControls.createReReadButton(chanel), col++, rowindex);
        grid.add(VisualControls.createReWriteButton(chanel), col++, rowindex);
        grid.add(VisualControls.createConfigureProppearty(chanel), col++, rowindex++);
        grid.add(VisualControls.createSliderControl(chanel, 0, 10, 1, 10, 10), 0, rowindex++, 5, 1);
        return rowindex;
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



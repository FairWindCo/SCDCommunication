package ua.pp.fairwind.javafx.panels.devices;

import eu.hansolo.enzo.canvasled.Led;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import ua.pp.fairwind.communications.devices.abstracts.RSLineDevice;
import ua.pp.fairwind.communications.propertyes.DeviceNamedCommandProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftBoolProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;
import ua.pp.fairwind.io.javafx.propertys.BooleanPropertyFXAdapter;
import ua.pp.fairwind.io.javafx.propertys.special.LongPropertyFXAdapterSpec;
import ua.pp.fairwind.io.javafx.propertys.special.ShortPropertyFXAdapterSpec;
import ua.pp.fairwind.javafx.I18N.I18N_FX;
import ua.pp.fairwind.javafx.VisualControls;

/**
 * Created by Сергей on 27.08.2015.
 */
public class DeviceConfigPanel  extends HBox {
    final private RSLineDevice device;

    public DeviceConfigPanel(RSLineDevice device) {
        super();
        this.device = device;
        initControl();
    }

    private void initControl(){
        GridPane grid = new GridPane();
        getChildren().add(grid);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        int rowindex=0;
        grid.add(new Label(device.getDeviceType() + " : " + device.getName() + " UUID=" + device.getUUID()), 0, rowindex++, 6, 1);
        grid.add(new Label(device.getDescription()),0,rowindex++,4,1);
        grid.add(new Label(I18N_FX.getLocalizedString("DEVICE_ADDRES")), 0, rowindex);
        grid.add(VisualControls.createAddressSelect(device.getDeviceAddressProperty()), 1, rowindex);
        grid.add(createConfigureButton(), 5, rowindex++,2,1);
        grid.add(new Label(I18N_FX.getLocalizedString("DEVICE_STATUS")), 0, rowindex);
        grid.add(new Label(I18N_FX.getLocalizedString("DEVICE_LINE1_STATUS")), 1, rowindex);
        grid.add(new Label(I18N_FX.getLocalizedString("DEVICE_LINE2_STATUS")), 2, rowindex);
        grid.add(new Label(I18N_FX.getLocalizedString("DEVICE_ERROR")), 3, rowindex);
        grid.add(new Label(I18N_FX.getLocalizedString("DEVICE_LINE1_ERROR")), 4, rowindex);
        grid.add(new Label(I18N_FX.getLocalizedString("DEVICE_LINE2_ERROR")), 5, rowindex++);

        grid.add(VisualControls.createLedIndicator(device.getLastCommunicationStatus()), 0, rowindex);
        grid.add(VisualControls.createLedIndicator(device.getLastCommunicationStatusLine1()), 1, rowindex);
        grid.add(VisualControls.createLedIndicator(device.getLastCommunicationStatusLine2()), 2, rowindex);
        grid.add(VisualControls.createLedIndicator(device.getErrorCommunicationStatus()), 3, rowindex);
        grid.add(VisualControls.createLedIndicator(device.getErrorCommunicationStatusLine1()), 4, rowindex);
        grid.add(VisualControls.createLedIndicator(device.getErrorCommunicationStatusLine2()), 5, rowindex++);
        grid.add(new Label(I18N_FX.getLocalizedString("LAST_COMMUNICATE_TIME")), 0, rowindex);
        grid.add(VisualControls.createCommandExecuteButton(device.getValidateErrorCommand()), 3, rowindex);
        grid.add(VisualControls.createCommandExecuteButton(device.getValidateErrorCommandLine1()), 4, rowindex);
        grid.add(VisualControls.createCommandExecuteButton(device.getValidateErrorCommandLine2()), 5, rowindex);
    }

    private Button createConfigureButton(){
        return new Button(I18N_FX.getLocalizedString("CONFIG_DEVICE_DIALOG"));
    }




}

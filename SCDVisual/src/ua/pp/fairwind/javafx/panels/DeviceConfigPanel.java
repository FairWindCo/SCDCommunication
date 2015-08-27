package ua.pp.fairwind.javafx.panels;

import eu.hansolo.enzo.canvasled.Led;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import ua.pp.fairwind.communications.devices.AbstractDevice;
import ua.pp.fairwind.communications.propertyes.DeviceNamedCommandProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftBoolProperty;
import ua.pp.fairwind.io.javafx.propertys.BooleanPropertyFXAdapter;
import ua.pp.fairwind.javafx.I18N.I18N_monitor;

/**
 * Created by Сергей on 27.08.2015.
 */
public class DeviceConfigPanel  extends HBox {
    final private AbstractDevice device;

    public DeviceConfigPanel(AbstractDevice device) {
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
        grid.add(new Label(I18N_monitor.COMMON.getString("DEVICE_ADDRES")), 0, rowindex);
        grid.add(createConfigureButton(), 5, rowindex++,2,1);
        grid.add(new Label(I18N_monitor.COMMON.getString("DEVICE_STATUS")), 0, rowindex);
        grid.add(new Label(I18N_monitor.COMMON.getString("DEVICE_LINE1_STATUS")), 1, rowindex);
        grid.add(new Label(I18N_monitor.COMMON.getString("DEVICE_LINE2_STATUS")), 2, rowindex);
        grid.add(new Label(I18N_monitor.COMMON.getString("DEVICE_ERROR")), 3, rowindex);
        grid.add(new Label(I18N_monitor.COMMON.getString("DEVICE_LINE1_ERROR")), 4, rowindex);
        grid.add(new Label(I18N_monitor.COMMON.getString("DEVICE_LINE2_ERROR")), 5, rowindex++);

        grid.add(createLedIndicator(device.getLastCommunicationStatus()), 0, rowindex);
        grid.add(createLedIndicator(device.getLastCommunicationStatusLine1()), 1, rowindex);
        grid.add(createLedIndicator(device.getLastCommunicationStatusLine2()), 2, rowindex);
        grid.add(createLedIndicator(device.getErrorCommunicationStatus()), 3, rowindex);
        grid.add(createLedIndicator(device.getErrorCommunicationStatusLine1()), 4, rowindex);
        grid.add(createLedIndicator(device.getErrorCommunicationStatusLine2()), 5, rowindex++);
        grid.add(new Label(I18N_monitor.COMMON.getString("LAST_COMMUNICATE_TIME")), 0, rowindex);
        grid.add(createCommandExecuteButton(device.getValidateErrorCommand()), 3, rowindex);
        grid.add(createCommandExecuteButton(device.getValidateErrorCommandLine1()), 4, rowindex);
        grid.add(createCommandExecuteButton(device.getValidateErrorCommandLine2()), 5, rowindex);
    }

    private Button createConfigureButton(){
        return new Button(I18N_monitor.COMMON.getString("CONFIG_DEVICE_DIALOG"));
    }

    private Button createCommandExecuteButton(DeviceNamedCommandProperty command){
        Button button=new Button(command.getDescription());
        button.setOnAction(event->command.activate());
        return button;
    }

    private Led createLedIndicator(SoftBoolProperty property){
        Led led = new Led();
        led.onProperty().bind(new BooleanPropertyFXAdapter(property));
        return led;
    }
}

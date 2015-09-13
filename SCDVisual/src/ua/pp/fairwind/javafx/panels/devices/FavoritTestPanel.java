package ua.pp.fairwind.javafx.panels.devices;

import eu.hansolo.enzo.canvasled.Led;
import eu.hansolo.enzo.lcd.Lcd;
import eu.hansolo.enzo.lcd.LcdBuilder;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import ua.pp.fairwind.communications.devices.hardwaredevices.favorit.FavoritCoreDeviceV1;
import ua.pp.fairwind.communications.propertyes.DeviceNamedCommandProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftBoolProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftFloatProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;
import ua.pp.fairwind.io.javafx.propertys.BooleanPropertyFXAdapter;
import ua.pp.fairwind.io.javafx.propertys.FloatPropertyFXAdapter;
import ua.pp.fairwind.io.javafx.propertys.LongPropertyFXAdapter;
import ua.pp.fairwind.javafx.I18N.I18N;
import ua.pp.fairwind.javafx.panels.dialogs.PropertyConfigDialog;

/**
 * Created by Сергей on 27.08.2015.
 */
public class FavoritTestPanel extends HBox {
    final private FavoritCoreDeviceV1 device;

    public FavoritTestPanel(FavoritCoreDeviceV1 device) {
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
        grid.add(new Label(I18N.getLocalizedString("DIGITAL IN")), 0, rowindex, 4, 1);
        grid.add(new Label(I18N.getLocalizedString("DIGITAL OUT")), 5, rowindex++, 5, 1);
        grid.add(new Label(I18N.getLocalizedString("DI1")), 0, rowindex);
        grid.add(createLedIndicator(device.getDigitalInChanelN1()), 1, rowindex);
        grid.add(createReReadButton(device.getDigitalInChanelN1()), 2, rowindex);
        grid.add(createConfigureProppearty(device.getDigitalInChanelN1()), 3, rowindex);

        grid.add(createConfigureProppearty(device.getDigitalOutChanelN1()), 5, rowindex);
        grid.add(createReReadButton(device.getDigitalOutChanelN1()), 6, rowindex);
        grid.add(createReWriteButton(device.getDigitalOutChanelN1()), 7, rowindex);
        grid.add(createBoolChangeCommandButton(device.getDigitalOutChanelN1()), 8, rowindex);
        grid.add(createLedIndicator(device.getDigitalOutChanelN1()), 9, rowindex);
        grid.add(new Label(I18N.getLocalizedString("DO1")), 10, rowindex++);

        grid.add(new Label(I18N.getLocalizedString("DI2")), 0, rowindex);
        grid.add(createLedIndicator(device.getDigitalInChanelN2()), 1, rowindex);
        grid.add(createReReadButton(device.getDigitalInChanelN2()), 2, rowindex);
        grid.add(createConfigureProppearty(device.getDigitalInChanelN2()), 3, rowindex);

        grid.add(createConfigureProppearty(device.getDigitalOutChanelN2()), 5, rowindex);
        grid.add(createReReadButton(device.getDigitalOutChanelN2()), 6, rowindex);
        grid.add(createReWriteButton(device.getDigitalOutChanelN2()), 7, rowindex);
        grid.add(createBoolChangeCommandButton(device.getDigitalOutChanelN2()), 8, rowindex);
        grid.add(createLedIndicator(device.getDigitalOutChanelN2()), 9, rowindex);
        grid.add(new Label(I18N.getLocalizedString("DO2")), 10, rowindex++);

        grid.add(new Label(I18N.getLocalizedString("DI3")), 0, rowindex);
        grid.add(createLedIndicator(device.getDigitalInChanelN3()), 1, rowindex);
        grid.add(createReReadButton(device.getDigitalInChanelN3()), 2, rowindex);
        grid.add(createConfigureProppearty(device.getDigitalInChanelN3()), 3, rowindex);

        grid.add(createConfigureProppearty(device.getDigitalOutChanelN3()), 5, rowindex);
        grid.add(createReReadButton(device.getDigitalOutChanelN3()), 6, rowindex);
        grid.add(createReWriteButton(device.getDigitalOutChanelN3()), 7, rowindex);
        grid.add(createBoolChangeCommandButton(device.getDigitalOutChanelN3()), 8, rowindex);
        grid.add(createLedIndicator(device.getDigitalOutChanelN3()), 9, rowindex);
        grid.add(new Label(I18N.getLocalizedString("DO3")), 10, rowindex++);

        grid.add(new Label(I18N.getLocalizedString("DI4")), 0, rowindex);
        grid.add(createLedIndicator(device.getDigitalInChanelN4()), 1, rowindex);
        grid.add(createReReadButton(device.getDigitalInChanelN4()), 2, rowindex);
        grid.add(createConfigureProppearty(device.getDigitalInChanelN4()), 3, rowindex);

        grid.add(createConfigureProppearty(device.getDigitalOutChanelN4()), 5, rowindex);
        grid.add(createReReadButton(device.getDigitalOutChanelN4()), 6, rowindex);
        grid.add(createReWriteButton(device.getDigitalOutChanelN4()), 7, rowindex);
        grid.add(createBoolChangeCommandButton(device.getDigitalOutChanelN4()), 8, rowindex);
        grid.add(createLedIndicator(device.getDigitalOutChanelN4()), 9, rowindex);
        grid.add(new Label(I18N.getLocalizedString("DO4")), 10, rowindex++);

        grid.add(new Label(I18N.getLocalizedString("DI5")), 0, rowindex);
        grid.add(createLedIndicator(device.getDigitalInChanelN5()), 1, rowindex);
        grid.add(createReReadButton(device.getDigitalInChanelN5()), 2, rowindex);
        grid.add(createConfigureProppearty(device.getDigitalInChanelN5()), 3, rowindex);

        grid.add(createConfigureProppearty(device.getDigitalOutChanelN5()), 5, rowindex);
        grid.add(createReReadButton(device.getDigitalOutChanelN5()), 6, rowindex);
        grid.add(createReWriteButton(device.getDigitalOutChanelN5()), 7, rowindex);
        grid.add(createBoolChangeCommandButton(device.getDigitalOutChanelN5()), 8, rowindex);
        grid.add(createLedIndicator(device.getDigitalOutChanelN5()), 9, rowindex);
        grid.add(new Label(I18N.getLocalizedString("DO5")), 10, rowindex++);

        grid.add(new Label(I18N.getLocalizedString("DI6")), 0, rowindex);
        grid.add(createLedIndicator(device.getDigitalInChanelN6()), 1, rowindex);
        grid.add(createReReadButton(device.getDigitalInChanelN6()), 2, rowindex);
        grid.add(createConfigureProppearty(device.getDigitalInChanelN6()), 3, rowindex);

        grid.add(createConfigureProppearty(device.getDigitalOutChanelN6()), 5, rowindex);
        grid.add(createReReadButton(device.getDigitalOutChanelN6()), 6, rowindex);
        grid.add(createReWriteButton(device.getDigitalOutChanelN6()), 7, rowindex);
        grid.add(createBoolChangeCommandButton(device.getDigitalOutChanelN6()), 8, rowindex);
        grid.add(createLedIndicator(device.getDigitalOutChanelN6()), 9, rowindex);
        grid.add(new Label(I18N.getLocalizedString("DO6")), 10, rowindex++);

        grid.add(new Label(I18N.getLocalizedString("ANALOG IN")), 0, rowindex,4,1);
        grid.add(new Label(I18N.getLocalizedString("ANALOG OUT")), 5, rowindex++,5,1);

        grid.add(new Label(I18N.getLocalizedString("AI1")), 0, rowindex);
        grid.add(createLcdIndicator(device.getAnalogInChanelN1()), 1, rowindex);
        grid.add(createReReadButton(device.getAnalogInChanelN1()), 2, rowindex);
        grid.add(createConfigureProppearty(device.getAnalogInChanelN1()), 3, rowindex);

        grid.add(createConfigureProppearty(device.getAnalogOutChanelN1()), 5, rowindex);
        grid.add(createReReadButton(device.getAnalogOutChanelN1()), 6, rowindex);
        grid.add(createReWriteButton(device.getAnalogOutChanelN1()), 7, rowindex);
        grid.add(createLcdIndicator(device.getAnalogOutChanelN1()), 9, rowindex);
        grid.add(new Label(I18N.getLocalizedString("AO1")), 10, rowindex++);


        grid.add(new Label(I18N.getLocalizedString("AI2")), 0, rowindex);
        grid.add(createLcdIndicator(device.getAnalogInChanelN2()), 1, rowindex);
        grid.add(createReReadButton(device.getAnalogInChanelN2()), 2, rowindex);
        grid.add(createConfigureProppearty(device.getAnalogInChanelN2()), 3, rowindex);

        grid.add(createConfigureProppearty(device.getAnalogOutChanelN2()), 5, rowindex);
        grid.add(createReReadButton(device.getAnalogOutChanelN2()), 6, rowindex);
        grid.add(createReWriteButton(device.getAnalogOutChanelN2()), 7, rowindex);
        grid.add(createLcdIndicator(device.getAnalogOutChanelN2()), 9, rowindex);
        grid.add(new Label(I18N.getLocalizedString("AO2")), 10, rowindex++);

        grid.add(new Label(I18N.getLocalizedString("AI3")), 0, rowindex);
        grid.add(createLcdIndicator(device.getAnalogInChanelN3()), 1, rowindex);
        grid.add(createReReadButton(device.getAnalogInChanelN3()), 2, rowindex);
        grid.add(createConfigureProppearty(device.getAnalogInChanelN3()), 3, rowindex);

        grid.add(createConfigureProppearty(device.getAnalogOutChanelN3()), 5, rowindex);
        grid.add(createReReadButton(device.getAnalogOutChanelN3()), 6, rowindex);
        grid.add(createReWriteButton(device.getAnalogOutChanelN3()), 7, rowindex);
        grid.add(createLcdIndicator(device.getAnalogOutChanelN3()), 9, rowindex);
        grid.add(new Label(I18N.getLocalizedString("AO3")), 10, rowindex++);

        grid.add(new Label(I18N.getLocalizedString("AI4")), 0, rowindex);
        grid.add(createLcdIndicator(device.getAnalogInChanelN4()), 1, rowindex);
        grid.add(createReReadButton(device.getAnalogInChanelN4()), 2, rowindex);
        grid.add(createConfigureProppearty(device.getAnalogInChanelN4()), 3, rowindex);

        grid.add(createConfigureProppearty(device.getAnalogOutChanelN4()), 5, rowindex);
        grid.add(createReReadButton(device.getAnalogOutChanelN4()), 6, rowindex);
        grid.add(createReWriteButton(device.getAnalogOutChanelN4()), 7, rowindex);
        grid.add(createLcdIndicator(device.getAnalogOutChanelN4()), 9, rowindex);
        grid.add(new Label(I18N.getLocalizedString("AO4")), 10, rowindex++);

        grid.add(new Label(I18N.getLocalizedString("LINE SELECT")), 0, rowindex++,10,1);
        grid.add(new Label(I18N.getLocalizedString("SELECTED_LINE:")), 0, rowindex);
        grid.add(createLineIndicator(device.getLineSelect()), 1, rowindex);
        grid.add(createConfigureProppearty(device.getLineSelect()), 2, rowindex);
        grid.add(createReReadButton(device.getLineSelect()), 3, rowindex);
        grid.add(createReWriteButton(device.getLineSelect()), 4, rowindex);
        grid.add(createSetLinebutton(device.getLineSelect(), 0), 6, rowindex);
        grid.add(createSetLinebutton(device.getLineSelect(), 1), 7, rowindex);
        grid.add(createSetLinebutton(device.getLineSelect(),2), 8, rowindex);
        grid.add(createSetLinebutton(device.getLineSelect(),3), 9, rowindex);
        grid.add(createSetLinebutton(device.getLineSelect(),4), 10, rowindex++);

        grid.add(new Label(I18N.getLocalizedString("DEVICE CONFIG")), 0, rowindex++,10,1);
        grid.add(new Label(I18N.getLocalizedString("DEVICE ADDRESS")), 0, rowindex);
        grid.add(createReReadButton(device.getConfigdeviceAddress()), 3, rowindex);
        grid.add(createReWriteButton(device.getConfigdeviceAddress()), 4, rowindex);

        grid.add(new Label(I18N.getLocalizedString("DEVICE SPEED")), 6, rowindex);
        grid.add(createReReadButton(device.getConfigdeviceSpeed()), 9, rowindex);
        grid.add(createReWriteButton(device.getConfigdeviceSpeed()), 10, rowindex);
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
        button.setOnAction(event->command.writeValueRequest());
        return button;
    }

    private Led createLedIndicator(SoftBoolProperty property){
        Led led = new Led();
        led.onProperty().bind(new BooleanPropertyFXAdapter(property));
        return led;
    }

    private Lcd  createLcdIndicator(SoftFloatProperty property){
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
}

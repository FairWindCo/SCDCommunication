package ua.pp.fairwind.javafx;

import eu.hansolo.enzo.canvasled.Led;
import eu.hansolo.enzo.lcd.Lcd;
import eu.hansolo.enzo.lcd.LcdBuilder;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import ua.pp.fairwind.communications.devices.abstracts.AbstractDevice;
import ua.pp.fairwind.communications.devices.abstracts.SerialDeviceInterface;
import ua.pp.fairwind.communications.propertyes.DeviceNamedCommandProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.software.*;
import ua.pp.fairwind.io.javafx.propertys.*;
import ua.pp.fairwind.io.javafx.propertys.special.LongPropertyFXAdapterSpec;
import ua.pp.fairwind.io.javafx.propertys.special.ShortPropertyFXAdapterSpec;
import ua.pp.fairwind.javafx.I18N.I18N_FX;
import ua.pp.fairwind.javafx.controls.slidecheckbox.SlideCheckBox;
import ua.pp.fairwind.javafx.guiElements.editors.IntegerInputText;
import ua.pp.fairwind.javafx.panels.dialogs.LineParametersDialog;
import ua.pp.fairwind.javafx.panels.dialogs.PropertyConfigDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Сергей on 24.09.2015.
 */
public class VisualControls {
    static private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.S");
    public static ComboBox<Long> createAddressSelect(SoftLongProperty addressProperty){
        ComboBox<Long> box=new ComboBox<>();
        for(int i=0;i<256;i++)box.getItems().add((long)i);
        box.valueProperty().bindBidirectional(new LongPropertyFXAdapterSpec(addressProperty));
        Tooltip.install(box, new Tooltip(addressProperty.getDescription()));
        return box;
    }

    public static ComboBox<Short> createAddressSelect(SoftShortProperty addressProperty){
        ComboBox<Short> box=new ComboBox<>();
        for(int i=0;i<256;i++)box.getItems().add((short)i);
        box.valueProperty().bindBidirectional(new ShortPropertyFXAdapterSpec(addressProperty));
        Tooltip.install(box, new Tooltip(addressProperty.getDescription()));
        return box;
    }

    public static Led createLedIndicator(SoftBoolProperty property){
        Led led = new Led();
        led.onProperty().bind(new BooleanPropertyFXAdapter(property));
        Tooltip.install(led, new Tooltip(property.getDescription()));
        return led;
    }

    public static Button createConfigureButton(SerialDeviceInterface device){
        Button button=new Button(I18N_FX.getLocalizedString("CONFIG_DEVICE_DIALOG"));
        button.setOnAction(action -> LineParametersDialog.getSerialLineParameterDialog(device));
        Tooltip.install(button, new Tooltip(I18N_FX.getLocalizedString("CONFIG_DEVICE_DIALOG.description")));
        return button;
    }

    public static Button createCommandExecuteButton(DeviceNamedCommandProperty command){
        Button button=new Button(command.getName());
        button.setOnAction(event -> command.activate());
        Tooltip.install(button, new Tooltip(command.getDescription()));
        return button;
    }

    public static Led createLedIndicator(SoftBoolProperty property,Color color){
        Led led = new Led();
        led.setLedColor(color);
        led.onProperty().bind(new BooleanPropertyFXAdapter(property));
        Tooltip.install(led, new Tooltip(property.getDescription()));
        return led;
    }


    public static Label createTimeLabel(SoftLongProperty property){
        Label lbl=new Label();
        property.addChangeEventListener(event -> {
            Object newVal = event.getNewValue();
            String txt;
            if (newVal != null) {
                long datetime = (Long) newVal;
                Date time = new Date(datetime);
                txt = dateFormat.format(time);
            } else {
                txt = "";
            }
            executeInJavaFXThread(() -> lbl.setText(txt));

        });
        Tooltip.install(lbl, new Tooltip(property.getDescription()));
        return lbl;
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

    public static Button createReReadButton(AbstractProperty command){
        Button button=new Button(I18N_FX.getLocalizedString("READ.name"));
        button.setOnAction(event -> command.readValueRequest());
        Tooltip.install(button, new Tooltip(I18N_FX.getLocalizedString("READ.description")));
        return button;
    }

    public static Button createReWriteButton(AbstractProperty command){
        Button button=new Button(I18N_FX.getLocalizedString("SAVE.name"));
        Tooltip.install(button, new Tooltip(I18N_FX.getLocalizedString("SAVE.description")));
        button.setOnAction(event -> command.writeValueRequest());
        return button;
    }


    public static Lcd createLcdIndicator(SoftShortProperty property){
        Lcd lcd = LcdBuilder.create()
                .prefWidth(170)
                .prefHeight(50)
                .styleClass(Lcd.STYLE_CLASS_STANDARD)
                .backgroundVisible(true)
                .foregroundShadowVisible(true)
                .crystalOverlayVisible(true)
                .title(property.getName())
                .titleVisible(true)
                .decimals(0)
                .valueFont(Lcd.LcdFont.LCD)
                .animated(true)
                .build();
        lcd.valueProperty().bind(new ShortPropertyFXAdapterSpec(property));
        Tooltip.install(lcd, new Tooltip(property.getDescription()));
        return lcd;
    }

    public static Lcd createLcdIndicator(SoftIntegerProperty property){
        Lcd lcd = LcdBuilder.create()
                .prefWidth(170)
                .prefHeight(50)
                .styleClass(Lcd.STYLE_CLASS_STANDARD)
                .backgroundVisible(true)
                .foregroundShadowVisible(true)
                .crystalOverlayVisible(true)
                .title(property.getName())
                .titleVisible(true)
                .decimals(0)
                .valueFont(Lcd.LcdFont.LCD)
                .animated(true)
                .build();
        lcd.valueProperty().bind(new IntegerPropertyFXAdapter(property));
        Tooltip.install(lcd, new Tooltip(property.getDescription()));
        return lcd;
    }

    public static Lcd  createLcdIndicatorBLUE(SoftFloatProperty property){
        Lcd lcd = LcdBuilder.create()
                .prefWidth(170)
                .prefHeight(50)
                .styleClass(Lcd.STYLE_CLASS_BLUE_LIGHTBLUE2)
                .backgroundVisible(true)
                .foregroundShadowVisible(true)
                .crystalOverlayVisible(true)
                .title(property.getName())
                .titleVisible(true)
                .decimals(2)
                .valueFont(Lcd.LcdFont.LCD)
                .animated(true)
                .build();
        lcd.valueProperty().bind(new FloatPropertyFXAdapter(property));
        Tooltip.install(lcd, new Tooltip(property.getDescription()));
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
                .title(property.getName())
                .titleVisible(true)
                .decimals(2)
                .valueFont(Lcd.LcdFont.LCD)
                .animated(true)
                .build();
        lcd.setTooltip(new Tooltip(property.getDescription()));
        lcd.valueProperty().bind(new FloatPropertyFXAdapter(property));
        return lcd;
    }


    public static ComboBox<Short> createAKONProtoclSelect(SoftShortProperty addressProperty){
        ComboBox<Short> box=new ComboBox<>();
        for(int i=0;i<2;i++)box.getItems().add((short)i);
        box.setConverter(new StringConverter<Short>() {
            @Override
            public String toString(Short value) {
                switch (value){
                    case 0x0:return"ObjectNet";
                    case 0x1:return"MobBus RTU";
                    default:return "ObjectNet";
                }
            }

            @Override
            public Short fromString(String value) {
                switch (value){
                    case "ObjectNet":return 0x0;
                    case "MobBus RTU":return 0x1;
                    default:return 0x0;
                }
            }
        });
        box.valueProperty().bindBidirectional(new ShortPropertyFXAdapterSpec(addressProperty));
        Tooltip.install(box, new Tooltip(addressProperty.getDescription()));
        return box;
    }

    static public Button createBoolChangeCommandButton(SoftBoolProperty property){
        Button button=new Button(I18N_FX.getLocalizedString("CHANGE.name"));
        button.setOnAction(event -> property.invertValue());
        Tooltip.install(button, new Tooltip(I18N_FX.getLocalizedString("CHANGE.description")));
        return button;
    }

    static public SlideCheckBox createSlideIndicator(SoftBoolProperty property){
        SlideCheckBox led = new SlideCheckBox();
        led.setScaleX(0.7);
        led.setScaleY(0.7);
        led.setPrefSize(100, 50);
        led.selectedProperty().bindBidirectional(new BooleanPropertyFXAdapter(property));
        Tooltip.install(led, new Tooltip(property.getDescription()));
        return led;
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
        Tooltip.install(slider, new Tooltip(property.getDescription()));
        return slider;
    }

    static public Lcd  createLineIndicator(SoftLongProperty property){
        Lcd lcd = LcdBuilder.create()
                .prefWidth(130)
                .prefHeight(40)
                .styleClass(Lcd.STYLE_CLASS_STANDARD)
                .backgroundVisible(true)
                .foregroundShadowVisible(true)
                .crystalOverlayVisible(true)
                .title(property.getName())
                .titleVisible(true)
                .decimals(0)
                .valueFont(Lcd.LcdFont.LCD)
                .animated(true)
                .build();
        lcd.valueProperty().bind(new LongPropertyFXAdapter(property));
        Tooltip.install(lcd, new Tooltip(property.getDescription()));
        return lcd;
    }

    static public Button createConfigureProppearty(AbstractProperty command){
        return PropertyConfigDialog.crateConfigButton(command);
    }


    static public Button createSetLinebutton(SoftLongProperty property,final long value){
        Button button=new Button(String.valueOf(value));
        button.setOnAction(event -> property.setValue(value));
        Tooltip.install(button, new Tooltip(I18N_FX.getLocalizedString("FAVORIT_VENTIL.SELECT_LINE_"+value)));
        return button;
    }

    static public Button createSetLinebuttonImidiatly(SoftLongProperty property,final long value){
        Button button=new Button(String.valueOf(value));
        button.setOnAction(event -> {
            property.setValue(value);
            Boolean imid = (Boolean) property.getAdditionalInfo(AbstractDevice.IMMEDIATELY_WRITE_FLAG);
            if (imid == null || !imid) {
                property.writeValueRequest();
            }
        });
        return button;
    }


    public static ComboBox<Short> createPANDRIVESpeedSelect(SoftShortProperty addressProperty){
        ComboBox<Short> box=new ComboBox<>();
        for(int i=0;i<12;i++)box.getItems().add((short)i);
        box.setConverter(new StringConverter<Short>() {
            @Override
            public String toString(Short value) {
                switch (value) {
                    case 0:
                        return "BAUD RATE 9600";
                    case 1:
                        return "BAUD RATE 14400";
                    case 2:
                        return "BAUD RATE 19200";
                    case 3:
                        return "BAUD RATE 28800";
                    case 4:
                        return "BAUD RATE 38400";
                    case 5:
                        return "BAUD RATE 57600";
                    case 6:
                        return "BAUD RATE 76800";
                    case 7:
                        return "BAUD RATE 115000";
                    case 8:
                        return "BAUD RATE 230400";
                    case 9:
                        return "BAUD RATE 250000";
                    case 10:
                        return "BAUD RATE 500000";
                    case 11:
                        return "BAUD RATE 100000";
                    default:
                        return "BAUD RATE 9600";
                }
            }

            @Override
            public Short fromString(String value) {
                switch (value) {
                    case "BAUD RATE 9600":
                        return 0;
                    case "BAUD RATE 14400":
                        return 1;
                    case "BAUD RATE 19200":
                        return 2;
                    case "BAUD RATE 28800":
                        return 3;
                    case "BAUD RATE 38400":
                        return 4;
                    case "BAUD RATE 57600":
                        return 5;
                    case "BAUD RATE 76800":
                        return 6;
                    case "BAUD RATE 115000":
                        return 7;
                    case "BAUD RATE 230400":
                        return 8;
                    case "BAUD RATE 250000":
                        return 9;
                    case "BAUD RATE 500000":
                        return 10;
                    case "BAUD RATE 100000":
                        return 11;
                    default:
                        return 1;
                }
            }
        });
        box.valueProperty().bindBidirectional(new ShortPropertyFXAdapterSpec(addressProperty));
        Tooltip.install(box, new Tooltip(addressProperty.getDescription()));
        return box;
    }

    public static Lcd createLcdIndicator(SoftLongProperty property) {
        Lcd lcd = LcdBuilder.create()
                .prefWidth(170)
                .prefHeight(50)
                .styleClass(Lcd.STYLE_CLASS_STANDARD)
                .backgroundVisible(true)
                .foregroundShadowVisible(true)
                .crystalOverlayVisible(true)
                .title(property.getName())
                .titleVisible(true)
                .decimals(0)
                .valueFont(Lcd.LcdFont.LCD)
                .animated(true)
                .build();
        lcd.valueProperty().bind(new LongPropertyFXAdapterSpec(property));
        Tooltip.install(lcd, new Tooltip(property.getDescription()));
        return lcd;
    }

    static public Slider createSliderControl(SoftLongProperty property, int min, int max, int blockincrement, int majorTick, int minorTick) {
        Slider slider = new Slider();
        slider.setMin(min);
        slider.setMax(max);
        slider.valueProperty().bindBidirectional(new LongPropertyFXAdapter(property));
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(majorTick);
        slider.setMinorTickCount(minorTick);
        slider.setBlockIncrement(blockincrement);
        Tooltip.install(slider, new Tooltip(property.getDescription()));
        return slider;
    }

    static public Slider createSliderControl(SoftShortProperty property, int min, int max, int blockincrement, int majorTick, int minorTick) {
        Slider slider = new Slider();
        slider.setMin(min);
        slider.setMax(max);
        slider.valueProperty().bindBidirectional(new ShortPropertyFXAdapter(property));
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(majorTick);
        slider.setMinorTickCount(minorTick);
        slider.setBlockIncrement(blockincrement);
        Tooltip.install(slider, new Tooltip(property.getDescription()));
        return slider;
    }

    static public IntegerInputText createIntegerEditControl(SoftShortProperty property, int min, int max) {
        IntegerInputText editor = new IntegerInputText();
        editor.setMinValue(min);
        editor.setMaxValue(max);
        editor.getIntegerValueProperty().bindBidirectional(new ShortPropertyFXAdapter(property));
        Tooltip.install(editor, new Tooltip(property.getDescription()));
        return editor;
    }

    static public IntegerInputText createIntegerEditControl(SoftLongProperty property, int min, int max) {
        IntegerInputText editor = new IntegerInputText();
        editor.setMinValue(min);
        editor.setMaxValue(max);
        editor.getIntegerValueProperty().bindBidirectional(new LongPropertyFXAdapter(property));
        Tooltip.install(editor, new Tooltip(property.getDescription()));
        return editor;
    }

    public static ComboBox<Short> createFavoritSpeedSelect(SoftShortProperty addressProperty){
        ComboBox<Short> box=new ComboBox<>();
        for(int i=1;i<5;i++)box.getItems().add((short)i);
        box.setConverter(new StringConverter<Short>() {
            @Override
            public String toString(Short value) {
                switch (value) {
                    case 1:
                        return "BAUD RATE 9600";
                    case 2:
                        return "BAUD RATE 19200";
                    case 3:
                        return "BAUD RATE 57000";
                    case 4:
                        return "BAUD RATE 115000";
                    default:
                        return "BAUD RATE 9600";
                }
            }

            @Override
            public Short fromString(String value) {
                switch (value) {
                    case "BAUD RATE 9600":
                        return 1;
                    case "BAUD RATE 19200":
                        return 2;
                    case "BAUD RATE 57000":
                        return 3;
                    case "BAUD RATE 115000":
                        return 4;
                    default:
                        return 1;
                }
            }
        });
        box.valueProperty().bindBidirectional(new ShortPropertyFXAdapterSpec(addressProperty));
        Tooltip.install(box, new Tooltip(addressProperty.getDescription()));
        return box;
    }

}

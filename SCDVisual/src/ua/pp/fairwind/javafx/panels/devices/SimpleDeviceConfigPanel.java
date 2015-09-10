package ua.pp.fairwind.javafx.panels.devices;

import eu.hansolo.enzo.canvasled.Led;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import ua.pp.fairwind.communications.devices.RSLineDevice;
import ua.pp.fairwind.communications.lines.AbstractLine;
import ua.pp.fairwind.communications.lines.LineInterface;
import ua.pp.fairwind.communications.propertyes.DeviceNamedCommandProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftBoolProperty;
import ua.pp.fairwind.io.javafx.propertys.BooleanPropertyFXAdapter;
import ua.pp.fairwind.javafx.I18N.I18N;
import ua.pp.fairwind.javafx.panels.dialogs.LineParametersDialog;

import java.util.List;

/**
 * Created by Сергей on 27.08.2015.
 */
public class SimpleDeviceConfigPanel extends HBox {
    final private RSLineDevice device;
    final private List<LineInterface> lines;
    final private LineChange action;
    private volatile LineInterface selectedLine;

    public interface LineChange{
        void primaryLineChange(LineInterface newline);
        void secondaryLineChange(LineInterface newline);
    }

    public SimpleDeviceConfigPanel(RSLineDevice device) {
        super();
        this.device = device;
        selectedLine=(AbstractLine)device.getPrimaryLine();
        lines=null;
        this.action=null;
        initControl();
    }

    public SimpleDeviceConfigPanel(RSLineDevice device,LineInterface line,List<LineInterface> lines) {
        super();
        this.device = device;
        selectedLine=(AbstractLine)device.getPrimaryLine();
        this.lines=lines;
        this.action=null;
        initControl();
    }

    public SimpleDeviceConfigPanel(RSLineDevice device,LineInterface line,List<LineInterface> lines,LineChange listener) {
        super();
        this.device = device;
        selectedLine=(AbstractLine)device.getPrimaryLine();
        this.lines=lines;
        this.action=listener;
        initControl();
    }

    public SimpleDeviceConfigPanel(RSLineDevice device,LineInterface line) {
        super();
        this.device = device;
        selectedLine=(AbstractLine)device.getPrimaryLine();
        lines=null;
        this.action=null;
        initControl();
    }

    private void initControl(){
        GridPane grid = new GridPane();
        setAlignment(Pos.TOP_CENTER);
        getChildren().add(grid);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        int rowindex=0;
        grid.add(new Label(device.getDeviceType() + " : " + device.getName()), 0, rowindex++, 3, 1);
        grid.add(new Label(I18N.COMMON.getString("DEVICE_ADDRES")), 0, rowindex);
        grid.add(DeviceConfigPanel.createAddressSelect(device.getDeviceAddressProperty()), 1, rowindex);
        grid.add(createConfigureButton(), 2, rowindex++);
        grid.add(new Label(I18N.COMMON.getString("DEVICE_STATUS")), 0, rowindex);
        grid.add(createLedIndicator(device.getLastCommunicationStatus(),Color.GREENYELLOW), 1, rowindex);
        grid.add(createLedIndicator(device.getErrorCommunicationStatus(),Color.RED), 2, rowindex++);
        grid.add(new Label(I18N.COMMON.getString("LAST_COMMUNICATE_TIME")), 0, rowindex++);
        if(lines!=null && !lines.isEmpty()){
            grid.add(new Label(I18N.getLocalizedString("SELECT_LINE_PRIMARY:")), 0, rowindex);
            grid.add(createLineComboBoxP(), 1, rowindex++,2,1);
            grid.add(new Label(I18N.getLocalizedString("SELECT_LINE_SECONDARY:")), 0, rowindex);
            grid.add(createLineComboBoxS(), 1, rowindex++,2,1);
        }
    }

    private Button createConfigureButton(){
        Button button=new Button(I18N.COMMON.getString("CONFIG_DEVICE_DIALOG"));
                button.setOnAction(action->LineParametersDialog.getSerialLineParameterDialog(device,device.getLineParameters()));
        return button;
    }

    public static Button createCommandExecuteButton(DeviceNamedCommandProperty command){
        Button button=new Button(command.getDescription());
        button.setOnAction(event->command.activate());
        return button;
    }

    private Led createLedIndicator(SoftBoolProperty property,Color color){
        Led led = new Led();
        led.setLedColor(color);
        led.onProperty().bind(new BooleanPropertyFXAdapter(property));
        return led;
    }

    private ComboBox<LineInterface> createLineComboBoxP(){
        ComboBox<LineInterface> combo=new ComboBox<>();
        if(lines!=null) combo.getItems().addAll(lines);
        combo.setOnAction(a -> {
            LineInterface selected = combo.getValue();
            device.setPrimerayLine(selected);
            if (action != null) action.primaryLineChange(selected);
        });
        combo.setValue(device.getPrimaryLine());
        return combo;
    }
    private ComboBox<LineInterface> createLineComboBoxS(){
        ComboBox<LineInterface> combo=new ComboBox<>();
        if(lines!=null) combo.getItems().addAll(lines);
        combo.setOnAction(a->{
            LineInterface selected=combo.getValue();
            device.setSecondaryLine(selected);
            if(action!=null)action.secondaryLineChange(selected);
        });
        combo.setValue(device.getSecondaryLine());
        return combo;
    }
}

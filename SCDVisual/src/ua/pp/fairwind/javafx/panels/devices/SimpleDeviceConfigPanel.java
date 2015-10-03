package ua.pp.fairwind.javafx.panels.devices;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import ua.pp.fairwind.communications.devices.abstracts.RSLineDevice;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.javafx.I18N.I18N_FX;
import ua.pp.fairwind.javafx.VisualControls;

import java.util.List;

/**
 * Created by Сергей on 27.08.2015.
 */
public class SimpleDeviceConfigPanel extends HBox {
    final private RSLineDevice device;
    final private List<LineInterface> lines;
    final private LineChange action;
    private volatile LineInterface selectedLine;

    public SimpleDeviceConfigPanel(RSLineDevice device) {
        super();
        this.device = device;
        selectedLine = device.getPrimaryLine();
        lines = null;
        this.action = null;
        initControl();
    }

    public SimpleDeviceConfigPanel(RSLineDevice device, LineInterface line, List<LineInterface> lines) {
        super();
        this.device = device;
        selectedLine = device.getPrimaryLine();
        this.lines = lines;
        this.action = null;
        initControl();
    }

    public SimpleDeviceConfigPanel(RSLineDevice device, LineInterface line, List<LineInterface> lines, LineChange listener) {
        super();
        this.device = device;
        selectedLine = device.getPrimaryLine();
        this.lines = lines;
        this.action = listener;
        initControl();
    }

    public SimpleDeviceConfigPanel(RSLineDevice device, LineInterface line) {
        super();
        this.device = device;
        selectedLine = device.getPrimaryLine();
        lines = null;
        this.action = null;
        initControl();
    }

    private void initControl() {
        GridPane grid = new GridPane();
        setAlignment(Pos.TOP_CENTER);
        getChildren().add(grid);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        int rowindex = 0;
        grid.add(new Label(device.getDeviceType() + " : " + device.getName()), 0, rowindex++, 3, 1);
        grid.add(new Label(I18N_FX.getLocalizedString("DEVICE_ADDRES")), 0, rowindex);
        grid.add(VisualControls.createAddressSelect(device.getDeviceAddressProperty()), 1, rowindex);
        if (lines != null && !lines.isEmpty()) {
            grid.add(new Label(I18N_FX.getLocalizedString("SELECT_LINE_PRIMARY")), 3, rowindex);
            grid.add(createLineComboBoxP(), 4, rowindex);
            grid.add(VisualControls.createPClosePortButton(device), 5, rowindex);
            grid.add(new Label(I18N_FX.getLocalizedString("SELECT_LINE_SECONDARY")), 3, rowindex + 1);
            grid.add(createLineComboBoxS(), 4, rowindex + 1);
            grid.add(VisualControls.createSClosePortButton(device), 5, rowindex + 1);
        }
        grid.add(VisualControls.createConfigureButton(device), 2, rowindex++);
        grid.add(new Label(I18N_FX.getLocalizedString("DEVICE_STATUS")), 0, rowindex);
        grid.add(VisualControls.createLedIndicator(device.getLastCommunicationStatus(), Color.GREENYELLOW), 1, rowindex);
        grid.add(VisualControls.createLedIndicator(device.getErrorCommunicationStatus(), Color.RED), 2, rowindex++);
        grid.add(new Label(I18N_FX.getLocalizedString("LAST_COMMUNICATE_TIME")), 0, rowindex);
        grid.add(VisualControls.createTimeLabel(device.getDeviceLastExchangeTimeProperty()), 1, rowindex);
        grid.add(new Label(I18N_FX.getLocalizedString("LAST_TRY_COMMUNICATE_TIME")), 2, rowindex);
        grid.add(VisualControls.createTimeLabel(device.getDeviceLastTryExchangeProperty()), 3, rowindex);
        grid.add(VisualControls.createCommandExecuteButton2(device.getValidateErrorCommand()), 5, rowindex++);
    }

    private ComboBox<LineInterface> createLineComboBoxP() {
        ComboBox<LineInterface> combo = new ComboBox<>();
        combo.setPrefWidth(150);
        if (lines != null) combo.getItems().addAll(lines);
        combo.setOnAction(a -> {
            LineInterface selected = combo.getValue();
            device.setPrimerayLine(selected);
            if (action != null) action.primaryLineChange(selected);
        });
        combo.setValue(device.getPrimaryLine());
        return combo;
    }

    private ComboBox<LineInterface> createLineComboBoxS() {
        ComboBox<LineInterface> combo = new ComboBox<>();
        combo.setPrefWidth(150);
        if (lines != null) combo.getItems().addAll(lines);
        combo.setOnAction(a -> {
            LineInterface selected = combo.getValue();
            device.setSecondaryLine(selected);
            if (action != null) action.secondaryLineChange(selected);
        });
        combo.setValue(device.getSecondaryLine());
        return combo;
    }

    public interface LineChange {
        void primaryLineChange(LineInterface newline);

        void secondaryLineChange(LineInterface newline);
    }
}

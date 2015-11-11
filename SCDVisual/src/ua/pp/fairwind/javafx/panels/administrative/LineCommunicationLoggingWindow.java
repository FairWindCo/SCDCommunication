package ua.pp.fairwind.javafx.panels.administrative;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import ua.pp.fairwind.communications.SCADASystem;
import ua.pp.fairwind.communications.devices.abstracts.LinedDeviceInterface;
import ua.pp.fairwind.communications.devices.logging.LineMonitoringEvent;
import ua.pp.fairwind.communications.devices.logging.LoggingDevice;
import ua.pp.fairwind.javafx.guiElements.menu.MenuExecutor;
import ua.pp.fairwind.javafx.guiElements.windows.SimpleMenuView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Сергей on 12.08.2014.
 */
public class LineCommunicationLoggingWindow extends SimpleMenuView {
    private final ObservableList<LineMonitoringEvent> list = FXCollections.observableArrayList();
    private final int maxEventinLog;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.S");
    private final SCADASystem scadaSystem;
    private final LoggingDevice dev = new LoggingDevice("lineMonitoringDevice", null,
            (event) -> list.add(event));

    public LineCommunicationLoggingWindow(String menuItem, String menuHint, int maxEventinLog) {
        super(menuItem, menuHint);
        this.maxEventinLog = maxEventinLog;
        this.scadaSystem = null;
    }

    public LineCommunicationLoggingWindow(String menuItem, int maxEventinLog) {
        super(menuItem);
        this.maxEventinLog = maxEventinLog;
        this.scadaSystem = null;
    }

    public LineCommunicationLoggingWindow(String menuItem, String menuHint, SCADASystem scada, int maxEventinLog) {
        super(menuItem, menuHint);
        this.maxEventinLog = maxEventinLog;
        this.scadaSystem = scada;
    }

    public LineCommunicationLoggingWindow(String menuItem, SCADASystem scada, int maxEventinLog) {
        super(menuItem);
        this.maxEventinLog = maxEventinLog;
        this.scadaSystem = scada;
    }

    public LineCommunicationLoggingWindow(String menuItem, String menuHint, SCADASystem scada) {
        super(menuItem, menuHint);
        this.maxEventinLog = 250;
        this.scadaSystem = scada;
    }

    public LineCommunicationLoggingWindow(String menuItem, SCADASystem scada) {
        super(menuItem);
        this.maxEventinLog = 250;
        this.scadaSystem = scada;
    }


    @Override
    protected Node createView() {
        GridPane pane = new GridPane();
        pane.setId("formGrid");
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setPadding(new Insets(5, 5, 5, 5));
        TableView<LineMonitoringEvent> table = new TableView<>(list);
        TableColumn<LineMonitoringEvent, String> col1 = new TableColumn<>("Time");
        col1.setCellValueFactory(param -> {
            if (param.getValue() == null) return new SimpleStringProperty("----");
            Date time = new Date(param.getValue().getDate());
            return new SimpleStringProperty(dateFormat.format(time));
        });
        TableColumn<LineMonitoringEvent, String> col2 = new TableColumn<>("Source Line");
        col2.setCellValueFactory(param -> {
            if (param.getValue() == null) return new SimpleStringProperty("----");
            if (param.getValue().getLine() == null) return new SimpleStringProperty("----");
            String node = param.getValue().getLine().getName();
            if (node == null) return new SimpleStringProperty("нет");
            return new SimpleStringProperty(node);
        });
        TableColumn<LineMonitoringEvent, String> col3 = new TableColumn<>("Source Device");
        col3.setCellValueFactory(param -> {
            if (param.getValue() == null) return new SimpleStringProperty("----");
            if (param.getValue().getDevice() == null) return new SimpleStringProperty("----");
            String node = param.getValue().getDevice().getName();
            if (node == null) return new SimpleStringProperty("нет");
            return new SimpleStringProperty(node);
        });
        TableColumn<LineMonitoringEvent, String> col4 = new TableColumn<>("Action");
        col4.setCellValueFactory(param -> {
            if (param.getValue() == null) return new SimpleStringProperty("----");
            String node = param.getValue().getAction().name();
            if (node == null) return new SimpleStringProperty("нет");
            return new SimpleStringProperty(node);
        });
        TableColumn<LineMonitoringEvent, String> col5 = new TableColumn<>("Message");
        col5.setCellValueFactory(param -> {
            if (param.getValue() == null) return new SimpleStringProperty("----");
            String node = param.getValue().getStringBuffer();
            return new SimpleStringProperty(node);
        });
        TableColumn<LineMonitoringEvent, String> col6 = new TableColumn<>("Target Property");
        col6.setCellValueFactory(param -> {
            if (param.getValue() == null) return new SimpleStringProperty("----");
            if (param.getValue().getProperty() == null) return new SimpleStringProperty("----");
            String node = param.getValue().getProperty().getName();
            if (node == null) return new SimpleStringProperty("нет");
            return new SimpleStringProperty(node);
        });

        table.getColumns().add(col1);
        table.getColumns().add(col2);
        table.getColumns().add(col3);
        table.getColumns().add(col4);
        table.getColumns().add(col5);
        table.getColumns().add(col6);

        col1.setPrefWidth(130);
        col2.setPrefWidth(100);
        col3.setPrefWidth(100);
        col4.setPrefWidth(50);
        col5.setPrefWidth(300);
        col6.setPrefWidth(110);
        table.setPrefHeight(600);
        table.setPrefWidth(750);
        pane.add(table, 0, 0);
        return pane;
    }

    private void addEvent(LineMonitoringEvent event) {
        if (Platform.isFxApplicationThread()) {
            if (list.size() >= maxEventinLog) {
                list.remove(0);
            }
            list.add(event);
        } else {
            Platform.runLater(() -> {
                if (list.size() >= maxEventinLog) {
                    list.remove(0);
                }
                list.add(event);
            });
        }
    }

    public LinedDeviceInterface getLoggingDevice() {
        return dev;
    }

    @Override
    public void onShow(MenuExecutor executor) {
        if (scadaSystem != null) scadaSystem.setMonitoringToAllLines(dev);
        super.onShow(executor);
    }
}

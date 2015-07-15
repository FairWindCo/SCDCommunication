package ua.pp.fairwind.javafx.panels;

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
import ua.pp.fairwind.io.devices.Device;
import ua.pp.fairwind.io.devices.DeviceInterfaces;
import ua.pp.fairwind.io.devices.protocols.LogingBufferProtocol;
import ua.pp.fairwind.io.lines.communicationcommands.CommunicationAction;
import ua.pp.fairwind.io.utils.CommunicationUtils;
import ua.pp.fairwind.javafx.guiElements.windows.SimpleView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Сергей on 12.08.2014.
 */
public class LogLineBufferView extends SimpleView{
    private final ObservableList<LogingBufferProtocol.MonitoringEvent> list= FXCollections.observableArrayList();
    private final int maxEventinLog;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.S");
    private final DeviceInterfaces dev=new Device(100013L,"lineMonitoringDevice","Line Monitoring Device",new LogingBufferProtocol(event -> {
        if(event!=null)addEvent(event);
    }));

    public LogLineBufferView(int maxEventinLog) {
        this.maxEventinLog = maxEventinLog;
    }

    public LogLineBufferView() {
        this.maxEventinLog = 1000;
    }

    @Override
    protected Node createView() {
        GridPane pane= new GridPane();
        pane.setId("formGrid");
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setPadding(new Insets(5, 5, 5, 5));
        TableView<LogingBufferProtocol.MonitoringEvent> table=new TableView<>(list);
        TableColumn<LogingBufferProtocol.MonitoringEvent, String> col1= new TableColumn<>("Time");
        col1.setCellValueFactory(param -> {
            if(param.getValue()==null) return new SimpleStringProperty("----");
            Date time=new Date(param.getValue().timeAction);
            return new SimpleStringProperty(dateFormat.format(time));
        });
        TableColumn<LogingBufferProtocol.MonitoringEvent, String> col2=new TableColumn<>("Source");
        col2.setCellValueFactory(param -> {
            if(param.getValue()==null) return new SimpleStringProperty("----");
            String node=param.getValue().action.name();
            if(node==null)return new SimpleStringProperty("нет");
            return new SimpleStringProperty(node);
        });
        TableColumn<LogingBufferProtocol.MonitoringEvent, String> col3=new TableColumn<>("Message");
        col3.setCellValueFactory(param -> {
            if(param.getValue()==null) return new SimpleStringProperty("----");
            String str=(param.getValue().action!= CommunicationAction.COMMAND)?CommunicationUtils.bufferToString(param.getValue().buffer):param.getValue().command;
            return new SimpleStringProperty(str);
        });

        table.getColumns().add(col1);
        table.getColumns().add(col2);
        table.getColumns().add(col3);

        col1.setPrefWidth(150);
        col2.setPrefWidth(170);
        col3.setPrefWidth(500);
        table.setPrefHeight(600);
        pane.add(table, 0, 0);
        return pane;
    }

    private void addEvent(LogingBufferProtocol.MonitoringEvent event){
        if(Platform.isFxApplicationThread()) {
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

    public DeviceInterfaces getDev() {
        return dev;
    }
}

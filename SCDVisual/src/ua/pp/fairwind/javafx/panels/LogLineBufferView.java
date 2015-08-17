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
import ua.pp.fairwind.communications.devices.DeviceInterface;
import ua.pp.fairwind.communications.devices.logging.LineMonitoringEvent;
import ua.pp.fairwind.communications.devices.logging.LoggingDevice;
import ua.pp.fairwind.javafx.guiElements.windows.SimpleView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Сергей on 12.08.2014.
 */
public class LogLineBufferView extends SimpleView{
    private final ObservableList<LineMonitoringEvent> list= FXCollections.observableArrayList();
    private final int maxEventinLog;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.S");
    private final LoggingDevice dev=new LoggingDevice(100013L, "lineMonitoringDevice", null, "Line Monitoring Device", null,(event)->list.add(event) );

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
        TableView<LineMonitoringEvent> table=new TableView<>(list);
        TableColumn<LineMonitoringEvent, String> col1= new TableColumn<>("Time");
        col1.setCellValueFactory(param -> {
            if(param.getValue()==null) return new SimpleStringProperty("----");
            Date time=new Date(param.getValue().getDate());
            return new SimpleStringProperty(dateFormat.format(time));
        });
        TableColumn<LineMonitoringEvent, String> col2=new TableColumn<>("Source Line");
        col2.setCellValueFactory(param -> {
            if(param.getValue()==null) return new SimpleStringProperty("----");
            if(param.getValue().getLine()==null) return new SimpleStringProperty("----");
            String node=param.getValue().getLine().getName();
            if(node==null)return new SimpleStringProperty("нет");
            return new SimpleStringProperty(node);
        });
        TableColumn<LineMonitoringEvent, String> col3=new TableColumn<>("Source Device");
        col2.setCellValueFactory(param -> {
            if(param.getValue()==null) return new SimpleStringProperty("----");
            if(param.getValue().getDevice()==null) return new SimpleStringProperty("----");
            String node=param.getValue().getDevice().getName();
            if(node==null)return new SimpleStringProperty("нет");
            return new SimpleStringProperty(node);
        });
        TableColumn<LineMonitoringEvent, String> col4=new TableColumn<>("Action");
        col2.setCellValueFactory(param -> {
            if(param.getValue()==null) return new SimpleStringProperty("----");
            String node=param.getValue().getAction().name();
            if(node==null)return new SimpleStringProperty("нет");
            return new SimpleStringProperty(node);
        });
        TableColumn<LineMonitoringEvent, String> col5=new TableColumn<>("Message");
        col3.setCellValueFactory(param -> {
            if(param.getValue()==null) return new SimpleStringProperty("----");
            String node=param.getValue().getStringBuffer();
            return new SimpleStringProperty(node);
        });

        table.getColumns().add(col1);
        table.getColumns().add(col2);
        table.getColumns().add(col3);
        table.getColumns().add(col4);
        table.getColumns().add(col5);

        col1.setPrefWidth(90);
        col2.setPrefWidth(120);
        col3.setPrefWidth(120);
        col4.setPrefWidth(30);
        col5.setPrefWidth(250);
        table.setPrefHeight(600);
        pane.add(table, 0, 0);
        return pane;
    }

    private void addEvent(LineMonitoringEvent event){
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

    public DeviceInterface getDev() {
        return dev;
    }
}

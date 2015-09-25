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
import ua.pp.fairwind.communications.messagesystems.event.ElementEventListener;
import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.messagesystems.event.EventType;
import ua.pp.fairwind.javafx.guiElements.windows.SimpleView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Сергей on 12.08.2014.
 */
public class LogEventView extends SimpleView implements ElementEventListener{
    private final ObservableList<HardwareNodeEvent> list= FXCollections.observableArrayList();
    private final int maxEventinLog;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.S");

    public LogEventView(int maxEventinLog) {
        this.maxEventinLog = maxEventinLog;
    }

    public LogEventView() {
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
        TableView<HardwareNodeEvent> table=new TableView<>(list);
        TableColumn<HardwareNodeEvent, String> col1= new TableColumn<>("Time");
        col1.setCellValueFactory(param -> {
            if(param.getValue()==null) return new SimpleStringProperty("----");
            Date time=new Date(param.getValue().getTime());
            return new SimpleStringProperty(dateFormat.format(time));
        });
        TableColumn<HardwareNodeEvent, String> col2=new TableColumn<>("Source");
        col2.setCellValueFactory(param -> {
            if(param.getValue()==null) return new SimpleStringProperty("----");
            String node=param.getValue().getElementName();
            if(node==null)return new SimpleStringProperty("нет");
            return new SimpleStringProperty(node);
        });
        TableColumn<HardwareNodeEvent, String> col3=new TableColumn<>("Message");
        col3.setCellValueFactory(param -> {
            if (param.getValue() == null) return new SimpleStringProperty("----");
            String str = param.getValue().getInfo();
            return new SimpleStringProperty(str == null ? "нет" : str);
        });
        TableColumn<HardwareNodeEvent, String> col4=new TableColumn<>("Level");
        col4.setCellValueFactory(param -> {
            if (param.getValue() == null) return new SimpleStringProperty("----");
            return new SimpleStringProperty(param.getValue().getLevel().name());
        });
        table.getColumns().add(col1);
        table.getColumns().add(col2);
        table.getColumns().add(col3);
        table.getColumns().add(col4);
        col1.setPrefWidth(120);
        col2.setPrefWidth(150);
        col3.setPrefWidth(350);
        col4.setPrefWidth(150);
        table.setPrefHeight(600);
        pane.add(table, 0, 0);
        return pane;
    }

    private void addEvent(HardwareNodeEvent event){
        if(event.getLevel()== EventType.FATAL_ERROR){
            getExecutr().showInfoDialog(event.getInfo(), InfoDialog.dialogstyle.ERROR);
        }
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

    @Override
    public void elementEvent(Event event,Object params) {
        addEvent(new HardwareNodeEvent(event.sourceElement.getHardwareName(),event.typeEvent,event.params));
    }

}

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
import ua.pp.fairwind.io.node.AddressableNode;
import ua.pp.fairwind.io.node.HardwareNodeEvent;
import ua.pp.fairwind.io.node.HardwareNodeListener;
import ua.pp.fairwind.javafx.guiElements.windows.SimpleView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Сергей on 12.08.2014.
 */
public class LogEventView extends SimpleView implements HardwareNodeListener{
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
            AddressableNode node=param.getValue().getSource();
            if(node==null)return new SimpleStringProperty("нет");
            return new SimpleStringProperty(node.getHardwareID());
        });
        TableColumn<HardwareNodeEvent, String> col3=new TableColumn<>("Message");
        col3.setCellValueFactory(param -> {
            if(param.getValue()==null) return new SimpleStringProperty("----");
            String str=param.getValue().getMessage();
            return new SimpleStringProperty(str==null?"нет":str);
        });
        TableColumn<HardwareNodeEvent, String> col4=new TableColumn<>("Level");
        col4.setCellValueFactory(param -> {
            if(param.getValue()==null) return new SimpleStringProperty("----");
            return new SimpleStringProperty(param.getValue().getLevel().name());
        });
        TableColumn<HardwareNodeEvent, String> col5=new TableColumn<>("Exception");
        col5.setCellValueFactory(param -> {
            if(param.getValue()==null) return new SimpleStringProperty("----");
            Exception ex=param.getValue().getException();
            if(ex==null)return new SimpleStringProperty("----");
            return new SimpleStringProperty(ex.getClass().getName()+" "+ex.getMessage());
        });
        table.getColumns().add(col1);
        table.getColumns().add(col2);
        table.getColumns().add(col3);
        table.getColumns().add(col4);
        table.getColumns().add(col5);
        col1.setPrefWidth(120);
        col2.setPrefWidth(150);
        col3.setPrefWidth(350);
        col4.setPrefWidth(150);
        col5.setPrefWidth(70);
        table.setPrefHeight(600);
        pane.add(table, 0, 0);
        return pane;
    }

    private void addEvent(HardwareNodeEvent event){
        if(event.getLevel()== HardwareNodeEvent.EventLevel.FATAL_ERROR){
            getExecutr().showInfoDialog(event.getMessage(), InfoDialog.dialogstyle.ERROR);
        }
        if(event.getParent()!=null) addEvent(event.getParent());
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
    public void eventFromHardwareNode(HardwareNodeEvent event) {
        if(event!=null)addEvent(event);
    }
}

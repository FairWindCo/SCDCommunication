package ua.pp.fairwind.javafx.panels;

import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.lines.AbstractLine;
import ua.pp.fairwind.communications.lines.LineInterface;
import ua.pp.fairwind.communications.lines.performance.PerformanceMonitorEventData;
import ua.pp.fairwind.communications.propertyes.event.ElementEventListener;
import ua.pp.fairwind.communications.propertyes.event.EventType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Сергей on 24.09.2014.
 */
public class LineInfoBar extends Pane {
    private final Label time=new Label();
    private final Label level=new Label();
    private final Label object=new Label();
    private final Label info=new Label();
    private final Label message=new Label();
    private final DateTimeFormatter formater=DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss.SSS");
    private final Label readStatistic=new Label();
    private final Label writeStatistic=new Label();
    private final Label otherStatistic=new Label();
    private final CheckBox enableStatistic=new CheckBox("Monitor");

    private final ElementEventListener listener=new ElementEventListener() {
        @Override
        public void elementEvent(ElementInterface element, EventType typeEvent, Object params) {
            if (typeEvent == EventType.PERFORMANCE && params instanceof PerformanceMonitorEventData) {
                Platform.runLater(() -> updateStatInfo((PerformanceMonitorEventData) params));
            } else if (typeEvent == EventType.ERROR || typeEvent == EventType.FATAL_ERROR || typeEvent == EventType.PARSE_ERROR) {
                Platform.runLater(() -> updateInfo(element, typeEvent, params));
            } else if (typeEvent == EventType.TIMEOUT) {
                Platform.runLater(() -> updateInfo(element, typeEvent, params));
            }
        }
    };

    private volatile LineInterface line;

    private final GridPane pane=new GridPane();

    public LineInfoBar(double height,LineInterface line) {
        setPrefHeight(height);
        setId("buttomPanel");
        setupLine(line);
        enableStatistic.onActionProperty().set(value -> {
            if (this.line != null && this.line instanceof AbstractLine) {
                ((AbstractLine) this.line).setPerformanceMonitor(enableStatistic.isSelected());
            }
        });
        pane.setHgap(10);
        this.getChildren().add(pane);
        pane.add(time, 0, 0);
        pane.add(level, 1, 0);
        pane.add(object, 2, 0);
        pane.add(info, 3, 0);
        pane.add(message, 4, 0);
        time.setPrefWidth(150);
        time.setMaxWidth(150);
        level.setMaxWidth(100);
        object.setMaxWidth(150);
        info.setMaxWidth(350);
        message.setMaxWidth(350);
        readStatistic.setMaxWidth(80);
        writeStatistic.setMaxWidth(80);
        otherStatistic.setMaxWidth(80);
        enableStatistic.setMaxHeight(30);
        enableStatistic.setMaxWidth(200);
        enableStatistic.setPrefSize(30,120);
        pane.add(readStatistic, 5, 0);
        pane.add(writeStatistic, 6, 0);
        pane.add(otherStatistic, 7, 0);
        pane.add(enableStatistic, 8, 0);
        pane.setGridLinesVisible(true);
    }

    public LineInfoBar(double height, String id,AbstractLine line) {
        setId(id);
        setPrefHeight(height);
        setupLine(line);
        enableStatistic.onActionProperty().set(value -> {
            if (this.line != null && this.line instanceof AbstractLine) {
                ((AbstractLine) this.line).setPerformanceMonitor(enableStatistic.isSelected());
            }
        });
        pane.setHgap(10);
        this.getChildren().add(pane);
        pane.add(time, 0, 0);
        pane.add(level, 1, 0);
        pane.add(object, 2, 0);
        pane.add(info, 3, 0);
        pane.add(message, 4, 0);
        pane.add(readStatistic, 5, 0);
        pane.add(writeStatistic, 6, 0);
        pane.add(otherStatistic, 7, 0);
        pane.add(enableStatistic, 8, 0);
        pane.setGridLinesVisible(true);
    }


    private void updateInfo(ElementInterface element,EventType eventType,Object parameters){
            time.setText(LocalDateTime.now().format(formater));
            if(eventType!=null) {
                level.setText(eventType.name());
            }
            if(parameters!=null){
                info.setText(parameters.toString());
            } else {
                info.setText("");
            }
            if(element!=null){
                String id=element.getHardwareName();
                object.setText(id);
            } else {
                object.setText("");
            }
            message.setText("");
    }

    private void updateStatInfo(PerformanceMonitorEventData performanceMonitorEventData){
        if(performanceMonitorEventData!=null) {
            switch (performanceMonitorEventData.getExecutionType()){
                case READ_OPERATION:readStatistic.setText("R:"+String.valueOf(performanceMonitorEventData.getExecutionTime()));break;
                case WRITE_OPERATION:writeStatistic.setText("W:"+String.valueOf(performanceMonitorEventData.getExecutionTime()));break;
                default:
                    otherStatistic.setText("O:"+String.valueOf(performanceMonitorEventData.getExecutionTime()));break;
            }
        }
    }

    public void setMessage(ElementInterface element,EventType eventType,Object parameters){
        if(Platform.isFxApplicationThread()){
            updateInfo(element,eventType,parameters);
        } else {
            try {
                Platform.runLater(() -> updateInfo(element,eventType,parameters));
            }catch (Exception ignore){
                ignore.printStackTrace();
            }
        }
    }

    private void updateMessage(String msg){
        time.setText(LocalDateTime.now().format(formater));
        level.setText("Message:");
        info.setText("");
        object.setText("");
        message.setText(msg);
    }

    public void setMessage(String msg){
        if(Platform.isFxApplicationThread()){
            updateMessage(msg);
        } else {
            try {
                Platform.runLater(() -> updateMessage(msg));
            }catch (Exception ignore){
                ignore.printStackTrace();
            }
        }
    }

    public void setupLine(LineInterface newline){
        if(line!=null){
            line.removeEventListener(listener);
        }
        line=newline;
        if(newline!=null) {
            line.addEventListener(listener);
            if (newline instanceof AbstractLine) {
                final boolean val = ((AbstractLine) line).isPerformanceMonitor();
                Platform.runLater(() -> enableStatistic.setSelected(val));
            } else {
                Platform.runLater(() -> enableStatistic.setIndeterminate(true));
            }
            setVisible(true);
        } else {
            setVisible(false);
        }
    }



}

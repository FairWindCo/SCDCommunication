package ua.pp.fairwind.javafx.panels;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import ua.pp.fairwind.communications.propertyes.event.ElementEventListener;
import ua.pp.fairwind.io.node.HardwareNodeEvent;
import ua.pp.fairwind.javafx.guiElements.ButtonPanel;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by Сергей on 24.09.2014.
 */
public class InfoBar extends ButtonPanel {
    private final Label time=new Label();
    private final Label level=new Label();
    private final Label object=new Label();
    private final Label info=new Label();
    private final Label message=new Label();
    private final DateTimeFormatter formater=DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss.SSS");
    private final GridPane pane=new GridPane();

    public InfoBar(double height) {
        super(height);
        pane.setHgap(10);
        this.getChildren().add(pane);
        pane.add(time,0,0);
        pane.add(level,1,0);
        pane.add(object,2,0);
        pane.add(info,3,0);
        pane.add(message,4,0);
        time.setMaxWidth(150);
        level.setMaxWidth(100);
        object.setMaxWidth(150);
        info.setMaxWidth(350);
        message.setMaxWidth(350);
        pane.setGridLinesVisible(true);
    }

    public InfoBar(double height, String id) {
        super(height, id);
        pane.setHgap(10);
        this.getChildren().add(pane);
        pane.add(time,0,0);
        pane.add(level,1,0);
        pane.add(object,2,0);
        pane.add(info,3,0);
        pane.add(message,4,0);
        pane.setGridLinesVisible(true);
    }

    @Override
    protected void constructAdditionalElements() {
    }

    private void updateInfo(ElementEventListener event){
        if(event!=null){
            Instant tim=Instant.ofEpochMilli(event.getTime());
            time.setText(LocalDateTime.ofInstant(tim, ZoneId.systemDefault()).format(formater));
            if(event.getLevel()!=null){
                level.setText(event.getLevel().name());
            } else {
                level.setText("");
            }
            if(event.getMessage()!=null){
                info.setText(event.getMessage());
            } else {
                info.setText("");
            }
            if(event.getSource()!=null){
                String id=event.getSource().getHardwareID();
                if(event.getParent()!=null){
                    HardwareNodeEvent parent=event.getParent();
                    while (parent.getParent()!=null){
                        parent=parent.getParent();
                    }
                    if(parent.getSource()!=null){
                        id=parent.getSource().getHardwareID();
                    }
                }
                object.setText(id);
            } else {
                object.setText("");
            }
            message.setText("");
        } else {
            time.setText(LocalDateTime.now().format(formater));
            level.setText("");
            message.setText("");
            info.setText("");
            object.setText("");
        }
    }

    public void setMessage(HardwareNodeEvent event){
        if(Platform.isFxApplicationThread()){
            updateInfo(event);
        } else {
            try {
                Platform.runLater(() -> updateInfo(event));
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

}

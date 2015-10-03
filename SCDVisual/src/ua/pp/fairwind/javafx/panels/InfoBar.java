package ua.pp.fairwind.javafx.panels;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.messagesystems.event.EventType;
import ua.pp.fairwind.javafx.guiElements.ButtonPanel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Сергей on 24.09.2014.
 */
public class InfoBar extends ButtonPanel {
    private final Label time = new Label();
    private final Label level = new Label();
    private final Label object = new Label();
    private final Label info = new Label();
    private final Label message = new Label();
    private final DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss.SSS");
    private final GridPane pane = new GridPane();

    public InfoBar(double height) {
        super(height);
        pane.setHgap(10);
        this.getChildren().add(pane);
        pane.add(time, 0, 0);
        pane.add(level, 1, 0);
        pane.add(object, 2, 0);
        pane.add(info, 3, 0);
        pane.add(message, 4, 0);
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
        pane.add(time, 0, 0);
        pane.add(level, 1, 0);
        pane.add(object, 2, 0);
        pane.add(info, 3, 0);
        pane.add(message, 4, 0);
        pane.setGridLinesVisible(true);
    }

    @Override
    protected void constructAdditionalElements() {
    }

    private void updateInfo(ElementInterface element, EventType eventType, Object parameters) {
        time.setText(LocalDateTime.now().format(formater));
        if (eventType != null) {
            level.setText(eventType.name());
        }
        if (parameters != null) {
            info.setText(parameters.toString());
        } else {
            info.setText("");
        }
        if (element != null) {
            String id = element.getHardwareName();
            object.setText(id);
        } else {
            object.setText("");
        }
        message.setText("");
    }

    public void setMessage(ElementInterface element, EventType eventType, Object parameters) {
        if (Platform.isFxApplicationThread()) {
            updateInfo(element, eventType, parameters);
        } else {
            try {
                Platform.runLater(() -> updateInfo(element, eventType, parameters));
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
        }
    }

    private void updateMessage(String msg) {
        time.setText(LocalDateTime.now().format(formater));
        level.setText("Message:");
        info.setText("");
        object.setText("");
        message.setText(msg);
    }

    public void setMessage(String msg) {
        if (Platform.isFxApplicationThread()) {
            updateMessage(msg);
        } else {
            try {
                Platform.runLater(() -> updateMessage(msg));
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
        }
    }

}

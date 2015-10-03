package ua.pp.fairwind.javafx.panels.administrative;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import ua.pp.fairwind.communications.SCADASystem;
import ua.pp.fairwind.communications.messagesystems.event.ElementEventListener;
import ua.pp.fairwind.communications.messagesystems.event.Event;
import ua.pp.fairwind.communications.messagesystems.event.EventType;
import ua.pp.fairwind.javafx.guiElements.menu.MenuExecutor;
import ua.pp.fairwind.javafx.guiElements.windows.SimpleMenuView;
import ua.pp.fairwind.javafx.panels.HardwareNodeEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AllEventMonitorWindow extends SimpleMenuView implements ElementEventListener {
    final private ObservableList<HardwareNodeEvent> events = FXCollections.observableArrayList();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.S");
    private final SCADASystem scadaSystem;
    private int maxSize = 100;

    public AllEventMonitorWindow(String menuItem, String menuHint, SCADASystem scadaSystem, int maxSize) {
        super(menuItem, menuHint);
        this.scadaSystem = scadaSystem;
        this.maxSize = maxSize;
    }

    public AllEventMonitorWindow(String menuItem, SCADASystem scadaSystem, int maxSize) {
        super(menuItem);
        this.scadaSystem = scadaSystem;
        this.maxSize = maxSize;
    }

    public AllEventMonitorWindow(String menuItem, int maxSize) {
        super(menuItem);
        this.maxSize = maxSize;
        this.scadaSystem = null;
    }

    public AllEventMonitorWindow(String menuItem, String menuHint, int maxSize) {
        super(menuItem, menuHint);
        this.maxSize = maxSize;
        this.scadaSystem = null;
    }

    public AllEventMonitorWindow(String menuItem) {
        super(menuItem);
        this.scadaSystem = null;
    }

    public AllEventMonitorWindow(String menuItem, String menuHint) {
        super(menuItem, menuHint);
        this.scadaSystem = null;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Node createView() {
        BorderPane base = new BorderPane();
        base.setId("basePanel");
        HBox title = new HBox(80d);
        title.setAlignment(Pos.CENTER);
        base.topProperty().set(title);
        Label titleLabel = new Label("SYSTEM LOG");
        title.getChildren().add(titleLabel);
        titleLabel.getStyleClass().add("formLabel");
        TableView<HardwareNodeEvent> logTable = new TableView<>(events);
        BorderPane.setMargin(logTable, new Insets(1d));
        base.setCenter(logTable);
        TableColumn<HardwareNodeEvent, String> message = new TableColumn<>("MESSAGE");
        TableColumn<HardwareNodeEvent, String> eventtype = new TableColumn<>("EVENT");
        TableColumn<HardwareNodeEvent, String> object = new TableColumn<>("OBJECT");
        TableColumn<HardwareNodeEvent, String> timecol = new TableColumn<>("Time");
        message.setMinWidth(350);
        message.setCellValueFactory(new PropertyValueFactory<>("info"));
        eventtype.setCellValueFactory(new PropertyValueFactory<>("level"));
        timecol.setCellValueFactory(param -> {
            if (param.getValue() == null) return new SimpleStringProperty("----");
            Date time = new Date(param.getValue().getTime());
            return new SimpleStringProperty(dateFormat.format(time));
        });
        //object.setCellValueFactory(new PropertyValueFactory<ErrorEvent,String>("source"));
        object.setCellValueFactory(arg0 -> {
            if (arg0 != null && arg0.getValue() != null && arg0.getValue().getElementName() != null) {
                return new SimpleStringProperty(arg0.getValue().getElementName());
            } else {
                return new SimpleStringProperty("null");
            }
        });
        logTable.getColumns().addAll(timecol, eventtype, object, message);/**/
        logTable.autosize();
        return base;

    }


    synchronized public void println(String message) {
        errorRecived(new HardwareNodeEvent(null, EventType.ERROR, message));
    }

    synchronized public void println(String message, EventType level) {
        errorRecived(new HardwareNodeEvent(null, level, message));
    }


    synchronized public void errorRecived(final HardwareNodeEvent referense) {
        if (referense != null && events != null) {
            //System.out.println(referense);
            if (Platform.isFxApplicationThread()) {
                events.add(0, referense);
                if (maxSize < events.size()) {
                    events.remove(maxSize - 1, events.size());
                }
            } else {
                try {
                    Platform.runLater(() -> {
                        events.add(0, referense);
                        if (maxSize < events.size()) {
                            events.remove(maxSize - 1, events.size());
                        }
                    });
                } catch (IllegalStateException ex) {
                    events.add(0, referense);
                    if (maxSize < events.size()) {
                        events.remove(maxSize - 1, events.size());
                    }
                }
            }

        }
    }

    @Override
    public void elementEvent(Event event, Object params) {
        errorRecived(new HardwareNodeEvent(event.sourceElement != null ? event.sourceElement.getName() : null, event.typeEvent, event.params != null ? event.params.toString() : ""));
    }

    @Override
    public void onShow(MenuExecutor executor) {
        if (scadaSystem != null) scadaSystem.setAllDevicesEventListener(this);
        super.onShow(executor);
    }

}

import images.MyResourceLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ua.pp.fairwind.communications.devices.hardwaredevices.favorit.FavoritCoreDeviceV1;
import ua.pp.fairwind.communications.lines.SerialLine;
import ua.pp.fairwind.communications.messagesystems.MessageSystemManager;
import ua.pp.fairwind.communications.messagesystems.event.EventType;
import ua.pp.fairwind.communications.timeaction.PropertyTimer;
import ua.pp.fairwind.javafx.panels.LineInfoBar;
import ua.pp.fairwind.javafx.panels.devices.FavoritPanel;
import ua.pp.fairwind.javafx.panels.devices.SimpleDeviceConfigPanel;

/**
 * Created by Сергей on 27.08.2015.
 */

public class testDevicePanel extends Application {
    SerialLine line;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MyResourceLoader resloader = new MyResourceLoader();
        VBox vbox = new VBox();
        vbox.setId("mainPanel");
        line = new SerialLine("com6", "SERIAL PORT #6", null, 5000);
        line.setPerformanceMonitor(true);
        line.addEventListener((event, params) -> {
            if (event.typeEvent == EventType.PERFORMANCE) {
                if (event.params != null) System.out.println(event.params);
            }
        });
        LineInfoBar infoBar = new LineInfoBar(50, line);
        FavoritCoreDeviceV1 dev = new FavoritCoreDeviceV1(1);
        dev.setPrimerayLine(line);
        //dev.getErrorCommunicationStatus().setInternalValue(true);
        //DeviceConfigPanel panel=new DeviceConfigPanel(dev);
        SimpleDeviceConfigPanel panel = new SimpleDeviceConfigPanel(dev);
        FavoritPanel panel2 = new FavoritPanel(dev);
        vbox.getChildren().add(panel);
        vbox.getChildren().add(panel2);
        vbox.getChildren().add(infoBar);
        Scene scene = new Scene(vbox);
        scene.getStylesheets().addAll(resloader.getExternalResourceURILink("application.css"));

        //vbox.setPrefHeight(970);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        if (line != null) line.destroy();
        PropertyTimer.stopWork();
        MessageSystemManager.destroy();
        super.stop();
    }
}

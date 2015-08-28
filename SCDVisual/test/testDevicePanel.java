import images.MyResourceLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ua.pp.fairwind.communications.devices.favorit.FavoritCoreDeviceV1;
import ua.pp.fairwind.communications.lines.SerialLine;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystemMultiDipatch;
import ua.pp.fairwind.communications.propertyes.event.EventType;
import ua.pp.fairwind.communications.timeaction.PropertyTimer;
import ua.pp.fairwind.javafx.panels.LineInfoBar;
import ua.pp.fairwind.javafx.panels.SimpleDeviceConfigPanel;
import ua.pp.fairwind.javafx.panels.devices.FavoritPanel;

/**
 * Created by Сергей on 27.08.2015.
 */
public class testDevicePanel extends Application {
    SerialLine line;
    MessageSubSystem ms=new MessageSubSystemMultiDipatch();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MyResourceLoader resloader=new MyResourceLoader();
        VBox vbox=new VBox();
        vbox.setId("mainPanel");
        line=new SerialLine("com6","SERIAL PORT #6",null,"RS LINE PORT",ms,5000);
        line.setPerformanceMonitor(true);
        line.addEventListener((elemnt, event, params) -> {
            if (event == EventType.PERFORMANCE) {
                if (params != null) System.out.println(params);
            }
        });
        LineInfoBar infoBar=new LineInfoBar(50,line);
        FavoritCoreDeviceV1 dev=new FavoritCoreDeviceV1(1,"Favorit Ventil V1",null,"Test Description",ms);
        dev.setPrimerayLine(line);
        //dev.getErrorCommunicationStatus().setInternalValue(true);
        //DeviceConfigPanel panel=new DeviceConfigPanel(dev);
        SimpleDeviceConfigPanel panel=new SimpleDeviceConfigPanel(dev);
        FavoritPanel panel2=new FavoritPanel(dev);
        vbox.getChildren().add(panel);
        vbox.getChildren().add(panel2);
        vbox.getChildren().add(infoBar);
        Scene scene=new Scene(vbox);
        scene.getStylesheets().addAll(resloader.getExternalResourceURILink("application.css"));

        //vbox.setPrefHeight(970);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        if(line!=null)line.destroy();
        PropertyTimer.stopWork();
        MessageSubSystemMultiDipatch.destroyAllService();
        super.stop();
    }
}

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ua.pp.fairwind.communications.devices.favorit.FavoritCoreDeviceV1;
import ua.pp.fairwind.communications.lines.SerialLine;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystemMultiDipatch;
import ua.pp.fairwind.communications.propertyes.event.EventType;
import ua.pp.fairwind.javafx.panels.DeviceConfigPanel;
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
        VBox vbox=new VBox();
        line=new SerialLine("com5","SERIAL PORT #5",null,"RS LINE PORT",ms,5000);
        line.setPerformanceMonitor(true);
        line.addEventListener((elemnt,event,params)->{
            if(event== EventType.PERFORMANCE){
                if(params!=null)System.out.println(params);
            }
        });
        FavoritCoreDeviceV1 dev=new FavoritCoreDeviceV1(1,"Favorit Ventil V1",null,"Test Description",ms);
        dev.setPrimerayLine(line);
        //dev.getErrorCommunicationStatus().setInternalValue(true);
        DeviceConfigPanel panel=new DeviceConfigPanel(dev);
        FavoritPanel panel2=new FavoritPanel(dev);
        vbox.getChildren().add(panel);
        vbox.getChildren().add(panel2);
        Scene scene=new Scene(vbox);
        vbox.setPrefHeight(970);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        if(line!=null)line.destroy();
        ms.destroy();
        super.stop();
    }
}

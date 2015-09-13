import images.MyResourceLoader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ua.pp.fairwind.communications.devices.hardwaredevices.favorit.FavoritCoreDeviceV1;
import ua.pp.fairwind.communications.lines.LineInterface;
import ua.pp.fairwind.communications.lines.SerialLine;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystemMultiDipatch;
import ua.pp.fairwind.communications.timeaction.PropertyTimer;
import ua.pp.fairwind.javafx.panels.devices.FavoritComplexPanel;

import java.util.List;

/**
 * Created by Сергей on 28.08.2015.
 */
public class testComplexFavoritFXPanel extends Application {
    final MessageSubSystem ms=new MessageSubSystemMultiDipatch();
    final List<LineInterface> lines=SerialLine.getSerialLines(ms,5000);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MyResourceLoader resloader=new MyResourceLoader();

        if(lines==null||lines.isEmpty()) {
            Platform.exit();
            return;
        }

        LineInterface oneLine=lines.get(0);
        FavoritCoreDeviceV1 dev=new FavoritCoreDeviceV1(1,"Favorit Ventil V1",null,"Test Description",ms);
        dev.setPrimerayLine(oneLine);
        FavoritComplexPanel panel=new FavoritComplexPanel(oneLine,dev, lines);
        panel.setId("mainPanel");
        Scene scene=new Scene(panel);
        scene.getStylesheets().addAll(resloader.getExternalResourceURILink("application.css"));

        //vbox.setPrefHeight(970);
        primaryStage.setTitle("UTILITY FAVORIT VENTIL");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        if(lines!=null)lines.stream().forEach(line->line.destroy());
        PropertyTimer.stopWork();
        ms.destroyService();
        super.stop();
    }
}

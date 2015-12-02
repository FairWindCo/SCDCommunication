package ua.fairwind.javafx;/**
 * Created by Сергей on 02.12.2015.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ua.pp.fairwind.communications.devices.hardwaredevices.dp5.DP5ConfigurationChanel;
import ua.pp.fairwind.javafx.panels.devices.dp5.DP5ParametersPanel;


public class TestDP5ConfigPanel extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane panel=new DP5ParametersPanel(new DP5ConfigurationChanel("test",null, DP5ConfigurationChanel.DEVICETYPE.DP5));
        Scene scene=new Scene(panel);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

package ua.pp.fairwind.javafx.controls.slidecheckbox;

/**
 * Created by Сергей on 28.08.2015.
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * Created by
 * User: hansolo
 * Date: 30.08.13
 * Time: 15:32
 */

public class Demo extends Application {

    @Override
    public void start(Stage stage) {
        SlideCheckBox checkBox = new SlideCheckBox();
        //checkBox.setScaleX(0.5);
        //checkBox.setScaleY(0.5);
        //checkBox.setPrefHeight(300);

        StackPane pane = new StackPane();
        pane.getChildren().addAll(checkBox);

        Scene scene = new Scene(pane, 200, 100);

        stage.setScene(scene);
        stage.setTitle("JavaFX FridayFun XVIII");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
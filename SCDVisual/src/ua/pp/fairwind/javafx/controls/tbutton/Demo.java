package ua.pp.fairwind.javafx.controls.tbutton;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Demo extends Application {
    private MyTButton control;

    @Override public void init() {
        control = new MyTButton();
        control.prefWidth(144);
        control.prefHeight(144);
        control.setText("Out 1");
        control.setOnSelect(selectEvent -> System.out.println("Button selected"));
        control.setOnDeselect(selectEvent -> System.out.println("Button deselected"));
    }

    @Override public void start(Stage stage) {
        StackPane pane = new StackPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.getChildren().setAll(control);

        Scene scene = new Scene(pane, 200, 200);

        stage.setTitle("JavaFX TButton");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
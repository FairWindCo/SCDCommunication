package ua.pp.fairwind.javafx.guiElements;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class ButtonPanel extends HBox {
    double height;

    public ButtonPanel(double height) {
        this(height, "buttomPanel");
    }

    public ButtonPanel(double height, String id) {
        super();
        setId(id);
        setHeight(height);
        setPrefHeight(height);
        setAlignment(Pos.BASELINE_LEFT);
        setPadding(new Insets(5));
        this.height = height;
        constructAdditionalElements();
    }

    protected void constructAdditionalElements() {
    }
}

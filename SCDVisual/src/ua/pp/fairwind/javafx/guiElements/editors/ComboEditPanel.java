package ua.pp.fairwind.javafx.guiElements.editors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import ua.pp.fairwind.communications.propertyes.software.stringlike.StringValuedPropertry;
import ua.pp.fairwind.io.javafx.propertys.StringPropertyFXAdapter;
import ua.pp.fairwind.javafx.guiElements.MyBaseResourceLoader;

public class ComboEditPanel extends Pane {
    private final StringValuedPropertry editCahnel;
    private final ComboBox<String> editor = new ComboBox<>();
    private MyBaseResourceLoader resloader;

    public ComboEditPanel(StringValuedPropertry editCahnel, MyBaseResourceLoader resloader) {
        super();
        this.editCahnel = editCahnel;
        this.resloader = resloader;
        formControl();
    }

    protected void formControl() {
        TilePane panel = new TilePane();
        panel.setPrefColumns(2);
        panel.setHgap(10);
        //panel.setSpacing(10f);
        Label lbl = new Label();
        if (editCahnel != null) {
            if (resloader != null) {
                String name = resloader.getStringEmpty(editCahnel.getName());
                if (name == null || name.isEmpty()) {
                    name = editCahnel.getDescription();
                    if (name == null || name.isEmpty()) {
                        name = editCahnel.getName();
                    }
                }
                lbl.setText(name);
            } else {
                lbl.setText(editCahnel.getName());
            }
            lbl.getStyleClass().add("formLabel");
            lbl.setTooltip(new Tooltip(editCahnel.getDescription()));
            StringPropertyFXAdapter adapter = new StringPropertyFXAdapter(editCahnel);
            //editCahnel.setValue("50");
            editor.valueProperty().bindBidirectional(adapter);
            editor.setTooltip(new Tooltip(editCahnel.getDiapason()));
            editor.setEditable(true);
            if (editCahnel.getCorrectValues() != null) {
                for (String correct : editCahnel.getCorrectValues()) {
                    editor.getItems().add(correct);
                }
            }
            editor.valueProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> obj, String oldvl, String newvl) {
                    //editCahnel.setValue(newvl);
                    //editor.setValue(editCahnel.getValue());
                    //System.out.println("EVENT"+oldvl+" "+newvl);
                    if (oldvl != null) {
                        if (newvl != null) {
                            String testval = editCahnel.getValue();
                            if (!newvl.equalsIgnoreCase(testval)) {
                                if (editCahnel.isCorrectValue(newvl)) {
                                    //editor.setValue(newvl);
                                } else {
                                    editor.setValue(testval);
                                }
                            }
                        } else {
                            editor.setValue(oldvl);
                        }
                    } else {
                        if (newvl != null) {
                            String testval = editCahnel.getValue();
                            if (!newvl.equalsIgnoreCase(testval)) {
                                editor.setValue(newvl);
                            }
                        }
                    }
                }
            });
        }
        panel.getChildren().add(lbl);
        panel.getChildren().add(editor);
        getChildren().add(panel);
    }
}

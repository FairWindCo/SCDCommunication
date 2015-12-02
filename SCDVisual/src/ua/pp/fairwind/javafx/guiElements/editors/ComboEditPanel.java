package ua.pp.fairwind.javafx.guiElements.editors;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import ua.pp.fairwind.communications.internatianalisation.I18N;
import ua.pp.fairwind.communications.propertyes.software.stringlike.StringValuedPropertry;
import ua.pp.fairwind.io.javafx.propertys.StringPropertyFXAdapter;

public class ComboEditPanel extends Pane {
    private final StringValuedPropertry editCahnel;
    private final ComboBox<String> editor = new ComboBox<>();

    public ComboEditPanel(StringValuedPropertry editCahnel) {
        super();
        this.editCahnel = editCahnel;
        formControl();
    }

    protected void formControl() {
        TilePane panel = new TilePane();
        panel.setPrefColumns(2);
        panel.setHgap(10);
        //panel.setSpacing(10f);
        Label lbl = new Label();
        if (editCahnel != null) {
                String name = I18N.getLocalizedString(editCahnel.getName());
                if (name == null || name.isEmpty()) {
                    name = editCahnel.getDescription();
                    if (name == null || name.isEmpty()) {
                        name = editCahnel.getName();
                    }
                }
                lbl.setText(name);
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
            //editor.setOnAction(a -> setupValue());
            //editor.setOnKeyReleased(event->setupValue());

            /*
            editor.valueProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> obj, String oldvl, String newvl) {
                    //editCahnel.setValue(newvl);
                    //editor.setValue(editCahnel.getValue());
                    System.out.println("EVENT" + oldvl + " " + newvl);
                    if (oldvl != null) {
                        if (newvl != null) {
                            String testval = editCahnel.getValue();
                            if (!newvl.equalsIgnoreCase(testval)) {
                                if (editCahnel.isCorrectValue(newvl)) {
                                    if (!newvl.contains("#"))
                                        editCahnel.setValue(newvl);
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
                            if (!editCahnel.isCorrectValue(newvl)) {
                                editor.setValue(testval);
                            } else {
                                if (!newvl.contains("#"))
                                    editCahnel.setValue(newvl);
                            }
                        }
                    }
                }
            });
            /**/
        }
        panel.getChildren().add(lbl);
        panel.getChildren().add(editor);
        getChildren().add(panel);
    }

    private void setupValue(){
        String value=editor.getValue();
        if (editCahnel.isCorrectValue(value)) {
            editCahnel.setValue(value);
        } else {
            String text=editCahnel.getValue();
            editor.setValue(text==null?"":text);
        }
    }
}

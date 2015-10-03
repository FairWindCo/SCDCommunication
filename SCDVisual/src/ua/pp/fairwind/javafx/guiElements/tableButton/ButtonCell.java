package ua.pp.fairwind.javafx.guiElements.tableButton;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import ua.pp.fairwind.javafx.I18N.I18N_FX;

/**
 * Created by Сергей on 02.10.2015.
 */
public class ButtonCell<T> extends TableCell<T, String> {
    final private Button cellButton;
    final private ButtonCellAction<T, String> action;
    final private String text;

    public ButtonCell(String text, ButtonCellAction<T, String> action) {
        cellButton = new Button();
        cellButton.setText(I18N_FX.getLocalizedString(text));
        this.text = text;
        this.action = action;
    }


    //Display button if the row is not empty
    @Override
    protected void updateItem(String t, boolean empty) {
        super.updateItem(t, empty);
        if (!empty) {
            final T record = (T) getTableRow().getItem();
            cellButton.setOnAction(event -> {
                action.action(record, t);
            });
            setGraphic(cellButton);
        } else { // you must always do the following in cell subclasses:
            setGraphic(null);
        }
    }

    public TableColumn<T, String> createButtonColumn(String columnname) {
        TableColumn<T, String> col_action = new TableColumn<>(I18N_FX.getLocalizedString(columnname));
        col_action.setCellValueFactory((p) -> new SimpleStringProperty(I18N_FX.getLocalizedString(text)));
        col_action.setCellFactory(p -> new ButtonCell(text, action));
        return col_action;
    }

    public TableColumn<T, String> createButtonColumn(String columnname, String text, ButtonCellAction<T, Boolean> action) {
        TableColumn<T, String> col_action = new TableColumn<>(I18N_FX.getLocalizedString(columnname));
        col_action.setCellValueFactory((p) -> new SimpleStringProperty(I18N_FX.getLocalizedString(text)));
        col_action.setCellFactory(p -> new ButtonCell(text, action));
        return col_action;
    }


}

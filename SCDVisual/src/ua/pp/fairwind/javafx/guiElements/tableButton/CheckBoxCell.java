package ua.pp.fairwind.javafx.guiElements.tableButton;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import ua.pp.fairwind.javafx.I18N.I18N_FX;

/**
 * Created by Сергей on 02.10.2015.
 */
public class CheckBoxCell<T> extends TableCell<T, Boolean> {
    final private CheckBox cellButton;
    final private ButtonCellAction<T, Boolean> action;
    final private ChackBoxConvertor<T, Boolean> convertor;
    final private String text;

    public CheckBoxCell(String text, ButtonCellAction<T, Boolean> action, ChackBoxConvertor<T, Boolean> convertor) {
        cellButton = new CheckBox();
        cellButton.setText(I18N_FX.getLocalizedString(text));
        this.text = text;
        this.action = action;
        this.convertor = convertor;
    }


    //Display button if the row is not empty
    @Override
    protected void updateItem(Boolean t, boolean empty) {
        super.updateItem(t, empty);
        if (!empty) {
            final T record = (T) getTableRow().getItem();
            cellButton.setOnAction(event -> {
                action.action(record, t);
            });
            cellButton.setSelected(t);
            setGraphic(cellButton);
        } else { // you must always do the following in cell subclasses:
            setGraphic(null);
        }
    }

    public TableColumn<T, Boolean> createButtonColumn(String columnname) {
        TableColumn<T, Boolean> col_action = new TableColumn<>(I18N_FX.getLocalizedString(columnname));
        col_action.setCellValueFactory((p) -> new SimpleBooleanProperty(convertor.convert(p.getValue())));
        col_action.setCellFactory(p -> new CheckBoxCell(text, action, convertor));
        return col_action;
    }

    public TableColumn<T, Boolean> createButtonColumn(String columnname, String text, ButtonCellAction<T, Boolean> action, ChackBoxConvertor<T, Boolean> convertor) {
        TableColumn<T, Boolean> col_action = new TableColumn<>(I18N_FX.getLocalizedString(columnname));
        col_action.setCellValueFactory((p) -> new SimpleBooleanProperty(convertor.convert(p.getValue())));
        col_action.setCellFactory(p -> new CheckBoxCell(text, action, convertor));
        return col_action;
    }


}

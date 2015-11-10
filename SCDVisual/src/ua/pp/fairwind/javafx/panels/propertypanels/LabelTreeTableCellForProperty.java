package ua.pp.fairwind.javafx.panels.propertypanels;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeTableCell;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;

/**
 * Created by Сергей on 08.11.2015.
 */
public class LabelTreeTableCellForProperty<T> extends TreeTableCell<AbstractProperty,T> {

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (getTreeTableRow() != null && getTreeTableRow().getTreeItem() != null && !empty) {
            AbstractProperty editedValue = getTreeTableRow().getTreeItem().getValue();
            if (editedValue != null) {
                Label label=new Label(editedValue.getName());
                label.getStyleClass().add("tree-view-label");
                Tooltip.install(label, new Tooltip(editedValue.getDescription()));
                super.setGraphic(label);
            } else {
                super.setText(item != null ? item.toString() :null);
                super.setGraphic(null);
            }
        } else {
            super.setText(null);
            super.setGraphic(null);
        }
    }
}

package ua.pp.fairwind.javafx.panels.propertypanels;

import javafx.scene.control.TreeTableCell;
import javafx.scene.layout.HBox;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.groups.GroupProperty;
import ua.pp.fairwind.javafx.VisualControls;

/**
 * Created by Сергей on 08.11.2015.
 */
public class ActionTreeTableCellForProperty<T> extends TreeTableCell<AbstractProperty,T> {

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (getTreeTableRow() != null && getTreeTableRow().getTreeItem() != null && !empty) {
            AbstractProperty editedValue = getTreeTableRow().getTreeItem().getValue();
            if (editedValue != null) {
                if (editedValue instanceof GroupProperty) {
                    HBox box=new HBox();
                    if(((GroupProperty) editedValue).isReadAccepted()){
                        box.getChildren().add(VisualControls.createReReadButton(editedValue));
                    }
                    if(((GroupProperty) editedValue).isWriteAccepted()){
                        box.getChildren().add(VisualControls.createReWriteButton(editedValue));
                    }
                    box.getChildren().add(VisualControls.createConfigureProppearty(editedValue));
                    super.setGraphic(box);
                } else if (editedValue instanceof ValueProperty) {
                    if(!((ValueProperty) editedValue).isNoControlProperty()) {
                        HBox box = new HBox();
                        if(((ValueProperty) editedValue).isReadAccepted()){
                            box.getChildren().add(VisualControls.createReReadButton(editedValue));
                        }
                        if(((ValueProperty) editedValue).isWriteAccepted()){
                            box.getChildren().add(VisualControls.createReWriteButton(editedValue));
                        }
                        box.getChildren().add(VisualControls.createConfigureProppearty(editedValue));
                        super.setGraphic(box);
                    } else {
                        super.setGraphic(null);
                    }
                    super.setText(null);

                } else {
                    super.setText(null);
                    super.setGraphic(null);
                }
            } else {
                super.setText(null);
                super.setGraphic(null);
            }
        } else {
            super.setText(null);
            super.setGraphic(null);
        }
    }
}

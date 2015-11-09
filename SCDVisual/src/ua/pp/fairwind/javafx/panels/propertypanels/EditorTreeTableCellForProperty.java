package ua.pp.fairwind.javafx.panels.propertypanels;

import javafx.scene.control.TreeTableCell;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.groups.GroupProperty;
import ua.pp.fairwind.communications.propertyes.software.*;
import ua.pp.fairwind.javafx.VisualControls;
import ua.pp.fairwind.javafx.guiElements.editorSoftProperty.*;

/**
 * Created by Сергей on 08.11.2015.
 */
public class EditorTreeTableCellForProperty<T> extends TreeTableCell<AbstractProperty,T> {


    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (getTreeTableRow() != null && getTreeTableRow().getTreeItem() != null && !empty) {
            AbstractProperty editedValue = getTreeTableRow().getTreeItem().getValue();
            if (editedValue != null) {
                if (editedValue instanceof GroupProperty) {
                    super.setText("GROUP PROPERTY");
                    super.setGraphic(null);
                } else if (editedValue instanceof ValueProperty) {

                    if (((ValueProperty) editedValue).isWriteAccepted()) {
                        if (editedValue instanceof SoftByteProperty) {
                            super.setText(null);
                            super.setGraphic(new SoftByteInputText((SoftByteProperty) editedValue));
                        } else if (editedValue instanceof SoftShortProperty) {
                            super.setText(null);
                            super.setGraphic(new SoftShortInputText((SoftShortProperty) editedValue));
                        } else if (editedValue instanceof SoftIntegerProperty) {
                            super.setText(null);
                            super.setGraphic(new SoftIntInputText((SoftIntegerProperty) editedValue));
                        } else if (editedValue instanceof SoftLongProperty) {
                            super.setText(null);
                            super.setGraphic(new SoftLongInputText((SoftLongProperty) editedValue));
                        } else if (editedValue instanceof SoftFloatProperty) {
                            super.setText(null);
                            super.setGraphic(new SoftFloatInputText((SoftFloatProperty) editedValue));
                        } else if (editedValue instanceof SoftDoubleProperty) {
                            super.setText(null);
                            super.setGraphic(new SoftDoubleInputText((SoftDoubleProperty) editedValue));
                        } else if (editedValue instanceof SoftBoolProperty) {
                            super.setText(null);
                            super.setGraphic(VisualControls.createLedIndicator((SoftBoolProperty) editedValue));
                        }
                    } else {
                        if (((ValueProperty) editedValue).getValue() != null) {
                            super.setText(((ValueProperty) editedValue).getValue().toString());
                        } else {
                            super.setText("null");
                        }
                        super.setGraphic(null);
                    }
                } else {
                    super.setText("NO EDITOR");
                    super.setGraphic(null);
                }
            } else {
                super.setText(null);
                super.setGraphic(null);
            }
        }
    }
}

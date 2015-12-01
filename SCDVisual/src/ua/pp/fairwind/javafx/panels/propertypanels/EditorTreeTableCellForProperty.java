package ua.pp.fairwind.javafx.panels.propertypanels;

import javafx.scene.control.TreeTableCell;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.markers.*;
import ua.pp.fairwind.communications.propertyes.groups.GroupProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftBoolProperty;
import ua.pp.fairwind.javafx.VisualControls;
import ua.pp.fairwind.javafx.guiElements.editorSoftProperty.*;

import java.util.HashMap;

/**
 * Created by Сергей on 08.11.2015.
 */
public class EditorTreeTableCellForProperty<T> extends TreeTableCell<AbstractProperty,T> {
    private final HashMap<String,CellControl> customControls;

    public EditorTreeTableCellForProperty(HashMap<String, CellControl> customControls) {
        this.customControls = customControls;
    }

    public EditorTreeTableCellForProperty() {
        this.customControls = null;
    }

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
                    if(customControls!=null){
                        String code=editedValue.getCodename();
                        CellControl control=customControls.get(code);
                        if(control!=null){
                            super.setText(null);
                            super.setGraphic(control.getControl(editedValue));
                        }
                    }
                    if (((ValueProperty) editedValue).isWriteAccepted()) {
                        if (editedValue instanceof ByteValueInterface) {
                            super.setText(null);
                            super.setGraphic(new SoftByteInputText((ValueProperty<Byte>) editedValue));
                        } else if (editedValue instanceof ShortValueInterface) {
                            super.setText(null);
                            super.setGraphic(new SoftShortInputText((ValueProperty<Short>) editedValue));
                        } else if (editedValue instanceof IntegerValueInterface) {
                            super.setText(null);
                            super.setGraphic(new SoftIntInputText((ValueProperty<Integer>) editedValue));
                        } else if (editedValue instanceof LongValueInterface) {
                            super.setText(null);
                            super.setGraphic(new SoftLongInputText((ValueProperty<Long>) editedValue));
                        } else if (editedValue instanceof FloatValueInterface) {
                            super.setText(null);
                            super.setGraphic(new SoftFloatInputText((ValueProperty<Float>) editedValue));
                        } else if (editedValue instanceof DoubleValueInterface) {
                            super.setText(null);
                            super.setGraphic(new SoftDoubleInputText((ValueProperty<Double>) editedValue));
                        } else if (editedValue instanceof SoftBoolProperty) {
                            super.setText(null);
                            if(((SoftBoolProperty) editedValue).isWriteAccepted()){
                                super.setGraphic(VisualControls.createSwitchIndicator((SoftBoolProperty) editedValue));
                            } else {
                                super.setGraphic(VisualControls.createLedIndicator((SoftBoolProperty) editedValue));
                            }
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
        }else{
            super.setText(null);
            super.setGraphic(null);
        }
    }
}

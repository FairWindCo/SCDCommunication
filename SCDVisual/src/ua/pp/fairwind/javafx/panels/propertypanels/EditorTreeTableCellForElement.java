package ua.pp.fairwind.javafx.panels.propertypanels;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import ua.pp.fairwind.communications.abstractions.ElementInterface;
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
public class EditorTreeTableCellForElement<T> extends TreeTableCell<ElementInterface,T> {
    private final HashMap<String,CellControl> customControls;
    private final boolean alwasEdit=false;

    public EditorTreeTableCellForElement(HashMap<String, CellControl> customControls) {
        this.customControls = customControls;
    }

    public EditorTreeTableCellForElement() {
        this.customControls = null;
    }

    private AbstractProperty editedProperty;

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            TreeItem<ElementInterface> elementItem=getTreeTableView().getFocusModel().getFocusedItem();
            if(elementItem!=null) {
                ElementInterface elementInterface = elementItem.getValue();
                if(elementInterface instanceof AbstractProperty){
                    super.startEdit();
                    editedProperty= (AbstractProperty) elementInterface;
                    getControl((AbstractProperty) elementInterface);
                }
            }
        } else {
            super.setText(null);
            super.setGraphic(null);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        if (!isEmpty()) {
            getView(editedProperty);
        } else {
            super.setText(getItem().toString());
            super.setGraphic(null);
        }
    }

    void getView(AbstractProperty editedValue){
        if (editedValue == null){
            super.setText(null);
            super.setGraphic(null);
        } else {
            if (editedValue instanceof GroupProperty) {
                super.setText("GROUP PROPERTY");
                super.setGraphic(null);
            } else if (editedValue instanceof ValueProperty) {
                if (editedValue instanceof SoftBoolProperty) {
                    super.setText(null);
                    super.setGraphic(VisualControls.createLedIndicator((SoftBoolProperty) editedValue));
                    return;
                }
                Object val=((ValueProperty) editedValue).getValue();
                super.setText(val == null ? "null" : val.toString());
                super.setGraphic(null);
            }
        }
    }

    void getControl(AbstractProperty editedValue){
        if (editedValue == null){
            super.setText(null);
            super.setGraphic(null);
        } else {
            if (editedValue instanceof GroupProperty) {
                super.setText("GROUP PROPERTY");
                super.setGraphic(null);
            } else if (editedValue instanceof ValueProperty) {
                if (customControls != null) {
                    String code = editedValue.getCodename();
                    CellControl control = customControls.get(code);
                    if (control != null) {
                        super.setText(null);
                        super.setGraphic(control.getControl(editedValue));
                    }
                }
                if (((ValueProperty) editedValue).isWriteAccepted() || alwasEdit) {
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
                        super.setGraphic(VisualControls.createLedIndicator((SoftBoolProperty) editedValue));
                    }
                } else {
                    if (editedValue instanceof SoftBoolProperty) {
                        super.setText(null);
                        super.setGraphic(VisualControls.createLedIndicator((SoftBoolProperty) editedValue));
                        return;
                    }
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
        }
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (getTreeTableRow() != null && getTreeTableRow().getTreeItem() != null && !empty) {
            ElementInterface ellement = getTreeTableRow().getTreeItem().getValue();
            if(ellement instanceof AbstractProperty){
                AbstractProperty editedValue = (AbstractProperty)ellement;
                if(isEditing()||alwasEdit) {
                    getControl(editedValue);
                }else {
                    getView(editedValue);
                }
            } else {
                super.setText(ellement.getDescription());
            }
        }else{
            super.setText(null);
            super.setGraphic(null);
        }
    }
}

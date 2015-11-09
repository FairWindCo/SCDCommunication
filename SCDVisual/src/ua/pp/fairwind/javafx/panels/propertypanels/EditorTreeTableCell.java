package ua.pp.fairwind.javafx.panels.propertypanels;

import javafx.scene.control.TreeTableCell;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.groups.GroupProperty;
import ua.pp.fairwind.communications.propertyes.software.*;
import ua.pp.fairwind.javafx.guiElements.editors.*;

/**
 * Created by Сергей on 08.11.2015.
 */
public class EditorTreeTableCell<T> extends TreeTableCell<AbstractProperty,T> {
    final ShortInputText shortEditor=new ShortInputText();
    final LongInputText longEditor=new LongInputText();
    final IntegerInputText intEditor=new IntegerInputText();
    final ByteInputText byteEditor=new ByteInputText();
    final FloatInputText floatEditor=new FloatInputText();

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if(getTreeTableRow()!=null &&  getTreeTableRow().getTreeItem()!=null) {
            AbstractProperty editedValue = getTreeTableRow().getTreeItem().getValue();
            if (editedValue != null) {
                if (editedValue instanceof GroupProperty) {
                    super.setText("GROUP PROPERTY");
                    super.setGraphic(null);
                } else if (editedValue instanceof SoftByteProperty) {
                    if (item != null && !empty) {
                        byteEditor.setValue((Byte) item);
                    } else {
                        byteEditor.setValue(null);
                    }
                    super.setText(null);
                    super.setGraphic(byteEditor);
                } else if (editedValue instanceof SoftShortProperty) {
                    if (item != null && !empty) {
                        shortEditor.setValue((Short) item);
                    } else {
                        shortEditor.setValue(null);
                    }
                    super.setText(null);
                    super.setGraphic(shortEditor);
                } else if (editedValue instanceof SoftIntegerProperty) {
                    if (item != null && !empty) {
                        intEditor.setValue((Integer) item);
                    } else {
                        intEditor.setValue(null);
                    }
                    super.setText(null);
                    super.setGraphic(intEditor);
                } else if (editedValue instanceof SoftLongProperty) {
                    if (item != null && !empty) {
                        longEditor.setValue((Long) item);
                    } else {
                        longEditor.setValue(null);
                    }
                    super.setText(null);
                    super.setGraphic(longEditor);
                } else if (editedValue instanceof SoftFloatProperty) {
                    if (item != null && !empty) {
                        floatEditor.setValue((Float) item);
                    } else {
                        floatEditor.setValue(null);
                    }
                    super.setText(null);
                    super.setGraphic(floatEditor);
                } else if (editedValue instanceof SoftDoubleProperty) {

                } else if (editedValue instanceof SoftBoolProperty) {
                }
            } else {
                super.setText("NO EDITOR");
                super.setGraphic(null);
            }
        }
        if(item!=null){
            /*
            if(item instanceof String){
                super.setText((String)item);
                super.setGraphic(null);
            } else if(item instanceof Byte){
                byteEditor.setValue((Byte) item);
                super.setGraphic(byteEditor);
            } else if(item instanceof Short){
                shortEditor.setValue(((Short) item).shortValue());
                super.setGraphic(shortEditor);
            }else if(item instanceof Integer){
                intEditor.setValue((Integer)item);
                super.setGraphic(intEditor);
            } else if(item instanceof Long){
                longEditor.setValue((Long) item);
                super.setGraphic(longEditor);
            } else if(item instanceof Float){
                floatEditor.setValue((Float) item);
                super.setGraphic(floatEditor);
            } else {
                super.setText("NO EDITOR");
                super.setGraphic(null);
            }
            /**/
        }

    }
}

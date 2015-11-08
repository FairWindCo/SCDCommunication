package ua.pp.fairwind.javafx.panels.propertypanels;

import javafx.scene.control.TreeTableCell;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.io.javafx.propertys.special.IntegerPropertyFXAdapterSpec;
import ua.pp.fairwind.io.javafx.propertys.special.LongPropertyFXAdapterSpec;
import ua.pp.fairwind.io.javafx.propertys.special.ShortPropertyFXAdapterSpec;
import ua.pp.fairwind.javafx.guiElements.editors.LongInputText;
import ua.pp.fairwind.javafx.guiElements.editors.ShortInputText;

/**
 * Created by Сергей on 08.11.2015.
 */
public class EditorTreeTableCell<T> extends TreeTableCell<AbstractProperty,T> {
    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if(item!=null){
            if(item instanceof ShortPropertyFXAdapterSpec){
                ShortInputText editor=new ShortInputText((ShortPropertyFXAdapterSpec)item);
                super.setGraphic(editor);
            }else if(item instanceof IntegerPropertyFXAdapterSpec){

            } else if(item instanceof LongPropertyFXAdapterSpec){
                LongInputText editor=new LongInputText((LongPropertyFXAdapterSpec)item);
                super.setGraphic(editor);
            }
            //super.setGraphic();
        }
    }
}

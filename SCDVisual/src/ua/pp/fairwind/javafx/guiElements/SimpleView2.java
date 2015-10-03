package ua.pp.fairwind.javafx.guiElements;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import ua.pp.fairwind.communications.propertyes.software.stringlike.StringIntegerValuedProperty;
import ua.pp.fairwind.javafx.guiElements.editors.ComboEditPanel;
import ua.pp.fairwind.javafx.guiElements.menu.PrefferedSize;
import ua.pp.fairwind.javafx.guiElements.windows.SimpleView;

public class SimpleView2 extends SimpleView {
    final private ComboEditPanel editPanel;
    final private StringIntegerValuedProperty confChanel;


    public SimpleView2(String title, Image icon, PrefferedSize prefferedSize,
                       final StringIntegerValuedProperty confChanel) {
        super(title, icon, prefferedSize);
        this.confChanel = confChanel;
        if (confChanel != null) {
            this.editPanel = new ComboEditPanel(confChanel, getResourceLoader());
        } else {
            this.editPanel = null;
        }
        if (confChanel != null) {
            confChanel.addChangeEventListener((event) -> System.out.println("CURENT VAL" + event.getNewValue()));
        }
    }


    @Override
    protected Node createView() {
        return editPanel == null ? new Pane() : editPanel;
    }


    public StringIntegerValuedProperty getConfChanel() {
        return confChanel;
    }


}

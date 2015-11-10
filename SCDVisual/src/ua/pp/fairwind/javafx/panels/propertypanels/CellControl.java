package ua.pp.fairwind.javafx.panels.propertypanels;

import javafx.scene.Node;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;

/**
 * Created by Сергей on 10.11.2015.
 */
public interface CellControl {
    Node getControl(AbstractProperty control);
}

package ua.pp.fairwind.javafx.guiElements.menu;

import javafx.scene.control.Dialog;

public class MenuConfigElementsDialog extends MenuConfigElements {
    final private Dialog<?> dialog;


    public MenuConfigElementsDialog(String name, String hint, Dialog<?> dialog) {
        super(name, hint);
        this.dialog = dialog;
    }

    public MenuConfigElementsDialog(String name, Dialog<?> dialog) {
        super(name);
        this.dialog = dialog;
    }

    public Dialog<?> getDialog() {
        return dialog;
    }
}

package ua.pp.fairwind.javafx.guiElements.menu;

import javafx.application.Platform;

public class MenuConfigElementsExitAction extends MenuConfigElementsAction {

    static final private ExecuteMenuInerface exit = (executor, elementsForexecute) -> {
        Platform.exit();
        System.exit(0);
    };

    public MenuConfigElementsExitAction(String name) {
        super(name, exit);
    }

    public MenuConfigElementsExitAction(String name, String hint) {
        super(name, hint, exit);
    }

    public MenuConfigElementsExitAction(String name, MenuExecutor executor) {
        super(name, executor, exit);
    }

    public MenuConfigElementsExitAction(String name, MenuExecutor executor, String hint) {
        super(name, executor, hint, exit);
    }
}

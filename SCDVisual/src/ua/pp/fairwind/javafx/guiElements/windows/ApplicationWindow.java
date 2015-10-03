package ua.pp.fairwind.javafx.guiElements.windows;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ua.pp.fairwind.javafx.guiElements.menu.MenuExecutor;

public interface ApplicationWindow extends ApplicationView {
    Scene getWindow();

    void changeView(ApplicationView newView);

    Node getTitlePanel();

    Node getButtomPanel();

    Node getViewPanel();

    Scene getMainView();

    void setMainMenuExecutor(MenuExecutor mainMenuExecutor);

    Stage formStage(Stage stage);

    void destroy();

    void close();
}

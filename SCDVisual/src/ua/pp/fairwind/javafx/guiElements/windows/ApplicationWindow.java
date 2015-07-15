package ua.pp.fairwind.javafx.guiElements.windows;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ua.pp.fairwind.javafx.guiElements.menu.MenuExecutor;

public interface ApplicationWindow extends ApplicationView {
	public Scene getWindow();
	public void changeView(ApplicationView newView);
	public Node getTitlePanel();
	public Node getButtomPanel();
	public Node getViewPanel();
	public Scene getMainView();
	public void setMainMenuExecutor(MenuExecutor mainMenuExecutor);
	public Stage formStage(Stage stage);
	public void destroy();
	public void close();
}

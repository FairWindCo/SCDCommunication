package ua.pp.fairwind.javafx.guiElements.windows;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import ua.pp.fairwind.javafx.guiElements.MyBaseResourceLoader;
import ua.pp.fairwind.javafx.guiElements.menu.MenuExecutor;
import ua.pp.fairwind.javafx.guiElements.menu.PrefferedSize;


public interface ApplicationView {
    String getTitle();

    Image getIcon();

    PrefferedSize getPrefferedSize();

    Scene getMainView();

    boolean isResizable();

    MyBaseResourceLoader getResourceLoader();

    void setResourceLoader(MyBaseResourceLoader resloader);

    void onShow(MenuExecutor parentExecutor);

    void onHide();

    long getID();

}

package ua.pp.fairwind.javafx.guiElements.windows;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import ua.pp.fairwind.javafx.guiElements.MyBaseResourceLoader;
import ua.pp.fairwind.javafx.guiElements.menu.MenuExecutor;
import ua.pp.fairwind.javafx.guiElements.menu.PrefferedSize;


public interface ApplicationView {
	public String getTitle();
	public Image getIcon();
	public PrefferedSize getPrefferedSize();
	public Scene getMainView();
	public boolean isResizable();
	public void setResourceLoader(MyBaseResourceLoader resloader);
	public MyBaseResourceLoader getResourceLoader();
	
	public void onShow(MenuExecutor parentExecutor);
	public void onHide();

    public long getID();
		
}

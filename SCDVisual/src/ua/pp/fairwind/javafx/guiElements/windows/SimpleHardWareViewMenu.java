package ua.pp.fairwind.javafx.guiElements.windows;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import ua.pp.fairwind.communications.SCADASystem;
import ua.pp.fairwind.javafx.guiElements.MyBaseResourceLoader;
import ua.pp.fairwind.javafx.guiElements.menu.PrefferedSize;

public class SimpleHardWareViewMenu extends SimpleMenuView implements ApplicationView {
	final protected SCADASystem centralsystem;
	final protected String deviceType;
	final protected String deviceName;

	public SimpleHardWareViewMenu(String menuItem, String menuHint, String title, Image icon, PrefferedSize prefferedSize, SCADASystem centralsystem, String deviceType, String deviceName) {
		super(menuItem, menuHint, title, icon, prefferedSize);
		this.centralsystem = centralsystem;
		this.deviceType = deviceType;
		this.deviceName = deviceName;
	}

	public SimpleHardWareViewMenu(String menuItem, String menuHint, String title, Image icon, PrefferedSize prefferedSize, Scene view, SCADASystem centralsystem, String deviceType, String deviceName) {
		super(menuItem, menuHint, title, icon, prefferedSize, view);
		this.centralsystem = centralsystem;
		this.deviceType = deviceType;
		this.deviceName = deviceName;
	}

	public SimpleHardWareViewMenu(String menuItem, String menuHint, String title, Image icon, SCADASystem centralsystem, String deviceType, String deviceName) {
		super(menuItem, menuHint, title, icon);
		this.centralsystem = centralsystem;
		this.deviceType = deviceType;
		this.deviceName = deviceName;
	}

	public SimpleHardWareViewMenu(String menuItem, String menuHint, String title, SCADASystem centralsystem, String deviceType, String deviceName) {
		super(menuItem, menuHint, title);
		this.centralsystem = centralsystem;
		this.deviceType = deviceType;
		this.deviceName = deviceName;
	}

	public SimpleHardWareViewMenu(String menuItem, String menuHint, SCADASystem centralsystem, String deviceType, String deviceName) {
		super(menuItem, menuHint);
		this.centralsystem = centralsystem;
		this.deviceType = deviceType;
		this.deviceName = deviceName;
	}

	public SimpleHardWareViewMenu(String menuItem, String menuHint, String title, Image icon, PrefferedSize prefferedSize, MyBaseResourceLoader resloader, SCADASystem centralsystem, String deviceType, String deviceName) {
		super(menuItem, menuHint, title, icon, prefferedSize, resloader);
		this.centralsystem = centralsystem;
		this.deviceType = deviceType;
		this.deviceName = deviceName;
	}

	public SimpleHardWareViewMenu(String menuItem, String menuHint, String title, Image icon, PrefferedSize prefferedSize, Scene view, MyBaseResourceLoader resloader, SCADASystem centralsystem, String deviceType, String deviceName) {
		super(menuItem, menuHint, title, icon, prefferedSize, view, resloader);
		this.centralsystem = centralsystem;
		this.deviceType = deviceType;
		this.deviceName = deviceName;
	}

	public SimpleHardWareViewMenu(String menuItem, String menuHint, String title, Image icon, MyBaseResourceLoader resloader, SCADASystem centralsystem, String deviceType, String deviceName) {
		super(menuItem, menuHint, title, icon, resloader);
		this.centralsystem = centralsystem;
		this.deviceType = deviceType;
		this.deviceName = deviceName;
	}

	public SimpleHardWareViewMenu(String menuItem, String menuHint, String title, MyBaseResourceLoader resloader, SCADASystem centralsystem, String deviceType, String deviceName) {
		super(menuItem, menuHint, title, resloader);
		this.centralsystem = centralsystem;
		this.deviceType = deviceType;
		this.deviceName = deviceName;
	}

	public SimpleHardWareViewMenu(String menuItem, String menuHint, MyBaseResourceLoader resloader, SCADASystem centralsystem, String deviceType, String deviceName) {
		super(menuItem, menuHint, resloader);
		this.centralsystem = centralsystem;
		this.deviceType = deviceType;
		this.deviceName = deviceName;
	}

	public SimpleHardWareViewMenu(String menuItem, String menuHint, String title, Image icon, PrefferedSize prefferedSize, boolean resizable, SCADASystem centralsystem, String deviceType, String deviceName) {
		super(menuItem, menuHint, title, icon, prefferedSize, resizable);
		this.centralsystem = centralsystem;
		this.deviceType = deviceType;
		this.deviceName = deviceName;
	}

	public SimpleHardWareViewMenu(String menuItem, String menuHint, String title, Image icon, PrefferedSize prefferedSize, Scene view, boolean resizable, SCADASystem centralsystem, String deviceType, String deviceName) {
		super(menuItem, menuHint, title, icon, prefferedSize, view, resizable);
		this.centralsystem = centralsystem;
		this.deviceType = deviceType;
		this.deviceName = deviceName;
	}

	public SimpleHardWareViewMenu(String menuItem, String title, Image icon, PrefferedSize prefferedSize, SCADASystem centralsystem, String deviceType, String deviceName) {
		super(menuItem, title, icon, prefferedSize);
		this.centralsystem = centralsystem;
		this.deviceType = deviceType;
		this.deviceName = deviceName;
	}

	public SimpleHardWareViewMenu(String menuItem, String title, Image icon, PrefferedSize prefferedSize, Scene view, SCADASystem centralsystem, String deviceType, String deviceName) {
		super(menuItem, title, icon, prefferedSize, view);
		this.centralsystem = centralsystem;
		this.deviceType = deviceType;
		this.deviceName = deviceName;
	}

	public SimpleHardWareViewMenu(String menuItem, String title, Image icon, SCADASystem centralsystem, String deviceType, String deviceName) {
		super(menuItem, title, icon);
		this.centralsystem = centralsystem;
		this.deviceType = deviceType;
		this.deviceName = deviceName;
	}

	public SimpleHardWareViewMenu(String menuItem, SCADASystem centralsystem, String deviceType, String deviceName) {
		super(menuItem);
		this.centralsystem = centralsystem;
		this.deviceType = deviceType;
		this.deviceName = deviceName;
	}

	public SimpleHardWareViewMenu(String menuItem, String title, Image icon, PrefferedSize prefferedSize, MyBaseResourceLoader resloader, SCADASystem centralsystem, String deviceType, String deviceName) {
		super(menuItem, title, icon, prefferedSize, resloader);
		this.centralsystem = centralsystem;
		this.deviceType = deviceType;
		this.deviceName = deviceName;
	}

	public SimpleHardWareViewMenu(String menuItem, String title, Image icon, PrefferedSize prefferedSize, Scene view, MyBaseResourceLoader resloader, SCADASystem centralsystem, String deviceType, String deviceName) {
		super(menuItem, title, icon, prefferedSize, view, resloader);
		this.centralsystem = centralsystem;
		this.deviceType = deviceType;
		this.deviceName = deviceName;
	}

	public SimpleHardWareViewMenu(String menuItem, String title, Image icon, MyBaseResourceLoader resloader, SCADASystem centralsystem, String deviceType, String deviceName) {
		super(menuItem, title, icon, resloader);
		this.centralsystem = centralsystem;
		this.deviceType = deviceType;
		this.deviceName = deviceName;
	}

	public SimpleHardWareViewMenu(String menuItem, MyBaseResourceLoader resloader, SCADASystem centralsystem, String deviceType, String deviceName) {
		super(menuItem, resloader);
		this.centralsystem = centralsystem;
		this.deviceType = deviceType;
		this.deviceName = deviceName;
	}

	public SimpleHardWareViewMenu(String menuItem, String title, Image icon, PrefferedSize prefferedSize, boolean resizable, SCADASystem centralsystem, String deviceType, String deviceName) {
		super(menuItem, title, icon, prefferedSize, resizable);
		this.centralsystem = centralsystem;
		this.deviceType = deviceType;
		this.deviceName = deviceName;
	}

	public SimpleHardWareViewMenu(String menuItem, String title, Image icon, PrefferedSize prefferedSize, Scene view, boolean resizable, SCADASystem centralsystem, String deviceType, String deviceName) {
		super(menuItem, title, icon, prefferedSize, view, resizable);
		this.centralsystem = centralsystem;
		this.deviceType = deviceType;
		this.deviceName = deviceName;
	}




}

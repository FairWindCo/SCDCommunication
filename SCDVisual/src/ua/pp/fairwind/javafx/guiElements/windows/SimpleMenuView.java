package ua.pp.fairwind.javafx.guiElements.windows;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import ua.pp.fairwind.javafx.guiElements.MyBaseResourceLoader;
import ua.pp.fairwind.javafx.guiElements.menu.MenuConfigElementsAbstractForm;
import ua.pp.fairwind.javafx.guiElements.menu.MenuExecutor;
import ua.pp.fairwind.javafx.guiElements.menu.PrefferedSize;

public class SimpleMenuView extends MenuConfigElementsAbstractForm {
    static private long curentID = 0;
    final private long id = curentID++;
    protected PrefferedSize prefferedSize;
    protected Scene view;
    private String title;
    private Image icon;
    private boolean resizable = true;
    private MenuExecutor executr;
    private MyBaseResourceLoader resloader;

    public SimpleMenuView(String menuItem, String menuHint, String title, Image icon, PrefferedSize prefferedSize) {
        super(menuItem, menuHint);
        this.title = title;
        this.icon = icon;
        this.prefferedSize = prefferedSize;
    }

    public SimpleMenuView(String menuItem, String menuHint, String title, Image icon, PrefferedSize prefferedSize,
                          Scene view) {
        super(menuItem, menuHint);
        this.title = title;
        this.icon = icon;
        this.prefferedSize = prefferedSize;
        this.view = view;
    }

    public SimpleMenuView(String menuItem, String menuHint, String title, Image icon) {
        super(menuItem, menuHint);
        this.title = title;
        this.icon = icon;
    }

    public SimpleMenuView(String menuItem, String menuHint, String title) {
        super(menuItem, menuHint);
        this.title = title;
    }

    public SimpleMenuView(String menuItem, String menuHint) {
        super(menuItem, menuHint);
    }

    public SimpleMenuView(String menuItem, String menuHint, String title, Image icon, PrefferedSize prefferedSize, MyBaseResourceLoader resloader) {
        super(menuItem, menuHint);
        this.title = title;
        this.icon = icon;
        this.prefferedSize = prefferedSize;
        this.resloader = resloader;
    }

    public SimpleMenuView(String menuItem, String menuHint, String title, Image icon, PrefferedSize prefferedSize,
                          Scene view, MyBaseResourceLoader resloader) {
        super(menuItem, menuHint);
        this.title = title;
        this.icon = icon;
        this.prefferedSize = prefferedSize;
        this.view = view;
        this.resloader = resloader;
    }

    public SimpleMenuView(String menuItem, String menuHint, String title, Image icon, MyBaseResourceLoader resloader) {
        super(menuItem, menuHint);
        this.title = title;
        this.icon = icon;
        this.resloader = resloader;
    }

    public SimpleMenuView(String menuItem, String menuHint, String title, MyBaseResourceLoader resloader) {
        super(menuItem, menuHint);
        this.title = title;
        this.resloader = resloader;
    }

    public SimpleMenuView(String menuItem, String menuHint, MyBaseResourceLoader resloader) {
        super(menuItem, menuHint);
        this.resloader = resloader;
    }

    public SimpleMenuView(String menuItem, String menuHint, String title, Image icon, PrefferedSize prefferedSize,
                          boolean resizable) {
        super(menuItem, menuHint);
        this.title = title;
        this.icon = icon;
        this.prefferedSize = prefferedSize;
        this.resizable = resizable;
    }

    public SimpleMenuView(String menuItem, String menuHint, String title, Image icon, PrefferedSize prefferedSize,
                          Scene view, boolean resizable) {
        super(menuItem, menuHint);
        this.title = title;
        this.icon = icon;
        this.prefferedSize = prefferedSize;
        this.view = view;
        this.resizable = resizable;
    }

    ///--------
    public SimpleMenuView(String menuItem, String title, Image icon, PrefferedSize prefferedSize) {
        super(menuItem);
        this.title = title;
        this.icon = icon;
        this.prefferedSize = prefferedSize;
    }

    public SimpleMenuView(String menuItem, String title, Image icon, PrefferedSize prefferedSize,
                          Scene view) {
        super(menuItem);
        this.title = title;
        this.icon = icon;
        this.prefferedSize = prefferedSize;
        this.view = view;
    }

    public SimpleMenuView(String menuItem, String title, Image icon) {
        super(menuItem);
        this.title = title;
        this.icon = icon;
    }

    public SimpleMenuView(String menuItem) {
        super(menuItem);
    }

    public SimpleMenuView(String menuItem, String title, Image icon, PrefferedSize prefferedSize, MyBaseResourceLoader resloader) {
        super(menuItem);
        this.title = title;
        this.icon = icon;
        this.prefferedSize = prefferedSize;
        this.resloader = resloader;
    }

    public SimpleMenuView(String menuItem, String title, Image icon, PrefferedSize prefferedSize,
                          Scene view, MyBaseResourceLoader resloader) {
        super(menuItem);
        this.title = title;
        this.icon = icon;
        this.prefferedSize = prefferedSize;
        this.view = view;
        this.resloader = resloader;
    }

    public SimpleMenuView(String menuItem, String title, Image icon, MyBaseResourceLoader resloader) {
        super(menuItem);
        this.title = title;
        this.icon = icon;
        this.resloader = resloader;
    }

    public SimpleMenuView(String menuItem, MyBaseResourceLoader resloader) {
        super(menuItem);
        this.resloader = resloader;
    }

    public SimpleMenuView(String menuItem, String title, Image icon, PrefferedSize prefferedSize,
                          boolean resizable) {
        super(menuItem);
        this.title = title;
        this.icon = icon;
        this.prefferedSize = prefferedSize;
        this.resizable = resizable;
    }

    public SimpleMenuView(String menuItem, String title, Image icon, PrefferedSize prefferedSize,
                          Scene view, boolean resizable) {
        super(menuItem);
        this.title = title;
        this.icon = icon;
        this.prefferedSize = prefferedSize;
        this.view = view;
        this.resizable = resizable;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public long getID() {
        return id;
    }

    protected MenuExecutor getExecutr() {
        return executr;
    }

    @Override
    public PrefferedSize getPrefferedSize() {
        return prefferedSize;
    }

    public void setPrefferedSize(PrefferedSize prefferedSize) {
        this.prefferedSize = prefferedSize;
    }

    protected Node createView() {
        return new Group();
    }

    @Override
    public Scene getMainView() {
        if (view == null) {

            view = new Scene((Parent) createView());
        }
        return view;
    }

    @Override
    public boolean isResizable() {
        return resizable;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }

    public void closeWindow() {
        if (executr != null) executr.closeView(this);
    }

    @Override
    public void onShow(MenuExecutor executor) {
        this.executr = executor;
    }

    @Override
    public void onHide() {
    }

    @Override
    public MyBaseResourceLoader getResourceLoader() {
        return resloader;
    }

    @Override
    public void setResourceLoader(MyBaseResourceLoader resloader) {
        this.resloader = resloader;

    }


}

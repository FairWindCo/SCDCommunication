package ua.pp.fairwind.javafx.guiElements.menu;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import ua.pp.fairwind.javafx.guiElements.MyBaseResourceLoader;
import ua.pp.fairwind.javafx.guiElements.windows.ApplicationView;

public class MenuConfigElementsForm extends MenuConfigElementsAbstractForm {
    final private ApplicationView forms;

    public MenuConfigElementsForm(String name, String hint, Modality modality, ApplicationView forms) {
        super(name, hint, modality);
        if (forms == null) throw new RuntimeException("form can`t be NULL!!");
        this.forms = forms;
    }

    public MenuConfigElementsForm(String name, Modality modality, ApplicationView forms) {
        super(name, modality);
        if (forms == null) throw new RuntimeException("form can`t be NULL!!");
        this.forms = forms;
    }

    public MenuConfigElementsForm(String name, String hint, ApplicationView forms) {
        super(name, hint);
        if (forms == null) throw new RuntimeException("form can`t be NULL!!");
        this.forms = forms;
    }

    public MenuConfigElementsForm(String name, ApplicationView forms) {
        super(name);
        if (forms == null) throw new RuntimeException("form can`t be NULL!!");
        this.forms = forms;
    }

    @Override
    public String getTitle() {
        return forms.getTitle();
    }

    @Override
    public Image getIcon() {
        return forms.getIcon();
    }

    @Override
    public PrefferedSize getPrefferedSize() {
        return forms.getPrefferedSize();
    }

    @Override
    public Scene getMainView() {
        return forms.getMainView();
    }

    @Override
    public boolean isResizable() {
        return forms.isResizable();
    }

    @Override
    public MyBaseResourceLoader getResourceLoader() {
        return forms.getResourceLoader();
    }

    @Override
    public void setResourceLoader(MyBaseResourceLoader resloader) {
        forms.setResourceLoader(resloader);
    }

    @Override
    public void onShow(MenuExecutor parentExecutor) {
        forms.onShow(parentExecutor);
    }

    @Override
    public void onHide() {
        forms.onHide();
    }

    @Override
    public long getID() {
        return forms.getID();
    }
}

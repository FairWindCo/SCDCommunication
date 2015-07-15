package ua.pp.fairwind.javafx.guiElements.windows;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import ua.pp.fairwind.io.systems.SCADAControl;
import ua.pp.fairwind.javafx.guiElements.MyBaseResourceLoader;
import ua.pp.fairwind.javafx.guiElements.menu.PrefferedSize;

/**
 * Created by Сергей on 23.09.2014.
 */
public class HardwareView extends SimpleView {
    final protected SCADAControl control;

    public HardwareView(String title, Image icon, PrefferedSize prefferedSize, Scene view, boolean resizable, SCADAControl control) {
        super(title, icon, prefferedSize, view, resizable);
        this.control = control;
    }

    public HardwareView(String title, Image icon, PrefferedSize prefferedSize, SCADAControl control) {
        super(title, icon, prefferedSize);
        this.control = control;
    }

    public HardwareView(String title, Image icon, PrefferedSize prefferedSize, Scene view, SCADAControl control) {
        super(title, icon, prefferedSize, view);
        this.control = control;
    }

    public HardwareView(String title, Image icon, SCADAControl control) {
        super(title, icon);
        this.control = control;
    }

    public HardwareView(String title, SCADAControl control) {
        super(title);
        this.control = control;
    }

    public HardwareView(SCADAControl control) {
        this.control = control;
    }

    public HardwareView(String title, Image icon, PrefferedSize prefferedSize, MyBaseResourceLoader resloader, SCADAControl control) {
        super(title, icon, prefferedSize, resloader);
        this.control = control;
    }

    public HardwareView(String title, Image icon, PrefferedSize prefferedSize, Scene view, MyBaseResourceLoader resloader, SCADAControl control) {
        super(title, icon, prefferedSize, view, resloader);
        this.control = control;
    }

    public HardwareView(String title, Image icon, MyBaseResourceLoader resloader, SCADAControl control) {
        super(title, icon, resloader);
        this.control = control;
    }

    public HardwareView(String title, MyBaseResourceLoader resloader, SCADAControl control) {
        super(title, resloader);
        this.control = control;
    }

    public HardwareView(MyBaseResourceLoader resloader, SCADAControl control) {
        super(resloader);
        this.control = control;
    }

    public HardwareView(String title, Image icon, PrefferedSize prefferedSize, boolean resizable, SCADAControl control) {
        super(title, icon, prefferedSize, resizable);
        this.control = control;
    }

}

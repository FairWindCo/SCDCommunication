package ua.pp.fairwind.javafx.guiElements.windows;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import ua.pp.fairwind.communications.SCADASystem;
import ua.pp.fairwind.javafx.guiElements.MyBaseResourceLoader;
import ua.pp.fairwind.javafx.guiElements.menu.PrefferedSize;

/**
 * Created by Сергей on 23.09.2014.
 */
public class HardwareView extends SimpleView {
    final protected SCADASystem control;

    public HardwareView(String title, Image icon, PrefferedSize prefferedSize, Scene view, boolean resizable, SCADASystem control) {
        super(title, icon, prefferedSize, view, resizable);
        this.control = control;
    }

    public HardwareView(String title, Image icon, PrefferedSize prefferedSize, SCADASystem control) {
        super(title, icon, prefferedSize);
        this.control = control;
    }

    public HardwareView(String title, Image icon, PrefferedSize prefferedSize, Scene view, SCADASystem control) {
        super(title, icon, prefferedSize, view);
        this.control = control;
    }

    public HardwareView(String title, Image icon, SCADASystem control) {
        super(title, icon);
        this.control = control;
    }

    public HardwareView(String title, SCADASystem control) {
        super(title);
        this.control = control;
    }

    public HardwareView(SCADASystem control) {
        this.control = control;
    }

    public HardwareView(String title, Image icon, PrefferedSize prefferedSize, MyBaseResourceLoader resloader, SCADASystem control) {
        super(title, icon, prefferedSize, resloader);
        this.control = control;
    }

    public HardwareView(String title, Image icon, PrefferedSize prefferedSize, Scene view, MyBaseResourceLoader resloader, SCADASystem control) {
        super(title, icon, prefferedSize, view, resloader);
        this.control = control;
    }

    public HardwareView(String title, Image icon, MyBaseResourceLoader resloader, SCADASystem control) {
        super(title, icon, resloader);
        this.control = control;
    }

    public HardwareView(String title, MyBaseResourceLoader resloader, SCADASystem control) {
        super(title, resloader);
        this.control = control;
    }

    public HardwareView(MyBaseResourceLoader resloader, SCADASystem control) {
        super(resloader);
        this.control = control;
    }

    public HardwareView(String title, Image icon, PrefferedSize prefferedSize, boolean resizable, SCADASystem control) {
        super(title, icon, prefferedSize, resizable);
        this.control = control;
    }

}

package ua.pp.fairwind.javafx.panels;

import javafx.scene.layout.Pane;

public class SubWindow extends Pane {
    String keyString;
    String titleString;
    String hoverString;
    String beanName;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getKeyString() {
        return keyString;
    }

    public void setKeyString(String keyString) {
        this.keyString = keyString;
    }

    public String getTitleString() {
        return titleString;
    }

    public void setTitleString(String titleString) {
        this.titleString = titleString;
    }

    public String getHoverString() {
        return hoverString;
    }

    public void setHoverString(String hoverString) {
        this.hoverString = hoverString;
    }


}

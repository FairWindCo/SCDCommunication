package ua.pp.fairwind.javafx.panels.devices.bdmg;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import ua.pp.fairwind.communications.devices.hardwaredevices.positron.BDMG04;
import ua.pp.fairwind.communications.propertyes.software.SoftFloatProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;
import ua.pp.fairwind.io.javafx.propertys.special.ShortPropertyFXAdapterSpec;
import ua.pp.fairwind.javafx.I18N.I18N_FX;
import ua.pp.fairwind.javafx.panels.TupicalPanels;
import ua.pp.fairwind.javafx.panels.propertypanels.PropertyEditorPanel;

/**
 * Created by Сергей on 27.08.2015.
 */
public class BDMGPanel extends HBox {
    final private BDMG04 device;
    final private TabPane tabs = new TabPane();

    public BDMGPanel(BDMG04 device) {
        super();
        this.device = device;
        initControl();
    }

    public static int setChanelControlRO(GridPane grid, SoftFloatProperty chanel, String name, int rowindex, int col) {

        return rowindex;
    }

    public static ComboBox<Short> createAddressSelect(SoftShortProperty addressProperty) {
        ComboBox<Short> box = new ComboBox<>();
        for (int i = 0; i < 256; i++) box.getItems().add((short) i);
        box.valueProperty().bindBidirectional(new ShortPropertyFXAdapterSpec(addressProperty));
        return box;
    }

    private void intiStatusPane() {
        final Tab initTab = new Tab(I18N_FX.getLocalizedString("STASUS"));
        tabs.getTabs().add(initTab);
        initTab.setClosable(false);
        initTab.setContent(TupicalPanels.createDeviceStatusPane(device));
    }

    private void intiCONFIGPane() {
        final Tab initTab = new Tab(I18N_FX.getLocalizedString("KOEFICIENTS"));
        tabs.getTabs().add(initTab);
        initTab.setClosable(false);
        Platform.runLater(() -> {
            final GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setId("formGrid");
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(10, 10, 10, 10));
            PropertyEditorPanel ep=new PropertyEditorPanel(device.getConfiguration());
            ScrollPane scrol = new ScrollPane(ep);
            scrol.setFitToWidth(true);
            scrol.setFitToHeight(true);
            initTab.setContent(scrol);
        });
    }

    private void intiDeviceControlPane() {
        final Tab initTab = new Tab(I18N_FX.getLocalizedString("DEVICE_INFO"));
        tabs.getTabs().add(initTab);
        initTab.setClosable(false);
        Platform.runLater(() -> {
            final GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setId("formGrid");
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(10, 10, 10, 10));

            PropertyEditorPanel ep=new PropertyEditorPanel(device.getState(),device.getDeviceInfo());
            ScrollPane scrol = new ScrollPane(ep);
            scrol.setFitToWidth(true);
            scrol.setFitToHeight(true);
            initTab.setContent(scrol);
        });
    }

    private void intiDeviceConfigPane() {
        final Tab initTab = new Tab(I18N_FX.getLocalizedString("SETUP"));
        tabs.getTabs().add(initTab);
        initTab.setClosable(false);
        Platform.runLater(() -> {
            final GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setId("formGrid");
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(10, 10, 10, 10));
            PropertyEditorPanel ep=new PropertyEditorPanel(device.getBootparam());
            ScrollPane scrol = new ScrollPane(ep);
            scrol.setFitToWidth(true);
            scrol.setFitToHeight(true);
            initTab.setContent(scrol);
        });
    }

    private void initControl() {
        setAlignment(Pos.CENTER);
        intiDeviceControlPane();
        intiDeviceConfigPane();
        intiCONFIGPane();
        intiStatusPane();
        tabs.setPrefHeight(430);
        getChildren().add(tabs);
    }

}

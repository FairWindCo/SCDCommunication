package ua.pp.fairwind.javafx.panels.devices.bdmg;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import ua.pp.fairwind.communications.devices.hardwaredevices.positron.BDMG04;
import ua.pp.fairwind.communications.propertyes.software.SoftFloatProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;
import ua.pp.fairwind.io.javafx.propertys.special.ShortPropertyFXAdapterSpec;
import ua.pp.fairwind.javafx.I18N.I18N_FX;
import ua.pp.fairwind.javafx.panels.TupicalPanels;
import ua.pp.fairwind.javafx.panels.propertypanels.PropertyEditorPanel;

import java.io.File;

/**
 * Created by Сергей on 27.08.2015.
 */
public class BDMGPanel extends VBox {
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
            PropertyEditorPanel ep = new PropertyEditorPanel(device.getConfiguration());

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
            PropertyEditorPanel ep = new PropertyEditorPanel(device.getBootparam());
            ScrollPane scrol = new ScrollPane(ep);
            scrol.setFitToWidth(true);
            scrol.setFitToHeight(true);
            initTab.setContent(scrol);
        });
    }

    private Node intiCommandPanel() {
        HBox box=new HBox();
        Button loadBtn=new Button("LOAD");
        Button saveBtn=new Button("SAVE");
        loadBtn.setOnAction(a->{
            FileChooser fileChooser=new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BINARY DATA","*.bhex"));
            File file=fileChooser.showOpenDialog(null);
            device.loadBinaryParameters(file);
        });
        saveBtn.setOnAction(a->{
            FileChooser fileChooser=new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BINARY DATA","*.bhex"));
            File file=fileChooser.showSaveDialog(null);
            device.saveBinaryParameters(file);
        });
        box.getChildren().addAll(loadBtn,saveBtn);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private void initControl() {
        setAlignment(Pos.CENTER);
        intiDeviceControlPane();
        intiDeviceConfigPane();
        intiCONFIGPane();
        intiStatusPane();
        tabs.setPrefHeight(600);
        getChildren().add(tabs);
        getChildren().add(intiCommandPanel());
    }

}

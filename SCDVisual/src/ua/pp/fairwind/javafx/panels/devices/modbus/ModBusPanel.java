package ua.pp.fairwind.javafx.panels.devices.modbus;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import ua.pp.fairwind.communications.devices.hardwaredevices.modbus.ModBusDevice;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;
import ua.pp.fairwind.communications.utils.EllementsCreator;
import ua.pp.fairwind.io.javafx.propertys.special.ShortPropertyFXAdapterSpec;
import ua.pp.fairwind.javafx.I18N.I18N_FX;
import ua.pp.fairwind.javafx.panels.TupicalPanels;
import ua.pp.fairwind.javafx.panels.dialogs.AddPropertyDialog;
import ua.pp.fairwind.javafx.panels.propertypanels.PropertyEditorPanel;

import java.io.File;

/**
 * Created by Сергей on 27.08.2015.
 */
public class ModBusPanel extends VBox {
    final private ModBusDevice device;
    final private TabPane tabs = new TabPane();
    final private EllementsCreator creator;
    PropertyEditorPanel ep;

    public ModBusPanel(ModBusDevice device,EllementsCreator creator) {
        super();
        this.creator=creator;
        this.device = device;
        initControl();
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


    private void intiDeviceConfigPane() {
        final Tab initTab = new Tab(I18N_FX.getLocalizedString("SETUP"));
        tabs.getTabs().add(initTab);
        initTab.setClosable(false);
        ep = new PropertyEditorPanel(device.getDeviceProperties());
        ScrollPane scrol = new ScrollPane(ep);
        scrol.setFitToWidth(true);
        scrol.setFitToHeight(true);
        initTab.setContent(scrol);
    }

    private Node intiCommandPanel() {
        HBox box=new HBox();
        Button addBtn=new Button("ADD");
        Button removeBtn=new Button("REMOVE");
        Button loadBtn=new Button("LOAD");
        Button saveBtn=new Button("SAVE");
        addBtn.setOnAction(a->{
            if(ep!=null){

                AbstractProperty prp=ep.getSelectedValue();
                if(ep!=null){
                    AddPropertyDialog.addNewProperty(prp,creator);
                    ep.checkSelectedValue();
                }
            }
        });
        loadBtn.setOnAction(a->{
            FileChooser fileChooser=new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BINARY DATA","*.bhex"));
            File file=fileChooser.showOpenDialog(null);
            device.loadBinaryParameters(file);
        });
        saveBtn.setOnAction(a -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BINARY DATA", "*.bhex"));
            File file = fileChooser.showSaveDialog(null);
            device.saveBinaryParameters(file);
        });
        box.getChildren().addAll(addBtn,removeBtn,loadBtn, saveBtn);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private void initControl() {
        setAlignment(Pos.CENTER);
        intiDeviceConfigPane();
        intiStatusPane();
        tabs.setPrefHeight(600);
        getChildren().add(tabs);
        getChildren().add(intiCommandPanel());
    }


}


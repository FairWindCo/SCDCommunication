package ua.pp.fairwind.javafx.panels.devices;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ua.pp.fairwind.communications.devices.hardwaredevices.favorit.FavoritCoreDeviceV1;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;
import ua.pp.fairwind.javafx.I18N.I18N_FX;
import ua.pp.fairwind.javafx.VisualControls;
import ua.pp.fairwind.javafx.panels.TupicalPanels;


/**
 * Created by Сергей on 27.08.2015.
 */
public class FavoritPanel extends HBox {
    final private FavoritCoreDeviceV1 device;
    final private TabPane tabs = new TabPane();

    public FavoritPanel(FavoritCoreDeviceV1 device) {
        super();
        this.device = device;
        initControl();
    }

    private void intiStatusPane() {
        final Tab initTab = new Tab(I18N_FX.getLocalizedString("STASUS"));
        tabs.getTabs().add(initTab);
        initTab.setClosable(false);
        initTab.setContent(TupicalPanels.createDeviceStatusPane(device));
    }


    private void intiDIPane() {
        final Tab initTab = new Tab(I18N_FX.getLocalizedString("DIGITAL_IN"));
        tabs.getTabs().add(initTab);
        initTab.setClosable(false);
        Platform.runLater(() -> {
            final GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setId("formGrid");
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(10, 10, 10, 10));
            int rowIndex = 0;
            TupicalPanels.setDIChanelControl(grid, device.getDigitalInChanelN1(), I18N_FX.getLocalizedString("DI1"), rowIndex++, 0);
            TupicalPanels.setDIChanelControl(grid, device.getDigitalInChanelN2(), I18N_FX.getLocalizedString("DI2"), rowIndex++, 0);
            TupicalPanels.setDIChanelControl(grid, device.getDigitalInChanelN3(), I18N_FX.getLocalizedString("DI3"), rowIndex++, 0);
            TupicalPanels.setDIChanelControl(grid, device.getDigitalInChanelN4(), I18N_FX.getLocalizedString("DI4"), rowIndex++, 0);
            TupicalPanels.setDIChanelControl(grid, device.getDigitalInChanelN5(), I18N_FX.getLocalizedString("DI5"), rowIndex++, 0);
            TupicalPanels.setDIChanelControl(grid, device.getDigitalInChanelN6(), I18N_FX.getLocalizedString("DI6"), rowIndex++, 0);
            grid.add(VisualControls.createCommandExecuteButton(device.getReadAllDI()), 0, rowIndex++, 3, 1);
            ScrollPane scrol = new ScrollPane(grid);
            scrol.setFitToWidth(true);
            scrol.setFitToHeight(true);
            initTab.setContent(scrol);
        });
    }

    private void intiDOPane() {
        final Tab initTab = new Tab(I18N_FX.getLocalizedString("DIGITAL_OUT"));
        tabs.getTabs().add(initTab);
        initTab.setClosable(false);
        Platform.runLater(() -> {
            final GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setId("formGrid");
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(10, 10, 10, 10));
            int rowIndex = 0;
            TupicalPanels.setDOChanelControl(grid, device.getDigitalOutChanelN1(), I18N_FX.getLocalizedString("DO1"), rowIndex++, 0);
            TupicalPanels.setDOChanelControl(grid, device.getDigitalOutChanelN2(), I18N_FX.getLocalizedString("DO2"), rowIndex++, 0);
            TupicalPanels.setDOChanelControl(grid, device.getDigitalOutChanelN3(), I18N_FX.getLocalizedString("DO3"), rowIndex++, 0);
            TupicalPanels.setDOChanelControl(grid, device.getDigitalOutChanelN4(), I18N_FX.getLocalizedString("DO4"), rowIndex++, 0);
            TupicalPanels.setDOChanelControl(grid, device.getDigitalOutChanelN5(), I18N_FX.getLocalizedString("DO5"), rowIndex++, 0);
            TupicalPanels.setDOChanelControl(grid, device.getDigitalOutChanelN6(), I18N_FX.getLocalizedString("DO6"), rowIndex++, 0);
            grid.add(VisualControls.createCommandExecuteButton(device.getReadAllDO()), 0, rowIndex, 3, 1);
            grid.add(VisualControls.createCommandExecuteButton(device.getWriteAllDO()), 4, rowIndex++, 3, 1);
            ScrollPane scrol = new ScrollPane(grid);
            scrol.setFitToWidth(true);
            scrol.setFitToHeight(true);
            initTab.setContent(scrol);
        });
    }

    private void intiAOPane() {
        final Tab initTab = new Tab(I18N_FX.getLocalizedString("ANALOG_OUT"));
        tabs.getTabs().add(initTab);
        initTab.setClosable(false);
        Platform.runLater(() -> {
            final GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setId("formGrid");
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(10, 10, 10, 10));
            int rowIndex = 0;
            rowIndex = TupicalPanels.setAOChanelControl(grid, device.getAnalogOutChanelN1(), I18N_FX.getLocalizedString("AO1"), rowIndex++, 0);
            rowIndex = TupicalPanels.setAOChanelControl(grid, device.getAnalogOutChanelN2(), I18N_FX.getLocalizedString("AO2"), rowIndex++, 0);
            rowIndex = TupicalPanels.setAOChanelControl(grid, device.getAnalogOutChanelN3(), I18N_FX.getLocalizedString("AO3"), rowIndex++, 0);
            rowIndex = TupicalPanels.setAOChanelControl(grid, device.getAnalogOutChanelN4(), I18N_FX.getLocalizedString("AO4"), rowIndex++, 0);
            grid.add(VisualControls.createCommandExecuteButton(device.getReadAllAO()), 0, rowIndex, 3, 1);
            grid.add(VisualControls.createCommandExecuteButton(device.getWriteAllAO()), 2, rowIndex++, 3, 1);
            ScrollPane scrol = new ScrollPane(grid);
            scrol.setFitToWidth(true);
            scrol.setFitToHeight(true);
            initTab.setContent(scrol);
        });
    }

    private void intiAIPane() {
        final Tab initTab = new Tab(I18N_FX.getLocalizedString("ANALOG_IN"));
        tabs.getTabs().add(initTab);
        initTab.setClosable(false);
        Platform.runLater(() -> {
            final GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setId("formGrid");
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(10, 10, 10, 10));
            int rowIndex = 0;
            TupicalPanels.setAIChanelControl(grid, device.getAnalogInChanelN1(), I18N_FX.getLocalizedString("AI1"), rowIndex++, 0);
            TupicalPanels.setAIChanelControl(grid, device.getAnalogInChanelN2(), I18N_FX.getLocalizedString("AI2"), rowIndex++, 0);
            TupicalPanels.setAIChanelControl(grid, device.getAnalogInChanelN3(), I18N_FX.getLocalizedString("AI3"), rowIndex++, 0);
            TupicalPanels.setAIChanelControl(grid, device.getAnalogInChanelN4(), I18N_FX.getLocalizedString("AI4"), rowIndex++, 0);
            grid.add(VisualControls.createCommandExecuteButton(device.getReadAllAI()), 0, rowIndex++, 3, 1);
            ScrollPane scrol = new ScrollPane(grid);
            scrol.setFitToWidth(true);
            scrol.setFitToHeight(true);
            initTab.setContent(scrol);
        });
    }

    private void intiLinePane() {
        final Tab initTab = new Tab(I18N_FX.getLocalizedString("LINE_CONTROL"));
        tabs.getTabs().add(initTab);
        initTab.setClosable(false);
        Platform.runLater(() -> {
            final GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setId("formGrid");
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(10, 10, 10, 10));
            int rowIndex = 0;
            setLineSelectChanelControl(grid, device.getLineSelect(), I18N_FX.getLocalizedString("LINE_CONTROL"), rowIndex++, 0);
            initTab.setContent(grid);
        });
    }

    private Pane createImidiatlyLineSelectPane() {
        final GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setId("formGrid");
        grid.setStyle("-fx-border-width: 1; -fx-border-style: solid;");
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        int rowIndex = 0;
        setLineSelectChanelControlImid(grid, device.getLineSelect(), I18N_FX.getLocalizedString("LINE_CONTROL"), rowIndex++, 0);
        return grid;
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
            int rowindex = 0;
            grid.add(new Label(I18N_FX.getLocalizedString("DEVICE ADDRESS")), 0, rowindex);
            grid.add(VisualControls.createAddressSelect(device.getConfigdeviceAddress()), 1, rowindex);
            grid.add(VisualControls.createReReadButton(device.getConfigdeviceAddress()), 2, rowindex);
            grid.add(VisualControls.createReWriteButton(device.getConfigdeviceAddress()), 3, rowindex++);

            grid.add(new Label(I18N_FX.getLocalizedString("DEVICE SPEED")), 0, rowindex);
            grid.add(VisualControls.createFavoritSpeedSelect(device.getConfigdeviceSpeed()), 1, rowindex);
            grid.add(VisualControls.createReReadButton(device.getConfigdeviceSpeed()), 2, rowindex);
            grid.add(VisualControls.createReWriteButton(device.getConfigdeviceSpeed()), 3, rowindex);
            ScrollPane scrol = new ScrollPane(grid);
            scrol.setFitToWidth(true);
            scrol.setFitToHeight(true);
            initTab.setContent(scrol);
        });
    }


    private void setLineSelectChanelControl(GridPane grid, SoftLongProperty chanel, String name, int rowindex, int col) {
        grid.add(new Label(name), col++, rowindex);
        grid.add(VisualControls.createLineIndicator(chanel), col++, rowindex);
        grid.add(VisualControls.createReReadButton(chanel), col++, rowindex);
        grid.add(VisualControls.createReWriteButton(chanel), col++, rowindex);
        grid.add(VisualControls.createConfigureProppearty(chanel), col++, rowindex++);
        col = 2;
        grid.add(VisualControls.createSetLinebutton(device.getLineSelect(), 0), col, rowindex);
        col++;
        grid.add(VisualControls.createSetLinebutton(device.getLineSelect(), 1), col++, rowindex);
        grid.add(VisualControls.createSetLinebutton(device.getLineSelect(), 2), col++, rowindex);
        grid.add(VisualControls.createSetLinebutton(device.getLineSelect(), 3), col++, rowindex);
        grid.add(VisualControls.createSetLinebutton(device.getLineSelect(), 4), col++, rowindex);
    }

    private void setLineSelectChanelControlImid(GridPane grid, SoftLongProperty chanel, String name, int rowindex, int col) {
        grid.add(new Label(name), col++, rowindex);
        grid.add(VisualControls.createLineIndicator(chanel), col++, rowindex);
        col++;
        grid.add(VisualControls.createSetLinebuttonImidiatly(device.getLineSelect(), 0), col, rowindex);
        col++;
        grid.add(VisualControls.createSetLinebuttonImidiatly(device.getLineSelect(), 1), col++, rowindex);
        grid.add(VisualControls.createSetLinebuttonImidiatly(device.getLineSelect(), 2), col++, rowindex);
        grid.add(VisualControls.createSetLinebuttonImidiatly(device.getLineSelect(), 3), col++, rowindex);
        grid.add(VisualControls.createSetLinebuttonImidiatly(device.getLineSelect(), 4), col++, rowindex);
    }

    private void initControl() {
        setAlignment(Pos.CENTER);
        intiStatusPane();
        intiDIPane();
        intiDOPane();
        intiAIPane();
        intiAOPane();
        intiLinePane();
        intiDeviceConfigPane();
        VBox hbox = new VBox();
        hbox.setAlignment(Pos.CENTER);
        tabs.setPrefHeight(430);
        hbox.setSpacing(10);
        hbox.setStyle("-fx-border-width: 1; -fx-border-style: solid;");
        hbox.getChildren().add(createImidiatlyLineSelectPane());
        hbox.getChildren().add(tabs);
        hbox.setAlignment(Pos.CENTER);
        getChildren().add(hbox);
    }


}

package ua.pp.fairwind.javafx.panels.administrative;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import ua.pp.fairwind.communications.devices.abstracts.ImitatorDevice;
import ua.pp.fairwind.communications.devices.hardwaredevices.Baumer.EncoderImmitator;
import ua.pp.fairwind.javafx.I18N.I18N_FX;
import ua.pp.fairwind.javafx.VisualControls;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Сергей on 02.10.2015.
 */
public class ImitatorDialogCreator {
    private final HashMap<UUID, Node> panels = new HashMap<>();

    public Node getNode(ImitatorDevice device) {
        UUID uuid = device.getUUID();
        Node node = panels.get(uuid);
        if (node != null) return node;
        return createNode(device);
    }

    public Node createNode(ImitatorDevice device) {
        if (device instanceof EncoderImmitator) {
            final GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setId("formGrid");
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(10, 10, 10, 10));
            int rowIndex = 0;
            grid.add(new Label(I18N_FX.getLocalizedString("STEPS")), 0, rowIndex);
            grid.add(VisualControls.getPropertyControl(((EncoderImmitator) device).getSteps()), 1, rowIndex++);
            grid.add(new Label(I18N_FX.getLocalizedString("REVOLUTION")), 0, rowIndex);
            grid.add(VisualControls.getPropertyControl(((EncoderImmitator) device).getRevolution()), 1, rowIndex++);
            grid.add(VisualControls.createCommandExecuteButton(((EncoderImmitator) device).getRandomCommand()), 0, rowIndex, 2, 1);
            return grid;
        }
        return null;
    }

    public Dialog getDialog(ImitatorDevice device) {
        Dialog<Void> dialog = new Dialog();
        dialog.getDialogPane().setContent(getNode(device));
        dialog.setTitle(I18N_FX.getLocalizedString("IMITATOR"));
        dialog.setHeaderText(I18N_FX.getLocalizedString("DEVICE") + " : " + device.getName());
        dialog.initModality(Modality.NONE);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        return dialog;
    }


}

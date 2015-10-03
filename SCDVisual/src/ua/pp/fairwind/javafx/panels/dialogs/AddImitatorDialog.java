package ua.pp.fairwind.javafx.panels.dialogs;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import ua.pp.fairwind.communications.devices.abstracts.ImitatorDevice;
import ua.pp.fairwind.communications.devices.hardwaredevices.ImitatorCreator;
import ua.pp.fairwind.javafx.I18N.I18N_FX;
import ua.pp.fairwind.javafx.panels.administrative.ImmitatorWindow;

import java.util.Optional;

/**
 * Created by Сергей on 10.09.2015.
 */
public class AddImitatorDialog {


    public static void getSerialLineParameterDialog(ImmitatorWindow windows) {
        if (windows == null) return;
        ImitatorCreator creator = new ImitatorCreator();
        Dialog<ImitatorDevice> dialog = new Dialog<>();
        dialog.setTitle(I18N_FX.getLocalizedString("Add_Line_Imitator_Dialog"));
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));


        ComboBox<String> types = new ComboBox<>();
        types.getItems().addAll(creator.getAviableImitator());
        Tooltip.install(types, new Tooltip(I18N_FX.getLocalizedString("IMITATOR_TYPE.description")));
        ComboBox<Integer> addres = createAddressSelect(1);
        Tooltip.install(addres, new Tooltip(I18N_FX.getLocalizedString("ADDRESS.description")));

        grid.add(new Label(I18N_FX.getLocalizedString("IMITATOR_TYPE")), 0, 0);
        grid.add(types, 1, 0);
        grid.add(new Label(I18N_FX.getLocalizedString("ADDRESS")), 0, 1);
        grid.add(addres, 1, 1);
        dialog.getDialogPane().setContent(grid);


        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                int adr = addres.getSelectionModel().getSelectedItem() == null ? 0 : addres.getSelectionModel().getSelectedItem();
                String type = types.getSelectionModel().getSelectedItem() == null ? "" : types.getSelectionModel().getSelectedItem();
                return creator.getImitatorDevice(type, (long) adr);
            }
            return null;
        });

        Optional<ImitatorDevice> result = dialog.showAndWait();
        result.ifPresent(lineparams -> windows.addImitator(lineparams));
    }


    public static ComboBox<Integer> createAddressSelect(int sinitialValue) {
        ComboBox<Integer> box = new ComboBox<>();
        for (int i = 0; i < 255; i++) box.getItems().add(i);
        box.getSelectionModel().select((Integer) sinitialValue);
        return box;
    }

}

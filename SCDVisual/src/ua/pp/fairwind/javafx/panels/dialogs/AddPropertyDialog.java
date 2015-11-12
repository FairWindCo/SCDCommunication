package ua.pp.fairwind.javafx.panels.dialogs;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.groups.GroupProperty;
import ua.pp.fairwind.communications.utils.EllementsCreator;
import ua.pp.fairwind.communications.utils.ModBusProtocol;
import ua.pp.fairwind.javafx.I18N.I18N_FX;
import ua.pp.fairwind.javafx.guiElements.editors.HexIntegerInputNullText;

import java.util.Optional;

/**
 * Created by Сергей on 10.09.2015.
 */
public class AddPropertyDialog {
    public static void addNewProperty(AbstractProperty property,EllementsCreator creator) {
        if (property == null) return;
        if(property instanceof GroupProperty) {
            final GroupProperty groupProperty=(GroupProperty)property;
            Dialog<AbstractProperty> dialog = new Dialog<>();
            dialog.setTitle(I18N_FX.getLocalizedString("create_property_dialog"));
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField name = new TextField();
            HexIntegerInputNullText addres=new HexIntegerInputNullText();
            ComboBox<String> types = new ComboBox<>();
            types.getItems().addAll(creator.getPropertyTypes());
            Tooltip.install(types, new Tooltip(I18N_FX.getLocalizedString("PROPERTY_TYPE.description")));
            ComboBox<String> modes = createOperation("READ_WRITE");
            Tooltip.install(modes, new Tooltip(I18N_FX.getLocalizedString("PROPERTY_MODE.description")));
            ComboBox<Integer> rfunc = createReadFunction(0x3);
            Tooltip.install(rfunc, new Tooltip(I18N_FX.getLocalizedString("MODBUS_READ_FUNCTION.description")));
            ComboBox<Integer> wfunc = createWriteFunction(0x10);
            Tooltip.install(wfunc, new Tooltip(I18N_FX.getLocalizedString("MODBUS_WRITE_FUNCTION.description")));

            grid.add(new Label(I18N_FX.getLocalizedString("PROPERTY_NAME")), 0, 0);
            grid.add(name, 1, 0);
            grid.add(new Label(I18N_FX.getLocalizedString("PROPERTY_TYPE")), 0, 1);
            grid.add(types, 1, 1);
            grid.add(new Label(I18N_FX.getLocalizedString("PROPERTY_MODE")), 0, 2);
            grid.add(modes, 1, 2);
            grid.add(new Label(I18N_FX.getLocalizedString("MODBUS_READ_FUNCTION")), 0, 3);
            grid.add(rfunc, 1, 3);
            grid.add(new Label(I18N_FX.getLocalizedString("MODBUS_WRITE_FUNCTION")), 0, 4);
            grid.add(wfunc, 1, 4);
            grid.add(new Label(I18N_FX.getLocalizedString("MODBUS_ADDRESS")), 0, 5);
            grid.add(addres, 1, 5);
            dialog.getDialogPane().setContent(grid);


            // Convert the result to a username-password-pair when the login button is clicked.
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
                    String nameProperty=name.getText();
                    if(nameProperty==null||nameProperty.isEmpty())return null;
                    String type = types.getSelectionModel().getSelectedItem() == null ? "" : types.getSelectionModel().getSelectedItem();
                    if(type==null||type.isEmpty())return null;
                    String mode = modes.getSelectionModel().getSelectedItem() == null ? "" : types.getSelectionModel().getSelectedItem();
                    ValueProperty.SOFT_OPERATION_TYPE modeProperty;
                    switch (mode){
                        case "READ_WRITE":
                            modeProperty= ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE;
                            break;
                        case "READ_ONLY":
                            modeProperty= ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE;
                        break;
                        case "WRITE_ONLY":
                            modeProperty= ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE;
                            break;
                        default:
                            modeProperty= ValueProperty.SOFT_OPERATION_TYPE.READ_WRITE;
                    }
                    AbstractProperty result=creator.createProperty(nameProperty,type, modeProperty);
                    if(result!=null) {
                        result.setAdditionalInfo(ModBusProtocol.MODBUS_READ_FUNCTION, rfunc.getValue());
                        result.setAdditionalInfo(ModBusProtocol.MODBUS_WRITE_FUNCTION, wfunc.getValue());
                        result.setAdditionalInfo(ModBusProtocol.MODBUS_ADDRESS, addres.getValue());
                    } else {
                        System.err.println("type");
                    }
                    return result;
                }
                return null;
            });

            Optional<AbstractProperty> result = dialog.showAndWait();
            result.ifPresent(proper -> groupProperty.addProperty(proper));
        } else {

        }
    }

    public static ComboBox<String> createOperation(String sinitialValue) {
        ComboBox<String> box = new ComboBox<>();
        box.getItems().addAll("READ_WRITE","READ_ONLY","WRITE_ONLY");
        box.getSelectionModel().select(sinitialValue);
        return box;
    }

    public static ComboBox<Integer> createReadFunction(int sinitialValue) {
        ComboBox<Integer> box = new ComboBox<>();
        box.getItems().addAll(0x1,0x2,0x3,0x4);
        box.getSelectionModel().select((Integer) sinitialValue);
        return box;
    }

    public static ComboBox<Integer> createWriteFunction(int sinitialValue) {
        ComboBox<Integer> box = new ComboBox<>();
        box.getItems().addAll(0x5,0x6,0xF,0x10);
        box.getSelectionModel().select((Integer) sinitialValue);
        return box;
    }

}

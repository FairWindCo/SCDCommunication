package ua.pp.fairwind.javafx.panels.dialogs;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import ua.pp.fairwind.communications.devices.AbstractDevice;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.timeaction.PropertyTimer;
import ua.pp.fairwind.javafx.I18N.I18N_monitor;


/**
 * Created by Сергей on 27.08.2015.
 */
public class PropertyConfigDialog {
    volatile private AbstractProperty property;
    private final GridPane grid;
    private final CheckBox checkBox;
    private final Button add;
    private final TableView<PropertyTimer> table;
    private Dialog<Void> dialog=new Dialog<Void>();

    final static private PropertyConfigDialog configDialog=new PropertyConfigDialog();

    private class ButtonCell extends TableCell<PropertyTimer, String>{
        final private Button cellButton;

            ButtonCell(String text){
                cellButton = new Button();
            cellButton.setText(text);
        }
        //Display button if the row is not empty
        @Override
        protected void updateItem(String t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                final PropertyTimer record = (PropertyTimer)getTableRow().getItem();
                cellButton.setOnAction(event -> {
                    record.destroy();
                    table.getItems().remove(record);
                });
                setGraphic(cellButton);
            } else { // you must always do the following in cell subclasses:
                setGraphic(null);
            }
        }
    }

    private PropertyConfigDialog() {
        checkBox=new CheckBox(I18N_monitor.COMMON.getStringEx("CONFIG_PROPERTY_ENABLE_IMMEDIATELY_WRITE"));
        checkBox.setOnAction(select->{
            if(property!=null&&property instanceof ValueProperty<?>){
                property.setAdditionalInfo(AbstractDevice.IMMEDIATELY_WRITE_FLAG,checkBox.isSelected());
            }
        });
        table=new TableView<>();
        TableColumn<PropertyTimer, String> col1= new TableColumn<>(I18N_monitor.COMMON.getStringEx("CONFIG_PROPERTY_AVIABLE_TIMERS"));
        col1.setCellValueFactory(param -> {
            if (param.getValue() == null) return new SimpleStringProperty("----");
            PropertyTimer time = param.getValue();
            return new SimpleStringProperty(time.toString());
        });
        ButtonCell col2=new ButtonCell(I18N_monitor.COMMON.getStringEx("CONFIG_PROPERTY_REMOVE_TIMER"));
        TableColumn<PropertyTimer,String> col_action = new TableColumn<>("Action");
        col_action.setCellValueFactory((p)->new SimpleStringProperty(I18N_monitor.COMMON.getStringEx("CONFIG_PROPERTY_REMOVE_TIMER")));


        col_action.setCellFactory(p ->new ButtonCell(I18N_monitor.COMMON.getStringEx("CONFIG_PROPERTY_REMOVE_TIMER")));


        table.getColumns().add(col1);
        table.getColumns().add(col_action);
        add=new Button(I18N_monitor.COMMON.getStringEx("CONFIG_PROPERTY_ADD_TIMER"));
        add.setOnAction(action->{
            if(property!=null){
                //PropertyTimer timer=PropertyTimer.createPropertyTimer(property, TIMER_ACTION.READ,1000);
                //if(timer!=null){
                //    table.getItems().add(timer);
                //}
                TimerParametersDialog.configureTimer(property,table.getItems());
            }
        });
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(checkBox, 0, 0);
        grid.add(table, 0, 1);
        grid.add(add,0,2);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        dialog.getDialogPane().setContent(grid);
    }

    synchronized public static PropertyConfigDialog showConfigDialog(AbstractProperty property){
        if(property==null)return null;
        configDialog.setupProprty(property);
        return configDialog;
    }


    private void setupProprty(AbstractProperty property){
        this.property=property;
        if(property instanceof ValueProperty<?>){
            checkBox.setVisible(true);
            Boolean state=(Boolean)property.getAdditionalInfo(AbstractDevice.IMMEDIATELY_WRITE_FLAG);
            checkBox.setSelected(state==null?false:state);
        } else {
            checkBox.setVisible(false);
        }
        dialog.setHeaderText(I18N_monitor.COMMON.getStringEx("CONFIG_PROPERTY:")+property.getName()+" "+property.getDescription());
        refreshTable(property);
        dialog.show();
    }

    private void refreshTable(final AbstractProperty property){
        if(property!=null){
            PropertyTimer[] arr=PropertyTimer.getTimersForProperty(property);
            if(arr!=null){
                //ObservableList<PropertyTimer> list=new SimpleListProperty<>();
                //list.addAll(arr);
                //table.setItems(list);
                table.getItems().addAll(arr);
            }else {
                table.getItems().clear();
            }
        }

    }

    public static Button crateConfigButton(final AbstractProperty property){
        Button but=new Button(I18N_monitor.COMMON.getStringEx("CONFIG"));
        but.setOnAction(action->showConfigDialog(property));
        return but;
    }




}

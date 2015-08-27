package ua.pp.fairwind.javafx.panels.dialogs;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.timeaction.PropertyTimer;
import ua.pp.fairwind.communications.timeaction.TIMER_ACTION;

import java.util.Optional;


/**
 * Created by Сергей on 28.08.2015.
 */
public class TimerParametersDialog {
    static private final TimerParametersDialog timerParametersDialog=new TimerParametersDialog();
    private final Dialog<Pair<TIMER_ACTION,Long>> dialog=new Dialog();

    public TimerParametersDialog() {
        dialog.setTitle("SELECT TIMER PARAMETER");
        dialog.setHeaderText("TIMER PARAMETER:");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ComboBox<TIMER_ACTION> types = new ComboBox();
        types.getItems().addAll(TIMER_ACTION.READ,TIMER_ACTION.WRITE,TIMER_ACTION.WRITE_READ,TIMER_ACTION.EXECUTE,TIMER_ACTION.VALIDATE);
        TextField value = new TextField();
        types.selectionModelProperty().getValue().select(0);
        value.setText("5000");


        grid.add(new Label("ACTION:"), 0, 0);
        grid.add(types, 1, 0);
        grid.add(new Label("PERIOD, ms:"), 0, 1);
        grid.add(value, 1, 1);
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    Long val = Long.valueOf(value.getText());
                    TIMER_ACTION act=types.getValue();
                    if(act==null)return null;
                    return new Pair<>(act, val);
                }catch (NumberFormatException form){
                    return null;
                }

            }
            return null;
        });
    }

    private void showDialog(AbstractProperty property,ObservableList<PropertyTimer> list){
        Optional<Pair<TIMER_ACTION,Long>> result=dialog.showAndWait();
        Pair<TIMER_ACTION,Long> value;
        result.ifPresent(val-> {
            PropertyTimer timer=PropertyTimer.createPropertyTimer(property,val.getKey(),val.getValue());
            if(timer!=null && list!=null)list.add(timer);
        });
    }

    public static void configureTimer(AbstractProperty property,ObservableList<PropertyTimer> list){
        if(property!=null) timerParametersDialog.showDialog(property,list);
    }
}

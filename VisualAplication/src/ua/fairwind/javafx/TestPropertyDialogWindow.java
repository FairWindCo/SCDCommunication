package ua.fairwind.javafx;

import javafx.application.Application;
import javafx.stage.Stage;
import ua.pp.fairwind.communications.propertyes.groups.GroupProperty;
import ua.pp.fairwind.communications.utils.EllementsCreator;
import ua.pp.fairwind.javafx.panels.dialogs.AddPropertyDialog;

/**
 * Created by Сергей on 17.08.2015.
 */
public class TestPropertyDialogWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage windowStage) throws Exception {
        GroupProperty prp=new GroupProperty("test");
        AddPropertyDialog.addNewProperty(prp,new EllementsCreator());
    }


    @Override
    public void stop() throws Exception {
        super.stop();
    }

}

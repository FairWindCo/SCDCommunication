package ua.pp.fairwind.javafx.panels.devices.dp5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import ua.pp.fairwind.communications.devices.hardwaredevices.dp5.DP5ConfigurationChanel;
import ua.pp.fairwind.communications.propertyes.software.stringlike.StringValuedPropertry;
import ua.pp.fairwind.io.javafx.propertys.StringValuedPropertyFXAdapter;
import ua.pp.fairwind.javafx.guiElements.editors.ComboEditPanel;
import ua.pp.fairwind.javafx.guiElements.editors.StringValueColumnEditor;

import java.io.IOException;

/**
 * Created by Сергей on 02.12.2015.
 */
public class DP5ParametersPanel extends BorderPane {
    final private DP5ConfigurationChanel configuration;
    String fileName="dp5module.cfg";

    public DP5ParametersPanel(DP5ConfigurationChanel configuration) {
        this.configuration = configuration;
        setCenter(createView());
    }

    protected Node createView() {
        TabPane tabPane=new TabPane();
        Tab tab1=new Tab("MCA");
        Tab tab2=new Tab("Shaping");
        Tab tab3=new Tab("GAIN");
        Tab tab4=new Tab("POWER");
        Tab tab5=new Tab("MISC");
        Tab tab6=new Tab("COMMANDS");
        Tab tab7=new Tab("ALL");
        tab1.setClosable(false);
        tab2.setClosable(false);
        tab3.setClosable(false);
        tab4.setClosable(false);
        tab5.setClosable(false);
        tab6.setClosable(false);
        tab7.setClosable(false);
        tab1.setContent(formMCA());
        tab2.setContent(formShaping());
        tab3.setContent(formGAIN());
        tab4.setContent(formPOWER());
        tab5.setContent(formMISC());
        tab6.setContent(formCOM());
        tab7.setContent(formALL());
        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);
        tabPane.getTabs().add(tab4);
        tabPane.getTabs().add(tab5);
        tabPane.getTabs().add(tab6);
        tabPane.getTabs().add(tab7);
        return tabPane;
    }


    private Node formLabel(String label){
        HBox box=new HBox();
        box.setAlignment(Pos.CENTER);
        Label lbl=new Label(label);
        lbl.getStyleClass().add("formLabel");
        box.getChildren().add(lbl);
        return box;
    }

    private Node formMCA(){
        BorderPane sceneRoot=new BorderPane();
        sceneRoot.setId("mainWindow");
        sceneRoot.setLayoutY(0);
        sceneRoot.setLayoutX(0);
        sceneRoot.setPadding(new Insets(3, 3, 3, 3));
        GridPane pane= new GridPane();
        pane.setId("formGrid");
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(10);
        pane.setHgap(30);
        pane.setPadding(new Insets(5, 5, 5, 5));
        ScrollPane scroll=new ScrollPane();
        scroll.getStyleClass().add("scrollPanel");
        scroll.setContent(pane);
        scroll.setPrefWidth(950);
        HBox btnpanel=new HBox();
        btnpanel.setAlignment(Pos.CENTER);
        //btnpanel.getChildren().add(btn);
        HBox scrl=new HBox();
        scrl.setAlignment(Pos.CENTER);
        scrl.getChildren().add(scroll);
        sceneRoot.topProperty().set(btnpanel);
        sceneRoot.centerProperty().set(scrl);
        pane.add(formLabel("STANDART MCA"), 0, 0);
        if(configuration.getmCAE()!=null){
            pane.add(new ComboEditPanel(configuration.getmCAE()), 0, 1);
        }
        if(configuration.getmCAC()!=null){
            pane.add(new ComboEditPanel(configuration.getmCAC()), 0, 2);
        }
        if(configuration.getmCSL()!=null){
            pane.add(new ComboEditPanel(configuration.getmCSL()), 0, 3);
        }
        if(configuration.getmCSH()!=null){
            pane.add(new ComboEditPanel(configuration.getmCSH()), 0, 4);
        }
        if(configuration.getmCAS()!=null){
            pane.add(new ComboEditPanel(configuration.getmCAS()), 0, 5);
        }
        if(configuration.getmCST()!=null){
            pane.add(new ComboEditPanel(configuration.getmCST()), 0, 6);
        }
        pane.add(formLabel("PRESETS"), 1, 0);
        if(configuration.getpREC()!=null){
            pane.add(new ComboEditPanel(configuration.getpREC()), 1, 1);
        }
        if(configuration.getpRCL()!=null){
            pane.add(new ComboEditPanel(configuration.getpRCL()), 1, 2);
        }
        if(configuration.getpRCH()!=null){
            pane.add(new ComboEditPanel(configuration.getpRCH()), 1, 3);
        }
        if(configuration.getpRER()!=null){
            pane.add(new ComboEditPanel(configuration.getpRER()), 1, 4);
        }
        if(configuration.getpRET()!=null){
            pane.add(new ComboEditPanel(configuration.getpRET()), 1, 5);
        }
        pane.add(formLabel("ADVANCED"), 1, 6);

        if(configuration.getpDMD()!=null){
            pane.add(new ComboEditPanel(configuration.getpDMD()), 1, 7);
        }
        pane.add(formLabel("ADVANCED MCA"), 0, 9,2,1);
        if(configuration.getgATE()!=null){
            pane.add(new ComboEditPanel(configuration.getgATE()), 0, 10);
        }
        if(configuration.gettHSL()!=null){
            pane.add(new ComboEditPanel(configuration.gettHSL()), 0, 11);
        }
        if(configuration.gettLLD()!=null){
            pane.add(new ComboEditPanel(configuration.gettLLD()), 1, 10);
        }
        if(configuration.getsOFF()!=null){
            pane.add(new ComboEditPanel(configuration.getsOFF()), 1, 11);
        }
        return sceneRoot;
    }

    private Node formShaping(){
        BorderPane sceneRoot=new BorderPane();
        sceneRoot.setId("mainWindow");
        sceneRoot.setLayoutY(0);
        sceneRoot.setLayoutX(0);
        sceneRoot.setPadding(new Insets(3, 3, 3, 3));
        GridPane pane=new GridPane();
        pane.setId("formGrid");
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(10);
        pane.setHgap(30);
        pane.setPadding(new Insets(5, 5, 5, 5));
        ScrollPane scroll=new ScrollPane();
        scroll.getStyleClass().add("scrollPanel");
        scroll.setContent(pane);
        scroll.setPrefWidth(950);
        HBox btnpanel=new HBox();
        btnpanel.setAlignment(Pos.CENTER);
        //btnpanel.getChildren().add(btn);
        HBox scrl=new HBox();
        scrl.setAlignment(Pos.CENTER);
        scrl.getChildren().add(scroll);
        sceneRoot.topProperty().set(btnpanel);
        sceneRoot.centerProperty().set(scrl);

        pane.add(formLabel("BASE LINE RESTORATION - BLR"), 0, 0,2,1);
        if(configuration.getcUSP()!=null){
            pane.add(new ComboEditPanel(configuration.getcUSP()), 0, 1);
        }
        if(configuration.getbLRD()!=null){
            pane.add(new ComboEditPanel(configuration.getbLRD()), 0, 2);
        }
        if(configuration.getbLRU()!=null){
            pane.add(new ComboEditPanel(configuration.getbLRU()), 1, 1);
        }
        if(configuration.getbLRM()!=null){
            pane.add(new ComboEditPanel(configuration.getbLRM()), 1, 2);
        }

        pane.add(formLabel("RISE TIME DESCRIMINATOR"), 0, 3);
        if(configuration.getrTDS()!=null){
            pane.add(new ComboEditPanel(configuration.getrTDS()), 0, 4);
        }
        if(configuration.getrTDT()!=null){
            pane.add(new ComboEditPanel(configuration.getrTDT()), 0, 5);
        }
        if(configuration.getrTDE()!=null){
            pane.add(new ComboEditPanel(configuration.getrTDE()), 0, 6);
        }
        if(configuration.getrTDD()!=null){
            pane.add(new ComboEditPanel(configuration.getrTDD()), 0, 7);
        }
        if(configuration.getrTDW()!=null){
            pane.add(new ComboEditPanel(configuration.getrTDW()), 0, 8);
        }
        pane.add(formLabel("FAST SHAPING"), 1, 3);

        if(configuration.gettPFA()!=null){
            pane.add(new ComboEditPanel(configuration.gettPFA()), 1, 4);
        }
        if(configuration.gettHFA()!=null){
            pane.add(new ComboEditPanel(configuration.gettHFA()), 1, 5);
        }

        pane.add(formLabel("PULSE SHAPING"), 1, 6,2,1);

        if(configuration.gettPEA()!=null){
            pane.add(new ComboEditPanel(configuration.gettPEA()), 1, 7);
        }
        if(configuration.gettFLA()!=null){
            pane.add(new ComboEditPanel(configuration.gettFLA()), 1, 8);
        }
        if(configuration.getcLCK()!=null){
            pane.add(new ComboEditPanel(configuration.getcLCK()), 1, 9);
        }
        return sceneRoot;
    }

    private Node formGAIN(){
        BorderPane sceneRoot=new BorderPane();
        sceneRoot.setId("mainWindow");
        sceneRoot.setLayoutY(0);
        sceneRoot.setLayoutX(0);
        sceneRoot.setPadding(new Insets(3, 3, 3, 3));
        GridPane pane=new GridPane();
        pane.setId("formGrid");
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(10);
        pane.setHgap(30);
        pane.setPadding(new Insets(5, 5, 5, 5));
        ScrollPane scroll=new ScrollPane();
        scroll.getStyleClass().add("scrollPanel");
        scroll.setContent(pane);
        scroll.setPrefWidth(950);
        HBox btnpanel=new HBox();
        btnpanel.setAlignment(Pos.CENTER);
        //btnpanel.getChildren().add(btn);
        HBox scrl=new HBox();
        scrl.setAlignment(Pos.CENTER);
        scrl.getChildren().add(scroll);
        sceneRoot.topProperty().set(btnpanel);
        sceneRoot.centerProperty().set(scrl);
        pane.add(formLabel("GAIN"), 0, 0);
        if(configuration.getgAIF()!=null){
            pane.add(new ComboEditPanel(configuration.getgAIF()), 0, 1);
        }
        if(configuration.getgAIN()!=null){
            pane.add(new ComboEditPanel(configuration.getgAIN()), 0, 2);
        }
        if(configuration.getpAPZ()!=null){
            pane.add(new ComboEditPanel(configuration.getpAPZ()), 0, 4);
        }

        pane.add(formLabel("INPUT"), 1, 0);
        if(configuration.getiNOF()!=null){
            pane.add(new ComboEditPanel(configuration.getiNOF()), 1, 1);
        }
        if(configuration.getiNOG()!=null){
            pane.add(new ComboEditPanel(configuration.getiNOG()), 1, 2);
        }
        if(configuration.getaINP()!=null){
            pane.add(new ComboEditPanel(configuration.getaINP()), 1, 3);
        }
        if(configuration.getrESL()!=null){
            pane.add(new ComboEditPanel(configuration.getrESL()), 1, 4);
        }
        return sceneRoot;
    }

    private Node formPOWER(){
        BorderPane sceneRoot=new BorderPane();
        sceneRoot.setId("mainWindow");
        sceneRoot.setLayoutY(0);
        sceneRoot.setLayoutX(0);
        sceneRoot.setPadding(new Insets(3, 3, 3, 3));
        GridPane pane=new GridPane();
        pane.setId("formGrid");
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(10);
        pane.setHgap(30);
        pane.setPadding(new Insets(5, 5, 5, 5));
        ScrollPane scroll=new ScrollPane();
        scroll.getStyleClass().add("scrollPanel");
        scroll.setContent(pane);
        scroll.setPrefWidth(950);
        HBox btnpanel=new HBox();
        btnpanel.setAlignment(Pos.CENTER);
        //btnpanel.getChildren().add(btn);
        HBox scrl=new HBox();
        scrl.setAlignment(Pos.CENTER);
        scrl.getChildren().add(scroll);
        sceneRoot.topProperty().set(btnpanel);
        sceneRoot.centerProperty().set(scrl);
        pane.add(formLabel("HIGHT VOLTAGE"), 0, 0);
        if(configuration.gethVSE()!=null){
            pane.add(new ComboEditPanel(configuration.gethVSE()), 0, 1);
        }
        if(configuration.getbOOT()!=null){
            pane.add(new ComboEditPanel(configuration.getbOOT()), 0, 2);
        }
        if(configuration.getmCSL()!=null){
            //pane.add(new ComboEditPanel(configuration.getmCSL()), 0, 3);
        }
        pane.add(formLabel("COOLER TEMP"), 1, 0);
        if(configuration.getpREC()!=null){
            //pane.add(new ComboEditPanel(configuration.getpREC()), 1, 1);
        }
        return sceneRoot;
    }

    private Node formMISC(){
        BorderPane sceneRoot=new BorderPane();
        sceneRoot.setId("mainWindow");
        sceneRoot.setLayoutY(0);
        sceneRoot.setLayoutX(0);
        sceneRoot.setPadding(new Insets(3, 3, 3, 3));
        GridPane pane=new GridPane();
        pane.setId("formGrid");
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(10);
        pane.setHgap(30);
        pane.setPadding(new Insets(5, 5, 5, 5));
        ScrollPane scroll=new ScrollPane();
        scroll.getStyleClass().add("scrollPanel");
        scroll.setContent(pane);
        scroll.setPrefWidth(950);
        HBox btnpanel=new HBox();
        btnpanel.setAlignment(Pos.CENTER);
        //btnpanel.getChildren().add(btn);
        HBox scrl=new HBox();
        scrl.setAlignment(Pos.CENTER);
        scrl.getChildren().add(scroll);
        sceneRoot.topProperty().set(btnpanel);
        sceneRoot.centerProperty().set(scrl);
        pane.add(formLabel("DAC"), 0, 0);
        if(configuration.getdACF()!=null){
            pane.add(new ComboEditPanel(configuration.getdACF()), 0, 1);
        }
        if(configuration.getdACO()!=null){
            pane.add(new ComboEditPanel(configuration.getdACO()), 0, 2);
        }
        if(configuration.getmCSL()!=null){
            //pane.add(new ComboEditPanel(configuration.getmCSL()), 0, 3);
        }
        pane.add(formLabel("GENERAL PURPOSE COUNTER GPC"), 1, 0);
        if(configuration.getgPED()!=null){
            pane.add(new ComboEditPanel(configuration.getgPED()), 1, 1);
        }
        if(configuration.getgPGA()!=null){
            pane.add(new ComboEditPanel(configuration.getgPGA()), 1, 2);
        }
        if(configuration.getgPIN()!=null){
            pane.add(new ComboEditPanel(configuration.getgPIN()), 1, 3);
        }
        if(configuration.getgPMC()!=null){
            pane.add(new ComboEditPanel(configuration.getgPMC()), 1, 4);
        }
        if(configuration.getgPME()!=null){
            pane.add(new ComboEditPanel(configuration.getgPME()), 1, 5);
        }
        pane.add(formLabel("SCOPE"), 1, 6);
        if(configuration.getsCOE()!=null){
            pane.add(new ComboEditPanel(configuration.getsCOE()), 1, 7);
        }
        if(configuration.getsCOT()!=null){
            pane.add(new ComboEditPanel(configuration.getsCOT()), 1, 8);
        }
        if(configuration.getsCOG()!=null){
            pane.add(new ComboEditPanel(configuration.getsCOG()), 1, 9);
        }
        pane.add(formLabel("AUX"), 0, 4);
        if(configuration.getaUO1()!=null){
            pane.add(new ComboEditPanel(configuration.getaUO1()), 0, 5);
        }
        if(configuration.getaUO2()!=null){
            pane.add(new ComboEditPanel(configuration.getaUO2()), 0, 6);
        }
        if(configuration.getcON1()!=null){
            pane.add(new ComboEditPanel(configuration.getcON1()), 0, 7);
        }
        if(configuration.getcON2()!=null){
            pane.add(new ComboEditPanel(configuration.getcON2()), 0, 8);
        }
        if(configuration.gettPMO()!=null){
            pane.add(new ComboEditPanel(configuration.gettPMO()), 0, 9);
        }
        if(configuration.getvOLU()!=null){
            pane.add(new ComboEditPanel(configuration.getvOLU()), 0, 10);
        }
        return sceneRoot;
    }
    private Node formCOM(){
        BorderPane sceneRoot=new BorderPane();
        sceneRoot.setId("mainWindow");
        sceneRoot.setLayoutY(0);
        sceneRoot.setLayoutX(0);
        sceneRoot.setPadding(new Insets(3, 3, 3, 3));
        GridPane pane= new GridPane();
        pane.setId("formGrid");
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(10);
        pane.setHgap(30);
        pane.setPadding(new Insets(5, 5, 5, 5));
        ScrollPane scroll=new ScrollPane();
        scroll.getStyleClass().add("scrollPanel");
        scroll.setContent(pane);
        scroll.setPrefWidth(950);
        HBox btnpanel=new HBox();
        btnpanel.setAlignment(Pos.CENTER);
        //btnpanel.getChildren().add(btn);
        HBox scrl=new HBox();
        scrl.setAlignment(Pos.CENTER);
        scrl.getChildren().add(scroll);
        sceneRoot.topProperty().set(btnpanel);
        sceneRoot.centerProperty().set(scrl);
        pane.add(formLabel("OTHER PROPERTY"), 0, 0);
        if(configuration.getaCKE()!=null){
            pane.add(new ComboEditPanel(configuration.getaCKE()), 0, 1,2,1);
        }
        if(configuration.getrESC()!=null){
            pane.add(new ComboEditPanel(configuration.getrESC()), 0, 2,2,1);
        }
        Button readBtn=new Button("READ PROPERTY");
        Button writeBtn=new Button("WRITE PROPERTY");
        Button saveBtn=new Button("SAVE PROPERTY");
        Button loadBtn=new Button("LOAD PROPERTY");
        pane.add(readBtn,0,3);
        pane.add(writeBtn,0,4);
        pane.add(saveBtn,1,3);
        pane.add(loadBtn, 1, 4);
        readBtn.setOnAction(arg0 -> configuration.readValueRequest());
        writeBtn.setOnAction(arg0 -> configuration.writeValueRequest());
        loadBtn.setOnAction(arg0 -> {
            try {
                configuration.readFile(fileName);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText(e.getLocalizedMessage());
                alert.showAndWait();
            }
        });
        saveBtn.setOnAction(arg0 -> {
            try {
                configuration.writeConfigToFile(fileName);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText(e.getLocalizedMessage());
                alert.showAndWait();
            }
        });

        return sceneRoot;
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private Node formALL(){
        ObservableList<StringValuedPropertyFXAdapter> list= FXCollections.observableArrayList();
        TableView<StringValuedPropertyFXAdapter> table=new TableView<>(list);
        table.setEditable(true);
        TableColumn<StringValuedPropertyFXAdapter, String> col1=new TableColumn<>("Name");
        TableColumn<StringValuedPropertyFXAdapter, String> col2=new TableColumn<>("Description");
        TableColumn<StringValuedPropertyFXAdapter, String> col3=new TableColumn<>("Value");
        TableColumn<StringValuedPropertyFXAdapter, String> col4=new TableColumn<>("Diapason");
        col1.setCellValueFactory(new PropertyValueFactory("name"));
        col2.setCellValueFactory(new PropertyValueFactory("description"));
        //col3.setCellValueFactory(new PropertyValueFactory("value"));
        col4.setCellValueFactory(new PropertyValueFactory("diapason"));

        col3.setCellValueFactory(cell -> cell.getValue());

        table.getColumns().add(col1);
        table.getColumns().add(col2);
        table.getColumns().add(col3);
        table.getColumns().add(col4);
        col3.setPrefWidth(70);
        col3.setEditable(true);
        col3.setCellFactory(column -> new StringValueColumnEditor());
        configuration.getStream().forEach(ch->{
            if(ch instanceof StringValuedPropertry){
                StringValuedPropertry chanel=(StringValuedPropertry) ch;
                list.add(new StringValuedPropertyFXAdapter(chanel));
            }
        });
        return table;
    }


}

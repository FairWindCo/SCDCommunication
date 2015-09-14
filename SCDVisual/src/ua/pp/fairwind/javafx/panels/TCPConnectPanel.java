package ua.pp.fairwind.javafx.panels;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import ua.pp.fairwind.communications.devices.abstracts.DeviceInterface;
import ua.pp.fairwind.communications.lines.lineparams.CommunicationLineParameters;
import ua.pp.fairwind.communications.lines.lineparams.LineParameters;
import ua.pp.fairwind.javafx.I18N.I18N;

public class TCPConnectPanel extends StackPane{
	private Label portLabel=new Label(I18N.COMMON.getString("IPLabel"));
	private TextField ipaddress=new TextField("127.0.0.1");
	private Label ipLabel=new Label(I18N.COMMON.getString("IPPort"));
	private TextField ipport=new TextField("9000");
	private int pauseBeforeCommand=0;
	DeviceInterface dev=null;
	LineParameters safeparams;

	public TCPConnectPanel(DeviceInterface comuunicateDevice) {
		super();
		this.dev = comuunicateDevice;
        if(dev==null) throw new IllegalArgumentException("NULL Device not allowed!");
        initConrols();
	}

	private void initConrols(){
		Pane apanel=new Pane();
		VBox vbox = new VBox();		
		vbox.setStyle("-fx-background-color: #88EE53;");
		GridPane grid = new GridPane();
		vbox.getChildren().add(grid);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.add(ipLabel, 0, 0);
		grid.add(ipaddress, 1, 0);
		grid.add(portLabel, 0, 1);
		grid.add(ipport, 1, 1);
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color: #336699;");
        Button buttonConnect = new Button(I18N.COMMON.getString("SAVE"));
		buttonConnect.setPrefSize(100, 20);
		hbox.getChildren().addAll(buttonConnect);
		vbox.getChildren().add(hbox);
		apanel.getChildren().add(vbox);
		this.getChildren().add(apanel);
		
		
		buttonConnect.setOnAction(event -> {
			if(dev!=null){
				CommunicationLineParameters newparams=new CommunicationLineParameters(ipaddress.getText(), Integer.valueOf(ipport.getText()),0);
				dev.setLineParameters(newparams);
			}
        });

  }
	
	public void abort(){

	}
}

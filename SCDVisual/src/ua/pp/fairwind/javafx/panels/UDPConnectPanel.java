package ua.pp.fairwind.javafx.panels;


import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ua.pp.fairwind.io.lines.Line;
import ua.pp.fairwind.io.lines.adapters.UDPCommunicationLineAdapter;
import ua.pp.fairwind.javafx.I18N.I18N_monitor;

public class UDPConnectPanel extends StackPane{
	private Label portLabel=new Label(I18N_monitor.COMMON.getString("IPLabel"));
	private TextField ipaddress=new TextField("192.168.0.107");
	private Label ipLabel=new Label(I18N_monitor.COMMON.getString("IPPort"));
	private TextField ipport=new TextField("10001");
    private UDPCommunicationLineAdapter lineAdapter;
	private Line line;
	private int pauseBeforeCommand=0;


	
	public UDPConnectPanel(Line line, int address) {
		super();
		this.line = line;
        if(line==null) throw new IllegalArgumentException("NULL Line not allowed!");
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
        Button buttonConnect = new Button(I18N_monitor.COMMON.getString("CONNECT"));
		buttonConnect.setPrefSize(100, 20);
        Button buttonDisconnect = new Button(I18N_monitor.COMMON.getString("DISCONNECT"));
		buttonDisconnect.setPrefSize(100, 20);
		buttonDisconnect.setDisable(true);
		hbox.getChildren().addAll(buttonConnect, buttonDisconnect);
		vbox.getChildren().add(hbox);
		apanel.getChildren().add(vbox);
		this.getChildren().add(apanel);
		
		
		buttonConnect.setOnAction(event -> {
            lineAdapter = new UDPCommunicationLineAdapter(ipaddress.getText(), Integer.valueOf(ipport.getText()));
            line.setCommunicationAdapter(lineAdapter);
            line.start();
        });
		
		buttonDisconnect.setOnAction(event -> line.stop());
  }
	
	public void abort(){
		if(line!=null) {
			line.stop();
		}
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}
	
	

}

package ua.pp.fairwind.javafx.panels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import ua.pp.fairwind.io.lines.Line;
import ua.pp.fairwind.io.lines.adapters.TCPCommunicationLineAdapter;
import ua.pp.fairwind.javafx.guiElements.editors.IntegerInputText;
import ua.pp.fairwind.javafx.guiElements.menu.MenuExecutor;
import ua.pp.fairwind.javafx.guiElements.menu.PrefferedSize;
import ua.pp.fairwind.javafx.guiElements.windows.SimpleView;


public class TCPNetworkConnectionForm  extends SimpleView {
	Line comline;
	StringProperty address=new SimpleStringProperty();
	IntegerProperty port=new SimpleIntegerProperty();
	TCPCommunicationLineAdapter adapter=null;
	
	public TCPNetworkConnectionForm(String title, Image icon,
			PrefferedSize prefferedSize, Line comline) {
		super(title, icon, prefferedSize);
		this.comline = comline;
	}
		
	
	public TCPNetworkConnectionForm(String title,Line comline) {
		super(title, null,(PrefferedSize) null);
		this.comline = comline;
	}
	
	@Override
	public void onShow(MenuExecutor executr) {
		if(comline!=null){
			if(comline.getCommunicationAdapter() instanceof TCPCommunicationLineAdapter){
				adapter=(TCPCommunicationLineAdapter) comline.getCommunicationAdapter();
                address.setValue(adapter.getAddress());
                port.setValue(adapter.getPort());
                //port.setValue(1000);
				//comline.stop();
			}
		} else {
			adapter=null;
		}
		super.onShow(executr);
	}

	@Override
	public void onHide() {
		if(comline!=null){
			if(adapter!=null){
				//comline.start();
			}
			adapter=null;
		}
		super.onHide();
	}

	
	
	@Override
	protected Node createView() {
		if(comline!=null){
		GridPane pane= new GridPane();
        pane.setId("formGrid");
		pane.setAlignment(Pos.CENTER);
		pane.setVgap(10);
		pane.setHgap(10);
		pane.setPadding(new Insets(5, 5, 5, 5));
		Label lbl_IP=new Label("ADDRESS:");		
		lbl_IP.setId("formLabel");
		pane.add(lbl_IP, 0, 0);
		TextField adr=new TextField();
		adr.textProperty().bindBidirectional(address);
		pane.add(adr, 1, 0);
		Label lbl_Port=new Label("PORT:");		
		lbl_Port.setId("formLabel");
		pane.add(lbl_Port, 0, 1);
		IntegerInputText prt=new IntegerInputText();
		prt.getIntegerValueProperty().bindBidirectional(port);
		pane.add(prt, 1, 1);
		Button ok=new Button("SAVE");
		ok.onActionProperty().setValue(arg0 -> {
            if(comline!=null){
                comline.stop();
                if(comline.getCommunicationAdapter() instanceof TCPCommunicationLineAdapter){
                    adapter=(TCPCommunicationLineAdapter) comline.getCommunicationAdapter();
                    adapter.disconnect();
                }
                adapter=new TCPCommunicationLineAdapter(address.getValue(), port.intValue());
                comline.setCommunicationAdapter(adapter);
                //comline.setLineInPause(true);
                comline.start();
                closeWindow();
            }

        });
		Button cancel=new Button("RELOAD");
		cancel.onActionProperty().setValue(arg0 -> {
            if(comline.getCommunicationAdapter() instanceof TCPCommunicationLineAdapter){
                adapter=(TCPCommunicationLineAdapter) comline.getCommunicationAdapter();
                address.setValue(adapter.getAddress());
                port.setValue(adapter.getPort());
            }
        });
		pane.add(ok, 0, 2);
		pane.add(cancel, 1, 2);
		return pane;
		} else return null;
	}


	public StringProperty getAddress() {
		return address;
	}


	public IntegerProperty getPort() {
		return port;
	}
	
	
}

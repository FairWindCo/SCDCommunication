package ua.pp.fairwind.javafx.guiElements;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class HeaderPanel extends Pane{
	private String logo;
	private String caption;
	double height;
	MyBaseResourceLoader resloader;
	public HeaderPanel(double height,MyBaseResourceLoader resloader) {
		this(height,"headerPanel","favorit_logo.png","test",resloader);
	}
	
	public HeaderPanel(double height,String headerText,MyBaseResourceLoader resloader) {
		this(height,"headerPanel","favorit_logo.png",headerText,resloader);
	}
	
	public HeaderPanel(double height,String id,String logoname,String caption,MyBaseResourceLoader resloader) {
		super();
		setId(id);
		setHeight(height);
		setPrefHeight(height);
		this.height=height;
		this.logo=logoname;
		this.caption=caption;
		this.resloader=resloader;
		constructAdditionalElements();
		
	}
	
	protected void constructAdditionalElements(){
		ImageView view=new ImageView();
		if(resloader!=null){
		Image image=new Image(resloader.getExternalResourceURILink(logo));
		
			view.setImage(image);		
			view.setPreserveRatio(true);
		
		}
		//view.fitHeightProperty().setValue(height);
		view.setId("logo");
		Label label=new Label();
		label.setId("logoLabel");	
		label.setText(caption);
        HBox boxs=new HBox(view,label);
        boxs.setId("tlabel");
        boxs.setAlignment(Pos.CENTER);
		getChildren().add(boxs);
	}
}

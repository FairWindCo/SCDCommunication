package ua.pp.fairwind.javafx.panels;

import images.MyResourceLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import ua.pp.fairwind.javafx.guiElements.menu.PrefferedSize;
import ua.pp.fairwind.javafx.guiElements.windows.SimpleView;


public class InfoDialog extends SimpleView {
	public enum dialogstyle{
		NONE,
		INFO,
		ERROR,
		WARNING,
		QUEST,
		OK
    }
	
	private SimpleStringProperty texts=new SimpleStringProperty();
	private ImageView imageView=new ImageView(); 
	@Override
	protected Node createView() {
        BorderPane sceneRoot = new BorderPane();
        sceneRoot.setId("mainWindow");
        sceneRoot.setLayoutX(0);
        sceneRoot.setLayoutY(0);
        sceneRoot.setPadding(new Insets(3, 3, 3, 3));
		HBox lblPanel=new HBox();	
		HBox btnPanel=new HBox();
		lblPanel.setAlignment(Pos.CENTER);
		btnPanel.setAlignment(Pos.CENTER);
		//lblPanel.getStyleClass().add("basePanel");
		btnPanel.getStyleClass().add("basePanel");
		Label text=new Label();
		text.setWrapText(true);
		text.textProperty().bindBidirectional(texts);
		Button set=new Button("OK");
		lblPanel.getChildren().addAll(imageView,text);
		btnPanel.getChildren().add(set);
		
		set.setOnAction(arg0 -> closeWindow());
		
		sceneRoot.centerProperty().set(lblPanel);
		sceneRoot.bottomProperty().set(btnPanel);
		return sceneRoot;

	}

	public SimpleStringProperty getTexts() {
		return texts;
	}
	
	public void setText(String text){
		this.texts.set(text);
	}
	
	 public void setImage(Image img){
		 imageView.setImage(img);
	 }
	
	 public void setImage(MyResourceLoader resloader,String imageName){
		 if(resloader!=null && imageName!=null && imageName.length()>0){
			 String url=resloader.getExternalResourceURILink(imageName);
			 if(url!=null){
				 Image img=new Image(url);
                 imageView.setFitWidth(60d);
                 imageView.setFitHeight(60d);
                 imageView.setImage(img);
             }
		 
		 }
	 }
	 
	 public void setImage(MyResourceLoader resloader,dialogstyle style){
		 if(resloader!=null){
			 switch (style) {
			case OK:
				setImage(resloader, "ok-icon.png");
				break;
			case ERROR:
				setImage(resloader, "symbol-error.png");
				break;
			case INFO:
				setImage(resloader, "Info.png");
				break;
			case WARNING:
				setImage(resloader, "warning.png");
				break;		
			case QUEST:
				setImage(resloader, "question-mark.png");
				break;
			default:
				break;
			}
		 
		 }
	 }

	@Override
	public PrefferedSize getPrefferedSize() {
		return new PrefferedSize(160d, 300d);
	}
	 
	 
}

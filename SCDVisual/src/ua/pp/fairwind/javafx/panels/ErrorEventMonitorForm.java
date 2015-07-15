package ua.pp.fairwind.javafx.panels;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import ua.pp.fairwind.io.node.HardwareNodeEvent;
import ua.pp.fairwind.io.node.HardwareNodeListener;
import ua.pp.fairwind.javafx.guiElements.windows.SimpleView;

public class ErrorEventMonitorForm extends SimpleView implements HardwareNodeListener{
	private ObservableList<HardwareNodeEvent> events=FXCollections.observableArrayList();
	private int maxSize=100;
	
	@SuppressWarnings("unchecked")
	@Override
	protected Node createView() {
		BorderPane base=new BorderPane();
		HBox title=new HBox(80d);
		title.setAlignment(Pos.CENTER);
		base.topProperty().set(title);
		Label titleLabel=new Label("SYSTEM LOG");
		title.getChildren().add(titleLabel);
		titleLabel.getStyleClass().add("formLabel");
		TableView<HardwareNodeEvent> logTable=new TableView<>(events);
		BorderPane.setMargin(logTable, new Insets(1d));
		base.setCenter(logTable);
		TableColumn<HardwareNodeEvent, String> message=new TableColumn<>("MESSAGE");
		message.setMinWidth(350);
		TableColumn<HardwareNodeEvent, String> eventtype=new TableColumn<>("EVENT");
		TableColumn<HardwareNodeEvent, String> object=new TableColumn<>("OBJECT");
		TableColumn<HardwareNodeEvent, String> time=new TableColumn<>("Time");
		message.setCellValueFactory(new PropertyValueFactory<>("message"));
		eventtype.setCellValueFactory(new PropertyValueFactory<>("level"));
		time.setCellValueFactory(new PropertyValueFactory<>("time"));
		//object.setCellValueFactory(new PropertyValueFactory<ErrorEvent,String>("source"));
		object.setCellValueFactory(arg0 -> {
            if(arg0!=null && arg0.getValue()!=null && arg0.getValue().getSource()!=null){
                return new SimpleStringProperty(arg0.getValue().getSource().getClass().getSimpleName());
            } else{
                return new SimpleStringProperty("null");
            }
        });
		
		/*
		message.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ErrorEvent,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<ErrorEvent, String> arg0) {
				if(arg0!=null && arg0.getValue()!=null){
					return new SimpleStringProperty(arg0.getValue().getMessage());
				} else{
					return new SimpleStringProperty("null");
				}
			}
		});

		eventtype.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ErrorEvent,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<ErrorEvent, String> arg0) {
				if(arg0!=null && arg0.getValue()!=null && arg0.getValue().getLevel()!=null){
					return new SimpleStringProperty(arg0.getValue().getLevel().toString());
				} else{
					return new SimpleStringProperty("null");
				}
			}
		});		

		object.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ErrorEvent,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<ErrorEvent, String> arg0) {
				if(arg0!=null && arg0.getValue()!=null && arg0.getValue().getSource()!=null){
					return new SimpleStringProperty(arg0.getValue().getSource().getClass().getCanonicalName());
				} else{
					return new SimpleStringProperty("null");
				}
			}
		});			
		
		time.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ErrorEvent,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<ErrorEvent, String> arg0) {				
				if(arg0!=null && arg0.getValue()!=null){
					return new SimpleStringProperty(arg0.getValue().getTime().toString());
				} else{
					return new SimpleStringProperty("null");
				}
			}
		});		/**/
		logTable.getColumns().addAll(eventtype,time,message,object);/**/
		logTable.autosize();
		return base;

    }



	
	synchronized public void println(String message){
		errorRecived(new HardwareNodeEvent(null, message, HardwareNodeEvent.EventLevel.ERROR));
	}
	
	synchronized public void println(String message,HardwareNodeEvent.EventLevel level){
		errorRecived(new HardwareNodeEvent(null, message,level));
	}



	synchronized public void errorRecived(final HardwareNodeEvent referense) {
		if(referense!=null && events!=null){
			//System.out.println(referense);
			if(Platform.isFxApplicationThread()){
				events.add(0, referense);
				if(maxSize<events.size()){
					events.remove(maxSize-1,events.size());
				}
        	} else {
        		try{
	        		Platform.runLater(() -> {
                      events.add(0, referense);
                      if(maxSize<events.size()){
                          events.remove(maxSize-1,events.size());
                      }
                    });
        		} catch (IllegalStateException ex){      			
        			events.add(0, referense);
        			if(maxSize<events.size()){
        				events.remove(maxSize-1,events.size());
        			}
                }
        	}				
			
		}
	}

    @Override
    public void eventFromHardwareNode(HardwareNodeEvent event) {
        errorRecived(event);
    }
}

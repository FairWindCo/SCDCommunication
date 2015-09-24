package ua.pp.fairwind.javafx.guiElements.editors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Cell;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.Tooltip;
import ua.pp.fairwind.communications.propertyes.software.stringlike.StringValuedPropertry;
import ua.pp.fairwind.io.javafx.propertys.StringValuedPropertyFXAdapter;

public class StringValueColumnEditor extends TableCell<StringValuedPropertyFXAdapter,String> {
	
	
    public StringValueColumnEditor() {

	}



	@SuppressWarnings("unchecked")
	@Override public void startEdit() {
		ComboBox<String> dbox=null;
		//System.out.println("USER DATA:"+getTableRow().getUserData());
		if(getTableRow().getUserData() instanceof ComboBox<?>){
			dbox=(ComboBox<String>) getTableRow().getUserData();
		}
		final ComboBox<String> comboBox;
		final StringValuedPropertyFXAdapter adapter=getTableView().getSelectionModel().getSelectedItem();
		final StringValuedPropertry editCahnel=(StringValuedPropertry) adapter.getProperty();
		
    	if (! isEditable() || ! getTableView().isEditable() || ! getTableColumn().isEditable()) {
  
    		setText(getItem());
    		return;
    	}         
    	
    	if(dbox!=null){
    		comboBox=dbox;
    	} else {
    		/*
        	adapter.addListener(new ChangeListener<String>() {

    			@Override
    			public void changed(ObservableValue<? extends String> arg0,
    					String arg1, String arg2) {
    				System.out.println(arg1+" <> "+arg2);
    				
    			}
    		});/**/

    		comboBox = new ComboBox<>();
    		comboBox.valueProperty().bindBidirectional(adapter);
    		comboBox.setTooltip(new Tooltip(editCahnel.getDiapason()));
    		comboBox.setEditable(true);
    		getTableRow().setUserData(comboBox);
    		
    		if(editCahnel.getCorrectValues()!=null){
    			for(String correct:editCahnel.getCorrectValues()){
    				comboBox.getItems().add(correct);
    			}
    		}
    		comboBox.valueProperty().addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> obj, String oldvl, String newvl) {
						//editCahnel.setValue(newvl);
						//editor.setValue(editCahnel.getValue());
                        if(oldvl!=null){
                            if(newvl!=null){
                                String testval=editCahnel.getValue();
                                if(!newvl.equalsIgnoreCase(testval)){
                                    if(editCahnel.isCorrectValue(newvl)){
                                        //comboBox.setValue(newvl);
                                        //adapter.setValue(newvl);
                                    } else {
                                        comboBox.setValue(testval);
                                    }
                                }
                            } else {
                                comboBox.setValue(oldvl);
                            }
                        } else {
                            if(newvl!=null){
                                String testval=editCahnel.getValue();
                                if(!newvl.equalsIgnoreCase(testval)){
                                    comboBox.setValue(newvl);
                                }
                            }
                        }
                        //System.out.println("NEW ITEM:"+comboBox.getValue());
						setItem(comboBox.getValue());
					}
					});       		
    }


	comboBox.getSelectionModel().select(getItem());

	super.startEdit();
	setText(null);
	setGraphic(comboBox);
    }



    	@Override public void cancelEdit() {
    		super.cancelEdit();
    		setText(getItem());
    		setGraphic(null);
    	}



    @Override public void updateItem(String item, boolean empty) {    	
    	super.updateItem(item, empty);
    	myupdateItem(this, null,getTableRow().getIndex());
    	//mupdateItem(this, getConverter(), null, null, comboBox);

    }
	
	

	@Override
	public void commitEdit(String val) {
		System.out.println("NEW VAL"+val);
        StringValuedPropertyFXAdapter adapter=getTableView().getSelectionModel().getSelectedItem();
		
		if(adapter!=null){
			val=adapter.getValue();
		}
			
		System.out.println(val);
		super.commitEdit(val);
	}



	void myupdateItem(final Cell<String> cell, final Node graphic,int rowIndex) {
		//getTableView().getItems().get(index)
			if (cell.isEmpty()) {
					cell.setText(null);					
					cell.setGraphic(null);
						
			} else {
				if (cell.isEditing()) {

				} else {
					//cell.setText(getTableView().getItems().get(rowIndex).getValue());
					String itm=getItem();
					//System.out.println(itm);
					cell.setText(itm);
					cell.setGraphic(graphic);
				}
			}
		}


}

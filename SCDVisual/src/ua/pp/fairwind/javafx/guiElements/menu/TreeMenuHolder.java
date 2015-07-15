package ua.pp.fairwind.javafx.guiElements.menu;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import ua.pp.fairwind.javafx.guiElements.windows.SimpleView;

public class TreeMenuHolder implements EventHandler<MouseEvent>{
	private ArrayList<MenuConfigElements> menus=new ArrayList<>();
	private TreeView<MenuConfigElements> visualMenu=null;
	final private MenuExecutor executor;
	boolean fomr=false;
	
	
	public TreeMenuHolder(MenuExecutor executor,boolean formStandartMenu) {
		super();
		this.executor = executor;
		if(formStandartMenu){
			formStandartMenu();
		}
	}

	protected void formStandartMenu(){
		MenuConfigElements quit=new MenuConfigElements("�����","exit");
		MenuConfigElements view=new MenuConfigElements("view",new SimpleView());
		MenuConfigElements file=new MenuConfigElements("����","",null,view,quit);
		MenuConfigElements about=new MenuConfigElements("� ���������","showdialog",new SimpleView("about..."));
		MenuConfigElements help=new MenuConfigElements("������","",null,about);
		
		menus.add(file);
		menus.add(help);
	}
	
	public void setMenuPoint(MenuConfigElements menuItem){
		menus.add(menuItem);
		fomr=false;
	}
	
	public void removeMenuPoint(MenuConfigElements menuItem){
		menus.remove(menuItem);
		fomr=false;
	}
	
	protected TreeItem<MenuConfigElements> formVisualMenu(MenuConfigElements item,boolean topLevel){
		if(item!=null){
			TreeItem<MenuConfigElements> curent=new TreeItem<>(item);
			if(item.getChilds()!=null){				
				for(MenuConfigElements element:item.getChilds()){
					curent.getChildren().add(formVisualMenu(element,false));
				}				
			}
			return curent;
		} else {
			return null;
		}
	}
	
	public TreeView<MenuConfigElements> createMenu(){		
		if(visualMenu!=null && fomr){
			return visualMenu;
		} else {
			TreeItem<MenuConfigElements> root=new TreeItem<>();
			for(MenuConfigElements menu:menus){
				root.getChildren().add(formVisualMenu(menu,true));
			}		
			visualMenu=new TreeView<>(root);
			visualMenu.setShowRoot(false);
			visualMenu.setOnMouseClicked(this);
			return visualMenu;
		}		
	}

	public void handless(ActionEvent event) {		
		if(executor!=null && event.getSource() instanceof MenuItem){
			MenuItem mitem=(MenuItem) event.getSource();
			if(mitem.getUserData() instanceof MenuConfigElements){
				//System.out.println("menu: "+mitem.getText());
				executor.menuExecutor((MenuConfigElements) mitem.getUserData());
			}
		}
	}

	@Override
	public void handle(MouseEvent event) {
		if(executor!=null && visualMenu!=null && event.getClickCount()==2){
			TreeItem<MenuConfigElements> item = visualMenu.getSelectionModel().getSelectedItem();
            executor.menuExecutor(item.getValue());
        }
		
	}
}

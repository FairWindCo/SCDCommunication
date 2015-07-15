package ua.pp.fairwind.javafx.guiElements.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import ua.pp.fairwind.javafx.guiElements.windows.SimpleView;

public class MenuHolder implements EventHandler<ActionEvent>{
	private ArrayList<MenuConfigElements> menus=new ArrayList<>();
	private ArrayList<Menu> visualMenu=null;
	final private MenuExecutor executor;
	boolean fomr=false;
	private final EventHandler<Event> onMenuShow=new EventHandler<Event>() {

		@Override
		public void handle(Event event) {
			if(executor!=null && event.getSource() instanceof MenuItem){
				MenuItem mitem=(MenuItem) event.getSource();
				if(mitem.getUserData() instanceof MenuConfigElements){
					//System.out.println("menu: "+mitem.getText());
					executor.menuExecutor((MenuConfigElements) mitem.getUserData());
				}
			}			
		}
	};
	
	
	public MenuHolder(MenuExecutor executor,boolean formStandartMenu) {
		super();
		this.executor = executor;
		if(formStandartMenu){
			formStandartMenu();
		}
	}

	protected void formStandartMenu(){
		MenuConfigElements quit=new MenuConfigElements("Выход","exit");
		//MenuConfigElements view=new MenuConfigElements("view1",new SimpleView());
		//MenuConfigElements view2=new MenuConfigElements("view2",new TreeViewWindow("test",new TreeMenuHolder(executor,true)));
		//MenuConfigElements file=new MenuConfigElements("����","",null,view,view2,quit);
		MenuConfigElements file=new MenuConfigElements("Файл","",null,quit);
		MenuConfigElements about=new MenuConfigElements("О программе","showdialog",new SimpleView("about..."));
		MenuConfigElements help=new MenuConfigElements("Помощь","",null,about);
		
		menus.add(file);
		menus.add(help);
	}
	
	public void setMenuPoint(MenuConfigElements menuItem){
		menus.add(menuItem);
		fomr=false;
	}
	
	public void setMenuPoints(List<MenuConfigElements> menuItems){
		if(menuItems!=null)
		for(MenuConfigElements menuItem:menuItems)menus.add(menuItem);
		fomr=false;
	}

    public void setMenuPoints(MenuConfigElements... menuItems){
        if(menuItems!=null)
            Collections.addAll(menus, menuItems);
        fomr=false;
    }
	
	public void removeMenuPoint(MenuConfigElements menuItem){
		menus.remove(menuItem);
		fomr=false;
	}
	
	protected MenuItem formVisualMenu(MenuConfigElements item,boolean topLevel){
		if(item!=null){
			MenuItem curent;
			if(item.getCreatedMenu()==null){
				if(item.getChilds()!=null || topLevel){
                    curent=new Menu(item.getName());
				} else {
                    curent=new MenuItem(item.getName());
				}
				curent.setUserData(item);
				item.setCreatedMenu(curent);
			} else {
				curent=item.getCreatedMenu();
			}		
			if(item.getChilds()!=null){
				Menu nmenu=((Menu)curent);
				nmenu.getItems().clear();
				for(MenuConfigElements element:item.getChilds()){
					nmenu.getItems().add(formVisualMenu(element,false));
				}
				
			} else {
				if(topLevel){					
					curent.setOnMenuValidation(onMenuShow);
				}
				curent.setOnAction(this);
			}
			return curent;
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Menu> createMenu(){
		if(visualMenu!=null && fomr){
			return (List<Menu>) visualMenu.clone();
		} else {
			visualMenu=new ArrayList<>();
			for(MenuConfigElements menu:menus){
				visualMenu.add((Menu) formVisualMenu(menu,true));
			}			
			return (List<Menu>) visualMenu.clone();
		}		
	}

	@Override
	public void handle(ActionEvent event) {		
		if(executor!=null && event.getSource() instanceof MenuItem){
			MenuItem mitem=(MenuItem) event.getSource();
			if(mitem.getUserData() instanceof MenuConfigElements){
				//System.out.println("menu: "+mitem.getText());
				executor.menuExecutor((MenuConfigElements) mitem.getUserData());
			}
		}
	}
}
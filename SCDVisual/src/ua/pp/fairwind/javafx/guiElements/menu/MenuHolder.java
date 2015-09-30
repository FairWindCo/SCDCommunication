package ua.pp.fairwind.javafx.guiElements.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import ua.pp.fairwind.javafx.I18N.I18N_FX;
import ua.pp.fairwind.javafx.guiElements.windows.SimpleView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuHolder implements EventHandler<ActionEvent>{
	private ArrayList<MenuConfigElements> menus=new ArrayList<>();
	private ArrayList<Menu> visualMenu=null;
	final private MenuExecutor executor;
	boolean fomr=false;

	
	
	public MenuHolder(MenuExecutor executor,boolean formStandartMenu) {
		super();
		this.executor = executor;
		if(formStandartMenu){
			formStandartMenu();
		}
	}

	protected void formStandartMenu(){
		MenuConfigElements quit=new MenuConfigElementsExitAction("EXIT");
		//MenuConfigElements view=new MenuConfigElements("view1",new SimpleView());
		//MenuConfigElements view2=new MenuConfigElements("view2",new TreeViewWindow("test",new TreeMenuHolder(executor,true)));
		//MenuConfigElements file=new MenuConfigElements("����","",null,view,view2,quit);
		MenuConfigElements file=new MenuConfigElements("FILE","",null,quit);
		MenuConfigElements about=new MenuConfigElementsForm("ABOUT", Modality.NONE,new SimpleView(I18N_FX.getLocalizedString("APP_ABOUT_TEXT")));
		MenuConfigElements help=new MenuConfigElements("HELP","",null,about);
		
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
					//Tooltip.install(curent,new Tooltip(item.getHint()));
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

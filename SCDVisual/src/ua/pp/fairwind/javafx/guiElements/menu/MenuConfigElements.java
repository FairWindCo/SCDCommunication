package ua.pp.fairwind.javafx.guiElements.menu;

import javafx.scene.control.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MenuConfigElements {
	private List<MenuConfigElements> child;
	final private String name;
	final private String hint;
	private MenuItem createdMenu;
	private boolean enabledMenu=true;


    public MenuConfigElements(String name, String hint) {
        this.name = name;
        this.hint = hint;
    }

	public MenuConfigElements(String name) {
		this.name = name;
		this.hint = null;
	}

	public MenuConfigElements(String name, String hint,MenuConfigElements... childs) {
		this.name = name;
		this.hint = hint;
		setAddChilds(childs);
	}

	public MenuConfigElements(String name,MenuConfigElements... childs) {
		this.name = name;
		this.hint = null;
		setAddChilds(childs);
	}


	public void setAddChild(MenuConfigElements children){
		if(children!=null){
		if(child==null){
			child=new ArrayList<>();
		}
			child.add(children);
		}
	}

    public void setAddChilds(MenuConfigElements... childrens){
        if(childrens!=null){
            for(MenuConfigElements children:childrens){
                setAddChild(children);
            }
        }
    }
	
	public void setAddChilds(List<MenuConfigElements> childrens){
		if(childrens!=null){
			for(MenuConfigElements children:childrens){
				setAddChild(children);
			}
		}
	}
	
	public MenuConfigElements[] getChilds() {
		if(child==null)return null;
		return child.toArray(new MenuConfigElements[child.size()]);
	}

	public String getName() {
		return name;
	}


	public String getHint() {
		return hint;
	}




	public MenuItem getCreatedMenu() {		
		return createdMenu;
	}

	public void setCreatedMenu(MenuItem createdMenu) {
		if(createdMenu!=null)createdMenu.setDisable(!enabledMenu);
		this.createdMenu = createdMenu;
	}



	@Override
	public String toString() {
		return name;
	}

	public boolean isEnabledMenu() {
		return enabledMenu;
	}

	public void setEnabledMenu(boolean enabledMenu) {
		this.enabledMenu = enabledMenu;
		if(createdMenu!=null) createdMenu.setDisable(!enabledMenu);
	}			

}

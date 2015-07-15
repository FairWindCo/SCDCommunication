package ua.pp.fairwind.javafx.guiElements.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.control.MenuItem;
import ua.pp.fairwind.javafx.guiElements.windows.ApplicationView;

public class MenuConfigElements {
	private List<MenuConfigElements> child;
	private String name;
	private String title;
	private String hint;
	final private ApplicationView forms;
	final private String command;
    final private ExecuteMenuInerface executeAction;
	private MenuItem createdMenu;	
	private PrefferedSize preferedSize;
	private boolean enabledMenu=true;

    public MenuConfigElements(String name, ExecuteMenuInerface executeAction) {
        this.name = name;
        this.executeAction = executeAction;
        command="ExecuteAction";
        forms=null;
    }

    public MenuConfigElements(String name, String title, String hint, ExecuteMenuInerface executeAction) {
        this.name = name;
        this.title = title;
        this.hint = hint;
        this.executeAction = executeAction;
        command="ExecuteAction";
        forms=null;
    }

    public MenuConfigElements(String name, String command,ApplicationView forms) {
		super();
		this.name = name;
		this.forms = forms;
		this.command = command;
		this.child=null;
        executeAction=null;
	}

	public MenuConfigElements(String name,
			String hint, String command,MenuConfigElements... child) {
		super();
		this.child = new ArrayList<>();
        Collections.addAll(this.child, child);
		this.name = name;
		this.hint = hint;
		this.command = command;
        executeAction=null;
        forms=null;
	}

	public MenuConfigElements(String name, String command) {
		super();
		this.name = name;
		this.command = command;
		this.child=null;
        executeAction=null;
        forms=null;
	}

	public MenuConfigElements(String name, ApplicationView forms) {
		super();
		this.name = name;
		this.forms = forms;
		this.child=null;
        executeAction=null;
        command=null;
	}

	public MenuConfigElements(String name, ApplicationView forms,MenuConfigElements... child) {
		super();
		this.child = new ArrayList<>();
        Collections.addAll(this.child, child);
		this.name = name;
		this.forms = forms;
        executeAction=null;
        command=null;
	}

	public MenuConfigElements(String name,
			String title, String hint, ApplicationView forms,MenuConfigElements... child) {
		super();
		this.child = new ArrayList<>();
        Collections.addAll(this.child, child);
		this.name = name;
		this.title = title;
		this.hint = hint;
		this.forms = forms;
        executeAction=null;
        command=null;
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

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public ApplicationView getForms() {
		return forms;
	}


	public String getCommand() {
		return command;
	}


	public MenuItem getCreatedMenu() {		
		return createdMenu;
	}

	public void setCreatedMenu(MenuItem createdMenu) {
		if(createdMenu!=null)createdMenu.setDisable(!enabledMenu);
		this.createdMenu = createdMenu;
	}

	public PrefferedSize getPreferedSize() {
		return preferedSize;
	}

	public void setPreferedSize(PrefferedSize preferedSize) {
		this.preferedSize = preferedSize;
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
	
    public void executeAction(MenuExecutor executor){
        if(executeAction!=null)executeAction.executeMenuItem(executor,this);
    }
	
	
}

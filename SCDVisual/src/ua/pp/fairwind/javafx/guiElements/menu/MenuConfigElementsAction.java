package ua.pp.fairwind.javafx.guiElements.menu;

public class MenuConfigElementsAction extends MenuConfigElements {
	final private ExecuteMenuInerface executeAction;
	final private MenuExecutor executor;

	public MenuConfigElementsAction(String name, ExecuteMenuInerface executeAction) {
		super(name);
        this.executeAction = executeAction;
		executor=null;
    }

    public MenuConfigElementsAction(String name, String hint, ExecuteMenuInerface executeAction) {
		super(name,hint);
        this.executeAction = executeAction;
		executor=null;
    }

	public MenuConfigElementsAction(String name, MenuExecutor executor,ExecuteMenuInerface executeAction) {
		super(name);
		this.executeAction = executeAction;
		this.executor=executor;
	}

	public MenuConfigElementsAction(String name, MenuExecutor executor,String hint, ExecuteMenuInerface executeAction) {
		super(name,hint);
		this.executeAction = executeAction;
		this.executor=executor;
	}


	
    public void executeAction(MenuExecutor executor){
        if(executeAction!=null)executeAction.executeMenuItem(executor,this);
    }

	public void executeAction(){
		if(executeAction!=null && executor!=null)executeAction.executeMenuItem(executor,this);
	}

}

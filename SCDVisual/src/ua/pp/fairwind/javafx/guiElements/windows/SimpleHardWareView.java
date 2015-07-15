package ua.pp.fairwind.javafx.guiElements.windows;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import ua.pp.fairwind.io.node.HardwareNode;
import ua.pp.fairwind.io.node.HardwareNodesHolders;
import ua.pp.fairwind.javafx.guiElements.MyBaseResourceLoader;
import ua.pp.fairwind.javafx.guiElements.menu.MenuExecutor;
import ua.pp.fairwind.javafx.guiElements.menu.PrefferedSize;

public class SimpleHardWareView implements ApplicationView {
    static private long curentID=0;
	final private long id=curentID++;
    private String title;
	private Image icon;
	protected PrefferedSize prefferedSize;
	protected Scene view;
	private boolean resizable=true;
	private MenuExecutor executr;
	private MyBaseResourceLoader resloader;
    private final HardwareNodesHolders<HardwareNode> hardwareNodesHolders;
	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public Image getIcon() {
		return icon;
	}

    public long getID() {
        return id;
    }

    protected MenuExecutor getExecutr() {
		return executr;
	}

	public SimpleHardWareView(String title, Image icon, PrefferedSize prefferedSize,HardwareNodesHolders<HardwareNode> hardwareNodesHolders) {
		super();
		this.title = title;
		this.icon = icon;
		this.prefferedSize = prefferedSize;
        this.hardwareNodesHolders=hardwareNodesHolders;
	}

	public SimpleHardWareView(String title, Image icon, PrefferedSize prefferedSize,
                              Scene view,HardwareNodesHolders<HardwareNode> hardwareNodesHolders) {
		super();
		this.title = title;
		this.icon = icon;
		this.prefferedSize = prefferedSize;
		this.view = view;
        this.hardwareNodesHolders=hardwareNodesHolders;
	}

	public SimpleHardWareView(String title, Image icon,HardwareNodesHolders<HardwareNode> hardwareNodesHolders) {
		super();
		this.title = title;
		this.icon = icon;
        this.hardwareNodesHolders=hardwareNodesHolders;
	}

	public SimpleHardWareView(String title,HardwareNodesHolders<HardwareNode> hardwareNodesHolders) {
		super();
		this.title = title;
        this.hardwareNodesHolders=hardwareNodesHolders;
	}

	public SimpleHardWareView(HardwareNodesHolders<HardwareNode> hardwareNodesHolders) {
		super();
        this.hardwareNodesHolders=hardwareNodesHolders;
	}

	public SimpleHardWareView(String title, Image icon, PrefferedSize prefferedSize, MyBaseResourceLoader resloader,HardwareNodesHolders<HardwareNode> hardwareNodesHolders) {
		super();
		this.title = title;
		this.icon = icon;
		this.prefferedSize = prefferedSize;
		this.resloader=resloader;
        this.hardwareNodesHolders=hardwareNodesHolders;
	}

	public SimpleHardWareView(String title, Image icon, PrefferedSize prefferedSize,
                              Scene view, MyBaseResourceLoader resloader,HardwareNodesHolders<HardwareNode> hardwareNodesHolders) {
		super();
		this.title = title;
		this.icon = icon;
		this.prefferedSize = prefferedSize;
		this.view = view;
		this.resloader=resloader;
        this.hardwareNodesHolders=hardwareNodesHolders;
	}

	public SimpleHardWareView(String title, Image icon, MyBaseResourceLoader resloader,HardwareNodesHolders<HardwareNode> hardwareNodesHolders) {
		super();
		this.title = title;
		this.icon = icon;
		this.resloader=resloader;
        this.hardwareNodesHolders=hardwareNodesHolders;
	}

	public SimpleHardWareView(String title, MyBaseResourceLoader resloader,HardwareNodesHolders<HardwareNode> hardwareNodesHolders) {
		super();
		this.title = title;
		this.resloader=resloader;
        this.hardwareNodesHolders=hardwareNodesHolders;
	}

	public SimpleHardWareView(MyBaseResourceLoader resloader,HardwareNodesHolders<HardwareNode> hardwareNodesHolders) {
		super();
		this.resloader=resloader;
        this.hardwareNodesHolders=hardwareNodesHolders;
	}

	@Override
	public PrefferedSize getPrefferedSize() {
		return prefferedSize;
	}

	protected Node createView(){
		return new Group();
	}

	@Override
	public Scene getMainView() {
		if(view==null){

			view = new Scene((Parent) createView());
		}
		return view;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
	}

	public void setPrefferedSize(PrefferedSize prefferedSize) {
		this.prefferedSize = prefferedSize;
	}

	public SimpleHardWareView(String title, Image icon, PrefferedSize prefferedSize,
                              boolean resizable,HardwareNodesHolders<HardwareNode> hardwareNodesHolders) {
		super();
		this.title = title;
		this.icon = icon;
		this.prefferedSize = prefferedSize;
		this.resizable = resizable;
        this.hardwareNodesHolders=hardwareNodesHolders;
	}

	public SimpleHardWareView(String title, Image icon, PrefferedSize prefferedSize,
                              Scene view, boolean resizable,HardwareNodesHolders<HardwareNode> hardwareNodesHolders) {
		super();
		this.title = title;
		this.icon = icon;
		this.prefferedSize = prefferedSize;
		this.view = view;
		this.resizable = resizable;
        this.hardwareNodesHolders=hardwareNodesHolders;
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

	@Override
	public boolean isResizable() {
		return resizable;
	}
	
	public void closeWindow(){
		if(executr!=null)executr.closeView(this);
	}

	@Override
	public void onShow(MenuExecutor executor) {	
		this.executr=executor;
	}

	@Override
	public void onHide() {	
	}

	@Override
	public void setResourceLoader(MyBaseResourceLoader resloader) {
		this.resloader=resloader;
		
	}

	@Override
	public MyBaseResourceLoader getResourceLoader() {
		return resloader;
	}

	
}

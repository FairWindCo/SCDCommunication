package ua.pp.fairwind.javafx.guiElements.windows;

import images.MyResourceLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ua.pp.fairwind.javafx.guiElements.ButtonPanel;
import ua.pp.fairwind.javafx.guiElements.HeaderPanel;
import ua.pp.fairwind.javafx.guiElements.menu.MenuExecutor;
import ua.pp.fairwind.javafx.guiElements.menu.PrefferedSize;
import ua.pp.fairwind.javafx.guiElements.menu.TreeMenuHolder;


public class TreeViewWindow extends SimpleView implements ApplicationWindow {
		private MenuExecutor mainMenuExecutor;
		private TreeMenuHolder menuHold;
		private BorderPane sceneRoot;
		Node buttonPanel=new ButtonPanel(29);
		Node headerPanel=null;
		private ApplicationView curentView=null;
		
		
		
		public TreeViewWindow(String title, Image icon, PrefferedSize prefferedSize,
				MenuExecutor mainMenuExecutor, TreeMenuHolder menuHold,
				BorderPane sceneRoot, Node buttonPanel, Node headerPanel, MyResourceLoader resloader) {
			super(title, icon, prefferedSize,resloader);
			this.mainMenuExecutor = mainMenuExecutor;
			this.menuHold = menuHold;
			this.sceneRoot = sceneRoot;
			this.buttonPanel = buttonPanel;
			if(headerPanel==null){
				this.headerPanel=new HeaderPanel(66,resloader);
			} else {
				this.headerPanel = headerPanel;
			}
		}
		
		public TreeViewWindow(String title, Image icon, PrefferedSize prefferedSize,
				MenuExecutor mainMenuExecutor, TreeMenuHolder menuHold,MyResourceLoader resloader) {
			super(title, icon, prefferedSize,resloader);
			this.mainMenuExecutor = mainMenuExecutor;
			this.menuHold = menuHold;		
		}

		public TreeViewWindow(String title, PrefferedSize prefferedSize,TreeMenuHolder menuHold,MyResourceLoader resloader){
			this(title, null, prefferedSize,null,menuHold,resloader);
		}
		
		public TreeViewWindow(String title,TreeMenuHolder menuHold,MyResourceLoader resloader){
			this(title, null, null,null,menuHold,resloader);
		}
		
		public TreeViewWindow(String title,MyResourceLoader resloader){
			this(title, null, null,null,null,resloader);
		}
		
		public TreeViewWindow(MyResourceLoader resloader){
			this(null, null, null,null,null,resloader);
		}
		
		@Override
		public Scene getWindow(){
			if(prefferedSize==null)prefferedSize=new PrefferedSize(500, 600);
            sceneRoot =new BorderPane();
            sceneRoot.setId("mainWindow");
            sceneRoot.setLayoutX(0);
            sceneRoot.setLayoutY(0);
            sceneRoot.setPadding(new Insets(3, 3, 3, 3));

            Scene sceneRef = new Scene(sceneRoot,prefferedSize.getWidth(),prefferedSize.getHeight());
			sceneRef.getStylesheets().addAll(getResourceLoader().getExternalResourceURILink("application.css"));
		
			sceneRoot.topProperty().set(createTitleBar());
			sceneRoot.bottomProperty().set(createButtonBar());
			sceneRoot.centerProperty().set(getViewPanel());
			Node menus=createMenuBar();
			if(menus!=null){
				System.out.println("form tree");
				sceneRoot.leftProperty().set(menus);
			}
			view=sceneRef;
			
			
			return sceneRef;
		}

		protected Node createMenuBar(){
			if(menuHold!=null){				
				return menuHold.createMenu();
			} else {
				return null;
			}
		}
		
		protected Node createTitleBar(){		
				return new VBox(headerPanel);
		}
		
		protected Node createButtonBar(){
			return buttonPanel;
		}
		
		@Override
		public void changeView(ApplicationView newView) {
			if(newView.getMainView()!=null){
				if(curentView!=null) curentView.onHide();
				curentView=newView;
				sceneRoot.centerProperty().setValue(newView.getMainView().getRoot());
			}
		}

		@Override
		public Node getTitlePanel() {
			return createTitleBar();
		}

		@Override
		public Node getButtomPanel() {
			return createButtonBar();
		}

		@Override
		public Node getViewPanel() {
			Pane mainPanel=new Pane();
			mainPanel.setId("mainPanel");
			return mainPanel;
		}

		public MenuExecutor getMainMenuExecutor() {
			return mainMenuExecutor;
		}

		public void setMainMenuExecutor(MenuExecutor mainMenuExecutor) {
			this.mainMenuExecutor = mainMenuExecutor;
			if(mainMenuExecutor!=null){
				mainMenuExecutor.setParentView(this);
			}
		}

		@Override
		public Scene getMainView() {
			if(view==null)getWindow();
			return view;
		}

		@Override
		public Stage formStage(Stage stage) {
			if(stage==null){
				stage=new Stage();
			}
			stage.setScene(getMainView());
			if(getTitle()!=null) stage.setTitle(getTitle());
			if(getIcon()!=null) stage.getIcons().add(getIcon());
			stage.setResizable(isResizable());
			stage.setOnCloseRequest(arg0 -> {
                if(mainMenuExecutor!=null)mainMenuExecutor.closeAllDialogs();
            });
			return stage;
		}

		public TreeMenuHolder getMenuHold() {
			return menuHold;
		}

		public void setMenuHold(TreeMenuHolder menuHold) {
			this.menuHold = menuHold;
		}

		public BorderPane getSceneRoot() {
			return sceneRoot;
		}

		public void setSceneRoot(BorderPane sceneRoot) {
			this.sceneRoot = sceneRoot;
		}

		public Node getButtonPanel() {
			return buttonPanel;
		}

		public void setButtonPanel(Node buttonPanel) {
			this.buttonPanel = buttonPanel;
		}

		public Node getHeaderPanel() {
			return headerPanel;
		}

		public void setHeaderPanel(Node headerPanel) {
			this.headerPanel = headerPanel;
		}

		@Override
		public void destroy() {
			if(curentView!=null) curentView.onHide();
		}

		@Override
		public void close() {
			if(curentView!=null){
				sceneRoot.centerProperty().setValue(null);
			}			
		}	


}

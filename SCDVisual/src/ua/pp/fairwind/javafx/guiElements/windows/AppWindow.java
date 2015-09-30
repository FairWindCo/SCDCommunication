package ua.pp.fairwind.javafx.guiElements.windows;

import images.MyResourceLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ua.pp.fairwind.communications.messagesystems.event.ElementEventListener;
import ua.pp.fairwind.javafx.I18N.I18N_FX;
import ua.pp.fairwind.javafx.guiElements.ButtonPanel;
import ua.pp.fairwind.javafx.guiElements.HeaderPanel;
import ua.pp.fairwind.javafx.guiElements.menu.MenuExecutor;
import ua.pp.fairwind.javafx.guiElements.menu.MenuHolder;
import ua.pp.fairwind.javafx.guiElements.menu.PrefferedSize;
import ua.pp.fairwind.javafx.panels.InfoBar;

import java.util.List;


public class AppWindow extends SimpleView implements ApplicationWindow {
		private MenuExecutor mainMenuExecutor;
		private MenuHolder menuHold;
		private BorderPane sceneRoot;
		Node buttonPanel=new ButtonPanel(29);
		Node headerPanel=null;
		private ApplicationView curentView=null;
		
		
		
		
		public AppWindow(String title, Image icon, PrefferedSize prefferedSize,
				MenuExecutor mainMenuExecutor, MenuHolder menuHold,
				BorderPane sceneRoot, Node buttonPanel, Node headerPanel,MyResourceLoader resloader) {
			super(title, icon, prefferedSize,resloader);
			this.mainMenuExecutor = mainMenuExecutor;
			this.menuHold = menuHold;
			this.sceneRoot = sceneRoot;
			this.buttonPanel = buttonPanel;
			if(headerPanel==null){
				this.headerPanel=new HeaderPanel(66, I18N_FX.getLocalizedString("APP_HEADER_TEXT"),resloader);
			} else {
				this.headerPanel = headerPanel;
			}			
		}
		
		public AppWindow(String title, Image icon, PrefferedSize prefferedSize,
				MenuExecutor mainMenuExecutor, MenuHolder menuHold,MyResourceLoader resloader) {
			super(title, icon, prefferedSize,resloader);
			this.mainMenuExecutor = mainMenuExecutor;
			this.menuHold = menuHold;		
			headerPanel=new HeaderPanel(66,I18N_FX.getLocalizedString("APP_HEADER_TEXT"),resloader);
		}

		public AppWindow(String title, PrefferedSize prefferedSize,MenuHolder menuHold,MyResourceLoader resloader){
			this(title, null, prefferedSize,null,menuHold,resloader);
		}
		
		public AppWindow(String title,MenuHolder menuHold,MyResourceLoader resloader){
			this(title, null, null,null,menuHold,resloader);
		}
		
		public AppWindow(String title,MyResourceLoader resloader){
			this(title, null, null,null,null,resloader);
		}
		
		public AppWindow(MyResourceLoader resloader){
			this(null, null, null,null,null,resloader);
		}
		
		@Override
		public Scene getWindow(){
			if(prefferedSize==null)prefferedSize=new PrefferedSize(700, 800);
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
			
			view=sceneRef;
			
			
			return sceneRef;
		}

		protected Node createMenuBar(){
			if(menuHold!=null){
				MenuBar bar=new MenuBar();
                bar.setId("globalMenu");
                bar.setPrefHeight(20);
                bar.setManaged(true);
				if(mainMenuExecutor==null){
					mainMenuExecutor=new MenuExecutor();
					mainMenuExecutor.setParentView(this);
				}
				List<Menu> menus=menuHold.createMenu();
				for(Menu m:menus){
					bar.getMenus().add(m);
				}
				return bar;
			} else {
				return null;
			}
		}
		
		protected Node createTitleBar(){		
			Node menuBar=createMenuBar();
			if(menuBar!=null){
                return new VBox(headerPanel,menuBar);
			} else {
                return new VBox(headerPanel);
			}
		}
		
		protected Node createButtonBar(){
            buttonPanel=new InfoBar(29);
            return buttonPanel;
		}
		
		@Override
		public void changeView(ApplicationView newView) {
			if(newView.getMainView()!=null && sceneRoot!=null){
				Parent newroot=newView.getMainView().getRoot();
				if(newroot!=null){
					if(curentView!=null) curentView.onHide();
					curentView=newView;					
					sceneRoot.centerProperty().setValue(newroot);
				}
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

		public MenuHolder getMenuHold() {
			return menuHold;
		}

		public void setMenuHold(MenuHolder menuHold) {
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
		
		public void setMessage(String msg){
            if(buttonPanel instanceof InfoBar){
                ((InfoBar) buttonPanel).setMessage(msg);
            }
        }

        public void setMessage(ElementEventListener event){
            if(buttonPanel instanceof InfoBar){
                ((InfoBar) buttonPanel).setMessage(event.toString());
            }
        }

}

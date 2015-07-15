package ua.pp.fairwind.javafx.guiElements.menu;


import java.util.concurrent.CopyOnWriteArrayList;

import images.MyResourceLoader;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import ua.pp.fairwind.communications.propertyes.event.ElementEventListener;
import ua.pp.fairwind.javafx.guiElements.windows.AppWindow;
import ua.pp.fairwind.javafx.guiElements.windows.ApplicationView;
import ua.pp.fairwind.javafx.guiElements.windows.ApplicationWindow;
import ua.pp.fairwind.javafx.panels.InfoDialog;

public class MenuExecutor {
		public interface CustumCommandProccesor{
			public boolean processCommand(String commandName,MenuExecutor executor);
		}
		private ApplicationWindow parentView;
		private CopyOnWriteArrayList<Stage> dialogs=new CopyOnWriteArrayList<>();
		private ApplicationView curent=null;
		private MyResourceLoader resloader=null;
    private Stage mainStage;
		private CopyOnWriteArrayList<CustumCommandProccesor> proccesor;
		private EventHandler<WindowEvent> closeEvent= event -> {
            if(event!=null && event.getSource()!=null && event.getSource() instanceof Stage){
dialogs.remove(event.getSource());
            }
        };
	
		protected void changeView(ApplicationView node){
			if(node!=null && parentView!=null){
				if(curent!=null)curent.onHide();
				parentView.changeView(node);
				curent=node;
				informNode(node);
			}
		}
		
		
		public void menuExecutor(MenuConfigElements item){
			if(item!=null){
				if(item.getCommand()!=null){
				if(!customProcess(item.getCommand())){
					switch(item.getCommand()){
                        case "ExecuteAction":
                            item.executeAction(this);
                            break;
						case "exit":
						case "quit":
							Platform.exit();
							System.exit(0);
							break;
						case "dialog":
						case "showdialog":{
								showDialog(item.getForms(),false);
							}
							break;
						case "modaldialog":
						case "showmodaldialog":{
								showDialog(item.getForms(),true);
							}
							break;
						case "modalappdialog":
						case "showmodalappdialog":{
								showAppDialog(item.getForms());
							}
							break;						
						case "show":
						case "window":
						default:
							changeView(item.getForms());
					}
				}
				} else {
					if(item.getForms()!=null){
						changeView(item.getForms());
					}
				}
			}
		}
		
		
		private void informNode(ApplicationView node){
			node.onShow(this);
		}
		
		
		public void showInfoDialog(final String text,final InfoDialog.dialogstyle style){
			if(Platform.isFxApplicationThread()){
	    		 showInfoDialogFX(text,style);
	    	} else {
		    	try{	
					Platform.runLater(() -> showInfoDialogFX(text,style));
	    		} catch (IllegalStateException ex){
	    			System.err.println(ex);
	    		}
	    	}
		}

        public void showInfoDialogFX(String text,InfoDialog.dialogstyle style){
            InfoDialog info = new InfoDialog();
			info.setText(text);
			info.setImage(resloader, style);
			showAppDialog(info);
            //showDialog(info,true);
		}

    synchronized public void showInfoMessage(final String message){
        if(parentView instanceof AppWindow){
            ((AppWindow) parentView).setMessage(message);
        }
    }

    synchronized public void showInfoMessage(final ElementEventListener event){
        if(parentView instanceof AppWindow){
            ((AppWindow) parentView).setMessage(event);
        }
    }

    synchronized public void showDialog(final ApplicationView node,boolean modal){
			if(node==null){
				return;
			}
			PrefferedSize pref=node.getPrefferedSize();
			if(pref==null) pref=new PrefferedSize(250, 300);
			Stage dialog = new Stage();
			dialog.initStyle(StageStyle.UTILITY);
			Scene scene=node.getMainView();
			if(scene==null)scene = new Scene(new Group(new Text(25, 25, "Hello World!")));
			dialog.setHeight(pref.getHeight());
			dialog.setWidth(pref.getWidth());
			dialog.setOnShown(arg0 -> informNode(node));
			
			dialog.setOnHidden(arg0 -> node.onHide());
			if(node.getTitle()!=null)dialog.setTitle(node.getTitle());
			if(node.getIcon()!=null)dialog.getIcons().add(node.getIcon());
			if(pref.isCenter()){
				dialog.centerOnScreen();
			} else {
				dialog.setX(pref.getX());
				dialog.setY(pref.getY());
			}
			dialog.setScene(scene);
			dialogs.add(dialog);
			dialog.setOnCloseRequest(closeEvent);
			if(modal){
				if(parentView!=null && parentView.getMainView()!=null){
					System.out.println("window");
					dialog.initModality(Modality.WINDOW_MODAL);
					dialog.initOwner(parentView.getMainView().getWindow());
					dialog.showAndWait();
				} else {
					System.out.println("app");
					dialog.initModality(Modality.APPLICATION_MODAL);
					dialog.showAndWait();
				}				
			} else {
				dialog.show();
			}			
		}
		
		synchronized public  void closeView(ApplicationView node){
			if(curent!=node){
				Stage stagclose=null;
				for(Stage stag:dialogs){

					/*
					ApplicationView view= (ApplicationView) stag.getScene().getRoot();
                    if(view.getID()==node.getID()){
                        stag.close();
                        stagclose=stag;
                        break;
                    }
                    /**/

                    if(stag.getScene()==node.getMainView()){
						stag.close();
						stagclose=stag;
						break;
					}/**/
				}
				if(stagclose!=null){
					dialogs.remove(stagclose);
				} else {
					if(parentView!=null){
						parentView.close();
					}
				}
			}
		}

    synchronized public void showAppDialog(final ApplicationView node){
			if(node==null){
				return;
			}
			PrefferedSize pref=node.getPrefferedSize();
			if(pref==null) pref=new PrefferedSize(250, 300);
			Stage dialog = new Stage();
			dialog.initStyle(StageStyle.UTILITY);
			Scene scene=node.getMainView();
			if(scene==null)scene = new Scene(new Group(new Text(25, 25, "Hello World!")));
			dialog.setHeight(pref.getHeight());
			dialog.setWidth(pref.getWidth());
			dialog.setOnShown(arg0 -> informNode(node));
			
			dialog.setOnHidden(arg0 -> node.onHide());
			dialog.setOnCloseRequest(closeEvent);
			if(node.getTitle()!=null)dialog.setTitle(node.getTitle());
			if(node.getIcon()!=null)dialog.getIcons().add(node.getIcon());
			if(pref.isCenter()){
				dialog.centerOnScreen();
			} else {
				dialog.setX(pref.getX());
				dialog.setY(pref.getY());
			}
			dialog.setScene(scene);
			dialog.initModality(Modality.APPLICATION_MODAL);
            dialogs.add(dialog);
			dialog.showAndWait();
		}

		public ApplicationWindow getParentView() {
			return parentView;
		}

		public void setParentView(ApplicationWindow parentView) {
			this.parentView = parentView;
		}
		
		public void closeAllDialogs(){
			for(Stage stag:dialogs){			
				stag.close();
			}
			dialogs.clear();
		}

		public MyResourceLoader getResloader() {
			return resloader;
		}

		public void setResloader(MyResourceLoader resloader) {
			this.resloader = resloader;
		}
		
		private boolean customProcess(String command){
			boolean res=false;
			if(proccesor!=null){
				for(CustumCommandProccesor proces:proccesor){
					res=proces.processCommand(command, this);
					if(res)break;
				}
			}
			return res;
		}
		
		public void setCustomProcessor(CustumCommandProccesor custom){			
			if(custom!=null){
				if(proccesor==null) proccesor=new CopyOnWriteArrayList<>();
                proccesor.add(custom);
            }
		}
		
		public void removeCustomProcessor(CustumCommandProccesor custom){
			if(proccesor!=null && custom!=null){
				proccesor.remove(custom);
			}
		}
		
		public void removeAllCustomProccesor(){
			if(proccesor!=null){
				proccesor.clear();
			}
		}

    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
}

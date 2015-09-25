package ua.pp.fairwind.javafx.guiElements.menu;


import images.MyResourceLoader;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ua.pp.fairwind.communications.messagesystems.event.ElementEventListener;
import ua.pp.fairwind.javafx.guiElements.windows.AppWindow;
import ua.pp.fairwind.javafx.guiElements.windows.ApplicationView;
import ua.pp.fairwind.javafx.guiElements.windows.ApplicationWindow;
import ua.pp.fairwind.javafx.panels.InfoDialog;

public class MenuExecutor {
		private ApplicationWindow parentView;
		private ApplicationView curent=null;
		private MyResourceLoader resloader=null;
    	private Stage mainStage;

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
				if(item instanceof MenuConfigElementsAction){
					((MenuConfigElementsAction)item).executeAction(this);
				} else if(item instanceof MenuConfigElementsDialog){
					Dialog dialog=((MenuConfigElementsDialog)item).getDialog();
					if(dialog!=null)dialog.show();
				}else if(item instanceof MenuConfigElementsAbstractForm){
					MenuConfigElementsAbstractForm form=(MenuConfigElementsAbstractForm)item;
					if(form.getModality()!=null){
						Dialog<Void> dialog = new Dialog<>();
						dialog.initOwner(mainStage);
						dialog.setOnCloseRequest(evet -> dialog.close());
						dialog.setHeaderText(form.getTitle());
						dialog.setResizable(form.isResizable());
						//dialog.initStyle(StageStyle.UTILITY);
						dialog.getDialogPane().setContent(form.getMainView().getRoot());
						dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
						dialog.initModality(form.getModality());
						dialog.showAndWait();
					} else {
						changeView(form);
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

    synchronized public void showDialog(final ApplicationView node,boolean modal) {
		if(node==null){
				return;
			}
			Dialog<Void> dialog=new Dialog<>();
			dialog.setHeaderText(node.getTitle());
			dialog.getDialogPane().setContent(node.getMainView().getRoot());
			dialog.setOnCloseRequest(evet -> dialog.close());
			dialog.initOwner(mainStage);
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
			if(modal){

				dialog.initModality(Modality.WINDOW_MODAL);
				dialog.showAndWait();
			} else {
				dialog.initModality(Modality.NONE);
				dialog.show();
			}

		}
		
		synchronized public  void closeView(ApplicationView node){
			if(curent!=node){
				if(parentView!=null){
					parentView.close();
				}
				Stage stagclose=null;
			}
		}

	public synchronized void closeAllDialogs(){
	}

    synchronized public void showAppDialog(final ApplicationView node){
		if(node==null){
			return;
		}
		Dialog<Void> dialog = new Dialog<>();
		dialog.initOwner(mainStage);
		dialog.setOnCloseRequest(evet -> dialog.close());
		dialog.setHeaderText(node.getTitle());
		dialog.initStyle(StageStyle.UTILITY);
		dialog.getDialogPane().setContent(node.getMainView().getRoot());
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.showAndWait();
	}

	public ApplicationWindow getParentView() {
			return parentView;
		}

	public void setParentView(ApplicationWindow parentView) {
			this.parentView = parentView;
	}
		
	public MyResourceLoader getResloader() {
			return resloader;
	}

	public void setResloader(MyResourceLoader resloader) {
			this.resloader = resloader;
	}
		

    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
}

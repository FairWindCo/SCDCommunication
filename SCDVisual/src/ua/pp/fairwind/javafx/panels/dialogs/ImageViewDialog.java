package ua.pp.fairwind.javafx.panels.dialogs;

import images.MyResourceLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import ua.pp.fairwind.javafx.I18N.I18N_FX;


/**
 * Created by Сергей on 28.08.2015.
 */
public class ImageViewDialog {
    static private final ImageViewDialog timerParametersDialog = new ImageViewDialog();

    private final Dialog<Void> dialog = new Dialog();
    private final MyResourceLoader resloader = new MyResourceLoader();
    private final ImageView iv2;

    private void setupName(String fileName){
        String path=resloader.getExternalResourceURILink(fileName);
        if(path==null||path.isEmpty()){
            path=resloader.getExternalResourceURILink(I18N_FX.getLocalizedString(fileName));
        }
        if(path==null||path.isEmpty()){
            path=resloader.getExternalResourceURILink(I18N_FX.getLocalizedString("reload.png"));
        }

        Image image=new Image(path);
        iv2.setImage(image);
    }

    public ImageViewDialog() {
        dialog.setTitle(I18N_FX.getLocalizedString("connect_view.dialog"));
        dialog.setHeaderText(I18N_FX.getLocalizedString("connect_view.header"));
        dialog.initModality(Modality.NONE);
        dialog.setResizable(true);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        iv2 = new ImageView();
        //iv2.setFitWidth(500);
        iv2.setPreserveRatio(true);
        iv2.setSmooth(true);
        iv2.setCache(true);

        dialog.getDialogPane().setContent(iv2);
    }

    public static ImageViewDialog getDialogImage(){
        return timerParametersDialog;
    }


    private void showDialog(String imageName) {
        setupName(imageName);
        dialog.show();
    }

    public static void showImageDialog(String imageName) {
        timerParametersDialog.showDialog(imageName);
    }
}

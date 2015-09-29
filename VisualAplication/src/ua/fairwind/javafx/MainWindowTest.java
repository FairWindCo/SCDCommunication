package ua.fairwind.javafx;

import images.MyResourceLoader;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ua.pp.fairwind.communications.propertyes.software.stringlike.StringIntegerValuedProperty;
import ua.pp.fairwind.javafx.guiElements.SCADASystemFX;
import ua.pp.fairwind.javafx.guiElements.menu.*;
import ua.pp.fairwind.javafx.guiElements.windows.AppWindow;
import ua.pp.fairwind.javafx.guiElements.windows.hardware.*;
import ua.pp.fairwind.javafx.panels.InfoDialog;
import ua.pp.fairwind.javafx.panels.LogEventView;
import ua.pp.fairwind.javafx.panels.LogLineBufferView;

/**
 * Created by Сергей on 17.08.2015.
 */
public class MainWindowTest extends Application {
    private AppWindow mainView;
    private MenuExecutor mainmenuexec=new MenuExecutor();
    private MenuHolder holderMenu=new MenuHolder(mainmenuexec, true);
    private LogEventView evetView=new LogEventView();
    private LogLineBufferView bufferView=new LogLineBufferView();
    private boolean showInfoInBar=true;
    private SCADASystemFX scada;

    public static void main(String[] args) {
        launch(args);
    }




    @Override
    public void start(Stage windowStage) throws Exception {
        MyResourceLoader resloader=new MyResourceLoader();
        scada=SCADASystemFX.createScadaSystem("CENTRAL_SCADA",5000);
        //LineCommunicationLoggingWindow loggingWindow=new LineCommunicationLoggingWindow("Buffer Logging",1000);
        //scada.setMonitoringToAllLines(loggingWindow.getLoggingDevice());

        mainView=new AppWindow(resloader);
        mainmenuexec.setResloader(resloader);
        mainView.setMainMenuExecutor(mainmenuexec);
        mainView.setTitle("TEST WINDOWS");
        mainView.setIcon(new Image(resloader.getExternalResourceURILink("icon-48x48.png")));
        MenuConfigElements testDev=new MenuConfigElements("Single_Devies", "");
        MenuConfigElements akon=new MenuConfigElements("AKON", "");
        MenuConfigElements test=new MenuConfigElements("test", "");
        StringIntegerValuedProperty chhh=new StringIntegerValuedProperty("test","","0", 0, 100,"val1","val2","val3","##.###");
        MenuConfigElements logmessage=new MenuConfigElementsForm("Message Log", evetView);
        MenuConfigElements logline=new MenuConfigElementsForm("Buffer Line Log", bufferView);
        MenuConfigElements testDialog=new MenuConfigElementsAction("modal app dialog",(a,b)->{
            //new Thread(() -> mainmenuexec.showInfoDialog("test", InfoDialog.dialogstyle.OK)).start();
            mainmenuexec.showInfoDialog("test", InfoDialog.dialogstyle.OK);});
        MenuConfigElements log=new MenuConfigElements("LOG", logmessage,logline,testDialog);
        MenuConfigElements favorit=new FavoritPlate("FavoritVentilV1",scada);
        MenuConfigElements pandrive=new PanDriveWindow("Pan_Drive_Step_Motor",scada);
        MenuConfigElements argMicro=new ArgMicroWindow("Arg_Micro",scada);
        MenuConfigElements encoder=new BelimaWindow("Baumer_Incremental_Encoder",scada);
        MenuConfigElements bdbg09=new BDBG09Window("BDBG09",scada);
        MenuConfigElements akonbase=new AkonBaseWindow("AKON_Base",scada,"baseakon");
        testDev.setAddChild(akon);
        akon.setAddChild(akonbase);
        testDev.setAddChild(favorit);
        testDev.setAddChild(pandrive);
        testDev.setAddChild(argMicro);
        testDev.setAddChild(encoder);
        testDev.setAddChild(bdbg09);
        holderMenu.setMenuPoint(testDev);
        holderMenu.setMenuPoint(test);
        holderMenu.setMenuPoint(log);
        holderMenu.setMenuPoint(scada.getAdministrativeMenu());
        mainView.setMenuHold(holderMenu);
        mainView.formStage(windowStage);
        windowStage.show();
    }


    @Override
    public void stop() throws Exception {
        scada.destroy();
        mainView.destroy();
        super.stop();
    }

}

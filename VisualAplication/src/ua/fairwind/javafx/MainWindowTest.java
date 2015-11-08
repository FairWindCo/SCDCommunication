package ua.fairwind.javafx;

import images.MyResourceLoader;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ua.pp.fairwind.javafx.I18N.I18N_FX;
import ua.pp.fairwind.javafx.guiElements.SCADASystemFX;
import ua.pp.fairwind.javafx.guiElements.menu.MenuConfigElements;
import ua.pp.fairwind.javafx.guiElements.menu.MenuExecutor;
import ua.pp.fairwind.javafx.guiElements.menu.MenuHolder;
import ua.pp.fairwind.javafx.guiElements.windows.AppWindow;
import ua.pp.fairwind.javafx.guiElements.windows.hardware.*;
import ua.pp.fairwind.javafx.panels.LogEventView;
import ua.pp.fairwind.javafx.panels.LogLineBufferView;
import ua.pp.fairwind.javafx.panels.administrative.ImmitatorWindow;

/**
 * Created by Сергей on 17.08.2015.
 */
public class MainWindowTest extends Application {
    private AppWindow mainView;
    private MenuExecutor mainmenuexec = new MenuExecutor();
    private MenuHolder holderMenu = new MenuHolder(mainmenuexec, true);
    private LogEventView evetView = new LogEventView();
    private LogLineBufferView bufferView = new LogLineBufferView();
    private boolean showInfoInBar = true;
    private SCADASystemFX scada;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage windowStage) throws Exception {
        MyResourceLoader resloader = new MyResourceLoader();
        scada = SCADASystemFX.createScadaSystem("CENTRAL_SCADA", 5000);
        //LineCommunicationLoggingWindow loggingWindow=new LineCommunicationLoggingWindow("Buffer Logging",1000);
        //scada.setMonitoringToAllLines(loggingWindow.getLoggingDevice());

        mainView = new AppWindow(resloader);
        mainmenuexec.setResloader(resloader);
        mainView.setMainMenuExecutor(mainmenuexec);
        mainView.setTitle(I18N_FX.getLocalizedString("APP_TITLE_TEXT"));
        mainView.setIcon(new Image(resloader.getExternalResourceURILink("icon-48x48.png")));
        MenuConfigElements testDev = new MenuConfigElements("Single_Devies", "");
        MenuConfigElements akon = new MenuConfigElements("AKON", "");
        MenuConfigElements favorit = new FavoritPlate("FavoritVentilV1", scada);
        MenuConfigElements pandrive = new PanDriveWindow("Pan_Drive_Step_Motor", scada);
        MenuConfigElements argMicro = new ArgMicroWindow("Arg_Micro", scada);
        MenuConfigElements encoder = new BelimaWindow("Baumer_Incremental_Encoder", scada);
        MenuConfigElements bdbg09 = new BDBG09Window("BDBG09", scada);
        MenuConfigElements bdmg04 = new BDMG04Window("BDMG04", scada);
        MenuConfigElements akonbase = new AkonBaseWindow("AKON_Base", scada, "baseakon");
        MenuConfigElements immitator_base = new MenuConfigElements("IMITATORS");
        MenuConfigElements immitator = new ImmitatorWindow("SERIAL_IMITATOR", scada);
        testDev.setAddChild(akon);
        akon.setAddChild(akonbase);
        testDev.setAddChild(favorit);
        testDev.setAddChild(pandrive);
        testDev.setAddChild(argMicro);
        testDev.setAddChild(encoder);
        testDev.setAddChild(bdbg09);
        testDev.setAddChild(bdmg04);
        immitator_base.setAddChild(immitator);
        holderMenu.setMenuPoint(testDev);
        holderMenu.setMenuPoint(immitator_base);
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

package ua.fairwind.javafx;

import images.MyResourceLoader;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ua.pp.fairwind.communications.propertyes.software.stringlike.StringIntegerValuedProperty;
import ua.pp.fairwind.javafx.guiElements.menu.*;
import ua.pp.fairwind.javafx.guiElements.windows.AppWindow;
import ua.pp.fairwind.javafx.panels.InfoDialog;
import ua.pp.fairwind.javafx.panels.LogEventView;
import ua.pp.fairwind.javafx.panels.LogLineBufferView;

/**
 * Created by Сергей on 17.08.2015.
 */
public class MainWindow  extends Application {
    private AppWindow mainView;
    private MenuExecutor mainmenuexec=new MenuExecutor();
    private MenuHolder holderMenu=new MenuHolder(mainmenuexec, true);
    private LogEventView evetView=new LogEventView();
    private LogLineBufferView bufferView=new LogLineBufferView();
    private boolean showInfoInBar=true;

    public static void main(String[] args) {
        launch(args);
    }




    @Override
    public void start(Stage windowStage) throws Exception {
        MyResourceLoader resloader=new MyResourceLoader();
        mainView=new AppWindow(resloader);
        mainmenuexec.setResloader(resloader);
        mainView.setMainMenuExecutor(mainmenuexec);
        mainView.setTitle("TEST WINDOWS");
        mainView.setIcon(new Image(resloader.getExternalResourceURILink("icon-48x48.png")));
        MenuConfigElements modul_dp5=new MenuConfigElements("DP5 Module", "");
        /*
        MenuConfigElements modul_dp5netconf=new MenuConfigElements("DP5 Module Network Config", new NetworkSettingsWindow(ethernetSettingsDP5Chanel));
        MenuConfigElements modul_dp5netconn=new MenuConfigElements("Connect Settings", "modaldialog", new UDPNetworkConnectionForm("", null, null, line));
        MenuConfigElements modul_dp5find=new MenuConfigElements("Find DP5 Module", "modaldialog", new NetworkFindForm(linefind,line));
        MenuConfigElements modul_dp5usbfind=new MenuConfigElements("Find USB DP5 Module", "modaldialog", new USBFindForm(line));
        MenuConfigElements modul_dp5stat=new MenuConfigElements("DP5 Module Status", new DeviseStatusWindow(statch));
        MenuConfigElements modul_dp5pref=new MenuConfigElements("DP5 Module Prefference",new ParametersWindow("preference",null,null,conf,resloader));
        MenuConfigElements modul_dp5spetr=new MenuConfigElements("Spectr","dialog", new SpectrumOnlyWindow(spectrum,dp5,line,monitorP));
*/
        MenuConfigElements testDev=new MenuConfigElements("Single Devies", "");
        MenuConfigElements akon=new MenuConfigElements("AKON", "");
        MenuConfigElements test=new MenuConfigElements("test", "");
        StringIntegerValuedProperty chhh=new StringIntegerValuedProperty("test","","0", 0, 100,"val1","val2","val3","##.###");
        /*
        MenuConfigElements view=new MenuConfigElements("view1",new SimpleView2("test",null,null,chhh));
        MenuConfigElements view2=new MenuConfigElements("view2",new ParametersWindow("test",null,null,new ConfigurationDP5Property(ConfigurationDP5Property.DEVICETYPE.DP5),resloader));
        MenuConfigElements pandrive=new MenuConfigElements("PanDrive",new PanDrivecommandWindow("pandrive", resloader,monitor));
        MenuConfigElements arg=new MenuConfigElements("ARGMicro",new ARGMicroWindow("argmicro", resloader,monitor));
        MenuConfigElements encoder=new MenuConfigElements("Encoder",new AbsEncoderWindow("argmicro", resloader,monitor));
        MenuConfigElements aik12=new MenuConfigElements("AIK12",new AKONAIK12Window("AKON AIK", resloader,12,3,monitor));
        MenuConfigElements aik=new MenuConfigElements("AIK",new AKONAIK12Window("AKON AIK", resloader,4,3,monitor));
        MenuConfigElements ao=new MenuConfigElements("AO",new AKONAO6Window("AKON AIK", resloader,4,2,monitor));
        MenuConfigElements ao6=new MenuConfigElements("AO6",new AKONAO6Window("AKON AIK", resloader,6,2,monitor));
        MenuConfigElements dio=new MenuConfigElements("DIO4",new AKONDIOWindow("AKON AIK", resloader,4,4,monitor));*/
        //MenuConfigElements fpmonitor=new MenuConfigElements("Monitor",new FPMonitorWindow("Favorit Monitor", resloader));
        //MenuConfigElements fpshuv=new MenuConfigElements("SHUV Test",new SHYVWindow("AKON SHUV", resloader,6,2));
        MenuConfigElements logmessage=new MenuConfigElementsForm("Message Log", evetView);
        MenuConfigElements logline=new MenuConfigElementsForm("Buffer Line Log", bufferView);
        /*
        MenuConfigElements testDialog=new MenuConfigElements("modal app dialog",(a,b)->{
            new Thread(() -> mainmenuexec.showInfoDialog("test", InfoDialog.dialogstyle.OK)).start();
            mainmenuexec.showInfoDialog("test", InfoDialog.dialogstyle.OK);});/**/
        MenuConfigElements testDialog=new MenuConfigElementsAction("modal app dialog",(a,b)->{
            //new Thread(() -> mainmenuexec.showInfoDialog("test", InfoDialog.dialogstyle.OK)).start();
            mainmenuexec.showInfoDialog("test", InfoDialog.dialogstyle.OK);});
        MenuConfigElements log=new MenuConfigElements("Log", logmessage,logline,testDialog);
        /*
        testDev.setAddChild(pandrive);
        testDev.setAddChild(arg);
        testDev.setAddChild(encoder);
        akon.setAddChild(aik12);
        akon.setAddChild(aik);
        akon.setAddChild(ao);
        akon.setAddChild(ao6);
        akon.setAddChild(dio);*/
        //test.setAddChild(fpmonitor);
        //test.setAddChild(fpshuv);
        //test.setAddChild(view);
       // test.setAddChild(view2);
        testDev.setAddChild(akon);
        /*
        modul_dp5.setAddChild(modul_dp5find);
        modul_dp5.setAddChild(modul_dp5netconn);
        modul_dp5.setAddChild(modul_dp5usbfind);
        modul_dp5.setAddChild(modul_dp5netconf);
        modul_dp5.setAddChild(modul_dp5stat);
        modul_dp5.setAddChild(modul_dp5pref);
        modul_dp5.setAddChild(modul_dp5spetr);*/
        holderMenu.setMenuPoint(modul_dp5);
        holderMenu.setMenuPoint(testDev);
        //holderMenu.setMenuPoint(akon);
        holderMenu.setMenuPoint(test);
        mainView.setMenuHold(holderMenu);
        holderMenu.setMenuPoint(log);
        mainView.formStage(windowStage);
        windowStage.show();
        //ethernetSettingsDP5Chanel.setDenmandRead();
    }


    @Override
    public void stop() throws Exception {
        super.stop();
        mainView.destroy();
    }

}

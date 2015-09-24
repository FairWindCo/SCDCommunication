package ua.pp.fairwind.javafx.guiElements;

import ua.pp.fairwind.communications.SCADASystem;
import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.devices.abstracts.DeviceInterface;
import ua.pp.fairwind.communications.elementsdirecotry.AutoCreateDeviceFunction;
import ua.pp.fairwind.communications.lines.SerialLine;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystemMultiDipatch;
import ua.pp.fairwind.javafx.I18N.I18N_FX;
import ua.pp.fairwind.javafx.guiElements.menu.MenuConfigElements;
import ua.pp.fairwind.javafx.panels.administrative.AllEventMonitorWindow;
import ua.pp.fairwind.javafx.panels.administrative.ErrorEventMonitorWindow;
import ua.pp.fairwind.javafx.panels.administrative.LineCommunicationLoggingWindow;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Сергей on 10.09.2015.
 */
public class SCADASystemFX extends SCADASystem {
    final private LineCommunicationLoggingWindow logingWindow;
    final private AllEventMonitorWindow eventLogingWindow;
    final private ErrorEventMonitorWindow eventErrorWindow;
    final private AtomicBoolean menuCreated=new AtomicBoolean(false);
    final private AtomicReference<MenuConfigElements> menu=new AtomicReference<>();
    final boolean canLogAllError= I18N_FX.getObject("LOGGING_ALL_ERROR")!=null?(boolean) I18N_FX.getObject("LOGGING_ALL_ERROR"):false;
    final boolean canLogAllEvent= I18N_FX.getObject("LOGGING_ALL_EVENT")!=null?(boolean) I18N_FX.getObject("LOGGING_ALL_EVENT"):false;
    final boolean canLogLineDeviceError = I18N_FX.getObject("LOGGING_LINEDEVICE_ERROR")!=null?(boolean) I18N_FX.getObject("LOGGING_LINEDEVICE_ERROR"):false;

    static public SCADASystemFX createScadaSystem(String name,int maxTrunsactionTime){
        SCADASystemFX scada=new SCADASystemFX(name,null,300);
        List<LineInterface> serialLines= SerialLine.getSerialLines(maxTrunsactionTime);
        scada.addLines(serialLines);
        return scada;
    }

    static public SCADASystemFX createScadaSystem(String name,String description,HashMap<String,String> uuids,int maxTrunsactionTime,int maxLogSize){
        MessageSubSystem topLevel=new MessageSubSystemMultiDipatch();
        SCADASystemFX scada=new SCADASystemFX(name,null,maxLogSize);
        List<LineInterface> serialLines= SerialLine.getSerialLines(maxTrunsactionTime);
        scada.addLines(serialLines);
        return scada;
    }

    protected SCADASystemFX(String name, AutoCreateDeviceFunction createDeviceFunction,int maxLogSize) {
        super(name, createDeviceFunction);
        logingWindow=new LineCommunicationLoggingWindow("Lines_Communications_Logs",maxLogSize);
        eventLogingWindow=new AllEventMonitorWindow("Event_Logs",maxLogSize);
        eventErrorWindow=new ErrorEventMonitorWindow("Error_Logs",maxLogSize);
    }

    public MenuConfigElements getLineCommunicationLoggingMenu(){
        return logingWindow;
    }

    public MenuConfigElements getAdministrativeMenu(){
        if(menuCreated.compareAndSet(false,true)){
            MenuConfigElements administrativeMenu=new MenuConfigElements("Administrative");
            administrativeMenu.setAddChild(logingWindow);
            if(canLogAllError || canLogLineDeviceError)administrativeMenu.setAddChild(eventErrorWindow);
            if(canLogAllEvent)administrativeMenu.setAddChild(eventLogingWindow);
            menu.set(administrativeMenu);
        }
        return menu.get();
    }

    @Override
    public void addElemnt(ElementInterface element) {
        if(element==null) return;
        //element.addEventListener(eventLogingWindow);
        if(canLogAllEvent)addListenerDeepElements(element,eventLogingWindow);
        if(canLogAllError){
            addListenerDeepElements(element,eventErrorWindow);
        } else {
            if(canLogLineDeviceError){
                if(element instanceof LineInterface || element instanceof DeviceInterface){
                    element.addEventListener(eventErrorWindow);
                }
            }
        }
        if(element instanceof LineInterface){
                DeviceInterface logdevice = logingWindow.getLoggingDevice();
                ((LineInterface) element).addWriteMonitoringDevice(logdevice);
                ((LineInterface) element).addReadMonitoringDevice(logdevice);
        }
        super.addElemnt(element);
    }


    @Override
    public void removeElemnt(ElementInterface element) {
        if(element==null) return;
        //element.removeEventListener(eventLogingWindow);
        if(canLogAllEvent)removeListenerDeepElements(element, eventLogingWindow);
        if(canLogAllError){
            removeListenerDeepElements(element, eventErrorWindow);
        } else {
            if(canLogLineDeviceError){
                if(element instanceof LineInterface || element instanceof DeviceInterface){
                    element.removeEventListener(eventErrorWindow);
                }
            }
        }
        if(element instanceof LineInterface){
                DeviceInterface logdevice=logingWindow.getLoggingDevice();
                ((LineInterface) element).removeWriteMonitoringDevice(logdevice);
                ((LineInterface) element).removeReadMonitoringDevice(logdevice);
        }
        super.removeElemnt(element);
    }
}

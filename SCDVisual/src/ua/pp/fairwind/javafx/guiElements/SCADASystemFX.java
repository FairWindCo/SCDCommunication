package ua.pp.fairwind.javafx.guiElements;

import ua.pp.fairwind.communications.SCADASystem;
import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.devices.abstracts.LinedDeviceInterface;
import ua.pp.fairwind.communications.lines.LoopBackLine;
import ua.pp.fairwind.communications.lines.SerialLine;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystemMultiDipatch;
import ua.pp.fairwind.communications.utils.EllementsCreator;
import ua.pp.fairwind.javafx.I18N.I18N_FX;
import ua.pp.fairwind.javafx.guiElements.menu.MenuConfigElements;
import ua.pp.fairwind.javafx.panels.administrative.AllEventMonitorWindow;
import ua.pp.fairwind.javafx.panels.administrative.ComponentsViewer;
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
    final boolean canLogAllError = I18N_FX.getObject("LOGGING_ALL_ERROR") != null ? (boolean) I18N_FX.getObject("LOGGING_ALL_ERROR") : false;
    final boolean canLogAllEvent = I18N_FX.getObject("LOGGING_ALL_EVENT") != null ? (boolean) I18N_FX.getObject("LOGGING_ALL_EVENT") : false;
    final boolean canLogLineDeviceError = I18N_FX.getObject("LOGGING_LINEDEVICE_ERROR") != null ? (boolean) I18N_FX.getObject("LOGGING_LINEDEVICE_ERROR") : false;
    final private LineCommunicationLoggingWindow logingWindow;
    final private AllEventMonitorWindow eventLogingWindow;
    final private ErrorEventMonitorWindow eventErrorWindow;
    final private AtomicBoolean menuCreated = new AtomicBoolean(false);
    final private AtomicReference<MenuConfigElements> menu = new AtomicReference<>();

    public SCADASystemFX(String codename, EllementsCreator elementsCreator, int maxLogSize) {
        super(codename, elementsCreator);
        logingWindow = new LineCommunicationLoggingWindow("Lines_Communications_Logs", maxLogSize);
        eventLogingWindow = new AllEventMonitorWindow("Event_Logs", maxLogSize);
        eventErrorWindow = new ErrorEventMonitorWindow("Error_Logs", maxLogSize);
    }

    static public SCADASystemFX createScadaSystem(String name, int maxTrunsactionTime) {
        SCADASystemFX scada = new SCADASystemFX(name, new EllementsCreator(), 300);
        List<LineInterface> serialLines = SerialLine.getSerialLines(maxTrunsactionTime);
        scada.addLines(serialLines);
        scada.addElemnt(new LoopBackLine());
        return scada;
    }

    static public SCADASystemFX createScadaSystem(String name, String description, HashMap<String, String> uuids, int maxTrunsactionTime, int maxLogSize) {
        MessageSubSystem topLevel = new MessageSubSystemMultiDipatch();
        SCADASystemFX scada = new SCADASystemFX(name, new EllementsCreator(), maxLogSize);
        List<LineInterface> serialLines = SerialLine.getSerialLines(maxTrunsactionTime);
        scada.addLines(serialLines);
        return scada;
    }

    public MenuConfigElements getLineCommunicationLoggingMenu() {
        return logingWindow;
    }

    public MenuConfigElements getAdministrativeMenu() {
        if (menuCreated.compareAndSet(false, true)) {
            MenuConfigElements administrativeMenu = new MenuConfigElements("Administrative");
            MenuConfigElements component_view=new ComponentsViewer("Component View",this);
            administrativeMenu.setAddChild(component_view);
            administrativeMenu.setAddChild(logingWindow);
            if (canLogAllError || canLogLineDeviceError) administrativeMenu.setAddChild(eventErrorWindow);
            if (canLogAllEvent) administrativeMenu.setAddChild(eventLogingWindow);
            menu.set(administrativeMenu);
        }
        return menu.get();
    }

    @Override
    public void addElemnt(ElementInterface element) {
        if (element == null) return;
        //element.addEventListener(eventLogingWindow);
        if (canLogAllEvent) addListenerDeepElements(element, eventLogingWindow);
        if (canLogAllError) {
            addListenerDeepElements(element, eventErrorWindow);
        } else {
            if (canLogLineDeviceError) {
                if (element instanceof LineInterface || element instanceof LinedDeviceInterface) {
                    element.addEventListener(eventErrorWindow);
                }
            }
        }
        if (element instanceof LineInterface) {
            LinedDeviceInterface logdevice = logingWindow.getLoggingDevice();
            ((LineInterface) element).addWriteMonitoringDevice(logdevice);
            ((LineInterface) element).addReadMonitoringDevice(logdevice);
        }
        super.addElemnt(element);
    }


    @Override
    public void removeElemnt(ElementInterface element) {
        if (element == null) return;
        //element.removeEventListener(eventLogingWindow);
        if (canLogAllEvent) removeListenerDeepElements(element, eventLogingWindow);
        if (canLogAllError) {
            removeListenerDeepElements(element, eventErrorWindow);
        } else {
            if (canLogLineDeviceError) {
                if (element instanceof LineInterface || element instanceof LinedDeviceInterface) {
                    element.removeEventListener(eventErrorWindow);
                }
            }
        }
        if (element instanceof LineInterface) {
            LinedDeviceInterface logdevice = logingWindow.getLoggingDevice();
            ((LineInterface) element).removeWriteMonitoringDevice(logdevice);
            ((LineInterface) element).removeReadMonitoringDevice(logdevice);
        }
        super.removeElemnt(element);
    }
}

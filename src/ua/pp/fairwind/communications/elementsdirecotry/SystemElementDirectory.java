package ua.pp.fairwind.communications.elementsdirecotry;

import ua.pp.fairwind.communications.abstractions.ElementInterface;
import ua.pp.fairwind.communications.abstractions.SystemEllement;
import ua.pp.fairwind.communications.devices.abstracts.DeviceInterface;
import ua.pp.fairwind.communications.devices.abstracts.ImitatorDevice;
import ua.pp.fairwind.communications.devices.abstracts.LinedDeviceInterface;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.communications.messagesystems.event.ElementEventListener;
import ua.pp.fairwind.communications.propertyes.AbsractCommandProperty;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Сергей on 01.09.2015.
 */
public class SystemElementDirectory extends SystemEllement {
    final private ConcurrentHashMap<UUID, ElementInterface> elements = new ConcurrentHashMap<>();
    final private ConcurrentHashMap<UUID, AbstractProperty> propertyes = new ConcurrentHashMap<>();
    final private ConcurrentHashMap<UUID, DeviceInterface> devices = new ConcurrentHashMap<>();
    final private ConcurrentHashMap<UUID, LineInterface> lines = new ConcurrentHashMap<>();
    final private ConcurrentHashMap<UUID, SystemElementDirectory> subsystems = new ConcurrentHashMap<>();

    public SystemElementDirectory(String codename, String uuid) {
        super(codename, uuid);
    }


    public SystemElementDirectory(String codename) {
        super(codename, null);
    }

    public void addElemnt(ElementInterface element) {
        elements.put(element.getUUID(), element);
        if (element instanceof AbstractProperty) {
            propertyes.put(element.getUUID(), (AbstractProperty) element);
        } else if (element instanceof DeviceInterface) {
            devices.put(element.getUUID(), (DeviceInterface) element);
        } else if (element instanceof LineInterface) {
            lines.put(element.getUUID(), (LineInterface) element);
        } else if (element instanceof SystemElementDirectory) {
            subsystems.put(element.getUUID(), (SystemElementDirectory) element);
        }
    }


    public void addElemnts(AbstractProperty... elements) {
        for (AbstractProperty property : elements) {
            addElemnt(property);
        }
    }

    public void addElemnts(List<AbstractProperty> elements) {
        for (AbstractProperty property : elements) {
            addElemnt(property);
        }
    }

    public void addElemnts(LineInterface... elements) {
        for (LineInterface property : elements) {
            addElemnt(property);
        }
    }

    public void addLines(List<LineInterface> elements) {
        for (LineInterface property : elements) {
            addElemnt(property);
        }
    }

    public void addElemntWithProperty(LinedDeviceInterface element) {
        if (element != null) {
            addElemnt(element);
            AbstractProperty[] propertyes = element.getPropertys();
            AbstractProperty[] commands = element.getCommands();
            if (propertyes != null) {
                for (AbstractProperty property : propertyes) addElemnt(property);
            }
            if (commands != null) {
                for (AbstractProperty command : commands) addElemnt(command);
            }
        }
    }


    public void removeElemnt(ElementInterface element) {
        elements.remove(element.getUUID());
        if (element instanceof AbstractProperty) {
            propertyes.remove(element.getUUID());
        } else if (element instanceof LinedDeviceInterface) {
            devices.remove(element.getUUID());
        } else if (element instanceof LineInterface) {
            lines.remove(element.getUUID());
        } else if (element instanceof SystemElementDirectory) {
            subsystems.remove(element.getUUID());
        }
    }


    public ElementInterface getElement(String uuid) {
        UUID ui = UUID.fromString(uuid);
        if (ui != null) {
            return elements.get(ui);
        } else {
            return null;
        }
    }

    public DeviceInterface getDevice(String uuid) {
        UUID ui = UUID.fromString(uuid);
        if (ui != null) {
            return devices.get(ui);
        } else {
            return null;
        }
    }

    public LineInterface getLine(String uuid) {
        UUID ui = UUID.fromString(uuid);
        if (ui != null) {
            return lines.get(ui);
        } else {
            return null;
        }
    }

    public AbstractProperty getPropertyes(String uuid) {
        UUID ui = UUID.fromString(uuid);
        if (ui != null) {
            return propertyes.get(ui);
        } else {
            return null;
        }
    }

    public SystemElementDirectory getSubSytem(String uuid) {
        UUID ui = UUID.fromString(uuid);
        if (ui != null) {
            return subsystems.get(ui);
        } else {
            return null;
        }
    }

    public void destroyAll() {
        elements.forEachValue(100, (elem) -> elem.destroy());
        lines.clear();
        devices.clear();
        propertyes.clear();
        elements.clear();
        centralSystem.destroy();
    }

    public Collection<LineInterface> getAllLines() {
        return lines.values();
    }

    public List<LineInterface> getListLines() {
        return new ArrayList<>(lines.values());
    }

    public Collection<DeviceInterface> getAllDevices() {
        return devices.values();
    }

    public void setAllEventListener(ElementEventListener listener) {
        elements.values().forEach(element -> element.addEventListener(listener));
    }

    protected void addListenerDeepElements(ElementInterface element, ElementEventListener listener) {
        element.addEventListener(listener);

        if (element instanceof LinedDeviceInterface) {
            AbstractProperty[] properties = ((LinedDeviceInterface) element).getPropertys();
            if (properties != null && properties.length > 0) {
                for (AbstractProperty property : properties) {
                    property.addEventListener(listener);
                }
            }
            AbsractCommandProperty[] commands = ((LinedDeviceInterface) element).getCommands();
            if (commands != null && commands.length > 0) {
                for (AbsractCommandProperty command : commands) {
                    command.addEventListener(listener);
                }
            }
        }
        if (element instanceof LineInterface) {
            ImitatorDevice[] devices = ((LineInterface) element).getDeivicesForService();
            if (devices != null && devices.length > 0) {
                for (LinedDeviceInterface device : devices) {
                    addListenerDeepElements(device, listener);
                }
            }
        }
        if (element instanceof SystemElementDirectory) {
            ((SystemElementDirectory) element).addDeepAllEventListener(listener);
        }
    }

    protected void removeListenerDeepElements(ElementInterface element, ElementEventListener listener) {
        element.removeEventListener(listener);

        if (element instanceof LinedDeviceInterface) {
            AbstractProperty[] properties = ((LinedDeviceInterface) element).getPropertys();
            if (properties != null && properties.length > 0) {
                for (AbstractProperty property : properties) {
                    property.removeEventListener(listener);
                }
            }
            AbsractCommandProperty[] commands = ((LinedDeviceInterface) element).getCommands();
            if (commands != null && commands.length > 0) {
                for (AbsractCommandProperty command : commands) {
                    command.removeEventListener(listener);
                }
            }
        }
        if (element instanceof LineInterface) {
            ImitatorDevice[] devices = ((LineInterface) element).getDeivicesForService();
            if (devices != null && devices.length > 0) {
                for (LinedDeviceInterface device : devices) {
                    removeListenerDeepElements(device, listener);
                }
            }
        }
        if (element instanceof SystemElementDirectory) {
            ((SystemElementDirectory) element).removeDeepAllEventListener(listener);
        }
    }

    public void addDeepAllEventListener(ElementEventListener listener) {
        elements.values().forEach(element -> {
            addListenerDeepElements(element, listener);
        });
    }

    public void removeDeepAllEventListener(ElementEventListener listener) {
        elements.values().forEach(element -> {
            removeListenerDeepElements(element, listener);
        });
    }

    public void setAllLinesEventListener(ElementEventListener listener) {
        lines.values().forEach(element -> element.addEventListener(listener));
    }

    public void setAllDevicesEventListener(ElementEventListener listener) {
        devices.values().forEach(element -> element.addEventListener(listener));
    }

    public void setAllPropertyesEventListener(ElementEventListener listener) {
        propertyes.values().forEach(element -> element.addEventListener(listener));
    }

    public void removeAllEventListener(ElementEventListener listener) {
        elements.values().forEach(element -> element.removeEventListener(listener));
    }

    public void removeAllLinesEventListener(ElementEventListener listener) {
        lines.values().forEach(element -> element.removeEventListener(listener));
    }

    public void removeAllDevicesEventListener(ElementEventListener listener) {
        devices.values().forEach(element -> element.removeEventListener(listener));
    }

    public void removeAllPropertyesEventListener(ElementEventListener listener) {
        propertyes.values().forEach(element -> element.removeEventListener(listener));
    }

    public void setMonitoringToAllLines(LinedDeviceInterface monitoringDevice) {
        lines.values().forEach(element -> {
            element.addReadMonitoringDevice(monitoringDevice);
            element.addWriteMonitoringDevice(monitoringDevice);
        });
    }

    public void setReadMonitoringToAllLines(LinedDeviceInterface monitoringDevice) {
        lines.values().forEach(element -> {
            element.addReadMonitoringDevice(monitoringDevice);
        });
    }

    public void setWriteMonitoringToAllLines(LinedDeviceInterface monitoringDevice) {
        lines.values().forEach(element -> {
            element.addWriteMonitoringDevice(monitoringDevice);
        });
    }
}

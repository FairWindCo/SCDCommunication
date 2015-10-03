package ua.pp.fairwind.communications;

import ua.pp.fairwind.communications.devices.abstracts.DeviceInterface;
import ua.pp.fairwind.communications.elementsdirecotry.AutoCreateDeviceFunction;
import ua.pp.fairwind.communications.elementsdirecotry.SystemElementDirectory;
import ua.pp.fairwind.communications.lines.LoopBackLine;
import ua.pp.fairwind.communications.lines.SerialLine;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystemMultiDipatch;
import ua.pp.fairwind.communications.timeaction.PropertyTimer;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ������ on 30.06.2015.
 */
public class SCADASystem extends SystemElementDirectory implements AutoCreateDeviceFunction {
    private final AutoCreateDeviceFunction createDeviceFunction;
    final private ConcurrentHashMap<String, DeviceInterface> createdDevices = new ConcurrentHashMap<>();


    protected SCADASystem(String name, AutoCreateDeviceFunction createDeviceFunction) {
        super(name, null);
        this.createDeviceFunction = createDeviceFunction;
    }

    static public SCADASystem createScadaSystem(String name, int maxTrunsactionTime) {
        MessageSubSystem topLevel = new MessageSubSystemMultiDipatch();
        SCADASystem scada = new SCADASystem(name, null);
        List<LineInterface> serialLines = SerialLine.getSerialLines(maxTrunsactionTime);
        scada.addElemnt(new LoopBackLine());
        scada.addLines(serialLines);
        return scada;
    }

    private void destroyAllLine() {
        getAllLines().forEach(LineInterface::destroy);
    }

    @Override
    public void destroy() {
        PropertyTimer.stopWork();
        MessageSubSystemMultiDipatch.destroyAllService();
        destroyAllLine();
        destroyAll();
        super.destroy();
    }


    public DeviceInterface createDevice(String deviceType, String deviceName) {
        return createDevice(null, deviceType, deviceName);
    }

    public DeviceInterface createDevice(Long address, String typeOfDevice, String name) {
        if (name == null) return null;
        DeviceInterface device = createdDevices.get(name);
        if (device == null) {
            if (createDeviceFunction == null) {
                device = createAutoDevice(address, typeOfDevice, name);
            } else {
                device = createDeviceFunction.createDevice(address, typeOfDevice, name);
            }
            if (device != null) {
                createdDevices.put(name, device);
                addElemnt(device);
            }
        }
        return device;
    }


}

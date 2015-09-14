package ua.pp.fairwind.communications;

import ua.pp.fairwind.communications.devices.abstracts.DeviceInterface;
import ua.pp.fairwind.communications.elementsdirecotry.AutoCreateDeviceFunction;
import ua.pp.fairwind.communications.elementsdirecotry.SystemElementDirectory;
import ua.pp.fairwind.communications.lines.SerialLine;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystemMultiDipatch;
import ua.pp.fairwind.communications.timeaction.PropertyTimer;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ������ on 30.06.2015.
 */
public class SCADASystem extends SystemElementDirectory implements AutoCreateDeviceFunction{
    private final HashMap<String,String> uuids;
    private final MessageSubSystem topLevel;
    private final AutoCreateDeviceFunction createDeviceFunction;
    final private ConcurrentHashMap<String,DeviceInterface> createdDevices=new ConcurrentHashMap<>();

    static public String getUiidFromMap(String name,HashMap<String,String> uuids){
        if(name==null || uuids==null) return null;
        return uuids.get(name);
    }

    static public String getUiidFromMap(String deviceName,String propertyName,HashMap<String,String> uuids){
        String name=deviceName+":"+propertyName;
        if(name==null || uuids==null) return null;
        return uuids.get(name);
    }

    static public SCADASystem createScadaSystem(String name,String description,HashMap<String,String> uuids,int maxTrunsactionTime){
        MessageSubSystem topLevel=new MessageSubSystemMultiDipatch();
        SCADASystem scada=new SCADASystem(name,description,topLevel,uuids,null);
        List<LineInterface> serialLines= SerialLine.getSerialLines(scada,maxTrunsactionTime);
        scada.addLines(serialLines);
        return scada;
    }

    protected SCADASystem(String name,String description,MessageSubSystem topLevel,HashMap<String,String> uuids,AutoCreateDeviceFunction createDeviceFunction) {
        super(name, getUiidFromMap(name,uuids),description,topLevel);
        this.uuids=uuids;
        this.topLevel=topLevel;
        this.createDeviceFunction=createDeviceFunction;
    }

    private void destroyAllLine(){
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


    public DeviceInterface createDevice(Long address,String deviceType,String deviceName,String deviceDescription){
        return createDevice(address, deviceType, deviceName, deviceDescription, topLevel, uuids);
    }

    public DeviceInterface createDevice(String deviceType,String deviceName,String deviceDescription){
        return createDevice(null,deviceType,deviceName,deviceDescription,topLevel,uuids);
    }

    public DeviceInterface createDevice(Long address,String deviceType,String deviceName){
        return createDevice(address,deviceType,deviceName,null,topLevel,uuids);
    }

    public DeviceInterface createDevice(String deviceType,String deviceName){
        return createDevice(null,deviceType,deviceName,null,topLevel,uuids);
    }

    public DeviceInterface createDevice(Long address, String typeOfDevice, String name, String description, MessageSubSystem ms, HashMap<String, String> uuids) {
        if(name==null)return null;
        DeviceInterface device=createdDevices.get(name);
        if(device==null) {
            if (createDeviceFunction == null) {
                device = createAutoDevice(address, typeOfDevice, name, description, ms, uuids);
            } else {
                device = createDeviceFunction.createDevice(address, typeOfDevice, name, description, topLevel, uuids);
            }
            if(device!=null){
                createdDevices.put(name,device);
                addElemnt(device);
            }
        }
        return device;
    }


}

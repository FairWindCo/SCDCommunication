package ua.pp.fairwind.communications.elementsdirecotry;

import ua.pp.fairwind.communications.devices.DeviceInterface;
import ua.pp.fairwind.communications.devices.hardwaredevices.Baumer.Encoder;
import ua.pp.fairwind.communications.devices.hardwaredevices.arg.micro.ArgMicroDevice;
import ua.pp.fairwind.communications.devices.hardwaredevices.favorit.FavoritCoreDeviceV1;
import ua.pp.fairwind.communications.devices.hardwaredevices.panDrive.StepDriver;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;

import java.util.HashMap;

/**
 * Created by Сергей on 09.09.2015.
 */
public interface AutoCreateDeviceFunction{
    public static String FAVORIT_VENTIL_V1="FavoritVentilV1";
    public static String PANDRIVE_MOTOR="PanDriveMotor";
    public static String BELIMA_ENCODER="BelimaEncoder";
    public static String ARG_MICRO="ArgMicro";
    DeviceInterface createDevice(Long address,String typeOfDevice,String name,String description,MessageSubSystem ms,HashMap<String,String> uuids);


    static String getUiidFromMap(String name,HashMap<String,String> uuids){
        if(name==null || uuids==null) return null;
        return uuids.get(name);
    }

    default  DeviceInterface createAutoDevice(Long address,String typeOfDevice,String name,String description,MessageSubSystem ms,HashMap<String,String> uuids){
        DeviceInterface newdevice=null;
        switch (typeOfDevice){
            case FAVORIT_VENTIL_V1:{
                newdevice=new FavoritCoreDeviceV1(address==null?1:address,name,getUiidFromMap(name,uuids),(description!=null&&!description.isEmpty())?description:"Favorit Ventil Device",ms,uuids);
                break;
            }
            case PANDRIVE_MOTOR:{
                newdevice=new StepDriver(address==null?1:address,name,getUiidFromMap(name,uuids),(description!=null&&!description.isEmpty())?description:"PanDrive Step Motor",ms,uuids);
                break;
            }
            case BELIMA_ENCODER:{
                newdevice=new Encoder(address==null?3:address,name,getUiidFromMap(name,uuids),(description!=null&&!description.isEmpty())?description:"Belimo Incremental Encoder",ms,uuids);
                break;
            }
            case ARG_MICRO:{
                newdevice=new ArgMicroDevice(address==null?10:address,name,getUiidFromMap(name,uuids),(description!=null&&!description.isEmpty())?description:"Arg Micro",ms,uuids);
                break;
            }
        }
        return newdevice;
    }

}

package ua.pp.fairwind.communications.elementsdirecotry;

import ua.pp.fairwind.communications.devices.abstracts.AbstractDevice;
import ua.pp.fairwind.communications.devices.abstracts.LinedDeviceInterface;
import ua.pp.fairwind.communications.devices.hardwaredevices.Baumer.Encoder;
import ua.pp.fairwind.communications.devices.hardwaredevices.akon.AkonBase;
import ua.pp.fairwind.communications.devices.hardwaredevices.akon.WAD_A06_BUS;
import ua.pp.fairwind.communications.devices.hardwaredevices.arg.micro.ArgMicroDevice;
import ua.pp.fairwind.communications.devices.hardwaredevices.ecotest.BDBG09;
import ua.pp.fairwind.communications.devices.hardwaredevices.favorit.FavoritCoreDeviceV1;
import ua.pp.fairwind.communications.devices.hardwaredevices.modbus.ModBusDevice;
import ua.pp.fairwind.communications.devices.hardwaredevices.panDrive.StepDriver;
import ua.pp.fairwind.communications.devices.hardwaredevices.positron.BDMG04;

import java.util.HashMap;

/**
 * Created by Сергей on 09.09.2015.
 */
public interface AutoCreateDeviceFunction {
    String FAVORIT_VENTIL_V1 = "FavoritVentilV1";
    String PANDRIVE_MOTOR = "PanDriveMotor";
    String BELIMA_ENCODER = "BelimaEncoder";
    String ARG_MICRO = "ArgMicro";
    String AKON = "AkonBase";
    String AKON_WAD_A06_BUS = "WAD_A06_BUS";
    String BDBG09 = "BDBG_09";
    String BDMG04="BDMG_04";
    String MODBUS="MODBUS";

    static String getUiidFromMap(String name, HashMap<String, String> uuids) {
        if (name == null || uuids == null) return null;
        return uuids.get(name);
    }

    AbstractDevice createDevice(String name,String typeOfDevice,Object... params);

    default LinedDeviceInterface createAutoDevice(Long address, String typeOfDevice, String name) {
        LinedDeviceInterface newdevice = null;
        switch (typeOfDevice) {
            case FAVORIT_VENTIL_V1: {
                newdevice = new FavoritCoreDeviceV1(address == null ? 1 : address, name != null ? name : "FAVORIT_VENTIL", null);
                break;
            }
            case PANDRIVE_MOTOR: {
                newdevice = new StepDriver(address == null ? 1 : address, name != null ? name : "PANDRIVE", null);
                break;
            }
            case BELIMA_ENCODER: {
                newdevice = new Encoder(address == null ? 3 : address, name != null ? name : "BAUMER", null);
                break;
            }
            case ARG_MICRO: {
                newdevice = new ArgMicroDevice(address == null ? 10 : address, name != null ? name : "ARGMICRO", null);
                break;
            }
            case AKON: {
                newdevice = new AkonBase(address == null ? 10 : address, name != null ? name : "baseakon", null);
                break;
            }
            case AKON_WAD_A06_BUS: {
                newdevice = new WAD_A06_BUS(address == null ? 10 : address, name, null);
                break;
            }
            case BDBG09: {
                newdevice = new BDBG09(address == null ? 1 : address, name != null ? name : "BDBG09", null);
                break;
            }
            case BDMG04: {
                newdevice = new BDMG04(address == null ? 1 : address, name != null ? name : "BDMG04", null);
                break;
            }
            case MODBUS:{
                newdevice=new ModBusDevice(address == null ? 1 : address, name != null ? name : "MODBUS", null);
                break;
            }
        }
        return newdevice;
    }

}

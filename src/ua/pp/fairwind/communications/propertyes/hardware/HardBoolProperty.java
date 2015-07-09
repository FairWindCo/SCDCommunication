package ua.pp.fairwind.communications.propertyes.hardware;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.HardWareValuePropertyInfo;
import ua.pp.fairwind.communications.propertyes.software.SoftBoolProperty;

/**
 * Created by Сергей on 09.07.2015.
 */
public class HardBoolProperty extends HardWareValuePropertyInfo<Boolean> {

    public HardBoolProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly) {
        super(address, new SoftBoolProperty(name,uuid,description,centralSystem,readonly,writeonly));
    }

    public HardBoolProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly, Boolean value) {
        super(address, new SoftBoolProperty(name,uuid,description,centralSystem,readonly,writeonly,value));
    }

    public HardBoolProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem) {
        this(address, name, uuid, description, centralSystem, false, false);
    }

    public HardBoolProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, Boolean value) {
        this(address, name, uuid, description, centralSystem, false, false,value);
    }
}

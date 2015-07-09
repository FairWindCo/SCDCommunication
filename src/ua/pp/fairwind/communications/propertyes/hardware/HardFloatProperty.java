package ua.pp.fairwind.communications.propertyes.hardware;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.HardWareValuePropertyInfo;
import ua.pp.fairwind.communications.propertyes.software.SoftFloatProperty;

/**
 * Created by Сергей on 09.07.2015.
 */
public class HardFloatProperty extends HardWareValuePropertyInfo<Float> {

    public HardFloatProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly) {
        super(address, new SoftFloatProperty(name,uuid,description,centralSystem,readonly,writeonly));
    }

    public HardFloatProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly, Float value) {
        super(address, new SoftFloatProperty(name,uuid,description,centralSystem,readonly,writeonly,value));
    }

    public HardFloatProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem) {
        this(address, name, uuid, description, centralSystem, false, false);
    }

    public HardFloatProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, Float value) {
        this(address, name, uuid, description, centralSystem, false, false,value);
    }
}

package ua.pp.fairwind.communications.propertyes.hardware;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.HardWareValuePropertyInfo;
import ua.pp.fairwind.communications.propertyes.software.SoftLongProperty;

/**
 * Created by Сергей on 09.07.2015.
 */
public class HardLongProperty extends HardWareValuePropertyInfo<Long> {

    public HardLongProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly) {
        super(address, new SoftLongProperty(name,uuid,description,centralSystem,readonly,writeonly));
    }

    public HardLongProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly,Long value) {
        super(address, new SoftLongProperty(name,uuid,description,centralSystem,readonly,writeonly,value));
    }

    public HardLongProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem) {
        this(address, name, uuid, description, centralSystem, false, false);
    }

    public HardLongProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem,Long value) {
        this(address, name, uuid, description, centralSystem, false, false,value);
    }
}

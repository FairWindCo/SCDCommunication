package ua.pp.fairwind.communications.propertyes.hardware;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.HardWareValuePropertyInfo;
import ua.pp.fairwind.communications.propertyes.software.SoftIntegerProperty;

/**
 * Created by Сергей on 09.07.2015.
 */
public class HardIntProperty extends HardWareValuePropertyInfo<Integer> {

    public HardIntProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly) {
        super(address, new SoftIntegerProperty(name,uuid,description,centralSystem,readonly,writeonly));
    }

    public HardIntProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly, Integer value) {
        super(address, new SoftIntegerProperty(name,uuid,description,centralSystem,readonly,writeonly,value));
    }

    public HardIntProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem) {
        this(address, name, uuid, description, centralSystem, false, false);
    }

    public HardIntProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, Integer value) {
        this(address, name, uuid, description, centralSystem, false, false,value);
    }
}

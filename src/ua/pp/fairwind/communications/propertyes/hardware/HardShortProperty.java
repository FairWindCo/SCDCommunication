package ua.pp.fairwind.communications.propertyes.hardware;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.HardWareValuePropertyInfo;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;

/**
 * Created by Сергей on 09.07.2015.
 */
public class HardShortProperty extends HardWareValuePropertyInfo<Short> {

    public HardShortProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly) {
        super(address, new SoftShortProperty(name,uuid,description,centralSystem,readonly,writeonly));
    }

    public HardShortProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly, Short value) {
        super(address, new SoftShortProperty(name,uuid,description,centralSystem,readonly,writeonly,value));
    }

    public HardShortProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem) {
        this(address, name, uuid, description, centralSystem, false, false);
    }

    public HardShortProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, Short value) {
        this(address, name, uuid, description, centralSystem, false, false,value);
    }
}

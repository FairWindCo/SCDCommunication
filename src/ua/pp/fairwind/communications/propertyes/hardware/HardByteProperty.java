package ua.pp.fairwind.communications.propertyes.hardware;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.HardWareValuePropertyInfo;
import ua.pp.fairwind.communications.propertyes.software.SoftByteProperty;

/**
 * Created by Сергей on 09.07.2015.
 */
public class HardByteProperty extends HardWareValuePropertyInfo<Byte> {

    public HardByteProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly) {
        super(address, new SoftByteProperty(name,uuid,description,centralSystem,readonly,writeonly));
    }

    public HardByteProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly, Byte value) {
        super(address, new SoftByteProperty(name,uuid,description,centralSystem,readonly,writeonly,value));
    }

    public HardByteProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem) {
        this(address, name, uuid, description, centralSystem, false, false);
    }

    public HardByteProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, Byte value) {
        this(address, name, uuid, description, centralSystem, false, false,value);
    }
}

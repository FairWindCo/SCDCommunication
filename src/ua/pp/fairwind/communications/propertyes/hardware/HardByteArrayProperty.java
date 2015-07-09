package ua.pp.fairwind.communications.propertyes.hardware;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.HardWarePropertyInfo;
import ua.pp.fairwind.communications.propertyes.software.SoftByteArrayProperty;

/**
 * Created by Сергей on 09.07.2015.
 */
public class HardByteArrayProperty extends HardWarePropertyInfo {

    public HardByteArrayProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly) {
        super(address, new SoftByteArrayProperty(name, uuid, description, centralSystem));
    }

    public HardByteArrayProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem) {
        this(address, name, uuid, description, centralSystem, false, false);
    }


}

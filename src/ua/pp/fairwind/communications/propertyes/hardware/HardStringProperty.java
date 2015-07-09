package ua.pp.fairwind.communications.propertyes.hardware;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.HardWareValuePropertyInfo;
import ua.pp.fairwind.communications.propertyes.software.SoftStringProperty;

/**
 * Created by Сергей on 09.07.2015.
 */
public class HardStringProperty extends HardWareValuePropertyInfo<String> {

    public HardStringProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly) {
        super(address, new SoftStringProperty(name,uuid,description,centralSystem,readonly,writeonly));
    }

    public HardStringProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly, String value) {
        super(address, new SoftStringProperty(name,uuid,description,centralSystem,readonly,writeonly,value));
    }

    public HardStringProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem) {
        this(address, name, uuid, description, centralSystem, false, false);
    }

    public HardStringProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, String value) {
        this(address, name, uuid, description, centralSystem, false, false,value);
    }
}

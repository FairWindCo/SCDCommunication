package ua.pp.fairwind.communications.propertyes.hardware;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.HardWareValuePropertyInfo;
import ua.pp.fairwind.communications.propertyes.software.SoftCharProperty;

/**
 * Created by Сергей on 09.07.2015.
 */
public class HardCharProperty extends HardWareValuePropertyInfo<Character> {

    public HardCharProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly) {
        super(address, new SoftCharProperty(name,uuid,description,centralSystem,readonly,writeonly));
    }

    public HardCharProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly, Character value) {
        super(address, new SoftCharProperty(name,uuid,description,centralSystem,readonly,writeonly,value));
    }

    public HardCharProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem) {
        this(address, name, uuid, description, centralSystem, false, false);
    }

    public HardCharProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, Character value) {
        this(address, name, uuid, description, centralSystem, false, false,value);
    }
}

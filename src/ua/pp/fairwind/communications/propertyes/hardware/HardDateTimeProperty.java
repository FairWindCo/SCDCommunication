package ua.pp.fairwind.communications.propertyes.hardware;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.HardWareValuePropertyInfo;
import ua.pp.fairwind.communications.propertyes.software.SoftDateTimeProperty;

import java.util.Date;

/**
 * Created by Сергей on 09.07.2015.
 */
public class HardDateTimeProperty extends HardWareValuePropertyInfo<Date> {

    public HardDateTimeProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly) {
        super(address, new SoftDateTimeProperty(name,uuid,description,centralSystem,readonly,writeonly));
    }

    public HardDateTimeProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly, Date value) {
        super(address, new SoftDateTimeProperty(name,uuid,description,centralSystem,readonly,writeonly,value));
    }

    public HardDateTimeProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem) {
        this(address, name, uuid, description, centralSystem, false, false);
    }

    public HardDateTimeProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, Date value) {
        this(address, name, uuid, description, centralSystem, false, false,value);
    }
}

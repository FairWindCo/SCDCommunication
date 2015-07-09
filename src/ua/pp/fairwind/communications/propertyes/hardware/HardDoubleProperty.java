package ua.pp.fairwind.communications.propertyes.hardware;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.HardWareValuePropertyInfo;
import ua.pp.fairwind.communications.propertyes.software.SoftDoubleProperty;

/**
 * Created by Сергей on 09.07.2015.
 */
public class HardDoubleProperty extends HardWareValuePropertyInfo<Double> {

    public HardDoubleProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly) {
        super(address, new SoftDoubleProperty(name,uuid,description,centralSystem,readonly,writeonly));
    }

    public HardDoubleProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly, Double value) {
        super(address, new SoftDoubleProperty(name,uuid,description,centralSystem,readonly,writeonly,value));
    }

    public HardDoubleProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem) {
        this(address, name, uuid, description, centralSystem, false, false);
    }

    public HardDoubleProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, Double value) {
        this(address, name, uuid, description, centralSystem, false, false,value);
    }
}

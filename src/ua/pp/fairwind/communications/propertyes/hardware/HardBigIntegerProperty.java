package ua.pp.fairwind.communications.propertyes.hardware;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.HardWareValuePropertyInfo;
import ua.pp.fairwind.communications.propertyes.software.SoftBigIntegerProperty;

import java.math.BigInteger;

/**
 * Created by Сергей on 09.07.2015.
 */
public class HardBigIntegerProperty extends HardWareValuePropertyInfo<BigInteger> {

    public HardBigIntegerProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly) {
        super(address, new SoftBigIntegerProperty(name,uuid,description,centralSystem,readonly,writeonly));
    }

    public HardBigIntegerProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly, BigInteger value) {
        super(address, new SoftBigIntegerProperty(name,uuid,description,centralSystem,readonly,writeonly,value));
    }

    public HardBigIntegerProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem) {
        this(address, name, uuid, description, centralSystem, false, false);
    }

    public HardBigIntegerProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, BigInteger value) {
        this(address, name, uuid, description, centralSystem, false, false,value);
    }
}

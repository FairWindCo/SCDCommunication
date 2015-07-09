package ua.pp.fairwind.communications.propertyes.hardware;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.HardWareValuePropertyInfo;
import ua.pp.fairwind.communications.propertyes.software.SoftBigDecimalProperty;

import java.math.BigDecimal;

/**
 * Created by Сергей on 09.07.2015.
 */
public class HardBigDecimalProperty extends HardWareValuePropertyInfo<BigDecimal> {

    public HardBigDecimalProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly) {
        super(address, new SoftBigDecimalProperty(name,uuid,description,centralSystem,readonly,writeonly));
    }

    public HardBigDecimalProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, boolean readonly, boolean writeonly, BigDecimal value) {
        super(address, new SoftBigDecimalProperty(name,uuid,description,centralSystem,readonly,writeonly,value));
    }

    public HardBigDecimalProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem) {
        this(address, name, uuid, description, centralSystem, false, false);
    }

    public HardBigDecimalProperty(long address, String name, String uuid, String description, MessageSubSystem centralSystem, BigDecimal value) {
        this(address, name, uuid, description, centralSystem, false, false,value);
    }
}

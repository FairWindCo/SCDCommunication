package ua.pp.fairwind.communications.devices.hardwaredevices.dp5;

import ua.pp.fairwind.communications.propertyes.abstraction.ValueProperty;
import ua.pp.fairwind.communications.propertyes.groups.GroupProperty;
import ua.pp.fairwind.communications.propertyes.software.SoftIntegerProperty;

/**
 * Created by Сергей on 01.12.2015.
 */
public class DPSpectrumValueChanel extends GroupProperty {
    public enum SpectrumSize{
        chanel256(256),
        chanel512(512),
        chanel1024(1024),
        chanel2048(2048),
        chanel4096(4096),
        chanel8192(8192),
        special(20);


        private SpectrumSize(int size) {
            this.size = size;
        }

        private int size;

        public int getSize() {
            return size;
        }


    }

    public DPSpectrumValueChanel(SpectrumSize size,String name, String uuid) {
        super(name, uuid);
        for(int i=0;i<size.getSize();i++){
            addProperty(new SoftIntegerProperty("Chanel #"+i, ValueProperty.SOFT_OPERATION_TYPE.READ_ONLY));
        }
    }
}

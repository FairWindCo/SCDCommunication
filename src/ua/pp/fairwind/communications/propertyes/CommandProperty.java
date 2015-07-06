package ua.pp.fairwind.communications.propertyes;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;

/**
 * Created by ������ on 30.06.2015.
 */
public class CommandProperty extends AbstractProperty {


    public CommandProperty(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public CommandProperty(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public CommandProperty(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }

    public void activate(){

    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    protected void bindPropertyForRead(AbstractProperty property) {
        throw new RuntimeException("NOT SUPPORTED EXCEPTION");
    }

    @Override
    protected void bindPropertyForWrite(AbstractProperty property) {
        throw new RuntimeException("NOT SUPPORTED EXCEPTION");
    }

    @Override
    protected void unbindPropertyForRead() {
        throw new RuntimeException("NOT SUPPORTED EXCEPTION");
    }

    @Override
    protected void unbindPropertyForWrite() {
        throw new RuntimeException("NOT SUPPORTED EXCEPTION");
    }

    @Override
    protected void writeBindingOpearion(AbstractProperty property) {
        
    }
}

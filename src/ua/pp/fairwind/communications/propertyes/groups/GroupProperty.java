package ua.pp.fairwind.communications.propertyes.groups;

import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;

/**
 * Created by ������ on 30.06.2015.
 */
public class GroupProperty extends StaticGroupProperty {

    public GroupProperty(String name, String uuid, AbstractProperty... propertyList) {
        super(name, uuid,  propertyList);
    }

    @Override
    public void addPropertyies(AbstractProperty... propertyList) {
        super.addPropertyies(propertyList);
    }

    @Override
    public void addProperty(AbstractProperty property) {
        super.addProperty(property);
    }

    @Override
    public void remove(AbstractProperty property) {
        super.remove(property);
    }

    @Override
    public void destroy() {
        super.destroy();
    }


}

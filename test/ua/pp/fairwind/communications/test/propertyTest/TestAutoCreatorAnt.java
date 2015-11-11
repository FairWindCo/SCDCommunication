package ua.pp.fairwind.communications.test.propertyTest;

import ua.pp.fairwind.communications.devices.abstracts.AbstractDevice;
import ua.pp.fairwind.communications.utils.EllementsCreator;

/**
 * Created by Сергей on 11.11.2015.
 */
public class TestAutoCreatorAnt {
    public static void main(String[] args) {
        EllementsCreator creator=new EllementsCreator();
        AbstractDevice dev=creator.getDevice("PanDriveMotor");
        System.out.println(dev);
        AbstractDevice dev2=creator.getDevice("PanDriveMotor");
        System.out.println(dev2);
        if(dev!=dev2) System.out.println("INCORRECT");
    }
}

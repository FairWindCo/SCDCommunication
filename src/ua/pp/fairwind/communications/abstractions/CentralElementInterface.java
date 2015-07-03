package ua.pp.fairwind.communications.abstractions;

/**
 * Created by Сергей on 30.06.2015.
 */
public interface CentralElementInterface extends ElementInterface {
    ElementInterface getByUUID(String UUID);
    boolean isMessageBrookerExist();

    void addElement(ElementInterface element);


}

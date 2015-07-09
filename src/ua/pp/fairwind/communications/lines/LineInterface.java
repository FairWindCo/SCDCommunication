package ua.pp.fairwind.communications.lines;

import ua.pp.fairwind.communications.abstractions.ElementInterface;

/**
 * Created by Сергей on 07.07.2015.
 */
public interface LineInterface  extends ElementInterface {
    void startTransaction() throws TrunsactionError;
    void endTransaction();

    void sendMessage(byte[] data,LineParameters params) throws TrunsactionError;
    byte[] reciveMessage(int timeOut,LineParameters params) throws TrunsactionError;

    void async_communicate(CommunicationProtocol request);
}

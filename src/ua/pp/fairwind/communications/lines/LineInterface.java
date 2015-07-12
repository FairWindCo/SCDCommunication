package ua.pp.fairwind.communications.lines;

import ua.pp.fairwind.communications.abstractions.ElementInterface;

import java.util.UUID;

/**
 * Created by Сергей on 07.07.2015.
 */
public interface LineInterface  extends ElementInterface {
    void startTransaction(UUID uuid) throws TrunsactionError;
    void endTransaction(UUID uuid);

    void sendMessage(UUID uuid,byte[] data,LineParameters params) throws TrunsactionError;
    byte[] reciveMessage(UUID uuid,long timeOut,LineParameters params) throws TrunsactionError,LineTimeOutException,LineErrorException;

    void async_communicate(CommunicationProtocol request);
}

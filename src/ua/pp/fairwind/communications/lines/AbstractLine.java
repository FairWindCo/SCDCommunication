package ua.pp.fairwind.communications.lines;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.abstractions.SystemEllement;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Сергей on 10.07.2015.
 */
abstract  public class AbstractLine extends SystemEllement implements LineInterface  {
    private final ConcurrentLinkedQueue<CommunicationProtocol> requests=new ConcurrentLinkedQueue<>();


    public AbstractLine(String name, String uuid, String description, MessageSubSystem centralSystem) {
        super(name, uuid, description, centralSystem);
    }


    @Override
    public void startTransaction() throws TrunsactionError {

    }

    @Override
    public void endTransaction() {

    }

    @Override
    public void sendMessage(byte[] data, LineParameters params) throws TrunsactionError {

    }

    @Override
    public byte[] reciveMessage(int timeOut, LineParameters params) throws TrunsactionError {
        return new byte[0];
    }

    @Override
    public void async_communicate(CommunicationProtocol request) {
        requests.add(request);
    }
}

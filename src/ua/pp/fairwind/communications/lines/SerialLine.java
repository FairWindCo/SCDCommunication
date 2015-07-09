package ua.pp.fairwind.communications.lines;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.abstractions.SystemEllement;

/**
 * Created by Сергей on 09.07.2015.
 */
public class SerialLine extends SystemEllement implements LineInterface {
    public SerialLine(String name, MessageSubSystem centralSystem) {
        super(name, centralSystem);
    }

    public SerialLine(String name, String description, MessageSubSystem centralSystem) {
        super(name, description, centralSystem);
    }

    public SerialLine(String name, String uuid, String description, MessageSubSystem centralSystem) {
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

    }


}

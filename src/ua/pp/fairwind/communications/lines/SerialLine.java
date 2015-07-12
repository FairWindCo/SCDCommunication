package ua.pp.fairwind.communications.lines;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.abstractions.SystemEllement;

/**
 * Created by Сергей on 09.07.2015.
 */
public class SerialLine extends AbstractLine {
    public SerialLine(String name, String uuid, String description, MessageSubSystem centralSystem, long maxTransactionTime) {
        super(name, uuid, description, centralSystem, maxTransactionTime);
    }

    @Override
    protected void sendMessage(byte[] data, LineParameters params) {

    }

    @Override
    protected byte[] reciveMessage(long timeOut, LineParameters params) throws LineErrorException, LineTimeOutException {
        return new byte[0];
    }

    @Override
    protected void onStartTrunsaction() {

    }

    @Override
    protected void onEndTrunsaction() {

    }
}

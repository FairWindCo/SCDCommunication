package ua.pp.fairwind.communications.lines;

import ua.pp.fairwind.communications.lines.abstracts.AbstractLine;
import ua.pp.fairwind.communications.lines.exceptions.LineErrorException;
import ua.pp.fairwind.communications.lines.exceptions.LineTimeOutException;
import ua.pp.fairwind.communications.lines.lineparams.LineParameters;
import ua.pp.fairwind.communications.utils.CommunicationUtils;

/**
 * Created by Сергей on 01.10.2015.
 */
public class LoopBackLine extends AbstractLine {
    volatile byte[] internalBuffer;

    public LoopBackLine(String codename, String uuid) {
        super(codename, uuid);
    }

    public LoopBackLine(String codename) {
        this(codename, null);
    }

    public LoopBackLine() {
        this("LOOPBACK", null);
    }

    @Override
    protected void sendMessageFromServerMode(byte[] data, LineParameters params) throws LineErrorException, LineTimeOutException {
        if(data!=null) internalBuffer=data.clone();
    }

    @Override
    protected void sendMessage(byte[] data, LineParameters params) throws LineErrorException, LineTimeOutException {
        if(data!=null) internalBuffer=data.clone();
    }

    @Override
    protected byte[] reciveMessage(long timeOut, long bytesForReadCount, LineParameters params) throws LineErrorException, LineTimeOutException {
        if(bytesForReadCount==0) return null;
        long StartTime=System.currentTimeMillis();
        long needByte=Math.abs(bytesForReadCount);
        if(internalBuffer!=null||internalBuffer.length<bytesForReadCount) {
            CommunicationUtils.RealThreadPause(timeOut);
        }
        if(internalBuffer!=null&&internalBuffer.length>=needByte) {
            return internalBuffer.clone();
        } else {
            throw new LineTimeOutException(timeOut,StartTime);
        }
    }

    @Override
    protected boolean setupLineParameters(LineParameters params, LineParameters curentLineParameters, boolean alwaysSet) {
        return true;
    }

    @Override
    protected boolean clearBuffers(LineParameters parameters) {
        internalBuffer=null;
        return true;
    }

    @Override
    protected void onStartTrunsaction() {

    }

    @Override
    protected void onEndTrunsaction() {

    }

    @Override
    protected void closeUsedResources() {

    }

    @Override
    protected boolean testIdentialyLineParameters(LineParameters current, LineParameters newparmeters) {
        return true;
    }

    @Override
    protected void activateServerMode(LineParameters serverLineParameter, SERVICE_MODE mode) {

    }

    @Override
    protected void deactivateServerMode() {

    }

    @Override
    public void closeLine() {

    }
}

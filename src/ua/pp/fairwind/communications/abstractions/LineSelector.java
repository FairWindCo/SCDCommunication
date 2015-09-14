package ua.pp.fairwind.communications.abstractions;

import ua.pp.fairwind.communications.lines.lineparams.LineParameters;
import ua.pp.fairwind.communications.utils.CommunicationUtils;

import java.util.Arrays;

/**
 * Created by Сергей on 27.08.2015.
 */
public class LineSelector {
    private final byte[] sendbuffer;
    private final byte[] comparebuffer;
    private final boolean alreadySelect;
    private final long readTimeOut;
    private final long pauseBeforeRead;
    private final long pauseBeforeWrite;
    private final LineParameters lineParam;

    public LineSelector(byte[] sendbuffer, byte[] comparebuffer,LineParameters lineParams,long readTimeOut,long pauseBeforeRead,long pauseBeforeWrite) {
        this.sendbuffer = sendbuffer;
        this.comparebuffer = comparebuffer;
        this.alreadySelect =false;
        this.lineParam=lineParams;
        this.readTimeOut=readTimeOut;
        this.pauseBeforeRead=pauseBeforeRead;
        this.pauseBeforeWrite=pauseBeforeWrite;
    }

    public LineSelector(boolean alreadySelect) {
        this.alreadySelect = alreadySelect;
        this.sendbuffer = null;
        this.comparebuffer = null;
        this.lineParam = null;
        readTimeOut=0;
        this.pauseBeforeRead=0;
        this.pauseBeforeWrite=0;
    }

    public byte[] getSendbuffer() {
        return sendbuffer;
    }

    public byte[] getComparebuffer() {
        return comparebuffer;
    }

    public boolean isAlreadySelect() {
        return alreadySelect;
    }

    public int neededByteCount(){
        if(comparebuffer!=null){
            return comparebuffer.length;
        } else {
            return 0;
        }
    }

    public boolean compare(byte[] readedValue){
        return Arrays.equals(comparebuffer,readedValue);
    }

    public LineParameters getLineParam() {
        return lineParam;
    }

    public long getReadTimeOut() {
        return readTimeOut;
    }

    public void getPauseBeforeRead() {
        if(pauseBeforeRead>0)
            CommunicationUtils.RealThreadPause(pauseBeforeRead);
    }

    public void getPauseBeforeWrite() {
        if(pauseBeforeWrite>0)
            CommunicationUtils.RealThreadPause(pauseBeforeWrite);
    }
}

package ua.pp.fairwind.communications.devices;

/**
 * Created by Сергей on 13.08.2015.
 */
public class RequestInformation {
    final private byte[] bufferForWrite;
    final private long neddedByteForRead;
    final private boolean needRollBack;

    public RequestInformation(byte[] bufferForWrite, long neddedByteForRead,boolean needRollBack) {
        this.bufferForWrite = bufferForWrite;
        this.neddedByteForRead = neddedByteForRead;
        this.needRollBack=needRollBack;
    }

    public RequestInformation(byte[] bufferForWrite, long neddedByteForRead) {
        this.bufferForWrite = bufferForWrite;
        this.neddedByteForRead = neddedByteForRead;
        this.needRollBack=false;
    }

    public byte[] getBufferForWrite() {
        return bufferForWrite;
    }

    public long getNeddedByteForRead() {
        return neddedByteForRead;
    }

    public boolean isNeedRollBack() {
        return needRollBack;
    }
}

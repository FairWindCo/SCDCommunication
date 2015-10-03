package ua.pp.fairwind.communications.devices;

/**
 * Created by Сергей on 13.08.2015.
 */
public class RequestInformation {
    final private byte[] bufferForWrite;
    final private long neddedByteForRead;
    final private boolean needRollBack;
    final private RequestInformation subrequest;

    public RequestInformation(byte[] bufferForWrite, long neddedByteForRead, boolean needRollBack) {
        this(bufferForWrite, neddedByteForRead, needRollBack, null);
    }

    public RequestInformation(byte[] bufferForWrite, long neddedByteForRead) {
        this(bufferForWrite, neddedByteForRead, false, null);
    }

    public RequestInformation(byte[] bufferForWrite, long neddedByteForRead, RequestInformation subrequest) {
        this(bufferForWrite, neddedByteForRead, false, subrequest);
    }

    public RequestInformation(byte[] bufferForWrite, long neddedByteForRead, boolean needRollBack, RequestInformation subrequest) {
        this.bufferForWrite = bufferForWrite;
        this.neddedByteForRead = neddedByteForRead;
        this.needRollBack = needRollBack;
        this.subrequest = subrequest;
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

    public RequestInformation getSubrequest() {
        return subrequest;
    }
}

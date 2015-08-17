package ua.pp.fairwind.communications.devices;

/**
 * Created by Сергей on 13.08.2015.
 */
public class RequestInformation {
    final private byte[] bufferForWrite;
    final private long neddedByteForRead;

    public RequestInformation(byte[] bufferForWrite, long neddedByteForRead) {
        this.bufferForWrite = bufferForWrite;
        this.neddedByteForRead = neddedByteForRead;
    }

    public byte[] getBufferForWrite() {
        return bufferForWrite;
    }

    public long getNeddedByteForRead() {
        return neddedByteForRead;
    }
}

package ua.pp.fairwind.communications.lines;

/**
 * Created by Сергей on 12.07.2015.
 */
public class LineTimeOutException extends Exception {
    final private long startWaitTime;
    final private long waitTime;

    public LineTimeOutException(long startWaitTime, long waitTime) {
        this.startWaitTime = startWaitTime;
        this.waitTime = waitTime;
    }

    public LineTimeOutException(String message, long startWaitTime, long waitTime) {
        super(message);
        this.startWaitTime = startWaitTime;
        this.waitTime = waitTime;
    }

    public LineTimeOutException(String message, Throwable cause, long startWaitTime, long waitTime) {
        super(message, cause);
        this.startWaitTime = startWaitTime;
        this.waitTime = waitTime;
    }

    public LineTimeOutException(Throwable cause, long startWaitTime, long waitTime) {
        super(cause);
        this.startWaitTime = startWaitTime;
        this.waitTime = waitTime;
    }

    public LineTimeOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long startWaitTime, long waitTime) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.startWaitTime = startWaitTime;
        this.waitTime = waitTime;
    }


    public long getStartWaitTime() {
        return startWaitTime;
    }

    public long getWaitTime() {
        return waitTime;
    }
}

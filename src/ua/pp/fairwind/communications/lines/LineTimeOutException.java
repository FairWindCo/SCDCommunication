package ua.pp.fairwind.communications.lines;

/**
 * Created by Сергей on 12.07.2015.
 */
public class LineTimeOutException extends Exception {
    public LineTimeOutException() {
    }

    public LineTimeOutException(String message) {
        super(message);
    }

    public LineTimeOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public LineTimeOutException(Throwable cause) {
        super(cause);
    }

    public LineTimeOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

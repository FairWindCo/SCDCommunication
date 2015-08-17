package ua.pp.fairwind.communications.lines;

/**
 * Created by Сергей on 12.07.2015.
 */
public class LineErrorException extends Exception {
    public LineErrorException() {
    }

    public LineErrorException(String message) {
        super(message);
    }

    public LineErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public LineErrorException(Throwable cause) {
        super(cause);
    }

    public LineErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

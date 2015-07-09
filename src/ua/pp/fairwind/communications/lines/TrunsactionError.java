package ua.pp.fairwind.communications.lines;

/**
 * Created by Сергей on 09.07.2015.
 */
public class TrunsactionError extends IllegalAccessException {
    public enum TrunsactionErrorType{
            ANOTHER_TRUNSACTION_EXECUTE,
            TRUNSACTION_TIMEOUT,
    }

    private final TrunsactionErrorType type;

    public TrunsactionError(String s, TrunsactionErrorType type) {
        super(s);
        this.type = type;
    }

    public TrunsactionErrorType getType() {
        return type;
    }
}

package ua.pp.fairwind.communications.lines;

/**
 * Created by Сергей on 09.07.2015.
 */
public interface LineParameters {
    enum ParametersName{
        IP_ADDRESS,
        IP_PORT,
        RS_SPEED,
        RS_PARITY,
        RS_STOBIT,
        RS_DATABIT,
        RS_DTR,
        RS_RTS,
        RS_FLOWCONTROL,
        TIME_OUT,
        SUB_LINE_NUMBER
    }

    Object getLineParameter(String name);
    String[] getParametresName();
}

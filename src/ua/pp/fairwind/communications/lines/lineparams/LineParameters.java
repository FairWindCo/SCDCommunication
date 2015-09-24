package ua.pp.fairwind.communications.lines.lineparams;

/**
 * Created by Сергей on 09.07.2015.
 */
public interface LineParameters {
    String IP_ADDRESS="IP_ADDRESS";
    String IP_PORT="IP_PORT";

    String RS_SPEED="RS_SPEED";
    String RS_PARITY="RS_PARITY";
    String RS_STOBIT="RS_STOBIT";
    String RS_DATABIT="RS_DATABIT";
    String RS_DTR="RS_DTR";
    String RS_RTS="RS_RTS";
    String RS_FLOWCONTROL="RS_FLOWCONTROL";
    String RS_PURGE_TX="RS_PURGE_TX";
    String RS_PURGE_RX="RS_PURGE_RX";

    String TIME_OUT="TIME_OUT";
    String NEED_LINE_CHANGE="NEED_LINE_CHANGE";
    String SUB_LINE_NUMBER="SUB_LINE_NUMBER";

    String ALWAYS_SET_LINE_PARAM="ALWAYS_SET_LINE_PARAM";
    String ALWAYS_SET_LINE="ALWAYS_SET_LINE";

    Object getLineParameter(String name);
    String[] getParametresName();

    static boolean getBooleanParameter(LineParameters curent,String parameterName){
        if(curent==null) return false;
        Object val=curent.getLineParameter(parameterName);
        if(val==null || !(val instanceof Boolean))return false;
        return (Boolean)val;
    }

    static boolean compareLineParameter(LineParameters curent,LineParameters newval,String parameterName){
        if(curent==null){
            return newval == null;
        } else {
            if(newval==null) return false;
        }
        Object curentVal=curent.getLineParameter(parameterName);
        Object newVal=newval.getLineParameter(parameterName);
        if(newVal==curentVal)return true;
        if(curentVal!=null){
            return curentVal.equals(newVal);
        } else {
            return newVal == null;
        }
    }
}

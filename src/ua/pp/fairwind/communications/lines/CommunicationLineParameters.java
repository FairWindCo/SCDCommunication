package ua.pp.fairwind.communications.lines;

/**
 * Created by Сергей on 09.07.2015.
 */
public class CommunicationLineParameters implements LineParameters{
    final private String ipadress;
    final private int port;
    final private long timeOut;
    final private int baoudrate;
    final private int databits;
    final private int paritytype;
    final private int stopbit;
    final private int sublinenum;
    final private boolean dtr;
    final private boolean rts;
    final private int flowcontrol;
    final private boolean needLineChange;
    final private boolean purge_rx;
    final private boolean purge_tx;
    final private boolean always_set_line_param;
    final private boolean always_set_line;

    public CommunicationLineParameters(String ipadress, int port, long timeOut) {
        this(ipadress, port, timeOut, 0, 0, 0, 0, 0,false,false,0,false,false,false,false,false);
    }

    public CommunicationLineParameters(String ipadress, int port, long timeOut, int baoudrate, int databits, int paritytype, int stopbit,int flowcontrol, boolean dtr, boolean rts, int sublinenum,  boolean needLineChange, boolean purge_rx, boolean purge_tx, boolean always_set_line_param, boolean always_set_line) {
    //public CommunicationLineParameters(String ipadress, int port, long timeOut, int baoudrate, int databits, int paritytype, int stopbit, int sublinenum,boolean dtr, boolean rts,int flowcontrol,   boolean needLineChange, boolean purge_rx, boolean purge_tx, boolean always_set_line_param, boolean always_set_line) {
        this.ipadress = ipadress;
        this.port = port;
        this.timeOut = timeOut;
        this.baoudrate = baoudrate;
        this.databits = databits;
        this.paritytype = paritytype;
        this.stopbit = stopbit;
        this.sublinenum = sublinenum;
        this.dtr = dtr;
        this.rts = rts;
        this.flowcontrol = flowcontrol;
        this.needLineChange = needLineChange;
        this.purge_rx = purge_rx;
        this.purge_tx = purge_tx;
        this.always_set_line_param = always_set_line_param;
        this.always_set_line = always_set_line;
    }
//ipadress, port,timeOut,baoudrate,databits,paritytype,stopbit, int sublinenum, boolean dtr, boolean rts, int flowcontrol, boolean needLineChange, boolean purge_rx, boolean purge_tx, boolean always_set_line_param, boolean always_set_line
    public CommunicationLineParameters(int baoudrate, int databits, int paritytype, int stopbit) {
        this(null,0,0,baoudrate,databits,paritytype,stopbit,0,false,false,0,false,false,false,false,false);
    }
    public CommunicationLineParameters(int baoudrate, int databits, int paritytype, int stopbit,int flowcontrol) {
        this(null,0,0,baoudrate,databits,paritytype,stopbit,flowcontrol,false,false,0,false,false,false,false,false);
    }
    public CommunicationLineParameters(int baoudrate, int databits, int paritytype, int stopbit,int flowcontrol,boolean setDTR,boolean setRTS) {
        this(null,0,0,baoudrate,databits,paritytype,stopbit,flowcontrol,setRTS,setDTR,0,false,false,false,false,false);
    }

    public CommunicationLineParameters(int baoudrate, int databits, int paritytype, int stopbit,int flowcontrol,int sublinenum) {
        this(null,0,0,baoudrate,databits,paritytype,stopbit,flowcontrol,false,false,sublinenum,true,false,false,false,false);
    }
    public CommunicationLineParameters(int baoudrate, int databits, int paritytype, int stopbit,int flowcontrol,boolean setDTR,boolean setRTS,int sublinenum) {
        this(null,0,0,baoudrate,databits,paritytype,stopbit,flowcontrol,setRTS,setDTR,sublinenum,true,false,false,false,false);
    }

    public CommunicationLineParameters(int baoudrate, int databits, int paritytype, int stopbit,boolean purge_rx,boolean purge_tx) {
        this(null,0,0,baoudrate,databits,paritytype,stopbit,0,false,false,0,false,purge_rx,purge_tx,false,false);
    }

    public CommunicationLineParameters(int baoudrate, int databits, int paritytype, int stopbit,int flowcontrol,boolean setDTR,boolean setRTS,boolean purge_rx,boolean purge_tx) {
        this(null,0,0,baoudrate,databits,paritytype,stopbit,flowcontrol,setRTS,setDTR,0,false,purge_rx,purge_tx,false,false);
    }

    public CommunicationLineParameters(int baoudrate, int databits, int paritytype, int stopbit,int flowcontrol,int sublinenum,boolean purge_rx,boolean purge_tx) {
        this(null,0,0,baoudrate,databits,paritytype,stopbit,flowcontrol,false,false,sublinenum,true,purge_rx,purge_tx,false,false);
    }
    public CommunicationLineParameters(int baoudrate, int databits, int paritytype, int stopbit,int flowcontrol,boolean setDTR,boolean setRTS,int sublinenum,boolean purge_rx,boolean purge_tx) {
        this(null,0,0,baoudrate,databits,paritytype,stopbit,flowcontrol,setRTS,setDTR,sublinenum,true,purge_rx,purge_tx,false,false);
    }


    public enum SerialLineSpeed{
        BAUDRATE_4800(4800,"4800bod"),
        BAUDRATE_9600(9600,"4800bod"),
        BAUDRATE_14400(14400,"4800bod"),
        BAUDRATE_19200(19200,"4800bod"),
        BAUDRATE_38400(38400,"4800bod"),
        BAUDRATE_57600(57600,"4800bod"),
        BAUDRATE_115200(115200,"4800bod"),
        BAUDRATE_128000(128000,"4800bod"),
        BAUDRATE_256000(256000,"4800bod");
        private int speed;
        private String name;

        SerialLineSpeed(int speed, String name) {
            this.speed = speed;
            this.name = name;
        }

        public int getSpeed() {
            return speed;
        }

        public String getName() {
            return name;
        }
    }


    @Override
    public Object getLineParameter(String name) {
        if (name==null)return null;
        switch (name){
            case LineParameters.IP_ADDRESS:return ipadress;
            case LineParameters.IP_PORT:return port;
            case LineParameters.TIME_OUT:return timeOut;
            case LineParameters.RS_SPEED:return baoudrate;
            case LineParameters.RS_PARITY:return paritytype;
            case LineParameters.RS_STOBIT:return stopbit;
            case LineParameters.RS_DATABIT:return databits;
            case LineParameters.SUB_LINE_NUMBER:return sublinenum;
            case LineParameters.RS_RTS:return rts;
            case LineParameters.RS_DTR:return dtr;
            case LineParameters.RS_FLOWCONTROL:return flowcontrol;
            case LineParameters.NEED_LINE_CHANGE:return needLineChange;
            case LineParameters.RS_PURGE_TX:return purge_rx;
            case LineParameters.RS_PURGE_RX:return purge_tx;
            case LineParameters.ALWAYS_SET_LINE_PARAM:return always_set_line_param;
            case LineParameters.ALWAYS_SET_LINE:return always_set_line;
        }
        return null;
    }

    @Override
    public String[] getParametresName() {
        return new String[]{"IP_ADDRESS","IP_PORT","TIME_OUT","RS_SPEED","RS_PARITY","RS_STOBIT","RS_DATABIT","SUB_LINE_NUMBER","RS_DTR","RS_RTS","RS_FLOWCONTROL","NEED_LINE_CHANGE"};
    }

    public String getIpadress() {
        return ipadress;
    }

    public int getPort() {
        return port;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public int getBaoudrate() {
        return baoudrate;
    }

    public int getDatabits() {
        return databits;
    }

    public int getParitytype() {
        return paritytype;
    }

    public int getStopbit() {
        return stopbit;
    }

    public int getSublinenum() {
        return sublinenum;
    }

    public boolean isDtr() {
        return dtr;
    }

    public boolean isRts() {
        return rts;
    }

    public int getFlowcontrol() {
        return flowcontrol;
    }

    public boolean isNeedLineChange() {
        return needLineChange;
    }

    public boolean isPurge_rx() {
        return purge_rx;
    }

    public boolean isPurge_tx() {
        return purge_tx;
    }

    public boolean isAlways_set_line_param() {
        return always_set_line_param;
    }

    public boolean isAlways_set_line() {
        return always_set_line;
    }



    static public boolean compare_RS_PARAMETERS(LineParameters curent,LineParameters newvalue){
        if(curent==null){
            if(newvalue==null) return true;
            return false;
        } else {
            if(newvalue==null) return false;
        }
        boolean result=false;
        if(curent instanceof CommunicationLineParameters && newvalue instanceof CommunicationLineParameters){
            if(((CommunicationLineParameters) newvalue).purge_tx || ((CommunicationLineParameters) newvalue).purge_rx)return false;
            result=((CommunicationLineParameters) curent).baoudrate==((CommunicationLineParameters) newvalue).baoudrate;
            result&=((CommunicationLineParameters) curent).databits==((CommunicationLineParameters) newvalue).databits;
            result&=((CommunicationLineParameters) curent).stopbit==((CommunicationLineParameters) newvalue).stopbit;
            result&=((CommunicationLineParameters) curent).paritytype==((CommunicationLineParameters) newvalue).paritytype;
            result&=((CommunicationLineParameters) curent).flowcontrol==((CommunicationLineParameters) newvalue).flowcontrol;
            result&=((CommunicationLineParameters) curent).dtr==((CommunicationLineParameters) newvalue).dtr;
            result&=((CommunicationLineParameters) curent).rts==((CommunicationLineParameters) newvalue).rts;
            result&=((CommunicationLineParameters) curent).purge_rx==((CommunicationLineParameters) newvalue).purge_rx;
            result&=((CommunicationLineParameters) curent).purge_tx==((CommunicationLineParameters) newvalue).purge_tx;
        } else {
            if(LineParameters.getBooleanParameter(newvalue,RS_PURGE_RX)||LineParameters.getBooleanParameter(newvalue,RS_PURGE_TX))return false;
            result=LineParameters.compareLineParameter(curent,newvalue,RS_SPEED);
            result&=LineParameters.compareLineParameter(curent,newvalue,RS_DATABIT);
            result&=LineParameters.compareLineParameter(curent,newvalue,RS_STOBIT);
            result&=LineParameters.compareLineParameter(curent,newvalue,RS_PARITY);
            result&=LineParameters.compareLineParameter(curent,newvalue,RS_FLOWCONTROL);
            result&=LineParameters.compareLineParameter(curent,newvalue,RS_DTR);
            result&=LineParameters.compareLineParameter(curent,newvalue,RS_RTS);
            result&=LineParameters.compareLineParameter(curent,newvalue,RS_PURGE_RX);
            result&=LineParameters.compareLineParameter(curent,newvalue,RS_PURGE_TX);
        }
        return result;
    }

    static public boolean compare_RS_BAUD(LineParameters curent,LineParameters newvalue){
        if(curent==null){
            if(newvalue==null) return true;
            return false;
        } else {
            if(newvalue==null) return false;
        }
        boolean result=false;
        if(curent instanceof CommunicationLineParameters && newvalue instanceof CommunicationLineParameters){
            result=((CommunicationLineParameters) curent).baoudrate==((CommunicationLineParameters) newvalue).baoudrate;
            result&=((CommunicationLineParameters) curent).databits==((CommunicationLineParameters) newvalue).databits;
            result&=((CommunicationLineParameters) curent).stopbit==((CommunicationLineParameters) newvalue).stopbit;
            result&=((CommunicationLineParameters) curent).paritytype==((CommunicationLineParameters) newvalue).paritytype;
            result&=((CommunicationLineParameters) curent).dtr==((CommunicationLineParameters) newvalue).dtr;
            result&=((CommunicationLineParameters) curent).rts==((CommunicationLineParameters) newvalue).rts;
        } else {
            result=LineParameters.compareLineParameter(curent,newvalue,RS_SPEED);
            result&=LineParameters.compareLineParameter(curent,newvalue,RS_DATABIT);
            result&=LineParameters.compareLineParameter(curent,newvalue,RS_STOBIT);
            result&=LineParameters.compareLineParameter(curent,newvalue,RS_PARITY);
            result&=LineParameters.compareLineParameter(curent,newvalue,RS_DTR);
            result&=LineParameters.compareLineParameter(curent,newvalue,RS_RTS);
        }
        return result;
    }


}

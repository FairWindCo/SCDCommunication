package ua.pp.fairwind.communications.lines;

import jssc.*;
import ua.pp.fairwind.communications.internatianalisation.I18N;
import ua.pp.fairwind.communications.lines.abstracts.AbstractLine;
import ua.pp.fairwind.communications.lines.abstracts.LineInterface;
import ua.pp.fairwind.communications.lines.exceptions.LineErrorException;
import ua.pp.fairwind.communications.lines.exceptions.LineTimeOutException;
import ua.pp.fairwind.communications.lines.lineparams.CommunicationLineParameters;
import ua.pp.fairwind.communications.lines.lineparams.LineParameters;
import ua.pp.fairwind.communications.messagesystems.event.EventType;
import ua.pp.fairwind.communications.utils.CommunicationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Сергей on 09.07.2015.
 */
public class SerialLine extends AbstractLine {
    final SerialPort port;
    final SerialPortEventListener eventlistener;

    public static List<LineInterface> getSerialLines(long maxTransactionTime){
        String[] portnames=SerialPortList.getPortNames();
        if(portnames!=null){
            List<LineInterface> list=new ArrayList<>(portnames.length);
            for(String portName:portnames)list.add(new SerialLine(portName,portName,null,maxTransactionTime));
            return list;
        }else return null;
    }

    public SerialLine(String comportName,String name, String uuid, long maxTransactionTime) {
        super(name, uuid, maxTransactionTime);
        port=new SerialPort(comportName);
        eventlistener=(event)->{
            if(isServerMode()){
                try {
                    int count = port.getInputBufferBytesCount();
                    if (count > 0) {
                        byte[] data = port.readBytes(count, 1000);
                        serviceRecivedFromLineDataInServerMode(data);
                    }
                }catch (SerialPortTimeoutException e){
                    fireEvent(EventType.FATAL_ERROR,e);
                }catch (SerialPortException ex){
                    fireEvent(EventType.FATAL_ERROR,ex);
                }
            }
        };
    }
    @Override
    protected void activateServerMode(LineParameters serverLineParameter,SERVICE_MODE mode){
        try {
            if(!port.isOpened()){
                port.openPort();
                setLineParameters(serverLineParameter);
            }
            port.addEventListener(eventlistener);
        } catch (SerialPortException e){
            fireEvent(EventType.FATAL_ERROR,e.getLocalizedMessage());
        }
    }
    @Override
    protected void deactivateServerMode(){
        try{
            port.removeEventListener();
            port.closePort();
        } catch (SerialPortException e){
            fireEvent(EventType.FATAL_ERROR,e.getLocalizedMessage());
        }
    }


    @Override
    synchronized protected void closeUsedResources() {
        try {
            if (port.isOpened()) {
                port.closePort();
            }
        }catch (SerialPortException ex){
            fireEvent(EventType.ERROR,ex.getLocalizedMessage());
        }
    }

    @Override
    synchronized protected void sendMessage(byte[] data, LineParameters params) throws LineErrorException, LineTimeOutException {
        try {
            if (!port.isOpened()) {
                port.openPort();
            }
            setLineParameters(params);
            //setLineParameters(params);
            //port.purgePort(SerialPort.PURGE_RXCLEAR|SerialPort.PURGE_RXCLEAR);
            port.writeBytes(data);

        }catch (SerialPortException portexception){
            try {
                if(port.isOpened())port.closePort();
            } catch (SerialPortException prp){
                //nothing
            }
            throw new LineErrorException(portexception.getMessage(),portexception);
        }
    }

    @Override
    synchronized protected byte[] reciveMessage(long timeOut,long bytesForReadCount, LineParameters params) throws LineErrorException, LineTimeOutException {
        if(bytesForReadCount==0) return null;
        try {
            if (!port.isOpened()) {
                port.openPort();
                setLineParameters(params);
            }
            //setLineParameters(params);
            long starttime=System.currentTimeMillis();
            if(bytesForReadCount<=0){
                //System.out.println("READ TIMEOUT"+timeOut);
                CommunicationUtils.RealThreadPause(timeOut);
                int recivedByteCount=port.getInputBufferBytesCount();
                if(recivedByteCount>=Math.abs(bytesForReadCount)){
                    return port.readBytes(recivedByteCount);
                } else {
                    throw new LineTimeOutException(starttime,System.currentTimeMillis()-starttime);
                }
            } else {
                try {
                    //System.out.println("READ TIMEOUT"+timeOut);
                    //System.out.print("START READ:"+ new Date(starttime)+" LENGTH:");
                    byte[] buf=port.readBytes((int) bytesForReadCount, (int) timeOut);
                    //System.out.println(System.currentTimeMillis()-starttime);
                    return buf;
                }catch (SerialPortTimeoutException timeoutex){
                    int recivedByteCount=port.getInputBufferBytesCount();
                    if(recivedByteCount>Math.abs(bytesForReadCount)){
                        return port.readBytes(recivedByteCount);
                    } else {
                        //System.out.println(System.currentTimeMillis()-starttime);
                        throw new LineTimeOutException(starttime,System.currentTimeMillis()-starttime);
                    }
                }

            }
        }catch (SerialPortException portexception){
            throw new LineErrorException(portexception.getMessage(),portexception);
        }
    }

    protected boolean clearBuffers(LineParameters parameters){
        try {
            if (port.isOpened()) {
                boolean clearRX=true;
                boolean clearTX=false;
                if(parameters!=null) {
                    clearRX = (boolean) (parameters.getLineParameter(LineParameters.RS_PURGE_RX) != null ? parameters.getLineParameter(LineParameters.RS_PURGE_RX) : true);
                    clearTX = (boolean) (parameters.getLineParameter(LineParameters.RS_PURGE_TX) != null ? parameters.getLineParameter(LineParameters.RS_PURGE_TX) : false);
                }
                if (clearTX) {
                    System.out.println("CLEAR TX");
                    port.purgePort(SerialPort.PURGE_TXABORT | SerialPort.PURGE_TXCLEAR);
                }
                if (clearRX) {
                    System.out.println("CLEAR RX");
                    port.purgePort(SerialPort.PURGE_RXABORT | SerialPort.PURGE_RXCLEAR);
                }
            }
            return true;
        } catch (SerialPortException e){
            System.out.println(e.getLocalizedMessage());
            fireEvent(EventType.ERROR,e.getLocalizedMessage());
            return false;
        }
    }

    protected boolean setupLineParameters(LineParameters parameters, LineParameters curentLineParameters, boolean alwaysSet){
        if(parameters!=null) {
            int speed = (int) (parameters.getLineParameter(LineParameters.RS_SPEED)!=null?parameters.getLineParameter(LineParameters.RS_SPEED):SerialPort.BAUDRATE_9600);
            int databit = (int) (parameters.getLineParameter(LineParameters.RS_DATABIT)!=null?parameters.getLineParameter(LineParameters.RS_DATABIT):SerialPort.DATABITS_8);
            int stopbit = (int) (parameters.getLineParameter(LineParameters.RS_STOBIT)!=null?parameters.getLineParameter(LineParameters.RS_STOBIT):SerialPort.STOPBITS_1);
            int parity = (int) (parameters.getLineParameter(LineParameters.RS_PARITY)!=null?parameters.getLineParameter(LineParameters.RS_PARITY):SerialPort.PARITY_NONE);
            int flowcontrol = (int) (parameters.getLineParameter(LineParameters.RS_FLOWCONTROL)!=null?parameters.getLineParameter(LineParameters.RS_FLOWCONTROL):SerialPort.FLOWCONTROL_NONE);
            boolean dtr = (boolean) (parameters.getLineParameter(LineParameters.RS_DTR)!=null?parameters.getLineParameter(LineParameters.RS_DTR):false);
            boolean rts = (boolean) (parameters.getLineParameter(LineParameters.RS_RTS)!=null?parameters.getLineParameter(LineParameters.RS_RTS):false);
            try {
                if (!port.isOpened()) port.openPort();
                if(alwaysSet || !CommunicationLineParameters.compare_RS_BAUD(parameters, curentLineParameters)){
                    System.out.printf("SET BAUD RATE %d DATABIT: %d STOP %d PARITY %d \n",speed,databit,stopbit,parity);
                    port.setParams(speed, databit, stopbit, parity, rts, dtr);
                }
                if(alwaysSet || !LineParameters.compareLineParameter(parameters,curentLineParameters,LineParameters.RS_FLOWCONTROL)) {
                    System.out.println("FLOW CONTROL:"+flowcontrol);
                    port.setFlowControlMode(flowcontrol);
                }
                return true;
            } catch (SerialPortException e){
                System.out.println(e.getLocalizedMessage());
                fireEvent(EventType.ERROR,e.getLocalizedMessage());
                return false;
            }
        } else {
            try {
                if (!port.isOpened()) port.openPort();
                port.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE, false, false);
                port.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
                return true;
            }catch (SerialPortException e){

                fireEvent(EventType.ERROR,e);
                return false;
            }
        }
    }

    @Override
    protected void onStartTrunsaction() {

    }

    @Override
    protected void onEndTrunsaction() {

    }

    @Override
    synchronized public void destroy() {
        try {
            if (port.isOpened())
                port.closePort();
        } catch (SerialPortException exc){
        //do nothing
        }
        super.destroy();
    }

    @Override
    public String toString() {
        return String.format(I18N.getLocalizedString("serialline.description"),getName(),getUUIDString(),getDescription());
    }

    @Override
    protected boolean testIdentialyLineParameters(LineParameters current, LineParameters newparmeters) {
        return CommunicationLineParameters.compare_RS_PARAMETERS(current,newparmeters);
    }

    @Override
    public void closeLine() {
        try {
            if (port.isOpened())
                port.closePort();
        } catch (SerialPortException exc){
            fireEvent(EventType.ERROR,exc.getLocalizedMessage());
        }
    }
}

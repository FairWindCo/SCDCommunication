package ua.pp.fairwind.communications.lines;

import jssc.*;
import ua.pp.fairwind.communications.SCADASystem;
import ua.pp.fairwind.communications.internatianalisation.I18N;
import ua.pp.fairwind.communications.lines.exceptions.LineErrorException;
import ua.pp.fairwind.communications.lines.exceptions.LineTimeOutException;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.propertyes.event.EventType;
import ua.pp.fairwind.communications.utils.CommunicationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Сергей on 09.07.2015.
 */
public class SerialLine extends AbstractLine {
    final SerialPort port;
    final SerialPortEventListener eventlistener;

    public static List<LineInterface> getSerialLines(MessageSubSystem centralSystem, long maxTransactionTime){
        String[] portnames=SerialPortList.getPortNames();
        if(portnames!=null){
            List<LineInterface> list=new ArrayList<>(portnames.length);
            for(String portName:portnames)list.add(new SerialLine(portName,portName,null,I18N.getLocalizedString("serialline.name"),centralSystem,maxTransactionTime));
            return list;
        }else return null;
    }

    public static List<LineInterface> getSerialLines(SCADASystem scadaSystem, long maxTransactionTime){
        String[] portnames=SerialPortList.getPortNames();
        if(portnames!=null){
            List<LineInterface> list=new ArrayList<>(portnames.length);
            for(String portName:portnames)list.add(new SerialLine(portName,portName,null,I18N.getLocalizedString("serialline.name"),scadaSystem.getChileMessageSubsystems(),maxTransactionTime));
            return list;
        }else return null;
    }

    public SerialLine(String comportName,String name, String uuid, String description, MessageSubSystem centralSystem, long maxTransactionTime) {
        super(name, uuid, description, centralSystem, maxTransactionTime);
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
    public void setServerLineParameter(boolean setServerMode, LineParameters serverLineParameter) {
        if(setServerMode){
            try {
                if(!port.isOpened()){
                    port.openPort();
                    setLineParameters(serverLineParameter);
                }
                port.addEventListener(eventlistener);
            } catch (SerialPortException e){
                serverMode.set(false);
                fireEvent(EventType.FATAL_ERROR,e.getLocalizedMessage());
                return;
            }

        } else {
            try{
                port.removeEventListener();
                port.closePort();
            } catch (SerialPortException e){
                serverMode.set(false);
                fireEvent(EventType.FATAL_ERROR,e.getLocalizedMessage());
                return;
            }
        }
        super.setServerLineParameter(setServerMode, serverLineParameter);
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

    protected boolean setLineParameters(LineParameters parameters){
        if(parameters!=null) {
            int speed = (int) (parameters.getLineParameter("RS_SPEED")!=null?parameters.getLineParameter("RS_SPEED"):SerialPort.BAUDRATE_9600);
            int databit = (int) (parameters.getLineParameter("RS_DATABIT")!=null?parameters.getLineParameter("RS_DATABIT"):SerialPort.DATABITS_8);
            int stopbit = (int) (parameters.getLineParameter("RS_STOBIT")!=null?parameters.getLineParameter("RS_STOBIT"):SerialPort.STOPBITS_1);
            int parity = (int) (parameters.getLineParameter("RS_PARITY")!=null?parameters.getLineParameter("RS_PARITY"):SerialPort.PARITY_NONE);
            int flowcontrol = (int) (parameters.getLineParameter("RS_FLOWCONTROL")!=null?parameters.getLineParameter("RS_FLOWCONTROL"):SerialPort.FLOWCONTROL_NONE);
            boolean dtr = (boolean) (parameters.getLineParameter("RS_DTR")!=null?parameters.getLineParameter("RS_DTR"):false);
            boolean rts = (boolean) (parameters.getLineParameter("RS_RTS")!=null?parameters.getLineParameter("RS_RTS"):false);
            boolean clearRX = (boolean) (parameters.getLineParameter("RS_PURGE_RX")!=null?parameters.getLineParameter("RS_PURGE_RX"):false);
            boolean clearTX = (boolean) (parameters.getLineParameter("RS_PURGE_TX")!=null?parameters.getLineParameter("RS_PURGE_TX"):false);
            try {
                if (!port.isOpened()) port.openPort();
                port.setParams(speed, databit, stopbit, parity, rts, dtr);
                port.setFlowControlMode(flowcontrol);
                if(clearTX){
                    port.purgePort(SerialPort.PURGE_TXABORT|SerialPort.PURGE_TXCLEAR);
                }
                if(clearRX){
                    port.purgePort(SerialPort.PURGE_RXABORT|SerialPort.PURGE_RXCLEAR);
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
        super.destroy();
        try {
            if (port.isOpened())
                port.closePort();
        } catch (SerialPortException exc){

        }
    }

    @Override
    public String toString() {
        return String.format(I18N.getLocalizedString("serialline.description"),getName(),getUUIDString(),getDescription());
    }
}

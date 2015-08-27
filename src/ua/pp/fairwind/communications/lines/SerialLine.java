package ua.pp.fairwind.communications.lines;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;
import ua.pp.fairwind.communications.lines.exceptions.LineErrorException;
import ua.pp.fairwind.communications.lines.exceptions.LineTimeOutException;
import ua.pp.fairwind.communications.messagesystems.MessageSubSystem;
import ua.pp.fairwind.communications.utils.CommunicationUtils;

/**
 * Created by Сергей on 09.07.2015.
 */
public class SerialLine extends AbstractLine {
    final SerialPort port;
    public SerialLine(String comportName,String name, String uuid, String description, MessageSubSystem centralSystem, long maxTransactionTime) {
        super(name, uuid, description, centralSystem, maxTransactionTime);
        port=new SerialPort(comportName);
    }

    @Override
    synchronized protected void sendMessage(byte[] data, LineParameters params) throws LineErrorException, LineTimeOutException {
        try {
            if (!port.isOpened()) port.openPort();
            setLineParameters(params);
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
            if (!port.isOpened()) port.openPort();
            setLineParameters(params);
            long starttime=System.currentTimeMillis();
            if(bytesForReadCount<=0){
                CommunicationUtils.RealThreadPause(timeOut);
                int recivedByteCount=port.getInputBufferBytesCount();
                if(recivedByteCount>=Math.abs(bytesForReadCount)){
                    return port.readBytes(recivedByteCount);
                } else {
                    throw new LineTimeOutException(starttime,System.currentTimeMillis()-starttime);
                }
            } else {
                try {

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

    private void setLineParameters(LineParameters parameters) throws SerialPortException{
        if(parameters!=null) {
            int speed = (int) (parameters.getLineParameter("RS_SPEED")!=null?parameters.getLineParameter("RS_SPEED"):SerialPort.BAUDRATE_9600);
            int databit = (int) (parameters.getLineParameter("RS_DATABIT")!=null?parameters.getLineParameter("RS_DATABIT"):SerialPort.DATABITS_8);
            int stopbit = (int) (parameters.getLineParameter("RS_STOBIT")!=null?parameters.getLineParameter("RS_STOBIT"):SerialPort.STOPBITS_1);
            int parity = (int) (parameters.getLineParameter("RS_PARITY")!=null?parameters.getLineParameter("RS_PARITY"):SerialPort.PARITY_NONE);
            int flowcontrol = (int) (parameters.getLineParameter("RS_FLOWCONTROL")!=null?parameters.getLineParameter("RS_FLOWCONTROL"):SerialPort.FLOWCONTROL_NONE);
            boolean dtr = (boolean) (parameters.getLineParameter("RS_DTR")!=null?parameters.getLineParameter("RS_DTR"):false);
            boolean rts = (boolean) (parameters.getLineParameter("RS_RTS")!=null?parameters.getLineParameter("RS_RTS"):false);
            port.setParams(speed, databit, stopbit, parity, rts, dtr);
            port.setFlowControlMode(flowcontrol);
        } else {
            port.setParams(SerialPort.BAUDRATE_9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE,false,false);
            port.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
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
}

package ua.pp.fairwind.communications.lines;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;
import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
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
    protected void sendMessage(byte[] data, LineParameters params) throws LineErrorException, LineTimeOutException  {
        try {
            if (!port.isOpened()) port.openPort();
            setLineParameters(params);
            port.writeBytes(data);
        }catch (SerialPortException portexception){
            throw new LineErrorException(portexception.getMessage(),portexception);
        }
    }

    @Override
    protected byte[] reciveMessage(long timeOut,long bytesForReadCount, LineParameters params) throws LineErrorException, LineTimeOutException {
        if(bytesForReadCount==0) return null;
        try {
            if (!port.isOpened()) port.openPort();
            setLineParameters(params);
            if(bytesForReadCount<=0){
                CommunicationUtils.RealThreadPause(timeOut);
                int recivedByteCount=port.getInputBufferBytesCount();
                if(recivedByteCount>Math.abs(bytesForReadCount)){
                    return port.readBytes(recivedByteCount);
                } else {
                    throw new LineTimeOutException();
                }
            } else {
                try {
                    return port.readBytes((int) bytesForReadCount, (int) timeOut);
                }catch (SerialPortTimeoutException timeoutex){
                    int recivedByteCount=port.getInputBufferBytesCount();
                    if(recivedByteCount>Math.abs(bytesForReadCount)){
                        return port.readBytes(recivedByteCount);
                    } else {
                        throw new LineTimeOutException();
                    }
                }

            }
        }catch (SerialPortException portexception){
            throw new LineErrorException(portexception.getMessage(),portexception);
        }
    }

    private void setLineParameters(LineParameters parameters) throws SerialPortException{
        int speed=(int)parameters.getLineParameter("RS_SPEED");
        int databit=(int)parameters.getLineParameter("RS_DATABIT");
        int stopbit=(int)parameters.getLineParameter("RS_STOBIT");
        int parity=(int)parameters.getLineParameter("RS_PARITY");
        int flowcontrol=(int)parameters.getLineParameter("RS_FLOWCONTROL");
        boolean dtr=(boolean)parameters.getLineParameter("RS_DTR");
        boolean rts=(boolean)parameters.getLineParameter("RS_RTS");
        port.setParams(speed,databit,stopbit,parity,rts,dtr);
        port.setFlowControlMode(flowcontrol);
    }

    @Override
    protected void onStartTrunsaction() {

    }

    @Override
    protected void onEndTrunsaction() {

    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            if (port.isOpened())
                port.closePort();
        } catch (SerialPortException exc){

        }
    }
}

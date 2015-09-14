package ua.pp.fairwind.communications.lines.operations;

import ua.pp.fairwind.communications.lines.abstracts.LineInterface;

import java.util.Date;

/**
 * Created by Сергей on 09.07.2015.
 */
public class CommunicationAnswer {
    public enum CommunicationResult{
        SUCCESS,
        ERROR,
        TIMEOUT,
        READ_MONITOR,
        WRITE_MONITOR
    }
    private final long executeTime=System.currentTimeMillis();
    private final CommunicationProtocolRequest request;
    private final CommunicationResult status;
    private final byte[] recivedMessage;
    private final String informationMesssage;
    private final LineInterface communicateOverLine;
    private final long startWaiting;
    private final long waitTime;

    public CommunicationAnswer(CommunicationProtocolRequest request, CommunicationResult status, byte[] recivedMessage, String informationMesssage,LineInterface communicateOverLine,long startWaiting,long waitTime) {
        this.request = request;
        this.status = status;
        this.recivedMessage = recivedMessage;
        this.informationMesssage = informationMesssage;
        this.communicateOverLine=communicateOverLine;
        this.startWaiting=startWaiting;
        this.waitTime=waitTime;
    }

    public CommunicationAnswer(CommunicationProtocolRequest request, byte[] recivedMessage,LineInterface communicateOverLine,long startWaiting,long waitTime) {
        this.request = request;
        this.status = CommunicationResult.SUCCESS;
        this.recivedMessage = recivedMessage;
        this.informationMesssage = "SUCCESS";
        this.communicateOverLine=communicateOverLine;
        this.startWaiting=startWaiting;
        this.waitTime=waitTime;
    }

    public long getExecuteTime() {
        return executeTime;
    }

    public CommunicationProtocolRequest getRequest() {
        return request;
    }

    public CommunicationResult getStatus() {
        return status;
    }

    public byte[] getRecivedMessage() {
        return recivedMessage;
    }

    public String getInformationMesssage() {
        if(status==CommunicationResult.TIMEOUT){
            return informationMesssage+" WAIT_TIME:"+waitTime+" START_WAIT:"+new Date(startWaiting);
        } else {
            return informationMesssage;
        }
    }

    public LineInterface getCommunicateOverLine() {
        return communicateOverLine;
    }

    public boolean isReservLineAvaible(){
        if(request==null) return false;
        return request.isExistReservLine();
    }

    public boolean sendOverReservLine() {
        if(request!=null){
            return request.sendRequestOverReservLine();
        }
        return false;
    }

    @Override
    protected void finalize() throws Throwable {
        destroy();
        super.finalize();
    }

    public void destroy(){
        if(request!=null){
            request.destroy();
        }
    }

    public void invalidate(){
        if(request!=null){
            request.invalidate();
        }
    }
}

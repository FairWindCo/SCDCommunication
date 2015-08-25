package ua.pp.fairwind.communications.lines;

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

    public CommunicationAnswer(CommunicationProtocolRequest request, CommunicationResult status, byte[] recivedMessage, String informationMesssage,LineInterface communicateOverLine) {
        this.request = request;
        this.status = status;
        this.recivedMessage = recivedMessage;
        this.informationMesssage = informationMesssage;
        this.communicateOverLine=communicateOverLine;
    }

    public CommunicationAnswer(CommunicationProtocolRequest request, byte[] recivedMessage,LineInterface communicateOverLine) {
        this.request = request;
        this.status = CommunicationResult.SUCCESS;
        this.recivedMessage = recivedMessage;
        this.informationMesssage = "SUCCESS";
        this.communicateOverLine=communicateOverLine;
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
        return informationMesssage;
    }

    public LineInterface getCommunicateOverLine() {
        return communicateOverLine;
    }

    public boolean isReservLineAvaible(){
        if(request==null) return false;
        return request.isExistReservLine();
    }

    public void sendOverReservLine(){
        if(request!=null){
            request.sendRequestOverReservLine();
        }
    }
}

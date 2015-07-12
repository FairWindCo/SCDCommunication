package ua.pp.fairwind.communications.lines;

import ua.pp.fairwind.communications.abstractions.MessageSubSystem;
import ua.pp.fairwind.communications.abstractions.SystemEllement;
import ua.pp.fairwind.communications.devices.DeviceInterface;

import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Сергей on 10.07.2015.
 */
abstract  public class AbstractLine extends SystemEllement implements LineInterface  {
    private final ConcurrentLinkedQueue<CommunicationProtocol> requests=new ConcurrentLinkedQueue<>();
    private final AtomicBoolean threadRunned=new AtomicBoolean(false);
    private final AtomicBoolean threadCreated=new AtomicBoolean(false);
    private final AtomicBoolean transuction=new AtomicBoolean(false);
    private volatile long startTrunsactionTime=-1;
    private volatile UUID trunsactionUUID;
    private final long maxTransactionTime;


    public AbstractLine(String name, String uuid, String description, MessageSubSystem centralSystem,long maxTransactionTime) {
        super(name, uuid, description, centralSystem);
        this.maxTransactionTime = maxTransactionTime;
    }


    synchronized private boolean isTrunsactionActive(){
        if(transuction.get()){
            if(startTrunsactionTime+ maxTransactionTime >System.currentTimeMillis()){
                transuction.set(false);
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    synchronized private boolean isTrunsactionActive(UUID uuid){
        if(uuid==null) return false;
        if(transuction.get()){
            if(startTrunsactionTime+ maxTransactionTime >System.currentTimeMillis()){
                transuction.set(false);
                return false;
            } else {
                if(uuid.equals(trunsactionUUID)) return false;
                return true;
            }
        } else {
            return false;
        }
    }


    @Override
    synchronized public void startTransaction(UUID uuid) throws TrunsactionError {
        if(uuid==null) throw new TrunsactionError("NEED UUID!!", TrunsactionError.TrunsactionErrorType.TRUNSACTION_ERROR);
        if(!uuid.equals(trunsactionUUID) && transuction.get() && startTrunsactionTime+ maxTransactionTime <System.currentTimeMillis()){
            throw new TrunsactionError("Another transaction started!", TrunsactionError.TrunsactionErrorType.ANOTHER_TRUNSACTION_EXECUTE);
        } else {
            trunsactionUUID=uuid;
            startTrunsactionTime=System.currentTimeMillis();
            transuction.set(true);
            onStartTrunsaction();
        }
    }

    abstract protected void sendMessage(byte[] data, LineParameters params);
    abstract protected byte[] reciveMessage(long timeOut, LineParameters params) throws LineErrorException,LineTimeOutException;
    abstract protected void onStartTrunsaction();
    abstract protected void onEndTrunsaction();

    @Override
    synchronized public void endTransaction(UUID uuid) {
        if(uuid!=null && isTrunsactionActive() && uuid.equals(trunsactionUUID)){
            transuction.set(false);
            onEndTrunsaction();
        }
    }

    @Override
    public void sendMessage(UUID uuid,byte[] data, LineParameters params) throws TrunsactionError {
        if(!isTrunsactionActive(uuid)){
            sendMessage(data,params);
        } else {
            throw new TrunsactionError("Another transaction started!", TrunsactionError.TrunsactionErrorType.TRUNSACTION_ERROR);
        }
    }

    @Override
    public byte[] reciveMessage(UUID uuid,long timeOut, LineParameters params) throws TrunsactionError,LineTimeOutException,LineErrorException {
        if(!isTrunsactionActive(uuid)){
            return reciveMessage(timeOut,params);
        } else {
            throw new TrunsactionError("Another transaction started!", TrunsactionError.TrunsactionErrorType.TRUNSACTION_ERROR);
        }
    }

    private CommunicationAnswer processRequest(CommunicationProtocol request){
        if(request!=null){
            sendMessage(
                    request.getBytesForSend(),
                    request.getParameters());
            if(request.getPauseBeforeRead()>0)
                try {
                    Thread.sleep(request.getPauseBeforeRead());
                } catch (InterruptedException e) {
                    //nothing to do
                }
            try {
                byte[] buf=reciveMessage(request.getTimeOut(),request.getParameters());
                CommunicationAnswer answ=new CommunicationAnswer(request, buf,this);
                return answ;
            } catch (LineErrorException e) {
                CommunicationAnswer answ=new CommunicationAnswer(request, CommunicationAnswer.CommunicationResult.ERROR,null,e.getLocalizedMessage(),this);
                return answ;
            } catch (LineTimeOutException e) {
                CommunicationAnswer answ=new CommunicationAnswer(request, CommunicationAnswer.CommunicationResult.TIMEOUT,null,e.getLocalizedMessage(),this);
                return answ;
            }

        }
        return new CommunicationAnswer(request, CommunicationAnswer.CommunicationResult.ERROR,null,"NULL REQUEST",this);
    }


    @Override
    public void async_communicate(CommunicationProtocol request) {
        requests.add(request);
        if(!threadCreated.get()){
            Thread processor=new Thread(new Runnable() {
                @Override
                public void run() {
                    threadCreated.set(true);
                    threadRunned.set(true);
                    while (threadRunned.get()){
                        CommunicationProtocol request=requests.peek();
                        CommunicationAnswer answer=processRequest(request);
                        if(request.getSenderDevice()!=null){
                            DeviceInterface dev=request.getSenderDevice();
                            dev.processRecivedMessage(answer);
                        }
                    }
                    threadCreated.set(false);
                }
            });
            processor.start();
        }
    }
}

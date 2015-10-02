package ua.pp.fairwind.communications.devices.hardwaredevices.Baumer;

import ua.pp.fairwind.communications.devices.abstracts.AbstractDevice;
import ua.pp.fairwind.communications.devices.abstracts.RSLineImmitatorDevice;
import ua.pp.fairwind.communications.propertyes.software.SoftShortProperty;

/**
 * Created by Сергей on 01.10.2015.
 */
public class EncoderImmitator extends RSLineImmitatorDevice {
    private final SoftShortProperty revolution;
    private final SoftShortProperty steps;
    private volatile int curentState=0;

    public EncoderImmitator(long address, String codename, String uuid) {
        super(address, codename, uuid);
        revolution=new SoftShortProperty("BAUMER.REVOLUTION");
        revolution.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 001L);
        steps=new SoftShortProperty("BAUMER.STEPS");
        steps.setAdditionalInfo(AbstractDevice.PROPERTY_ADDRESS, 002L);
        addPropertys(revolution);
        addPropertys(steps);
    }

    @Override
    public String getDeviceType() {
        return "BAUMER Encoder Imitator";
    }


    protected byte[] setState(byte curentByte,long deviceAddress){
        switch (curentState){
            case 0:{
                if(curentByte==(byte)0x01){
                    curentState=1;
                }
            }break;
            case 1:{
                if(curentByte==(byte)0x80){
                    curentState=2;
                } else {
                    curentState=0;
                }
            }break;
            case 2:{
                if(curentByte==(byte)(deviceAddress&0x07)){
                    curentState=3;
                } else {
                    curentState=0;
                }
            }break;
            case 3:{
                if(curentByte==(byte)0x80){
                    curentState=4;
                } else {
                    curentState=0;
                }
            }break;
            case 4:{
                if(curentByte==(byte)0x4){
                    curentState=0;
                    int revolve=this.revolution.getValue()==null?0:this.revolution.getValue(); //(short)(((recivedMessage[i + 2]<<8)+recivedMessage[i + 3])&0xFF);
                    int steps=this.steps.getValue()==null?0:this.steps.getValue();//(short)(((recivedMessage[i + 4]<<8)+recivedMessage[i + 5])&0xFF);
                    byte[] result=new byte[8];
                    result[0]=(byte)0x1;
                    result[1]=(byte)((deviceAddress & 0x7)+0x30);
                    result[2]=(byte)((revolve>>8)&0xFF);
                    result[3]=(byte)((revolve)&0xFF);
                    result[4]=(byte)((steps>>8)&0xFF);
                    result[5]=(byte)((steps)&0xFF);
                    result[7]=(byte)0x4;
                    result[6] = (byte) ((result[1] ^ result[2] ^ result[3] ^ result[4] ^ result[5]) & 0xFF);
                    return result;
                } else {
                    curentState=0;
                }
            }break;
            default:
                curentState=0;
        }
        return null;
    }

    public SoftShortProperty getRevolution() {
        return revolution;
    }

    public SoftShortProperty getSteps() {
        return steps;
    }


}

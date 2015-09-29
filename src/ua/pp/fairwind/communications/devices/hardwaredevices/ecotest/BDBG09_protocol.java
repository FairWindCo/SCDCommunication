package ua.pp.fairwind.communications.devices.hardwaredevices.ecotest;

import ua.pp.fairwind.communications.devices.RequestInformation;

/**
 * Created by Сергей on 28.09.2015.
 */
public class BDBG09_protocol {

    public static byte BDBG_CRC(byte[] buf,int length){
        byte res=0;
        if(buf!=null && buf.length>0 && buf.length>=length){
            int CRC=0;
            for(int i=0;i<length;i++){
                CRC+=(int)buf[i] & 0xFF;
                CRC+=((CRC>>8) & 0xFF);
                CRC=(CRC & 0xFF);
            }
            res=(byte)(CRC&0xFF);
        }
        return res;
    }

    public static byte BDBG_CRC(byte[] buf,int startpos,int length){
        byte res=0;
        if(buf!=null && buf.length>0 && buf.length>=startpos+length){
            int CRC=0;
            for(int i=startpos;i<startpos+length;i++){
                CRC+=(int)buf[i] & 0xFF;
                CRC+=((CRC>>8) & 0xFF);
                CRC=(CRC & 0xFF);
            }
            res=(byte)(CRC&0xFF);
        }
        return res;
    }

    public static float parseMSPFloat(byte[] buffer, int pos) {
        if(buffer.length>=pos+4){
            int bits=0;
            bits+=((buffer[pos+2]<<0 ) & 0xFF);
            bits+=((buffer[pos+3]<<8 ) & 0xFF00);
            bits+=((buffer[pos+0]<<16) & 0x7F0000);
            bits+=((buffer[pos+1]<<23) & 0x7F800000);

            if((buffer[pos+0] & 0x80)>0) bits+=0x80000000;
            return (Float.intBitsToFloat(bits)/2);
        } else return 0;
    }

    public static byte[] prepareMSPFloat(float value) {
        byte[] buffer=new byte[4];
        int bits = Float.floatToIntBits(value*2);
        buffer[0]=(byte)((bits>>16) & 0x7F);
        buffer[1]=(byte)((bits>>23) & 0xFF);
        buffer[2]=(byte)(bits & 0xFF);
        buffer[3]=(byte)((bits>>8) & 0xFF);
        return buffer;
    }



    public static int prepareMSPFloat(float value,byte[] buffer,int pos) {
        if(buffer!=null && pos+4<buffer.length) {
            int bits = Float.floatToIntBits(value * 2);
            buffer[pos + 0] = (byte) ((bits >> 16) & 0x7F);
            buffer[pos + 1] = (byte) ((bits >> 23) & 0xFF);
            buffer[pos + 2] = (byte) (bits & 0xFF);
            buffer[pos + 3] = (byte) ((bits >> 8) & 0xFF);
            return pos+4;
        }
        return pos;
    }

    public static int prepareWord(int value,byte[] buffer,int pos) {
        if(buffer!=null && pos+2<buffer.length) {
            buffer[pos + 0] = (byte) ((value     ) & 0xFF);
            buffer[pos + 1] = (byte) ((value >> 8) & 0xFF);
            return pos+2;
        }
        return pos;
    }

    public static int parseWord(byte[] buffer,int pos) {
        int result=0;
        if(buffer!=null && pos+2<buffer.length) {
            result =(buffer[pos + 0]& 0xFF);
            result+=(buffer[pos + 1]& 0xFF)<<8;
        }
        return result;
    }

    public static float parseBDBGTemp(byte[] buffer, int pos,int length) {
        if(buffer.length>=pos+4 &&  length>=4){
            int bits = (buffer[pos+0] & 0xFF);
            bits+=((buffer[pos+1]<<8) & 0xFF00);
            return (float)(bits/3.68645);
        } else return 0;
    }

    public static byte[] prepareBDBGTemp(float value) {
        byte[] buffer=new byte[2];
        int val=(int)(value*3.68645);
        buffer[0]=(byte)(val & 0xFF);
        buffer[1]=(byte)((val>>8) & 0xFF);
        return buffer;
    }

    public static float  parseKoifFloat(byte[] buffer, int pos, int length) {
        if(buffer.length>=pos+2 &&  length>=2){
            int bits = (buffer[pos+0] & 0xFF);
            bits+=((buffer[pos+1]<<8) & 0xFF00);
            return (float)(bits/3.68645);
        } else return 0;
    }



    public static byte[] prepareKoifFloat(float value) {
        byte[] buffer=new byte[2];
        int val=(int)(value*3.68645);
        buffer[0]=(byte)(val & 0xFF);
        buffer[1]=(byte)((val>>8) & 0xFF);
        return buffer;
    }

    public static long getResponseLength(byte frame_code){
        switch (frame_code){
            case 0:return 10;
            case 0x8:return 6;
            case 0x5:return 8;
            case 0x4:return 40;
        }
        return 0;
    }

    public static RequestInformation READREQUEST(byte frame_code,byte device_address){
        byte res=(byte)(((frame_code<<4)+(device_address & 0xF))&0xFF);
        byte[] pack=new byte[3];
        pack[0]=(byte)0x55;
        pack[1]=(byte)0xAA;
        pack[2]=res;
        return new RequestInformation(pack,getResponseLength(frame_code));
    }

    public static RequestInformation WRITEREQUEST(byte device_address,float koif_hight_detect,float ded_time_hight_detect,int ded_time_phis_hight_detect,int ded_time_phis3_hight_detect,float fon__hight_detect,float base_fon__hight_detect,float koif_low_detect,float ded_time_low_detect,int ded_time_phis_low_detect,int ded_time_phis3_low_detect,float fon_low_detect){
        byte res=(byte)(((0x2<<4)+(device_address & 0xF))&0xFF);
        byte[] pack=new byte[40];
        pack[0]=(byte)0x55;
        pack[1]=(byte)0xAA;
        pack[2]=res;
        int index=3;
        index=prepareMSPFloat(koif_hight_detect, pack, index);
        index=prepareMSPFloat(ded_time_hight_detect, pack, index);
        index=prepareWord(ded_time_phis_hight_detect, pack, index);
        index=prepareWord(ded_time_phis3_hight_detect, pack, index);
        index=prepareMSPFloat(fon__hight_detect, pack, index);
        index=prepareMSPFloat(base_fon__hight_detect, pack, index);
        index=prepareMSPFloat(koif_low_detect, pack, index);
        index=prepareMSPFloat(ded_time_low_detect, pack, index);
        index=prepareWord(ded_time_phis_low_detect, pack, index);
        index=prepareWord(ded_time_phis3_low_detect, pack, index);
        index=prepareMSPFloat(fon_low_detect, pack, index);
        pack[index]=BDBG_CRC(pack,index);
        return new RequestInformation(pack,3);
    }

    public static RequestInformation WRITEREQUEST(byte device_address,byte new_address){
        byte res=(byte)(((0x6<<4)+(device_address & 0xF))&0xFF);
        byte[] pack=new byte[5];
        pack[0]=(byte)0x55;
        pack[1]=(byte)0xAA;
        pack[2]=res;
        pack[3]=(byte)(new_address&0xF);
        pack[4]=BDBG_CRC(pack,4);
        return new RequestInformation(pack,3);
    }

    public static long getResponseLength_v3(byte frame_code){
        switch (frame_code){
            case 0:return 12;
            case 0x8:return 8;
            case 0x5:return 11;
            case 0x4:return 42;
        }
        return 0;
    }

    public static RequestInformation READREQUEST_v3(byte frame_code,byte device_address){
        byte[] pack=new byte[6];
        pack[0]=(byte)0x55;
        pack[1]=(byte)0xAA;
        pack[2]=(byte)0x70;
        pack[3]=device_address;
        pack[4]=frame_code;
        pack[5]=BDBG_CRC(pack,5);
        return new RequestInformation(pack,getResponseLength_v3(frame_code));
    }

    public static RequestInformation WRITEREQUEST_v3(byte device_address,float koif_hight_detect,float ded_time_hight_detect,int ded_time_phis_hight_detect,int ded_time_phis3_hight_detect,float fon__hight_detect,float base_fon__hight_detect,float koif_low_detect,float ded_time_low_detect,int ded_time_phis_low_detect,int ded_time_phis3_low_detect,float fon_low_detect){
        byte[] pack=new byte[42];
        pack[0]=(byte)0x55;
        pack[1]=(byte)0xAA;
        pack[2]=(byte)0x70;
        pack[3]=device_address;
        pack[4]=0x2;
        int index=5;
        index=prepareMSPFloat(koif_hight_detect, pack, index);
        index=prepareMSPFloat(ded_time_hight_detect, pack, index);
        index=prepareWord(ded_time_phis_hight_detect, pack, index);
        index=prepareWord(ded_time_phis3_hight_detect, pack, index);
        index=prepareMSPFloat(fon__hight_detect, pack, index);
        index=prepareMSPFloat(base_fon__hight_detect, pack, index);
        index=prepareMSPFloat(koif_low_detect, pack, index);
        index=prepareMSPFloat(ded_time_low_detect, pack, index);
        index=prepareWord(ded_time_phis_low_detect, pack, index);
        index=prepareWord(ded_time_phis3_low_detect, pack, index);
        index=prepareMSPFloat(fon_low_detect, pack, index);
        pack[index]=BDBG_CRC(pack,index);
        return new RequestInformation(pack,3);
    }


    public static RequestInformation WRITEREQUEST_v3(byte device_address,byte new_address,byte coif){
        byte[] pack=new byte[8];
        pack[0]=(byte)0x55;
        pack[1]=(byte)0xAA;
        pack[2]=(byte)0x70;
        pack[3]=device_address;
        pack[4]=0x6;
        pack[5]=(byte)(new_address&0xF);
        pack[6]=coif;
        pack[7]=BDBG_CRC(pack,7);
        return new RequestInformation(pack,3);
    }

    public static boolean checkCRC(byte[] buffer,int startpos,int crcpos){
        byte crc=buffer[crcpos];
        byte calccrc=BDBG_CRC(buffer, startpos,crcpos);
        if(crc==calccrc){
            return true;
        }
        return false;
    }


}

package ua.pp.fairwind.communications.devices.hardwaredevices.dp5;

import ua.pp.fairwind.communications.propertyes.abstraction.AbstractProperty;
import ua.pp.fairwind.communications.propertyes.groups.GroupProperty;
import ua.pp.fairwind.communications.propertyes.software.stringlike.StringFloatValuedProperty;
import ua.pp.fairwind.communications.propertyes.software.stringlike.StringIntegerValuedProperty;
import ua.pp.fairwind.communications.propertyes.software.stringlike.StringLongValuedProperty;
import ua.pp.fairwind.communications.propertyes.software.stringlike.StringValuedPropertry;

import java.io.*;
import java.util.stream.Stream;

/**
 * Created by Сергей on 04.10.2015.
 */
public class DP5ConfigurationChanel extends GroupProperty {
    private StringIntegerValuedProperty aCKE;
    private StringIntegerValuedProperty aINP;
    private StringIntegerValuedProperty aUO1;
    private StringIntegerValuedProperty aUO2;
    private StringIntegerValuedProperty bLRD;
    private StringIntegerValuedProperty bLRU;
    private StringIntegerValuedProperty bLRM;
    private StringIntegerValuedProperty bOOT;
    private StringIntegerValuedProperty cON1;
    private StringIntegerValuedProperty cON2;
    private StringIntegerValuedProperty cLKL;
    private StringIntegerValuedProperty cUSP;
    private StringIntegerValuedProperty dACF;
    private StringIntegerValuedProperty dACO;
    private StringIntegerValuedProperty gAIA;
    private StringIntegerValuedProperty gATE;
    private StringIntegerValuedProperty gPED;
    private StringIntegerValuedProperty gPGA;
    private StringIntegerValuedProperty gPIN;
    private StringIntegerValuedProperty gPMC;
    private StringIntegerValuedProperty gPME;
    private StringIntegerValuedProperty hVSE;
    private StringIntegerValuedProperty iNOF;
    private StringIntegerValuedProperty iNOG;
    private StringIntegerValuedProperty mCAC;
    private StringIntegerValuedProperty mCAE;
    private StringIntegerValuedProperty mCSL;
    private StringIntegerValuedProperty mCSH;
    private StringFloatValuedProperty mCST;
    private StringIntegerValuedProperty pAPS;
    private StringFloatValuedProperty pAPZ;
    private StringIntegerValuedProperty pDMD;
    private StringIntegerValuedProperty pRCL;
    private StringIntegerValuedProperty pRCH;
    private StringLongValuedProperty pREC;
    private StringLongValuedProperty pRER;
    private StringFloatValuedProperty pRET;
    private StringIntegerValuedProperty rESC;
    private StringIntegerValuedProperty cLCK;
    private StringFloatValuedProperty gAIF;
    private StringFloatValuedProperty gAIN;
    private StringFloatValuedProperty pURE;
    private StringIntegerValuedProperty mCAS;
    private StringIntegerValuedProperty rTDS;
    private StringFloatValuedProperty rTDT;
    private StringIntegerValuedProperty sCAI;
    private StringIntegerValuedProperty sCAH;
    private StringIntegerValuedProperty sCAL;
    private StringIntegerValuedProperty sCAO;
    private StringIntegerValuedProperty sCAW;
    private StringIntegerValuedProperty sCOE;
    private StringIntegerValuedProperty sCOG;
    private StringIntegerValuedProperty sCOT;
    private StringFloatValuedProperty sOFF;
    private StringIntegerValuedProperty sYNC;
    private StringIntegerValuedProperty tECS;
    private StringFloatValuedProperty tHFA;
    private StringFloatValuedProperty tHSL;
    private StringIntegerValuedProperty tLLD;
    private StringIntegerValuedProperty tPMO;
    private StringIntegerValuedProperty vOLU;
    private StringFloatValuedProperty tPEA;
    private StringIntegerValuedProperty rESL;
    private StringFloatValuedProperty tFLA;
    private StringIntegerValuedProperty tPFA;
    private StringIntegerValuedProperty rTDE;
    private StringIntegerValuedProperty rTDD;
    private StringIntegerValuedProperty rTDW;

    public void readFile(String fileName)throws IOException {
        File file = new File(fileName);
        if (file.exists() && file.canRead()) {
            BufferedReader br = null;
            try (FileReader fr = new FileReader(file)) {
                br = new BufferedReader(fr);
                String line = br.readLine();
                int index = 0;
                while (line != null) {
                    String[] vals = line.split("=");
                    if (vals.length == 2) {
                        StringValuedPropertry ch = (StringValuedPropertry) getPopertyByIndex(index++);
                        if (ch != null) {
                            //TODO ch.readValueFromString(line, 10);
                            ch.checkInternalValue();
                        }
                    }
                    line = br.readLine();
                }

            } finally {
                if (br != null) br.close();

            }

        }
    }

    public void writeConfigToFile(String fileName) throws IOException{
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();


        if (file.canWrite()) {

            try (PrintWriter writer = new PrintWriter((file))) {
                Stream<AbstractProperty> stream = getStream();
                if (stream != null) {
                    //stream.collect(Collectors.joining("\n");
                    //TODO stream.forEach(p -> writer.println(p.formStringValue("%s")));
                }
                writer.flush();

            }
        }

    }

    public enum DEVICETYPE{
        DP5,
        DP5G,
        PX5,
        MCA8000D,
        CUSTOM
    }

    public DP5ConfigurationChanel(String name, String uuid, DEVICETYPE devicetype) {
        super(name, uuid);
        /*C*/aCKE = new StringIntegerValuedProperty("ACKE","ACK / Don't ACK packets with errors","OFF", "ON","OFF","OF");
		/*G*/aINP = new StringIntegerValuedProperty("AINP","Analog input pos/neg", "NEG", "NEG","POS","NE","PO");
		/*M*/aUO1 = new StringIntegerValuedProperty("AUO1","AUX_OUT selection", "ICR", 1, 8, "#","ICR","PILEUP","MCSTB","ONESH","DETRES","MCAEN","PEAKH","SCA8");
		/*M*/aUO2 = new StringIntegerValuedProperty("AUO2","AUX_OUT2 selection", "ICR", 1, 8, "#","ICR","DIAG","RTDOS","ONESH","RTDREJ","LIVE","PEAKH","VETO","STREAM");
		/*-*/bLRD = new StringIntegerValuedProperty("BLRD","BLR down correction", "0", 0, 3, "#");
		/*-*/bLRU=new StringIntegerValuedProperty("BLRU","BLR up correction", "0", 0, 3, "#");
		/*-*/bLRM = new StringIntegerValuedProperty("BLRM","BLR mode", "OFF", "1","OFF","OF");
		/*P*/bOOT = new StringIntegerValuedProperty("BOOT","Turn supplies on/off at power up", "OFF", "ON","OFF","OF");
        cLKL = new StringIntegerValuedProperty("CLKL","List-mode clock", "100", "100","1000");
		/*-*/cUSP = new StringIntegerValuedProperty("CUSP","Non-trapezoidal shaping", "0", -99, 99, "##");
		/*M*/dACF = new StringIntegerValuedProperty("DACF","DAC offset", "0", -500, 499, "###");
		/*M*/dACO = new StringIntegerValuedProperty("DACO","DAC output", "OFF", 1, 8, "#","OFF","FAST","SHAPED","INPUT","PEAK");
        gAIA = new StringIntegerValuedProperty("GAIA","Analog gain index", "1", 1, 28, "###");
		/*+*/gATE = new StringIntegerValuedProperty("GATE","Gate control", "OFF", "HIGH","OFF","OF","HI","LOW","LO");
		/*M*/gPED = new StringIntegerValuedProperty("GPED","G.P. counter edge", "FALLING", "FALLING","FA","RISING","RI");
		/*M*/gPGA = new StringIntegerValuedProperty("GPGA","G.P. counter uses GATE", "OFF", "ON","OFF","OF");
		/*M*/gPIN = new StringIntegerValuedProperty("GPIN","G.P. counter input", "AUX1", 1, 8, "#","AUX1","AUX2","PILEUP","RTDREJ","SCA8","TBD","DETRES","OFF");
		/*M*/gPMC = new StringIntegerValuedProperty("GPMC","G.P. counter cleared with MCA counters?", "ON", "ON","OFF","OF");
		/*M*/gPME = new StringIntegerValuedProperty("GPME","G.P. counter uses MCA_EN?", "ON", "ON","OFF","OF");
		/*P*/hVSE = new StringIntegerValuedProperty("HVSE","HV BIAS set", "OFF", 0, 1499, "#","OFF","OF");
		
		/*+*/mCAC = new StringIntegerValuedProperty("MCAC","MCA/MCS channels", "1024", "256","512","1024","2048","4096","8192");
		/*+*/mCAE = new StringIntegerValuedProperty("MCAE","MCA/MCS enable", "ON", "ON","OFF","OF");
		/*+*/mCSL = new StringIntegerValuedProperty("MCSL","MCS low threshold", "0", 0, 8191, "####");
		/*+*/mCSH = new StringIntegerValuedProperty("MCSH","MCS high threshold", "8191", 0, 8191, "####");
		/*+*/mCST = new StringFloatValuedProperty("MCST","MCS timebase", "0", 0.01f, 655.35f, "##.####");
		/*+*/pRCL = new StringIntegerValuedProperty("PRCL","Preset counts, low threshold", "0", 0, 8191, "####");
		/*+*/pRCH = new StringIntegerValuedProperty("PRCH","Preset counts, high threshold", "0", 0, 8191, "####");
		/*+*/pREC = new StringLongValuedProperty("PREC","Preset counts", "OFF", 0L, 4294967295L, "########","OFF","OF");
		/*+*/pRER = new StringLongValuedProperty("PRER","Preset Real Time", "OFF", 0L, 4294967295L, "########","OFF","OF");
		/*+*/pRET = new StringFloatValuedProperty("PRET","Preset time", "OFF", 0f, 99999999.9f, "########","OFF","OF");
		/*-*/rTDS = new StringIntegerValuedProperty("RTDS","RTD sensitivity", "0", 0, 1593, "########");
		/*-*/rTDT = new StringFloatValuedProperty("RTDT","RTD threshold", "0", 0f, 49.9f, "########");
        sCAI = new StringIntegerValuedProperty("SCAI","SCA index", "1", 1, 16, "##");
        sCAH = new StringIntegerValuedProperty("SCAH","SCAx high threshold", "0", 0, 8191, "#####");
        sCAL = new StringIntegerValuedProperty("SCAL","SCAx low theshold", "0", 0, 8191, "#####");
        sCAO = new StringIntegerValuedProperty("SCAO","SCAx output (SCA1-8 only)", "OFF", "HIGH","OFF","OF","HI","LOW","LO");
        sCAW = new StringIntegerValuedProperty("SCAW","SCA pulse width (not indexed - SCA1-8)","100", "100","1000");
		/*M*/sCOE = new StringIntegerValuedProperty("SCOE","Scope trigger edge", "RISING", "RISING","RI","FALLING","FA");
		/*M*/sCOT = new StringIntegerValuedProperty("SCOT","Scope trigger position", "87", "87","50","12","-25");
		/*M*/sCOG = new StringIntegerValuedProperty("SCOG","Digital scope gain", "1", "1","4","16");
		/*+*/sOFF = new StringFloatValuedProperty("SOFF","Set spectrum offset", "OFF", 8192f, 8191.75f, "##.####","OFF");
        sYNC = new StringIntegerValuedProperty("SYNC","List-mode sync source", "INT", "INT","IN","EXT","EX","FRAME","FR","NOTIMETAG","NO");
		/*-*/tHFA = new StringFloatValuedProperty("THFA","Fast threshold", "0", 0f, 255.937f, "###.####");
		/*+*/tHSL = new StringFloatValuedProperty("THSL","Slow threshold", "0", 0f, 24.9f, "###.####");
		/*+*/tLLD = new StringIntegerValuedProperty("TLLD","LLD threshold", "OFF", 0, 8191, "#####","OFF","OF");
		/*M*/tPMO = new StringIntegerValuedProperty("TPMO","Test pulser on/off",  "OFF","OFF","OF","+SNG","+S","-SNG","-S","+DBL","+D","-DBL","-D");
		/*M*/vOLU = new StringIntegerValuedProperty("VOLU","Volume [PX5 only]",  "OFF","OFF","OF","ON");

		/*C*/rESC = new StringIntegerValuedProperty("RESC","Reset Configuration", "NO", "Y","YES","NO");
		/*-*/cLCK = new StringIntegerValuedProperty("CLCK","20MHz/80MHz", "AUTO", "AUTO","AU","20","80");
		/*-*/tPEA = new StringFloatValuedProperty("TPEA","peaking time", "0.2", 0.2f, 102.4f, "###.####");
		/*G*/gAIF = new StringFloatValuedProperty("GAIF","Fine gain", "0", 0.5f, 1.9999f, "##.####");
		/*G*/gAIN = new StringFloatValuedProperty("GAIN","Total Gain (analog fine)", "0.75", 0.75f, 150f, "###.##");
		/*G*/rESL = new StringIntegerValuedProperty("RESL","Detector Reset lockout", "OFF", 0, 65535, "####","OFF","OF");
		/*-*/tFLA = new StringFloatValuedProperty("TFLA","Flat top width", "0", 0f, 51.2f, "##.####");
		/*-*/tPFA = new StringIntegerValuedProperty("TPFA","Fast channel peaking time","50", "50","100","200","400","1600");
		/*-*/rTDE = new StringIntegerValuedProperty("RTDE","RTD on/off",  "OFF","OFF","OF","ON");
		/*+*/mCAS = new StringIntegerValuedProperty("MCAS","MCA Source", "NORM", "NORM","NO","MCS","MC","FAST","FA","PUR","PU","RTD","RT");
		/*-*/rTDD = new StringIntegerValuedProperty("RTDD","Custom RTD oneshot delay", "1", 1, 127, "##");
		/*-*/rTDW = new StringIntegerValuedProperty("RTDW","Custom RTD oneshot width", "1", 1, 127, "##");

        switch (devicetype) {
            case DP5:
			/*G*/iNOF = new StringIntegerValuedProperty("INOF","Input offset", "DEF", -2047, 2047, "#","DEF","AUTO","AU","DE");
                pAPS = new StringIntegerValuedProperty("PAPS","preamp 8.5/5","OFF", "8.5","8","5","ON","OFF","OF");
			/*+*/pDMD = new StringIntegerValuedProperty("PDMD","Peak detect mode (min/max)", "NORM", (Integer)null, null, "NORM","MIN");
                pURE = new StringFloatValuedProperty("PURE","PUR interval, on/off", "OFF", 0f, 999.999f, "##.##","OFF","OF","ON");
                tECS = new StringIntegerValuedProperty("TECS","TEC set", "OFF", 0, 299, "#","OFF","OF");
                addPropertyies(aCKE,aINP,bLRD,bLRU,bLRM,aUO1,aUO2,bOOT,cLKL,cLCK,
                        cUSP,dACF,dACO,gAIA,gATE,gPED,gPGA,gPIN,gPMC,gPME,hVSE,iNOF,mCAC,mCAE,mCSL,mCSH,mCST,pAPS,pDMD,pRCL,pRCH,pREC,pRER,pRET,
                        rTDS,rTDT,sCAI,sCAH,sCAL,sCAO ,	sCAW,sCOE,sCOG,	sOFF,sYNC,tECS,tHFA,tHFA,tHSL,tHSL,tLLD,tPMO,sCOT,
                        rESC,cLCK,tPEA,
                        gAIF,gAIN,pURE,rESL,tFLA,tPFA,
                        rTDE,
                        mCAS,rTDD,rTDW);
                break;
            case DP5G:
			/*M*/cON1=new StringIntegerValuedProperty("CON1","Select Connector 1 signal","AUXIN1", "DAC","AUXOUT1","AUXIN1");
			/*M*/cON2 = new StringIntegerValuedProperty("CON2","Select Connector 2 signal","AUXIN1", "GATEH","AUXOUT2","AUXIN2","GATEL");
			/*+*/pDMD = new StringIntegerValuedProperty("PDMD","Peak detect mode (min/max)", "NORM", (Integer)null, null, "NORM","MIN");
                pURE = new StringFloatValuedProperty("PURE","PUR interval, on/off", "OFF", 0f, 999.999f, "##.##","OFF","OF","ON");
                addPropertyies(aCKE,aINP,bLRD,bLRU,bLRM,aUO1,aUO2,bOOT,cON1,cON2,cLKL,cLCK,
                        cUSP,dACF,dACO,gAIA,gATE,gPED,gPGA,gPIN,gPMC,gPME,hVSE,mCAC,mCAE,mCSL,mCSH,mCST,pDMD,pRCL,pRCH,pREC,pRER,pRET,
                        rTDS,rTDT,sCAI,sCAH,sCAL,sCAO ,	sCAW,sCOE,sCOG,	sOFF,sYNC,tHFA,tHFA,tHSL,tHSL,tLLD,tPMO,sCOT,
                        rESC,cLCK,tPEA,
                        gAIF,gAIN,pURE,rESL,tFLA,tPFA,
                        rTDE,
                        mCAS,rTDD,rTDW);
                break;
            case PX5:
			/*M*/cON1=new StringIntegerValuedProperty("CON1","Select Connector 1 signal","AUXIN1", "DAC","AUXOUT1","AUXIN1");
			/*M*/cON2 = new StringIntegerValuedProperty("CON2","Select Connector 2 signal", "AUXIN1", "GATEH","AUXOUT2","AUXIN2","GATEL");
			/*G*/iNOF = new StringIntegerValuedProperty("INOF","Input offset", "DEF", -2047, 2047, "#","DEF","AUTO","AU","DE");
			/*G*/iNOG = new StringIntegerValuedProperty("INOG","Input offset gain", "LOW", "HIGH","HI","LOW","LO");
                pAPS = new StringIntegerValuedProperty("PAPS","preamp 8.5/5", "OFF", "8.5","8","5","ON","OFF","OF");
			/*+*/pDMD = new StringIntegerValuedProperty("PDMD","Peak detect mode (min/max)", "NORM", (Integer)null, null, "NORM","MIN");
                tECS = new StringIntegerValuedProperty("TECS","TEC set", "OFF", 0, 299, "#","OFF","OF");
			/*G*/pAPZ = new StringFloatValuedProperty("PAPZ","Preamp pole-zero cancellation", "OFF", 34.5f, 4387f, "##.####","OFF");
                pURE = new StringFloatValuedProperty("PURE","PUR interval, on/off", "OFF", 0f, 999.999f, "##.##","OFF","OF","ON");
                addPropertyies(aCKE,aINP,bLRD,bLRU,bLRM,aUO1,aUO2,bOOT,cON1,cON2,cLKL,cLCK,
                        cUSP,dACF,dACO,gAIA,gATE,gPED,gPGA,gPIN,gPMC,gPME,hVSE,iNOF,iNOG,mCAC,mCAE,mCSL,mCSH,mCST,pAPS,pAPZ,pDMD,pRCL,pRCH,pREC,pRER,pRET,
                        rTDS,rTDT,sCAI,sCAH,sCAL,sCAO ,	sCAW,sCOE,sCOG,	sOFF,sYNC,tECS,tHFA,tHFA,tHSL,tHSL,tLLD,tPMO,vOLU,sCOT,
                        rESC,cLCK,tPEA,
                        gAIF,gAIN,pURE,rESL,tFLA,tPFA,
                        rTDE,
                        mCAS,rTDD,rTDW);
                break;
            case MCA8000D:
                pURE = new StringFloatValuedProperty("PURE","PUR Enable", "OFF", "OFF","OF","HIGT","HI","LOW","LO");
                addPropertyies(aCKE,aINP,bLRD,bLRU,bLRM,aUO1,aUO2,bOOT,cLKL,cLCK,
                        cUSP,dACF,dACO,gAIA,gATE,gPED,gPGA,gPIN,gPMC,gPME,hVSE,mCAC,mCAE,mCSL,mCSH,mCST,pRCL,pRCH,pREC,pRER,pRET,
                        rTDS,rTDT,sCAI,sCAH,sCAL,sCAO ,	sCAW,sCOE,sCOG,	sOFF,sYNC,tHFA,tHFA,tHSL,tHSL,tLLD,tPMO,sCOT,
                        rESC,cLCK,tPEA,
                        gAIF,gAIN,pURE,rESL,tFLA,tPFA,
                        rTDE,
                        mCAS,rTDD,rTDW);
                break;
            default:
                break;
        }

    }

    public StringIntegerValuedProperty getaCKE() {
        return aCKE;
    }

    public StringIntegerValuedProperty getaINP() {
        return aINP;
    }

    public StringIntegerValuedProperty getaUO1() {
        return aUO1;
    }

    public StringIntegerValuedProperty getaUO2() {
        return aUO2;
    }

    public StringIntegerValuedProperty getbLRD() {
        return bLRD;
    }

    public StringIntegerValuedProperty getbLRU() {
        return bLRU;
    }

    public StringIntegerValuedProperty getbLRM() {
        return bLRM;
    }

    public StringIntegerValuedProperty getbOOT() {
        return bOOT;
    }

    public StringIntegerValuedProperty getcON1() {
        return cON1;
    }

    public StringIntegerValuedProperty getcON2() {
        return cON2;
    }

    public StringIntegerValuedProperty getcLKL() {
        return cLKL;
    }

    public StringIntegerValuedProperty getcUSP() {
        return cUSP;
    }

    public StringIntegerValuedProperty getdACF() {
        return dACF;
    }

    public StringIntegerValuedProperty getdACO() {
        return dACO;
    }

    public StringIntegerValuedProperty getgAIA() {
        return gAIA;
    }

    public StringIntegerValuedProperty getgATE() {
        return gATE;
    }

    public StringIntegerValuedProperty getgPED() {
        return gPED;
    }

    public StringIntegerValuedProperty getgPGA() {
        return gPGA;
    }

    public StringIntegerValuedProperty getgPIN() {
        return gPIN;
    }

    public StringIntegerValuedProperty getgPMC() {
        return gPMC;
    }

    public StringIntegerValuedProperty getgPME() {
        return gPME;
    }

    public StringIntegerValuedProperty gethVSE() {
        return hVSE;
    }

    public StringIntegerValuedProperty getiNOF() {
        return iNOF;
    }

    public StringIntegerValuedProperty getiNOG() {
        return iNOG;
    }

    public StringIntegerValuedProperty getmCAC() {
        return mCAC;
    }

    public StringIntegerValuedProperty getmCAE() {
        return mCAE;
    }

    public StringIntegerValuedProperty getmCSL() {
        return mCSL;
    }

    public StringIntegerValuedProperty getmCSH() {
        return mCSH;
    }

    public StringFloatValuedProperty getmCST() {
        return mCST;
    }

    public StringIntegerValuedProperty getpAPS() {
        return pAPS;
    }

    public StringFloatValuedProperty getpAPZ() {
        return pAPZ;
    }

    public StringIntegerValuedProperty getpDMD() {
        return pDMD;
    }

    public StringIntegerValuedProperty getpRCL() {
        return pRCL;
    }

    public StringIntegerValuedProperty getpRCH() {
        return pRCH;
    }

    public StringLongValuedProperty getpREC() {
        return pREC;
    }

    public StringLongValuedProperty getpRER() {
        return pRER;
    }

    public StringFloatValuedProperty getpRET() {
        return pRET;
    }

    public StringIntegerValuedProperty getrESC() {
        return rESC;
    }

    public StringIntegerValuedProperty getcLCK() {
        return cLCK;
    }

    public StringFloatValuedProperty getgAIF() {
        return gAIF;
    }

    public StringFloatValuedProperty getgAIN() {
        return gAIN;
    }

    public StringFloatValuedProperty getpURE() {
        return pURE;
    }

    public StringIntegerValuedProperty getmCAS() {
        return mCAS;
    }

    public StringIntegerValuedProperty getrTDS() {
        return rTDS;
    }

    public StringFloatValuedProperty getrTDT() {
        return rTDT;
    }

    public StringIntegerValuedProperty getsCAI() {
        return sCAI;
    }

    public StringIntegerValuedProperty getsCAH() {
        return sCAH;
    }

    public StringIntegerValuedProperty getsCAL() {
        return sCAL;
    }

    public StringIntegerValuedProperty getsCAO() {
        return sCAO;
    }

    public StringIntegerValuedProperty getsCAW() {
        return sCAW;
    }

    public StringIntegerValuedProperty getsCOE() {
        return sCOE;
    }

    public StringIntegerValuedProperty getsCOG() {
        return sCOG;
    }

    public StringIntegerValuedProperty getsCOT() {
        return sCOT;
    }

    public StringFloatValuedProperty getsOFF() {
        return sOFF;
    }

    public StringIntegerValuedProperty getsYNC() {
        return sYNC;
    }

    public StringIntegerValuedProperty gettECS() {
        return tECS;
    }

    public StringFloatValuedProperty gettHFA() {
        return tHFA;
    }

    public StringFloatValuedProperty gettHSL() {
        return tHSL;
    }

    public StringIntegerValuedProperty gettLLD() {
        return tLLD;
    }

    public StringIntegerValuedProperty gettPMO() {
        return tPMO;
    }

    public StringIntegerValuedProperty getvOLU() {
        return vOLU;
    }

    public StringFloatValuedProperty gettPEA() {
        return tPEA;
    }

    public StringIntegerValuedProperty getrESL() {
        return rESL;
    }

    public StringFloatValuedProperty gettFLA() {
        return tFLA;
    }

    public StringIntegerValuedProperty gettPFA() {
        return tPFA;
    }

    public StringIntegerValuedProperty getrTDE() {
        return rTDE;
    }

    public StringIntegerValuedProperty getrTDD() {
        return rTDD;
    }

    public StringIntegerValuedProperty getrTDW() {
        return rTDW;
    }
}

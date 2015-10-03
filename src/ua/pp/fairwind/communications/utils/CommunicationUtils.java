package ua.pp.fairwind.communications.utils;


import ua.pp.fairwind.communications.utils.crc.CRC16;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Wind on 07.07.2014.
 */
public class CommunicationUtils {
    String lastError;

    static public int calculate_crc16_IBM(byte[] buffer, int pos, int len) {
        return CRC16.crc16table_IBM_mirror(buffer, pos, len);
    }

    static public int calculate_crc(byte[] buffer, int pos) {
        return calculate_crc16_IBM(buffer, pos, buffer.length);
    }

    static public void showStringList(List<String> list) {
        if (list == null) return;
        int i = 0;
        for (String str : list) {
            System.out.println(i + "\t" + str);
            i++;
        }
    }

    public static short getByteByteFromString(String strt, int pos) {
        if (pos + 1 < strt.length()) {
            byte k = getHalfByteFromChar(strt.charAt(pos));
            if (k == -1) return -1;
            byte res = (byte) (k << 4);
            k = getHalfByteFromChar(strt.charAt(pos + 1));
            if (k == -1) return -1;
            res += k;
            return res;
        } else {
            return -1;
        }
    }

    static public byte getHalfByteHex(long b) {
        byte val = (byte) (b & 0xF);
        if (val < 10) {
            return (byte) (val + '0');
        } else {
            return (byte) (val - 10 + 'A');
        }
    }

    static public byte getHalfByteFromChar(char c) {
        switch (c) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case 'A':
            case 'a':
                return 10;
            case 'B':
            case 'b':
                return 11;
            case 'C':
            case 'c':
                return 12;
            case 'D':
            case 'd':
                return 13;
            case 'E':
            case 'e':
                return 14;
            case 'F':
            case 'f':
                return 15;
            default:
                return -1;
        }
    }

    static public Byte getByteFromBuffer(byte[] buffer, int startPosition) {
        if (buffer == null) {
            return null;
        }
        if (buffer.length < startPosition) {
            return null;
        }
        return (byte) (buffer[startPosition] & 0xFF);
    }

    static public Short getWordFromBuffer(byte[] buffer, int startPosition) {
        if (buffer == null) {
            return null;
        }
        if (buffer.length < startPosition + 1) {
            return null;
        }
        int value = (getByteFromBuffer(buffer, startPosition) << 8 | (getByteFromBuffer(buffer, startPosition + 1) & 0xFF));
        return (short) (value & 0xFFFF);
    }

    static public Short getWordFromBufferIND(byte[] buffer, int startPosition) {
        if (buffer == null) {
            return null;
        }
        if (buffer.length < startPosition + 1) {
            return null;
        }
        int value = (getByteFromBuffer(buffer, startPosition + 1) << 8 | (getByteFromBuffer(buffer, startPosition) & 0xFF));
        return (short) (value & 0xFFFF);
    }

    static public Integer getDoubleWordFromBuffer(byte[] buffer, int startPosition) {
        if (buffer == null) {
            return null;
        }
        if (buffer.length < startPosition + 3) {
            return null;
        }
        long value = (getByteFromBuffer(buffer, startPosition + 3) << 24) & 0xFF000000;
        value |= (getByteFromBuffer(buffer, startPosition + 2) << 16) & 0xFF0000;
        value |= (getByteFromBuffer(buffer, startPosition + 1) << 8) & 0xFF00;
        value |= (getByteFromBuffer(buffer, startPosition)) & 0xFF;
        return (int) (value);
    }

    static public Long getLongFromBufferIND(byte[] buffer, int startPosition) {
        if (buffer == null) {
            return null;
        }
        if (buffer.length < startPosition + 3) {
            return null;
        }
        long value = (getByteFromBuffer(buffer, startPosition) << 24) & 0xFF000000;
        value |= (getByteFromBuffer(buffer, startPosition + 1) << 16) & 0xFF0000;
        value |= (getByteFromBuffer(buffer, startPosition + 2) << 8) & 0xFF00;
        value |= (getByteFromBuffer(buffer, startPosition + 3)) & 0xFF;
        return value;
    }

    static public Long getLongFromBuffer(byte[] buffer, int startPosition) {
        if (buffer == null) {
            return null;
        }
        if (buffer.length < startPosition + 3) {
            return null;
        }
        long value = ((getByteFromBuffer(buffer, startPosition + 3) << 24) | (getByteFromBuffer(buffer, startPosition + 2) << 16));
        value = (value | (getByteFromBuffer(buffer, startPosition + 1) << 8) | getByteFromBuffer(buffer, startPosition));
        return value;
    }

    static public Integer getDoubleWordFromBufferIND(byte[] buffer, int startPosition) {
        if (buffer == null) {
            return null;
        }
        if (buffer.length < startPosition + 3) {
            return null;
        }
        long value = (getByteFromBuffer(buffer, startPosition) << 24) & 0xFF000000;
        value |= (getByteFromBuffer(buffer, startPosition + 1) << 16) & 0xFF0000;
        value |= (getByteFromBuffer(buffer, startPosition + 2) << 8) & 0xFF00;
        value |= (getByteFromBuffer(buffer, startPosition + 3)) & 0xFF;
        return (int) (value);
    }

    static public Float getFloatFromBuffer(byte[] buffer, int startPosition) {
        Integer bits = getDoubleWordFromBuffer(buffer, startPosition);
        if (bits == null) {
            return null;
        } else {
            return Float.intBitsToFloat(bits);
        }
    }

    static public Float getFloatFromBufferIND(byte[] buffer, int startPosition) {
        Integer bits = getDoubleWordFromBufferIND(buffer, startPosition);
        if (bits == null) {
            return null;
        } else {
            return Float.intBitsToFloat(bits);
        }
    }

    static public Double getDoubleFromBuffer(byte[] buffer, int startPosition) {
        Long bits = getLongFromBuffer(buffer, startPosition);
        if (bits == null) {
            return null;
        } else {
            return Double.longBitsToDouble(bits);
        }
    }

    static public Double getDoubleFromBufferIND(byte[] buffer, int startPosition) {
        Long bits = getLongFromBufferIND(buffer, startPosition);
        if (bits == null) {
            return null;
        } else {
            return Double.longBitsToDouble(bits);
        }
    }

    static public byte getLowByte(int val) {
        return (byte) (val & 0xFF);
    }

    static public byte getHiByte(int val) {
        return (byte) ((val >> 8) & 0xFF);
    }

    static public int getLowWord(long val) {
        return (int) (val & 0xFFFF);
    }

    static public int getHiWord(long val) {
        return (int) ((val >> 16) & 0xFFFF);
    }

    static public void showByteHex(int val) {
        System.out.print(String.format("%02X", val));
    }

    static public byte[] convertDateToBytes(Date value) {
        byte[] res = new byte[2 + 2 + 2 + 2 + 2 + 2];
        int delta;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(value);
        delta = calendar.get(Calendar.SECOND);
        res[0] = getHiByte(delta);
        res[1] = getLowByte(delta);
        delta = calendar.get(Calendar.MINUTE);
        res[2] = getHiByte(delta);
        res[3] = getLowByte(delta);
        delta = calendar.get(Calendar.HOUR_OF_DAY);
        res[4] = getHiByte(delta);
        res[5] = getLowByte(delta);
        delta = calendar.get(Calendar.DAY_OF_MONTH);
        res[6] = getHiByte(delta);
        res[7] = getLowByte(delta);
        delta = calendar.get(Calendar.MONTH) + 1;
        res[8] = getHiByte(delta);
        res[9] = getLowByte(delta);
        delta = calendar.get(Calendar.YEAR);
        res[10] = getHiByte(delta);
        res[11] = getLowByte(delta);
        return res;
    }

    static public Date convertBytesToDate(byte[] value) {
        if (value.length != 12) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        int val = ((value[0] & 0xFF) << 8) | (value[1] & 0xFF);
        calendar.set(Calendar.SECOND, val);
        val = ((value[2] & 0xFF) << 8) | (value[3] & 0xFF);
        calendar.set(Calendar.MINUTE, val);
        val = ((value[4] & 0xFF) << 8) | (value[5] & 0xFF);
        calendar.set(Calendar.HOUR, val);
        val = ((value[6] & 0xFF) << 8) | (value[7] & 0xFF);
        calendar.set(Calendar.DAY_OF_MONTH, val);
        val = ((value[8] & 0xFF) << 8) | (value[9] & 0xFF);
        calendar.set(Calendar.MONTH, val - 1);
        val = ((value[10] & 0xFF) << 8) | (value[11] & 0xFF);
        calendar.set(Calendar.YEAR, val);
        return calendar.getTime();
    }

    static public byte[] convertWordToBytes(int value) {
        byte[] res = new byte[2];
        res[0] = getHiByte(value);
        res[1] = getLowByte(value);
        return res;
    }

    static public byte[] convertWordToBytesIND(int value) {
        byte[] res = new byte[2];
        res[1] = getHiByte(value);
        res[0] = getLowByte(value);
        return res;
    }

    static public byte[] convertDoubleWordToBytes(long value) {
        byte[] res = new byte[4];
        int i;
        i = getHiWord(value);
        res[3] = getHiByte(i);
        res[2] = getLowByte(i);
        i = getLowWord(value);
        res[1] = getHiByte(i);
        res[0] = getLowByte(i);
        return res;
    }

    static public byte[] convertDoubleWordToBytesIND(long value) {
        byte[] res = new byte[4];
        int i;
        i = getHiWord(value);
        res[0] = getHiByte(i);
        res[1] = getLowByte(i);
        i = getLowWord(value);
        res[2] = getHiByte(i);
        res[3] = getLowByte(i);
        return res;
    }

    static public byte[] convertLongToBytes(long value) {
        byte[] res = new byte[8];
        for (int i = 7; i >= 0; i--) {
            byte k = (byte) (value & 0xFF);
            value >>= 8;
            res[i] = k;
        }
        return res;
    }

    static public byte[] convertLongToBytesIND(long value) {
        byte[] res = new byte[8];
        for (int i = 0; i < 8; i++) {
            byte k = (byte) (value & 0xFF);
            value >>= 8;
            res[i] = k;
        }
        return res;
    }

    static public byte[] reverseBuffer(byte[] buf) {
        if (buf == null) return null;
        int len = buf.length;
        byte tmp[] = new byte[len];
        for (int i = 0; i < len; i++) {
            tmp[len - i - 1] = buf[i];
        }
        return tmp;
    }

    static public byte[] convertWordsToBytes(int[] value) {
        byte[] res = new byte[2 * value.length];
        for (int j = 0; j < value.length; j++) {
            res[(2 * j)] = getHiByte(value[j]);
            res[2 * j + 1] = getLowByte(value[j]);
        }
        return res;
    }

    static public byte[] convertDoubleWordsToBytes(long[] value) {
        byte[] res = new byte[4 * value.length];
        for (int j = 0; j < value.length; j++) {
            int i;
            i = getHiWord(value[j]);
            res[4 * j + 2] = getHiByte(i);
            res[4 * j + 3] = getLowByte(i);
            i = getLowWord(value[j]);
            res[(4 * j)] = getHiByte(i);
            res[4 * j + 1] = getLowByte(i);
        }
        return res;
    }

    static public byte[] convertFloatToBytes(float value) {
        return convertDoubleWordToBytes(Float.floatToIntBits(value));
    }

    static public byte[] convertFloatToBytesIND(float value) {
        return convertDoubleWordToBytesIND(Float.floatToIntBits(value));
    }

    static public byte[] convertDoubleToBytes(double value) {
        return convertDoubleWordToBytes(Double.doubleToLongBits(value));
    }

    static public byte[] convertDoubleToBytesIND(double value) {
        return convertDoubleWordToBytesIND(Double.doubleToLongBits(value));
    }

    static public byte[] convertFloatsToBytes(float[] value) {
        byte[] res = new byte[4 * value.length];
        for (int j = 0; j < value.length; j++) {
            //long value=(getByte(buffer,startPosition+2)<<24) |(getByte(buffer,startPosition+3)<<16) | (getByte(buffer,startPosition)<<8) | getByte(buffer,startPosition+1);
            int i;
            long k = Float.floatToIntBits(value[j]);
            i = getHiWord(k);
            res[4 * j + 2] = getHiByte(i);
            res[4 * j + 3] = getLowByte(i);
            i = getLowWord(k);
            res[(4 * j)] = getHiByte(i);
            res[4 * j + 1] = getLowByte(i);
        }
        return res;
    }

    static public void showBuffer(byte buf[], int len) {
        System.out.print("[");
        for (int i = 0; i < len; i++) {
            int k = (int) buf[i] & 0xFF;
            showByteHex(k);
            System.out.print(" ");
        }
        System.out.println("]");
    }

    static public String bufferToString(byte buf[]) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        if (buf != null) {
            for (byte aBuf : buf) {
                int k = (int) aBuf & 0xFF;
                builder.append(String.format("%02X", k));
                builder.append(" ");
            }
        }
        builder.append("]");
        return builder.toString();
    }

    static public void showBuffer(byte buf[]) {
        if (buf == null) {
            return;
        }
        showBuffer(buf, buf.length);
    }

    static public boolean[] convertFromByteToBool(int value) {
        int val = value & 0x0FF;
        boolean[] res = new boolean[8];
        int mask = 0x1;
        for (int i = 0; i < 8; i++) {
            res[i] = (val & mask) != 0;
            mask <<= 1;
        }
        return res;
    }

    static public boolean[] convertFromBytesToBool(byte[] value) {
        if (value == null) return null;
        boolean[] res = new boolean[value.length * 8];
        for (int j = 0; j < value.length; j++) {
            int mask = 0x1;
            for (int i = 0; i < 8; i++) {
                res[j * 8 + i] = (value[j] & mask) != 0;
                mask <<= 1;

            }
        }
        return res;
    }

    static public byte convertFromBoolToByte(boolean[] value) {
        byte res = 0;
        int len = value.length > 8 ? 8 : value.length;
        int mask = 0x1;
        for (int i = 7; i > 8 - len; i--) {
            if (value[i]) {
                res |= mask;
            }
            mask <<= 1;
        }
        return res;
    }

    static public byte[] convertFromBoolToBytes(boolean[] value) {
        int len = value.length / 8;
        if ((len % 8) > 0) len++;
        byte[] res = new byte[len];
        for (int j = 0; j < value.length; j++) {
            int i = j % 8;
            int k = j / 8;
            int mask = 0x1 << (i);
            if (value[j]) res[k] |= mask;
        }
        return res;
    }

    static public void showBool(boolean[] flags) {
        if (flags != null) {
            for (boolean flag : flags) {
                if (flag) {
                    System.out.print(1);
                } else {
                    System.out.print(0);
                }
            }
            System.out.println();
        }
    }

    static public void showWords(int[] val) {
        if (val != null) {
            for (int aVal : val) {
                System.out.print(aVal);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    static public void showHexWords(int[] val) {
        if (val != null) {
            for (int aVal : val) {
                System.out.print(String.format("%04X ", aVal));
            }
            System.out.println();
        }
    }

    static public void showDoubleWords(long[] val) {
        if (val != null) {
            for (long aVal : val) {
                System.out.print(aVal);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    static public void showHexDoubleWords(long[] val) {
        if (val != null) {
            for (long aVal : val) {
                System.out.print(String.format("%08X ", (aVal & 0xFFFFFFFFL)));
                /*
                int l=getHiWord(val[i]);
					int k=getHiByte(l);
						showByteHex(k);
					k=getLowByte(l);
						showByteHex(k);
				l=getLowWord(val[i]);
					k=getHiByte(l);
						showByteHex(k);
					k=getLowByte(l);
						showByteHex(k);
				System.out.print(" ");		*/
            }
            System.out.println();
        }
    }

    static public void showFloats(float[] val) {
        if (val != null) {
            for (float aVal : val) {
                System.out.print(aVal);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    static public void showHexFloats(float[] val) {
        if (val != null) {
            for (float aVal : val) {
                System.out.print(String.format("%08X ", Float.floatToIntBits(aVal)));
            }
            System.out.println();
        }
    }

    static public boolean[] setupFlags(int state, boolean[] flags, int pos, int length) {
        int mask = 1;
        if (flags != null && flags.length >= pos + length) {
            for (int i = pos; i < pos + length; i++) {
                flags[i] = (state & mask) > 0;
                mask = mask << 1;
            }
        }
        return flags;
    }

    static public String formStringFomByteBuffer(byte[] buffer, int pos, int length) {
        StringBuilder buf = new StringBuilder();
        if (buffer != null && length > 0 && pos >= 0 && pos + length < buffer.length) {
            for (int i = pos; i < pos + length; i++) {
                buf.append((char) buffer[i]);
            }
        }
        return buf.toString();
    }

    public static byte[] convertDoubleWordToBytesPID(long value) {
        byte[] res = new byte[4];
        int i;
        i = getHiWord(value);
        res[1] = getHiByte(i);
        res[0] = getLowByte(i);
        i = getLowWord(value);
        res[3] = getHiByte(i);
        res[2] = getLowByte(i);
        return res;
    }

    public static Integer getDoubleWordFromBufferPID(byte[] buffer, int pos) {
        if (buffer == null) {
            return null;
        }
        if (buffer.length < pos + 3) {
            return null;
        }
        long value = (getByteFromBuffer(buffer, pos + 1) << 24) & 0xFF000000;
        value |= (getByteFromBuffer(buffer, pos) << 16) & 0xFF0000;
        value |= (getByteFromBuffer(buffer, pos + 3) << 8) & 0xFF00;
        value |= (getByteFromBuffer(buffer, pos + 2)) & 0xFF;
        return (int) (value);
    }

    public static byte[] convertDoubleToBytesPID(Double value) {
        return convertDoubleWordToBytesPID(Double.doubleToLongBits(value));
    }

    public static Long getLongFromBufferPID(byte[] buffer, int pos) {
        if (buffer == null) {
            return null;
        }
        if (buffer.length < pos + 8) {
            return null;
        }
        long value = 0;
        value |= (((long) buffer[0]) << 48);
        value |= (((long) buffer[1]) << 56);
        value |= (((long) buffer[2]) << 32);
        value |= (((long) buffer[3]) << 40);
        value |= (((long) buffer[4]) << 16);
        value |= (((long) buffer[5]) << 24);
        value |= (((long) buffer[6]));
        value |= (((long) buffer[7]) << 8);
        return value;
    }

    public static byte[] convertLongToBytesPID(Long value) {
        byte[] res = new byte[8];
        res[0] = (byte) ((value >> 48) & 0xFF);
        res[1] = (byte) ((value >> 56) & 0xFF);
        res[2] = (byte) ((value >> 32) & 0xFF);
        res[3] = (byte) ((value >> 40) & 0xFF);
        res[4] = (byte) ((value >> 16) & 0xFF);
        res[5] = (byte) ((value >> 24) & 0xFF);
        res[6] = (byte) ((value) & 0xFF);
        res[7] = (byte) ((value >> 8) & 0xFF);
        return res;
    }

    public static Double getDoubleFromBufferPID(byte[] buffer, int pos) {
        Long bits = getLongFromBufferPID(buffer, pos);
        if (bits == null) {
            return null;
        } else {
            return Double.longBitsToDouble(bits);
        }
    }

    public static Float getFloatFromBufferPID(byte[] buffer, int pos) {
        Integer bits = getDoubleWordFromBufferPID(buffer, pos);
        if (bits == null) {
            return null;
        } else {
            return Float.intBitsToFloat(bits);
        }
    }

    public static byte[] convertFloatToBytesPID(Float value) {
        return convertDoubleWordToBytesPID(Float.floatToIntBits(value));
    }

    public static void RealThreadPause(long pause_milis) {
        boolean pause_finished = true;
        do {
            long neddedTime = System.currentTimeMillis() + Math.abs(pause_milis);
            try {
                Thread.sleep(pause_milis);
            } catch (InterruptedException ex) {
                long curent_time = System.currentTimeMillis();
                if (curent_time < neddedTime) {
                    pause_finished = false;
                    pause_milis = neddedTime - curent_time;
                }
            }
        } while (!pause_finished);

    }

    public static byte[] hexStringToByteArray(String str) {
        if (str == null || str.length() == 0) return null;
        int len = str.length();
        byte[] buffer = new byte[len / 2 + ((len % 2 > 0) ? 1 : 0)];
        for (int i = 0; i < len; i += 2) {
            if (i + 1 < len) {
                buffer[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + (Character.digit(str.charAt(i + 1), 16)));
            } else {
                buffer[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4));
            }
        }
        return buffer;
    }

    public static byte[] cobineArrays(byte[] src, byte[] dst) {
        if (dst == null) return src;
        if (src == null) return dst;
        byte[] all = new byte[src.length + dst.length];
        // copy first half
        System.arraycopy(src, 0, all, 0, src.length);
        // copy second half
        System.arraycopy(dst, 0, all, src.length, dst.length);
        return all;
    }

    public String getLastError() {
        return lastError;
    }

    public int getByte(byte[] buffer, int startPosition) {
        if (buffer == null) {
            lastError = "EMPTY BUFFER!";
            return -1;
        }
        if (buffer.length < startPosition) {
            lastError = "INCORRECT POSITION";
            return -1;
        }
        return (buffer[startPosition] & 0xFF);
    }

    public int getWord(byte[] buffer, int startPosition) {
        if (buffer == null) {
            lastError = "EMPTY BUFFER!";
            return -1;
        }
        if (buffer.length < startPosition + 1) {
            lastError = "INCORRECT POSITION";
            return -1;
        }
        int value = getByte(buffer, startPosition) << 8 | getByte(buffer, startPosition + 1);
        return (value & 0xFFFF);
    }

    public long getDoubleWord(byte[] buffer, int startPosition) {
        if (buffer == null) {
            lastError = "EMPTY BUFFER!";
            return -1;
        }
        if (buffer.length < startPosition + 3) {
            lastError = "INCORRECT POSITION";
            return -1;
        }
        long value = ((getByteFromBuffer(buffer, startPosition + 2) << 24) | (getByteFromBuffer(buffer, startPosition + 3) << 16));
        value = (value | (getByteFromBuffer(buffer, startPosition) << 8) | getByteFromBuffer(buffer, startPosition + 1));
        return (int) (value);
    }

    public float getFloat(byte[] buffer, int startPosition) {
        long bits = getDoubleWord(buffer, startPosition);
        if (bits == -1) {
            return Float.NaN;
        } else {
            return Float.intBitsToFloat((int) bits);
        }
    }

    public int[] getWords(byte[] buffer) {
        int[] res;
        if (buffer == null) {
            lastError = "EMPTY BUFFER!";
            return null;
        }
        if ((buffer.length % 2) != 0) {
            lastError = "INCORRECT COUNT OF ELEMENTS";
            return null;
        }
        int len = buffer.length / 2;
        res = new int[len];
        for (int i = 0; i < len; i++) {
            res[i] = getWord(buffer, i * 2);
        }
        return res;
    }

    public long[] getDoubleWords(byte[] buffer) {
        long[] res;
        if (buffer == null) {
            lastError = "EMPTY BUFFER!";
            return null;
        }
        if ((buffer.length % 2) != 0) {
            lastError = "INCORRECT COUNT OF ELEMENTS";
            return null;
        }
        int len = buffer.length / 4;
        res = new long[len];
        for (int i = 0; i < len; i++) {
            res[i] = getDoubleWord(buffer, i * 4);
        }
        return res;
    }

    public float[] getFloats(byte[] buffer) {
        float[] res;
        if (buffer == null) {
            lastError = "EMPTY BUFFER!";
            return null;
        }
        if ((buffer.length % 2) != 0) {
            lastError = "INCORRECT COUNT OF ELEMENTS";
            return null;
        }
        int len = buffer.length / 4;
        res = new float[len];
        for (int i = 0; i < len; i++) {
            res[i] = getFloat(buffer, i * 4);
        }
        return res;
    }

}

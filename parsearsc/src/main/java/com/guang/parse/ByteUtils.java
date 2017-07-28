package com.guang.parse;

import com.guang.parse.type.ResPackageChunk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by wangguang.
 * Date:2017/6/29
 * Description:
 */

public class ByteUtils {

    public static int LONG_BYTE_COUNT = 8;

    public static int INT_BYTE_COUNT = 4;

    public static int SHORT_BYTE_COUNT = 2;

    public static int BYTE_COUNT = 1;

    public static int byte2Int(byte[] data, int start) {
        byte[] intbyte = Arrays.copyOfRange(data, start, start + INT_BYTE_COUNT);
        long i1 = intbyte[0] & 0x000000FF;
        long i2 = OneByte2Long(intbyte[1], 8);
        long i3 = OneByte2Long(intbyte[2], 16);
        long i4 = OneByte2Long(intbyte[3], 24);
        return (int) (i1 + i2 + i3 + i4);
    }

    public static short byte2Short(byte[] data, int start) {
        byte[] intbyte = Arrays.copyOfRange(data, start, start + INT_BYTE_COUNT);
        long i1 = intbyte[0];
        long i2 = OneByte2Long(intbyte[1], 8);
        return (short) (i1 + i2);
    }

    public static long byte2SLong(byte[] data, int start) {
        byte[] intbyte = Arrays.copyOfRange(data, start, start + INT_BYTE_COUNT);
        long i1 = intbyte[0] & 0x000000FF;
        long i2 = OneByte2Long(intbyte[1], 8);
        long i3 = OneByte2Long(intbyte[2], 16);
        long i4 = OneByte2Long(intbyte[3], 24);
        long i5 = OneByte2Long(intbyte[4], 32);
        long i6 = OneByte2Long(intbyte[5], 40);
        long i7 = OneByte2Long(intbyte[6], 48);
        long i8 = OneByte2Long(intbyte[7], 56);
        return i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8;
    }

    public static long OneByte2Long(byte i, int moveBit) {
        return (((long) i) & 0x000000FF) << moveBit;
    }

    public static byte[] toByte(int i) {
        long a = Integer.toUnsignedLong(i);
        byte[] origin = toByte(a);
        return Arrays.copyOfRange(origin, 0, Integer.BYTES);
    }

    public static byte[] toByte(short i) {
        long a = Integer.toUnsignedLong(i);
        byte[] origin = toByte(a);
        return Arrays.copyOfRange(origin, 0, Short.BYTES);

    }

    public static byte[] toByte(String i, int count) throws IOException {
        byte[] origin = i.getBytes("utf-8");
        if (count <= origin.length) {
            return origin;
        } else {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(origin);
            byte[] fill0 = new byte[count - origin.length];
            Arrays.fill(fill0, (byte) 0);
            outputStream.write(fill0);
            byte[] result = outputStream.toByteArray();
            outputStream.close();
            return result;
        }

    }

    public static byte[] toByte(String i) throws IOException {
        return toByte(i, i.length() + 1);

    }

    public static byte[] toByte(long i) {
        byte[] orgin = new byte[Long.BYTES];
        orgin[0] = (byte) (i & 0xFF);
        orgin[1] = (byte) ((i & 0xFF00) >>> 8);
        orgin[2] = (byte) ((i & 0xFF0000) >>> 16);
        orgin[3] = (byte) ((i & 0xFF000000) >>> 24);
        orgin[4] = (byte) ((i & Long.decode("0xFF00000000")) >>> 32);
        orgin[5] = (byte) ((i & Long.decode("0xFF0000000000")) >>> 40);
        orgin[6] = (byte) ((i & Long.decode("0xFF000000000000")) >>> 48);
        orgin[7] = (byte) (i >>> 56);
        return orgin;
    }
}

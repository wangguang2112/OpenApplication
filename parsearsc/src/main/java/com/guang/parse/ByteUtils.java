package com.guang.parse;

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
}

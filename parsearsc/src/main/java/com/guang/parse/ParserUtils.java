package com.guang.parse;

import com.guang.parse.type.ResChunkHeader;
import com.guang.parse.type.ResPackageChunk;
import com.guang.parse.type.ResPackageHeader;
import com.guang.parse.type.ResString;
import com.guang.parse.type.ResStringPoolChunk;
import com.guang.parse.type.ResStringPoolHeader;
import com.guang.parse.type.ResStringPoolRef;
import com.guang.parse.type.ResTableHeader;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by wangguang.
 * Date:2017/6/29
 * Description:
 */

public class ParserUtils {

    public static int LONG_BYTE_COUNT = 8;

    public static int INT_BYTE_COUNT = 4;

    public static int SHORT_BYTE_COUNT = 2;

    public static int BYTE_COUNT = 1;

    /**
     * 读取文件
     */

    public static byte[] readByte(File file) {
        long size = file.length();
        byte[] srcByte = null;
        int percent = 0;
        int lastPercent = 0;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BufferedInputStream bis = new BufferedInputStream(fis);
            int len = 0;
            int alllen = 0;
            byte[] tempData = new byte[1024];
            while ((len = bis.read(tempData, 0, 1024)) != -1) {
                outputStream.write(tempData, 0, len);
                alllen += len;
                percent = (int) (alllen * 100 / size);
                if (lastPercent != percent) {
                    System.out.print("read arsc ..." + percent + "%\r");
                    lastPercent = percent;
                }
            }
            srcByte = outputStream.toByteArray();
            System.out.println("read arsc ...100%");
            outputStream.close();
            bis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return srcByte;
    }

    public static ResTableHeader parseResTableHeaderChunk(byte[] data, int offset) {
        ResTableHeader header = new ResTableHeader();
        header.header = parseResChunkHeader(data, offset + 0);
        header.packageCount = byte2Int(data, header.header.getHeaderSize() + offset + 0);
        return header;
    }

    public static ResStringPoolChunk parseStringPoolChunk(byte[] data, int offset) {
        ResStringPoolChunk chunk = new ResStringPoolChunk();
        chunk.header = parseStringPoolHeader(data, offset + 0);
        chunk.stringIndexAry = parseStringPoolRef(data, offset + chunk.header.getHeaderSize(), chunk.header.stringCount);
        chunk.styleIndexAry = parseStringPoolRef(data, offset + chunk.header.stringCount * INT_BYTE_COUNT + chunk.header.getHeaderSize(), chunk.header.styleCount);
        chunk.strings = parseStringPoolWithIndex(data, offset + chunk.header.stringsStart, chunk.header.stringCount, chunk.stringIndexAry);
        chunk.styles = parseStringPoolWithIndex(data, offset + chunk.header.styleStart, chunk.header.styleCount, chunk.styleIndexAry);
        return chunk;
    }

    public static ResStringPoolHeader parseStringPoolHeader(byte[] data, int offset) {
        ResStringPoolHeader header = new ResStringPoolHeader();
        header.header = parseResChunkHeader(data, offset + 0);
        header.stringCount = byte2Int(data, offset + header.header.getHeaderSize());
        header.styleCount = byte2Int(data, offset + header.header.getHeaderSize() + INT_BYTE_COUNT);
        header.flags = byte2Int(data, offset + header.header.getHeaderSize() + INT_BYTE_COUNT + INT_BYTE_COUNT);
        header.stringsStart = byte2Int(data, offset + header.header.getHeaderSize() + INT_BYTE_COUNT + INT_BYTE_COUNT + INT_BYTE_COUNT);
        header.styleStart = byte2Int(data, offset + header.header.getHeaderSize() + INT_BYTE_COUNT + INT_BYTE_COUNT + INT_BYTE_COUNT + INT_BYTE_COUNT);
        return header;
    }

    public static ResPackageChunk parsePackageChunk(byte[] data, int offset){
        ResPackageChunk packageChunk=new ResPackageChunk();
        packageChunk.header=parsePackageHeader(data,offset);
        //TODO parse String
//        packageChunk.resTypeStrings=parseStringPool(data,offset+packageChunk.header.getHeaderSize());
        return packageChunk;
    }
    public static ResPackageHeader parsePackageHeader(byte[] data, int offset){
        ResPackageHeader header=new ResPackageHeader();

        return header;
    }




    public static ResStringPoolRef[] parseStringPoolRef(byte[] data, int start, int count) {
        ResStringPoolRef[] refs = new ResStringPoolRef[count];
        for (int i = 0; i < count; i++) {
            refs[i] = new ResStringPoolRef();
            refs[i].index = byte2Int(data, start + i * INT_BYTE_COUNT);
        }
        return refs;
    }

    public static ResString[] parseStringPoolWithIndex(byte[] data, int start, int count, ResStringPoolRef[] refs) {
        ResString[] strs = new ResString[count];
        for (int i = 0; i < count; i++) {
            strs[i] = parseResString(data, start + refs[i].index);
        }
        return strs;
    }

    public static ResString parseResString(byte[] data, int start) {
        ResString string = new ResString();
        string.length = (int) OneByte2Long(data[start + 1], 0);
        byte[] stringByte = Arrays.copyOfRange(data, start + 2, start + 2 + string.length);
        try {
            string.str = new String(stringByte, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return string;
    }

    public static ResChunkHeader parseResChunkHeader(byte[] data, int offset) {
        ResChunkHeader header = new ResChunkHeader();
        header.type = byte2Short(data, offset);
        header.headerSize = byte2Short(data, offset + SHORT_BYTE_COUNT);
        header.size = byte2Int(data, offset + SHORT_BYTE_COUNT + SHORT_BYTE_COUNT);
        return header;
    }


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

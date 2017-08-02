package com.guang.parse.type;

import com.guang.parse.ByteUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by wangguang.
 * Date:2017/6/27
 * Description:
 */

public class ResStringPoolHeader implements IChunkHeader {

    public final static int SORTED_FLAG = 1;

    public final static int UTF8_FLAG = (1 << 8);

    public ResChunkHeader header = new ResChunkHeader();

    public int stringCount;

    public int styleCount;

    public int flags;

    public int stringsStart;

    public int styleStart;


    @Override
    public int getHeaderSize() {
        return header.getHeaderSize() + 4 + 4 + 4 + 4 + 4;
    }

    @Override
    public String toString() {
        return "ResStringPoolHeader{" +
                "header=" + header +
                ", stringCount=" + stringCount +
                ", styleCount=" + styleCount +
                ", flags=" + flags +
                ", stringsStart=" + stringsStart +
                ", styleStart=" + styleStart +
                '}';
    }

    @Override
    public byte[] toByte() throws IOException {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        outputStream.write(header.toByte());
        outputStream.write(ByteUtils.toByte(stringCount));
        outputStream.write(ByteUtils.toByte(styleCount));
        outputStream.write(ByteUtils.toByte(flags));
        outputStream.write(ByteUtils.toByte(stringsStart));
        outputStream.write(ByteUtils.toByte(styleStart));
        byte[] result= outputStream.toByteArray();
        outputStream.close();
        return result;
    }
}

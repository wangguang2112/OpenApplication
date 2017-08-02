package com.guang.parse.type;

import com.guang.parse.ByteUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by wangguang.
 * Date:2017/6/28
 * Description:
 */

public class ResTypeSpecHeader implements IChunkHeader {

    public final static int SPEC_PUBLIC = 0x40000000;

    public ResChunkHeader header = new ResChunkHeader();

    public byte id;

    public byte res0;

    public short res1;

    public int entryCount;

    @Override
    public int getHeaderSize() {
        return header.getHeaderSize()+1+1+2+4;
    }

    @Override
    public String toString() {
        return "ResTypeSpecHeader{" +
                "header=" + header +
                ", id=" + id +
                ", res0=" + res0 +
                ", res1=" + res1 +
                ", entryCount=" + entryCount +
                '}';
    }

    @Override
    public byte[] toByte() throws IOException {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        outputStream.write(header.toByte());
        outputStream.write(id);
        outputStream.write(res0);
        outputStream.write(ByteUtils.toByte(res1));
        outputStream.write(ByteUtils.toByte(entryCount));
        byte[] result= outputStream.toByteArray();
        outputStream.close();
        return result;
    }
}

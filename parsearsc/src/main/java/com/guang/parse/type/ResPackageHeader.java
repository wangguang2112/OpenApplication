package com.guang.parse.type;

import com.guang.parse.ByteUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by wangguang.
 * Date:2017/6/28
 * Description:
 */

public class ResPackageHeader implements IChunkHeader {

    public ResChunkHeader header=new ResChunkHeader();

    /**
     * 0x7F
     */
    public int id;

    /**
     * 256位 16行
     */
    public String name;

    public int typeStrings;

    public int lastPublicType;

    public int keyStrings;

    public int lastPublicKey;

    public byte[] orginByte;

    @Override
    public String toString() {
        return "ResPackageHeader{" +
                "header=" + header +
                ", id=" + id +
                ", name=" + name +
                ", typeStrings=" + typeStrings +
                ", lastPublicType=" + lastPublicType +
                ", keyStrings=" + keyStrings +
                ", lastPublicKey=" + lastPublicKey +
                '}';
    }

    @Override
    public int getHeaderSize() {
        return header.getHeaderSize()+4+128*2+4+4+4+4;
    }

    @Override
    public byte[] toByte() throws IOException {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        outputStream.write(header.toByte());
        outputStream.write(ByteUtils.toByte(id));
        outputStream.write(ByteUtils.toByte(name,32));
        outputStream.write(ByteUtils.toByte(typeStrings));
        outputStream.write(ByteUtils.toByte(lastPublicType));
        outputStream.write(ByteUtils.toByte(keyStrings));
        outputStream.write(ByteUtils.toByte(lastPublicKey));
        byte[] result= outputStream.toByteArray();
        outputStream.close();
        return result;
    }
}

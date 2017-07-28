package com.guang.parse.type;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by wangguang.
 * Date:2017/6/28
 * Description:
 */

public class ResPackageChunk implements IChunk {

    public ResPackageHeader header = new ResPackageHeader();

    public ResStringPoolChunk resTypeStrings;

    public ResStringPoolChunk resNameStrings;

    public ResTypeChunk[] typeChunk;

    @Override
    public byte[] toByte() throws IOException {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        outputStream.write(header.toByte());
        outputStream.write(resTypeStrings.toByte());
        outputStream.write(resNameStrings.toByte());
        for(ResTypeChunk chunk:typeChunk) {
            outputStream.write(chunk.toByte());
        }
        byte[] result= outputStream.toByteArray();
        outputStream.close();
        return result;
    }
}

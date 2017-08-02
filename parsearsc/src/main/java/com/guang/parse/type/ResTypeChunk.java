package com.guang.parse.type;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by wangguang.
 * Date:2017/7/4
 * Description:
 */

public class ResTypeChunk implements IChunk {
    public ResTypeSpecChunk specChunk;
    public ResTableTypeChunk[] typeChunks;

    @Override
    public byte[] toByte() throws IOException {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        outputStream.write(specChunk.toByte());
        for(ResTableTypeChunk chunk:typeChunks){
            outputStream.write(chunk.toByte());
        }
        byte[] result= outputStream.toByteArray();
        outputStream.close();
        return result;
    }
}

package com.guang.parse.type;

import com.guang.parse.ByteUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by wangguang.
 * Date:2017/6/28
 * Description:
 */

public class ResTableTypeChunk implements IChunk {

    public ResTableTypeHeader header;
    public int[] typeIndexArray;
    public ResTableEntry[] tableEntry;

    @Override
    public byte[] toByte() throws IOException {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        outputStream.write(header.toByte());
        for(int i:typeIndexArray){
            outputStream.write(ByteUtils.toByte(i));
        }
        for(ResTableEntry entry:tableEntry){
            outputStream.write(entry.toByte());
        }
        byte[] result= outputStream.toByteArray();
        outputStream.close();
        return result;
    }
}

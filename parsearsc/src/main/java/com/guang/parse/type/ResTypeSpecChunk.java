package com.guang.parse.type;

import com.guang.parse.ByteUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by wangguang.
 * Date:2017/6/28
 * Description:
 */

public class ResTypeSpecChunk implements IChunk {

    public ResTypeSpecHeader header;
    //描述差异性 不是index
    public int[] specArray;

    @Override
    public byte[] toByte() throws IOException {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        outputStream.write(header.toByte());
        for(int item:specArray) {
            outputStream.write(ByteUtils.toByte(item));
        }
        byte[] result= outputStream.toByteArray();
        outputStream.close();
        return result;
    }
}

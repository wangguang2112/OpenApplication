package com.guang.parse.type;

import java.io.IOException;

/**
 * Created by wangguang.
 * Date:2017/7/4
 * Description:
 */

public class ResTypeChunk implements IChunk {
    public ResTypeSpecChunk specChunk;
    public ResTableTypeChunk[] typeChunks;

    //TODO
    @Override
    public byte[] toByte() throws IOException {
        return new byte[0];
    }
}

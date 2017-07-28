package com.guang.parse.type;

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

    //TODO
    @Override
    public byte[] toByte() throws IOException {
        return new byte[0];
    }
}

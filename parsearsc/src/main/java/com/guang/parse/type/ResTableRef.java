package com.guang.parse.type;

import com.guang.parse.ByteUtils;

import java.io.IOException;

/**
 * Created by wangguang.
 * Date:2017/6/28
 * Description:
 */
public class ResTableRef implements IChunkBody{

    public int index;
    @Override
    public int getSize() {
        return 4;
    }

    @Override
    public String toString() {
        return "ResTableRef{" +
                "index=" + index +
                '}';
    }

    @Override
    public byte[] toByte() throws IOException {
        return ByteUtils.toByte(index);
    }
}

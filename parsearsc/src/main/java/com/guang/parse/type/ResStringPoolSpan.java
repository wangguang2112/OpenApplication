package com.guang.parse.type;

import java.io.IOException;

/**
 * Created by wangguang.
 * Date:2017/6/27
 * Description:
 */

public class ResStringPoolSpan implements IChunkBody {


    public ResStringPoolRef name;

    public int firstChar;

    public int lastChar;

    @Override
    public String toString() {
        return "ResStringPoolSpan{" +
                "name=" + name +
                ", firstChar=" + firstChar +
                ", lastChar=" + lastChar +
                '}';
    }

    @Override
    public int getSize() {
        return name.getSize()+4+4;
    }

    //TODO
    @Override
    public byte[] toByte() throws IOException {
        return new byte[0];
    }
}

package com.guang.parse.type;

import java.io.IOException;

/**
 * Created by wangguang.
 * Date:2017/6/28
 * Description:
 */

public class ResTableMap implements IChunkBody {

    public ResTableRef name;
    public ResValue value;

    @Override
    public int getSize() {
        return name.getSize()+value.getSize();
    }

    @Override
    public String toString() {
        return "ResTableMap{" +
                "name=" + name +
                ", value=" + value +
                '}';
    }

    //TODO
    @Override
    public byte[] toByte() throws IOException {
        return new byte[0];
    }
}

package com.guang.parse.type;

/**
 * Created by wangguang.
 * Date:2017/6/27
 * Description:
 */

public class ResStringPoolRef implements IChunkBody {

    public int index;

    @Override
    public String toString() {
        return "ResStringPoolRef{" +
                "index=" + index +
                '}';
    }

    @Override
    public int getSize() {
        return 4;
    }
}

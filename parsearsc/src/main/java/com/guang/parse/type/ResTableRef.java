package com.guang.parse.type;

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
}

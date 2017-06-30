package com.guang.parse.type;

/**
 * Created by wangguang.
 * Date:2017/6/28
 * Description:TODO
 */

public class ResTableConfig implements IChunkHeader {
    int size;
    short mnc;

    @Override
    public int getHeaderSize() {
        return 0;
    }
//   ...
}

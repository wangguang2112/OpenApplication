package com.guang.parse.type;

/**
 * Created by wangguang.
 * Date:2017/6/28
 * Description:
 */

public class ResTableTypeChunk implements IChunk {

    public ResTableTypeHeader header;
    public int[] typeIndexArray;
    public ResTableEntry[] tableEntry;
}

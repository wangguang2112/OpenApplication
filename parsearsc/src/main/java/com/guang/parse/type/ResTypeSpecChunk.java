package com.guang.parse.type;

/**
 * Created by wangguang.
 * Date:2017/6/28
 * Description:
 */

public class ResTypeSpecChunk implements IChunk {

    public ResTypeSpecHeader header;
    //描述差异性 不是index
    public int[] specArray;
}

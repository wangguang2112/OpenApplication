package com.guang.parse.type;

import java.util.Arrays;

/**
 * Created by wangguang.
 * Date:2017/6/27
 * Description:
 */

public class ResStringPoolChunk implements IChunk {

    public ResStringPoolHeader header=new ResStringPoolHeader();

    public ResStringPoolRef[] stringIndexAry;

    public ResStringPoolRef[] styleIndexAry;

    public ResString[] strings;
    public ResString[] styles;

    @Override
    public String toString() {
        return "ResStringPoolChunk{" +
                "header=" + header +
                '}';
    }
}

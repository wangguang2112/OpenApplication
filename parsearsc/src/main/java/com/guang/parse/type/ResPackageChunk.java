package com.guang.parse.type;

/**
 * Created by wangguang.
 * Date:2017/6/28
 * Description:
 */

public class ResPackageChunk implements IChunk {

    public ResPackageHeader header = new ResPackageHeader();

    public ResStringPoolChunk resTypeStrings;

    public ResStringPoolChunk resNameStrings;

    public ResTypeChunk[] typeChunk;

}

package com.guang.parse.type;

/**
 * Created by wangguang.
 * Date:2017/7/4
 * Description:
 */

public class RescFile implements IChunk {
    public ResTableHeader header;
    public ResStringPoolChunk wholeStringChunk;
    public ResPackageChunk[] mChunks;
}

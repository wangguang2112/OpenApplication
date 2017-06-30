package com.guang.parse.type;

/**
 * Created by wangguang.
 * Date:2017/6/27
 * Description:
 */

public interface IChunkHeader extends IChunk {
    /**
     * 获取头部大小
     */
    int getHeaderSize();
}

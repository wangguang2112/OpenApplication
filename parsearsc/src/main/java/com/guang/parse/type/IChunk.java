package com.guang.parse.type;

import java.io.IOException;

/**
 * Created by wangguang.
 * Date:2017/6/27
 * Description:
 */

public interface IChunk {
    byte[] toByte() throws IOException;
}

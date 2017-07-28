package com.guang.parse.type;

import java.io.IOException;

/**
 * Created by wangguang.
 * Date:2017/6/30
 * Description:
 */

public class ResString implements IChunkBody {

    public int length;
    public String str;

    @Override
    public int getSize() {
        if(str!=null) {
            return length * 4 + str.length();
        }else{
            return 0;
        }
    }

    @Override
    public String toString() {
        return str.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return str.equals(obj);
    }

    //TODO
    @Override
    public byte[] toByte() throws IOException {
        return new byte[0];
    }
}

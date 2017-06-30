package com.guang.parse.type;

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
}

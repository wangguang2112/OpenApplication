package com.guang.parse.type;

/**
 * Created by wangguang.
 * Date:2017/6/28
 * Description:
 */

public class ResTableEntry implements IChunkBody {

    public final static int FLAG_COMPLEX = 0x0001;
    public final static int FLAG_PUBLIC = 0x0002;


    public short size;

    public short flags;

    public ResStringPoolRef ref = new ResStringPoolRef();

    @Override
    public int getSize() {
        return 2 + 2 + ref.getSize();
    }

    @Override
    public String toString() {
        return "ResTableEntry{" +
                "size=" + size +
                ", flags=" + flags +
                ", ref=" + ref +
                '}';
    }
}

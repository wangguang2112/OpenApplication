package com.guang.parse.type;

/**
 * Created by wangguang.
 * Date:2017/6/28
 * Description:
 */

public class ResTableTypeSpec implements IChunkHeader {

    public final static int SPEC_PUBLIC = 0x40000000;

    public ResChunkHeader header = new ResChunkHeader();

    byte id;

    byte res0;

    short res1;

    int entryCount;

    @Override
    public int getHeaderSize() {
        return header.getHeaderSize()+1+1+2+4;
    }

    @Override
    public String toString() {
        return "ResTableTypeSpec{" +
                "header=" + header +
                ", id=" + id +
                ", res0=" + res0 +
                ", res1=" + res1 +
                ", entryCount=" + entryCount +
                '}';
    }
}

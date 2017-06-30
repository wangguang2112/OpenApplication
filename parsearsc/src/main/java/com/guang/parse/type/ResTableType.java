package com.guang.parse.type;

/**
 * Created by wangguang.
 * Date:2017/6/28
 * Description:
 */

public class ResTableType implements IChunkHeader {

    public final static int NO_ENTRY = 0xFFFFFFFF;

    public ResChunkHeader mResChunk_header = new ResChunkHeader();


    public byte id;

    public byte res0;

    public short res1;

    public int entryCount;

    public int entriesStart;

    public ResTableConfig resConfig;

    @Override
    public int getHeaderSize() {
        return 0;
    }

    @Override
    public String toString() {
        return "ResTableType{" +
                "mResChunk_header=" + mResChunk_header +
                ", id=" + id +
                ", res0=" + res0 +
                ", res1=" + res1 +
                ", entryCount=" + entryCount +
                ", entriesStart=" + entriesStart +
                ", resConfig=" + resConfig +
                '}';
    }
}

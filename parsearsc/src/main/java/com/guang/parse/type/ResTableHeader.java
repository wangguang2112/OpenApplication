package com.guang.parse.type;

/**
 * Created by wangguang.
 * Date:2017/6/27
 * Description:
 */

public class ResTableHeader implements IChunkHeader {

    public ResChunkHeader header = new ResChunkHeader();

    public int packageCount;

    @Override
    public String toString() {
        return "ResTableHeader{" +
                "header=" + header +
                ", packageCount=" + packageCount +
                '}';
    }

    @Override
    public int getHeaderSize() {
        return header.getHeaderSize() + 4;
    }

}

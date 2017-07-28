package com.guang.parse.type;

/**
 * Created by wangguang.
 * Date:2017/6/27
 * Description:
 */

public class ResTableHeader implements IChunkHeader {

    public ResChunkHeader header = new ResChunkHeader();

    public int packageCount;

    public byte[] orginByte;
    @Override
    public String toString() {
        return "ResTableHeader{" +
                "header=" + header +
                ", packageCount=" + packageCount +
                '}';
    }

    public byte[] toByte(){
        return orginByte;
    }
    @Override
    public int getHeaderSize() {
        return header.getHeaderSize() + 4;
    }

}

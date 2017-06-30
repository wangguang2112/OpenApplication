package com.guang.parse.type;

import java.util.Arrays;

/**
 * Created by wangguang.
 * Date:2017/6/28
 * Description:
 */

public class ResPackageHeader implements IChunkHeader {

    public ResChunkHeader header=new ResChunkHeader();

    /**
     * 0x7F
     */
    public int id;

    /**
     * 256位 16行
     */
    public char[] name=new char[128];

    public int typeStrings;

    public int lastPublicType;

    public int keyStrings;

    public int lastPublicKey;


    @Override
    public String toString() {
        return "ResPackageHeader{" +
                "header=" + header +
                ", id=" + id +
                ", name=" + Arrays.toString(name) +
                ", typeStrings=" + typeStrings +
                ", lastPublicType=" + lastPublicType +
                ", keyStrings=" + keyStrings +
                ", lastPublicKey=" + lastPublicKey +
                '}';
    }

    @Override
    public int getHeaderSize() {
        return header.getHeaderSize()+4+128*2+4+4+4+4;
    }
}

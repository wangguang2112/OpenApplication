package com.guang.parse.type;

import com.guang.parse.ByteUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by wangguang.
 * Date:2017/6/28
 * Description:
 */

public class ResTableMapEntry extends ResTableEntry {

    public ResTableRef parent;

    public int count;

    public ResTableMap[] maps;
    @Override
    public int getSize() {
        return super.getSize() + parent.getSize() + 4 +maps.length*maps[0].getSize();
    }

    @Override
    public String toString() {
        return "ResTableMapEntry{" +
                "parent=" + parent +
                ", count=" + count +
                '}';
    }

    @Override
    public byte[] toByte() throws IOException {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        outputStream.write(ByteUtils.toByte(size));
        outputStream.write(ByteUtils.toByte(flags));
        outputStream.write(ref.toByte());
        outputStream.write(value.toByte());
        outputStream.write(ByteUtils.toByte(count));
        for(ResTableMap map:maps){
            outputStream.write(map.toByte());
        }
        byte[] result= outputStream.toByteArray();
        outputStream.close();
        return result;
    }
}

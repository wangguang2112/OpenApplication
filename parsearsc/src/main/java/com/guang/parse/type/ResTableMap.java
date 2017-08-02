package com.guang.parse.type;

import com.guang.parse.ByteUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by wangguang.
 * Date:2017/6/28
 * Description:
 */

public class ResTableMap implements IChunkBody {

    public ResTableRef name;
    public ResValue value;

    @Override
    public int getSize() {
        return name.getSize()+value.getSize();
    }

    @Override
    public String toString() {
        return "ResTableMap{" +
                "name=" + name +
                ", value=" + value +
                '}';
    }

    @Override
    public byte[] toByte() throws IOException {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        outputStream.write(name.toByte());
        outputStream.write(value.toByte());
        byte[] result= outputStream.toByteArray();
        outputStream.close();
        return result;
    }
}

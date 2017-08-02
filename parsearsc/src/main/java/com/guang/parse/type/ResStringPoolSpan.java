package com.guang.parse.type;

import com.guang.parse.ByteUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by wangguang.
 * Date:2017/6/27
 * Description:
 */

public class ResStringPoolSpan implements IChunkBody {


    public ResStringPoolRef name = new ResStringPoolRef();

    public int firstChar;

    public int lastChar;

    @Override
    public String toString() {
        return "ResStringPoolSpan{" +
                "name=" + name +
                ", firstChar=" + firstChar +
                ", lastChar=" + lastChar +
                '}';
    }

    @Override
    public int getSize() {
        return name.getSize() + 4 + 4;
    }

    @Override
    public byte[] toByte() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(name.toByte());
        outputStream.write(ByteUtils.toByte(firstChar));
        outputStream.write(ByteUtils.toByte(lastChar));
        byte[] result = outputStream.toByteArray();
        outputStream.close();
        return result;
    }
}

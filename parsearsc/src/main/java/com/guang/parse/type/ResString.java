package com.guang.parse.type;

import com.guang.parse.ByteUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by wangguang.
 * Date:2017/6/30
 * Description:
 */

public class ResString implements IChunkBody {

    //字符串的长度
    public int length;

    //字节的长度
    public int byteLength;

    public String str;

    @Override
    public int getSize() {
        if (str != null) {
            return byteLength * 4 + str.length();
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return str.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return str.equals(obj);
    }

    @Override
    public byte[] toByte() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(ByteUtils.encodeLength(length));
        outputStream.write(ByteUtils.encodeLength(byteLength));
        outputStream.write(ByteUtils.toByte(str));
        byte[] result = outputStream.toByteArray();
        outputStream.close();
        return result;
    }
}

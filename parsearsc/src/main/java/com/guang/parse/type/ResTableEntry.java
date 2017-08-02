package com.guang.parse.type;

import com.guang.parse.ByteUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by wangguang.
 * Date:2017/6/28
 * Description:
 */

public class ResTableEntry implements IChunkBody {

    public final static int FLAG_COMPLEX = 0x0001;
    public final static int FLAG_PUBLIC = 0x0002;


    /**
     * 表示头部大小
     */
    public short size;

    public short flags;

    public ResStringPoolRef ref = new ResStringPoolRef();

    public ResValue value=new ResValue();
    @Override
    public int getSize() {
        return 2 + 2 + ref.getSize()+value.getSize();
    }

    @Override
    public String toString() {
        return "ResTableEntry{" +
                "size=" + size +
                ", flags=" + flags +
                ", ref=" + ref +
                '}';
    }

    @Override
    public byte[] toByte() throws IOException {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        outputStream.write(ByteUtils.toByte(size));
        outputStream.write(ByteUtils.toByte(flags));
        outputStream.write(ref.toByte());
        outputStream.write(value.toByte());
        byte[] result= outputStream.toByteArray();
        outputStream.close();
        return result;
    }
}

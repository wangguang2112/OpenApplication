package com.guang.parse.type;

import com.guang.parse.ByteUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by wangguang.
 * Date:2017/6/27
 * Description:
 */

public class ResStringPoolChunk implements IChunk {

    public ResStringPoolHeader header=new ResStringPoolHeader();

    public ResStringPoolRef[] stringIndexAry;

    public ResStringPoolRef[] styleIndexAry;

    public ResString[] strings;
    public ResStringPoolSpan[][] styles;

    public byte[] orginByte;

    @Override
    public String toString() {
        return "ResStringPoolChunk{" +
                "header=" + header +
                '}';
    }

    @Override
    public byte[] toByte() throws IOException {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        outputStream.write(header.toByte());
        for(ResStringPoolRef strIndex:stringIndexAry) {
            outputStream.write(strIndex.toByte());
        }
        for(ResStringPoolRef styIndex:styleIndexAry) {
            outputStream.write(styIndex.toByte());
        }
        for(ResString str:strings){
            outputStream.write(str.toByte());
        }
        for (ResStringPoolSpan[] sty : styles) {
            if (sty != null) {
                for (ResStringPoolSpan st : sty) {
                    outputStream.write(st.toByte());
                }
            }
            outputStream.write(ByteUtils.toByte(0xFFFFFFFF));
        }
        outputStream.write(ByteUtils.toByte(0xFFFFFFFF));
        outputStream.write(ByteUtils.toByte(0xFFFFFFFF));
        byte[] result= outputStream.toByteArray();
        outputStream.close();
        return result;
    }
}

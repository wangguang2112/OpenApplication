package com.guang.parse.type;

import com.guang.parse.FileUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by wangguang.
 * Date:2017/7/4
 * Description:
 */

public class RescFile implements IChunk {
    public ResTableHeader header;
    public ResStringPoolChunk wholeStringChunk;
    public ResPackageChunk[] mChunks;

    public void makeNewResFile(String file){
        byte[] newData=new byte[header.header.size];
        FileUtil.writeBytesToFile(new File(file),newData);
    }

    @Override
    public byte[] toByte() throws IOException {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        outputStream.write(header.toByte());
        outputStream.write(wholeStringChunk.toByte());
        for(ResPackageChunk chunk:mChunks) {
            outputStream.write(chunk.toByte());
        }
        byte[] result= outputStream.toByteArray();
        outputStream.close();
        return result;
    }
}

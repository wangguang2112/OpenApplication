package com.guang.parse;

import com.guang.parse.type.ResPackageChunk;
import com.guang.parse.type.ResStringPoolChunk;
import com.guang.parse.type.ResTableHeader;

import java.io.File;

public class MainClass {
    public static void main(String[] args){
        if(args.length!=2){
            throw new IllegalArgumentException("填入arsc文件 和目标文件");
        }
        File arscFile=new File(args[0]);
        byte[] srcByte=ParserUtils.readByte(arscFile);
        System.out.println("Now srcByte.length="+srcByte.length);
        System.out.println("");
        ResTableHeader header=ParserUtils.parseResTableHeaderChunk(srcByte,0);
        System.out.println(header);
        System.out.println("");
        System.out.println("********************************************");
        System.out.println("");
        ResStringPoolChunk stringChunk=ParserUtils.parseStringPoolChunk(srcByte,header.getHeaderSize());
        System.out.println("");
        System.out.println("********************************************");
        ResPackageChunk packageChunk = ParserUtils.parsePackageChunk(srcByte, header.getHeaderSize() + stringChunk.header.header.size);


        System.out.println("");
        System.out.println("********************************************");

    }
}

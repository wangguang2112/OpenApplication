package com.guang.parse;

import com.guang.parse.type.RescFile;

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
        RescFile file=ParserUtils.parseResFileStructure(srcByte);
        System.out.println("");
        ParserUtils.printArsc(file);
    }
}

package com.guang.parse;

import com.guang.parse.type.ResChunkHeader;
import com.guang.parse.type.ResPackageChunk;
import com.guang.parse.type.ResPackageHeader;
import com.guang.parse.type.ResString;
import com.guang.parse.type.ResStringPoolChunk;
import com.guang.parse.type.ResStringPoolHeader;
import com.guang.parse.type.ResStringPoolRef;
import com.guang.parse.type.ResTableConfig;
import com.guang.parse.type.ResTableEntry;
import com.guang.parse.type.ResTableHeader;
import com.guang.parse.type.ResTableMap;
import com.guang.parse.type.ResTableMapEntry;
import com.guang.parse.type.ResTableRef;
import com.guang.parse.type.ResTableTypeChunk;
import com.guang.parse.type.ResTableTypeHeader;
import com.guang.parse.type.ResTypeChunk;
import com.guang.parse.type.ResTypeSpecChunk;
import com.guang.parse.type.ResTypeSpecHeader;
import com.guang.parse.type.ResValue;
import com.guang.parse.type.RescFile;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wangguang.
 * Date:2017/6/29
 * Description:
 */

public class ParserUtils {

    public static final int LONG_BYTE_COUNT = 8;

    public static final int INT_BYTE_COUNT = 4;

    public static final int SHORT_BYTE_COUNT = 2;

    public static final int BYTE_COUNT = 1;

    public static final int PACKAGE_NAME_BYTE_COUNT = 256;

    /**
     * 读取文件
     */

    public static byte[] readByte(File file) {
        long size = file.length();
        byte[] srcByte = null;
        int percent = 0;
        int lastPercent = 0;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BufferedInputStream bis = new BufferedInputStream(fis);
            int len = 0;
            int alllen = 0;
            byte[] tempData = new byte[1024];
            while ((len = bis.read(tempData, 0, 1024)) != -1) {
                outputStream.write(tempData, 0, len);
                alllen += len;
                percent = (int) (alllen * 100 / size);
                if (lastPercent != percent) {
                    System.out.print("read arsc ..." + percent + "%\r");
                    lastPercent = percent;
                }
            }
            srcByte = outputStream.toByteArray();
            System.out.println("read arsc ...100%");
            outputStream.close();
            bis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return srcByte;
    }

    public static RescFile parseResFileStructure(byte[] data) {
        RescFile file = new RescFile();
        file.header = parseResTableHeaderChunk(data, 0);
        file.wholeStringChunk = ParserUtils.parseStringPoolChunk(data, file.header.getHeaderSize());
        file.mChunks = new ResPackageChunk[file.header.packageCount];
        int start = file.header.getHeaderSize() + file.wholeStringChunk.header.header.size;
        for (int i = 0; i < file.header.packageCount && start <= data.length; i++) {
            ResPackageChunk packageChunk = ParserUtils.parsePackageChunk(data, start);
            file.mChunks[i] = packageChunk;
            start += packageChunk.header.header.size;
        }
        return file;
    }

    public static ResTableHeader parseResTableHeaderChunk(byte[] data, int offset) {
        ResTableHeader header = new ResTableHeader();
        header.header = parseResChunkHeader(data, offset + 0);
        header.packageCount = byte2Int(data, header.header.getHeaderSize() + offset + 0);
        return header;
    }

    public static ResStringPoolChunk parseStringPoolChunk(byte[] data, int offset) {
        ResStringPoolChunk chunk = new ResStringPoolChunk();
        chunk.header = parseStringPoolHeader(data, offset + 0);
        chunk.stringIndexAry = parseStringPoolRef(data, offset + chunk.header.getHeaderSize(), chunk.header.stringCount);
        chunk.styleIndexAry = parseStringPoolRef(data, offset + chunk.header.stringCount * INT_BYTE_COUNT + chunk.header.getHeaderSize(), chunk.header.styleCount);
        chunk.strings = parseStringPoolWithIndex(data, offset + chunk.header.stringsStart, chunk.header.stringCount, chunk.stringIndexAry);
        chunk.styles = parseStringPoolWithIndex(data, offset + chunk.header.styleStart, chunk.header.styleCount, chunk.styleIndexAry);
        return chunk;
    }

    public static ResStringPoolHeader parseStringPoolHeader(byte[] data, int offset) {
        ResStringPoolHeader header = new ResStringPoolHeader();
        header.header = parseResChunkHeader(data, offset + 0);
        header.stringCount = byte2Int(data, offset + header.header.getHeaderSize());
        header.styleCount = byte2Int(data, offset + header.header.getHeaderSize() + INT_BYTE_COUNT);
        header.flags = byte2Int(data, offset + header.header.getHeaderSize() + INT_BYTE_COUNT + INT_BYTE_COUNT);
        header.stringsStart = byte2Int(data, offset + header.header.getHeaderSize() + INT_BYTE_COUNT + INT_BYTE_COUNT + INT_BYTE_COUNT);
        header.styleStart = byte2Int(data, offset + header.header.getHeaderSize() + INT_BYTE_COUNT + INT_BYTE_COUNT + INT_BYTE_COUNT + INT_BYTE_COUNT);
        return header;
    }

    public static ResPackageChunk parsePackageChunk(byte[] data, int offset) {
        ResPackageChunk packageChunk = new ResPackageChunk();
        packageChunk.header = parsePackageHeader(data, offset);
        packageChunk.resTypeStrings = parseStringPoolChunk(data, offset + packageChunk.header.typeStrings);
        packageChunk.resNameStrings = parseStringPoolChunk(data, offset + packageChunk.header.keyStrings);
        ArrayList<ResTypeChunk> list = new ArrayList<>();
        int start = offset + packageChunk.header.keyStrings + packageChunk.resNameStrings.header.header.size;
        for (int i = 0; i < packageChunk.header.lastPublicType; i++) {
            ArrayList<ResTableTypeChunk> typeList = new ArrayList<>();
            ResTypeChunk chunk = new ResTypeChunk();
            chunk.specChunk = parseTypeSpecChunk(data, start);
            start += chunk.specChunk.header.header.size;
            ResChunkHeader header = parseResChunkHeader(data, start);
            while (header != null && header.type == ResChunkHeader.Type.RES_TABLE_TYPE_TYPE.value()) {
                ResTableTypeChunk typeChunk = parseTableTypeChunk(data, start);
                typeList.add(typeChunk);
                start += typeChunk.header.header.size;
                header = parseResChunkHeader(data, start);
            }
            chunk.typeChunks = typeList.toArray(new ResTableTypeChunk[0]);
            list.add(chunk);
        }
        packageChunk.typeChunk = list.toArray(new ResTypeChunk[0]);
        return packageChunk;
    }

    public static ResTypeSpecChunk parseTypeSpecChunk(byte[] data, int offset) {
        ResTypeSpecChunk spec = new ResTypeSpecChunk();
        spec.header = parseTypeSpecHeader(data, offset);
        spec.specArray = new int[spec.header.entryCount];
        int start = offset + spec.header.getHeaderSize();
        for (int i = 0; i < spec.header.entryCount; i++) {
            spec.specArray[i] = byte2Int(data, start);
            start += INT_BYTE_COUNT;
        }
        return spec;
    }

    public static ResTableTypeChunk parseTableTypeChunk(byte[] data, int offset) {
        ResTableTypeChunk typeChunk = new ResTableTypeChunk();
        typeChunk.header = parseTableTypeHeader(data, offset);
        typeChunk.typeIndexArray = new int[typeChunk.header.entryCount];
        int start = offset + typeChunk.header.header.headerSize;
        for (int i = 0; i < typeChunk.header.entryCount; i++) {
            typeChunk.typeIndexArray[i] = byte2Int(data, start);
            start += INT_BYTE_COUNT;
        }
        typeChunk.tableEntry = parseTableEntryWithIndex(data, offset + typeChunk.header.entriesStart, typeChunk.header.entryCount, typeChunk.typeIndexArray);
        return typeChunk;
    }

    public static ResTableTypeHeader parseTableTypeHeader(byte[] data, int offset) {
        ResTableTypeHeader header = new ResTableTypeHeader();
        header.header = parseResChunkHeader(data, offset);
        header.id = data[offset + header.header.getHeaderSize()];
        header.res0 = data[offset + header.header.getHeaderSize() + BYTE_COUNT];
        header.res1 = byte2Short(data, offset + header.header.getHeaderSize() + BYTE_COUNT + BYTE_COUNT);
        header.entryCount = byte2Int(data, offset + header.header.getHeaderSize() + BYTE_COUNT + BYTE_COUNT + SHORT_BYTE_COUNT);
        header.entriesStart = byte2Int(data, offset + header.header.getHeaderSize() + BYTE_COUNT + BYTE_COUNT + SHORT_BYTE_COUNT + INT_BYTE_COUNT);
        header.resConfig = parseResTableConfig(data, offset + header.header.getHeaderSize() + BYTE_COUNT + BYTE_COUNT + SHORT_BYTE_COUNT + INT_BYTE_COUNT + INT_BYTE_COUNT);
        return header;
    }

    public static ResTableConfig parseResTableConfig(byte[] data, int offset) {
        ResTableConfig config = new ResTableConfig();
        int start = offset;
        config.size = byte2Int(data, start);
        start += INT_BYTE_COUNT;
        config.imsi = byte2Int(data, start);
        start += INT_BYTE_COUNT;
        config.locale = byte2Int(data, start);
        start += INT_BYTE_COUNT;
        config.screenType = byte2Int(data, start);
        config.density = byte2Short(data, start + 2);
        start += INT_BYTE_COUNT;
        config.input = byte2Int(data, start);
        start += INT_BYTE_COUNT;
        config.screenSize = byte2Int(data, start);
        start += INT_BYTE_COUNT;
        config.version = byte2Int(data, start);
        start += INT_BYTE_COUNT;
        config.screenConfig = byte2Int(data, start);
        start += INT_BYTE_COUNT;
        config.screenSizeDp = byte2Int(data, start);
        start += INT_BYTE_COUNT;
        config.localeScript = Arrays.copyOfRange(data, start, start + 4);
        start += 4;
        config.localeVariant = Arrays.copyOfRange(data, start, start + 8);
        return config;
    }

    public static ResTypeSpecHeader parseTypeSpecHeader(byte[] data, int offset) {
        ResTypeSpecHeader header = new ResTypeSpecHeader();
        header.header = parseResChunkHeader(data, offset);
        header.id = data[offset + header.header.getHeaderSize()];
        header.res0 = data[offset + header.header.getHeaderSize() + BYTE_COUNT];
        header.res1 = byte2Short(data, offset + header.header.getHeaderSize() + BYTE_COUNT + BYTE_COUNT);
        header.entryCount = byte2Int(data, offset + header.header.getHeaderSize() + BYTE_COUNT + BYTE_COUNT + SHORT_BYTE_COUNT);
        return header;
    }


    public static ResPackageHeader parsePackageHeader(byte[] data, int offset) {
        ResPackageHeader header = new ResPackageHeader();
        header.header = parseResChunkHeader(data, offset);
        header.id = byte2Int(data, offset + header.header.getHeaderSize());
        header.name = parseUTF8String(data, offset + header.header.getHeaderSize() + INT_BYTE_COUNT, PACKAGE_NAME_BYTE_COUNT);
        header.typeStrings = byte2Int(data, offset + header.header.getHeaderSize() + INT_BYTE_COUNT + PACKAGE_NAME_BYTE_COUNT);
        header.lastPublicType = byte2Int(data, offset + header.header.getHeaderSize() + INT_BYTE_COUNT + PACKAGE_NAME_BYTE_COUNT + INT_BYTE_COUNT);
        header.keyStrings = byte2Int(data, offset + header.header.getHeaderSize() + INT_BYTE_COUNT + PACKAGE_NAME_BYTE_COUNT + INT_BYTE_COUNT + INT_BYTE_COUNT);
        header.lastPublicKey = byte2Int(data, offset + header.header.getHeaderSize() + INT_BYTE_COUNT + PACKAGE_NAME_BYTE_COUNT + INT_BYTE_COUNT + INT_BYTE_COUNT + INT_BYTE_COUNT);
        return header;
    }


    public static ResTableEntry[] parseTableEntryWithIndex(byte[] data, int start, int count, int[] index) {
        ResTableEntry[] entrys = new ResTableEntry[count];
        for (int i = 0; i < count; i++) {
            if (index[i] != -1) {
                entrys[i] = parseTableEntry(data, start + index[i]);
            } else {
                ResTableEntry entry = new ResTableEntry();
                entry.size = 0;
                entrys[i] = entry;
            }
        }
        return entrys;
    }

    public static ResTableEntry parseTableEntry(byte[] data, int start) {
        ResTableEntry entry = new ResTableEntry();
        entry.size = byte2Short(data, start);
        entry.flags = byte2Short(data, start + SHORT_BYTE_COUNT);
        entry.ref = new ResStringPoolRef();
        entry.ref.index = byte2Int(data, start + SHORT_BYTE_COUNT + SHORT_BYTE_COUNT);
        if (entry.flags == ResTableEntry.FLAG_PUBLIC) {
            entry.value = parseResValue(data, start + SHORT_BYTE_COUNT + SHORT_BYTE_COUNT + INT_BYTE_COUNT);
        } else if (entry.flags == ResTableEntry.FLAG_COMPLEX) {
            entry = parseResTableMapEntry(data, start + SHORT_BYTE_COUNT + SHORT_BYTE_COUNT + INT_BYTE_COUNT, entry);
        }else{
            entry.value = parseResValue(data, start + SHORT_BYTE_COUNT + SHORT_BYTE_COUNT + INT_BYTE_COUNT);
        }
        return entry;
    }

    public static ResTableMapEntry parseResTableMapEntry(byte[] data, int start, ResTableEntry parent) {
        ResTableMapEntry entry = new ResTableMapEntry();
        entry.size = parent.size;
        entry.flags = parent.flags;
        entry.ref = parent.ref;
        entry.parent = new ResTableRef();
        entry.parent.index = byte2Int(data, start);
        entry.count = byte2Int(data, start + INT_BYTE_COUNT);
        entry.maps = new ResTableMap[entry.count];
        for (int i = 0; i < entry.count; i++) {
            entry.maps[i] = parseResTableMap(data, start + 8 * i);
        }
        return entry;
    }

    public static ResTableMap parseResTableMap(byte[] data, int start) {
        ResTableMap map = new ResTableMap();
        map.name = new ResTableRef();
        map.name.index = byte2Int(data, start);
        map.value = parseResValue(data, start + map.name.getSize());
        return map;
    }

    public static ResValue parseResValue(byte[] data, int start) {
        ResValue v = new ResValue();
        v.size = byte2Short(data, start);
        v.res0 = data[start + SHORT_BYTE_COUNT];
        v.dataType = data[start + SHORT_BYTE_COUNT + BYTE_COUNT];
        v.data = byte2Int(data, start + SHORT_BYTE_COUNT + BYTE_COUNT + BYTE_COUNT);
        return v;
    }

    public static ResStringPoolRef[] parseStringPoolRef(byte[] data, int start, int count) {
        ResStringPoolRef[] refs = new ResStringPoolRef[count];
        for (int i = 0; i < count; i++) {
            refs[i] = new ResStringPoolRef();
            refs[i].index = byte2Int(data, start + i * INT_BYTE_COUNT);
        }
        return refs;
    }

    public static ResString[] parseStringPoolWithIndex(byte[] data, int start, int count, ResStringPoolRef[] refs) {
        ResString[] strs = new ResString[count];
        for (int i = 0; i < count; i++) {
            strs[i] = parseResString(data, start + refs[i].index);
        }
        return strs;
    }

    public static ResString parseResString(byte[] data, int start) {
        ResString string = new ResString();
        string.length = (int) OneByte2Long(data[start + 1], 0);
        byte[] stringByte = Arrays.copyOfRange(data, start + 2, start + 2 + string.length);
        try {
            string.str = new String(stringByte, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return string;
    }

    public static String parseUTF8String(byte[] data, int start, int maxLen) {
        char lastChar = '\n';
        int nowPosition = 0;
        while (lastChar != 0 && nowPosition < maxLen) {
            lastChar = (char) byte2Short(data, start + nowPosition);
            nowPosition += 2;
        }
        if (nowPosition == 0) {
            return "";
        }
        byte[] byteArray = Arrays.copyOfRange(data, start, start + nowPosition - 2);
        char[] charArray = new char[byteArray.length / 2];
        for (int i = 0; i < nowPosition - 2; i += 2) {
            charArray[i / 2] = (char) byte2Short(byteArray, i);
        }
        return new String(charArray);
    }

    public static ResChunkHeader parseResChunkHeader(byte[] data, int offset) {
        if (offset > data.length - 8) {
            return null;
        }
        ResChunkHeader header = new ResChunkHeader();
        header.type = byte2Short(data, offset);
        header.headerSize = byte2Short(data, offset + SHORT_BYTE_COUNT);
        header.size = byte2Int(data, offset + SHORT_BYTE_COUNT + SHORT_BYTE_COUNT);
        return header;
    }


    public static int byte2Int(byte[] data, int start) {
        byte[] intbyte = Arrays.copyOfRange(data, start, start + INT_BYTE_COUNT);
        long i1 = intbyte[0] & 0x000000FF;
        long i2 = OneByte2Long(intbyte[1], 8);
        long i3 = OneByte2Long(intbyte[2], 16);
        long i4 = OneByte2Long(intbyte[3], 24);
        return (int) (i1 + i2 + i3 + i4);
    }

    public static short byte2Short(byte[] data, int start) {
        byte[] intbyte = Arrays.copyOfRange(data, start, start + SHORT_BYTE_COUNT);
        long i1 = intbyte[0] & 0x000000FF;
        long i2 = OneByte2Long(intbyte[1], 8);
        return (short) (i1 + i2);
    }

    public static long byte2SLong(byte[] data, int start) {
        byte[] intbyte = Arrays.copyOfRange(data, start, start + LONG_BYTE_COUNT);
        long i1 = intbyte[0] & 0x000000FF;
        long i2 = OneByte2Long(intbyte[1], 8);
        long i3 = OneByte2Long(intbyte[2], 16);
        long i4 = OneByte2Long(intbyte[3], 24);
        long i5 = OneByte2Long(intbyte[4], 32);
        long i6 = OneByte2Long(intbyte[5], 40);
        long i7 = OneByte2Long(intbyte[6], 48);
        long i8 = OneByte2Long(intbyte[7], 56);
        return i1 + i2 + i3 + i4 + i5 + i6 + i7 + i8;
    }

    public static long OneByte2Long(byte i, int moveBit) {
        return (((long) i) & 0x000000FF) << moveBit;
    }


    public static void printArsc(RescFile file) {
        for (int i = 0; i < file.header.packageCount; i++) {
            ResPackageChunk chunk = file.mChunks[i];
            System.out.printf("package id 0x%s name %s \n", Integer.toHexString(chunk.header.id), chunk.header.name);
            for (ResTypeChunk ch : chunk.typeChunk) {
                String typeName = chunk.resTypeStrings.strings[ch.specChunk.header.id - 1].str;
                System.out.printf("   type %s  entryCount %s\n", typeName, ch.specChunk.header.entryCount);
                for (ResTableTypeChunk ch2 : ch.typeChunks) {
                    System.out.printf("\n");
                    if (ch2.header.resConfig.version == 0) {
                        System.out.printf("        config %s-%s\n", typeName, ResTableConfig.getDensityStr(ch2.header.resConfig.density));
                    } else {
                        System.out.printf("        config %s-%s-v%d\n", typeName, ResTableConfig.getDensityStr(ch2.header.resConfig.density), ch2.header.resConfig.version);
                    }
                    System.out.printf("\n");
                    for (int index = 0; index < ch2.tableEntry.length; index++) {
                        ResTableEntry entry = ch2.tableEntry[index];
                        if (entry.size != 0) {
                         /*   if (entry.flags == 0) {
                                System.out.printf("             resource id 0x%02x%02x%04x name %25s \n", chunk.header.id, ch.specChunk.header.id, index,
                                        chunk.resNameStrings.strings[entry.ref.index]);
                            } else {*/
                                System.out.printf("             resource  id  0x%02x%02x%04x  name:    %40s  value:    %50s\n", chunk.header.id, ch.specChunk.header.id, entry.ref.index,
                                        chunk.resNameStrings.strings[entry.ref.index], entry.value.getDataStr(file.wholeStringChunk));
//                            }
                        }
                    }
                }
            }
        }
    }
}

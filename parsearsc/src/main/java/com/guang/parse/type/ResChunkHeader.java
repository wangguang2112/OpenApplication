package com.guang.parse.type;

/**
 * Created by wangguang.
 * Date:2017/6/27
 * Description:
 * type：是当前这个chunk的类型
 * headerSize：是当前这个chunk的头部大小
 * size：是当前这个chunk的大小
 */

public class ResChunkHeader implements IChunkHeader {

    public short type;

    public short headerSize;

    public int size;


    @Override
    public int getHeaderSize() {
        return 2 + 2 + 4;
    }

    @Override
    public String toString() {
        return "ResChunkHeader{" +
                "type=" + type +
                ", headerSize=" + headerSize +
                ", size=" + size +
                '}';
    }

    public enum Type {
        RES_NULL_TYPE(0x0000),
        RES_STRING_POOL_TYPE(0x0001),
        RES_TABLE_TYPE(0x0002),
        RES_XML_TYPE(0x0003),

        // Chunk types in RES_XML_TYPE
        RES_XML_FIRST_CHUNK_TYPE(0x0100),
        RES_XML_START_NAMESPACE_TYPE(0x0100),
        RES_XML_END_NAMESPACE_TYPE(0x0101),
        RES_XML_START_ELEMENT_TYPE(0x0102),
        RES_XML_END_ELEMENT_TYPE(0x0103),
        RES_XML_CDATA_TYPE(0x0104),
        RES_XML_LAST_CHUNK_TYPE(0x017f),

        // This contains a uint32_t array mapping strings in the string
        // pool back to resource identifiers.  It is optional.
        RES_XML_RESOURCE_MAP_TYPE(0x0180),

        // Chunk types in RES_TABLE_TYPE
        RES_TABLE_PACKAGE_TYPE(0x0200),
        RES_TABLE_TYPE_TYPE(0x0201),
        RES_TABLE_TYPE_SPEC_TYPE(0x0202),
        RES_TABLE_LIBRARY_TYPE(0x0203);


        private short value = 0;

        Type(int value) {
            this.value = (short) value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.value);
        }
    }

}

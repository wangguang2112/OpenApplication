package com.guang.parse.type;

/**
 * Created by wangguang.
 * Date:2017/6/28
 * Description:
 */

public class ResTableMapEntry extends ResTableEntry {

    public ResTableRef parent;

    public int count;

    @Override
    public int getSize() {
        return super.getSize() + parent.getSize() + 4;
    }

    @Override
    public String toString() {
        return "ResTableMapEntry{" +
                "parent=" + parent +
                ", count=" + count +
                '}';
    }
}
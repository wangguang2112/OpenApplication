package com.wang.annoation.compiler.model;

import com.squareup.javapoet.TypeName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangguang.
 * Date:2017/2/9
 * Description:
 */

public class ClassModel {

    public String pack = "com.guang.wang.openapplication.model";

    public boolean open = false;

    public String className;

    public Map<String, TypeName> field=new HashMap<>();

    public int count;

    public boolean hasSetter = true;

    public boolean hasGetter = true;

    public boolean hasToString = true;

    public boolean build=false;
    public void clear() {
        pack = "com.guang.wang.openapplication.model";
        open = false;
        className = null;
        field.clear();
        count = 0;
        hasGetter = true;
        hasSetter = true;
        build=false;
    }

    @Override
    public String toString() {
        return "ClassModel{" +
                "open=" + open +
                ", className='" + className + '\'' +
                ", field=" + field +
                ", count=" + count +
                '}';
    }
}

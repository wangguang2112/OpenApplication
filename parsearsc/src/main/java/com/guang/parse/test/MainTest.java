package com.guang.parse.test;


import com.guang.parse.ByteUtils;
import com.guang.parse.confuse.ConfuseMechine;

import junit.framework.TestCase;

import java.util.Arrays;

/**
 * Created by wangguang.
 * Date:2017/7/27
 * Description:
 */

public class MainTest extends TestCase {
    int position=0;
    int notation=0;
    int number=0;
    protected void setUp() {
        position= 19;
        notation=15;
        number=1294;
    }

    public void testGetIndexFromLength(){
        ConfuseMechine m=ConfuseMechine.getMechine(123);
        assertTrue(String.valueOf(m.getIndexFromLength(position,notation,number)),m.getIndexFromLength(position,notation,number)==11);
    }
    public void testNembaer(){
        long i=Long.decode("0x0101010101010101");
        long l=Long.decode("0x0101010101010101");
        assertTrue(Arrays.equals(ByteUtils.toByte(i),ByteUtils.toByte(l)));
    }
}

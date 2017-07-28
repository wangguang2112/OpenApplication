package com.guang.parse.confuse;

import java.util.Random;

/**
 * Created by wangguang.
 * Date:2017/7/27
 * Description:
 */

public class ConfuseMechine {

    public static final String ELEMENTS = "abcdefghijklmnopqrstuvwxyz_0123456789";

    public final int jump = 3;

    private String[] elements;

    private int index[];

    private int startIndex = 1;

    private int length = 0;

    public static ConfuseMechine getMechine(long seed) {
        ConfuseMechine mechine = new ConfuseMechine(seed);
        return mechine;
    }

    private ConfuseMechine(long seed) {
        length = ELEMENTS.length() + 1;
        index = new int[length];
        index[0] = -1;
        for (int i = 1; i < length; i++) {
            index[i] = i;
        }
        breakIndex(index, seed);
        elements = new String[length];
        elements = ("?" + ELEMENTS).split("");
    }

    public String getNextName() {
        StringBuilder builder = new StringBuilder();
        boolean isMatch=false;
        do {
            builder.delete(0,builder.length());
            getElement(builder, 0);
            startIndex++;
            isMatch=builder.indexOf("?") != -1||!builder.toString().matches("^([a-z0-9_])*[a-z]$");
        } while (isMatch);
        builder.reverse();
        return builder.toString();
    }

    private void getElement(StringBuilder builder, int position) {
        int eIndex = getIndexFromLength(position, length, startIndex);
        if (eIndex == -1) {
            return;
        } else {
            builder.append(elements[eIndex]);
            getElement(builder, ++position);
        }
    }

    private void breakIndex(int[] index, long seed) {
        Random random = new Random(seed);
        int originStart = Math.abs(random.nextInt()) % length;
        for (int i = 1; i < length; i++) {
            int destIndex = (originStart + jump + i) % length;
            int start = (originStart + i) % length;
            index[start] = index[start] ^ index[destIndex];
            index[destIndex] = index[start] ^ index[destIndex];
            index[start] = index[start] ^ index[destIndex];
        }
        System.out.println(index);
    }

    /**
     * 把startIndex转换为length进制的数
     */
    public int getIndexFromLength(int position, int notation, int originNumber) {
        int result = 0;
        if (position == 0) {
            result = originNumber % notation;
        } else {
            int floor = (int) Math.pow(notation, position);
            if (floor <= originNumber) {
                result = (originNumber / floor % notation);
            } else {
                result = -1;
            }
        }
        return result;
    }

}

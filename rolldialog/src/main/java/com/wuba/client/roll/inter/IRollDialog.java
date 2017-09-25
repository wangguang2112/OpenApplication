package com.wuba.client.roll.inter;

/**
 * Created by wangguang.
 * Date:2017/9/21
 * Description:
 */

public interface IRollDialog {

    boolean showDialog();

    void preload();

    void reset();
    /**
     * 表示当前Dialog在队列中的优先级 数字越小，优先级越高
     */
    int priority();

    void setChain(Chain chain);
}

interface Chain {

    void next();

    void stop();
}

package com.wuba.client.roll.inter;

/**
 * Created by wangguang.
 * Date:2017/9/21
 * Description:
 */

public interface IRollHandler {

    String TAG="IRollHandler";

    void start();

    void stop();

    String getKey();

    void reset();

    IRollHandler getNextHandler();

    boolean nextHandler(IRollHandler handler);

    RollTransaction beginTransaction();

    void preload();

    interface RollTransaction {

        RollTransaction add(IRollDialog dialog);

        RollTransaction remove(IRollDialog dialog);

        RollTransaction commit();
    }
}

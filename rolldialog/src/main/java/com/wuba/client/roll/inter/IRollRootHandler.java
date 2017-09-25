package com.wuba.client.roll.inter;

/**
 * Created by wangguang.
 * Date:2017/9/22
 * Description:
 */

public interface IRollRootHandler extends IRollHandler {

    RollTransaction beginChildTransaction(String key);

    void trigger(String key);

    void clearHandler(String key);

    void setOnRollListener(OnDialogRollListener onRollListener);
}

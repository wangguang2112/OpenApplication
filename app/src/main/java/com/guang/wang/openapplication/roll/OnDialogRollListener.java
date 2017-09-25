package com.guang.wang.openapplication.roll;

/**
 * Created by wangguang.
 * Date:2017/9/25
 * Description:
 */

public interface OnDialogRollListener {
    void onTouchEnd(String tag);
    void onHandlerChange(String lastTag,String nextTag);
}

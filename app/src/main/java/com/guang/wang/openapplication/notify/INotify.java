package com.guang.wang.openapplication.notify;

import android.content.Intent;

/**
 * Created by wangguang.
 * Date:2016/12/14
 * Description:
 */

public interface INotify {
    void rigisterCallBack(String action,NotifyCallBack callBack);
    void unrigisterCallBack(String action,NotifyCallBack callBack);
    void clearCallBack(String action);
    void clearAllCallBack();
    void notify(String action,Intent intent);
    void notifyOnMain(String action,Intent intent);
}

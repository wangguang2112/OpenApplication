package com.guang.wang.openapplication;

import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import android.app.Application;
import android.content.Intent;

/**
 * Created by wangguang.
 * Date:2017/4/28
 * Description:
 */
@DefaultLifeCycle(application = "com.guang.wang.openapplication.ApplicationProxy",             //application name to generate
        flags = ShareConstants.TINKER_ENABLE_ALL)
public class AppLike extends DefaultApplicationLike {

    public AppLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime,
            Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }
}

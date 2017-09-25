package com.guang.wang.openapplication.roll;

/**
 * Created by wangguang.
 * Date:2017/9/22
 * Description:
 */

public class RollHelper {

    static IRollRootHandler mIRollRootHandler;

    public static IRollRootHandler getRootHandler() {
        if (mIRollRootHandler == null) {
            mIRollRootHandler = new RootHandler();
        }
        return mIRollRootHandler;
    }

    public static void clear(){
        mIRollRootHandler=null;
    }

}

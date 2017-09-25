package com.wuba.client.roll;

/**
 * Created by wangguang.
 * Date:2017/9/22
 * Description:
 */

public class LeafHandler extends BaseHandler {

    String key;

    public LeafHandler(RootHandler handler, String key) {
        super(handler);
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }

}

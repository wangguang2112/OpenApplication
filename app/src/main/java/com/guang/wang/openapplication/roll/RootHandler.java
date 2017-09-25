package com.guang.wang.openapplication.roll;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangguang.
 * Date:2017/9/22
 * Description:
 */

public class RootHandler extends BaseHandler implements IRollRootHandler {

    private static final String ROOT_KEY = "root";

    private Map<String, IRollHandler> tempList = new LinkedHashMap<>();

    private List<String> keyList = new ArrayList<>();

    protected OnDialogRollListener onRollListener;

    public RootHandler() {
        super(null);
    }

    @Override
    public String getKey() {
        return ROOT_KEY;
    }

    public synchronized void acceptHandler(String key, IRollHandler handler) {
        Log.d(TAG, getKey() + "  acceptHandler() key = [" + key + "], handler = [" + handler + "]");
        if (!nextHandler(handler)) {
            IRollHandler next = this.getNextHandler();
            IRollHandler prev = next;
            while (next != null) {
                prev = next;
                next = next.getNextHandler();
            }
            prev.nextHandler(handler);
        }
    }

    @Override
    public RollTransaction beginChildTransaction(String key) {
        Log.d(TAG, getKey() + "  beginChildTransaction() key = [" + key + "]");
        if (tempList.containsKey(key)) {
            return tempList.get(key).beginTransaction();
        } else {
            tempList.put(key, new LeafHandler(this, key));
            return tempList.get(key).beginTransaction();
        }
    }

    @Override
    public void trigger(String key) {
        Log.d(TAG, getKey() + "  trigger() key = [" + key + "]");
        if (!keyList.contains(key) && tempList.containsKey(key)) {
            keyList.add(key);
            acceptHandler(key, tempList.get(key));
        } else if (keyList.contains(key) && tempList.containsKey(key)) {
            IRollHandler handler = tempList.get(key);
            handler.start();
        }
    }

    @Override
    public void clearHandler(String key) {
        Log.d(TAG, getKey() + "  clearHandler() key = [" + key + "]");
        tempList.remove(key);
    }

    @Override
    public void setOnRollListener(OnDialogRollListener onRollListener) {
        this.onRollListener = onRollListener;
    }

    @Override
    protected void touchTail() {
        super.touchTail();
        if (nextHandler != null && onRollListener != null) {
            onRollListener.onHandlerChange(this.getKey(), nextHandler.getKey());
        }
    }
}

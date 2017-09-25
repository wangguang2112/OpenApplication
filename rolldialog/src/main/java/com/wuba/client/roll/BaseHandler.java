package com.wuba.client.roll;

import com.wuba.client.roll.inter.Chain;
import com.wuba.client.roll.inter.IRollDialog;
import com.wuba.client.roll.inter.IRollHandler;

import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wangguang.
 * Date:2017/9/21
 * Description:
 */

public abstract class BaseHandler implements IRollHandler, Comparator<IRollDialog> {

    private RootHandler root;

    private List<IRollDialog> mBaseRollDialogs = new LinkedList<>();

    private Chain head = null;

    private Chain now = null;

    private boolean isStop = false;

    private Chain stopChain = null;

    protected IRollHandler nextHandler;

    private boolean touchTail = false;

    public BaseHandler(RootHandler root) {
        this.root = root;
    }

    @Override
    public void start() {
        Log.d(TAG, getKey() + "  start()");
        if (touchTail) {
            return;
        } else if (stopChain != null) {
            stopChain.next();
            stopChain = null;
        } else if (now != null) {
            now.next();
        } else if (head != null) {
            head.next();
        }
    }

    @Override
    public RollTransaction beginTransaction() {
        Log.d(TAG, getKey() + "  beginTransaction()");
        return new RootTransaction();
    }

    @Override
    public int compare(IRollDialog lhs, IRollDialog rhs) {
        return lhs.priority() - rhs.priority();
    }

    private void processChain() {
        Log.d(TAG, getKey() + "  processChain()");
        head = new RealChain(-1);
    }

    @Override
    public void stop() {
        Log.d(TAG, getKey() + "  stop()");
        isStop = true;
    }


    @Override
    public void preload() {
        Log.d(TAG, getKey() + "  preload()");
        for (IRollDialog dialog : mBaseRollDialogs) {
            dialog.preload();
        }
    }

    protected void touchTail() {
        Log.d(TAG, getKey() + "  touchTail()");
        touchTail = true;
        if (nextHandler != null) {
            nextHandler.start();
            if (root != null && root.onRollListener != null) {
                root.onRollListener.onHandlerChange(this.getKey(), nextHandler.getKey());
            }
        } else {
            if (root != null && root.onRollListener != null) {
                root.onRollListener.onTouchEnd(this.getKey());
            }
        }
        if (root != null) {
            root.clearHandler(getKey());
        }
    }

    @Override
    public boolean nextHandler(IRollHandler handler) {
        Log.d(TAG, getKey() + "  nextHandler() handler = [" + handler + "]");
        if (nextHandler != null) {
            if (!touchTail) {
                nextHandler.start();
            }
            return false;

        }
        nextHandler = handler;
        if (nextHandler != null && touchTail == true) {
            nextHandler.start();
        }
        return true;
    }

    @Override
    public void reset() {
        Log.d(TAG, getKey() + "  reset()");
        touchTail = false;
        if (nextHandler != null) {
            nextHandler.reset();
        }
        for (IRollDialog dialog : mBaseRollDialogs) {
            dialog.reset();
        }
    }

    @Override
    public String toString() {
        return getKey();
    }

    @Override
    public IRollHandler getNextHandler() {
        return nextHandler;
    }

    private class RootTransaction implements RollTransaction {

        private List<IRollDialog> tempDialogs = new LinkedList<>();

        private List<IRollDialog> removeDialogs = new LinkedList<>();

        @Override
        public RollTransaction add(IRollDialog dialog) {
            Log.d(TAG, "add() dialog = [" + dialog + "]");
            tempDialogs.add(dialog);
            return this;
        }

        @Override
        public RollTransaction remove(IRollDialog dialog) {
            if (!tempDialogs.remove(dialog)) {
                removeDialogs.add(dialog);
            }
            return this;
        }

        @Override
        public RollTransaction commit() {
            Log.d(TAG, "commit()");
            mBaseRollDialogs.removeAll(removeDialogs);
            mBaseRollDialogs.addAll(tempDialogs);
            Collections.sort(mBaseRollDialogs, BaseHandler.this);
            processChain();
            return this;
        }
    }

    private class RealChain implements Chain {

        int index = 0;

        public RealChain(int index) {
            this.index = index;
        }

        @Override
        public void next() {
            Log.d(TAG, "next()");
            if (isStop) {
                stopChain = this;
                isStop = false;
                return;
            }
            //达到末尾以后不再触发
            if (touchTail) {
                touchTail();
                return;
            }
            if (index + 1 < mBaseRollDialogs.size()) {
                IRollDialog dialog = mBaseRollDialogs.get(index + 1);
                Chain nextChain = new RealChain(index + 1);
                dialog.setChain(nextChain);
                if (!dialog.showDialog()) {
                    now = this;
                }
            } else {
                touchTail();
            }
        }

        @Override
        public void stop() {
            Log.d(TAG, "stop()");
//            mBaseRollDialogs.remove(index);
            isStop = true;
        }
    }


}

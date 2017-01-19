//package com.guang.wang.openapplication.sundry;
//
//import android.content.Context;
//import android.content.res.Configuration;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Point;
//import android.graphics.Rect;
//import android.os.Binder;
//import android.os.IBinder;
//import android.os.Looper;
//import android.view.Display;
//import android.view.Gravity;
//import android.view.InputEvent;
//import android.view.MotionEvent;
//import android.view.Surface;
//import android.view.View;
//import android.view.WindowManager;
//
///**
// * Created by wangguang.
// * Date:2016/12/21
// * Description:
// */
//
//public class SampleWindow {
//
//    public static void main(String[] args) {
//        try {
//            new SampleWindow().Run();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    IWindowSession mSession = null;
//
//    InputChannel mInputChannel = new InputChannel();
//
//    Rect mInsets = new Rect();
//
//    Rect mFrame = new Rect();
//
//    Rect mVisibleInsets = new Rect();
//
//    Configuration mConfig = new Configuration();
//
//    Surface mSurface = new Surface();
//
//    Paint mPaint = new Paint();
//
//    IBinder mToken = new Binder();
//
//    MyWindow mWindow = new MyWindow();
//
//    WindowManager.LayoutParams mLp = new WindowManager.LayoutParams();
//
//    Choreographer mChoreographer = null;
//
//    InputHandler mInputHandler = null;
//
//    boolean mContinueAnime = true;
//
//    public void Run() throws Exception {
//        IWindowManager wms = IWindowManager.Stub.asInterface(
//                ServiceManager.getService(Context.WINDOW_SERVICE));
//        mSession = WindowManagerGlobal.getWindowSession(Looper.myLooper());
//        IDisplayManager dm = IDisplayManager.Stub.asInterface(
//                ServiceManager.getService(Context.DISPLAY_SERVICE));
//        DisplayInfo di = dm.getDisplayInfo(Display.DEFAULT_DISPLAY);
//        Point scrnSize = new Point(di.appWidth, di.appHeight);
//        initLayoutParams(scrnSize);
//        installWindow(wms);
//        mChoreographer = Choreographer.getInstance();
//        scheduleNextFrame();
//        Looper.loop();
//        mContinueAnime = false;
//        uninstallWindow(wms);
//    }
//
//
//    public void initLayoutParams(Point screenSize) {
//        // 标记即将安装的窗口类型为SYSTEM_ALERT， 这将使得窗口的ZOrder顺序比较靠前
//        mLp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
//        mLp.setTitle("SampleWindow");
//        // 设定窗口的左上角坐标以及高度和宽度
//        mLp.gravity = Gravity.LEFT | Gravity.TOP;
//        mLp.x = screenSize.x / 4;
//        mLp.y = screenSize.y / 4;
//        mLp.width = screenSize.x / 2;
//        mLp.height = screenSize.y / 2;
//        // 和输入事件相关的Flag， 希望当输入事件发生在此窗口之外时， 其他窗口也可以接受输入事件
//        mLp.flags = mLp.flags | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
//    }
//
//    public void installWindow(IWindowManager wms) throws Exception {
//        // 首先向WMS声明一个Token， 任何一个Window都需要隶属与一个特定类型的Token
//        wms.addWindowToken(mToken, WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//        // 设置窗口所隶属的Token
//        mLp.token = mToken;
//        // 通过IWindowSession将窗口安装进WMS， 注意， 此时仅仅是安装到WMS， 本例的Window
//        // 目前仍然没有有效的Surface。 不过， 经过这个调用后， mInputChannel已经可以用来接受
//        // 输入事件了
//        mSession.add(mWindow, 0, mLp, View.VISIBLE, mInsets, mInputChannel);
//        /*通过IWindowSession要求WMS对本窗口进行重新布局， 经过这个操作后， WMS将会为窗口
//        创建一块用于绘制的Surface并保存在参数mSurface中。 同时， 这个Surface被WMS放置在
//        LayoutParams所指定的位置上 */
//        mSession.relayout(mWindow, 0, mLp, mLp.width, mLp.height, View.VISIBLE,
//                0, mFrame, mInsets, mVisibleInsets, mConfig, mSurface);
//        if (!mSurface.isValid()) {
//            throw new RuntimeException("Failed creating Surface.");
//        }
//        // 基于WMS返回的InputChannel创建一个Handler， 用于监听输入事件
//        //mInputHandler一旦被创建， 就已经在监听输入事件了
//        mInputHandler = new InputHandler(mInputChannel, Looper.myLooper());
//    }
//
//    public void uninstallWindow(IWindowManager wms) throws Exception {
//        // 从WMS处卸载窗口
//        mSession.remove(mWindow);
//        // 从WMS处移除之前添加的Token
//        wms.removeWindowToken(mToken);
//    }
//
//    public void scheduleNextFrame() {
//        // 要求在显示系统刷新下一帧时回调mFrameRender， 注意， 只回调一次
//        mChoreographer.postCallback(Choreographer.CALLBACK_ANIMATION
//                , mFrameRender, null);
//    }
//
//    // 这个Runnable对象用以在窗口上描绘一帧
//    public Runnable mFrameRender = new Runnable() {
//        @Override
//        public void run() {
//            try {
//                // 获取当期时间戳
//                long time = mChoreographer.getFrameTime() % 1000;
//                // 绘图
//                if (mSurface.isValid()) {
//                    Canvas canvas = mSurface.lockCanvas(null);
//                    canvas.drawColor(Color.DKGRAY);
//                    canvas.drawRect(2 * mLp.width * time / 1000
//                            - mLp.width, 0, 2 * mLp.width * time
//                            / 1000, mLp.height, mPaint);
//                    mSurface.unlockCanvasAndPost(canvas);
//                    mSession.finishDrawing(mWindow);
//                }
//                if (mContinueAnime) {
//                    scheduleNextFrame();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    };
//
//    // 定义一个类继承InputEventReceiver， 用以在其onInputEvent()函数中接收窗口的输入事件
//    class InputHandler extends InputEventReceiver {
//
//        Looper mLooper = null;
//
//        public InputHandler(InputChannel inputChannel, Looper looper) {
//            super(inputChannel, looper);
//            mLooper = looper;
//        }
//
//        @Override
//        public void onInputEvent(InputEvent event) {
//            if (event instanceof MotionEvent) {
//                MotionEvent me = (MotionEvent) event;
//                if (me.getAction() == MotionEvent.ACTION_UP) {
//                    // 退出程序
//                    mLooper.quit();
//                }
//            }
//            super.onInputEvent(event);
//        }
//    }
//
//    // 实现一个继承自IWindow.Stub的类MyWindow。
//    class MyWindow extends IWindow.Stub {
//        // 保持默认的实现即可
//    }
//
//}
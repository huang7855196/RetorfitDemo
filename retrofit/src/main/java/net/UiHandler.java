package net;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

/**
 * desc:
 * Created by huangxy on 2018/7/30.
 */
public class UiHandler {
    private static Handler sHandler;

    private UiHandler() {
    }

    public static void post(Runnable r) {
        prepare();
        if(Looper.myLooper() == Looper.getMainLooper()) {
            r.run();
        } else {
            sHandler.post(r);
        }

    }

    public static void post(Runnable r, Object token) {
        prepare();
        if(Looper.myLooper() == Looper.getMainLooper()) {
            r.run();
        } else {
            sHandler.postAtTime(r, token, SystemClock.uptimeMillis());
        }

    }

    public static void postDelayed(Runnable r, long delay) {
        prepare();
        sHandler.postAtTime(r, SystemClock.uptimeMillis() + delay);
    }

    public static void postDelayed(Runnable r, Object token, long delay) {
        prepare();
        Message message = Message.obtain(sHandler, r);
        message.obj = token;
        sHandler.postAtTime(r, token, SystemClock.uptimeMillis() + delay);
    }

    public static void remove(Object token) {
        prepare();
        sHandler.removeCallbacksAndMessages(token);
    }

    private static synchronized void prepare() {
        if(sHandler == null) {
            sHandler = new Handler(Looper.getMainLooper());
        }

    }
}

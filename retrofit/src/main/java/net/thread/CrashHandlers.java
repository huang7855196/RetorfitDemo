package net.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * 多个UncaughtExceptionHandler的处理集合，防止互相覆盖
 * author:caowy
 * date:2016-12-28
 */
public class CrashHandlers implements Thread.UncaughtExceptionHandler {

    public static CrashHandlers getInstance() {
        return InstanceHolder.instance;
    }

    /**
     * 单例
     * author:caowy
     * date:2016-12-28
     */
    private static class InstanceHolder {
        /**
         *
         */
        private static final CrashHandlers instance = new CrashHandlers();
    }

    private final List<Thread.UncaughtExceptionHandler> mHandlers = new ArrayList<>();

    private final Thread.UncaughtExceptionHandler mDefaultHandler;

    private CrashHandlers() {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 添加崩溃处理
     * author:caowy
     * date:2016-12-28
     *
     * @param handler
     * @return
     */
    public boolean addHandler(Thread.UncaughtExceptionHandler handler) {
        return mHandlers.add(handler);
    }

    /**
     * 移除崩溃处理
     * author:caowy
     * date:2016-12-28
     *
     * @param handler
     * @return
     */
    public boolean removeHandler(Thread.UncaughtExceptionHandler handler) {
        return mHandlers.remove(handler);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        for (Thread.UncaughtExceptionHandler handler : mHandlers) {
            handler.uncaughtException(thread, ex);
        }
        if(mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }
}

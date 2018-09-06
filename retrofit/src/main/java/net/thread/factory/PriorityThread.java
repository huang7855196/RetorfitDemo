package net.thread.factory;

/**
 * Created by zhouL on 2016/2/9.
 */
public class PriorityThread extends Thread {

    /** 默认应用的优先级 */
    public static final int THREAD_PRIORITY_DEFAULT = 0;

    /** 有效的线程最低的优先级 */
    public static final int THREAD_PRIORITY_LOWEST = 19;

    /** 标准后台程序 */
    public static final int THREAD_PRIORITY_BACKGROUND = 10;

    /** 标准前台线程优先级 */
    public static final int THREAD_PRIORITY_FOREGROUND = -2;

    /** 标准显示系统优先级，主要是改善UI的刷新 */
    public static final int THREAD_PRIORITY_DISPLAY = -4;

    /** 标准较重要显示优先级，对于输入事件同样适用 */
    public static final int THREAD_PRIORITY_URGENT_DISPLAY = -8;

    /** 标准音乐播放使用的线程优先级 */
    public static final int THREAD_PRIORITY_AUDIO = -16;

    /** 标准较重要音频播放优先级 */
    public static final int THREAD_PRIORITY_URGENT_AUDIO = -19;

    /** 高于favorable */
    public static final int THREAD_PRIORITY_MORE_FAVORABLE = -1;

    /** 低于favorable */
    public static final int THREAD_PRIORITY_LESS_FAVORABLE = 1;

    /**
     * 同步android.os.Process下的常量，并加入注释（如：android.os.Process.THREAD_PRIORITY_DEFAULT）
     */
    private int mOSPriority = THREAD_PRIORITY_DEFAULT;


    public PriorityThread(Runnable r) {
        super(r);
    }

    public void setOSPriority(int p) {
        mOSPriority = p;
    }

    @Override
    public void run() {
        //设置线程优先级为后台，这样当多个线程并发后很多无关紧要的线程分配的CPU时间将会减少，有利于主线程的处理
        android.os.Process.setThreadPriority(mOSPriority);
        super.run();
    }

}

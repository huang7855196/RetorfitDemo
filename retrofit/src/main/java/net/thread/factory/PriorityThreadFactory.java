package net.thread.factory;

import java.util.concurrent.ThreadFactory;

/**
 * 创建原始线程
 * Created by zhouL on 2016/2/9.
 */
public class PriorityThreadFactory {

    /** 创建一个普通线程 */
    public static ThreadFactory createNormPriorityThread(){
        return new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                PriorityThread t = new PriorityThread(r);
                t.setOSPriority(PriorityThread.THREAD_PRIORITY_BACKGROUND);
                t.setPriority(Thread.NORM_PRIORITY);
                return t;
            }
        };
    }

    /** 创建一个优先级最低线程 */
    public static ThreadFactory createMinPriorityThread(){
        return new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                PriorityThread t = new PriorityThread(r);
                t.setOSPriority(PriorityThread.THREAD_PRIORITY_LOWEST);
                t.setPriority(Thread.MIN_PRIORITY);
                return t;
            }
        };
    }

    /** 创建一个优先级最高线程 */
    public static ThreadFactory createMaxPriorityThread(){
        return new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                PriorityThread t = new PriorityThread(r);
                t.setOSPriority(PriorityThread.THREAD_PRIORITY_DISPLAY);
                t.setPriority(Thread.MAX_PRIORITY);
                return t;
            }
        };
    }
}

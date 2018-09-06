package net.thread;


import net.thread.factory.PriorityThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhouL on 2016/2/9.
 */
public class ExecutorServiceHelp {

    /**
     * 界面数据加载，最高优先级
     */
    private static ExecutorService displayExecutor;

    /**
     * 默认加载，一般优先级，用于下载之类的
     */
    private static ExecutorService defaultExecutor;

    /**
     * 最新优先级，数据同步等
     */
    private static ExecutorService lowestExecutor;

    /**
     * 网络线程池
     */
    private static ExecutorService httpExecutor;

    public synchronized static void executeDisplay(Runnable runnable){
        if(displayExecutor == null){
            displayExecutor = Executors.newCachedThreadPool(PriorityThreadFactory.createMaxPriorityThread());
        }

        displayExecutor.execute(runnable);
    }

    public static synchronized void executeBackground(Runnable runnable){
        if(defaultExecutor == null){
            defaultExecutor = Executors.newCachedThreadPool(PriorityThreadFactory.createNormPriorityThread());
        }
        defaultExecutor.execute(runnable);
    }

    public static synchronized void executeLowest(Runnable runnable){
        if(lowestExecutor == null){
            lowestExecutor = Executors.newCachedThreadPool(PriorityThreadFactory.createMinPriorityThread());
        }
        lowestExecutor.execute(runnable);
    }

    public static synchronized ExecutorService getHttpExecutor() {
        if(httpExecutor == null) {
            httpExecutor = Executors.newCachedThreadPool(PriorityThreadFactory.createMaxPriorityThread());
        }
        return httpExecutor;
    }
}

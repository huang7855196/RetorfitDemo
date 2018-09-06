package net.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * description:单线程实例
 * @author: jinyuef
 * @Create Time:2016/3/19
 * @version:
 */
public class SingleThreadExecutorManager {
	
	private static ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
	
	public static void execute(Runnable r){
		
		singleThreadExecutor.execute(r);
		
	}

}

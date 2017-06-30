package com.ehome.core.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程工具类
 * 
 * @Title:ThreadUtils
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月21日 下午4:11:46
 * @version:
 */
public final class ThreadUtils {
	private ThreadUtils() {
	}

	/**
	 * 创建CPU数量的一半大小的线程池
	 * 
	 * @return
	 */
	public static ExecutorService newFixedThreadPool() {
		return Executors.newFixedThreadPool(Math.max(Runtime.getRuntime()
				.availableProcessors() >> 1, 1));
	}
}

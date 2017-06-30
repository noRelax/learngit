package com.ehome.core.util;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.ehome.core.frame.BusinessException;

/**
 * 业务操作重试工具类
 * 
 * @Title:RetryUtils
 * @Description:TODO
 * @author:张钟武
 * @date:2017年4月28日 上午11:10:23
 * @version:
 */
public final class RetryUtils {

	private final static Map<String, Integer> count = new ConcurrentHashMap<>();

	private RetryUtils() {

	}

	public interface IRetryAction {
		void action() throws BusinessException;
	}

	/**
	 * 重试执行
	 * 
	 * @param retryCount
	 * @param interval
	 * @param timeUnit
	 * @param throwIfFail
	 * @param function
	 * @throws Exception
	 */
	public static void retry(int retryCount, long interval, TimeUnit timeUnit,
			boolean throwIfFail, IRetryAction function)
			throws BusinessException {
		if (function == null) {
			return;
		}
		for (int i = 0; i < retryCount; i++) {
			try {
				function.action();
				//retryCount--;
				break;
			} catch (BusinessException e) {
				if (i == retryCount - 1) {
					if (throwIfFail) {
						count.put("i_" + UUID.randomUUID(), i);
						throw e;
					} else {
						break;
					}
				} else {
					if (timeUnit != null && interval > 0L) {
						try {
							timeUnit.sleep(interval);
						} catch (InterruptedException e1) {
							//LOGGER.error(e1.getMessage(), e1);
						}
					}
				}
			}
		}
	}

	/**
	 * 有间隔的重试
	 * 
	 * @param retryCount
	 * @param interval
	 * @param timeUnit
	 * @param handler
	 * @throws Exception
	 */
	public static void retry(int retryCount, long interval, TimeUnit timeUnit,
			IRetryAction handler) throws BusinessException {
		retry(retryCount, interval, timeUnit, false, handler);
	}

	/**
	 * 不间隔重试
	 * 
	 * @param retryCount
	 * @param function
	 * @throws Exception
	 */
	public static void retry(int retryCount, IRetryAction function)
			throws BusinessException {
		retry(retryCount, -1, null, function);
	}

	/**
	 * //TODO 不间隔重试并抛出异常
	 * @param retryCount
	 * @param throwIfFail
	 * @param function
	 * @throws BusinessException
	 */
	public static void retry(int retryCount, boolean throwIfFail,
			IRetryAction function) throws BusinessException {
		retry(retryCount, -1, null, throwIfFail, function);
	}

	public static int getSize() {
		return count.size();
	}

}

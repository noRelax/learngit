package com.ehome.core.frame;

/**
 * 统一异常包装类
 * 
 * @Title:BusinessException
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月20日 上午11:06:24
 * @version:
 */
public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1693400610401864939L;

	public BusinessException() {
		super();
	}

	/**
	 * @param message
	 *            消息详细信息
	 * @param cause
	 *            原因
	 * @param enableSuppression
	 *            启用或禁用抑制
	 * @param writableStackTrace
	 *            堆栈跟踪是否可写
	 */
	public BusinessException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 *            消息详细信息
	 * @param cause
	 *            原因
	 */
	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 *            消息详细信息
	 */
	public BusinessException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 *            原因
	 */
	public BusinessException(Throwable cause) {
		super(cause);
	}

}

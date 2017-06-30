package com.ehome.activiti.general;

/**
 * 错误码类
 * 
 * @ClassName Code
 * @Description TODO
 * @author kokjuis 189155278@qq.com
 * @date 2016-8-18
 * @content
 */
public class Code {

	// 操作结果
	public static final int SUCCESS = 10000;// 操作成功
	public static final int FAIL = 10001;// 操作失败

	/**
	 * 错误码
	 */
	public static final int PARAMETER_LOST = 10002;// 参数丢失

	public static final int NO_USER = 10003;// 不存在该用户

	public static final int PASSWORD_MISTAKE = 10004;// 密码错误

	public static final int IS_USER = 10005;// 用户已经存在

	public static final int SERVER_EXCEPTION = 10006;// 服务器异常

}

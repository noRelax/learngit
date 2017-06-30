package com.ehome.core.shiro.cons;

/**
 * 会话常量
 * 
 * @Title:SessionCons
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月10日 下午2:05:05
 * @version:
 */
public interface SessionCons {

	/**
	 * 后端用户
	 */
	String LOGIN_USER_ID = "session_login_user_id";

	String LOGIN_USER_PERMISSIONS = "session_login_user_permissions";

	String LOGIN_USER_ROLES = "session_login_user_roles";

	String LOGIN_USER_SESSION = "session_login_user";

	String TOKEN_PREFIX = "web_session_key-";
	
	String TOKEN_PREFIX_KEY = "web_session_key-*";
	
	String DEVICE_TYPE = "device_type";

	/**
	 * App用户
	 */

	String APP_LOGIN_USER_SESSION = "app_session_login_user";

	String APP_LOGIN_USER_ID = "app_login_user_id";

}

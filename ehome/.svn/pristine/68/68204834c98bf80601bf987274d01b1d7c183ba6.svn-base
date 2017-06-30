package com.ehome.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.mgt.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Web端权限拦截器 暂未实现
 * 
 * @Title:WebAuthorizing
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月21日 下午12:22:11
 * @version:
 */
public class WebAuthorizing extends HandlerInterceptorAdapter {
	@Autowired
	private SecurityManager securityManager;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			// HandlerMethod handlerMethod = (HandlerMethod) handler;
//			String redirectUrl;
//			SecurityUtils.setSecurityManager(securityManager);
//			Subject user = SecurityUtils.getSubject();
//			Session session = user.getSession();
//			if (null == session.getAttribute(SessionCons.LOGIN_USER_SESSION)) {
//				if ("XMLHttpRequest".equals(request
//						.getHeader("X-Requested-With"))) {
//					redirectUrl = request.getContextPath()
//							+ "/system/user/index.html";
//					response.setCharacterEncoding("UTF-8");
//					response.setContentType("application/json; charset=utf-8");
//				} else {
//					redirectUrl = request.getContextPath()
//							+ "/system/user/index.html";
//					response.sendRedirect(redirectUrl);
//				}
//				return false;
//			}
		}
		return true;
	}
}

package com.ehome.core.shiro;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSON;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.model.AjaxResult;
import com.ehome.core.util.StringUtils;

/**
 * 扩展自FormAuthenticationFilter过滤器 只覆盖父类的登录URL跳转方法,以兼容APP登录异常捕获
 * 
 * @Title:CustomerFormAuthenticationFilter
 * @Description:TODO
 * @author:张钟武
 * @date:2017年3月7日 上午10:46:37
 * @version:
 */
public class CustomerFormAuthenticationFilter extends FormAuthenticationFilter {

	private final static Logger logger = LoggerFactory
			.getLogger(CustomerFormAuthenticationFilter.class);

	private final static String TOKEN = "Cookie";

	@Autowired
	private transient RedisTemplate<Serializable, Session> redisTemplate;

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		boolean isLogin = false;
		String device = httpRequest.getHeader("device");
		// 如果是客户端是H5
		if (StringUtils.isNotEmpty(device) && device.equals("H5")) {
			String h5Token = httpRequest.getHeader("token");
			Cookie[] cookies = httpRequest.getCookies();
			if (null != cookies) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("token")) {
						cookie.setValue(h5Token);
					}
				}
			}
			isLogin = isH5Login(h5Token);
		} else {
			// 如果是APP或者PC端
			Subject subject = getSubject(request, response);
			isLogin = subject.isAuthenticated();
		}
		return isLogin;
	}

	// @Override
	// protected boolean onAccessDenied(ServletRequest request, ServletResponse
	// response) throws Exception {
	// System.out.println("2222222222222222222222222222222222222222222");
	// if (isLoginRequest(request, response)) {
	// if (isLoginSubmission(request, response)) {
	// if (logger.isTraceEnabled()) {
	// logger.trace("Login submission detected.  Attempting to execute login.");
	// }
	// return executeLogin(request, response);
	// } else {
	// if (logger.isTraceEnabled()) {
	// logger.trace("Login page view.");
	// }
	// //allow them to see the login page ;)
	// return true;
	// }
	// } else {
	// if (logger.isTraceEnabled()) {
	// logger.trace("Attempting to access a path which requires authentication.  Forwarding to the "
	// +
	// "Authentication url [" + getLoginUrl() + "]");
	// }
	// saveRequestAndRedirectToLogin(request, response);
	// return false;
	// }
	// }
	// @Override
	// protected void saveRequestAndRedirectToLogin(ServletRequest request,
	// ServletResponse response) throws IOException {
	// saveRequest(request);
	// redirectToLogin(request, response);
	// }

	@Override
	protected void redirectToLogin(ServletRequest request,
			ServletResponse response) throws IOException {
		String loginUrl = getLoginUrl();
		if (logger.isDebugEnabled()) {
			logger.debug("客户端登录的URL:{}", loginUrl);
		}
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setContentType("text/html; charset=utf-8");
		httpRequest.setCharacterEncoding("UTF-8");
		// 是否为APP登录请求
		if (StringUtils.isNotEmpty(httpRequest.getHeader("device"))
				&& (httpRequest.getHeader("device").equals("APP") || httpRequest
						.getHeader("device").equals("H5"))) {
			String token = httpRequest.getHeader(TOKEN);
			if (logger.isDebugEnabled()) {
				logger.debug("客户端设备:{},token:{}",
						httpRequest.getHeader("device"), token);
			}
			if (StringUtils.isBlank(token)) {
				AjaxResult result = new AjaxResult(
						String.valueOf(ResponseCode.token_not_exist.getCode()),
						"认证失败!", ResponseCode.token_not_exist.getMsg());
				httpResponse.getWriter().append(JSON.toJSONString(result));
				httpResponse.getWriter().flush();
				httpResponse.getWriter().close();
			} else {
				AjaxResult result = new AjaxResult(
						String.valueOf(ResponseCode.app_unknow_account
								.getCode()), "认证失败!",
						ResponseCode.app_unknow_account.getMsg());
				httpResponse.getWriter().append(JSON.toJSONString(result));
				httpResponse.getWriter().flush();
				httpResponse.getWriter().close();
			}
		} else {
			// PC跳转 如果是非Ajax请求 按默认的配置跳转到登录页面
			if (!"XMLHttpRequest".equalsIgnoreCase(httpRequest
					.getHeader("X-Requested-With"))) {// 不是ajax请求
				WebUtils.issueRedirect(request, response, loginUrl);
			} else {
				// 如果是Aajx请求,则返回会话失效的JSON信息
				AjaxResult result = new AjaxResult(
						String.valueOf(ResponseCode.session_unvaildate
								.getCode()), "请求失败!",
						ResponseCode.session_unvaildate.getMsg());
				httpResponse.getWriter().append(JSON.toJSONString(result));
				httpResponse.getWriter().flush();
				httpResponse.getWriter().close();
			}
		}
	}

	/**
	 * 判断H5是否登录
	 * 
	 * @param token
	 * @return
	 */
	protected boolean isH5Login(String token) {
		Session session = redisTemplate.opsForValue().get(token);
		if(logger.isDebugEnabled())
			logger.debug("H5刷新会话");
		if (null != session) {
			session.setTimeout(24 * 60 * 60 * 1000);
			redisTemplate.opsForValue().set(session.getId(), session,
					24 * 60 * 60, TimeUnit.SECONDS);
			return true;
		} else {
			return false;
		}
	}
}

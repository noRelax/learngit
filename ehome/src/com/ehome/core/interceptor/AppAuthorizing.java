package com.ehome.core.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.model.AjaxResult;
import com.ehome.core.shiro.security.dto.CacheDto;
import com.ehome.core.util.redis.RedisTemplate;

/**
 * App端权限拦截器 暂未实现
 * 
 * @Title:AppAuthorizing
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月21日 下午12:22:28
 * @version:
 */
public class AppAuthorizing extends HandlerInterceptorAdapter {

	private final static Logger logger = Logger.getLogger(AppAuthorizing.class);

	private final static String TOKEN = "token";

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			response.setContentType("text/html; charset=utf-8");
			request.setCharacterEncoding("UTF-8");
			if (logger.isDebugEnabled()) {
				logger.debug("App端账户登录拦截");
			}
			// 从header中取出token
			String token = request.getHeader(TOKEN);
			if (StringUtils.isBlank(token)) {
				AjaxResult result = new AjaxResult(
						String.valueOf(ResponseCode.token_not_exist.getCode()),
						"认证失败!", ResponseCode.token_not_exist.getMsg());
				response.getWriter().append(JSON.toJSONString(result));
				response.getWriter().flush();
				response.getWriter().close();
				return false;
			} else {
				HandlerMethod handlerMethod = (HandlerMethod) handler;
				Method method = handlerMethod.getMethod();
				if (method.isAnnotationPresent(RequiresUser.class)) {
					if (isAccess(token)) {
						return true;
					} else {
						AjaxResult result = new AjaxResult(
								String.valueOf(ResponseCode.app_unknow_account
										.getCode()), "认证失败!",
								ResponseCode.app_unknow_account.getMsg());
						response.getWriter().append(JSON.toJSONString(result));
						response.getWriter().flush();
						response.getWriter().close();
						return false;
					}
				} else {
					return true;
				}
			}
		}
		return true;
	}

	/**
	 * 判断token的合法性
	 * 
	 * @param token
	 * @return
	 */
	public boolean isAccess(String token) {
		if (StringUtils.isNotBlank(token)) {
			CacheDto cacheDto = RedisTemplate.get(token);
			if (null != cacheDto)
				return true;
			else
				return false;
		} else {
			return false;
		}
	}
}

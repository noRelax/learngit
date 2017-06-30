package com.ehome.core.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ehome.core.dict.ResponseCode;
import com.ehome.core.model.AjaxResult;

/**
 * Shiro认证授权过滤器 (已废弃不用)
 * 
 * @Title:ShiroAuthenticationFilter
 * @Description:TODO
 * @author:张钟武
 * @date:2017年1月21日 下午3:08:31
 * @version:
 */
public class ShiroAuthenticationFilter extends PassThruAuthenticationFilter {

	private static final Logger logger = LoggerFactory
			.getLogger(ShiroAuthenticationFilter.class);

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("===================="
					+ ((HttpServletRequest) request).getRequestURI());
		}
		if (isLoginRequest(request, response)) {
			return true;
		} else {
			saveRequest(request);
			if (((HttpServletRequest) request).getHeader("Accept").contains(
					"application/json")) {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json;charset=UTF-8");
				AjaxResult result = new AjaxResult(
						ResponseCode.success.getCode(),
						ResponseCode.success.getMsg(), null);
				response.getWriter().append(
						JSONObject.fromObject(result).toString());
				response.getWriter().flush();
				response.getWriter().close();
			} else {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=UTF-8");
				((HttpServletResponse) response)
						.sendRedirect(((HttpServletRequest) request)
								.getContextPath() + "/admin/index.html");
			}
			return false;
		}
	}
}

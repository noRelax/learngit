package com.ehome.core.frame;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ehome.cloud.common.service.CommonService;
import com.ehome.cloud.sys.dto.AppLoginInfoDto;
import com.ehome.cloud.sys.dto.LoginInfoDto;
import com.ehome.cloud.sys.model.UserModel;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.model.AjaxResult;
import com.ehome.core.shiro.cons.SessionCons;
import com.ehome.core.util.ClassUtil;
import com.ehome.core.util.NumberUtils;
import com.ehome.core.util.PageData;
import com.ehome.core.util.RSA;
import com.ehome.core.util.SqlUtil;
import com.ehome.core.util.StringUtils;

public class BaseController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	// 默认通过controller 获得 对应操作的表名
	public String table = "";

	public String controller = "";

	public PageData getPageData() {
		return new PageData(this.getRequest());
	}

	public BaseController() {
		this.table = SqlUtil.getPrefixTable(this.getClass().getName());
		this.controller = ClassUtil.getName(this.getClass().getName());
	}

	/**
	 * 解密
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 *
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> decode(Map<String, Object> map) throws Exception {
		try {
			String data = map.get("data") == null ? "" : map.get("data")
					.toString();
			// 获取服务端存储的私钥
			String decodeData = RSA.decryptPrivateKey(data);
			Map<String, Object> mapFromJson = JSON.parseObject(decodeData,
					Map.class);
			map.clear();
			map.putAll(mapFromJson);
			/**
			 * 分页参数
			 */
			int rows = NumberUtils.toInt(map.get("rows") == null ? "10" : map
					.get("rows").toString(), 10);
			int page = NumberUtils.toInt(map.get("page") == null ? "1" : map
					.get("page").toString(), 1); // 每页显示条数
			map.put("rows", rows);
			map.put("page", page);
		} catch (Exception e) {
			throw new Exception("解密出错");
		}
		return map;

	}

	/**
	 * @return the table
	 */
	public String getTable() {
		return this.table;
	}

	/**
	 * @param table
	 *            the table to set
	 */
	public void setTable(String table) {
		this.table = table;
	}

	/**
	 * @param table
	 *            the table to set
	 */
	public void setTable(String table, String prefix) {
		this.table = prefix + table;
	}

	@Resource(name = "commonService")
	private CommonService commonService;

	/**
	 * 通过request的参数与数据库字段对比得到可以操作数据库的字段和值
	 * 
	 * @return entity
	 */
	public Map create() {
		Map entity = new HashMap();
		try {
			List columns = commonService.columnsList(table);
			Map requestMap = this.getRequestParams();
			for (Object o : requestMap.keySet()) {
				String key = StringUtils.obj2String(o, "");
				for (int i = 0; i < columns.size(); i++) {
					Map tmp = (Map) columns.get(i);
					if (key.equals(tmp.get("column_name"))) {
						entity.put(key, requestMap.get(key));
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}

	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request;
	}

	/**
	 * 获取所有参数放到Map
	 * 
	 * @return
	 */
	public Map<String, Object> getRequestParams() {
		Map<String, Object> map = new HashMap<>();
		Enumeration<String> paramNames = this.getRequest().getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String[] paramValues = this.getRequest().getParameterValues(
					paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					map.put(paramName, paramValue);
				}
			}
		}
		return map;
	}

	/**
	 * 获取当前用户对象.
	 * 
	 * @return
	 */
	protected UserModel getCurrentShiroUser() {
		UserModel user = (UserModel) SecurityUtils.getSubject().getPrincipal();
		return user;
	}

	/**
	 * 获取当前用户session.
	 * 
	 * @return session
	 */
	protected Session getSession() {
		Session session = SecurityUtils.getSubject().getSession();
		return session;
	}

	/**
	 * 获取当前用户httpSession.
	 * 
	 * @return
	 */
	protected Session getHttpSession() {
		Session session = SecurityUtils.getSubject().getSession();
		return session;
	}

	/**
	 * 获取当前PC用户对象.
	 * 
	 * @return user
	 */
	protected LoginInfoDto getCurrentUser() {
		Session session = SecurityUtils.getSubject().getSession();
		if (null != session) {
			return (LoginInfoDto) session
					.getAttribute(SessionCons.LOGIN_USER_SESSION);
		} else {
			return null;
		}
	}

	/**
	 * 获取当前App用户对象
	 * 
	 * @return
	 */
	protected AppLoginInfoDto getCurrentAppUser() {
		Session session = SecurityUtils.getSubject().getSession();
		if (null != session) {
			return (AppLoginInfoDto) session
					.getAttribute(SessionCons.APP_LOGIN_USER_SESSION);
		} else {
			return null;
		}
	}

	/**
	 * 获取当前登录PC用户id.
	 * 
	 * @return
	 */
	protected Integer getCurrentUserId() {
		LoginInfoDto user = getCurrentUser();
		if (null != user) {
			return user.getId();
		}
		return null;
	}

	/**
	 * 获取当前登录APP用户ID
	 * 
	 * @return
	 */
	protected Integer getCurrentAppUserId() {
		AppLoginInfoDto appUser = getCurrentAppUser();
		if (null != appUser) {
			return appUser.getId();
		}
		return null;
	}

	/**
	 * 获取当前登录用户的基层工会Id
	 * 
	 * @return
	 */
	protected Integer getCurrentUserBaseOrgId() {
		LoginInfoDto user = getCurrentUser();
		if (null != user) {
			return user.getBaseUnionId();
		}
		return null;
	}

	/**
	 * 获取当前登录用户的上级工会ID
	 * 
	 * @return
	 */
	protected Integer getCurrentUserOrgId() {
		LoginInfoDto user = getCurrentUser();
		if (null != user) {
			return user.getOrgainId();
		}
		return null;
	}

	/**
	 * 获取当前登录用户的部门ID
	 * 
	 * @return
	 */
	protected Integer getCurrentUserDeptId() {
		LoginInfoDto user = getCurrentUser();
		if (null != user) {
			return user.getDeptId();
		}
		return null;
	}

	/**
	 * 获取当前登录用户名.
	 * 
	 * @return
	 */
	protected String getCurrentUserName() {
		LoginInfoDto user = getCurrentUser();
		if (user != null) {
			return user.getUserName();
		}
		return null;
	}

	/**
	 * 获取当前登录者
	 * 
	 * @param request
	 *            http请求
	 * @return 登录者编号
	 */
	protected String getUserPrincipal(NativeWebRequest request) {
		Object userId = request.getAttribute(SessionCons.LOGIN_USER_ID,
				RequestAttributes.SCOPE_REQUEST);
		return userId == null ? null : String.valueOf(userId);
	}

	protected String getUserPrincipal(HttpServletRequest request) {
		Object userId = request.getAttribute(SessionCons.LOGIN_USER_ID);
		return userId == null ? null : String.valueOf(userId);
	}

	/**
	 * 获取用户的权限列表
	 * 
	 * @param request
	 * @return
	 */
	protected Set<?> getUserPermissions(NativeWebRequest request) {
		Object permissions = request.getAttribute(
				SessionCons.LOGIN_USER_PERMISSIONS,
				RequestAttributes.SCOPE_REQUEST);
		return permissions == null ? Collections.emptySet()
				: (Set<?>) permissions;
	}

	protected Set<?> getUserPermissions(HttpServletRequest request) {
		Object permissions = request
				.getAttribute(SessionCons.LOGIN_USER_PERMISSIONS);
		return permissions == null ? Collections.emptySet()
				: (Set<?>) permissions;
	}

	/**
	 * 系统异常处理
	 * 
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ BusinessException.class, UnauthorizedException.class,
			AuthorizationException.class,
			DataIntegrityViolationException.class, SQLException.class })
	@ResponseBody
	public Object handlerException(Exception ex, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isErrorEnabled()) {
			logger.error("用户{}请求路径{}时发生异常", this.getCurrentUserId(),
					request.getContextPath(), ex);
		}
		if (StringUtils.isNotEmpty(request.getHeader("device"))
				&& (request.getHeader("device").equals("APP") || request
						.getHeader("device").equals("H5"))) {
			if (ex instanceof BusinessException) {// 业务异常
				return new AjaxResult(String.valueOf(ResponseCode.fail
						.getCode()), "", ex.getMessage());
			} else if (ex instanceof UnauthorizedException) {// shiro授权异常
				return new AjaxResult(String.valueOf(ResponseCode.unauthorized
						.getCode()), "", ResponseCode.unauthorized.getMsg());
			} else {// 其他异常
				return new AjaxResult(String.valueOf(ResponseCode.fail
						.getCode()), "", ex.getMessage());
			}
		} else {
			if (ex instanceof BusinessException) {// 业务异常
				if (!"XMLHttpRequest".equalsIgnoreCase(request
						.getHeader("X-Requested-With"))) {// 不是ajax请求
					ModelAndView view = new ModelAndView("redirect:/exception");
					view.addObject("message", ex.getMessage());
					return view;
				} else {
					return new AjaxResult(ResponseCode.fail.getCode(), "",
							ex.getMessage());
				}
			} else if (ex instanceof UnauthorizedException) {// shiro授权异常
				if (!"XMLHttpRequest".equalsIgnoreCase(request
						.getHeader("X-Requested-With"))) {// 不是ajax请求
					ModelAndView view = new ModelAndView(
							"redirect:/unAuthorized");
					view.addObject("message", ex.getMessage());
					return view;
				} else {
					return new AjaxResult(ResponseCode.unauthorized.getCode(),
							"", "你没有操作权限");
				}
			} else {// 其他异常
				if (!"XMLHttpRequest".equalsIgnoreCase(request
						.getHeader("X-Requested-With"))) {// 不是ajax请求
					ModelAndView view = new ModelAndView("redirect:/exception");
					view.addObject("message", ex.getMessage());
					return view;
				} else {
					return new AjaxResult(ResponseCode.fail.getCode(), "",
							ex.getMessage());
				}
			}
		}
	}

	/**
	 * 其它异常
	 *
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Throwable.class)
	public ModelAndView handlerThrowable(Throwable e, NativeWebRequest request) {
		if (logger.isErrorEnabled()) {
			logger.error("用户{}请求路径{}时发生异常", request.getUserPrincipal(),
					request.getContextPath(), e);
		}
		ModelAndView mav = new ModelAndView("redirect:/exception");
		mav.addObject("message", e.getMessage());
		return mav;
	}

}

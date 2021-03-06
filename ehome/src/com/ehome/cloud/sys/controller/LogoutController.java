package com.ehome.cloud.sys.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import com.ehome.core.frame.BaseController;
import com.ehome.core.frame.BusinessException;

/**
 * 系统登出控制类
 * 
 * @Title:LogoutController
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月9日 上午11:47:56
 * @version:
 */
@Controller
public class LogoutController extends BaseController {

	@Autowired
	private SecurityManager securityManager;

	/**
	 * 系统登出操作
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/logout")
	public String logout(Model model, NativeWebRequest request)
			throws BusinessException {
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "/system/admin/login.html";
	}
}

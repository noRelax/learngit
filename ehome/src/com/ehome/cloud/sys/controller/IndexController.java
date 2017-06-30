package com.ehome.cloud.sys.controller;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ehome.cloud.sys.dto.LoginInfoDto;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.model.AjaxResult;

/**
 * 
 * @Title:IndexController
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月9日 下午2:39:47
 * @version:
 */
@Controller
@RequestMapping(value = "/admin")
public class IndexController extends BaseController {

	/**
	 * 系统首页
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@RequiresUser
	public ModelAndView index(Model model, NativeWebRequest request) {
		return new ModelAndView("/system/admin/console.html");
	}

	/**
	 * 加载控制台数据
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/console", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public AjaxResult console(Model model, NativeWebRequest request)
			throws BusinessException {
		return null;
	}

	/**
	 * 获取系统菜单信息
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/sysMenu", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public AjaxResult sysMenu(Model model, NativeWebRequest request)
			throws BusinessException {
		LoginInfoDto loginInfoDto = this.getCurrentUser();
		if (null != loginInfoDto) {
			return new AjaxResult(ResponseCode.success.getCode(), "",
					loginInfoDto);
		} else {
			return new AjaxResult(ResponseCode.error.getCode(), "",
					ResponseCode.error.getMsg());
		}
	}
}

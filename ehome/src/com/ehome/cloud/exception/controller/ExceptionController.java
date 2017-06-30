package com.ehome.cloud.exception.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;

import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.model.AjaxResult;

/**
 * 系统异常控制类
 * 
 * @Title:ExceptionController
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月14日 下午3:00:18
 * @version:
 */
@Controller
public class ExceptionController extends BaseController {

	@RequestMapping(value = "exception", method = RequestMethod.GET, produces = {
			"text/html", "application/xhtml+xml", "application/xml" })
	public String handlerBusinessException(Model model, NativeWebRequest request) {
		model.addAttribute("message", request.getParameter("message"));
		return "/system/admin/exception.html";
	}

	@RequestMapping(value = "exception", method = RequestMethod.POST, produces = {
			"application/json", "application/xml" })
	@ResponseBody
	public AjaxResult handlerAjaxBusinessException(Model model,
			NativeWebRequest request) {
		return new AjaxResult(ResponseCode.fail.getCode(), "",
				request.getParameter("message"));
	}

	@RequestMapping(value = "/unAuthorized", method = RequestMethod.GET, produces = {
			"text/html", "application/xhtml+xml", "application/xml" })
	public String handlerUnauthorizedException(Model model,
			NativeWebRequest request) {
		model.addAttribute("message", request.getParameter("message"));
		return "/system/admin/unauthorized.html";
	}

	@RequestMapping(value = "/unAuthorized", method = RequestMethod.POST, produces = {
			"application/json", "application/xml" })
	@ResponseBody
	public AjaxResult handlerAjaxUnauthorizedException(Model model,
			NativeWebRequest request) {
		return new AjaxResult(ResponseCode.unauthorized.getCode(), "",
				request.getParameter("message"));
	}

	@RequestMapping(value = "/appUnAuthorized")
	@ResponseBody
	public AjaxResult handlerAppUnauthorizedException(Model model,
			NativeWebRequest request) {
		return new AjaxResult(ResponseCode.unauthorized.getCode(), "",
				ResponseCode.unauthorized.getMsg());
	}

	@RequestMapping(value = "/appException")
	@ResponseBody
	public AjaxResult handlerAppException(Model model, NativeWebRequest request) {
		return new AjaxResult(ResponseCode.fail.getCode(), "",
				ResponseCode.fail.getMsg());
	}

}

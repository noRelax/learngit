package com.ehome.cloud.sys.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ehome.cloud.sys.service.IAppUserService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.frame.Pagination;
import com.ehome.core.model.AjaxResult;
import com.github.pagehelper.PageInfo;

/**
 * App用户控制类
 * 
 * @Title:AppUserController
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月13日 上午11:10:29
 * @version:
 */
@Controller
@RequestMapping(value = "/appUser")
public class AppUserController extends BaseController {

	@Resource
	private IAppUserService appUserService;

	/**
	 * 前端用户首页
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/app-user-index")
	@RequiresUser
	@RequiresPermissions("sys:appUser:index")
	public String appUserIndex(Model model, NativeWebRequest request) {
		return "/system/user/app-user-index.html";
	}

	/**
	 * 用户查询列表
	 * 
	 * @param model
	 * @param request
	 * @param keyword
	 * @param start
	 * @param pageSize
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/queryAppUser", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	@RequiresPermissions("sys:appUser:list")
	public AjaxResult queryUserList(
			Model model,
			NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") String keyword,
			@RequestParam(required = false, defaultValue = "") Integer blackTypeId,
			@RequestParam(required = false, defaultValue = "") Integer sEcho,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int rows)
			throws BusinessException {
		// 当前登录者ID
		// Integer userId = this.getCurrentUserId();
		List<Map<String, Object>> userList = appUserService.queryForList(
				keyword, blackTypeId, page, rows);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(userList);
		Pagination<Map<String, Object>> pagination = new Pagination<>();
		pagination.setData(pageInfo.getList());
		pagination.setsEcho(sEcho);
		pagination.setiTotalDisplayRecords((int) pageInfo.getTotal());
		pagination.setiTotalRecords((int) pageInfo.getTotal());
		return new AjaxResult(ResponseCode.success.getCode(), "", pagination);
	}

	/**
	 * 冻结解冻账户
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
	@RequiresUser
	@RequiresPermissions("sys:appUser:freeze")
	public AjaxResult updateStatus(int id, String status,
			NativeWebRequest request) throws BusinessException {
		String[] ids = new String[] { id + "" };
		if (appUserService.updateStatus(status, ids) > 0) {
			return new AjaxResult(ResponseCode.success.getCode(), "",
					ResponseCode.success.getMsg());
		} else {
			return new AjaxResult(ResponseCode.fail.getCode(), "",
					ResponseCode.fail.getMsg());
		}
	}

	/**
	 * 批量冻结解冻账户
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/batchUpdateStatus", method = RequestMethod.POST)
	@RequiresUser
	@RequiresPermissions(value = { "sys:appUser:batchFreeze",
			"sys:appUser:batchUnFreeze" }, logical = Logical.OR)
	public AjaxResult batchUpdateStatus(String ids, String status,
			NativeWebRequest request) throws BusinessException {
		String[] idsArray = ids.split(",");
		if (appUserService.updateStatus(status, idsArray) > 0) {
			return new AjaxResult(ResponseCode.success.getCode(), "",
					ResponseCode.success.getMsg());
		} else {
			return new AjaxResult(ResponseCode.fail.getCode(), "",
					ResponseCode.fail.getMsg());
		}
	}

	/**
	 * 设置黑名单
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/setBlack", method = RequestMethod.POST)
	@RequiresUser
	@RequiresPermissions("sys:appUser:setBlack")
	public AjaxResult setBlackAccount(int id, int blackType)
			throws BusinessException {

		if (appUserService.updateBlackType(id, blackType) > 0) {
			return new AjaxResult(ResponseCode.success.getCode(), "",
					ResponseCode.success.getMsg());
		} else {
			return new AjaxResult(ResponseCode.fail.getCode(), "",
					ResponseCode.fail.getMsg());
		}
	}

	/**
	 * 查看详情
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/queryInfo")
	@RequiresUser
	@RequiresPermissions("sys:appUser:queryInfo")
	public ModelAndView queryInfo(int id) throws BusinessException {
		ModelAndView mv = new ModelAndView("/system/user/app_user_info.html");
		Map<String, Object> info = appUserService.queryInfo(id);
		mv.addObject("info", info);
		return mv;
	}

	/**
	 * 选择黑名单类型页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/toBlackTypePage")
	@RequiresUser
	@RequiresPermissions("sys:appUser:setBlack")
	public ModelAndView toBlackTypePage(int id) {
		ModelAndView mv = new ModelAndView("/system/user/blackType.html");
		mv.addObject("id", id);
		return mv;
	}

	/**
	 * 批量导入（异步操作）
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/importMember", method = RequestMethod.POST)
	public AjaxResult importMember(Model model, NativeWebRequest request)
			throws BusinessException {
		return new AjaxResult(ResponseCode.success.getCode(), "",
				ResponseCode.success.getMsg());
	}
}

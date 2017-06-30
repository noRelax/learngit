package com.ehome.cloud.sys.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ehome.cloud.sys.dto.AdvertiseDto;
import com.ehome.cloud.sys.model.Advertise;
import com.ehome.cloud.sys.service.IAdvertiseService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.frame.BaseModelAndView;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.frame.Pagination;
import com.ehome.core.model.AjaxResult;
import com.ehome.core.util.DictoryCodeUtils;
import com.ehome.core.util.NumberUtils;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title:AdvertiseController
 * @Description:TODO
 * @author:张钟武
 * @date:2017年4月14日 下午3:59:14
 * @version:
 */
@Controller
@RequestMapping(value = "/advertise")
public class AdvertiseController extends BaseController {

	@Resource
	private IAdvertiseService advertiseService;

	/**
	 * 搜索页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/index")
	@RequiresUser
	@RequiresPermissions("sys:advertise:list")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView view = new BaseModelAndView("/system/advertise/list.html",
				request);
		return view;
	}

	/**
	 * 后台获取广告列表
	 * 
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	@RequiresPermissions("sys:advertise:list")
	public AjaxResult list(Model model, NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") String keyword,
			@RequestParam(required = false, defaultValue = "") Integer sEcho,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int rows)
			throws BusinessException {
		List<AdvertiseDto> userList = advertiseService.queryForList(keyword,
				page, rows);
		DictoryCodeUtils.renderList(userList);
		PageInfo<AdvertiseDto> pageInfo = new PageInfo<>(userList);
		Pagination<AdvertiseDto> pagination = new Pagination<>();
		pagination.setData(pageInfo.getList());
		pagination.setsEcho(sEcho);
		pagination.setiTotalDisplayRecords((int) pageInfo.getTotal());
		pagination.setiTotalRecords((int) pageInfo.getTotal());
		return new AjaxResult(ResponseCode.success.getCode(), "", pagination);
	}

	/**
	 * 新增服务
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "add-cfg-page", method = RequestMethod.GET)
	@RequiresUser
	@RequiresPermissions("sys:advertise:add")
	public String addLifePage(Model model, NativeWebRequest request) {
		return "/system/advertise/add.html";
	}

	/**
	 * 修改服务
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "edit-cfg-page", method = RequestMethod.GET)
	@RequiresUser
	@RequiresPermissions("sys:advertise:edit")
	public String editLifePage(Model model, NativeWebRequest request) {
		Integer pkId = NumberUtils.toInt(request.getParameter("id"));
		Advertise advertise = advertiseService.selectByKey(pkId);
		model.addAttribute("pkId", pkId);
		model.addAttribute("advertise", advertise);
		return "/system/advertise/edit.html";
	}

	/**
	 * 保存服务
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/addAdvertise", method = RequestMethod.POST)
	@RequiresUser
	@ResponseBody
	@RequiresPermissions(value = { "sys:advertise:add", "sys:advertise:edit" }, logical = Logical.OR)
	public AjaxResult addLife(Model model, NativeWebRequest request,
			MultipartFile filed, AdvertiseDto advertiseDto)
			throws BusinessException {
		Integer userId = this.getCurrentUserId();
		if (!NumberUtils.isNull(advertiseDto.getId())
				&& !NumberUtils.eqZero(advertiseDto.getId())) {
			Advertise advertise = advertiseService.selectByKey(advertiseDto
					.getId());
			if (null != advertise) {
				advertise.setArea(advertiseDto.getArea());
				advertise.setProvince(advertiseDto.getProvince());
				advertise.setCity(advertiseDto.getCity());
				advertise.setImgId(advertiseDto.getImgId());
				advertise.setIsInner(advertiseDto.getIsInner());
				advertise.setStatus(advertiseDto.getStatus());
				advertise.setUrl(advertiseDto.getUrl());
				advertise.setIcon(advertiseDto.getIcon());
				advertise.setIsInner(0);
				advertiseService.updateByKey(advertise);
			}
		} else {
			Advertise advertise = new Advertise();
			advertise.setArea(advertiseDto.getArea());
			advertise.setProvince(advertiseDto.getProvince());
			advertise.setCity(advertiseDto.getCity());
			advertise.setImgId(advertiseDto.getImgId());
			advertise.setIsInner(advertiseDto.getIsInner());
			advertise.setStatus(advertiseDto.getStatus());
			advertise.setUrl(advertiseDto.getUrl());
			advertise.setIcon(advertiseDto.getIcon());
			advertise.setCreaterUser(userId);
			advertise.setCreateTime(new Date());
			advertise.setIsInner(0);
			advertiseService.save(advertise);
		}
		return new AjaxResult(ResponseCode.success.getCode(), "",
				ResponseCode.success.getMsg());
	}

	/**
	 * _ 删除服务列表
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@RequiresUser
	@ResponseBody
	@RequiresPermissions("sys:advertise:delete")
	public AjaxResult delete(Model model, NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") Integer id)
			throws BusinessException {
		advertiseService.deleteByKey(id);
		return new AjaxResult(ResponseCode.success.getCode(), "",
				ResponseCode.success.getMsg());
	}
}

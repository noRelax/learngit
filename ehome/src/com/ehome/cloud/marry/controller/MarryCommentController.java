package com.ehome.cloud.marry.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ehome.cloud.marry.dto.MarryCommentDto;
import com.ehome.cloud.marry.service.IMarryCommentService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.frame.BaseModelAndView;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.frame.Pagination;
import com.ehome.core.model.AjaxResult;
import com.ehome.core.util.CollectionUtils;
import com.ehome.core.util.DictoryCodeUtils;
import com.ehome.core.util.NumberUtils;
import com.github.pagehelper.PageInfo;

/**
 * 婚恋评论管理
 * 
 * @Title:MarryCommentController
 * @Description:TODO
 * @author:tcr
 * @date:2017年4月17日 上午11:51:20
 * @version:
 */

@Controller
@RequestMapping(value = "/marry/comment")
public class MarryCommentController extends BaseController {

	@Resource
	private IMarryCommentService marryCommentService;

	/**
	 * 首页
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/index")
	@RequiresUser
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) {
		Integer photoId = NumberUtils.toInt(request.getParameter("photoId"));
		Integer uid = NumberUtils.toInteger(request.getParameter("uid"));
		ModelAndView view = new BaseModelAndView(
				"/marry/marryComment/list.html", request);
		view.addObject("photoId", photoId);
		view.addObject("uid", uid);
		view.addobjec("uu",sad);
	
		return view;
	}

	/**
	 * 婚恋列表
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult list(
			Model model,
			NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") Integer selectName,
			@RequestParam(required = false, defaultValue = "") String keyName,
			@RequestParam(required = false, defaultValue = "") Integer isShielding,
			@RequestParam(required = false, defaultValue = "") String startTime,
			@RequestParam(required = false, defaultValue = "") String endTime,
			@RequestParam(required = false, defaultValue = "") Integer sEcho,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int rows)
			throws BusinessException {
		MarryCommentDto dto = new MarryCommentDto();
		dto.setSelectName(selectName);
		dto.setKeyword(keyName);
		dto.setIsShielding(isShielding);
		dto.setStartPublictTime(startTime);
		dto.setEndPublicTime(endTime);
		List<MarryCommentDto> list = marryCommentService.queryForList(dto,
				page, rows);
		DictoryCodeUtils.renderList(list);
		PageInfo<MarryCommentDto> pageInfo = new PageInfo<>(list);
		Pagination<MarryCommentDto> pagination = new Pagination<>();
		pagination.setData(pageInfo.getList());
		pagination.setsEcho(sEcho);
		pagination.setiTotalDisplayRecords((int) pageInfo.getTotal());
		pagination.setiTotalRecords((int) pageInfo.getTotal());
		return new AjaxResult(ResponseCode.success.getCode(), "", pagination);
	}

	/**
	 * 设为屏蔽/取消屏蔽
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/shielding", method = RequestMethod.POST)
	@RequiresUser
	@ResponseBody
	public AjaxResult shielding(
			Model model,
			NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") Integer isShielding,
			@RequestParam(name = "photoIds[]", required = false) Integer[] photoIds)
			throws BusinessException {
		List<Integer> photoIdsList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(photoIds)) {
			photoIdsList = CollectionUtils.removeNull(new ArrayList<>(Arrays
					.asList(photoIds)));
			marryCommentService.updateShielding(isShielding, photoIdsList);
		}
		return new AjaxResult(ResponseCode.success.getCode(),
				ResponseCode.success.getMsg(), ResponseCode.success.getMsg());
	}

	/**
	 * 设置屏蔽和取消屏蔽
	 * 
	 * @param model
	 * @param request
	 * @param status
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	public AjaxResult updateStatus(
			Model model,
			NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") Integer isShielding,
			@RequestParam(required = false, defaultValue = "") Integer id)
			throws BusinessException {
		marryCommentService.updateStatus(isShielding, id);
		return new AjaxResult(ResponseCode.success.getCode(), "",
				ResponseCode.success.getMsg());
	}
}

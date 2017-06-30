package com.ehome.cloud.marry.controller;

import java.io.IOException;
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

import com.alibaba.fastjson.JSON;
import com.ehome.cloud.marry.dto.MarryMemberDto;
import com.ehome.cloud.marry.service.IGoldCoinService;
import com.ehome.cloud.marry.service.IMarryMemberService;
import com.ehome.cloud.sys.model.Dictionary;
import com.ehome.cloud.sys.service.IDictionaryService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.frame.BaseModelAndView;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.frame.Pagination;
import com.ehome.core.model.AjaxResult;
import com.ehome.core.util.CollectionUtils;
import com.ehome.core.util.DateUtils;
import com.ehome.core.util.DictoryCodeUtils;
import com.ehome.core.util.NumberUtils;
import com.ehome.core.util.StringUtils;
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
@RequestMapping(value = "/marry/member")
public class MarryMemberController extends BaseController {

	@Resource
	private IMarryMemberService marryMemberService;

	@Resource
	private IDictionaryService dictionaryService;

	@Resource
	private IGoldCoinService goldCoinService;
asdaswerweadasdZxasadasasda
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
		ModelAndView view = new BaseModelAndView(
				"/marry/marryMember/list.html", request);
		return view;
	}

	/**
	 * 婚恋会员列表
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
			@RequestParam(required = false, defaultValue = "") Integer isBlacklist,
			@RequestParam(required = false, defaultValue = "") String startTime,
			@RequestParam(required = false, defaultValue = "") String endTime,
			@RequestParam(required = false, defaultValue = "") String fieldName,
			@RequestParam(required = false, defaultValue = "") String fieldSort,
			@RequestParam(required = false, defaultValue = "") Integer sEcho,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int rows)
			throws BusinessException {
		MarryMemberDto dto = new MarryMemberDto();
		dto.setSelectName(selectName);
		dto.setKeyword(keyName);
		dto.setIsBlacklist(isBlacklist);
		dto.setStartTime(startTime);
		dto.setEndTime(endTime);
		dto.setFieldName(fieldName);
		dto.setFieldSort(fieldSort);
		List<MarryMemberDto> list = marryMemberService.queryForList(dto, page,
				rows);
		//		for (MarryMemberDto marryMemberDto : list) {
		//			marryMemberDto.setGoldCoins(Integer.valueOf(goldCoinService
		//					.getGoldCoinTotalNum(marryMemberDto.getAppUserId())));
		//		}
		DictoryCodeUtils.renderList(list);
		PageInfo<MarryMemberDto> pageInfo = new PageInfo<>(list);
		Pagination<MarryMemberDto> pagination = new Pagination<>();
		pagination.setData(pageInfo.getList());
		pagination.setsEcho(sEcho);
		pagination.setiTotalDisplayRecords((int) pageInfo.getTotal());
		pagination.setiTotalRecords((int) pageInfo.getTotal());
		return new AjaxResult(ResponseCode.success.getCode(), "", pagination);

	}

	/**
	 * 编辑页面
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	@RequiresUser
	public String edit(Model model, NativeWebRequest request)
			throws BusinessException {
		Integer id = NumberUtils.toInt(request.getParameter("id"));
		List<Dictionary> dictblacklist = dictionaryService
				.queryByCode("CODE_BLACKLIST");
		if (CollectionUtils.isNotEmpty(dictblacklist))
			model.addAttribute("dictblacklist", JSON.toJSON(dictblacklist));
		MarryMemberDto memberModel = marryMemberService.queryForEditList(id);
		DictoryCodeUtils.renderCode(memberModel);
		model.addAttribute("memberModel", memberModel);
		if (null != memberModel.getLastLoginTime()) {
			model.addAttribute("lastLoginTime",
					DateUtils.getTime(memberModel.getLastLoginTime()));
		} else {
			model.addAttribute("lastLoginTime", "");
		}
		model.addAttribute("activeTime",
				DateUtils.getTime(memberModel.getActiveTime()));
		return "/marry/marryMember/edit.html";
	}

	/**
	 * 婚恋会员列表
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/blacklist", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult blacklist(Model model, NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") Integer id,
			@RequestParam(required = false, defaultValue = "") Integer uid,
			@RequestParam(required = false, defaultValue = "") Integer sEcho,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int rows)
			throws BusinessException {
		List<MarryMemberDto> memberModel = marryMemberService
				.queryForEditBlackList(id, uid, page, rows);
		DictoryCodeUtils.renderList(memberModel);
		PageInfo<MarryMemberDto> pageInfo = new PageInfo<>(memberModel);
		Pagination<MarryMemberDto> pagination = new Pagination<>();
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
	@RequestMapping(value = "/updateIsBlacklist", method = RequestMethod.POST)
	@RequiresUser
	@ResponseBody
	public AjaxResult updateIsBlacklist(
			Model model,
			NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") Integer isBlacklist,
			@RequestParam(required = false, defaultValue = "") Integer id)
			throws BusinessException {
		Integer curUserId = this.getCurrentUserId();
		if (!NumberUtils.isNull(isBlacklist)) {
			marryMemberService.updateIsBlacklist(id, isBlacklist, curUserId);
		} else {
			return new AjaxResult(ResponseCode.fail.getCode(),
					ResponseCode.fail.getMsg(), "必须设置黑名单");
		}
		return new AjaxResult(ResponseCode.success.getCode(),
				ResponseCode.success.getMsg(), ResponseCode.success.getMsg());
	}

	@RequestMapping(value = "/export")
	public void exportExcel(
			Model model,
			MarryMemberDto marryMemberDto,
			HttpServletRequest request,
			@RequestParam(name = "marryMemberIds", required = false) String marryMemberIds,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		List<String> marryMemberList = new ArrayList<>();
		if (StringUtils.isNotBlank(marryMemberIds)) {
			marryMemberList = Arrays.asList(marryMemberIds.split(","));
		}
		List<Integer> list = new ArrayList<>();
		marryMemberList.forEach(o -> {
			list.add(NumberUtils.toInt(o));
		});
		marryMemberDto.setMarryMoldelList(list);
		List<MarryMemberDto> marryMoldelList = new ArrayList<>();
		marryMoldelList = marryMemberService.queryById(marryMemberDto);
		DictoryCodeUtils.renderList(marryMoldelList);
		marryMemberService.exportMember(marryMoldelList, response);
	}
}

package com.ehome.cloud.help.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ehome.cloud.help.dto.HelpRightExplainDto;
import com.ehome.cloud.help.model.HelpRightExplainModel;
import com.ehome.cloud.help.service.IHelpRightExplainService;
import com.ehome.cloud.sys.model.Dictionary;
import com.ehome.cloud.sys.service.IDictionaryService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.frame.Pagination;
import com.ehome.core.model.AjaxResult;
import com.ehome.core.util.CollectionUtils;
import com.ehome.core.util.DictoryCodeUtils;
import com.ehome.core.util.NumberUtils;
import com.github.pagehelper.PageInfo;

/**
 * @Title: HelpRightController
 * @Description: TODO
 * @author hl@diandianwifi.com
 * @date 2017年3月28日 上午11:47:42
 * @version
 */
@Controller
@RequestMapping(value = "/helpRightExplain")
public class HelpRightDExplainController extends BaseController {

	@Resource
	private IHelpRightExplainService helpRightExplainService;

	@Resource
	private IDictionaryService dictionaryService;

	/**
	 * 跳转至帮扶或维权说明首页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index")
	@RequiresUser
	@RequiresPermissions("help:helpexplain")
	public String getIndexPage(Model model) {
		List<Dictionary> distExplain = dictionaryService
				.queryByCode("CODE_EXPLAIN_TYPE");
		if (CollectionUtils.isNotEmpty(distExplain)) {
			model.addAttribute("distExplain", JSON.toJSON(distExplain));
		}
		return "/help/helpExplain/list.html";
	}

	/**
	 * 显示说明列表
	 * 
	 * @param model
	 * @param request
	 * @param title
	 *            前台页面检索标题
	 * @param explainType
	 *            说明类型
	 * @param sEcho
	 * @param page
	 * @param rows
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/queryList", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	@RequiresPermissions("help:helpexplain")
	public AjaxResult queryUnsignList(
			Model model,
			NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") String title,
			@RequestParam(required = false, defaultValue = "") Integer explainType,
			@RequestParam(required = false, defaultValue = "") Integer sEcho,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int rows)
			throws BusinessException {
		HelpRightExplainDto explainDto = new HelpRightExplainDto();
		explainDto.setTitle(title);
		explainDto.setExplainType(explainType);
		List<HelpRightExplainDto> explainList = helpRightExplainService
				.queryForList(explainDto, page, rows);
		DictoryCodeUtils.renderList(explainList);
		PageInfo<HelpRightExplainDto> pageInfo = new PageInfo<>(explainList);
		Pagination<HelpRightExplainDto> pagination = new Pagination<>();
		pagination.setData(pageInfo.getList());
		pagination.setsEcho(sEcho);
		pagination.setiTotalDisplayRecords((int) pageInfo.getTotal());
		pagination.setiTotalRecords((int) pageInfo.getTotal());
		return new AjaxResult(ResponseCode.success.getCode(), "", pagination);
	}

	/**
	 * 查看编辑帮扶或维权说明
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/explainInfo")
	@RequiresUser
	@RequiresPermissions("help:helpexplain")
	public ModelAndView showExplainInfo(Model model, NativeWebRequest request) {
		ModelAndView mv = new ModelAndView();
		Integer id = NumberUtils.toInt(request.getParameter("id"), 0);
		HelpRightExplainDto rightExplainDto = new HelpRightExplainDto();
		if (id.intValue() != 0) {
			HelpRightExplainModel rightExplainModel = helpRightExplainService
					.selectByKey(id);
			BeanUtils.copyProperties(rightExplainModel, rightExplainDto);
			DictoryCodeUtils.renderCode(rightExplainDto);
			List<Dictionary> distExplain = dictionaryService
					.queryByCode("CODE_EXPLAIN_TYPE");
			if (CollectionUtils.isNotEmpty(distExplain)) {
				mv.addObject("distExplain", JSON.toJSON(distExplain));
			}
		}
		mv.addObject("rightExplainDto", rightExplainDto);
		mv.setViewName("/help/helpExplain/info.html");
		return mv;
	}

	/**
	 * 增加维权或帮扶说明
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addExplainInfo")
	@RequiresUser
	@RequiresPermissions("help:helpexplain")
	public String addExplainInfo(Model model, NativeWebRequest request) {
		List<Dictionary> distExplain = dictionaryService
				.queryByCode("CODE_EXPLAIN_TYPE");
		if (CollectionUtils.isNotEmpty(distExplain)) {
			model.addAttribute("distExplain", JSON.toJSON(distExplain));
		}
		return "/help/helpExplain/add.html";
	}

	@RequestMapping(value = "/deleteExplain", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult deleteExplain(Model model, NativeWebRequest request) {
		Integer id = NumberUtils.toInt(request.getParameter("id"), 0);
		if (id.intValue() != 0) {
			helpRightExplainService.deleteByKey(id);
		}
		return new AjaxResult(ResponseCode.success.getCode(), "",
				ResponseCode.success.getMsg());
	}

	/**
	 * 更改维权或帮扶说明
	 * 
	 * @param model
	 * @param helpRightExplainDto
	 * @return
	 */
	@RequestMapping(value = "/updateExplain", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult updateExplain(Model model,
			HelpRightExplainDto helpRightExplainDto) {
		Integer id = helpRightExplainDto.getId();
		if (!NumberUtils.isNull(id) && !NumberUtils.eqZero(id)) {
			helpRightExplainDto.setCreatePerson(this.getCurrentUser().getUserName());
			helpRightExplainDto.setCreateDate(new Date());
			HelpRightExplainModel helpRightExplainModel = new HelpRightExplainModel();
			BeanUtils
					.copyProperties(helpRightExplainDto, helpRightExplainModel);
			helpRightExplainService.updateByKey(helpRightExplainModel);
		}
		return new AjaxResult(ResponseCode.success.getCode(), "",
				ResponseCode.success.getMsg());
	}

	/**
	 * 加载维权或帮扶说明类型
	 * 
	 * @param model
	 * @param helpRightExplainDto
	 * @return
	 */
	@RequestMapping(value = "/getRightOrHelpType", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult getRightOrHelpType(Model model, NativeWebRequest request) {
		Integer id = Integer.parseInt(request.getParameter("pid"));
		List<Dictionary> distExplain = null;
		List<Dictionary> distServiceButton = null;
		AjaxResult ajaxResult = new AjaxResult();
		if (!NumberUtils.isNull(id) && !NumberUtils.eqZero(id)) {
			if (id.intValue() == 1) {
				distExplain = dictionaryService.queryByCode("CODE_HELP_TYPE");
				distServiceButton = dictionaryService
						.queryByCode("CODE_HELP_EBUTTON");
				if (CollectionUtils.isNotEmpty(distExplain)) {
					ajaxResult.setStatus(distExplain);
				}
				if (CollectionUtils.isNotEmpty(distServiceButton)) {
					ajaxResult.setData(distServiceButton);
				}
			} else {
				distExplain = dictionaryService.queryByCode("CODE_RIGHT_TYPE");
				distServiceButton = dictionaryService
						.queryByCode("CODE_RIGHT_EBUTTON");
				if (CollectionUtils.isNotEmpty(distExplain)) {
					ajaxResult.setStatus(distExplain);
				}
				if (CollectionUtils.isNotEmpty(distServiceButton)) {
					ajaxResult.setData(distServiceButton);
				}
			}
		}
		return ajaxResult;
	}
	
	@RequestMapping(value = "/saveExplain", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult saveExplain(Model model,
			HelpRightExplainDto helpRightExplainDto) {
		if (helpRightExplainDto!=null) {
			helpRightExplainDto.setCreatePerson(this.getCurrentUser().getUserName());
			helpRightExplainDto.setCreateDate(new Date());
			HelpRightExplainModel helpRightExplainModel = new HelpRightExplainModel();
			BeanUtils.copyProperties(helpRightExplainDto, helpRightExplainModel);
			helpRightExplainService.save(helpRightExplainModel);
		}
		return new AjaxResult(ResponseCode.success.getCode(), "",
				ResponseCode.success.getMsg());
	}
}

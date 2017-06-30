package com.ehome.cloud.sys.controller;

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
import org.springframework.web.servlet.ModelAndView;

import com.ehome.cloud.sys.model.Dictionary;
import com.ehome.cloud.sys.model.DictionaryType;
import com.ehome.cloud.sys.service.IDictionaryService;
import com.ehome.cloud.sys.service.IDictionaryTypeService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.frame.BaseModelAndView;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.frame.Pagination;
import com.ehome.core.model.AjaxResult;
import com.ehome.core.util.NumberUtils;
import com.github.pagehelper.PageInfo;

/**
 * 数据字典控制类
 * 
 * @Title:DictionaryController
 * @Description:TODO
 * @author:张钟武
 * @date:2017年2月20日 下午2:25:47
 * @version:
 */
@Controller
@RequestMapping(value = "/dict")
public class DictionaryController extends BaseController {

	@Resource
	private IDictionaryTypeService dictionaryTypeService;

	@Resource
	private IDictionaryService dictionaryService;

	/**
	 * 数据字典代码类型列表
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/index")
	@RequiresUser
	@RequiresPermissions("sys:dictType:list")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView view = new BaseModelAndView("/system/dict/index.html",
				request);
		return view;
	}

	/**
	 * 查询列表
	 * 
	 * @param model
	 * @param request
	 * @param keyword
	 * @param start
	 * @param pageSize
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/queryDictType", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	@RequiresPermissions("sys:dictType:list")
	public AjaxResult queryDictTypeList(Model model, NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") String typeName,
			@RequestParam(required = false, defaultValue = "") Integer sEcho,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int rows)
			throws BusinessException {
		// 当前登录者ID
		Integer userId = this.getCurrentUserId();
		List<DictionaryType> dictList = dictionaryTypeService.queryForList(
				typeName, userId, page, rows);
		PageInfo<DictionaryType> pageInfo = new PageInfo<>(dictList);
		Pagination<DictionaryType> pagination = new Pagination<>();
		pagination.setData(pageInfo.getList());
		pagination.setsEcho(sEcho);
		pagination.setiTotalDisplayRecords((int) pageInfo.getTotal());
		pagination.setiTotalRecords((int) pageInfo.getTotal());
		return new AjaxResult(ResponseCode.success.getCode(), "", pagination);
	}

	/**
	 * 删除代码类型
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/deleteDictType", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	@RequiresPermissions("sys:dictType:delete")
	public AjaxResult deleteDictType(Model model, NativeWebRequest request)
			throws BusinessException {
		Integer pkId = NumberUtils.toInt(request.getParameter("pkId"), 0);
		if (pkId != 0) {
			dictionaryService.deleteByDictTypeId(pkId);
		}
		return new AjaxResult(ResponseCode.success.getCode(), "",
				ResponseCode.success.getMsg());
	}

	/**
	 * 新增代码类型
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "add-dictType", method = RequestMethod.GET)
	@RequiresUser
	@RequiresPermissions("sys:dictType:add")
	public String addDictType(Model model, NativeWebRequest request) {
		return "/system/dict/add_dictType.html";
	}

	/**
	 * 修改代码类型
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "update-dictType", method = RequestMethod.GET)
	@RequiresUser
	@RequiresPermissions("sys:dictType:update")
	public String updateDictType(Model model, NativeWebRequest request) {
		Integer pkId = NumberUtils.toInt(request.getParameter("pkId"));
		DictionaryType dtModel = dictionaryTypeService.selectByKey(pkId);
		model.addAttribute("dtModel", dtModel);
		return "/system/dict/edit_dictType.html";
	}

	/**
	 * 增加代码类型
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/addDictType", method = RequestMethod.POST)
	@RequiresUser
	@RequiresPermissions(value = { "sys:dictType:add", "sys:dictType:update" }, logical = Logical.OR)
	@ResponseBody
	public AjaxResult addDictType(Model model, NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") Integer id,
			@RequestParam(required = false, defaultValue = "") String typeCode,
			@RequestParam(required = false, defaultValue = "") String typeName,
			@RequestParam(required = false, defaultValue = "") String remark)
			throws BusinessException {
		dictionaryService.saveDictType(id, typeCode, typeName, remark);
		return new AjaxResult(ResponseCode.success.getCode(), "",
				ResponseCode.success.getMsg());
	}

	/**
	 * 查询字典项列表
	 * 
	 * @param model
	 * @param request
	 * @param keyword
	 * @param start
	 * @param pageSize
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/queryDict", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	@RequiresPermissions("sys:dict:list")
	public AjaxResult queryDictList(
			Model model,
			NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") Integer dictTypeId,
			@RequestParam(required = false, defaultValue = "") Integer sEcho,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int rows)
			throws BusinessException {
		// 当前登录者ID
		List<Dictionary> dictList = dictionaryService.queryForList(dictTypeId,
				page, rows);
		PageInfo<Dictionary> pageInfo = new PageInfo<>(dictList);
		Pagination<Dictionary> pagination = new Pagination<>();
		pagination.setData(pageInfo.getList());
		pagination.setsEcho(sEcho);
		pagination.setiTotalDisplayRecords((int) pageInfo.getTotal());
		pagination.setiTotalRecords((int) pageInfo.getTotal());
		return new AjaxResult(ResponseCode.success.getCode(), "", pagination);
	}

	/**
	 * 删除代码项
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/deleteDict", method = RequestMethod.POST)
	@ResponseBody
	@RequiresUser
	@RequiresPermissions("sys:dict:delete")
	public AjaxResult deleteDict(Model model, NativeWebRequest request)
			throws BusinessException {
		Integer pkId = NumberUtils.toInt(request.getParameter("pkId"), 0);
		dictionaryService.deleteByDictId(pkId);
		return new AjaxResult(ResponseCode.success.getCode(), "",
				ResponseCode.success.getMsg());
	}

	/**
	 * 新增代码项
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "add-dict", method = RequestMethod.GET)
	@RequiresUser
	@RequiresPermissions("sys:dict:add")
	public String addDict(Model model, NativeWebRequest request) {
		Integer pkId = NumberUtils.toInt(request.getParameter("pkId"));
		model.addAttribute("dictTypeId", pkId);
		return "/system/dict/add_dict.html";
	}

	/**
	 * 修改代码项
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "update-dict", method = RequestMethod.GET)
	@RequiresUser
	@RequiresPermissions("sys:dict:update")
	public String updateDict(Model model, NativeWebRequest request) {
		Integer pkId = NumberUtils.toInt(request.getParameter("pkId"));
		Dictionary dtModel = dictionaryService.selectByKey(pkId);
		model.addAttribute("dtModel", dtModel);
		return "/system/dict/edit_dict.html";
	}

	/**
	 * 增加代码项
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/addDict", method = RequestMethod.POST)
	@RequiresUser
	@RequiresPermissions(value = { "sys:dict:add", "sys:dict:update" }, logical = Logical.OR)
	@ResponseBody
	public AjaxResult addDict(
			Model model,
			NativeWebRequest request,
			@RequestParam(required = false, defaultValue = "") Integer id,
			@RequestParam(required = false, defaultValue = "") Integer dictTypeId,
			@RequestParam(required = false, defaultValue = "") String dictKey,
			@RequestParam(required = false, defaultValue = "") String dictValue,
			@RequestParam(required = false, defaultValue = "") Integer sort,
			@RequestParam(required = false, defaultValue = "") String remark)
			throws BusinessException {
		Integer userId = this.getCurrentUserId();
		dictionaryService.saveDict(id, dictTypeId, dictKey, dictValue, sort,
				remark, userId);
		return new AjaxResult(ResponseCode.success.getCode(), "",
				ResponseCode.success.getMsg());
	}
}

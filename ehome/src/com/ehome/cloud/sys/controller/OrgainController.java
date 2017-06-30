package com.ehome.cloud.sys.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ehome.cloud.sys.model.OrgainModel;
import com.ehome.cloud.sys.model.TreeModel;
import com.ehome.cloud.sys.service.IOrgainService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.frame.BusinessException;
import com.ehome.core.model.AjaxResult;
import com.ehome.core.util.NumberUtils;
import com.ehome.core.util.PageData;

/*
 * @Title:OrgainController
 * @Description:组织结构
 * @author:zsh
 * @date:2017年2月3日
 * @version 1.0,2017年2月3日
 * @{tags}
 */
@Controller
@RequestMapping(value = "/orgain")
public class OrgainController extends BaseController {

	private final static Logger logger = LoggerFactory
			.getLogger(OrgainController.class);

	@Resource
	private IOrgainService orgainService;

	/**
	 * 进入组织架构主页面
	 * 
	 * @param orgainModel
	 * @param request
	 * @param response
	 * @return
	 *
	 */
	@RequestMapping(value = "list")
	@RequiresUser
	@RequiresPermissions("sys:orgain:list")
	public ModelAndView getList(OrgainModel orgainModel,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		Integer orgId = this.getCurrentUserOrgId();
		ModelAndView result = null;
		Map<String, Object> map = new HashMap<>();
		map.put("id", orgId);
		map.put("queryid", "1");
		try {
			result = new ModelAndView("/system" + request.getServletPath()
					+ ".html");
			//List<Map<String, Object>> varList = orgainService.queryTreelist(map);
			List<Map<String, Object>> varList = orgainService.queryByPId(map);
			result.addObject("resultStr", JSON.toJSONString(varList));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "/initTree")
	public ModelAndView initTree(OrgainModel orgainModel,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		HashMap<String, Object> map = new HashMap<>();
		ModelAndView result = null;
		try {
			result = new ModelAndView("/system/orgain/list");
			List<Map<String, Object>> varList = orgainService
					.queryTreelist(map);
			result.addObject("resultStr", JSON.toJSONString(varList)
					.replaceAll("false", "0").replaceAll("true", "1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取子节点
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *
	 */
	@RequestMapping(value = "/getChildNode")
	@ResponseBody
	public Object getChildNode(HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		Map<String, Object> map = new HashMap<>();
		map.put("id", request.getParameter("nodeId"));
		List<Map<String, Object>> result = orgainService.queryByPId(map);
		return JSON.toJSONString(result);
	}

	/**
	 * 获取子节点
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *
	 */
	@RequestMapping(value = "/getChilds")
	public void getChilds(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		try {
			String pid = request.getParameter("nodeId");// 当前节点ID
			List<Map<String, Object>> varList = orgainService.getChilds(pid);// 查找该节点下的所有数据
			String resultStr = "";
			if (varList == null || varList.size() == 0) {
				resultStr = "{}";
				response.getWriter().print(resultStr);
				return;
			}
			resultStr = JSON.toJSONString(varList);
			response.getWriter().print(resultStr);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}

	/**
	 * 进入节点新增或编辑页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	@RequiresUser
	@RequiresPermissions("sys:orgain:add")
	public ModelAndView goAddNode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		ModelAndView result = null;
		Map<String, Object> obj = new HashMap<>();
		try {
			result = new ModelAndView("/system" + request.getServletPath()
					+ ".html");
			String id = request.getParameter("id");
			obj.put("parent_id", request.getParameter("parent_id"));
			// XXX 节点类型 获取父节点的类型
			obj.put("type", request.getParameter("type"));
			if (id != null && !"".equals(id)) {
				obj = orgainService.queryByIdReturnMap(Integer.parseInt(id));
			}
			result.addObject("obj", obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 增加节点
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/addNode")
	@RequiresUser
	@RequiresPermissions("sys:orgain:addNode")
	public ModelAndView addNode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		Map<String, Object> map = new HashMap<>();
		map = this.getPageData();
		try {
			if (map.get("sort") == null
					|| "".equals(map.get("sort").toString())) {
				map.put("sort", 1);
			}
			if (map.get("id") == null || "".equals(map.get("id"))) {
				orgainService.insertOrgainByMap(map);
			} else {
				orgainService.updateOrgainByMap(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/orgain/list");
	}

	/**
	 * 删除节点
	 * 
	 * @param out
	 * @param request
	 *
	 */
	@RequestMapping(value = "/deleteNode")
	@RequiresUser
	@RequiresPermissions("sys:orgain:deleteNode")
	public void deleteNode(PrintWriter out, HttpServletRequest request) {
		PageData pd = new PageData();
		int flag = 0;
		try {
			pd = this.getPageData();
			if (pd.get("id") != null && !"".equals(pd.get("id"))) {
				flag = orgainService.deleteOrgainById(pd.getInteger("id", -1));
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		out.println(flag);
	}

	/**
	 * 
	 * //TODO 添加方法功能描述
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/findTreeData", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult findTreeNode(Model model, NativeWebRequest request)
			throws BusinessException {
		Integer nodeId = NumberUtils.toInt(request.getParameter("nodeId"), 0);
		Integer isChild = NumberUtils.toInt(request.getParameter("isChild"), 0);
		Integer isParent = NumberUtils.toInt(request.getParameter("isParent"),
				0);
		Integer orgainType = NumberUtils.toInt(
				request.getParameter("orgainType"), 0);
		Integer orgId = this.getCurrentUserOrgId();
		if (!NumberUtils.eqZero(nodeId))
			orgId = nodeId;
		List<TreeModel> list = orgainService.findTreeNode(orgId, orgainType,
				isChild, isParent);
		if (CollectionUtils.isNotEmpty(list)) {
			list.forEach(o -> {
				o.setIsParent(true);
			});
		}
		return new AjaxResult(ResponseCode.success.getCode(), "", list);
	}
}

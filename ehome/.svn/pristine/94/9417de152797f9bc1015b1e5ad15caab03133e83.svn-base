package com.ehome.cloud.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ehome.cloud.common.service.CommonService;
import com.ehome.core.frame.BaseController;
import com.ehome.core.frame.BaseModelAndView;
import com.ehome.core.util.JsonUtil;
import com.ehome.core.util.StringUtils;

public class CommonController extends BaseController {
	@Resource(name = "commonService")
	private CommonService commonService;

	@ResponseBody
	@RequestMapping(value = "list")
	public ModelAndView list(HttpServletRequest req, HttpServletResponse res) {
		ModelAndView view = new BaseModelAndView("/system/"
				+ this.controller.toLowerCase() + "/index.html", req);
		List userlist = null;
		Map m = new HashMap();
		Map entity = this.create();
		m.put("table", this.table);
		m.put("condition", entity);// 暂时只支付等于的条件
		Map params = this.getRequestParams();
		if (params.get("fields") != null) {
			m.put("fields", params.get("fields"));
		}
		if (params.get("order") != null) {
			m.put("order", params.get("order"));
		}
		if (params.get("page") != null) {
			m.put("page", params.get("page"));
		}
		if (params.get("pagesize") != null) {
			m.put("pagesize", params.get("pagesize"));
		}
		// m.put("fields", "test,test1"); //选填
		// m.put("order", "");//选填
		// m.put("page", 1);//选填
		// m.put("size", 10);//选填
		try {
			userlist = commonService.list(m);
		} catch (Exception e) {
			e.printStackTrace();
		}
		view.addObject("tablelist", JsonUtil.javaList2JsonList(userlist));
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "add")
	public ModelAndView add(HttpServletRequest req, HttpServletResponse res) {
		ModelAndView view = new BaseModelAndView("/system/"
				+ this.controller.toLowerCase() + "/edit.html", req);
		if (req.getMethod().equals("POST")) {
			try {
				Map entity = this.create();
				Map params = new HashMap();
				logger.debug("tablename " + table);
				params.put("entity", entity);
				params.put("table", table);
				int a = commonService.insert(params);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		} else {
		}
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "update")
	public String update(HttpServletRequest req, HttpServletResponse res) {
		try {
			Map entity = this.create();
			Map params = new HashMap();
			logger.debug("tablename " + table);
			params.put("entity", entity);
			params.put("table", table);
			params.put("id", entity.get("id"));
			int a = commonService.update(params);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "1";
	}

	@RequestMapping(value = "delete")
	public void delete(HttpServletRequest req, HttpServletResponse res) {
		try {
			Map reqParmas = this.getRequestParams();
			String ids = StringUtils.getString(reqParmas, "ids");
			if (ids.equals("")) {
				ids = StringUtils.getString(reqParmas, "id");
			}
			Map params = new HashMap();
			params.put("table", table);
			params.put("ids", ids);
			commonService.deleteByBatchId(params);
		} catch (Exception e) {

		}
	}
}

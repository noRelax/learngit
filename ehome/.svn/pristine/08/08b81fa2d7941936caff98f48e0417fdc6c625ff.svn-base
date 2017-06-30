/**
 * @Project:ZGHome
 * @FileName:ClassifyController.java
 */
package com.ehome.cloud.puhui.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tk.mybatis.mapper.entity.Condition;

import com.ehome.cloud.puhui.model.ClassifyModel;
import com.ehome.cloud.puhui.service.IClassifyService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.frame.Pagination;
import com.ehome.core.model.AjaxResult;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title:ClassifyController
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年3月1日
 * @version:
 * 
 */

@Controller
@RequestMapping(value="/puhui/classify")
public class ClassifyController extends BaseController {
	
	@Resource
	private IClassifyService iClassifyService;
	
	/**
	 * 
	 * 商家分类列表页面
	 * @return
	 * 
	 */
	
	@RequestMapping(value = "/list")
	public String listPage(){
		return "/puhui/classify/list.html";
	}
	
	@RequestMapping(value="/querylist")
	@ResponseBody
	public AjaxResult querylist(
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "") Integer sEcho,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer rows
			){
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		
		List<Map<String,Object>> List = iClassifyService.selectPageList(map, page, rows);
		
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(List);
		Pagination<Map<String,Object>> pagination = new Pagination<Map<String,Object>>();
		pagination.setsEcho(sEcho);
		pagination.setData(pageInfo.getList());
		pagination.setiTotalDisplayRecords((int) pageInfo.getTotal());
		pagination.setiTotalRecords((int) pageInfo.getTotal());

		return new AjaxResult(ResponseCode.success.getCode(), "", pagination);
	}
	

	/**
	 * 新增商家分类页面
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(){
		return "/puhui/classify/add.html";
	}

	/**
	 * 保存
	 * @return
	 */
	@RequestMapping(value="/addclass")
	@ResponseBody
	public AjaxResult addclass(
			@RequestParam(required = false, defaultValue = "") String id,
			@RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false, defaultValue = "1") String sort){
		
		ClassifyModel cm = new ClassifyModel();
		cm.setName(name);
		cm.setSort(Integer.parseInt(sort));
		cm.setCreatetime(new Date());
		
		int count = 0;
		if(id.equals(""))
			count = iClassifyService.saveNotNull(cm);
		else{
			cm.setId(Integer.parseInt(id));
			count = iClassifyService.updateByKey(cm);
		}
		
		if(count > 0){
			return new AjaxResult(ResponseCode.success.getCode(), "", "");
		}else{
			return new AjaxResult(ResponseCode.fail.getCode(), "", "");
		}
		
	}
	
	
	
	/**
	 * 编辑商家分类页面
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam(required = false, defaultValue = "") String id){
		ModelAndView mv = new ModelAndView("/puhui/classify/edit.html");
		ClassifyModel cm = iClassifyService.selectByKey(Integer.parseInt(id));
		mv.addObject("cm", cm);
		return mv;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public AjaxResult delete(@RequestParam(required = false, defaultValue = "") String id){
		
		int count = 0;
		if(!id.equals("")){
			count = iClassifyService.deleteByKey(Integer.parseInt(id.trim()));
		}
		
		if(count > 0){
			return new AjaxResult(ResponseCode.success.getCode(), "", "");
		}else{
			return new AjaxResult(ResponseCode.fail.getCode(), "", "");
		}
		
	}
	
	/**
	 * 获取所有分类
	 * @return
	 */
	@RequestMapping(value="/getClssify")
	@ResponseBody
	public Map<String, Object> getClssify(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		Condition condition = new Condition(ClassifyModel.class);
		condition.orderBy("sort");
		List<ClassifyModel> list = iClassifyService.selectByCondition(condition);
		map.put("data", list);
		return map;
	}
}

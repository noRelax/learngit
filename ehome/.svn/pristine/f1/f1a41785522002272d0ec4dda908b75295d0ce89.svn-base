/**
 * @Project:ZGHome
 * @FileName:AreaController.java
 */
package com.ehome.cloud.area.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehome.cloud.area.model.AreaModel;
import com.ehome.cloud.area.service.IAreaService;
import com.ehome.core.frame.BaseController;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @Title:AreaController
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月24日
 * @version:
 */
@Controller
@RequestMapping(value="/area")
public class AreaController extends BaseController {
	
	@Resource
	private IAreaService iAreaService;

	/**
	 * 获取所有省
	 * @return
	 */
	@RequestMapping(value="/getProvince")
	@ResponseBody
	public Map<String, Object> getProvince(){
		Map<String, Object> map = new HashMap<String, Object>();
		Condition condition = new Condition(AreaModel.class);
		Criteria criteria = condition.createCriteria();
		criteria.andEqualTo("pid", 0);
		List<AreaModel> list = iAreaService.selectByCondition(condition);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 根据pid获取地区
	 * @return
	 */
	@RequestMapping(value="/getByPid")
	@ResponseBody
	public Map<String, Object> getByPid(@RequestParam(required = false, defaultValue = "") String pid){
		Map<String, Object> map = new HashMap<String, Object>();
		Condition condition = new Condition(AreaModel.class);
		Criteria criteria = condition.createCriteria();
		criteria.andEqualTo("pid", pid);
		List<AreaModel> list = iAreaService.selectByCondition(condition);
		map.put("data", list);
		return map;
	}
}

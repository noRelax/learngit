package com.ehome.cloud.app.module.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ehome.cloud.app.module.service.IAppModuleService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.util.CollectionUtils;
import com.ehome.core.util.JsonUtil;
import com.ehome.core.util.MapUtils;
import com.ehome.core.util.StringUtils;

/*
 * @Title:AppModuleController
 * @Description:TODO
 * @author:zsh
 * @date:2017年2月28日
 * @version 1.0,2017年2月28日
 * @{tags}
 */
@Controller
@RequestMapping(value = "/app/module")
public class AppModuleController extends BaseController {

//	private final static Logger logger = LoggerFactory
//			.getLogger(AppModuleController.class);
	@Resource
	private IAppModuleService appModuleService;

	/**
	 * 通过城市id查询当前城市的模块列表
	 * scene 现场 countyName 县
	 * @param request
	 * @param response
	 * @throws Exception
	 *
	 */
	@RequestMapping(value = "/getModuleyCityId", method = RequestMethod.POST)
	public void getModuleyCityId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		List<Map<String, Object>> dataList = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map = this.getPageData();
		int status = ResponseCode.success.getCode();
		String message = ResponseCode.success.getMsg();
		try {
			List<Map<String, Object>> list = null;
			if (StringUtils.isNoneBlank(MapUtils.getString(map, "city_name",
					null))) {
				Integer city_id = -1;
				List<Map<String, Object>> citys = appModuleService
						.queryCityInfo(map);
				if (CollectionUtils.isNotEmpty(citys)) {
					for (Map<String, Object> city : citys) {
						Integer cityIdTemp = Integer.parseInt(city.get(
								"city_id").toString());
						if (cityIdTemp > city_id) {
							city_id = cityIdTemp;
						}
					}
				}
				if (city_id != -1)
					map.put("city_id", city_id);
			}
			if(map.get("scene")!=null && !"".equals(map.get("scene").toString()) && "zj".equals(map.get("scene").toString())){
				list = appModuleService.queryModuleByZJ(map);
			}else{
				list = appModuleService.queryModuleByCityId(map);
			}
			Map<String, Object> lifeConfig = new HashMap<>();
			lifeConfig.put("name", "modules");
			lifeConfig.put("list", list);
			dataList.add(lifeConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.getWriter().print(
				JsonUtil.appResposeMoreListJson(status, message, dataList));
	}

	/**
	 * 热门城市
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *
	 */
	@RequestMapping(value = "/getHotCity", method = RequestMethod.POST)
	public void getHotCity(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		List<Map<String, Object>> dataList = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map = this.getPageData();
		int status = ResponseCode.success.getCode();
		String message = ResponseCode.success.getMsg();
		try {
			List<Map<String, Object>> list = null;
			list = appModuleService.queryHotCitys(map);
			Map<String, Object> lifeConfig = new HashMap<>();
			lifeConfig.put("name", "citys");
			lifeConfig.put("list", list);
			dataList.add(lifeConfig);
		} catch (Exception e) {
			e.printStackTrace();

		}
		response.getWriter().print(
				JsonUtil.appResposeMoreListJson(status, message, dataList));
	}

}

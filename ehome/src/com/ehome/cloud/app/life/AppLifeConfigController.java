package com.ehome.cloud.app.life;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ehome.cloud.life.service.IAppLifeConfigService;
import com.ehome.core.dict.ResponseCode;
import com.ehome.core.frame.BaseController;
import com.ehome.core.util.JsonUtil;
import com.ehome.core.util.MapUtils;
import com.ehome.core.util.StringUtils;

/*
 * @Title:AppLifeConfigController
 * @Description:TODO
 * @author:zsh
 * @date:2017年2月28日
 * @version 1.0,2017年2月28日
 * @{tags}
  */
@Controller
@RequestMapping(value = "/app/appLifeConfig")
public class AppLifeConfigController extends BaseController {
//	private final static Logger logger = LoggerFactory
//			.getLogger(AppLifeConfigController.class);
	@Resource
	private IAppLifeConfigService appLifeConfigService;
	
	/**
	 *  通过城市id查询当前城市的服务父ID列表
	  * @param request
	  * @param response
	  * @throws Exception 
	  *
	 */
	@RequestMapping(value = "/getLifeConfigyCityId", method = RequestMethod.POST)
	public void getLifeConfigyCityId(HttpServletRequest request,
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
			if(StringUtils.isNoneBlank(MapUtils.getString(map, "city_name", null))){
				Integer city_id = -1;
				List<Map<String, Object>> citys = appLifeConfigService.queryCityInfo(map);
				if(CollectionUtils.isNotEmpty(citys)){
					for(Map<String, Object> city : citys){
						Integer cityIdTemp = Integer.parseInt(city.get("city_id").toString());
						if(cityIdTemp>city_id){
							city_id = cityIdTemp;
						}
					}
				}
				if(city_id!=-1) map.put("city_id", city_id);
			}
			list = appLifeConfigService.queryLifeConfigByCityId(map);
			if(CollectionUtils.isNotEmpty(list)){
				for (Map<String, Object> mapTemp : list) {
					List<Map<String, Object>> childs = appLifeConfigService.queryChilds(mapTemp.get("life_config_pid"));
					if (CollectionUtils.isNotEmpty(childs)) {
						mapTemp.put("is_start", 1);
					}else{
						mapTemp.put("is_start", 0);
					}
				}
			}
			Map<String, Object> lifeConfig = new HashMap<>();
			lifeConfig.put("name", "life_config_pids");
			lifeConfig.put("list", list);
			dataList.add(lifeConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.getWriter().print(
				JsonUtil.appResposeMoreListJson(status, message, dataList));
	}
	
	/**
	 *  根据父服务查询服务列表
	  * @param request
	  * @param response
	  * @throws Exception 
	  *
	 */
	@RequestMapping(value = "/getLifeConfigyPId", method = RequestMethod.POST)
	public void getLifeConfigyPId(HttpServletRequest request,
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
			list = appLifeConfigService.queryLifeConfigByPId(map);
			Map<String, Object> lifeConfig = new HashMap<>();
			lifeConfig.put("name", "lifeConfigs");
			lifeConfig.put("list", list);
			dataList.add(lifeConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.getWriter().print(
				JsonUtil.appResposeMoreListJson(status, message, dataList));
	}
	
	/**
	 *  根据服务id查询服务详情
	  * @param request
	  * @param response
	  * @throws Exception 
	  *
	 */
	@RequestMapping(value = "/getLifeConfigyId", method = RequestMethod.POST)
	public void getLifeConfigyId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		//List<Map<String, Object>> dataList = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map = this.getPageData();
		int status = ResponseCode.success.getCode();
		String message = ResponseCode.success.getMsg();
		try {
			map = appLifeConfigService.queryLifeConfigById(MapUtils.getInteger(map, "life_config_id", -1));			
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.getWriter().print(
				JsonUtil.appResposeSingeMapJson(status, message, map));
	}
	
	
	/**
	 *  热门城市
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
			list = appLifeConfigService.queryHotCitys(map);
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

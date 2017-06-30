package com.ehome.cloud.life.service;

import java.util.List;
import java.util.Map;

/*
 * @Title:IAppLifeConfigService
 * @Description:TODO
 * @author:zsh
 * @date:2017年2月28日
 * @version 1.0,2017年2月28日
 * @{tags}
  */
public interface IAppLifeConfigService {

	/**
	 * @param map
	 * @return 
	 * 
	 */
	List<Map<String, Object>> queryLifeConfigByCityId(Map<String, Object> map);

	/**
	 * @param integer
	 * @return 
	 * 
	 */
	List<Map<String, Object>> queryLifeConfigByPId(Map<String, Object> map);

	/**
	 * @param integer
	 * @return 
	 * 
	 */
	Map<String, Object> queryLifeConfigById(Integer integer);

	/**
	 * @param map
	 * @return 
	 * 
	 */
	List<Map<String, Object>> queryCityInfo(Map<String, Object> map);

	/**
	 * @param map
	 * @return 
	 * 
	 */
	List<Map<String, Object>> queryHotCitys(Map<String, Object> map);

	/**
	 * @param object
	 * @return 
	 * 
	 */
	List<Map<String, Object>> queryChilds(Object object);

}

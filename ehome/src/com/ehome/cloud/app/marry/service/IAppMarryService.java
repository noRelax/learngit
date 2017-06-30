package com.ehome.cloud.app.marry.service;

import java.util.List;
import java.util.Map;

import com.ehome.cloud.marry.model.MarryMemberModel;
import com.ehome.core.frame.IService;

/*
 * @Title:IAppMarryService
 * @Description:TODO
 * @author:zsh
 * @date:2017年4月19日
 * @version 1.0,2017年4月19日
 * @{tags}
  */
public interface IAppMarryService extends IService<MarryMemberModel> {

	
	 /**
	  * @param map 
	  * 
	  */
	void insertInterests(Map<String, Object> map);

	
	 /**
	  * @param id
	  * @return 
	  * 
	  */
	List<Map<String, Object>> queryInterets(Integer id);


	
	 /**
	  * @param id
	  * @return 
	  * 
	  */
	Map<String, Object> queryUserMarryInfo(Integer id);


	
	 /**
	  * @param map
	  * @return 
	  * 
	  */
	List<Map<String, Object>> queryThumpByAppUserId(Map<String, Object> map);


	
	 /**
	  * @param appUserId
	  * @return 
	  * 
	  */
	List<Map<String, Object>> querytMarryLoveByAppUserId(Map<String, Object> map);


	
	 /**
	  * @param map
	  * @return 
	  * 
	  */
	List<Map<String, Object>> querytMarryReplysByAppUserId(Map<String, Object> map);


    /**
     * 
     * @param map
     * @return
     */
    List<Map<String, Object>> querytMarryLooksByAppUserId(Map<String, Object> map);


    /**
     * 查询会员信息
     * @param intValue
     * @return
     */
    Map<String, Object> queryMemberByAppUserId(Integer appUserId);



}

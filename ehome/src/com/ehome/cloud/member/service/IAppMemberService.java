package com.ehome.cloud.member.service;

import java.util.List;
import java.util.Map;

import com.ehome.cloud.member.model.Member;
import com.ehome.core.frame.IService;



/* app会员
 * @Title:IAppMemberService
 * @Description:TODO
 * @author:zsh
 * @date:2017年2月22日
 * @version 1.0,2017年2月22日
 * @{tags}
  */
public interface IAppMemberService extends IService<Member>{

	
	 /**
	  * 查询基层工会
	  * @param map
	  * @return 
	  * 
	  */
	List<Map<String, Object>> queryBaseUnion(Map<String, Object> map);

	
	 /**
	  * @param map
	  * @return 
	  * 
	  */
	int insertApply(Map<String, Object> map);


	
	 /**
	  * @param map
	  * @return 
	  * 
	  */
	int updateApply(Map<String, Object> map);


	
	 /**
	  * @param integer
	  * @return 
	  * 
	  */
	Map<String, Object> queryApplyInfoById(Integer integer);


	
	 /**
	  * 查询省市
	  * @param map
	  * @return 
	  * 
	  */
	List<Map<String, Object>> queryProvinceCityByname(Map<String, Object> map);
	
	/**查询市
	 * @param map
	 * @return 
	 * 
	 */
	List<Map<String, Object>> queryCityByname(Map<String, Object> map);


	
	 /** 查询父id
	  * @param map
	  * @return 
	  * 
	  */
	Map<String, Object> queryOrgainPId(Map<String, Object> map);


	
	 /** 查询父id信息
	  * @param pidInfo
	  * @return 
	  * 
	  */
	Map<String, Object> queryOrgian(Map<String, Object> pidInfo);


	
	 /**
	  * @param map
	  * @return 
	  * 
	  */
	String queryCityNameByCityId(Map<String, Object> map);

}

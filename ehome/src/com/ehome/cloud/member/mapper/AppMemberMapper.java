package com.ehome.cloud.member.mapper;

import java.util.List;
import java.util.Map;


/**
 * 
 * @Title:AppMemberMapper
 * @Description:TODO
 * @author:zsh
 * @date:2017年2月22日
 * @version 1.0,2017年2月22日
 * @{tags}
 */
public interface AppMemberMapper{

	 /** 查询基层工会
	  * @param map
	  * @return 
	  * 
	  */
	List<Map<String, Object>> queryBaseUnion(Map<String, Object> map);

}

/**
 * @Project:ZGHome
 * @FileName:IMerchantService.java
 */
package com.ehome.cloud.puhui.service;

import java.util.List;
import java.util.Map;

import com.ehome.cloud.puhui.model.MerchantModel;
import com.ehome.core.frame.IService;

/**
 * 商家接口
 * 
 * @Title:IMerchantService
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月22日
 * @version:
 */

public interface IMerchantService extends IService<MerchantModel> {
	List<Map<String, Object>> selectPageByConditionMap(Map<String,Object> map, int page, int rows);
	List<Map<String, Object>> getListByPosition(Map<String, Object> conditionMap);
	int getOrderCountByMcId(int id);
	String getMcPicByPicid(int id);
	List<Map<String, Object>> getPicsByIds(String[] picIds);
	int setTypeByIds(Map<String,Object> map);
	List<Map<String, Object>> selectPageForRecommendlist(Map<String,Object> map, int page, int rows);
}

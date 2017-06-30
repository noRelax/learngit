/**
 * @Project:ZGHome
 * @FileName:MerchantServiceImpl.java
 */
package com.ehome.cloud.puhui.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ehome.cloud.puhui.mapper.MerchantMapper;
import com.ehome.cloud.puhui.model.MerchantModel;
import com.ehome.cloud.puhui.service.IMerchantService;
import com.ehome.core.frame.BaseService;
import com.github.pagehelper.PageHelper;

/**
 * 商家接口实现
 * 
 * @Title:MerchantServiceImpl
 * @Description:TODO
 * @author:张宗奎
 * @date:2017年2月22日
 * @version:
 */
@Service
public class MerchantServiceImpl extends BaseService<MerchantModel> implements IMerchantService {

	@Resource 
	private MerchantMapper merchantMapper;
	
	@Override
	public List<Map<String, Object>> selectPageByConditionMap(Map<String, Object> map, int page, int rows) {
		
		PageHelper.startPage(page, rows);
		return merchantMapper.selectPageByConditionMap(map);
	}

	@Override
	public List<Map<String, Object>> getListByPosition(Map<String, Object> conditionMap) {
		PageHelper.startPage((int)conditionMap.get("start"), (int)conditionMap.get("limit"), false);
		return merchantMapper.getListByPosition(conditionMap);
	}

	@Override
	public int getOrderCountByMcId(int id) {
		return merchantMapper.getOrderCountByMcId(id);
	}

	@Override
	public String getMcPicByPicid(int id) {
		return merchantMapper.getMcPicByPicid(id);
	}

	@Override
	public List<Map<String, Object>> getPicsByIds(String[] picIds) {
		return merchantMapper.getPicsByIds(picIds);
	}

	@Override
	public int setTypeByIds(Map<String, Object> map) {
		return merchantMapper.setTypeByIds(map);
	}

	
	@Override
	public List<Map<String, Object>> selectPageForRecommendlist(Map<String, Object> map, int page, int rows) {
		
		PageHelper.startPage(page, rows);
		return merchantMapper.selectPageForRecommendlist(map);
	}
}

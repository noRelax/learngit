package com.ehome.cloud.app.module.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.ehome.cloud.app.module.service.IAppModuleService;
/**
 * 
 * @Title:AppModuleServiceImpl
 * @Description:TODO
 * @author:zsh
 * @date:2017年3月9日
 * @version 1.0,2017年3月9日
 * @{tags}
 */
@Service("appModuleService")
public class AppModuleServiceImpl implements IAppModuleService {
	
	@Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;
	
	//通过城市id查询当前城市的模块列表
	public List<Map<String, Object>> queryModuleByCityId(Map<String,Object> map) {
		return sqlSessionTemplate.selectList("com.ehome.cloud.sys.mapper.ConfigModuleMapper.queryModuleByCityId",map);
	}

	 /**
	  * 查询城市信息
	  * @param map
	  * @return 
	  */
	@Override
	public List<Map<String, Object>> queryCityInfo(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("com.ehome.cloud.sys.mapper.ConfigModuleMapper.queryCityInfo",map);
	}

	
	 /**
	  * 
	  * @param map
	  * @return 
	  */
	@Override
	public List<Map<String, Object>> queryHotCitys(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("com.ehome.cloud.sys.mapper.ConfigModuleMapper.queryHotCitys",map);

	}

	
	 /**
	  * 
	  * @param map
	  * @return 
	  */
	@Override
	public List<Map<String, Object>> queryModuleByZJ(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("com.ehome.cloud.sys.mapper.ConfigModuleMapper.queryModuleByZJ",map);
	}
	
}

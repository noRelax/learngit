package com.ehome.cloud.life.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.ehome.cloud.life.service.IAppLifeConfigService;

@Service("appLifeConfigService")
public class AppLifeConfigServiceImpl implements IAppLifeConfigService {
	
	@Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;
	
	//通过城市id查询当前城市的服务
	public List<Map<String, Object>> queryLifeConfigByCityId(Map<String,Object> map) {
		return sqlSessionTemplate.selectList("com.ehome.cloud.life.mapper.AppLifeConfigMapper.queryLifeConfigByCityId",map);
	}
	
	//通过服务id查询服务详情
	public List<Map<String, Object>> queryLifeConfigByPId(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("com.ehome.cloud.life.mapper.AppLifeConfigMapper.queryLifeConfigByPId",map);
	}
	
	 /**
	  * 
	  * @param integer
	  * @return 
	  */
	@Override
	public Map<String, Object> queryLifeConfigById(Integer LifeConfigId) {
		return sqlSessionTemplate.selectOne("com.ehome.cloud.life.mapper.AppLifeConfigMapper.queryLifeConfigById",LifeConfigId);
	}
	
	 /**
	  * 
	  * @param map
	  * @return 
	  */
	@Override
	public List<Map<String, Object>> queryCityInfo(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("com.ehome.cloud.life.mapper.AppLifeConfigMapper.queryCityInfo",map);
	}
	
	 /**
	  * 
	  * @param map
	  * @return 
	  */
	@Override
	public List<Map<String, Object>> queryHotCitys(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("com.ehome.cloud.life.mapper.AppLifeConfigMapper.queryHotCitys",map);
	}

	 /**
	  * 
	  * @param object
	  * @return 
	  */
	@Override
	public List<Map<String, Object>> queryChilds(Object object) {
		return sqlSessionTemplate.selectList("com.ehome.cloud.life.mapper.AppLifeConfigMapper.queryChilds",object);
	}
	
}

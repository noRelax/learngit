package com.ehome.cloud.app.marry.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.ehome.cloud.app.marry.service.IAppMarryService;
import com.ehome.cloud.marry.model.MarryMemberModel;
import com.ehome.core.frame.BaseService;

/*
 * @Title:AppMarryServiceImpl
 * @Description:TODO
 * @author:zsh
 * @date:2017年4月19日
 * @version 1.0,2017年4月19日
 * @{tags}
 */
@Service("appMarryService")
public class AppMarryServiceImpl extends BaseService<MarryMemberModel>
		implements IAppMarryService {
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 
	 * @param map
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void insertInterests(Map<String, Object> map) {
		sqlSessionTemplate.delete(
				"com.ehome.cloud.marry.mapper.AppMarryMapper.deleteInterests",
				map);
		if (map.get("list") != null) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) map
					.get("list");
			if (list != null && list.size() > 0) {
				sqlSessionTemplate
						.insert("com.ehome.cloud.marry.mapper.AppMarryMapper.insertInterests",
								map);
			}
		}

	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryInterets(Integer id) {
		return sqlSessionTemplate
				.selectList(
						"com.ehome.cloud.marry.mapper.AppMarryMapper.queryInterets",
						id);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public Map<String, Object> queryUserMarryInfo(Integer id) {
		return sqlSessionTemplate
				.selectOne(
						"com.ehome.cloud.marry.mapper.AppMarryMapper.queryUserMarryInfo",
						id);
	}

	@Override
	public List<Map<String, Object>> queryThumpByAppUserId(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("com.ehome.cloud.marry.mapper.AppMarryMapper.queryThumpByAppUserId",map);
	}

	
	 /**
	  * 
	  * @param appUserId
	  * @return 
	  */
	@Override
	public List<Map<String, Object>> querytMarryLoveByAppUserId(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("com.ehome.cloud.marry.mapper.AppMarryMapper.querytMarryLoveByAppUserId",map);
	}

	
	 /**
	  * 
	  * @param map
	  * @return 
	  */
	@Override
	public List<Map<String, Object>> querytMarryReplysByAppUserId(Map<String, Object> map) {
		return sqlSessionTemplate.selectList("com.ehome.cloud.marry.mapper.AppMarryMapper.querytMarryReplysByAppUserId",map);
	}

    /**
     * //TODO 添加override说明
     * @see com.ehome.cloud.app.marry.service.IAppMarryService#querytMarryLooksByAppUserId(java.util.Map)
     **/
    @Override
    public List<Map<String, Object>> querytMarryLooksByAppUserId(Map<String, Object> map) {
        return sqlSessionTemplate.selectList("com.ehome.cloud.marry.mapper.AppMarryMapper.querytMarryLooksByAppUserId",map);
    }

    /**
     * 
     * @see com.ehome.cloud.app.marry.service.IAppMarryService#queryMemberByAppUserId(int)
     **/
    @Override
    public Map<String, Object> queryMemberByAppUserId(Integer appUserId) {
        return sqlSessionTemplate.selectOne("com.ehome.cloud.marry.mapper.AppMarryMapper.queryMemberByAppUserId",appUserId);
    }
}

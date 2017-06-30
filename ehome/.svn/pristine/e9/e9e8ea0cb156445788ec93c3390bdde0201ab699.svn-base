package com.ehome.cloud.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ehome.cloud.sys.model.OrgainModel;
import com.ehome.cloud.sys.model.TreeModel;
import com.ehome.core.frame.MyMapper;

/*
 * @Title:OrgainMapper
 * @Description:TODO
 * @author:zsh
 * @date:2017年2月3日
 * @version 1.0,2017年2月3日
 * @{tags}
 */
public interface OrgainMapper extends MyMapper<OrgainModel> {
	
	int insertOrgain(OrgainModel OrgainModel);

	int updateOrgain(OrgainModel OrgainModel);

	int deleteOrgainById(Integer id);

	List<OrgainModel> queryForList(String keyword);

	OrgainModel queryById(@Param("id") Integer id);

	List<Map<String, Object>> queryByPId(Map<String, Object> map);

	/**
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map<String, Object>> queryTreelist(Map map);

	/**
	 * 
	 * @param pid
	 * @return
	 */
	List<Map<String, Object>> getChilds(String pid);

	/**
	 * 
	 * @param id
	 * @return
	 */
	Map<String, Object> queryByIdReturnMap(Integer id);

	/**
	 * 
	 * @param orgainId
	 * @return
	 */
	List<Map<String, Object>> queryUsersByOrgainId(Integer orgainId);

	List<Map<String, Object>> queryUsersByDeptId(Integer deptId);

	/**
	 * 
	 * @param map
	 * @return
	 */
	int insertOrgainByMap(Map<String, Object> map);

	/**
	 * 
	 * @param map
	 * @return
	 */
	int updateOrgainByMap(Map<String, Object> map);

	List<TreeModel> findTreeNode(@Param("baseOrgId") Integer baseOrgId,
			@Param("orgainType") Integer orgainType,
			@Param("isChild") Integer isChild,
			@Param("isParent") Integer isParent);

	List<OrgainModel> queryOrgainList(
			@Param("orgIdList") List<Integer> orgIdList);

	int seletctUpperUnionId(@Param("unionChairId") Integer unionChairId);

	OrgainModel quserUnionChairName(@Param("id") Integer id);

	OrgainModel quserySupUnionChairName(@Param("id") Integer id);

	List<OrgainModel> selectOrgainType(@Param("id") Integer id);

	Integer selectOrgainId(@Param("id") Integer id);
}

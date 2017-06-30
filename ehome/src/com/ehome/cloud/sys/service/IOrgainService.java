package com.ehome.cloud.sys.service;

import java.util.List;
import java.util.Map;

import com.ehome.cloud.sys.model.OrgainModel;
import com.ehome.cloud.sys.model.TreeModel;
import com.ehome.core.frame.IService;

/*
 * @Title:IOrgainService
 * @Description:TODO
 * @author:zsh
 * @date:2017年2月3日
 * @version 1.0,2017年2月3日
 * @{tags}
 */
public interface IOrgainService extends IService<OrgainModel> {// 可以不继承IService接口
																// 让实现类继承BaseService

	public int insertOrgain(OrgainModel orgainModel) throws Exception;// 增加

	public int deleteOrgainById(Integer id) throws Exception;// 删除

	List<OrgainModel> queryForList(String keyword, Integer page, Integer rows)
			throws Exception;

	OrgainModel queryById(Integer id) throws Exception;

	public int updateOrgain(OrgainModel orgainModel) throws Exception;// 修改

	/**
	 * 
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryTreelist(Map<String, Object> map);

	/**
	 * 
	 * @param pid
	 * @return
	 */
	public List<Map<String, Object>> getChilds(String pid);

	/**
	 * 
	 * @param parseInt
	 * @return
	 */
	public Map<String, Object> queryByIdReturnMap(int parseInt);

	/**
	 * 
	 * @param orgainId
	 * @return
	 */
	public List<Map<String, Object>> queryUsersByOrgainId(Integer orgainId);

	public List<Map<String, Object>> queryUsersByDeptId(Integer deptId);

	/**
	 * 
	 * @param map
	 */
	public int insertOrgainByMap(Map<String, Object> map);

	/**
	 * 
	 * @param map
	 */
	public int updateOrgainByMap(Map<String, Object> map);

	List<TreeModel> findTreeNode(Integer baseOrgId, Integer orgainType,
			Integer isChild, Integer isParent);

	List<OrgainModel> queryOrgainList(List<Integer> orgIdList);

	public int seletctUpperUnionId(Integer unionChairId);

	public OrgainModel quserUnionChairName(Integer id);

	public OrgainModel quserySupUnionChairName(Integer id);

	List<OrgainModel> selectOrgainType(Integer id);

	public Integer selectOrgainId(Integer id);

	public List<Map<String, Object>> queryByPId(Map<String, Object> map);
}
